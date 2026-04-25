/*
 *
 *  * | Licensed 未经许可不能去掉「Enjoy-iot」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: dreamswang2020@foxmail.com
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
package com.enjoyiot.eiot.temporal.kw.dm;

import com.enjoyiot.module.eiot.api.thingmodel.dto.ThingModel;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FieldParser {

    private static final Map<String, String> TYPE_MAPPING = Collections.unmodifiableMap(new HashMap<String, String>() {{
        put("int", "INT4");
        put("int32", "INT8");
        put("long", "INT8");
        put("int64", "INT8");
        put("float", "FLOAT8");
        put("double", "FLOAT8");
        put("bool", "INT2");
        put("enum", "INT4");
        put("string", "NCHAR");
        put("text", "NCHAR");
        put("date", "NCHAR");
        put("datetime", "NCHAR");
        put("array", "NCHAR");
        put("object", "NCHAR");
        put("position", "NCHAR");
    }});

    public static KwField parse(ThingModel.Property property) {
        String fieldName = property.getIdentifier().toLowerCase();
        String fieldType = TYPE_MAPPING.get(property.getDataType().normalizedType());
        int length = -1;
        Object rawLength = property.getDataType().getSpecMap().get("length");
        if (rawLength != null) {
            length = Integer.parseInt(String.valueOf(rawLength));
        }
        if ("NCHAR".equals(fieldType) && length < 1) {
            length = 1024;
        }
        return new KwField(fieldName, fieldType, length);
    }

    public static List<KwField> parse(ThingModel thingModel) {
        return thingModel.getModel().getProperties().stream().map(FieldParser::parse).collect(Collectors.toList());
    }

    public static List<KwField> parse(List rows) {
        return (List<KwField>) rows.stream().map((rowValue) -> {
            List row = (List) rowValue;
            String type = row.get(1).toString().toUpperCase();
            return new KwField(
                    row.get(0).toString(),
                    type,
                    type.endsWith("CHAR") ? Integer.parseInt(row.get(2).toString()) : -1);
        }).collect(Collectors.toList());
    }

    public static String getFieldDefine(KwField field) {
        return field.getName() + " " + (field.getLength() > 0
                ? String.format("%s(%d)", field.getType(), field.getLength())
                : field.getType());
    }

    public static String getFieldTagDefine(KwField field) {
        return field.getName();
    }

    public static String getAlterFieldDefine(KwField field) {
        return field.getName() + " TYPE " + (field.getLength() > 0
                ? String.format("%s(%d)", field.getType(), field.getLength())
                : field.getType());
    }
}
