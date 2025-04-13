
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
package com.enjoyiot.module.eiot.api.device;

import cn.hutool.core.util.ObjectUtil;
import com.enjoyiot.eiot.common.thing.ThingService;
import com.enjoyiot.eiot.common.utils.CodecUtil;
import com.enjoyiot.framework.common.pojo.CommonResult;
import com.enjoyiot.framework.tenant.core.aop.TenantIgnore;
import com.enjoyiot.framework.tenant.core.util.TenantUtils;
import com.enjoyiot.module.eiot.api.device.dto.*;
import com.enjoyiot.module.eiot.api.product.ProductApi;
import com.enjoyiot.module.eiot.api.product.dto.Product;
import com.enjoyiot.module.eiot.service.device.DeviceConfigService;
import com.enjoyiot.module.eiot.service.device.DeviceCtrlService;
import com.enjoyiot.module.eiot.service.device.DeviceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

import static com.enjoyiot.framework.common.exception.enums.GlobalErrorCodeConstants.BAD_REQUEST;

@Slf4j
@Service
public class DeviceApiImpl implements DeviceApi {

    @Resource
    private DeviceInfoService deviceInfoService;

    @Resource
    private DeviceConfigService deviceConfigService;

    @Resource
    private DeviceCtrlService deviceCtrlService;

    @Resource
    private ProductApi productApi;

    @Override
    public DeviceInfo getDeviceByPkDnByCache(String pk, String dn) {
        return TenantUtils.executeIgnoreResult(() -> deviceInfoService.getDeviceByPkDnByCache(pk, dn));
    }

    @Override
    public DeviceInfo getDeviceInfoFromCache(Long deviceId) {
        return TenantUtils.executeIgnoreResult(() -> deviceInfoService.getDeviceInfoFromCache(deviceId));
    }



    @Override
    public DeviceInfo registerDevice(RegisterDevice registerDevice) {
        return TenantUtils.executeIgnoreResult(() -> deviceInfoService.registerDevice(registerDevice));
    }

    @Override
    @TenantIgnore
    public CommonResult<DeviceInfo> auth(DeviceAuth authDTO) {

        return TenantUtils.executeIgnoreResult(()->{
            return doAuth(authDTO);
        });
    }

    @NotNull
    private CommonResult<DeviceInfo> doAuth(DeviceAuth authDTO) {
        String clientId = authDTO.getClientId();
        String[] parts = clientId.split("_");
        String productKey = parts[0];
        String deviceName = parts[1];
        String gwModel = parts[2];
        if (!authDTO.getUserName().equals(deviceName)) {
            log.error("username:{}不正确", deviceName);
            return CommonResult.error(BAD_REQUEST.getCode(), "deviceName不正确");

        }


        DeviceInfo device = deviceInfoService.getDeviceByPkDnByCache(productKey, deviceName);
        if (Objects.isNull(device)) {
            if (!authDTO.isCanRegister()) {
                return CommonResult.error(BAD_REQUEST.getCode(), "设备未注册");
            }
            Product product = productApi.getProduct(productKey);
            if (Objects.isNull(product)) {
                return CommonResult.error(BAD_REQUEST.getCode(), "产品信息不存在");
            }
            String validPasswd = CodecUtil.md5Str(product.getProductSecret() + clientId);
            if (!validPasswd.equalsIgnoreCase(authDTO.getPassword())) {
                log.info("deviceName:{}, validPasswd:{}", deviceName, validPasswd);
                return CommonResult.error(BAD_REQUEST.getCode(), "密码验证识别");
            }

            RegisterDevice registerDeviceDTO = RegisterDevice.builder().deviceName(deviceName).productKey(productKey)
                    .tenantId(authDTO.getTenantId()).build();

            DeviceInfo registerDevice =   this.registerDevice(registerDeviceDTO);
            if(ObjectUtil.isNull(registerDevice)){
                return CommonResult.error(BAD_REQUEST.getCode(), "设备注册失败");
            }
            return CommonResult.success(registerDevice);
        }


        return CommonResult.success(device);
    }
    @Override
    public Map<String, DevicePropertyCache> getPropertiesFromCache(Long deviceId) {
        return TenantUtils.executeIgnoreResult(() -> deviceInfoService.getPropertiesFromCache(deviceId));
    }

    @Override
    public void updateDeviceLastTimeCache(Long deviceId, long lastTime) {
        TenantUtils.executeIgnore(() -> deviceInfoService.updateDeviceLastTimeCache(deviceId, lastTime));
    }

    @Override
    public Boolean updateDeviceState(Long deviceId, boolean online) {
        return TenantUtils.executeIgnoreWithResult(() -> deviceInfoService.updateDeviceState(deviceId, online));
    }

    @Override
    public void savePropertiesCache(Long deviceId, Map<String, DevicePropertyCache> properties) {
        TenantUtils.executeIgnore(() -> deviceInfoService.savePropertiesCache(deviceId, properties));
    }

    @Override
    public void clearPropertiesCache(String productKey) {
        TenantUtils.executeIgnore(() -> deviceInfoService.clearPropertiesCache(productKey));
    }

    @Override
    public DeviceConfig getDeviceConfig(Long deviceId) {
        return TenantUtils.executeIgnoreResult(() -> deviceConfigService.findByDeviceId(deviceId));
    }

    @Override
    public void invoke(ThingService<?> service) {
        TenantUtils.executeIgnore(()->{
            DeviceInfo device = deviceInfoService.getDeviceByPkDnByCache(service.getProductKey(), service.getDn());
            deviceCtrlService.invokeService(device.getId(),service.getIdentifier(), (Map<String, Object>) service.getParams());
        });
    }
}
