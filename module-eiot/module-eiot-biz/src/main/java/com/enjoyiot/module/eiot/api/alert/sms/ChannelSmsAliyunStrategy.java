package com.enjoyiot.module.eiot.api.alert.sms;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.teaopenapi.models.Config;
import com.enjoyiot.module.eiot.api.alert.dto.SmsConfig;
import com.enjoyiot.framework.common.exception.util.ServiceExceptionUtil;
import com.enjoyiot.framework.common.util.json.JsonUtils;
import com.enjoyiot.module.eiot.api.alert.ChannelSmsStrategy;
import com.enjoyiot.module.eiot.api.enums.ErrorCodeConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@ConditionalOnProperty(name = "config.alert.sms-provider", havingValue = "aliyun")
public class ChannelSmsAliyunStrategy implements ChannelSmsStrategy {
    private static final String ALI_END_POINT = "dysmsapi.aliyuncs.com";

    public void sendSms(Map<String, Object> templateParam, String templateId, SmsConfig smsConfig) {
        try {
            // 创建阿里云SMS客户端
            Client client = getClient(smsConfig);

            // 创建发送短信的请求
            SendSmsRequest request = new SendSmsRequest()
                    .setSignName(smsConfig.getSignName())
                    .setPhoneNumbers(smsConfig.getPhoneNumbers())
                    .setTemplateCode(templateId)
                    .setTemplateParam(JsonUtils.toJsonString(templateParam));

            // 发送短信
            SendSmsResponse response = client.sendSms(request);
            log.info("SMS sent submit, request={}, response={}", JsonUtils.toJsonString(request), JsonUtils.toJsonString(response));
            if ("OK".equals(response.getBody().getCode())) {
                log.info("SMS sent successfully, templateId: {}, requestId: {}", templateId, response.getBody().getRequestId());
            } else {
                log.error("Failed to send SMS, error: {}", response.getBody().getMessage());
                throw ServiceExceptionUtil.exception(ErrorCodeConstants.SMS_SEND_FAILED);
            }
        } catch (Exception e) {
            log.error("Error sending SMS for templateId: {}", templateId, e);
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.SMS_SEND_FAILED);
        }


    }

    /**
     * 创建阿里云短信模板
     *
     * @param templateContent
     * @param templateId
     * @param smsConfig
     * @return
     */
    public String createSmsTemplate(String templateContent, Long templateId, SmsConfig smsConfig) {
        try {
            // 创建阿里云SMS客户端
            Client client = getClient(smsConfig);

            // 调用阿里云API创建模板
            CreateSmsTemplateRequest request = new CreateSmsTemplateRequest()
                    .setRelatedSignName(smsConfig.getSignName())
                    .setTemplateType(1) // 通知类
                    .setTemplateName(UUID.randomUUID().toString().split("-")[0])
                    .setTemplateContent(templateContent)
                    .setTemplateRule(buildTemplateRule(templateContent));
            CreateSmsTemplateResponse response = client.createSmsTemplate(request);
            log.info("SMS template created submit, request={}, response={}", JsonUtils.toJsonString(request), JsonUtils.toJsonString(response));

            if ("OK".equals(response.getBody().getCode())) {
                String templateCode = response.getBody().getTemplateCode();
                log.info("SMS template created successfully, templateId: {}, templateCode: {}", templateId, templateCode);
                return templateCode;
            } else {
                log.error("Failed to create SMS template, error: {}", response.getBody().getMessage());
                throw ServiceExceptionUtil.exception(ErrorCodeConstants.SMS_TEMPLATE_CREATE_FAILED);
            }
        } catch (Exception e) {
            log.error("Error creating SMS template for templateId: {}", templateId, e);
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.SMS_TEMPLATE_CREATE_FAILED);
        }
    }

    /**
     * 更新阿里云短信模板
     *
     * @param templateContent
     * @param templateCode
     * @param channelConfig
     * @return
     */
    public String updateSmsTemplate(String templateContent, String templateCode, SmsConfig smsConfig) {
        try {
            // 创建阿里云SMS客户端
            Client client = getClient(smsConfig);

            // 调用阿里云API更新模板
            UpdateSmsTemplateRequest request = new UpdateSmsTemplateRequest()
                    .setTemplateCode(templateCode)
                    .setRelatedSignName(smsConfig.getSignName())
                    .setTemplateType(1) // 通知类
                    .setTemplateName(UUID.randomUUID().toString().split("-")[0])
                    .setTemplateContent(templateContent)
                    .setTemplateRule(buildTemplateRule(templateContent));
            UpdateSmsTemplateResponse response = client.updateSmsTemplate(request);
            log.info("SMS template updated submit, request={}, response={}", JsonUtils.toJsonString(request), JsonUtils.toJsonString(response));

            if ("OK".equals(response.getBody().getCode())) {
                log.info("SMS template updated successfully, templateCode: {}", templateCode);
                return templateCode;
            } else {
                log.error("Failed to update SMS template, error: {}", response.getBody().getMessage());
                throw ServiceExceptionUtil.exception(ErrorCodeConstants.SMS_TEMPLATE_UPDATE_FAILED);
            }
        } catch (Exception e) {
            log.error("Error updating SMS template for templateCode: {}", templateCode, e);
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.SMS_TEMPLATE_UPDATE_FAILED);
        }
    }

    /**
     * 查询阿里云短信模板状态
     *
     * @param smsConfig    通道配置
     * @param templateCode 模板代码
     * @return 状态 (0-待审核, 1-审核通过, 2-审核失败)
     */
    public Integer querySmsTemplateStatus(SmsConfig smsConfig, String templateCode) {
        try {
            // 创建阿里云SMS客户端
            Client client = getClient(smsConfig);

            // 查询短信模板状态
            QuerySmsTemplateRequest request = new QuerySmsTemplateRequest()
                    .setTemplateCode(templateCode);
            QuerySmsTemplateResponse response = client.querySmsTemplate(request);
            log.info("SMS template query submit, request={}, response={}", JsonUtils.toJsonString(request), JsonUtils.toJsonString(response));

            if (response.getBody() != null && "OK".equals(response.getBody().getCode())) {
                /*
                 * 阿里云短信模板状态:
                 * 0: 审核中
                 * 1: 审核通过
                 * 2: 审核失败
                 */
                Integer templateStatus = response.getBody().getTemplateStatus();
                if (templateStatus != null) {
                    // 转换为本地状态 (0-待审核, 1-审核通过, 2-审核失败)
                    return templateStatus;
                }
            } else {
                log.warn("查询短信模板 {} 状态失败: {}", templateCode, response.getBody() != null ? response.getBody().getMessage() : "未知错误");
            }
        } catch (Exception e) {
            log.error("查询阿里云短信模板 {} 状态时发生错误", templateCode, e);
        }

        return null;
    }

    /**
     * 删除阿里云短信模板
     *
     * @param templateCode
     * @param smsConfig
     */
    public void deleteSmsTemplate(String templateCode, SmsConfig smsConfig) {
        try {
            // 创建阿里云SMS客户端
            Client client = getClient(smsConfig);

            // 调用阿里云API删除模板
            DeleteSmsTemplateRequest request = new DeleteSmsTemplateRequest()
                    .setTemplateCode(templateCode);
            DeleteSmsTemplateResponse response = client.deleteSmsTemplate(request);
            log.info("SMS template deleted submit, request={}, response={}", JsonUtils.toJsonString(request), JsonUtils.toJsonString(response));
            if ("OK".equals(response.getBody().getCode())) {
                log.info("SMS template deleted successfully, templateCode: {}", templateCode);
            } else {
                log.error("Failed to deleted SMS template, error: {}", response.getBody().getMessage());
                throw ServiceExceptionUtil.exception(ErrorCodeConstants.SMS_TEMPLATE_UPDATE_FAILED);
            }
        } catch (Exception e) {
            log.error("Error deleting SMS template for templateCode: {}", templateCode, e);
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.SMS_TEMPLATE_DELETE_FAILED);
        }
    }

    private Client getClient(SmsConfig smsConfig) throws Exception {
        Config config = new Config()
                .setAccessKeyId(smsConfig.getAccessKeyId())
                .setAccessKeySecret(smsConfig.getAccessKeySecret())
                .setEndpoint(ALI_END_POINT);
        return new Client(config);
    }

    private String buildTemplateRule(String templateContent) {
        Map<String, String> templateRuleMap = new HashMap<>();
        Pattern pattern = Pattern.compile("\\$\\{([^}]+)}");
        Matcher matcher = pattern.matcher(templateContent);
        while (matcher.find()) {
            String placeholder = matcher.group(1);
            templateRuleMap.put(placeholder, determinePlaceholderType(placeholder));
        }
        return JsonUtils.toJsonString(templateRuleMap);
    }

    /**
     * 根据占位符名称确定其类型，符合阿里云短信模板规范
     *
     * @param placeholder 占位符名称
     * @return 对应的类型
     */
    private String determinePlaceholderType(String placeholder) {
        // 转换为小写进行比较
        String lowerPlaceholder = placeholder.toLowerCase()
                .replace(" ", StringUtils.EMPTY)
                .replace("_", StringUtils.EMPTY)
                .replace("-", StringUtils.EMPTY)
                .replace(".", StringUtils.EMPTY)
                .replace(":", StringUtils.EMPTY);

        // 时间类型
        if (lowerPlaceholder.contains("time") || lowerPlaceholder.contains("date")
                || lowerPlaceholder.contains("day") || lowerPlaceholder.contains("year")
                || lowerPlaceholder.contains("month") || lowerPlaceholder.contains("week")
                || lowerPlaceholder.contains("hour") || lowerPlaceholder.contains("minute")
                || lowerPlaceholder.contains("second")) {
            return "date";
        }

        // 金额/数量类型
        if (lowerPlaceholder.contains("money") || lowerPlaceholder.contains("amount")
                || lowerPlaceholder.contains("price") || lowerPlaceholder.contains("fee")
                || lowerPlaceholder.contains("count") || lowerPlaceholder.contains("num")
                || lowerPlaceholder.contains("quantity")) {
            return "amount";
        }

        // 用户昵称类型
        if (lowerPlaceholder.contains("nick") || lowerPlaceholder.contains("username")
                || lowerPlaceholder.contains("nickname")) {
            return "user_nick";
        }

        // 个人姓名类型
        if (lowerPlaceholder.contains("name") && !lowerPlaceholder.contains("user")
                && !lowerPlaceholder.contains("product") && !lowerPlaceholder.contains("item")) {
            return "name";
        }

        // 企业/组织名称类型
        if (lowerPlaceholder.contains("company") || lowerPlaceholder.contains("enterprise")
                || lowerPlaceholder.contains("org") || lowerPlaceholder.contains("organization")
                || lowerPlaceholder.contains("unit")) {
            return "unit_name";
        }

        // 地址类型
        if (lowerPlaceholder.contains("address") || lowerPlaceholder.contains("location")) {
            return "address";
        }

        // 车牌号类型
        if (lowerPlaceholder.contains("plate") || lowerPlaceholder.contains("license")) {
            return "license_plate_number";
        }

        // 快递单号类型
        if (lowerPlaceholder.contains("tracking") || lowerPlaceholder.contains("express")) {
            return "tracking_number";
        }

        // 取件码类型
        if (lowerPlaceholder.contains("code") && (lowerPlaceholder.contains("pick") || lowerPlaceholder.contains("pickup"))) {
            return "pick_up_code";
        }

        // 电话号码类型
        if (lowerPlaceholder.contains("phone") || lowerPlaceholder.contains("tel")
                || lowerPlaceholder.contains("mobile") || lowerPlaceholder.contains("telephone")) {
            return "phone";
        }

        // 其他号码类型（默认）
        if (lowerPlaceholder.contains("code") || lowerPlaceholder.contains("order")
                || lowerPlaceholder.contains("password") || lowerPlaceholder.contains("pass")
                || lowerPlaceholder.contains("id") || lowerPlaceholder.contains("sn")) {
            return "other_number2";
        }

        // 默认类型
        return "others";
    }
}
