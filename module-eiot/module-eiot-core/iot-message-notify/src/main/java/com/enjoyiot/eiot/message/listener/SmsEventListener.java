/*
 *
 *  * | Licensed 未经许可不能去掉「Enjoy-iot」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2025] [Enjoy-iot]
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * /
 */

package com.enjoyiot.eiot.message.listener;


import com.enjoyiot.eiot.message.config.VertxManager;
import com.enjoyiot.eiot.message.event.MessageEvent;
import com.enjoyiot.module.eiot.api.alert.ChannelSmsStrategy;
import com.enjoyiot.module.eiot.api.alert.dto.SmsConfig;
import com.enjoyiot.framework.common.util.json.JsonUtils;
import com.enjoyiot.module.eiot.api.alert.dto.Message;
import io.vertx.ext.web.client.WebClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map;
import java.util.HashMap;

import static com.alibaba.fastjson.JSON.toJSONString;

/**
 * author: EnjoyIot
 * date: 2023-05-08 15:09
 * description:
 **/
@Slf4j
@Component
public class SmsEventListener implements MessageEventListener {
    WebClient client = WebClient.create(VertxManager.INSTANCE.getVertx());
    @Resource
    private ChannelSmsStrategy channelSmsStrategy;

    @Override
    @EventListener(classes = MessageEvent.class, condition = "#event.message.channelCode=='SMS'")
    public void doEvent(MessageEvent event) {
        Message message = event.getMessage();
        String channelConfig = message.getChannelConfig();
        SmsConfig smsConfig = JsonUtils.parseObject(channelConfig, SmsConfig.class);
        channelSmsStrategy.sendSms(message.getParam(), message.getTemplateCode(), smsConfig);
    }

}
