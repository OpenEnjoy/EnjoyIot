# 满血版物模型改造与闭环验证文档（2026-04-22）

## 1. 本次目标
- 规则引擎监听器/过滤器中，`object` 和 `array` 从“整段 JSON 文本输入”改为“字段路径 + 类型化输入”。
- 物模型补齐全类型：`bool/enum/int32/int64/float/double/string/text/date/datetime/position/object/array`。
- `service` 与 `event` 参数同样覆盖全类型（含三层 `object/array` 嵌套）。
- 提供一键可复现脚本：自动新建产品、物模型、设备、规则并完成全链路验证。

## 2. 规则引擎可用性改造（前端）
改动文件：
- `enjoy-web/src/views/eiot/ruleinfo/modules/listener.vue`
- `enjoy-web/src/views/eiot/ruleinfo/modules/filtera.vue`

核心改造点：
- 字段选择支持复杂路径展开（含多层 `object/array`，例如 `p_array3[*].items[*].k`）。
- 下拉项展示统一为“字段名 + 数据类型”，并支持 `filterable`。
- 比较器按数据类型动态收敛，复杂结构优先使用 `contain/notContain/==/!=`。
- 值输入控件按类型切换：
  - `bool`：固定 `0/1` 下拉（附带映射文案，例如 `0（在线）`、`1（离线）`）
  - `enum`：下拉
  - `date`：日期选择
  - `datetime`：日期时间选择
  - 其他：输入框
- 修复监听器中的枚举类型识别错误（`enum` 不再被误判）。
- 修复监听器输入校验乱码文案，统一为可读中文提示。

## 3. 后端修复（规则保存返回值）
改动文件：
- `enjoy-iot/module-eiot/module-eiot-biz/src/main/java/com/enjoyiot/module/eiot/service/rule/EiotRuleInfoServiceImpl.java`

修复内容：
- `rule_engine/save` 在“新建规则”场景返回 `newId`，避免返回 `null` 导致调用方拿不到规则 ID。

## 4. 全链路自动化脚本与数据
新增/使用脚本：
- `enjoy-web/scripts/eiot/fullblood-recreate-and-verify.js`（新建实体并验证）
- `enjoy-web/scripts/eiot/fullblood-e2e-runner.js`（已有闭环脚本）
- `enjoy-web/scripts/eiot/run-fullblood-e2e.ps1`（PowerShell 入口）
- `enjoy-web/scripts/eiot/fullblood-all-types-testdata.json`（全类型测试数据）

`fullblood-recreate-and-verify.js` 覆盖：
1. 登录后台
2. 新建产品
3. 保存满血物模型（含三层 `object/array`，含 `event/service`）
4. 新建设备
5. 新建规则并启用
6. 上报 `property/event/service_reply`
7. 下发 `property/set` 与 `service/invoke`
8. 校验设备日志、规则日志、MQTT 下行主题
9. 输出报告和本次生成测试数据

## 5. 关键脚本修正
- `fullblood-recreate-and-verify.js` 新增规则 ID 兜底解析：
  - `save` 返回空时，通过规则名回查 `rule_engine/page` 获取 ID。
- `simulateSend` 请求补齐 `productKey`、`dn`：
  - 否则规则监听器按 `pk/dn` 匹配会失配，导致规则日志为 0。

## 6. 最新一次闭环验证结果（已通过）
执行时间：2026-04-22 19:58（Asia/Shanghai）

执行命令：
```powershell
node scripts/eiot/fullblood-recreate-and-verify.js
```

结果摘要：
- `pass: true`
- 生成实体：
  - `productKey: AUTO_FULLBLOOD_1776859131764`
  - `dn: AUTO-FULLBLOOD-DN-1776859131764`
  - `deviceId: 2046921674897514496`
  - `ruleId: 33`
- 规则日志命中：通过（脚本断言成功）
- MQTT 下行命中：
  - `/c/service/property/set`
  - `/c/service/calibrate`

产物文件：
- `enjoy-web/tmp/fullblood-recreate-report-1776859134910.json`
- `enjoy-web/tmp/fullblood-generated-testdata-1776859131764.json`

## 7. 前端构建验证
已通过：
```powershell
pnpm exec eslint src/views/eiot/ruleinfo/modules/listener.vue src/views/eiot/ruleinfo/modules/filtera.vue
pnpm build:local
```
