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
package com.enjoyiot.eiot.temporal.rdb.dm;


import com.enjoyiot.module.eiot.api.thingmodel.dto.ThingModel;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.enjoyiot.eiot.common.enums.ErrorCodeConstants.FILED_DEFINE;
import static com.enjoyiot.framework.common.exception.util.ServiceExceptionUtil.exception;

public class FieldParser {

    private static final Map<String, String> TYPE_MAPPING = Collections.unmodifiableMap(new HashMap<String, String>() {{
        put("int32", "BIGINT");
        put("float", "DOUBLE");
        put("double", "DOUBLE");
        put("bool", "TINYINT");
        put("enum", "INT");
        put("text", "VARCHAR");
        put("date", "VARCHAR");
        put("position", "VARCHAR");
    }});

    public static RdbField parse(ThingModel.Property property) {
        String fieldName = property.getIdentifier().toLowerCase();
        ThingModel.DataType dataType = property.getDataType();
        String type = dataType.getType();

        String fType = TYPE_MAPPING.get(type);
        Object specs = dataType.getSpecs();
        int len = -1;
        if (specs instanceof Map) {
            Object objLen = ((Map<?, ?>) specs).get("length");
            if (objLen != null) {
                try {
                    len = Integer.parseInt(objLen.toString());
                } catch (Exception e) {
                    throw exception(FILED_DEFINE, fieldName + " 长度错误");
                }
            }
            if ("VARCHAR".equals(fType) && len < 1) {
                len = 255;
            }
        }

        if ("VARCHAR".equals(fType) && len < 0) {
            len = 255;
        }

        return new RdbField(fieldName, fType, len);
    }

    public static List<RdbField> parse(ThingModel thingModel) {
        return thingModel.getModel().getProperties().stream().map(FieldParser::parse).collect(Collectors.toList());
    }

    public static String getFieldDefine(RdbField field) {
        String name = field.getName().replace("-", "_");
        return name + " " + (field.getLength() > 0 ?
                String.format("%s(%d)", field.getType(), field.getLength())
                : field.getType());
    }
}
