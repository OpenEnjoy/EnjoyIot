/*
 *
 *  * | Licensed 未经许可不能去掉「Enjoy-iot」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com
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
package com.enjoyiot.module.eiot.api.product.dto;


import com.enjoyiot.module.eiot.api.TenantModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends TenantModel {

    //定位更新方式，手动/设备上报
    public static final Integer LOCATE_MANUAL = 2;
    public static final Integer LOCATE_DEVICE = 1;

    public static final Integer DN_TYPE_SERIAL = 1;
    public static final Integer DN_TYPE_DN = 2;
    /**
     * 产品id
     */
    private Long id;
    /**
     * 产品名称
     */
    private String name;
    /**
     * 产品分类id
     */
    private Long categoryId;

    private String categoryName;
    /**
     * productKey
     */
    private String productKey;
    /**
     * mcu code
     */
    private String mcuCode;
    /**
     * 功能介绍
     */
    private String remark1;
    /**
     * 图片url
     */
    private String imgUrl;
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态（0启用 1禁用）
     */
    private Integer status;
    /**
     * 设备类型(0 网关设备, 1 网关子设备, 2 直连设备, 3 非联网设备 )
     */
    private Integer nodeType;
    /**
     * 协议code
     */
    private String protocolCode;
    /**
     * 保活时间
     */
    private Long keepAliveTime;
    /**
     * 产品密钥
     */
    private String productSecret;
    /**
     * 是否透传
     */
    private Boolean transparent;

    private Integer locateType;

    public boolean isTransparent() {
        return transparent != null && transparent;
    }


}
