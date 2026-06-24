# iot-temporal-serviceimpl-iotdb

基于 **Apache IoTDB** 的物联网时序数据服务实现，是 `module-eiot-temporal` 的可插拔存储后端之一。

## 定位

```
module-eiot-temporal/
├── iot-temporal-service              # 接口定义（IDevicePropertyData、IThingModelMessageData ...）
├── iot-temporal-serviceimpl-iotdb    # 【本模块】IoTDB 实现
├── iot-temporal-serviceimpl-td       # TDengine 实现
├── iot-temporal-serviceimpl-es       # Elasticsearch 实现
├── iot-temporal-serviceimpl-kw       # KaiwuDB 实现
├── iot-temporal-serviceimpl-timescaledb  # TimescaleDB 实现
└── iot-temporal-serviceimpl-rdb      # 通用关系型数据库实现
```

本模块通过实现 `iot-temporal-service` 中定义的接口，将 IoTDB 作为时序数据存储引擎引入系统。**按需引入**——只有 pom.xml 中依赖本模块时，Spring 才会扫描并启用 IoTDB 实现。

## 启用方式

在业务模块的 `pom.xml` 中添加依赖：

```xml
<dependency>
    <groupId>com.enjoy-iot</groupId>
    <artifactId>iot-temporal-serviceimpl-iotdb</artifactId>
</dependency>
```

然后配置 IoTDB 连接信息（见下方配置项），启动时 Spring 自动装配所有 Service Bean。

## 接口实现对照

| 接口（iot-temporal-service） | 实现类 | 职责 |
|---|---|---|
| `IDbStructureData` | `DbStructureDataImpl` | 初始化 storage group 和物模型 message template；`defineThingModel` / `updateThingModel` 为空实现（见注意事项） |
| `IDevicePropertyData` | `DevicePropertyDataImpl` | 设备属性时序数据读写 |
| `IThingModelMessageData` | `ThingModelMessageDataImpl` | 物模型消息存储与多维查询 |
| `IRuleLogData` | `RuleLogDataImpl` | 规则引擎执行日志 |
| `ITaskLogData` | `TaskLogDataImpl` | 任务执行日志 |
| `IVirtualDeviceLogData` | `VirtualDeviceLogDataImpl` | 虚拟设备运行日志 |

## 依赖

| 依赖 | 说明 |
|---|---|
| `iotdb-session` (1.3.1) | IoTDB SessionPool，通过 Thrift RPC 直连，自带连接池 |
| `spring-boot-starter` | Spring 基础（`@Service`、`@Resource` 等注解） |
| `iot-temporal-service` | 时序数据接口定义 |
| `lombok` | 编译期代码生成 |

## 配置项

```yaml
spring:
  iotdb-datasource:
    url: 127.0.0.1:6667
    username: root
    password: root
    baseDb: root.enjoy_iot
```

| 配置项 | 必填 | 说明 |
|---|---|---|
| `spring.iotdb-datasource.url` | 是 | IoTDB 节点，格式 `host:port`，多节点逗号分隔 |
| `spring.iotdb-datasource.username` | 是 | 用户名 |
| `spring.iotdb-datasource.password` | 是 | 密码 |
| `spring.iotdb-datasource.baseDb` | 是 | 时序数据库根路径（storage group），所有数据统一存放在此路径下 |

## 内部架构

```
src/main/java/com/enjoyiot/eiot/temporal/iotdb/
├── config/
│   ├── Constants.java              # 时序路径前缀常量
│   └── IotdbDatasourceConfig.java  # SessionPool Bean 配置
├── dao/
│   └── IotdbBaseService.java       # 通用 CRUD 基类（insertRecord / queryList / queryPage）
└── service/                        # 六个接口实现
```

`IotdbBaseService<T>` 封装了 IoTDB SessionPool 的基础操作：
- `insertRecord(path, properties, time)` — 写入时序数据，自动推断 TSDataType
- `queryList(sql, defaultEntity)` — 执行查询，通过模板方法 `mapToEntity` 映射结果
- `queryPage(countSql, sql, defaultEntity)` — 分页查询，先 count 再查数据
- `deleteTimeseries(path)` — 删除指定路径序列
- 一系列 `tryGetXxxV(rowRecord, key, columnIndexMap)` 安全取值方法

