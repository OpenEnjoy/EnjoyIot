
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
package com.enjoyiot.module.eiot.service.virtualdevice;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.enjoyiot.eiot.IVirtualDeviceLogData;
import com.enjoyiot.eiot.virtualdevice.VirtualManager;
import com.enjoyiot.framework.common.exception.ServiceException;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.enjoyiot.module.eiot.api.virtualdevice.dto.VirtualDevice;
import com.enjoyiot.module.eiot.api.virtualdevice.dto.VirtualDeviceLog;
import com.enjoyiot.module.eiot.controller.admin.virtualdevice.vo.EiotVirtualDeviceSaveReqVO;
import com.enjoyiot.module.eiot.controller.admin.virtualdevice.vo.EiotVirtualSaveDevicesMappingVo;
import com.enjoyiot.module.eiot.controller.admin.virtualdevice.vo.EiotVirtualSaveScriptVo;
import com.enjoyiot.module.eiot.controller.admin.virtualdevice.vo.VirtualDevicePageReqVO;
import com.enjoyiot.module.eiot.convert.VirtualDeviceConvert;
import com.enjoyiot.module.eiot.dal.dataobject.virtualdevice.VirtualDeviceDO;
import com.enjoyiot.module.eiot.dal.dataobject.virtualdevice.VirtualDeviceMappingDO;
import com.enjoyiot.module.eiot.dal.mysql.virtualdevice.EiotVirtualDeviceMapper;
import com.enjoyiot.module.eiot.dal.mysql.virtualdevice.EiotVirtualDeviceMappingMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 虚拟设备实现类
 *
 * @author clickear
 */
@Slf4j
@Service
public class EiotVirtualDeviceServiceImpl implements VirtualDeviceService {

    @Resource
    private EiotVirtualDeviceMapper virtualDeviceMapper;

    @Resource
    private EiotVirtualDeviceMappingMapper virtualDeviceMappingMapper;

    @Resource
    private VirtualManager virtualManager;

    @Autowired
    private IVirtualDeviceLogData virtualDeviceLogData;

    @Override
    public Long saveVirtualDevice(EiotVirtualDeviceSaveReqVO virtualDevice) {
        VirtualDeviceDO obj = VirtualDeviceConvert.INSTANCE.toDo(virtualDevice);
        obj.setState(VirtualDevice.STATE_STOPPED);
        virtualDeviceMapper.insert(obj);
        return obj.getId();
    }

    @Override
    public void updateVirtualDevice(VirtualDevice virtualDevice) {
        validateVirtualDeviceExists(virtualDevice.getId());
        virtualDevice.setState(VirtualDevice.STATE_STOPPED);
        VirtualDeviceDO obj = VirtualDeviceConvert.INSTANCE.toDo(virtualDevice);
        virtualDeviceMapper.updateById(obj);
    }

    @Override
    public void saveVirtualDeviceMapping(EiotVirtualSaveDevicesMappingVo data) {
        //删除旧的添加新的关联设备记录
        virtualDeviceMappingMapper.deleteByVirtualDeviceId(data.getId());

        Set<VirtualDeviceMappingDO> mappingList = data.getDevices().stream()
                .map(deviceId -> {
                    VirtualDeviceMappingDO mapping = new VirtualDeviceMappingDO();
                    mapping.setVirtualDeviceId(data.getId());
                    mapping.setDeviceId(deviceId);
                    return mapping;
                }).collect(Collectors.toSet());
        virtualDeviceMappingMapper.insertBatch(mappingList);
    }

    @Override
    public void saveScript(EiotVirtualSaveScriptVo saveScriptVo) {
        virtualManager.refreshScript(saveScriptVo.getId(), saveScriptVo.getScript());
        // 更新脚本状态
        virtualDeviceMapper.updateScriptById(saveScriptVo);
    }

    @Override
    public void deleteVirtualDevice(Long virtualDeviceId) {
        VirtualDevice oldData = validateVirtualDeviceExists(virtualDeviceId);
        virtualManager.remove(oldData);
        virtualDeviceMapper.deleteById(virtualDeviceId);
        virtualDeviceMappingMapper.deleteByVirtualDeviceId(virtualDeviceId);
    }

    @Override
    public VirtualDevice getVirtualDevice(Long virtualDeviceId) {
        VirtualDevice virtualDevice = validateVirtualDeviceExists(virtualDeviceId);
        virtualDevice.setDevices(getMappingDeviceIdsByVirtualDeviceId(virtualDevice.getId()));
        return virtualDevice;
    }

    @Override
    public void setState(Long id, String state) {
        VirtualDevice oldData = getVirtualDevice(id);
        oldData.setState(state);
        if (VirtualDevice.STATE_RUNNING.equals(state)) {
            virtualManager.add(oldData);
        } else {
            virtualManager.remove(oldData);
        }

        // 更新状态
        LambdaUpdateWrapper<VirtualDeviceDO> up = new LambdaUpdateWrapper<>();
        up.set(VirtualDeviceDO::getState, state);
        up.eq(VirtualDeviceDO::getId, id);
        virtualDeviceMapper.update(up);
    }

    @Override
    public void run(Long id) {
        VirtualDevice virtualDevice = getVirtualDevice(id);
        virtualManager.run(virtualDevice);
    }


    @Override
    public List<VirtualDevice> findByTriggerAndState(String trigger, String state) {
        LambdaQueryWrapperX<VirtualDeviceDO> q = new LambdaQueryWrapperX<>();
        q.eqIfPresent(VirtualDeviceDO::getTrigger, trigger);
        q.eqIfPresent(VirtualDeviceDO::getState, state);
        List<VirtualDevice> virtualDevices = VirtualDeviceConvert.INSTANCE.convertList(virtualDeviceMapper.selectList(q));
        // 可考虑改成批量查询
        for (VirtualDevice virtualDevice : virtualDevices) {
            virtualDevice.setDevices(getMappingDeviceIdsByVirtualDeviceId(virtualDevice.getId()));
        }
        return virtualDevices;
    }

    @Override
    public PageResult<VirtualDevice> selectPage(VirtualDevicePageReqVO reqVO) {
        PageResult<VirtualDeviceDO> result = virtualDeviceMapper.selectPage(reqVO);
        return VirtualDeviceConvert.INSTANCE.convertPage(result);
    }

    @Override
    public PageResult<VirtualDeviceLog> findByVirtualDeviceId(Long virtualDeviceId, int page, int size) {
        return virtualDeviceLogData.findByVirtualDeviceId(virtualDeviceId, page, size);
    }

    /**
     * 校验虚拟设备设备是否存在
     *
     * @param id
     */
    private VirtualDevice validateVirtualDeviceExists(Long id) {
        VirtualDeviceDO virtualDeviceDO = virtualDeviceMapper.selectById(id);
        if (virtualDeviceDO == null) {
            throw new ServiceException(400, "规则不存在");
            // throw exception(ErrorCodeConstants.VIRTUAL_DEVICE_NOT_EXISTS);
        }
        return VirtualDeviceConvert.INSTANCE.convert(virtualDeviceDO);
    }

    private List<Long> getMappingDeviceIdsByVirtualDeviceId(Long virtualId) {
        List<VirtualDeviceMappingDO> deviceMappings = virtualDeviceMappingMapper.findByVirtualDeviceId(virtualId);
        return deviceMappings.stream().map(VirtualDeviceMappingDO::getDeviceId).collect(Collectors.toList());
    }
}
