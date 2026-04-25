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
package com.enjoyiot.module.eiot.api.thingmodel.dto;

import com.enjoyiot.framework.common.util.json.JsonUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.enjoyiot.module.eiot.api.TenantModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThingModel extends TenantModel {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String productKey;

    private Model model;

    public ThingModel(String productKey) {
        this.productKey = productKey;
    }

    @Data
    public static class Model {
        private List<Property> properties;
        private List<Service> services;
        private List<Event> events;

        public Map<String, Service> serviceMap() {
            if (services == null) {
                return new HashMap<>();
            }
            return services.stream().collect(Collectors.toMap(Service::getIdentifier, service -> service));
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Property {
        private String identifier;
        private DataType dataType;
        private String name;
        private String accessMode = "rw";
        private String description;
        private String unit;
        private Long iconId;
        private String icon;
        private String proData;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Parameter {
        private String identifier;
        private DataType dataType;
        private String name;
        private Boolean required = false;
        private String description;
    }

    @Data
    public static class Service {
        private String identifier;
        private List<Parameter> inputData;
        private List<Parameter> outputData;
        private String name;
        private String description;
    }

    @Data
    public static class Event {
        private String identifier;
        private List<Parameter> outputData;
        private String name;
        private String description;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataType {
        public static final String TYPE_BOOL = "bool";
        public static final String TYPE_ENUM = "enum";
        public static final String TYPE_INT32 = "int32";
        public static final String TYPE_INT64 = "int64";
        public static final String TYPE_FLOAT = "float";
        public static final String TYPE_DOUBLE = "double";
        public static final String TYPE_STRING = "string";
        public static final String TYPE_TEXT = "text";
        public static final String TYPE_DATE = "date";
        public static final String TYPE_DATETIME = "datetime";
        public static final String TYPE_ARRAY = "array";
        public static final String TYPE_OBJECT = "object";
        public static final String TYPE_POSITION = "position";

        private String type;
        private Object specs;

        public <T> Object parse(T value) {
            if (value == null) {
                return null;
            }

            switch (normalizedType()) {
                case TYPE_BOOL:
                    return parseBoolean(value);
                case TYPE_ENUM:
                    return parseEnum(value);
                case TYPE_INT32:
                    return parseInteger(value);
                case TYPE_INT64:
                    return parseLong(value);
                case TYPE_FLOAT:
                    return parseFloat(value);
                case TYPE_DOUBLE:
                    return parseDouble(value);
                case TYPE_STRING:
                case TYPE_TEXT:
                case TYPE_DATE:
                case TYPE_DATETIME:
                case TYPE_POSITION:
                    return stringifyScalar(value);
                case TYPE_OBJECT:
                    return parseObjectValue(value);
                case TYPE_ARRAY:
                    return parseArrayValue(value);
                default:
                    return value;
            }
        }

        public String normalizedType() {
            this.type = normalizeType(type);
            return this.type;
        }

        @JsonIgnore
        public Map<String, Object> getSpecMap() {
            if (specs instanceof Map) {
                return new LinkedHashMap<>((Map<String, Object>) specs);
            }
            if (specs == null) {
                return new LinkedHashMap<>();
            }
            Map<String, Object> parsed = JsonUtils.parseObjectQuietly(JsonUtils.toJsonString(specs),
                    new TypeReference<LinkedHashMap<String, Object>>() {});
            return parsed == null ? new LinkedHashMap<>() : parsed;
        }

        @JsonIgnore
        public List<Parameter> getPropertySpecs() {
            Map<String, Object> specMap = getSpecMap();
            Object raw = specMap.get("properties");
            if (raw == null) {
                raw = specMap.get("fields");
            }
            if (raw == null) {
                return new ArrayList<>();
            }
            List<Parameter> parsed = JsonUtils.parseObjectQuietly(JsonUtils.toJsonString(raw),
                    new TypeReference<List<Parameter>>() {});
            return parsed == null ? new ArrayList<>() : parsed;
        }

        @JsonIgnore
        public DataType getItemTypeSpec() {
            Map<String, Object> specMap = getSpecMap();
            Object raw = specMap.get("itemType");
            if (raw == null) {
                raw = specMap.get("items");
            }
            if (raw == null) {
                return null;
            }
            return JsonUtils.parseObjectQuietly(JsonUtils.toJsonString(raw), new TypeReference<DataType>() {});
        }

        private Object parseEnum(Object value) {
            if (value instanceof Number) {
                return value;
            }
            String text = stringifyScalar(value);
            return text == null ? null : text.trim();
        }

        private Integer parseBoolean(Object value) {
            if (value instanceof Boolean) {
                return (Boolean) value ? 1 : 0;
            }
            if (value instanceof Number) {
                return ((Number) value).intValue() != 0 ? 1 : 0;
            }
            String text = stringifyScalar(value);
            if (text == null) {
                return null;
            }
            switch (text.trim().toLowerCase()) {
                case "1":
                case "true":
                case "yes":
                case "on":
                    return 1;
                case "0":
                case "false":
                case "no":
                case "off":
                    return 0;
                default:
                    return null;
            }
        }

        private Integer parseInteger(Object value) {
            if (value instanceof Number) {
                return ((Number) value).intValue();
            }
            String text = stringifyScalar(value);
            if (text == null || text.trim().isEmpty()) {
                return null;
            }
            try {
                return Integer.parseInt(text.trim());
            } catch (NumberFormatException ex) {
                return null;
            }
        }

        private Long parseLong(Object value) {
            if (value instanceof Number) {
                return ((Number) value).longValue();
            }
            String text = stringifyScalar(value);
            if (text == null || text.trim().isEmpty()) {
                return null;
            }
            try {
                return Long.parseLong(text.trim());
            } catch (NumberFormatException ex) {
                return null;
            }
        }

        private Float parseFloat(Object value) {
            if (value instanceof Number) {
                return ((Number) value).floatValue();
            }
            String text = stringifyScalar(value);
            if (text == null || text.trim().isEmpty()) {
                return null;
            }
            try {
                return Float.parseFloat(text.trim());
            } catch (NumberFormatException ex) {
                return null;
            }
        }

        private Double parseDouble(Object value) {
            if (value instanceof Number) {
                return ((Number) value).doubleValue();
            }
            String text = stringifyScalar(value);
            if (text == null || text.trim().isEmpty()) {
                return null;
            }
            try {
                return Double.parseDouble(text.trim());
            } catch (NumberFormatException ex) {
                return null;
            }
        }

        private Object parseObjectValue(Object value) {
            Map<String, Object> raw = asMap(value);
            if (raw == null) {
                return null;
            }
            List<Parameter> properties = getPropertySpecs();
            if (properties.isEmpty()) {
                return raw;
            }
            Map<String, Parameter> propertyMap = properties.stream()
                    .filter(Objects::nonNull)
                    .filter(parameter -> parameter.getIdentifier() != null)
                    .collect(Collectors.toMap(Parameter::getIdentifier, parameter -> parameter, (left, right) -> right, LinkedHashMap::new));
            Map<String, Object> parsed = new LinkedHashMap<>();
            raw.forEach((key, rawValue) -> {
                Parameter parameter = propertyMap.get(key);
                if (parameter == null || parameter.getDataType() == null) {
                    parsed.put(key, rawValue);
                } else {
                    parsed.put(key, parameter.getDataType().parse(rawValue));
                }
            });
            return parsed;
        }

        private Object parseArrayValue(Object value) {
            List<Object> raw = asList(value);
            if (raw == null) {
                return null;
            }
            DataType itemType = getItemTypeSpec();
            if (itemType == null) {
                return raw;
            }
            List<Object> parsed = new ArrayList<>(raw.size());
            raw.forEach(item -> parsed.add(itemType.parse(item)));
            return parsed;
        }

        private static String normalizeType(String rawType) {
            if (rawType == null || rawType.trim().isEmpty()) {
                return TYPE_TEXT;
            }
            switch (rawType.trim().toLowerCase()) {
                case "int":
                case TYPE_INT32:
                    return TYPE_INT32;
                case "long":
                case TYPE_INT64:
                    return TYPE_INT64;
                case TYPE_FLOAT:
                    return TYPE_FLOAT;
                case "decimal":
                case TYPE_DOUBLE:
                    return TYPE_DOUBLE;
                case "str":
                case TYPE_STRING:
                    return TYPE_STRING;
                case TYPE_TEXT:
                    return TYPE_TEXT;
                case "timestamp":
                case TYPE_DATETIME:
                    return TYPE_DATETIME;
                case TYPE_DATE:
                    return TYPE_DATE;
                case "struct":
                case TYPE_OBJECT:
                    return TYPE_OBJECT;
                case TYPE_ARRAY:
                    return TYPE_ARRAY;
                case "boolean":
                case TYPE_BOOL:
                    return TYPE_BOOL;
                case TYPE_ENUM:
                    return TYPE_ENUM;
                case TYPE_POSITION:
                    return TYPE_POSITION;
                default:
                    return rawType.trim().toLowerCase();
            }
        }

        private static String stringifyScalar(Object value) {
            if (value == null) {
                return null;
            }
            if (value instanceof Map || value instanceof Collection || value.getClass().isArray()) {
                return JsonUtils.toJsonString(value);
            }
            return String.valueOf(value);
        }

        private static Map<String, Object> asMap(Object value) {
            if (value instanceof Map) {
                return new LinkedHashMap<>((Map<String, Object>) value);
            }
            if (!(value instanceof String) || !JsonUtils.isJson((String) value)) {
                return null;
            }
            return JsonUtils.parseObjectQuietly((String) value, new TypeReference<LinkedHashMap<String, Object>>() {});
        }

        private static List<Object> asList(Object value) {
            if (value instanceof List) {
                return new ArrayList<>((List<Object>) value);
            }
            if (value != null && value.getClass().isArray()) {
                int length = Array.getLength(value);
                List<Object> list = new ArrayList<>(length);
                for (int index = 0; index < length; index++) {
                    list.add(Array.get(value, index));
                }
                return list;
            }
            if (!(value instanceof String) || !JsonUtils.isJson((String) value)) {
                return null;
            }
            return JsonUtils.parseObjectQuietly((String) value, new TypeReference<ArrayList<Object>>() {});
        }
    }
}
