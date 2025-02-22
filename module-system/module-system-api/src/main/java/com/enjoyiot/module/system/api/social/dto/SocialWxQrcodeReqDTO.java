
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
package com.enjoyiot.module.system.api.social.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 获取小程序码 Request DTO
 *
 * @author HUIHUI
 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/qrcode-link/qr-code/getUnlimitedQRCode.html">获取不限制的小程序码</a>
 */
@Data
public class SocialWxQrcodeReqDTO {

    /**
     * 页面路径不能携带参数（参数请放在scene字段里）
     */
    public static final String SCENE = "";
    /**
     * 二维码宽度
     */
    public static final Integer WIDTH = 430;
    /**
     * 自动配置线条颜色，如果颜色依然是黑色，则说明不建议配置主色调
     */
    public static final Boolean AUTO_COLOR = true;
    /**
     * 检查 page 是否存在
     */
    public static final Boolean CHECK_PATH = true;
    /**
     * 是否需要透明底色
     *
     * hyaline 为 true 时，生成透明底色的小程序码
     */
    public static final Boolean HYALINE = true;

    /**
     * 场景
     */
    @NotEmpty(message = "场景不能为空")
    private String scene;
    /**
     * 页面路径
     */
    @NotEmpty(message = "页面路径不能为空")
    private String path;
    /**
     * 二维码宽度
     */
    private Integer width;

    /**
     * 是否需要透明底色
     */
    private Boolean autoColor;
    /**
     * 是否检查 page 是否存在
     */
    private Boolean checkPath;
    /**
     * 是否需要透明底色
     */
    private Boolean hyaline;

}
