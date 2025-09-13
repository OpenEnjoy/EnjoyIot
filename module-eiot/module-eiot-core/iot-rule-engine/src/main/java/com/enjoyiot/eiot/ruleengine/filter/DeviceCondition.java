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


import com.enjoyiot.eiot.ruleengine.expression.Expression;
import com.enjoyiot.module.eiot.api.device.DeviceApi;
import com.enjoyiot.module.eiot.api.device.dto.DeviceInfo;
import com.enjoyiot.module.eiot.api.device.dto.DevicePropertyCache;
import lombok.Data;

import java.util.Map;

@Data
public class DeviceCondition {

    private String device;

    private String type;

    private String identifier;

    private String value;

    private String comparator;

    private DeviceApi deviceApi;

    @Override
    public DeviceCondition clone() {
        DeviceCondition con = new DeviceCondition();
        con.setDevice(device);
        con.setType(type);
        con.setIdentifier(identifier);
        con.setValue(value);
        con.setComparator(comparator);
        con.setDeviceApi(deviceApi);
        return con;
    }

    public boolean matches() {
        DeviceInfo deviceInfo;
        String[] pkDn = device.split("/");
        if (pkDn.length < 2) {
            //用deviceId取
            deviceInfo = deviceApi.getDeviceInfoFromCache(Long.parseLong(device));
        } else {
            //用pk/dn取
            deviceInfo = deviceApi.getDeviceByPkDnByCache(pkDn[0], pkDn[1]);
        }
        if (deviceInfo == null) {
            return false;
        }
        Object left = null;
        if ("property".equals(type)) {
            Map<String, ?> properties = deviceApi.getPropertiesFromCache(deviceInfo.getId());
            DevicePropertyCache propertyCache = (DevicePropertyCache) properties.get(identifier);
            if (propertyCache == null) {
                return false;
            }
            left = propertyCache.getValue();
        } else if ("state".equals(type)) {

            left = deviceInfo.isOnline();
        } else if ("tag".equals(type)) {
            //取设备标签判断
            Map<String, DeviceInfo.Tag> tags = deviceInfo.getTag();
            if (tags != null) {
                DeviceInfo.Tag tag = tags.get(identifier);
                if (tag != null) {
                    //设备标签值
                    left = tag.getValue();
                }
            }
        }
        return Expression.eval(comparator, String.valueOf(left), value);
    }
}
