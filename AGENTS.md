# AGENTS.md

## 项目概述

Enjoy-IoT（乐享智联）开源物联网平台后端，基于 Spring Boot 3.4.1 / Java 21。在 RuoYi-Vue-Pro 通用管理框架基础上扩展了完整的 IoT 能力：设备管理、协议接入（MQTT/HTTP/TCP/UDP/CoAP/Modbus）、物模型、规则引擎、告警中心、时序数据存储、OTA 升级、设备影子。

前端项目：`../enjoy-iot-vue3`（Vue 3 + TypeScript），API 路径前缀 `/admin-api`。

## 技术栈

| 类别 | 技术 |
|------|------|
| 语言 | Java 21 |
| 框架 | Spring Boot 3.4.1, Spring Security 6.4.2 |
| ORM | MyBatis Plus 3.5.9 + dynamic-datasource（多数据源） |
| 数据库 | MySQL 8.x（主库），Druid 连接池 |
| 缓存/锁 | Redis + Redisson |
| 消息队列 | Spring Event / Redis / RabbitMQ / Kafka / RocketMQ（可配置） |
| 时序数据库 | TDengine / IoTDB / PostgreSQL+TimescaleDB / Elasticsearch / KaiwuDB（可插拔） |
| 定时任务 | Quartz（JDBC JobStore，集群模式） |
| 工作流 | Flowable 6.8.0 |
| 协议组件 | Netty（TCP/UDP）, Vert.x（MQTT/CoAP/HTTP）, Eclipse Paho（MQTT 客户端） |
| 构建 | Maven 多模块 |
| 文档 | springdoc-openapi + Knife4j |

## 模块架构

```
iot-platform（根 pom）
├── dependencies/              — BOM：集中管理依赖版本
├── framework/                 — 14+ 个 Spring Boot Starter（web、security、mybatis、redis、mq、websocket、job、excel、monitor、biz-tenant、biz-data-permission、biz-ip、protection、test）
├── server/                    — 应用入口（ServerApplication.java），配置和资源文件
├── module-system/             — 系统管理：用户、角色、菜单、部门、租户、OAuth2、社交登录
│   ├── module-system-api      — 接口 + DTO
│   └── module-system-biz      — 实现
├── module-infra/              — 基础设施：代码生成、文件、配置、定时任务、日志、WebSocket
│   ├── module-infra-api
│   └── module-infra-biz
├── module-eiot/               — IoT 核心（最大模块）
│   ├── module-eiot-api        — 接口：告警、设备、产品、规则、物模型、影子
│   ├── module-eiot-biz        — REST 控制器、Service、MySQL DAL
│   ├── module-eiot-core       — 消息路由、事件总线、协议引擎
│   ├── module-eiot-task       — IoT 定时任务
│   ├── module-eiot-temporal   — 时序数据抽象层 + 可插拔实现（RDB/TD/ES/IoTDB/KW/TimescaleDB）
│   └── iot-components/        — 协议组件（MQTT、EMQX、HTTP、UDP、TCP、CoAP、Modbus）
└── module-ai/                 — AI 算法集成
    ├── module-ai-api
    └── module-ai-biz
```

## 包结构约定

根包：`com.enjoyiot`

业务模块统一结构：
```
com.enjoyiot.module.<模块名>
├── controller/admin/<实体>    — REST 控制器（@Validated, @PreAuthorize, @ApiAccessLog）
│   └── vo/                    — 请求/响应 VO（XxxSaveReqVO, XxxUpdateReqVO, XxxPageReqVO, XxxRespVO）
├── dal/dataobject/            — 实体类（DO，平铺不分子目录）
├── dal/mysql/                 — MyBatis-Plus Mapper（继承 BaseMapperX，不是 MP 的 BaseMapper）
├── service/<实体>/            — Service 接口 + ServiceImpl
├── convert/                   — 转换器（MapStruct 或 BeanUtils）
└── api/                       — Feign 内部 API 实现
```

框架组件：`com.enjoyiot.framework.<组件>.{core,config}`，通过 Spring 自动配置加载。

## 如何新增一个功能

以后端新增「设备标签」为例，完整链路：

