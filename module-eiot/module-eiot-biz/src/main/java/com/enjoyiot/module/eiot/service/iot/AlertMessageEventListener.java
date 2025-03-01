/*
 *
 *  * | Licensed 未经许可不能去掉「Enjoy-iot」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com | Tel: 19918996474
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2025] [Enjoy-iot] | Tel: 19918996474
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

package com.enjoyiot.module.eiot.service.iot;


import com.enjoyiot.eiot.message.event.MessageEvent;
import com.enjoyiot.eiot.message.listener.MessageEventListener;
import com.enjoyiot.module.eiot.api.alert.dto.AlertConfig;
import com.enjoyiot.module.eiot.api.alert.dto.Message;
import com.enjoyiot.module.eiot.service.alert.AlertConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author sjg
 */
@Slf4j
@Component
public class AlertMessageEventListener implements MessageEventListener {

    @Resource
    private AlertConfigService alertConfigService;

    @Override
    @EventListener(classes = MessageEvent.class)
    public void doEvent(MessageEvent event) {
        Message message = event.getMessage();
        AlertConfig alertConfig = alertConfigService.getAlertConfig(message.getAlertConfigId());
        alertConfigService.addAlertRecord(alertConfig, message.getFormatContent());
    }
}
