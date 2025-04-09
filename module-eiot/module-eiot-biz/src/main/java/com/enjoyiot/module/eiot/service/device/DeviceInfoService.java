
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
package com.enjoyiot.module.eiot.service.device;

import com.enjoyiot.framework.common.pojo.CommonResult;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.eiot.api.device.dto.DeviceInfo;
import com.enjoyiot.module.eiot.api.device.dto.DevicePropertyCache;
import com.enjoyiot.module.eiot.api.device.dto.DeviceShortInfo;
import com.enjoyiot.module.eiot.api.device.dto.RegisterDevice;
import com.enjoyiot.module.eiot.controller.admin.device.vo.DeviceBindReqVO;
import com.enjoyiot.module.eiot.controller.admin.device.vo.DeviceInfoImportVo;
import com.enjoyiot.module.eiot.controller.admin.device.vo.DeviceInfoPageReqVO;
import com.enjoyiot.module.eiot.controller.admin.device.vo.DeviceInfoSaveReqVO;
import com.enjoyiot.module.eiot.controller.admin.device.vo.devicegroup.DeviceImportRespVO;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


/**
 * 设备信息 Service 接口
 *
 * @author EnjoyIot
 */
public interface DeviceInfoService {

    /**
     * 创建设备信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDeviceInfo(@Valid DeviceInfoSaveReqVO createReqVO);

    /**
     * 更新设备信息
     *
     * @param updateReqVO 更新信息
     */
    void updateDeviceInfo(@Valid DeviceInfoSaveReqVO updateReqVO);

    /**
     * 删除设备信息
     *
     * @param id 编号
     */
    Boolean deleteDeviceInfo(Long id);

    /**
     * 获得设备信息
     *
     * @param id 编号
     * @return 设备信息
     */
    DeviceInfo getDeviceInfo(Long id);

    DeviceInfo getDeviceByPkDnByCache(String pk, String dn);

    DeviceInfo getDeviceInfoFromCache(Long deviceId);

    Map<String, DevicePropertyCache> getPropertiesFromCache(Long deviceId);

    /**
     * 获得设备信息分页
     *
     * @param pageReqVO 分页查询
     * @return 设备信息分页
     */
    PageResult<DeviceShortInfo> getDeviceInfoPage(DeviceInfoPageReqVO pageReqVO);

    DeviceInfo getDeviceBySerialNo(String serialNumber);

    boolean deleteByIds(List<Long> ids);

    DeviceImportRespVO importDevice(List<DeviceInfoImportVo> list, Long productId);

    List<DeviceInfo> findSubDeviceList(String productKey, String deviceName);

    void bindParent(DeviceBindReqVO saveReqVO);

    DeviceInfo registerDevice(RegisterDevice registerDevice);

    long getLastTimeCache(Long deviceId);

    void updateDeviceLastTimeCache(Long deviceId, long lastTime);

    Boolean updateDeviceState(Long deviceId, boolean online);

    void savePropertiesCache(Long deviceId, Map<String, DevicePropertyCache> properties);

    void clearPropertiesCache(String productKey);
}
