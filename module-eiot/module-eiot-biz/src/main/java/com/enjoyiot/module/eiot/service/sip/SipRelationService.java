
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
package com.enjoyiot.module.eiot.service.sip;


import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.eiot.controller.admin.sip.vo.SipRelation;
import com.enjoyiot.module.eiot.controller.admin.sip.vo.SipRelationPageReqVO;

import java.util.List;

/**
 * 监控设备关联Service接口
 *
 */
public interface SipRelationService
{
    /**
     * 查询监控设备关联
     *
     * @param id 监控设备关联主键
     * @return 监控设备关联
     */
     SipRelation selectSipRelationById(Long id);

     List<SipRelation> selectSipRelationByDeviceId(Long deviceId);
    /**
     * 查询监控设备关联列表
     *
     * @param sipRelation 监控设备关联
     * @return 监控设备关联集合
     */
     List<SipRelation> selectSipRelationList(SipRelation sipRelation);

    /**
     * 根据channelId获取关联关系
     * @param channelId
     * @return
     */
    SipRelation selectByChannelId(String channelId);

    /**
     * 新增或者更新监控设备关联
     * @param sipRelation
     * @return
     */
    int addOrUpdateSipRelation(SipRelation sipRelation);

    /**
     * 新增监控设备关联
     *
     * @param sipRelation 监控设备关联
     * @return 结果
     */
     int insertSipRelation(SipRelation sipRelation);

    /**
     * 修改监控设备关联
     *
     * @param sipRelation 监控设备关联
     * @return 结果
     */
     int updateSipRelation(SipRelation sipRelation);

    /**
     * 批量删除监控设备关联
     *
     * @param ids 需要删除的监控设备关联主键集合
     * @return 结果
     */
     int deleteSipRelationByIds(Long[] ids);

    /**
     * 删除监控设备关联信息
     *
     * @param id 监控设备关联主键
     * @return 结果
     */
     int deleteSipRelationById(Long id);

    PageResult<SipRelation> selectSipRelationPage(SipRelationPageReqVO sipRelation);
}
