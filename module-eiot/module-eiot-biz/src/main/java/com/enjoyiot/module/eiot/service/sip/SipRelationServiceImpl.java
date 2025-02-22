
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
import com.enjoyiot.module.eiot.convert.SipRelationConvert;
import com.enjoyiot.module.eiot.dal.dataobject.sip.SipRelationDO;
import com.enjoyiot.module.eiot.dal.mysql.sip.SipRelationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 监控设备关联Service业务层处理

 */
@Service
public class SipRelationServiceImpl implements SipRelationService
{
    @Resource
    private SipRelationMapper sipRelationMapper;

    /**
     * 查询监控设备关联
     *
     * @param id 监控设备关联主键
     * @return 监控设备关联
     */
    @Override
    public SipRelation selectSipRelationById(Long id)
    {
        return SipRelationConvert.INSTANCE.convert(sipRelationMapper.selectById(id));
    }

    @Override
    public List<SipRelation> selectSipRelationByDeviceId(Long deviceId) {
        SipRelation sipRelation = new SipRelation();
        sipRelation.setReDeviceId(deviceId);
        return sipRelationMapper.selectSipRelationList(sipRelation);
    }

    /**
     * 查询监控设备关联列表
     *
     * @param sipRelation 监控设备关联
     * @return 监控设备关联
     */
    @Override
    public List<SipRelation> selectSipRelationList(SipRelation sipRelation)
    {
        return sipRelationMapper.selectSipRelationList(sipRelation);
    }

    /**
     * 根据channelId获取关联关系
     * @param channelId
     * @return
     */
    @Override
     public SipRelation selectByChannelId(String channelId){
        return SipRelationConvert.INSTANCE.convert(sipRelationMapper.selectByChannelId(channelId));
     }

    /**
     * 新增或者更新监控设备关联
     * @param sipRelation
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addOrUpdateSipRelation(SipRelation sipRelation){
        String channelId = sipRelation.getChannelId();
        assert !Objects.isNull(channelId) : "channelId is null";
        SipRelationDO selectObj = sipRelationMapper.selectByChannelId(channelId);
        if (Objects.isNull(selectObj)){
            //新增

            return this.insertSipRelation(sipRelation);
        }else {

            return sipRelationMapper.updateByChannelId(sipRelation);
        }
    }

    /**
     * 新增监控设备关联
     *
     * @param sipRelation 监控设备关联
     * @return 结果
     */
    @Override
    public int insertSipRelation(SipRelation sipRelation)
    {
        SipRelationDO convert = SipRelationConvert.INSTANCE.convertDO(sipRelation);
        return sipRelationMapper.insert(convert);
    }

    /**
     * 修改监控设备关联
     *
     * @param sipRelation 监控设备关联
     * @return 结果
     */
    @Override
    public int updateSipRelation(SipRelation sipRelation)
    {
        SipRelationDO convert = SipRelationConvert.INSTANCE.convertDO(sipRelation);

        return sipRelationMapper.updateById(convert);
    }


    /**
     * 批量删除监控设备关联
     *
     * @param ids 需要删除的监控设备关联主键
     * @return 结果
     */
    @Override
    public int deleteSipRelationByIds(Long[] ids)
    {
        return sipRelationMapper.deleteByIds(Arrays.asList(ids));
    }

    /**
     * 删除监控设备关联信息
     *
     * @param id 监控设备关联主键
     * @return 结果
     */
    @Override
    public int deleteSipRelationById(Long id)
    {
        return sipRelationMapper.deleteById(id);
    }

    @Override
    public PageResult<SipRelation> selectSipRelationPage(SipRelationPageReqVO sipRelation) {
        return sipRelationMapper.selectPage(sipRelation);
    }
}
