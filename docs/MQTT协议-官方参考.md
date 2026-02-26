> Topic 中的
>
> **/c/** **代表 to client ,由服务器发给设备的, 设备端需要订阅**
>
> **/s/** **代表 to server, 由设备发给服务器的**
>
> {productKey} 替换成设备的productKey
>
> {deviceName} 替换成设备的deviceName

## 网关连接和注册

通过 mqtt 连接： mqttClientId: productKey_deviceName_model

mqttUserName: deviceName

mqttPassword: md5(产品密钥+mqttClientId) 

**密钥和clientId直接拼成字符串后求md5**

  

产品密钥： 见产品详情页面产品密钥

说明：

productKey: 在平台创建的产品 productKey

deviceName: 设备唯一标识，如 MAC

model: 设备型号

网关连接 mqtt 成功后，云端会对网关进行注册

连接成功后网关订阅 topic: /sys/{productKey}/{deviceName}/c/#

云端验证订阅成功后，会将网关置为在线。

## 子设备注册

*由网关上报*

### 请求

Topic：/sys/{网关 productKey}/{网关 deviceName}/s/register

payload 参数：

| 参数   | 类型   | 说明                                                         |
| ------ | ------ | ------------------------------------------------------------ |
| id     | String | 消息 ID                                                      |
| method | String | thing.lifetime.register                                      |
| params | JSON   | 内容固定为： {  "productKey":子设备 PK,  "deviceName":子设备 mac,  "model":子设备型号 } |

### 示例

```JSON
topic:/sys/hbtgIA0SuVw9lxjB/AA:BB:CC:DD:22/s/register,
payload: {
        "id": "9f981472-7681-4392-b27e-9dc53fe0b020",
        "method": "thing.lifetime.register",
        "params": {
                "productKey": "Rf4QSjbm65X45753",
                "deviceName": "ABC12300002",
                "model": "S01"
        }
}
```

### 响应

Topic：/sys/{网关 productKey}/{网关 deviceName}/c/register_reply

payload 参数：

| 参数   | 类型   | 说明                                                         |
| ------ | ------ | ------------------------------------------------------------ |
| id     | String | 消息 ID                                                      |
| method | String | thing.lifetime.register_reply                                |
| code   | int    | 响应码，0：成功，x：其它错误                                 |
| data   | JSON   | 注册成功的设备信息： {  "productKey":设备 PK,  "deviceName":设 mac,  "model":设备型号 } |

子设备注册成功后，订阅 topic: /sys/{productKey}/{deviceName}/c/# 设备将变为在线，反之取消订阅该 topic，设备将变为离线。

## 子设备注销(商业版本里用-通知网关拓扑关系变化)

*网关接收*

### 请求

Topic：/sys/{网关 productKey}/{网关 deviceName}/c/deregister

payload 参数：

| 参数   | 类型   | 说明                                                         |
| ------ | ------ | ------------------------------------------------------------ |
| id     | String | 消息 ID                                                      |
| method | String | thing.lifetime.deregister                                    |
| params | JSON   | 内容固定为： {  "productKey":设备 PK,  "deviceName":设 mac } |

### 响应

Topic：/sys/{网关 productKey}/{网关 deviceName}/s/deregister_reply

payload 参数：

| 参数   | 类型   | 说明                            |
| ------ | ------ | ------------------------------- |
| id     | String | 消息 ID                         |
| method | String | thing.lifetime.deregister_reply |
| code   | int    | 响应码，0：成功，x：其它错误    |

云端先对子设备解绑然后网关再注销，方能在其它网关注册。

## 服务调用

### 请求

Topic：/sys/{productKey}/{deviceName}/c/service/服务名

payload 参数：

| 参数   | 类型   | 说明                                                         |
| ------ | ------ | ------------------------------------------------------------ |
| id     | String | 消息 ID                                                      |
| method | String | thing.service.服务名                                         |
| params | JSON   | 服务输入参数，格式:{"参数 1":值 1} 具体见产品物模型中服务的定义 |

### 响应

Topic：/sys/{productKey}/{deviceName}/s/service/服务名_reply

payload 参数：

| 参数   | 类型   | 说明                         |
| ------ | ------ | ---------------------------- |
| id     | String | 消息 ID                      |
| method | String | thing.service.服务名_reply   |
| code   | int    | 响应码，0：成功，x：其它错误 |
| data   | JSON   | 服务调用结果数据             |

## 事件上报

### 请求

Topic：/sys/{productKey}/{deviceName}/s/event/事件名

payload 参数：

| 参数   | 类型   | 说明                                                         |
| ------ | ------ | ------------------------------------------------------------ |
| id     | String | 消息 ID                                                      |
| method | String | thing.event.事件名                                           |
| params | JSON   | 事件上报参数， 格式:{"参数 1":值 1} 具体见产品物模型中事件的定义 |

```json
payload: {
        "id": "2a72ddb8-7a84-4a57-b745-97698716fb9e",
        "method": "thing.event.事件名",
        "params": {
                "volt": 26
        },
        "version":"1.0.0"
}
```

### 响应

Topic：/sys/{productKey}/{deviceName}/c/event/事件名_reply

payload 参数：

| 参数   | 类型   | 说明                         |
| ------ | ------ | ---------------------------- |
| id     | String | 消息 ID                      |
| method | String | thing.event.事件名_reply     |
| code   | int    | 响应码，0：成功，x：其它错误 |

## 属性设置

### 请求

Topic：/sys/{productKey}/{deviceName}/c/service/property/set

payload 参数：

| 参数   | 类型   | 说明                                                         |
| ------ | ------ | ------------------------------------------------------------ |
| id     | String | 消息 ID                                                      |
| method | String | thing.service.property.set                                   |
| params | JSON   | 属性参数， 格式:{"参数 1":值 1} 具体见产品物模型中属性的定义 |

### 响应

Topic：/sys/{productKey}/{deviceName}/s/service/property/set_reply

payload 参数：

| 参数   | 类型   | 说明                             |
| ------ | ------ | -------------------------------- |
| id     | String | 消息 ID                          |
| method | String | thing.service.property.set_reply |
| code   | int    | 响应码，0：成功，x：其它错误     |

## 属性获取

### 请求

Topic：/sys/{productKey}/{deviceName}/c/service/property/get

payload 参数：

| 参数   | 类型   | 说明                                    |
| ------ | ------ | --------------------------------------- |
| id     | String | 消息 ID                                 |
| method | String | thing.service.property.get              |
| params | JSON   | 数组，要获取的属性列表 ["a","b","c"...] |

### 响应

Topic：/sys/{productKey}/{deviceName}/s/service/property/get_reply

payload 参数：

| 参数   | 类型   | 说明                             |
| ------ | ------ | -------------------------------- |
| id     | String | 消息 ID                          |
| method | String | thing.service.property.get_reply |
| code   | int    | 响应码，0：成功，x：其它错误     |

## 属性上报

### 请求

Topic：/sys/{productKey}/{deviceName}/s/event/property/post

payload 参数：

| 参数    | 类型   | 说明                                                         |
| ------- | ------ | ------------------------------------------------------------ |
| id      | String | 消息 ID                                                      |
| version | String | 消息版本,暂时未使用                                          |
| method  | String | thing.event.property.post                                    |
| params  | JSON   | 属性参数， 格式:{"参数 1":值 1} 具体见产品物模型中属性的定义 |

### 示例

```JSON
topic:/sys/Rf4QSjbm65X45753/TEST_SW_000005/s/event/property/post,
payload: {
        "id": "2a72ddb8-7a84-4a57-b745-97698716fb9e",
        "method": "thing.event.property.post",
        "params": {
                "volt": 26
        },
        "version":"1.0.0"
}
```

### 响应

Topic：/sys/{productKey}/{deviceName}/c/event/property/post_reply

payload 参数：

| 参数   | 类型   | 说明                            |
| ------ | ------ | ------------------------------- |
| id     | String | 消息 ID                         |
| method | String | thing.event.property.post_reply |
| code   | int    | 响应码，0：成功，x：其它错误    |

## 设备获取配置

### 请求

Topic：/sys/{productKey}/{deviceName}/s/config/get

payload 参数：

| 参数   | 类型   | 说明             |
| ------ | ------ | ---------------- |
| id     | String | 消息 ID          |
| method | String | thing.config.get |

### 响应

Topic：/sys/{productKey}/{deviceName}/c/config/get_reply

payload 参数：

| 参数   | 类型   | 说明                         |
| ------ | ------ | ---------------------------- |
| id     | String | 消息 ID                      |
| method | String | thing.config.get_reply       |
| code   | int    | 响应码，0：成功，x：其它错误 |
| data   | JSON   | json 格式设备配置内容        |

## 设备配置下发

### 请求

Topic：/sys/{productKey}/{deviceName}/c/config/set

payload 参数：

| 参数   | 类型   | 说明                  |
| ------ | ------ | --------------------- |
| id     | String | 消息 ID               |
| method | String | thing.config.set      |
| params | JSON   | json 格式设备配置内容 |

### 响应

Topic：/sys/{productKey}/{deviceName}/s/config/set_reply

payload 参数：

| 参数   | 类型   | 说明                         |
| ------ | ------ | ---------------------------- |
| id     | String | 消息 ID                      |
| method | String | thing.config.set_reply       |
| code   | int    | 响应码，0：成功，x：其它错误 |

## 获取设备的拓扑关系

网关类型的设备，可以通过该Topic获取该设备和子设备的拓扑关系。

### 请求

Topic：/sys/{productKey}/{deviceName}/s/topo/get

payload 参数：

| 参数   | 类型   | 说明           |
| ------ | ------ | -------------- |
| id     | String | 消息 ID        |
| method | String | thing.topo.get |

### 响应

Topic：/sys/{productKey}/{deviceName}/c/topo/get_reply

payload 参数：

| 参数   | 类型   | 说明                         |
| ------ | ------ | ---------------------------- |
| id     | String | 消息 ID                      |
| method | String | thing.topo.get_reply         |
| code   | int    | 响应码，0：成功，x：其它错误 |
| data   | JSON   | json 格式设备配置内容        |

### 示例

```JavaScript
{
  "id": "123",
  "code": 200,
  "method": "thing.topo.get_reply",
  "data": [
    {
      "deviceName": "deviceName1234",
      "productKey": "1234556554"
    }
  ]
}
```

## 通知网关拓扑关系变化

将拓扑关系变化通知网关。

网关订阅对应主体

### 请求

Topic：/sys/{productKey}/{deviceName}/c/topo/change

payload 参数：

| 参数   | 类型   | 说明              |
| ------ | ------ | ----------------- |
| id     | String | 消息 ID           |
| method | String | thing.topo.change |

### 示例

```JavaScript
{"id":"123",
"params":{
    "status":0,  //0-创建  1-删除 2-恢复禁用  8-禁用
   "subList":[{"productKey":"a1hRrzD****","deviceName":"abcd"}]
   }, 
  "method":"thing.topo.change"  
}
```

### 响应

Topic：/sys/{productKey}/{deviceName}/s/topo/change_reply

payload 参数：

| 参数   | 类型   | 说明                         |
| ------ | ------ | ---------------------------- |
| id     | String | 消息 ID                      |
| method | String | thing.topo.change_reply      |
| code   | int    | 响应码，0：成功，x：其它错误 |
| data   | JSON   | json 格式设备配置内容        |

## 网关主动注销子设备

服务器接收

### 请求

Topic：/sys/{网关 productKey}/{网关 deviceName}/s/deregister

payload 参数：

| 参数   | 类型   | 说明                                                         |
| ------ | ------ | ------------------------------------------------------------ |
| id     | String | 消息 ID                                                      |
| method | String | thing.lifetime.deregister                                    |
| params | JSON   | 内容固定为： {  "productKey":设备 PK,  "deviceName":设 mac } |

### 响应

Topic：/sys/{网关 productKey}/{网关 deviceName}/c/deregister_reply

payload 参数：

| 参数   | 类型   | 说明                            |
| ------ | ------ | ------------------------------- |
| id     | String | 消息 ID                         |
| method | String | thing.lifetime.deregister_reply |
| code   | int    | 响应码，0：成功，x：其它错误    |

## 属性批量上报

待实现

## OTA

### 平台下发OTA升级指令

topic: /ota/deivce/upgrade/{productKey}/{deviceName}

```JSON
{"id": "123","code": 200,"data": {"size": 93796291,"sign": "f8d85b250d4d787a9f483d89a974***","version": "10.0.1.9.20171112.1432","isDiff": 1,"url": "https://the_firmware_url","signMethod": "MD5","md5": "f8d85b250d4d787a9f48***","module": "MCU","extData":{"key1":"value1","key2":"value2","_package_udi":"{\"ota_notice\":\"升级底层摄像头驱动，解决视频图像模糊的问题。\"}"}}}
```

## 设备影子

设备影子是一个JSON文档，用于存储设备的当前状态信息（reported）和期望状态信息（desired）。设备影子支持版本控制，通过乐观锁机制防止并发冲突。

### 设备影子数据结构

```json
{
  "state": {
    "reported": {
      "temperature": 25,
      "humidity": 60
    },
    "desired": {
      "temperature": 28
    }
  },
  "metadata": {
    "reported": {
      "temperature": {
        "timestamp": 1234567890
      }
    }
  },
  "version": 1
}
```

### 获取设备影子

设备主动获取完整的设备影子信息。

#### 请求

Topic：/shadow/update/{productKey}/{deviceName}

payload 参数：

| 参数   | 类型   | 说明    |
| ------ | ------ | ------- |
| method | String | get     |

#### 示例

```json
{
  "method": "get"
}
```

#### 响应

Topic：/shadow/get/{productKey}/{deviceName}

payload 参数：

| 参数      | 类型   | 说明                                |
| --------- | ------ | ----------------------------------- |
| method    | String | reply                               |
| payload   | JSON   | 影子数据，包含 status、state 等信息 |
| version   | Long   | 当前影子版本号                      |
| timestamp | Long   | 时间戳（毫秒）                      |

#### 响应示例

```json
{
  "method": "reply",
  "payload": {
    "status": "success",
    "state": {
      "reported": {
        "temperature": 25,
        "humidity": 60
      },
      "desired": {
        "temperature": 28
      }
    },
    "metadata": {
      "reported": {
        "temperature": {
          "timestamp": 1234567890
        }
      }
    }
  },
  "version": 1,
  "timestamp": 1234567890123
}
```

### 更新设备影子（上报状态）

设备上报当前状态，更新 reported 部分。支持增量更新（合并属性）。

#### 请求

Topic：/shadow/update/{productKey}/{deviceName}

payload 参数：

| 参数    | 类型   | 说明                         |
| ------- | ------ | ---------------------------- |
| method  | String | update                       |
| state   | JSON   | 状态数据，包含 reported 字段 |
| version | Long   | 当前版本号                   |

#### 示例 1：增量更新属性

```json
{
  "method": "update",
  "state": {
    "reported": {
      "temperature": 26,
      "humidity": 65
    }
  },
  "version": 1
}
```

#### 示例 2：清空所有 reported 属性（reported："null"）

```json
{
  "method": "update",
  "state": {
    "reported": "null"
  },
  "version": 1
}
```

#### 示例 3：清空整个设备影子（特殊版本号：-1 表示清空整个影子）

```json
{
  "method": "update",
  "version": -1
}
```

#### 响应

Topic：/shadow/get/{productKey}/{deviceName}

payload 参数：

| 参数      | 类型   | 说明                                                |
| --------- | ------ | --------------------------------------------------- |
| method    | String | reply                                               |
| payload   | JSON   | 包含 status 和 version                              |
| timestamp | Long   | 时间戳（毫秒）                                      |

#### 成功响应示例

```json
{
  "method": "reply",
  "payload": {
    "status": "success",
    "version": 1
  },
  "timestamp": 1234567890123
}
```

#### 错误响应示例

```json
{
  "method": "reply",
  "payload": {
    "status": "error",
    "content": {
      "errorcode": "409",
      "errormessage": "版本冲突"
    }
  },
  "timestamp": 1234567890123
}
```

### 删除设备影子属性

删除 reported 中的指定属性或全部属性。

#### 请求

Topic：/shadow/update/{productKey}/{deviceName}

payload 参数：

| 参数    | 类型   | 说明                         |
| ------- | ------ | ---------------------------- |
| method  | String | delete                       |
| state   | JSON   | 状态数据，包含 reported 字段 |
| version | Long   | 当前版本号                   |

#### 示例 1：删除指定属性

将要删除的属性值设置为 "null"

```json
{
  "method": "delete",
  "state": {
    "reported": {
      "temperature": "null",
      "humidity": "null"
    }
  },
  "version": 2
}
```

#### 示例 2：删除所有 reported 属性

```json
{
  "method": "delete",
  "state": {
    "reported": "null"
  },
  "version": 2
}
```

#### 响应

Topic：/shadow/get/{productKey}/{deviceName}

响应格式与更新设备影子相同。

### 平台下发期望状态

平台向设备下发期望状态，更新 desired 部分。设备订阅该 Topic 接收期望状态变化。

#### 请求

Topic：/shadow/get/{productKey}/{deviceName}

payload 参数：

| 参数      | 类型   | 说明                    |
| --------- | ------ | ----------------------- |
| method    | String | control                 |
| payload   | JSON   | 包含 state.desired 字段 |
| version   | Long   | 版本号                  |
| timestamp | Long   | 时间戳（毫秒）          |

#### 示例

```json
{
  "method": "control",
  "payload": {
    "state": {
      "desired": {
        "temperature": 30,
        "mode": "cool"
      }
    }
  },
  "version": 3,
  "timestamp": 1234567890123
}
```

### 错误码说明

| 错误码 | 说明                                  |
| ------ | ------------------------------------- |
| 400    | 请求参数错误（缺少 method、state 等） |
| 401    | 影子数据缺少method信息                |
| 402    | 影子数据缺少state字段                 |
| 403    | 影子数据中version值不是数字           |
| 404    | 设备影子数据缺少reported字段          |
| 405    | 影子数据中reported属性字段为空        |
| 406    | 影子数据中method是无效的方法          |
| 407    | 影子内容为空                          |
| 408    | 影子数据中reported属性个数超过128个   |
| 409    | 影子版本冲突                          |
| 500    | 服务端处理异常                        |
