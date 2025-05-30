# 20250530 提交内容
## 1. 修复物模型消息推送时，messageID 为空的问题；
```java
if (!StringUtils.hasText(action.getId())) {
message.setId(IdUtil.fastSimpleUUID());
}else {
message.setId(action.getId());
}
if (ObjectUtils.isEmpty(action.getTime())) {
message.setTime(System.currentTimeMillis());
}else {
message.setTime(action.getTime());
}
```


## 2. 增加UDP组件
### 2.1 UDP组件代码简单说明
#### 2.1.1 结构
和其他协议的组件一样：
UdpServer 服务启动入口；
UdpComponent 组件 判断数据类型以及格式，并调用对应的解析方法；
UdpVerticle 启动方法
UdpPacketDispatcher 基于策略模式，
将所有 PacketHandler 写成静态内部类，并用 list 固定顺序(按照性能优先级降序)
#### 2.2.2  接入方式
数据库执行sql

```mysql
INSERT INTO `eiot_component`( `name`, `type`, `config`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES ('内置官方 UDP 组件', 'udp', '{\n     \"port\": 7101,\n     \"host\": \"0.0.0.0\"\n}', 1, '', NULL, '2025-02-19 01:01:54', '1', '2025-05-28 10:54:00', b'0');
```


