package com.enjoyiot.module.eiot.api.modbus.dto;

import com.enjoyiot.module.eiot.api.TenantModel;
import com.enjoyiot.module.eiot.api.thingmodel.dto.ThingModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        public <T> Object parse(T value) {
            if (value == null) {
                return null;
            }

            String val = value.toString();
            type = type.toLowerCase();
            switch (type) {
                case "bool":
                case "enum":
                    return val;
                case "int":
                    return Integer.parseInt(val);
                default:
                    return val;
            }

        }
    }
}
