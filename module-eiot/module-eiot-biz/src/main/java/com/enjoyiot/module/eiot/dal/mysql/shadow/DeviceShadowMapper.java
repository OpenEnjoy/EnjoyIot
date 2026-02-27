/*
 * Copyright [2025] [Enjoy-iot]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.enjoyiot.module.eiot.dal.mysql.shadow;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.mybatis.core.mapper.BaseMapperX;
import com.enjoyiot.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.enjoyiot.module.eiot.controller.admin.shadow.vo.DeviceShadowPageReqVO;
import com.enjoyiot.module.eiot.dal.dataobject.shadow.DeviceShadowDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 设备影子 Mapper
 *
 * @author EnjoyIot
 */
@Mapper
public interface DeviceShadowMapper extends BaseMapperX<DeviceShadowDO> {

    default PageResult<DeviceShadowDO> selectPage(DeviceShadowPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DeviceShadowDO>()
                .eqIfPresent(DeviceShadowDO::getDeviceId, reqVO.getDeviceId())
                .eqIfPresent(DeviceShadowDO::getProductKey, reqVO.getProductKey())
                .likeIfPresent(DeviceShadowDO::getDn, reqVO.getDn())
                .orderByDesc(DeviceShadowDO::getUpdateTime));
    }

    default DeviceShadowDO selectByDeviceId(Long deviceId) {
        return selectOne(DeviceShadowDO::getDeviceId, deviceId);
    }

    default DeviceShadowDO selectByProductKeyAndDn(String productKey, String dn) {
        return selectOne(new LambdaQueryWrapper<DeviceShadowDO>()
                .eq(DeviceShadowDO::getProductKey, productKey)
                .eq(DeviceShadowDO::getDn, dn));
    }

    /**
     * 带乐观锁的更新方法
     * WHERE id = ? AND version < ?
     * 确保当前版本小于传入版本时才更新
     */
    default int updateByIdWithVersion(DeviceShadowDO shadow, Long newVersion) {
        return update(shadow, new LambdaQueryWrapper<DeviceShadowDO>()
                .eq(DeviceShadowDO::getId, shadow.getId())
                .lt(DeviceShadowDO::getVersion, newVersion));  // 当前版本 < 新版本
    }
}
