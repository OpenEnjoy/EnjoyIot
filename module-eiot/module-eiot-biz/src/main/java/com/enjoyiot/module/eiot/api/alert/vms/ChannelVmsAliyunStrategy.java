package com.enjoyiot.module.eiot.api.alert.vms;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.tea.TeaConverter;
import com.aliyun.tea.TeaPair;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teaopenapi.models.OpenApiRequest;
import com.aliyun.teaopenapi.models.Params;
import com.aliyun.teautil.models.RuntimeOptions;
import com.enjoyiot.framework.common.util.json.JsonUtils;
import com.enjoyiot.module.eiot.api.alert.ChannelVmsStrategy;
import com.enjoyiot.module.eiot.api.alert.dto.VmsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
@ConditionalOnProperty(name = "config.alert.vms-provider", havingValue = "aliyun", matchIfMissing = true)
public class ChannelVmsAliyunStrategy implements ChannelVmsStrategy {
    private static final String ALI_END_POINT = "dyvmsapi.aliyuncs.com";

    @Override
    public void callByTts(Map<String, Object> templateParam, String templateId, VmsConfig vmsConfig) {
        try {
            Client client = getClient(vmsConfig);

            Map<String, Object> query = new HashMap<>();
            query.put("CalledNumber", vmsConfig.getPhoneNumbers());
            query.put("TtsCode", templateId);
            query.put("TtsParam", templateParam);

            OpenApiRequest req = OpenApiRequest.build(TeaConverter.buildMap(
                    new TeaPair("query", com.aliyun.openapiutil.Client.query(query))
            ));
            Params params = Params.build(TeaConverter.buildMap(
                    new TeaPair("action", "SingleCallByTts"),
                    new TeaPair("version", "2017-05-25"),
                    new TeaPair("protocol", "HTTPS"),
                    new TeaPair("pathname", "/"),
                    new TeaPair("method", "POST"),
                    new TeaPair("authType", "AK"),
                    new TeaPair("style", "RPC"),
                    new TeaPair("reqBodyType", "formData"),
                    new TeaPair("bodyType", "json")
            ));
            Map<String, ?> response = client.callApi(params, req, new RuntimeOptions());
            log.info("Aliyun voice sent submit, request={}, response={}", JsonUtils.toJsonString(req), JsonUtils.toJsonString(response));

            boolean callSuccess = false;
            if (response != null && response.containsKey("body")) {
                Map<String, String> body = (Map<String, String>) response.get("body");
                if (Objects.equals(body.get("Code"), "OK")) {
                    callSuccess = true;
                }
            }

            if (callSuccess) {
                log.info("Aliyun voice sent successfully, templateId: {}", templateId);
            } else {
                log.error("Failed to send Aliyun voice, templateId: {}", templateId);
            }
        } catch (Exception e) {
            log.error("Error sending Aliyun voice for templateId: {}", templateId, e);
        }
    }

    private Client getClient(VmsConfig smsConfig) throws Exception {
        Config config = new Config()
                .setAccessKeyId(smsConfig.getAccessKeyId())
                .setAccessKeySecret(smsConfig.getAccessKeySecret())
                .setEndpoint(ALI_END_POINT);
        return new Client(config);
    }

}