### 1. 定义 API 接口（module-eiot-api）
```
// 在 module-eiot-api 的 api/ 目录下定义 Feign 接口
public interface DeviceTagApi {
    // 供其他模块远程调用
}
```

### 2. 定义 DO（module-eiot-biz）
```
// dal/dataobject/DeviceTagDO.java
@TableName("device_tag")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceTagDO extends BaseDO {  // 必须继承 BaseDO（含 @TableLogic 软删除 + 审计字段）
    private String name;
    private Long deviceId;
}
```

### 3. 定义 Mapper（module-eiot-biz）
```
// dal/mysql/DeviceTagMapper.java
@Mapper
public interface DeviceTagMapper extends BaseMapperX<DeviceTagDO> {  // 不是 MP 的 BaseMapper
    // BaseMapperX 提供 selectPage(PageParam) → PageResult<T>
}
```

### 4. 定义 VO（module-eiot-biz）
```
// controller/admin/devicetag/vo/DeviceTagSaveReqVO.java  — 新增/编辑
// controller/admin/devicetag/vo/DeviceTagPageReqVO.java  — 分页查询
// controller/admin/devicetag/vo/DeviceTagRespVO.java     — 响应
```

### 5. 定义 Service（module-eiot-biz）
```
// service/devicetag/DeviceTagService.java       — 接口
// service/devicetag/DeviceTagServiceImpl.java   — 实现
```

### 6. 定义 Controller（module-eiot-biz）
```
@Tag(name = "设备标签")
@RestController
@RequestMapping("/eiot/device-tag")
@Validated
public class DeviceTagController {

    @Resource  // 使用 @Resource，不是 @Autowired
    private DeviceTagService deviceTagService;

    @PostMapping("/create")
    @Operation(summary = "创建设备标签")
    @PreAuthorize("@ss.hasPermission('eiot:device-tag:create')")  // 权限格式：模块:实体:操作
    @ApiAccessLog(operateType = OperateTypeEnum.CREATE)           // 审计日志
    public CommonResult<Long> createDeviceTag(@Valid @RequestBody DeviceTagSaveReqVO reqVO) {
        return CommonResult.success(deviceTagService.createDeviceTag(reqVO));
    }

    @GetMapping("/page")
    @Operation(summary = "获取设备标签分页")
    @PreAuthorize("@ss.hasPermission('eiot:device-tag:query')")
    public CommonResult<PageResult<DeviceTagRespVO>> getDeviceTagPage(@Valid DeviceTagPageReqVO pageReqVO) {
        return CommonResult.success(deviceTagService.getDeviceTagPage(pageReqVO));
    }
}
```

### 7. 跨模块调用约定
- `-api` 模块定义接口 + DTO，`-biz` 模块实现
- 其他模块只依赖 `-api`，不依赖 `-biz`
- 内部调用通过 Feign 接口（`-api` 中定义）

## 核心约定

- **Jakarta EE**：使用 `jakarta.validation`、`jakarta.annotation.Resource`，**禁止导入 `javax.*`**
- **依赖注入**：统一使用 `@Resource`（不要用 `@Autowired`）
- **DO 基类**：所有 DO 必须继承 `BaseDO`（提供 `@TableLogic` 软删除 + 自动填充审计字段）
- **Mapper 基类**：继承 `BaseMapperX`（不是 MyBatis-Plus 的 `BaseMapper`），提供 `selectPage(PageParam)` → `PageResult<T>`
- **Bean 拷贝**：使用 `com.enjoyiot.framework.common.util.object.BeanUtils`（不是 Spring 的 BeanUtils）
- **VO 命名**：`XxxSaveReqVO`（新增/编辑）、`XxxUpdateReqVO`（更新）、`XxxPageReqVO`（分页）、`XxxRespVO`（响应）
- **返回格式**：统一 `CommonResult.success(data)`，前端期望 `{ code: 0, data: ..., msg: ... }`
- **权限注解**：`@PreAuthorize("@ss.hasPermission('模块:实体:操作')")`，格式 `eiot:device:create`
- **审计日志**：Controller 方法上加 `@ApiAccessLog`（来自 `com.enjoyiot.framework.apilog`）
- **API 文档**：Controller 类加 `@Tag`，方法加 `@Operation`
- **多租户**：内置通过 `spring-boot-starter-biz-tenant` 自动注入 `tenant_id` 到查询，启用条件 `platform.tenant.enable: true`。**写原生 SQL 或 `@DS` 时需注意透传租户 ID**
- **可插拔时序库**：`iot-temporal-service` 接口，通过 Maven 依赖选择实现（RDB/TDengine/ES/IoTDB/KaiwuDB/TimescaleDB）
- **协议组件**：独立可部署的 Spring 组件，位于 `iot-components/`
- **事件驱动**：多种消息生产者（Spring Event / RocketMQ / Kafka / Akka），由 `eiot.message.producer-type` 控制
- **Lombok 注解**：`@Data` + `@EqualsAndHashCode(callSuper = true)` + `@Builder` + `@NoArgsConstructor` + `@AllArgsConstructor`
- **编译参数**：`-parameters`（配合 Lombok + MapStruct 参数名发现）

