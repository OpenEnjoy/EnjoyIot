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
import com.enjoyiot.eiot.common.thing.ThingService;
import com.enjoyiot.eiot.common.utils.UniqueIdUtil;

import com.enjoyiot.module.eiot.api.device.DeviceApi;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DeviceActionService {

    @Resource
    private DeviceApi deviceApi;

    public String invoke(Service service) {
        String[] pkDn = service.getDevice().split("/");
        ThingService<Map<String, Object>> thingService = new ThingService<>();
        thingService.setMid(UniqueIdUtil.newRequestId());
        thingService.setProductKey(pkDn[0]);
        thingService.setDn(pkDn[1]);
        thingService.setType(service.getType());
        thingService.setIdentifier(service.getIdentifier());
        thingService.setParams(service.parseInputData());
        deviceApi.invoke(thingService);
        return thingService.getMid();
    }

    @Data
    public static class Service {

        private String device;

        private String identifier;

        private String type;

        public String getType() {
            //identifier为set固定为属性设置，其它为服务调用
            if (ThingModelMessage.ID_PROPERTY_SET.equals(identifier) ||
                    ThingModelMessage.ID_PROPERTY_GET.equals(identifier)) {
                return ThingModelMessage.TYPE_PROPERTY;
            }
            return ThingModelMessage.TYPE_SERVICE;
        }

        private List<Parameter> inputData;

        public Map<String, Object> parseInputData() {
            Map<String, Object> data = new HashMap<>();
            for (Parameter p : inputData) {
                data.put(p.getIdentifier(), p.getValue());
            }
            return data;
        }

        @Data
        public static class Parameter {
            private String identifier;
            private Object value;
        }
    }

}
