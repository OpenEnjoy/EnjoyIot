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

/**
 * 设备行为
 *
 * @author sjg
 */
public interface IDeviceAction {

    /**
     * 获取唯一标识id
     *
     * @return id
     */
    String getId();

    /**
     * 设置id
     *
     * @param id id
     */
    void setId(String id);

    /**
     * 获取类型
     *
     * @return ActionType
     */
    ActionType getType();

    /**
     * 设备类型
     *
     * @param type type
     */
    void setType(ActionType type);

    /**
     * 获取产品key
     *
     * @return ProductKey
     */
    String getProductKey();

    /**
     * 设置产品key
     *
     * @param productKey pk
     */
    void setProductKey(String productKey);

    /**
     * 获取设备DN
     *
     * @return DN
     */
    String getDeviceName();

    /**
     * 设置设备DN
     *
     * @param deviceName dn
     */
    void setDeviceName(String deviceName);

    /**
     * 获取时间
     *
     * @return timestamp
     */
    Long getTime();

    /**
     * 设置时间
     *
     * @param time timestamp
     */
    void setTime(Long time);
}
