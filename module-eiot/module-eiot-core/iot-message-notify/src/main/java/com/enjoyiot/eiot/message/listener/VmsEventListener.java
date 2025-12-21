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
import com.enjoyiot.framework.common.util.json.JsonUtils;
import com.enjoyiot.module.eiot.api.alert.ChannelVmsStrategy;
import com.enjoyiot.module.eiot.api.alert.dto.Message;
import com.enjoyiot.module.eiot.api.alert.dto.VmsConfig;
import io.vertx.ext.web.client.WebClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

/**
 * author: EnjoyIot
 * date: 2023-05-08 15:09
 * description:
 **/
@Slf4j
@Component
public class VmsEventListener implements MessageEventListener {
    @Resource
    private ChannelVmsStrategy channelVmsStrategy;

    @Override
    @EventListener(classes = MessageEvent.class, condition = "#event.message.channelCode=='VMS'")
    public void doEvent(MessageEvent event) {
        Message message = event.getMessage();
        String channelConfig = message.getChannelConfig();
        VmsConfig vmsConfig = JsonUtils.parseObject(channelConfig, VmsConfig.class);
        channelVmsStrategy.callByTts(message.getParam(), message.getTemplateCode(), vmsConfig);
    }

}
