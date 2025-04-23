package com.enjoyiot.eiot.component.modbusCustom.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModbusConfig {
    private Integer port;

    private Integer timer;

}
