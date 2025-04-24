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
package com.enjoyiot.eiot.ruleengine.action.device;

import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.eiot.ruleengine.action.Action;
import com.enjoyiot.eiot.ruleengine.action.device.DeviceActionService.Service;
import com.enjoyiot.framework.common.util.json.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeviceAction implements Action<Service> {

    public static final String TYPE = "device";

    private String type;

    private List<Service> services;

    private DeviceActionService deviceActionService;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public List<String> execute(ThingModelMessage msg) {
        List<String> results = new ArrayList<>();
        for (Service service : services) {
            deviceActionService.invoke(service);
            results.add(JsonUtils.toJsonString(service));
        }
        return results;
    }

}
