package com.enjoyiot.module.eiot.api.alert;


import com.enjoyiot.module.eiot.api.alert.dto.SmsConfig;

import java.util.Map;

public interface ChannelSmsStrategy {
    void sendSms(Map<String, Object> templateParam, String templateId, SmsConfig smsConfig);

    String createSmsTemplate(String templateContent, Long templateId, SmsConfig smsConfig);

    String updateSmsTemplate(String templateContent, String templateCode, SmsConfig smsConfig);

    Integer querySmsTemplateStatus(SmsConfig smsConfig, String templateCode);

    void deleteSmsTemplate(String templateCode, SmsConfig smsConfig);
}
