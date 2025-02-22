
/*
 *
 *  * | Licensed 未经许可不能去掉「Enjoy-iot」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com | Tel: 19918996474
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2025] [Enjoy-iot] | Tel: 19918996474
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
package com.enjoyiot.eiot.component.core;


import com.enjoyiot.eiot.common.thing.ThingModelMessage;

/**
 * 设备组件接口
 */
public interface Component {

    /**
     * 组件实例id
     */
    String getId();

    /**
     * 获取组件类型
     */
    String getType();

    /**
     * 组件名
     */
    String getName();

    /**
     * 发送消息
     */
    void sendMessage(ThingModelMessage message);

    /**
     * 接收消息
     */
    void onMessage(ThingModelMessage message);
}