
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
package com.enjoyiot.module.eiot.controller.admin.sip.vo;

import com.enjoyiot.module.eiot.api.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 监控设备关联对象 iot_sip_relation
 *

 */
@Schema(description = "监控设备关联 iot_sip_relation")
@Data
@EqualsAndHashCode(callSuper = true)
public class SipRelation extends BaseModel {
    private static final long serialVersionUID = 1L;

    /**
     * 业务id
     */
    private Long id;

    /**
     * 监控设备编号
     */

    @Schema(description = "监控设备编号")
    private String channelId;

    @Schema(description = "通道名称")

    private String channelName;

    @Schema(description = "产品型号")

    private String model;

    /**
     * 关联的设备id
     */

    @Schema(description = "关联的设备id")
    private Long reDeviceId;

    /**
     * 关联的场景id
     */

    @Schema(description = "关联的场景id")
    private Long reSceneModelId;

    @Schema(description = "监控设备id")
    private Long deviceId;

    @Schema(description = "sip设备编号")
    private String deviceSipId;

    @Schema(description = "通道状态")
    private Integer status;

}
