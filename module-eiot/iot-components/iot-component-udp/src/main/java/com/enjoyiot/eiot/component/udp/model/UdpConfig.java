package com.enjoyiot.eiot.component.udp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UDP配置类
 *@createtime 2025/5/22 13:26
 *@version 1.0
 *@author stuil
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UdpConfig {

    private int port;
    private String  host;
    private boolean enableAutoRegister = false;
    private String  defaultProductKey="UWB_DEFAULT_PRODUCT";
}
