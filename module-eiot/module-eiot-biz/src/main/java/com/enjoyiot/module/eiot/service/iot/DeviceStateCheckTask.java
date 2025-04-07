
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
package com.enjoyiot.module.eiot.service.iot;


import cn.hutool.core.util.IdUtil;
import com.enjoyiot.eiot.common.constant.Constants;
import com.enjoyiot.eiot.common.enums.DeviceState;
import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.eiot.message.core.MqProducer;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.tenant.core.aop.TenantIgnore;
import com.enjoyiot.module.eiot.api.device.dto.DeviceInfo;
import com.enjoyiot.module.eiot.api.device.dto.DeviceShortInfo;
import com.enjoyiot.module.eiot.api.product.dto.Product;
import com.enjoyiot.module.eiot.controller.admin.device.vo.DeviceInfoPageReqVO;
import com.enjoyiot.module.eiot.service.device.DeviceInfoService;
import com.enjoyiot.module.eiot.service.product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author: EnjoyIot
 * @Date: 2024/12/23 15:16
 * @Version: V1.0
 * @Description: 设备在线状态检测
 */
@Slf4j
@Component
public class DeviceStateCheckTask {

    @Resource
    private ProductService productService;

    @Resource
    private DeviceInfoService deviceInfoService;

    @Resource
    private MqProducer<ThingModelMessage> producer;

    @Scheduled(fixedDelay = 10, initialDelay = 20, timeUnit = TimeUnit.SECONDS)
    @TenantIgnore
    public void syncState() {
        int pn = 1;
        int pageSize = 100;
        DeviceInfoPageReqVO pageReqVO = new DeviceInfoPageReqVO();
        pageReqVO.setPageNo(pn);
        pageReqVO.setPageSize(pageSize);
        while (true) {
            pageReqVO.setState(1);
            pageReqVO.setPageNo(pn);

            //取出数据库中所有在线设备
            // TODO: 从数据库库中取出到redis比较时,会有一定的时间间隔
            PageResult<DeviceShortInfo> result = deviceInfoService.getDeviceInfoPage(pageReqVO);
            if (result.getTotal() == 0) {
                return;
            }
            //判断属性更新时间是否大于产品定义保活时长
            for (DeviceShortInfo device : result.getList()) {
                Product product = productService.getProductByPkFromCache(device.getProductKey());
                Long keepAliveTime = product.getKeepAliveTime();
                // 如果没有设置保活时长或为0，则跳过
                if (keepAliveTime == null || keepAliveTime <= 0) {
                    continue;
                }
                Long deviceId = device.getId();
                long lastTime = deviceInfoService.getLastTimeCache(deviceId);
                //最后更新时间超时保活时长1.1倍认为设备离线了
                if (System.currentTimeMillis() - lastTime > keepAliveTime * 1000 * 1.1) {
                    DeviceInfo realTimeDevice = deviceInfoService.getDeviceInfoFromCache(deviceId);
                    if (!realTimeDevice.isOnline()) {
                        continue;
                    }
                    log.info("device state check offline,{}", deviceId);

                    // 发送设备离线物模型消息
                    sendDeviceOfflineMessage(device);

                }
            }

            if (result.getList().size() < pageSize) {
                break;
            }
            pn++;
        }
    }

    private void sendDeviceOfflineMessage(DeviceShortInfo device) {
        // TODO: 提却到某个公共类中

        ThingModelMessage msg = ThingModelMessage.builder()
                .id(IdUtil.fastSimpleUUID())
                .time(System.currentTimeMillis())
                .dn(device.getDn())
                .productKey(device.getProductKey())
                .occurred(System.currentTimeMillis())
                .deviceId(device.getId())
                .identifier(ThingModelMessage.ID_OFFLINE)
                .type(ThingModelMessage.TYPE_STATE)
                .build();
        producer.publish(Constants.THING_MODEL_MESSAGE_TOPIC, msg);

    }


}
