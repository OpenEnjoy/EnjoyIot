# iot-temporal-serviceimpl-kw

基于 **KaiwuDB** 的物联网时序数据服务实现，是 `module-eiot-temporal` 的可插拔存储后端之一。

## 定位

```
module-eiot-temporal/
├── iot-temporal-service              # 接口定义（IDevicePropertyData、IThingModelMessageData ...）
├── iot-temporal-serviceimpl-kw       # 【本模块】KaiwuDB 实现
├── iot-temporal-serviceimpl-iotdb    # IoTDB 实现
├── iot-temporal-serviceimpl-td       # TDengine 实现
├── iot-temporal-serviceimpl-es       # Elasticsearch 实现
├── iot-temporal-serviceimpl-timescaledb  # TimescaleDB 实现
└── iot-temporal-serviceimpl-rdb      # 通用关系型数据库实现
```

本模块通过实现 `iot-temporal-service` 中定义的接口，将 KaiwuDB 作为时序数据存储引擎引入系统。**按需引入**——只有 pom.xml 中依赖本模块时，Spring 才会扫描并启用 KaiwuDB 实现。

KaiwuDB 使用**超级表（Super Table）**模型组织时序数据：每类数据创建一张超级表模板，各设备/产品作为子表自动继承模板结构，通过 TAG 区分不同实体。

## 启用方式

在业务模块的 `pom.xml` 中添加依赖：

```xml
<dependency>
    <groupId>com.enjoy-iot</groupId>
    <artifactId>iot-temporal-serviceimpl-kw</artifactId>
</dependency>
```

然后配置 KaiwuDB 连接信息（见下方配置项），启动时 Spring 自动装配所有 Service Bean。

## 接口实现对照

| 接口（iot-temporal-service） | 实现类 | 职责 |
|---|---|---|
| `IDbStructureData` | `DbStructureDataImpl` | 初始化超级表；根据物模型创建/更新设备属性超级表结构 |
| `IDevicePropertyData` | `DevicePropertyDataImpl` | 设备属性时序数据读写，按产品分超级表 |
| `IThingModelMessageData` | `ThingModelMessageDataImpl` | 物模型消息存储与多维统计查询 |
| `IRuleLogData` | `RuleLogDataImpl` | 规则引擎执行日志 |
| `ITaskLogData` | `TaskLogDataImpl` | 任务执行日志 |
| `IVirtualDeviceLogData` | `VirtualDeviceLogDataImpl` | 虚拟设备运行日志 |

## 依赖

| 依赖 | 说明 |
|---|---|
| `kaiwudb-jdbc` | KaiwuDB JDBC 驱动 |
| `spring-boot-starter-jdbc` | Spring JDBC 基础（`JdbcTemplate`） |
| `dynamic-datasource-spring-boot-starter` | 动态数据源路由（`@DS` 注解切换数据源） |
| `spring-boot-starter-mybatis` | MyBatis Plus 支持，用于日志类数据的 CRUD |
| `iot-temporal-service` | 时序数据接口定义 |
| `module-eiot-api` | IoT 领域 API 定义（ThingModel、DeviceProperty 等 DTO） |
| `lombok` | 编译期代码生成 |

## 配置项

```yaml
spring:
  kw-datasource:
    url: jdbc:kaiwudb://127.0.0.1:26300/enjoy_iot
    username: root
    password: root
```

| 配置项 | 必填 | 说明 |
|---|---|---|
| `spring.kw-datasource.url` | 是 | KaiwuDB JDBC 连接地址 |
| `spring.kw-datasource.username` | 是 | 用户名 |
| `spring.kw-datasource.password` | 是 | 密码 |

KaiwuDB 数据源通过 `dynamic-datasource` 的 `@DS("kwDataSource")` 注解实现数据源路由，与业务主数据源隔离。

## 内部架构

```
src/main/java/com/enjoyiot/eiot/temporal/kw/
├── config/
│   ├── Constants.java              # 超级表命名规则
│   └── KwDatasourceConfig.java     # KaiwuDB 数据源 Bean 配置
├── dao/
│   ├── KwJdbcTemplate.java         # KaiwuDB 专用 JdbcTemplate 封装
│   ├── KwRuleLogMapper.java        # 规则日志 Mapper
│   ├── KwTaskLogMapper.java        # 任务日志 Mapper
│   ├── KwThingModelMessageMapper.java  # 物模型消息 Mapper
│   └── KwVirtualDeviceLogMapper.java   # 虚拟设备日志 Mapper
├── dm/
│   ├── FieldParser.java            # 物模型类型→KaiwuDB 列类型映射 & SQL 片段生成
│   ├── KwField.java                # 字段定义 DTO（name, type, length）
│   └── TableManager.java           # DDL SQL 生成（建表/删表/列变更）
├── model/
│   ├── KwDeviceProperty.java       # 设备属性行模型
│   ├── KwRuleLog.java              # 规则日志实体（→ rule_log 超级表）
│   ├── KwTaskLog.java              # 任务日志实体（→ task_log 超级表）
│   ├── KwThingModelMessage.java    # 物模型消息实体（→ thing_model_message 超级表）
│   └── KwVirtualDeviceLog.java     # 虚拟设备日志实体（→ virtual_device_log 超级表）
└── service/                        # 六个接口实现
```

