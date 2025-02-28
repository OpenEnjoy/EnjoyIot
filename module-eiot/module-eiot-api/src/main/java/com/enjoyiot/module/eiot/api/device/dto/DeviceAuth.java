package com.enjoyiot.module.eiot.api.device.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceAuth implements Serializable {

    public static final Integer AUTH_TYPE_MQTT = 1;

    private Long tenantId;


    private String clientId;


    private String userName;

    private String password;

    private boolean canRegister;

    private Integer authType;
}
