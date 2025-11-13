package com.enjoyiot.module.eiot.api.alert.sms;

import com.enjoyiot.module.eiot.api.alert.dto.SmsConfig;
import com.enjoyiot.module.eiot.api.alert.ChannelSmsStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@ConditionalOnProperty(name = "config.alert.sms-provider", havingValue = "tencent")
public class ChannelSmsTencentStrategy implements ChannelSmsStrategy {

    @Override
    public void sendSms(Map<String, Object> templateParam, String templateId, SmsConfig smsConfig) {
        // todo
    }

    /**
     * 创建腾讯云短信模板
     *
     * @param templateContent
     * @param templateId
     * @param smsConfig
     * @return
     */
    public String createSmsTemplate(String templateContent, Long templateId, SmsConfig smsConfig) {
        // todo
        return null;
    }

    /**
     * 更新腾讯云短信模板
     *
     * @param templateContent
     * @param templateCode
     * @param smsConfig
     * @return
     */
    public String updateSmsTemplate(String templateContent, String templateCode, SmsConfig smsConfig) {
        // todo
        return null;
    }

    /**
     * 查询腾讯云短信模板状态
     *
     * @param smsConfig    通道配置
     * @param templateCode 模板代码
     * @return 状态 (0-待审核, 1-审核通过, 2-审核失败)
     */
    public Integer querySmsTemplateStatus(SmsConfig smsConfig, String templateCode) {
        // todo
        return null;
    }

    /**
     * 删除腾讯云短信模板
     *
     * @param templateCode
     * @param smsConfig
     */
    public void deleteSmsTemplate(String templateCode, SmsConfig smsConfig) {
        // todo
    }
}