## 数据路径规范

```
{baseDb}                                # root.enjoy_iot
├── p_{productKey}.d_{dn}               # 设备属性，列名由写入数据动态决定
│   └── temperature / humidity / ...
├── thing_model_message.d_{dn}          # 物模型消息（使用 schema template）
│   ├── mid / product_key / device_name / uid
│   ├── type / identifier / code / data / report_time
├── rule_log.r_{ruleId}                 # 规则日志
│   ├── state1 / content / success
├── task_log.t_{taskId}                 # 任务日志
│   ├── content / success
└── virtual_device_log.v_{virtualId}    # 虚拟设备日志
    ├── virtual_device_name / device_total / result
```

## API 示例

### 设备属性

```java
@Resource
private IDevicePropertyData devicePropertyData;

// 批量写入属性
Map<String, DevicePropertyCache> props = new HashMap<>();
props.put("temperature", new DevicePropertyCache(25.5, System.currentTimeMillis()));
devicePropertyData.addProperties(deviceId, props, System.currentTimeMillis());

// 按时间范围查历史
List<DeviceProperty> list = devicePropertyData.findDevicePropertyHistory(
    deviceId, "temperature", startTs, endTs, 100);
```

### 物模型消息

```java
@Resource
private IThingModelMessageData messageData;

// 写入消息
messageData.add(ThingModelMessage.builder()
    .mid("msg_001").deviceId(1L).dn("dev_01")
    .type("property").identifier("temp").code(0)
    .data(Map.of("value", 25.5)).occurred(System.currentTimeMillis())
    .build());

// 单设备查询
PageResult<ThingModelMessage> page = messageData
    .findByTypeAndIdentifier(deviceId, "property", "temp", 1, 20);

// 跨设备查询（时间倒序）
PageResult<ThingModelMessage> page2 = messageData
    .findByTypeAndDeviceIds(deviceIds, "event", null, 1, 20);

// 消息总数
long total = messageData.count();
```

### 规则 / 任务 / 虚拟设备日志

```java
@Resource private IRuleLogData ruleLogData;
@Resource private ITaskLogData taskLogData;
@Resource private IVirtualDeviceLogData virtualDeviceLogData;

// 写入
ruleLogData.add(new RuleLog(null, ruleId, "matched_filter", "命中", true, null));
taskLogData.add(TaskLog.builder().taskId(taskId).content("完成").success(true).build());
virtualDeviceLogData.add(VirtualDeviceLog.builder()
    .virtualDeviceId(vdId).virtualDeviceName("虚拟设备").deviceTotal(10).result("ok").build());

// 分页查询
PageResult<RuleLog> r = ruleLogData.findByRuleId(ruleId, 1, 10);
PageResult<TaskLog> t = taskLogData.findByTaskId(taskId, 1, 10);
PageResult<VirtualDeviceLog> v = virtualDeviceLogData.findByVirtualDeviceId(vdId, 1, 10);

// 删除
ruleLogData.deleteByRuleId(ruleId);
taskLogData.deleteByTaskId(taskId);
```

## 启动流程

1. `IotdbDatasourceConfig` 创建 `SessionPool` Bean（Thrift RPC 连接，maxSize=3）
2. `DbStructureDataImpl.initDbStructure()` 通过 `@PostConstruct` 自动执行：
   - 检查并创建 `baseDb` storage group
   - 检查并创建 `thing_model_message` schema template 并绑定到路径

## 注意事项

- **`defineThingModel` / `updateThingModel` 为空实现**：IoTDB 在写入时自动创建测点（`SessionPool.insertRecord`），无需像 TDengine/TimescaleDB 那样预先建表或列。物模型变更不影响已有数据的读写
- **设备属性列自动创建**：不在 template 中预定义，按实际写入的 key 动态生成测点
- **SessionPool 连接数**：默认 maxSize=3，高并发时可调整 `IotdbDatasourceConfig`
- **分页从 1 开始**：`page` 以 1 为首页
- **删除行为**：`deleteTimeseries` 只删序列元数据，历史数据依赖 IoTDB 侧 TTL 策略清理
