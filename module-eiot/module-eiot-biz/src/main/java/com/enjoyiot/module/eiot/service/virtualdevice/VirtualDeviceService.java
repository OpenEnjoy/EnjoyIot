
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


import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.eiot.api.virtualdevice.dto.VirtualDevice;
import com.enjoyiot.module.eiot.api.virtualdevice.dto.VirtualDeviceLog;
import com.enjoyiot.module.eiot.controller.admin.virtualdevice.vo.EiotVirtualDeviceSaveReqVO;
import com.enjoyiot.module.eiot.controller.admin.virtualdevice.vo.EiotVirtualSaveDevicesMappingVo;
import com.enjoyiot.module.eiot.controller.admin.virtualdevice.vo.EiotVirtualSaveScriptVo;
import com.enjoyiot.module.eiot.controller.admin.virtualdevice.vo.VirtualDevicePageReqVO;

import java.util.List;

/**
 * 虚拟设备服务
 *
 * @author clickear@163.com
 */
public interface VirtualDeviceService {

    /**
     * 添加虚拟设备
     *
     * @param virtualDevice
     * @return 虚拟设备id
     */
    Long saveVirtualDevice(EiotVirtualDeviceSaveReqVO virtualDevice);

    /**
     * 更新虚拟设备
     *
     * @param virtualDevice
     */
    void updateVirtualDevice(VirtualDevice virtualDevice);

    /**
     * 保存关联信息
     * @param data
     */
    void saveVirtualDeviceMapping(EiotVirtualSaveDevicesMappingVo data);

    /**
     * 更新脚本信息
     * @param saveScriptVo
     */
    void saveScript(EiotVirtualSaveScriptVo saveScriptVo);

    /**
     * 删除虚拟设备
     *
     * @param virtualDeviceId 虚拟设备id
     */
    void deleteVirtualDevice(Long virtualDeviceId);

    /**
     * 获取虚拟设备信息
     *
     * @param virtualDeviceId
     * @return
     */
    VirtualDevice getVirtualDevice(Long virtualDeviceId);

    /**
     * 设置状态
     *
     * @param id
     * @param state
     */
    void setState(Long id, String state);

    /**
     * 手动执行虚拟设备
     *
     * @param id
     */
    void run(Long id);


    /**
     * 根据表达式获取虚拟设备信息
     *
     * @param trigger
     * @param state
     * @return
     */
    List<VirtualDevice> findByTriggerAndState(String trigger, String state);

    /**
     * 分页查询虚拟设备
     * @param reqVO
     * @return
     */
    PageResult<VirtualDevice> selectPage(VirtualDevicePageReqVO reqVO);



    /**
     * 根据虚拟设备id分页查询日志
     *
     * @param virtualDeviceId
     * @param page
     * @param size
     * @return
     */
    PageResult<VirtualDeviceLog> findByVirtualDeviceId(Long virtualDeviceId, int page, int size);


}
