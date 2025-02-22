
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

import com.enjoyiot.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.enjoyiot.module.eiot.controller.admin.alert.vo.ChannelReqVO;
import com.enjoyiot.module.eiot.controller.admin.channelconfig.vo.Channel;
import com.enjoyiot.module.eiot.convert.ChannelConvert;
import com.enjoyiot.module.eiot.dal.dataobject.ChannelDO;
import com.enjoyiot.module.eiot.dal.mysql.ChannelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 通道配置 Service 接口
 *
 * @author EnjoyIot
 */
@Service
public class ChannelServiceImpl implements ChannelService {

    @Resource
    private ChannelMapper channelMapper;

    public List<Channel> getChannelList(ChannelReqVO reqVO) {
        LambdaQueryWrapperX<ChannelDO> reqVOX = new LambdaQueryWrapperX<>();

        return ChannelConvert.INSTANCE.convertList(channelMapper.selectList(reqVOX));
    }

}
