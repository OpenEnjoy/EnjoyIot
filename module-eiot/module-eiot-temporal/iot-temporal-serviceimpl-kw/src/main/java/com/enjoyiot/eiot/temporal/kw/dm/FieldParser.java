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

import static com.enjoyiot.eiot.common.enums.ErrorCodeConstants.FILED_DEFINE;
import static com.enjoyiot.framework.common.exception.util.ServiceExceptionUtil.exception;

public class FieldParser {

    /**
     * 物模型到td数据类型映射
     * | 原数据类型  | 默认宽度 | 最大宽度   | 支持转换的数据类型                                   | 说明                                                         |
     * | ----------- | -------- | ---------- | ---------------------------------------------------- | ------------------------------------------------------------ |
     * | TIMESTAMP   | -        | -          | TIMESTAMPTZ、INT8、FLOAT4、FLOAT8                    | 标签不支持该类型。TIMESTAMP 转 INT8 时，输出结果固定为毫秒精度的数字，即 13 位数字。TIMESTAMP 转 FLOAT4 时，输出结果只能保证约前 7 位有效数字的精度。TIMESTAMP 转 FLOAT8 时，输出结果只能保证约前 15-17 位有效数字的精度。 |
     * | TIMESTAMPTZ | -        | -          | TIMESTAMP、INT8、FLOAT4、FLOAT8                      | 标签不支持该类型。TIMESTAMPTZ 转 INT8 时，输出结果固定为毫秒精度的数字，即 13 位数字。TIMESTAMPTZ 转 FLOAT4 时，输出结果只能保证约前 7 位有效数字的精度。TIMESTAMPTZ 转 FLOAT8 时，输出结果只能保证约前 15-17 位有效数字的精度。 |
     * | INT2        | 2 字节   | -          | INT4、INT8、VARCHAR                                  | INT2 转 VARCHAR 时，VARCHAR 的最小宽度为 6。                 |
     * | INT4        | 4 字节   | -          | INT8、VARCHAR                                        | INT4 转 VARCHAR 时，VARCHAR 的最小宽度为 11。                |
     * | INT8        | 8 字节   | -          | VARCHAR                                              | INT8 转 VARCHAR 时，VARCHAR 的最小宽度为 20。                |
     * | FLOAT4      | 4 字节   | -          | FLOAT、VARCHAR                                       | REAL 转 VARCHAR 时，VARCHAR 的最小宽度为 30。                |
     * | FLOAT8      | 8 字节   | -          | VARCHAR                                              | FLOAT 转 VARCHAR 时，VARCHAR 的最小宽度为 30。               |
     * | CHAR        | 1 字节   | 1023       | NCHAR、VARCHAR、NVARCHAR                             | CHAR 转 NCHAR 或 NVARCHAR 时，NCHAR 或 NVARCHAR 的宽度不得小于原宽度的 ¼。 |
     * | VARCHAR     | 254 字节 | 65536 字节 | CHAR、NCHAR、NVARCHAR、INT2、INT4、INT8、REAL、FLOAT | VARCHAR 转 NCHAR 或 NVARCHAR 时，NCHAR 和 NVARCHAR 的宽度不得小于原宽度的 ¼。 |
     * | NCHAR       | 1 字符   | 254 字符   | CHAR、VARCHAR、NVARCHAR                              | NCHAR 转 CHAR 或 VARCHAR 时，CHAR 和 VARCHAR 的宽度不得小于原宽度的 4 倍。 |
     * | NVARCHAR    | 63 字符  | 16384 字符 | CHAR、VARCHAR、NCHAR                                 | NVARCHAR 转 CHAR 或 VARCHAR 时，CHAR 和 VARCHAR 的宽度不得小于原宽度的 4 倍。标签不支持该类型。 |
     */
    private static final Map<String, String> TYPE_MAPPING = Collections.unmodifiableMap(new HashMap<String, String>() {{
        put("int32", "INT8");
        put("float", "FLOAT8");
        put("bool", "INT2 ");
        put("enum", "INT4");
        put("text", "NCHAR");
        put("date", "NCHAR");
        put("position", "NCHAR");
    }});

    /**
     * 将物模型字段转换为td字段
     */
    public static KwField parse(ThingModel.Property property) {
        String filedName = property.getIdentifier().toLowerCase();
        ThingModel.DataType dataType = property.getDataType();
        String type = dataType.getType();

        //将物模型字段类型映射为td字段类型
        String fType = TYPE_MAPPING.get(type);
        Object specs = dataType.getSpecs();
        int len = -1;
        if (specs instanceof Map) {
            Object objLen = ((Map<?, ?>) specs).get("length");
            if (objLen != null) {
                try {
                    len = Integer.parseInt(objLen.toString());
                } catch (Exception e) {
                    throw exception(FILED_DEFINE, filedName + " 长度错误");
                }
            }
            if ("NCHAR".equals(fType) && len < 1) {
                throw exception(FILED_DEFINE, filedName + " 长度错误");
            }
        }

        return new KwField(filedName, fType, len);
    }

    /**
     * 获取物模型中的字段列表
     */
    public static List<KwField> parse(ThingModel thingModel) {
        return thingModel.getModel().getProperties().stream().map(FieldParser::parse).collect(Collectors.toList());
    }

    /**
     * 将从库中查出来的字段信息转换为td字段对象
     */
    public static List<KwField> parse(List rows) {
        return (List<KwField>) rows.stream().map((r) -> {
            List row = (List) r;
            String type = row.get(1).toString().toUpperCase();
            return new KwField(
                    row.get(0).toString(),
                    type,
                    type.endsWith("CHAR") ? Integer.parseInt(row.get(2).toString()) : -1);
        }).collect(Collectors.toList());
    }

    /**
     * 获取字段字义
     */
    public static String getFieldDefine(KwField field) {
        return field.getName() + " " + (field.getLength() > 0 ?
                String.format("%s(%d)", field.getType(), field.getLength())
                : field.getType());
    }

    /**
     * 获取Tag字义
     */
    public static String getFieldTagDefine(KwField field) {
        return field.getName();
    }

    /**
     * 获取字段字义
     */
    public static String getAlterFieldDefine(KwField field) {
        return field.getName() + " TYPE " + (field.getLength() > 0 ?
                String.format("%s(%d)", field.getType(), field.getLength())
                : field.getType());
    }
}
