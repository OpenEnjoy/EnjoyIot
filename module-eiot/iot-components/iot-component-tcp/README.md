# TCP 模块教程

## 概览
- 模块位置：`module-eiot/iot-components/iot-component-tcp`
- 目标：为设备提供基于 TCP 的轻量通信接入，支持注册、心跳、数据上报与数据下发
- 技术栈：Spring + Vert.x TCP Server
- 组件架构：`TcpStarter` 启动 → `TcpVerticle` 管理 TCP 服务 → `TcpComponent` 处理协议与平台集成 → `parser/*` 编解码

## 协议说明
- 数据包结构（大端序）：
  - `length(4B) | addrLen(2B) | addr(bytes) | code(2B) | mid(2B) | payload(bytes)`
  - 其中 `length` 是后续字段的总字节数（不包含自身 4 字节）
- 功能码：
  - `0x10` 设备注册（包体：`productKey` 字节，可选）
  - `0x11` 注册回复（包体：4 字节整型状态码，大端；0 表示成功）
  - `0x20` 设备心跳（包体：无；建议每 30 秒一次）
  - `0x30` 设备数据上报（包体：业务数据；接收端不回复）
  - `0x40` 设备数据下发（包体：业务数据；服务端发给设备）
- 约定：
  - 注册包体缺省时，仅回复成功但不进行平台注册；需平台预先创建设备（以设备地址为 `deviceName`）
  - 超过 60 秒未收到心跳判定离线

## 关键代码
- 启动与部署：
  - `TcpStarter` 在 Spring 初始化后部署 Vert.x：`src/main/java/.../TcpStarter.java:20-29`
  - `TcpVerticle` 持有 `NetServer` 并绑定连接处理器：`src/main/java/.../service/TcpVerticle.java:29-47`
- 协议编解码：
  - 编码：`src/main/java/.../parser/DataEncoder.java:19-27`
  - 解码：`src/main/java/.../parser/DataDecoder.java:23-34`
  - 解析长度前缀：`src/main/java/.../parser/DataReader.java:17-45`
  - 常量与包体类型（字节数组）：`src/main/java/.../parser/DataPackage.java:26-31,54-58`
- 服务端处理：
  - 绑定连接处理器：`src/main/java/.../service/TcpComponent.java:99-105`
  - 连接接入与解析：`src/main/java/.../service/TcpComponent.java:109-135`
  - 注册逻辑（`productKey` 可选）：`src/main/java/.../service/TcpComponent.java:134-168`
  - 心跳处理与上线上报：`src/main/java/.../service/TcpComponent.java:170-174,351-363`
  - 数据上报（不回复 ACK）：`src/main/java/.../service/TcpComponent.java:176-186`
  - 数据下发（属性设置）：`src/main/java/.../service/TcpComponent.java:292-303`
  - 离线判定（>60s）：`src/main/java/.../service/TcpComponent.java:315-342`

## 配置与启动
- 组件配置模型：`TcpConfig` 包含 `host` 与 `port`，见 `src/main/java/.../model/TcpConfig.java`
- 启动方式：
  - 组件管理下发 `TcpConfig` 后，`TcpComponent.stateChange` 启动或停止 TCP 服务：`src/main/java/.../service/TcpComponent.java:228-251`
  - 本地构建与运行（示例）：
    - 构建：`mvn -q -DskipTests -pl module-eiot/iot-components/iot-component-tcp -am clean package`
    - 启动平台服务，确保组件管理模块可下发 TCP 配置

## 客户端测试
- 示例客户端：`src/test/java/.../client/TcpTestClient.java`
  - 参数：`host port deviceName [productKey] [intervalSec] [mode]`
    - `intervalSec` 默认 `5`（上报间隔秒数）
    - `mode`：`prop`、`event`、`both`（默认 `both`，交替上报）
  - 行为：
    - 注册（可选 `productKey`）→ 读注册 ACK（4 字节状态码）
    - 发送一次心跳（`0x20`）
    - 每隔 `intervalSec` 发送数据上报（`0x30`），不读 ACK
    - 尝试读取一次数据下发（`0x40`），打印负载
  - 运行示例：
    - `java -cp target/classes;target/test-classes com.enjoyiot.eiot.component.tcp.client.TcpTestClient 127.0.0.1 6666 C00001 R755G5Wb3jst4tD7 3 prop`
    - `java -cp target/classes;target/test-classes com.enjoyiot.eiot.component.tcp.client.TcpTestClient 127.0.0.1 6666 C00001 R755G5Wb3jst4tD7 10 both`

## 与平台集成
- 组件发现与路由缓存：
  - 组件发现发布与订阅由基类完成：`module-eiot/iot-components/iot-component-core/src/main/java/com/enjoyiot/eiot/component/core/AbstractComponent.java:80-91,116-122,139-144`
  - 注册成功后缓存设备路由（用于下发寻址）：`TcpComponent.cacheDeviceComponentInfo` 调用基类方法，见注册分支：`src/main/java/.../service/TcpComponent.java:154-155`
- 上报统一封装：
  - 调用 `ThingComponent.report` 转发为平台消息：`module-eiot/iot-components/iot-component-core/src/main/java/com/enjoyiot/eiot/component/core/ThingComponent.java:206-228`

## 负载示例
- 注册包体：`productKey` 的字节数组（UTF-8 编码）
- 数据上报包体：自定义 JSON 或二进制（按双方约定），例如：
  - `{"temp":23.5,"hum":66}`（属性上报）
  - `{"event":"alarm","level":2}`（事件作为数据的一类）
- 下发包体：属性设置 JSON，例如：
  - `{"id":"<requestId>","method":"thing.service.property.set","params":{"switch":1}}`

## 常见问题
- 包长度与字符集：
  - 必须按字节长度计算 `length` 与 `addrLen`；地址使用 UTF-8 编码；包体按字节透传
- ACK 行为：
  - 数据上报（`0x30`）不需要服务端回复；仅注册（`0x11`）会回复 4 字节整型状态码
- 未注册设备：
  - 非注册/心跳的包，若设备未注册则断开连接：`src/main/java/.../service/TcpComponent.java:218-223`
- 心跳与离线：
  - 建议设备每 30 秒发送一次心跳；超过 60 秒未收到心跳判定离线并上报：`src/main/java/.../service/TcpComponent.java:315-342`

## 扩展建议
- 若需要严格区分“属性上报”与“事件上报”，可在 `0x30` 包体中增加 `type` 字段；服务端按 `type` 解析后分别转为属性/事件上报
- 若需要批量下发，建议在 `0x40` 包体中添加 `id`、`method`、`params` 等字段保持一致的服务调用模型

