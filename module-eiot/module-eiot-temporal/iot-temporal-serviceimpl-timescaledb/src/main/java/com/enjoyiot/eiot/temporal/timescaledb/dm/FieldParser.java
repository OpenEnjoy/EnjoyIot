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
package com.enjoyiot.eiot.temporal.timescaledb.dm;

import com.enjoyiot.module.eiot.api.thingmodel.dto.ThingModel;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FieldParser {

    private static final Map<String, String> TYPE_MAPPING = Collections.unmodifiableMap(new HashMap<String, String>() {{
        put("int", "INTEGER");
        put("int32", "INTEGER");
        put("long", "BIGINT");
        put("int64", "BIGINT");
        put("float", "DOUBLE PRECISION");
        put("double", "DOUBLE PRECISION");
        put("bool", "BOOLEAN");
        put("enum", "SMALLINT");
        put("string", "VARCHAR");
        put("text", "VARCHAR");
        put("date", "VARCHAR");
        put("datetime", "VARCHAR");
        put("array", "VARCHAR");
        put("object", "VARCHAR");
        put("position", "VARCHAR");
    }});

    public static PgField parse(ThingModel.Property property) {
        String fieldName = property.getIdentifier().toLowerCase();
        ThingModel.DataType dataType = property.getDataType();
        String fieldType = TYPE_MAPPING.get(dataType.normalizedType());
        int length = -1;
        Object rawLength = dataType.getSpecMap().get("length");
        if (rawLength != null) {
            length = Integer.parseInt(String.valueOf(rawLength));
        }
        if ("VARCHAR".equals(fieldType) && length < 1) {
            length = 1024;
        }
        return new PgField(fieldName, fieldType, length);
    }

    public static List<PgField> parse(ThingModel thingModel) {
        return thingModel.getModel().getProperties().stream().map(FieldParser::parse).collect(Collectors.toList());
    }

}