## 常见陷阱（Gotchas）

- **DO 不继承 BaseDO**：不继承会导致软删除（`@TableLogic`）和审计字段（`createTime`、`updateTime`、`creator`、`updater`）失效
- **导入 javax.* 而非 jakarta.***：项目是 Spring Boot 3 / Jakarta EE，`javax.validation` 会编译失败
- **用 MyBatis-Plus 的 BaseMapper 而非 BaseMapperX**：`BaseMapperX` 提供了 `selectPage(PageParam)` 等方法，分页参数类型不同
- **用 Spring 的 BeanUtils 而非项目自定义的**：`com.enjoyiot.framework.common.util.object.BeanUtils` 对 null 处理更友好
- **直接写原生 SQL 绕过租户过滤器**：`tenant_id` 由框架自动注入，原生 SQL 需要手动添加
- **`@DS` 注解误用**：多数据源切换时需确保租户隔离逻辑仍然生效
- **`-api` / `-biz` 循环依赖**：DTO 定义在 `-api`，`-biz` 实现并依赖 `-api`，其他模块只依赖 `-api`
- **权限注解格式错误**：`@ss.hasPermission('module:entity:action')` 三层结构，不一致会导致权限校验失败

## 构建与运行

```bash
# 构建
mvn clean install -DskipTests

# 运行（默认 profile: eiot，端口 48080）
java -jar server/target/server.jar --spring.profiles.active=eiot

# 本地开发（创建自己的 application-local.yaml）
java -jar server/target/server.jar --spring.profiles.active=local
```

**前置条件**：MySQL 8.x、Redis。用 `_sql/mysql/system.sql` 初始化数据库。

**API 文档**：`http://localhost:48080/doc.html`（Knife4j）或 `/swagger-ui`

## 配置

- `server/src/main/resources/application.yaml` — 核心配置
- `application-eiot.yaml` — 激活 profile：数据库、Redis、MQ、时序库、微信、社交登录
- `application-my.yaml` — 备选本地配置
- `logback-spring.xml` — 日志配置（含 SkyWalking GRPC 集成）

## 调试

- 创建 `application-local.yaml` 覆盖本地配置（数据库连接、端口等）
- 调整日志级别：修改 `logback-spring.xml` 中的 `root` 级别，或通过 `application-local.yaml` 设置 `logging.level.com.enjoyiot=DEBUG`
- 远程调试：`java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar server/target/server.jar`
- Druid 监控：`http://localhost:48080/druid/`
- Knife4j 在线调试：`http://localhost:48080/doc.html` 可在线发送 API 请求

## 测试

- 测试框架：JUnit 5 + Mockito + Spring Boot Test
- 测试目录：`src/test/java/`（与 main 同包结构）
- 当前测试覆盖率：无（待补充）

## 重要文档

- `docs/MQTT协议-官方参考.md` — MQTT Topic 规范和认证流程
- `docs/设备影子数据流转说明.md` — 设备影子数据流
- `_docs/fullblood-thing-model-old-vs-new-chain-2026-04-23.md` — 物模型迁移指南（`_docs/` 存放内部迁移文档，`docs/` 存放对外协议文档）