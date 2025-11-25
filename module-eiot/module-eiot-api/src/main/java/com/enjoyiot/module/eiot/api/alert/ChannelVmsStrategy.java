package com.enjoyiot.module.eiot.api.alert;


import com.enjoyiot.module.eiot.api.alert.dto.VmsConfig;

import java.util.Map;

public interface ChannelVmsStrategy {
    void callByTts(Map<String, Object> templateParam, String templateId, VmsConfig smsConfig);
}
