
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
package com.enjoyiot.module.eiot.api.alert;

import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.eiot.api.alert.dto.AlertConfig;
import com.enjoyiot.module.eiot.api.alert.dto.AlertConfigPageReqVO;
import com.enjoyiot.module.eiot.api.alert.dto.Message;
import com.enjoyiot.module.eiot.controller.admin.channelconfig.vo.ChannelConfig;
import com.enjoyiot.module.eiot.controller.admin.channeltemplate.vo.ChannelTemplate;
import com.enjoyiot.module.eiot.service.alert.AlertConfigService;
import com.enjoyiot.module.eiot.service.alert.ChannelConfigService;
import com.enjoyiot.module.eiot.service.alert.ChannelTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Slf4j
@Service
public class AlertApiImpl implements AlertApi {

    @Resource
    private AlertConfigService alertConfigService;

    @Resource
    private ChannelTemplateService channelTemplateService;

    @Resource
    private ChannelConfigService channelConfigService;

    @Override
    public PageResult<AlertConfig> getAlertConfigPage(AlertConfigPageReqVO reqVO) {
        return alertConfigService.getAlertConfigPage(reqVO);
    }

    @Override
    public Message getNotifyMessage(AlertConfig alertConfig) {
        ChannelTemplate channelTemplate = channelTemplateService.getChannelTemplate(alertConfig.getMessageTemplateId());
        if (Objects.isNull(channelTemplate)) {
            return null;
        }
        Long channelConfigId = channelTemplate.getChannelConfigId();

        Message message = Message.builder()
                .content(channelTemplate.getContent())
                .alertConfigId(alertConfig.getId())
                .build();

        if (channelConfigId != null) {
            ChannelConfig channelConfig = channelConfigService.getChannelConfig(channelTemplate.getChannelConfigId());
            if(Objects.isNull(channelConfig)){
                log.warn("告警通道配置丢失");
                return null;
            }
            message.setChannelCode(channelConfig.getCode());
            message.setChannelConfig(channelConfig.getParam());
        }
        return message;
    }
}
