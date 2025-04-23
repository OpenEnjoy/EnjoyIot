package com.enjoyiot.module.eiot.api.modbus.dto;


import cn.hutool.core.util.ByteUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.enjoyiot.module.eiot.api.TenantModel;
import com.enjoyiot.module.eiot.api.thingmodel.dto.ThingModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModbusThingModel extends TenantModel {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String productKey;

    private ModbusThingModel.Model model;

    public ModbusThingModel(String productKey) {
        this.productKey = productKey;
    }

    @Data
    public static class Model {
        private List<Property> properties;
        private List<ModbusThingModel.Service> services;
        private List<ModbusThingModel.Event> events;

        public Map<String, Service> serviceMap() {
            if (services == null) {
                return new HashMap<>();
            }
            return services.stream().collect(Collectors.toMap(ModbusThingModel.Service::getIdentifier, s -> s));
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Property {
        public static final String[] SORT_ENUM = {"AB", "BA", "AB CD", "CD AB", "DC BA", "BA DC"};
        private String name;
        private String identifier;
        // 描述
        private String description;
        /**
         * 寄存器类型
         * [{value:'01',label:'线圈状态(RW)',},
         * {value:'02',label:'离散输入(RO)',},
         * {value:'03',label:'保持寄存器(RW)',},
         * {value:'04',label:'输入寄存器(RO)',},]
         */
        private String regType;
        //寄存器地址
        private Integer regAddr;
        //寄存器数量
        private Integer regNum;
        //  * 处理公式
        private String processor;
        //数据顺序
        /**
         * [
         * { value: 'AB',  },
         * { value: 'BA',  },
         * { value: 'AB CD',  },
         * { value: 'CD AB',  },
         * { value: 'DC BA',  },
         * { value: 'BA DC',  },
         * ]
         */
        private String sort;

        private ModbusThingModel.DataType dataType;

        private String accessMode = "rw";

        // 单位
        private String unit;

        public Object parse(byte[] bytes) {
            if (bytes == null || bytes.length == 0) {
                return null;
            }

            // 设置默认数据顺序
            String sort1 = "AB";
            if (Arrays.stream(SORT_ENUM).anyMatch(s -> s.equals(sort))){
                sort1 = sort.replace(" ", "");
            }

            byte[] sortedBytes = new byte[bytes.length];
            // 按照数据顺序重排序bytes
            if (sort1.equals("ABCD") || sort1.equals("AB")) {
                sortedBytes = bytes;
            } else {
                // 将sort1中的ABCD的索引放到一个数组中
                int[] index = new int[sort1.length()];
                for (int i = 0; i < sort1.length(); i++) {
                    index[i] = sort1.indexOf(i + 'A');
                }

                int len = bytes.length / index.length;
                int remainder = bytes.length % index.length;

                if (remainder != 0 || len == 0) {
                    // 长度匹配不上的，一律按照原样处理
                    sortedBytes = bytes;
                } else {
                    for (int i = 0; i < len; i++) {
                        int start = i * index.length;
                        for (int j = 0; j < index.length; j++) {
                            sortedBytes[start + j] = bytes[start + index[j]];
                        }
                    }
                }


            }
            // 解析已经排好顺序的数据
            return dataType.parse(sortedBytes);
        }
    }


    @Data
    public static class ProData {
        /**
         * 寄存器类型
         * [{value:'01',label:'线圈状态(RW)',},
         * {value:'02',label:'离散输入(RO)',},
         * {value:'03',label:'保持寄存器(RW)',},
         * {value:'04',label:'输入寄存器(RO)',},]
         */
        private String regType;
        //寄存器地址
        private Integer regAddr;
        //寄存器数量
        private Integer regNum;
        //  * 处理公式
        private String processor;
        //数据顺序
        /**
         * [
         * { value: 'AB',  },
         * { value: 'BA',  },
         * { value: 'AB CD',  },
         * { value: 'CD AB',  },
         * { value: 'DC BA',  },
         * { value: 'BA DC',  },
         * ]
         */
        private String sort;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Parameter {
        private String identifier;
        private ThingModel.DataType dataType;
        private String name;
        private Boolean required = false;
    }

    @Data
    public static class Service {
        private String identifier;
        private List<ModbusThingModel.Parameter> inputData;
        private List<ModbusThingModel.Parameter> outputData;
        private String name;
    }

    @Data
    public static class Event {
        private String identifier;
        private List<ModbusThingModel.Parameter> outputData;
        private String name;
    }

    @Data
    public static class DataType {
        private String type;
        private Object specs;

        public Object parse(byte[] bytes) {
            type = type.toLowerCase();
            JSONObject specsMap = specs != null ? JSONUtil.parseObj(specs) : new JSONObject();


            int intValue;
            if (bytes.length >= 4) {
                intValue = ByteUtil.bytesToInt(bytes, ByteOrder.BIG_ENDIAN);
            } else if (bytes.length >= 2) {
                intValue = ByteUtil.bytesToShort(bytes, ByteOrder.BIG_ENDIAN);
            } else {
                intValue = (bytes[0] & 255);
            }

            switch (type) {
                case "bool":
                    String _false = specsMap.getStr("0", "false");
                    String _true = specsMap.getStr("1", "true");
                    return intValue == 0 ? _false : _true;
                case "enum":
                    return specsMap.getStr(String.valueOf(intValue), String.valueOf(intValue));
                case "int":
                case "int32":
                    return intValue;
                case "float":
                    float floatVal = Float.intBitsToFloat(intValue);
                    // 保留小数位数
                    Integer precision = specsMap.getInt("precision", -1);
                    if (NumberUtil.isValid(floatVal)) {
                        if (precision >= 0) {
                            BigDecimal bigDecimal = NumberUtil.roundHalfEven(floatVal, precision);
                            return bigDecimal.floatValue();
                        } else {
                            return floatVal;
                        }
                    }
                    return null;
                case "date":
                    // String类型的UTC时间戳（毫秒）
                    String dateStr = new String(bytes, StandardCharsets.US_ASCII);
                    if (NumberUtil.isLong(dateStr)) {
                        return new Date(Long.parseLong(dateStr));
                    }
                    return null;
                case "text":
                    return new String(bytes, StandardCharsets.US_ASCII);
                case "position":
                    // TODO: 还未规定具体的格式，先按照浮点数处理
                    float position = Float.intBitsToFloat(intValue);
                    if (NumberUtil.isValid(position)) {
                        return position;
                    }
                    return null;
                default:
                    return new String(bytes, StandardCharsets.US_ASCII);
            }

        }
    }
}
