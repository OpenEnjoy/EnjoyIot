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
package com.enjoyiot.module.eiot.service.alert;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.enjoyiot.module.eiot.api.alert.dto.SmsConfig;
import com.enjoyiot.framework.common.exception.util.ServiceExceptionUtil;
import com.enjoyiot.framework.common.util.json.JsonUtils;
import com.enjoyiot.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.enjoyiot.framework.tenant.core.aop.TenantIgnore;
import com.enjoyiot.module.eiot.api.enums.ErrorCodeConstants;
import com.enjoyiot.module.eiot.controller.admin.channelconfig.vo.ChannelConfig;
import com.enjoyiot.module.eiot.controller.admin.channeltemplate.vo.ChannelTemplateSaveReqVO;
import com.enjoyiot.module.eiot.dal.dataobject.channelconfig.ChannelConfigDO;
import com.enjoyiot.module.eiot.dal.dataobject.channeltemplate.ChannelTemplateDO;
import com.enjoyiot.module.eiot.dal.mysql.channelconfig.ChannelConfigMapper;
import com.enjoyiot.module.eiot.dal.mysql.channeltemplate.ChannelTemplateMapper;
import com.enjoyiot.module.eiot.service.alert.ChannelConfigService;
import com.enjoyiot.module.eiot.api.alert.ChannelSmsStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 短信模板状态同步服务
 * 定时查询服务商短信模板状态并更新本地记录
 *
 * @author EnjoyIot
 */
@Slf4j
@Service
public class ChannelSmsService {
    private static final String SMS_CONFIG_CODE = "SMS";
    @Resource
    private ChannelSmsStrategy channelSmsStrategy;
    @Resource
    private ChannelTemplateMapper channelTemplateMapper;
    @Resource
    private ChannelConfigMapper channelConfigMapper;
    @Resource
    private ChannelConfigService channelConfigService;


    /**
     * 创建短信模板
     *
     * @param createReqVO
     * @param channelTemplate
     */
    public void createTemplate(ChannelTemplateSaveReqVO createReqVO, ChannelTemplateDO channelTemplate) {
        ChannelConfig channelConfig = channelConfigService.getChannelConfig(createReqVO.getChannelConfigId());
        if (!Objects.equals(channelConfig.getCode(), SMS_CONFIG_CODE)) {
            return;
        }

        SmsConfig smsConfig = JsonUtils.parseObject(channelConfig.getParam(), SmsConfig.class);
        String templateCode = channelSmsStrategy.createSmsTemplate(createReqVO.getContent(), createReqVO.getId(), smsConfig);
        channelTemplate.setTemplateCode(templateCode);
        channelTemplate.setStatus(0); // 待审核状态
    }

    /**
     * 修改短信模板
     *
     * @param updateReqVO
     * @param updateObj
     */
    public void updateTemplate(ChannelTemplateSaveReqVO updateReqVO, ChannelTemplateDO updateObj) {
        ChannelConfig channelConfig = channelConfigService.getChannelConfig(updateReqVO.getChannelConfigId());
        if (!Objects.equals(channelConfig.getCode(), SMS_CONFIG_CODE)) {
            return;
        }

        // 如果模板内容有变化，则需要更新服务商短信模板
        ChannelTemplateDO originalTemplate = channelTemplateMapper.selectById(updateReqVO.getId());
        if (originalTemplate != null && !Objects.equals(originalTemplate.getContent(), updateReqVO.getContent())) {
            // 待审核状态无法更新短信模板
            if (originalTemplate.getStatus() == 0) {
                ServiceExceptionUtil.exception(ErrorCodeConstants.SMS_TEMPLATE_UPDATE_ERROR_IN_AUDIT);
            }
            // 更新服务商短信模板
            SmsConfig smsConfig = JsonUtils.parseObject(channelConfig.getParam(), SmsConfig.class);
            String templateCode = channelSmsStrategy.updateSmsTemplate(updateReqVO.getContent(), originalTemplate.getTemplateCode(), smsConfig);
            updateObj.setTemplateCode(templateCode);
            updateObj.setStatus(0); // 重置为待审核状态
        }
    }

