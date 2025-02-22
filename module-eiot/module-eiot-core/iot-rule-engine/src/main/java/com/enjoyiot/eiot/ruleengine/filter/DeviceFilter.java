/*
 *
 *  * | Licensed 未经许可不能去掉「Enjoy-iot」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2025] [Enjoy-iot]
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
package com.enjoyiot.eiot.ruleengine.filter;


import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.module.eiot.api.device.DeviceApi;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Data
public class DeviceFilter implements Filter<DeviceCondition> {

    public static String TYPE = "device";

    private String type;

    private String pk;

    private String dn;

    private List<DeviceCondition> conditions;

    private DeviceApi deviceApi;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void init() {
    }

    @Override
    public boolean execute(ThingModelMessage msg) {
        for (DeviceCondition condition : getConditions()) {
            DeviceCondition con = condition.clone();
            //未指定device，使用消息中的deviceId
            if (StringUtils.isBlank(con.getDevice()) || "null/#".equals(con.getDevice())) {
                con.setDevice(String.valueOf(msg.getDeviceId()));
            }

            con.setDeviceApi(deviceApi);
            if (!con.matches()) {
                return false;
            }
        }
        return true;
    }

}
