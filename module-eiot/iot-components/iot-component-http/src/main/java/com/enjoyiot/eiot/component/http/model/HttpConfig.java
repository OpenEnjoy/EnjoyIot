package com.enjoyiot.eiot.component.http.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HttpConfig {
    private int port;

    private String sslKey;

    private String sslCert;

    private boolean ssl;
}
