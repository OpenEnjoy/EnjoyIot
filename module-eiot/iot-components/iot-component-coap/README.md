# 执行sql脚本(若UDP组件不存在)
```plain
INSERT INTO `enjoyiot`.`eiot_component` (`id`, `name`, `type`, `config`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (6, '内置官方 COAP 组件', 'coap', '{\n     \"port\": 5683}', 1, '', NULL, '2025-02-19 01:01:54', '1', '2025-06-23 11:59:55', b'0');

```

执行完成之后，组件中心就会出现UDP类型，点击开启组件即可

![](https://cdn.nlark.com/yuque/0/2025/png/40917953/1750746948362-b77e59df-9416-4ca2-8d4c-67f1571763cb.png)



## 配置参数
Port: 服务端口号



## 新增产品
![](https://cdn.nlark.com/yuque/0/2025/png/40917953/1750747062005-a6f37c4f-fd9c-42cb-a158-35d2a7f3815f.png)



# 配置物模型
![](https://cdn.nlark.com/yuque/0/2025/png/40917953/1750747292352-01058b7e-4fea-450a-8b57-d788268fe0b5.png)



# 新增设备
![](https://cdn.nlark.com/yuque/0/2025/png/40917953/1750747799149-76095a0e-7e5a-4c27-bb81-3bdaf2352d75.png)

# 调试代码，测试上报
![](https://cdn.nlark.com/yuque/0/2025/png/40917953/1750747504198-a69499dd-801c-4276-865a-f610418182c9.png)



调试的时候 把对应的参数替换成实际参数，该测试样例是针对该样例的物模型发消息

![](https://cdn.nlark.com/yuque/0/2025/png/40917953/1750747632730-c20ca81c-aaee-4cb8-84d1-dbbfd9b1c340.png)

返回成功

![](https://cdn.nlark.com/yuque/0/2025/png/40917953/1750747852660-af09b5a3-ad57-4c6c-b7e1-3e2dfdecf5d3.png)



平台设备显示在线

![](https://cdn.nlark.com/yuque/0/2025/png/40917953/1750747879521-4f91e0ae-1f3f-4ea1-a058-34e4488ae448.png)

设备日志显示告警事件与属性上报

![](https://cdn.nlark.com/yuque/0/2025/png/40917953/1750747921611-7adac3ac-9fe3-4445-9cb9-7259c93b16ef.png)