### 两种数据访问模式

| 模式 | 适用场景 | 技术 |
|---|---|---|
| MyBatis Plus Mapper | 日志类数据（RuleLog/TaskLog/VirtualDeviceLog/ThingModelMessage 的分页查询与写入） | `@DS("kwDataSource")` 注解路由 |
| KwJdbcTemplate 直接 SQL | 设备属性读写（动态列名）、物模型消息统计聚合、DDL 操作 | 参数化 SQL，`?` 占位符 |

### 类型映射

`FieldParser` 将物模型数据类型映射为 KaiwuDB 列类型：

| 物模型类型 | KaiwuDB 列类型 | 说明 |
|---|---|---|
| `int` | `INT4` | 32 位整数 |
| `int32` / `long` / `int64` | `INT8` | 64 位整数 |
| `float` / `double` | `FLOAT8` | 浮点数 |
| `bool` | `INT2` | 布尔值 |
| `enum` | `INT4` | 枚举（存序号） |
| `string` / `text` / `date` / `datetime` / `array` / `object` / `position` | `NCHAR` | 字符串类，默认长度 1024 |

## 超级表命名与结构

### 固定超级表（启动时自动创建）

| 超级表名 | TAG 列 | 数据列 | 用途 |
|---|---|---|---|
| `rule_log` | `rule_id INT8` | `time, state1, content, success` | 规则执行日志 |
| `task_log` | `task_id INT8` | `time, content, success` | 任务执行日志 |
| `thing_model_message` | `device_id INT8` | `time, mid, product_key, device_name, uid, type, identifier, code, data, report_time` | 物模型上下行消息 |
| `virtual_device_log` | `virtual_device_id INT8` | `time, virtual_device_name, device_total, result` | 虚拟设备运行日志 |

### 动态超级表（根据物模型创建）

| 超级表名 | 命名规则 | TAG 列 |
|---|---|---|
| `product_property_{productKey}` | `Constants.getProductPropertyTableName(key)` | `device_id INT8` |

数据列为物模型定义的属性标识符（如 `temperature`, `humidity`），前缀为固定列 `time TIMESTAMP NOT NULL`。

### KwRuleLog 字段映射

`KwRuleLog.state1` 对应 API 模型 `RuleLog.state`。写入时 `add(RuleLog)` 自动将 `state` 映射到 `state1` 列；查询时反向映射。这是因为 KaiwuDB 保留字或字段命名约定所致。

## 启动流程

1. `KwDatasourceConfig` 创建 `kwDataSource` Bean 并注册到 `DynamicRoutingDataSource`，同时构建 `KwJdbcTemplate`
2. `DbStructureDataImpl.initDbStructure()` 通过 `@PostConstruct` 自动执行：
   - 依次检查 `rule_log`、`task_log`、`thing_model_message`、`virtual_device_log` 四张超级表是否存在
   - 不存在的表自动创建（先查 `SHOW COLUMNS`，异常则执行 `CREATE TABLE`）

## API 示例

### 设备属性

```java
@Resource
private IDevicePropertyData devicePropertyData;

// 批量写入属性（自动根据物模型类型做类型转换）
Map<String, DevicePropertyCache> props = new HashMap<>();
props.put("temperature", new DevicePropertyCache(25.5, System.currentTimeMillis()));
devicePropertyData.addProperties(deviceId, props, System.currentTimeMillis());

// 按时间范围查历史（name 为属性标识符，仅允许字母数字下划线）
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

// 按小时统计上下行消息量
List<TimeData> stats = messageData.getDeviceUpMessageStatsWithUid(uid, startTs, endTs);
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

// 分页查询（按时间倒序）
PageResult<RuleLog> r = ruleLogData.findByRuleId(ruleId, 1, 10);
PageResult<TaskLog> t = taskLogData.findByTaskId(taskId, 1, 10);
PageResult<VirtualDeviceLog> v = virtualDeviceLogData.findByVirtualDeviceId(vdId, 1, 10);

// 删除
ruleLogData.deleteByRuleId(ruleId);
taskLogData.deleteByTaskId(taskId);
```

## 注意事项

- **物模型定义即建表**：调用 `defineThingModel` 会立即创建对应的 `product_property_{key}` 超级表；`updateThingModel` 会对比新旧字段差异并执行 ADD/MODIFY/DROP COLUMN
- **属性名安全校验**：`findDevicePropertyHistory` 的 `name` 参数仅允许 `[a-zA-Z_][a-zA-Z0-9_]*` 格式，防止 SQL 注入
- **动态列写入**：`addProperties` 写入时根据物模型字段类型做自动类型转换（INT→Convert.toInt, FLOAT→Convert.toDouble, NCHAR→toString/JSON）
- **TIMETRUNCATE 聚合**：消息统计方法使用 KaiwuDB 的 `TIMETRUNCATE(time, '1h')` 函数按小时窗口聚合
- **KWTimestamp**：时间字段使用 KaiwuDB 专用的 `KWTimestamp` 类型，写入时 `new KWTimestamp(epochMillis)`
- **分页从 1 开始**：`page` 以 1 为首页
- **设备属性查询仅返回前 N 条**：`findDevicePropertyHistory` 的 `OFFSET` 固定为 0，不翻页——如需更多数据请缩小时间范围
