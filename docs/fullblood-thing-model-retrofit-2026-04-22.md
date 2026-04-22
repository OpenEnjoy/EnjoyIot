# 满血版物模型改造说明（2026-04-22）

## 1. 背景与目标

本次改造目标是把“残缺物模型链路”补齐为可运行的全链路，且仅改动物模型相关模块，不影响其他业务模块。

目标覆盖：

- 物模型复杂结构可上报与存储（含 `object`/`array` 多层嵌套、`bool`、`enum`、`date`、`datetime` 等）
- 规则引擎可消费物模型消息并执行
- 设备控制链路可下发（`property/set`、`service/invoke`）
- 前后端主链路可打通：产品 -> 设备 -> 消息 -> 规则 -> 下发

---

## 2. 本次实际改造范围

### 2.1 代码改动文件

1. [module-eiot/iot-components/iot-component-mqtt/src/main/java/com/enjoyiot/eiot/component/mqtt/service/MqttComponent.java](D:/aiLocal/enjoy-iot/module-eiot/iot-components/iot-component-mqtt/src/main/java/com/enjoyiot/eiot/component/mqtt/service/MqttComponent.java)
2. [module-eiot/module-eiot-temporal/iot-temporal-serviceimpl-td/src/main/java/com/enjoyiot/eiot/temporal/td/service/DevicePropertyDataImpl.java](D:/aiLocal/enjoy-iot/module-eiot/module-eiot-temporal/iot-temporal-serviceimpl-td/src/main/java/com/enjoyiot/eiot/temporal/td/service/DevicePropertyDataImpl.java)

### 2.2 关键修复点

#### A. MQTT `clientId` 解析增强（支持 `productKey` 含下划线）

问题：

- 原逻辑固定 `clientId=productKey_deviceName_model` 且按 `_` 切 3 段。
- 当 `productKey` 本身包含 `_` 时，连接被拒绝，设备无法在线，导致下行路由失败。

修复：

- 改为“从尾段反向解析”：
  - 最后 1 段 = `model`
  - 倒数第 2 段 = `deviceName`
  - 前面所有段合并回 `productKey`

结果：

- `E2E_COMPLEX_...` 这类产品可正常 MQTT 建连并进入在线链路。

#### B. Endpoint 键值与失效连接兜底

问题：

- endpoint key 使用 `_` 拼接，存在歧义风险。
- 下发时命中失效 endpoint，可能抛 `Connection not accepted yet`。

修复：

- endpoint key 改为稳定分隔符（`||`）组合。
- `publish` 失败/异常时自动清理 endpoint，避免残留连接反复报错。

#### C. TDengine `bool` 入库标准化（`true/false -> 1/0`）

问题：

- TDengine 对该链路字段定义为 `TINYINT`。
- 布尔值直接写 `true/false` 会触发 `invalid tinyint data`，导致属性链路报错。

修复：

- 入库前统一转换：
  - `Boolean true/false -> 1/0`
  - 字符串 `"true"/"false" -> 1/0`

结果：

- 清除 `invalid tinyint data` 报错，属性时序入库恢复正常。

---

## 3. 全链路验证结果

验证时间：2026-04-22（本地环境，MySQL/Redis/TDengine 已启动）

### 3.1 设备在线与基础查询

- `POST /admin-api/eiot/device/getDeviceWithProperty`：`code=0`
- 复杂测试设备：
  - `deviceId=2046612371976318976`
  - `productKey=E2E_COMPLEX_20260421232948`
  - `dn=E2E-COMPLEX-DN-20260421232948`
- MQTT 常连时 `online=true`；脚本退出后会离线（符合预期）。

### 3.2 上行消息（property/event/service）

- `POST /admin-api/eiot/device/simulateSend`
  - `type=property, identifier=report`：`code=0`
  - `type=event, identifier=alarm`：`code=0`
  - `type=service, identifier=calibrate_reply`：`code=0`

### 3.3 规则引擎

- 可观察到规则执行日志（含 `rule execution completed`）。
- 规则日志页面 `ruleLog/list` 与后端执行存在异步窗口，建议看执行日志与动作结果联合判断。

### 3.4 下行消息

- `POST /admin-api/eiot/device/service/property/set`：`code=0`
- `POST /admin-api/eiot/device/service/invoke`：`code=0`
- 设备侧 MQTT 收到下行：
  - `/c/service/property/set`
  - `/c/service/{serviceName}`（如 `calibrate`）

### 3.5 设备在线判定逻辑（关键说明）

- 在线不是“只看最近一条消息是否存在”，而是综合最近活跃时间与产品保活参数判断。
- 设备上报消息会刷新最近活跃时间（lastActiveTime）。
- 在线状态判定基于 `lastActiveTime + keepAliveTime`（产品配置）与当前时间比较：
  - 未超时：`online=true`
  - 超时：`online=false`
- 因此“脚本退出后在线设备消失”是预期行为，不是链路异常。

---

## 4. 关于 `bool` 显示为 `true/false` 的说明

- **消息层（MQTT payload / rule 消费日志）**：可能看到 `true/false`（原始 JSON 表达）
- **存储层（TDengine）**：已统一落 `0/1`

即：日志看到 `true` 不等于数据库不是 `0/1`；两层语义不同。

---

## 5. 交付信息

- 分支：`feature/fullblood-thing-model`
- 本次关键提交：`14d41df9`
- 已推送：`origin/feature/fullblood-thing-model`

---

## 6. 后续建议（可选）

1. 若产品要求“全系统只展示 0/1”，可在入站消息层再加一次统一标准化（日志/规则/UI 全部数字化）。
2. 增加自动化回归用例：
   - `productKey` 含 `_` 的 MQTT 认证与上下行
   - `bool` 在 TDengine 的写入/查询断言
   - 复杂 `object/array` 三层结构上报与规则触发
3. 规则日志查询可补一个“执行态实时缓存”或查询延迟提示，避免异步窗口导致误判。