    /**
     * 删除短信模板
     *
     * @param originalTemplate
     */
    public void deleteTemplate(ChannelTemplateDO originalTemplate) {
        ChannelConfig channelConfig = channelConfigService.getChannelConfig(originalTemplate.getChannelConfigId());
        if (!Objects.equals(channelConfig.getCode(), SMS_CONFIG_CODE)) {
            return;
        }

        // 待审核状态无法更新短信模板
        if (originalTemplate.getStatus() == 0) {
            ServiceExceptionUtil.exception(ErrorCodeConstants.SMS_TEMPLATE_DELETE_ERROR_IN_AUDIT);
        }

        // 删除服务商短信模板
        SmsConfig smsConfig = JsonUtils.parseObject(channelConfig.getParam(), SmsConfig.class);
        channelSmsStrategy.deleteSmsTemplate(originalTemplate.getTemplateCode(), smsConfig);
    }

    /**
     * 每5分钟同步一次短信模板状态
     */
    @Scheduled(fixedRate = 5 * 60 * 1000)
    @TenantIgnore
    public void syncSmsTemplateStatus() {
        log.info("开始同步短信模板状态");

        try {
            // 先查询所有SMS类型的通道配置
            LambdaQueryWrapperX<ChannelConfigDO> channelConfigQuery = new LambdaQueryWrapperX<ChannelConfigDO>()
                    .eq(ChannelConfigDO::getCode, "SMS");
            List<ChannelConfigDO> smsChannelConfigs = channelConfigMapper.selectList(channelConfigQuery);

            Map<Long, SmsConfig> smsConfigMap = smsChannelConfigs.stream().collect(Collectors.toMap(ChannelConfigDO::getId,
                    channelConfig -> JsonUtils.parseObject(channelConfig.getParam(), SmsConfig.class)));

            if (smsChannelConfigs.isEmpty()) {
                log.info("没有找到SMS类型的通道配置");
                return;
            }

            // 提取通道配置ID列表
            List<Long> channelConfigIds = smsChannelConfigs.stream()
                    .map(ChannelConfigDO::getId)
                    .collect(Collectors.toList());

            // 查询所有待审核且属于SMS通道的短信模板
            LambdaQueryWrapperX<ChannelTemplateDO> templateQuery = new LambdaQueryWrapperX<ChannelTemplateDO>()
                    .eq(ChannelTemplateDO::getStatus, 0) // 0-待审核状态
                    .in(ChannelTemplateDO::getChannelConfigId, channelConfigIds);

            List<ChannelTemplateDO> templateList = channelTemplateMapper.selectList(templateQuery);
            log.info("找到 {} 个待审核的短信模板", templateList.size());

            // 构建通道配置映射，方便后续使用
            Map<Long, ChannelConfigDO> channelConfigMap = smsChannelConfigs.stream()
                    .collect(Collectors.toMap(ChannelConfigDO::getId, config -> config));

            for (ChannelTemplateDO template : templateList) {
                try {
                    // 获取通道配置信息
                    ChannelConfigDO channelConfigDO = channelConfigMap.get(template.getChannelConfigId());
                    if (channelConfigDO == null) {
                        log.warn("短信模板 {} 对应的通道配置不存在", template.getId());
                        continue;
                    }

                    // 查询服务商短信模板状态
                    Integer templateStatus = channelSmsStrategy.querySmsTemplateStatus(smsConfigMap.get(channelConfigDO.getId()), template.getTemplateCode());
                    if (templateStatus != null && !templateStatus.equals(template.getStatus())) {
                        // 更新本地模板状态
                        channelTemplateMapper.update(null, new LambdaUpdateWrapper<ChannelTemplateDO>()
                                .eq(ChannelTemplateDO::getId, template.getId())
                                .set(ChannelTemplateDO::getStatus, templateStatus));
                        log.info("更新短信模板 {} 状态为 {}", template.getId(), templateStatus);
                    }
                } catch (Exception e) {
                    log.error("处理短信模板 {} 状态时发生错误", template.getId(), e);
                }
            }
        } catch (Exception e) {
            log.error("同步短信模板状态时发生错误", e);
        }

        log.info("短信模板状态同步完成");
    }
}