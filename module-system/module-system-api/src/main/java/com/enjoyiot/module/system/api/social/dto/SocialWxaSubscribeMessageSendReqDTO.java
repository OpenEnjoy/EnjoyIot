
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

import com.enjoyiot.framework.common.enums.UserTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信小程序订阅消息发送 Request DTO
 *
 * @author HUIHUI
 */
@Data
public class SocialWxaSubscribeMessageSendReqDTO {

    /**
     * 用户编号
     *
     * 关联 MemberUserDO 的 id 编号
     * 关联 AdminUserDO 的 id 编号
     */
    @NotNull(message = "用户编号不能为空")
    private Long userId;
    /**
     * 用户类型
     *
     * 关联 {@link UserTypeEnum}
     */
    @NotNull(message = "用户类型不能为空")
    private Integer userType;

    /**
     * 消息模版标题
     */
    @NotEmpty(message = "消息模版标题不能为空")
    private String templateTitle;

    /**
     * 点击模板卡片后的跳转页面，仅限本小程序内的页面
     *
     * 支持带参数，（示例 index?foo=bar ）。该字段不填则模板无跳转。
     */
    private String page;

    /**
     * 模板内容的参数
     */
    private Map<String, String> messages;

    public SocialWxaSubscribeMessageSendReqDTO addMessage(String key, String value) {
        if (messages == null) {
            messages = new HashMap<>();
        }
        messages.put(key, value);
        return this;
    }

}
