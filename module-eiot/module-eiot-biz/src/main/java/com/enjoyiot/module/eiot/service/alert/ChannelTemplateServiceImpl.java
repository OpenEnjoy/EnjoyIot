
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

import com.enjoyiot.framework.common.exception.util.ServiceExceptionUtil;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.common.util.object.BeanUtils;
import com.enjoyiot.module.eiot.api.enums.ErrorCodeConstants;
import com.enjoyiot.module.eiot.controller.admin.channeltemplate.vo.ChannelTemplate;
import com.enjoyiot.module.eiot.controller.admin.channeltemplate.vo.ChannelTemplatePageReqVO;
import com.enjoyiot.module.eiot.controller.admin.channeltemplate.vo.ChannelTemplateSaveReqVO;
import com.enjoyiot.module.eiot.convert.ChannelTemplateConvert;
import com.enjoyiot.module.eiot.dal.dataobject.channelconfig.ChannelConfigDO;
import com.enjoyiot.module.eiot.dal.dataobject.channeltemplate.ChannelTemplateDO;
import com.enjoyiot.module.eiot.dal.mysql.alertconfig.AlertConfigMapper;
import com.enjoyiot.module.eiot.dal.mysql.channelconfig.ChannelConfigMapper;
import com.enjoyiot.module.eiot.dal.mysql.channeltemplate.ChannelTemplateMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * 通道模板 Service 实现类
 *
 * @author EnjoyIot
 */
@Slf4j
@Service
@Validated
public class ChannelTemplateServiceImpl implements ChannelTemplateService {

    @Resource
    private ChannelTemplateMapper channelTemplateMapper;

    @Resource
    private AlertConfigMapper alertConfigMapper;

    @Resource
    private ChannelConfigService channelConfigService;

    @Resource
    private ChannelConfigMapper channelConfigMapper;

    @Resource
    private ChannelSmsService ChannelSmsTemplateService;

    @Override
    public Long createChannelTemplate(ChannelTemplateSaveReqVO createReqVO) {
        ChannelTemplateDO channelTemplate = BeanUtils.toBean(createReqVO, ChannelTemplateDO.class);

        // 短信需要单独处理
        ChannelSmsTemplateService.createTemplate(createReqVO, channelTemplate);

        // 插入
        channelTemplateMapper.insert(channelTemplate);
        // 返回
        return channelTemplate.getId();
    }

    @Override
    public void updateChannelTemplate(ChannelTemplateSaveReqVO updateReqVO) {
        // 校验存在
        validateChannelTemplateExists(updateReqVO.getId());
        // 更新
        ChannelTemplateDO updateObj = BeanUtils.toBean(updateReqVO, ChannelTemplateDO.class);

        // 短信需要单独处理
        ChannelSmsTemplateService.updateTemplate(updateReqVO, updateObj);

        channelTemplateMapper.updateById(updateObj);
    }

    @Override
    public void deleteById(Long id) {
        // 校验存在
        validateChannelTemplateExists(id);
        // 检测是否被使用
        if(alertConfigMapper.selectCountByChannelTemplateId(id)>0){
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.CHANNEL_TEMPLATE_USED);
        }

        // 短信需要单独处理
        ChannelTemplateDO channelTemplateDO = channelTemplateMapper.selectById(id);
        ChannelSmsTemplateService.deleteTemplate(channelTemplateDO);

        // 删除
        channelTemplateMapper.deleteById(id);
    }

    @Override
    public ChannelTemplate getChannelTemplate(Long id) {
        return ChannelTemplateConvert.INSTANCE.convert(channelTemplateMapper.selectById(id));
    }

    @Override
    public PageResult<ChannelTemplate> getChannelTemplatePage(ChannelTemplatePageReqVO pageReqVO) {
        PageResult<ChannelTemplate> pageResult = ChannelTemplateConvert.INSTANCE.convertPage(channelTemplateMapper.selectPage(pageReqVO));
        if (CollectionUtils.isNotEmpty(pageResult.getList())) {
            Set<Long> channelConfigIds = pageResult.getList().stream().map(ChannelTemplate::getChannelConfigId).collect(Collectors.toSet());
            List<ChannelConfigDO> channelConfigDOList = channelConfigMapper.selectByIds(channelConfigIds);
            Map<Long, String> channelConfigCodeMap = channelConfigDOList.stream().collect(Collectors.toMap(ChannelConfigDO::getId, ChannelConfigDO::getCode));
            pageResult.getList().forEach(template -> template.setChannelCode(channelConfigCodeMap.get(template.getChannelConfigId())));
        }
        return pageResult;
    }

    private void validateChannelTemplateExists(Long id) {
        if (channelTemplateMapper.selectById(id) == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.NOT_EXISTS);
        }
    }
}
