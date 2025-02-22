/*
 *
 *  * | Licensed 未经许可不能去掉「EnjoyIot」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2024] [EnjoyIot]
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * /
 */

package com.enjoyiot.eiot.component.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 设备物行为类型
 *
 * @author sjg
 */
@Getter
@AllArgsConstructor
public enum ActionType {

    // 认证
    AUTH("auth"),

    //注册
    REGISTER("register"),
    //子注册
    SUB_REGISTER("sub_register"),
    // 心跳ping
    PING("ping"),

    //设备拓扑更新
    TOPOLOGY("topology"),

    //在离线状态变更
    STATE_CHANGE("state_change"),
    //属性上报
    PROPERTY_REPORT("property_report"),
    //事件上报
    EVENT_REPORT("event_report"),
    //服务回复
    SERVICE_REPLY("service_reply"),

    //属性设置
    PROPERTY_SET("property_set"),
    //属性获取
    PROPERTY_GET("property_get"),
    //服务调用
    SERVICE_INVOKE("service_invoke"),
    //配置
    CONFIG("config"),
    TOPO_CHANGE("topo_change"),

    //OTA
    OTA("ota");

    private final String type;

}
