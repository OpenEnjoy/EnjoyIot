
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
import com.enjoyiot.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.enjoyiot.module.eiot.api.enums.ErrorCodeConstants;
import com.enjoyiot.module.eiot.controller.admin.channelconfig.vo.ChannelConfig;
import com.enjoyiot.module.eiot.controller.admin.channelconfig.vo.ChannelConfigPageReqVO;
import com.enjoyiot.module.eiot.controller.admin.channelconfig.vo.ChannelConfigReqVO;
import com.enjoyiot.module.eiot.controller.admin.channeltemplate.vo.ChannelTemplate;
import com.enjoyiot.module.eiot.convert.ChannelConfigConvert;
import com.enjoyiot.module.eiot.dal.dataobject.channelconfig.ChannelConfigDO;
import com.enjoyiot.module.eiot.dal.mysql.channelconfig.ChannelConfigMapper;
import com.enjoyiot.module.eiot.dal.mysql.channeltemplate.ChannelTemplateMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 * 通道配置 Service 实现类
 *
 * @author EnjoyIot
 */
@Service
@Validated
public class ChannelConfigServiceImpl implements ChannelConfigService {

    @Resource
    private ChannelConfigMapper channelConfigMapper;

    @Resource
    private ChannelTemplateMapper channelTemplateMapper;

    @Override
    public Long createChannelConfig(ChannelConfig createReqVO) {
        // 插入
        ChannelConfigDO channelConfig = BeanUtils.toBean(createReqVO, ChannelConfigDO.class);
        channelConfigMapper.insert(channelConfig);
        // 返回
        return channelConfig.getId();
    }

    @Override
    public boolean updateChannelConfig(ChannelConfig updateReqVO) {
        // 校验存在
        validateChannelConfigExists(updateReqVO.getId());
        // 更新
        ChannelConfigDO updateObj = BeanUtils.toBean(updateReqVO, ChannelConfigDO.class);
        return channelConfigMapper.updateById(updateObj) > 0;
    }

    @Override
    public void deleteChannelConfig(Long id) {
        // 校验存在
        validateChannelConfigExists(id);
        // 检测通道配置是否已经被使用
        if(channelTemplateMapper.selectCountByConfigId(id)>0){
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.CHANNEL_CONFIG_USED);
        }
        // 删除
        channelConfigMapper.deleteById(id);
    }

    @Override
    public ChannelConfig getChannelConfig(Long id) {
        return ChannelConfigConvert.INSTANCE.convert(channelConfigMapper.selectById(id));
    }

    @Override
    public PageResult<ChannelConfig> getChannelConfigPage(ChannelConfigPageReqVO pageReqVO) {
        return ChannelConfigConvert.INSTANCE.convertPage(channelConfigMapper.selectPage(pageReqVO));
    }

    @Override
    public List<ChannelConfig> getChannelConfigAll(ChannelConfigReqVO reqVO) {
        LambdaQueryWrapperX<ChannelConfigDO> q = new LambdaQueryWrapperX<>();
        q.likeIfPresent(ChannelConfigDO::getTitle, reqVO.getTitle());

        return ChannelConfigConvert.INSTANCE.convertList(channelConfigMapper.selectList(q));
    }

    private void validateChannelConfigExists(Long id) {
        if (channelConfigMapper.selectById(id) == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.CHANNEL_CONFIG_NOT_EXISTS);
        }
    }
}
