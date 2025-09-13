/*
 *
 *  * | Licensed 未经许可不能去掉「Enjoy-iot」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: dreamswang2020@foxmail.com
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
package com.enjoyiot.eiot.temporal.kw.service;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.enjoyiot.eiot.IVirtualDeviceLogData;
import com.enjoyiot.eiot.temporal.kw.dao.KwVirtualDeviceLogMapper;
import com.enjoyiot.eiot.temporal.kw.model.KwVirtualDeviceLog;
import com.enjoyiot.framework.common.pojo.PageParam;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.common.util.object.BeanUtils;
import com.enjoyiot.module.eiot.api.virtualdevice.dto.VirtualDeviceLog;
import com.kaiwudb.util.KWTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class VirtualDeviceLogDataImpl implements IVirtualDeviceLogData {

    @Autowired
    private KwVirtualDeviceLogMapper virtualDeviceLogMapper;

    @Override
    public PageResult<VirtualDeviceLog> findByVirtualDeviceId(Long virtualDeviceId, int page, int size) {
        PageParam pageParam = new PageParam();
        pageParam.setPageNo(page);
        pageParam.setPageSize(size);
        PageResult<KwVirtualDeviceLog> result = virtualDeviceLogMapper.selectPage(pageParam,
                Wrappers.lambdaQuery(KwVirtualDeviceLog.class)
                        .eq(KwVirtualDeviceLog::getVirtualDeviceId, virtualDeviceId)
                        .orderByDesc(KwVirtualDeviceLog::getTime)
        );

        return new PageResult<>(result.getList().stream().map(r ->
                        new VirtualDeviceLog(r.getTime().getTime(), virtualDeviceId,
                                r.getVirtualDeviceName(),
                                r.getDeviceTotal(), r.getResult(), r.getTime().getTime()))
                .collect(Collectors.toList()), result.getTotal());
    }

    @Override
    public void add(VirtualDeviceLog log) {
        KwVirtualDeviceLog deviceLog = BeanUtils.toBean(log, KwVirtualDeviceLog.class);
        deviceLog.setTime(new KWTimestamp(System.currentTimeMillis()));
        virtualDeviceLogMapper.insert(deviceLog);
    }
}
