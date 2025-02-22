
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
package com.enjoyiot.module.system.dal.dataobject.sms;

import com.enjoyiot.framework.common.enums.UserTypeEnum;
import com.enjoyiot.framework.mybatis.core.dataobject.BaseDO;
import com.enjoyiot.module.system.enums.sms.SmsReceiveStatusEnum;
import com.enjoyiot.module.system.enums.sms.SmsSendStatusEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 短信日志 DO
 *
 * @author zzf
 * @since 2021-01-25
 */
@TableName(value = "system_sms_log", autoResultMap = true)
@KeySequence("system_sms_log_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmsLogDO extends BaseDO {

    /**
     * 自增编号
     */
    private Long id;

    // ========= 渠道相关字段 =========

    /**
     * 短信渠道编号
     *
     * 关联 {@link SmsChannelDO#getId()}
     */
    private Long channelId;
    /**
     * 短信渠道编码
     *
     * 冗余 {@link SmsChannelDO#getCode()}
     */
    private String channelCode;

    // ========= 模板相关字段 =========

    /**
     * 模板编号
     *
     * 关联 {@link SmsTemplateDO#getId()}
     */
    private Long templateId;
    /**
     * 模板编码
     *
     * 冗余 {@link SmsTemplateDO#getCode()}
     */
    private String templateCode;
    /**
     * 短信类型
     *
     * 冗余 {@link SmsTemplateDO#getType()}
     */
    private Integer templateType;
    /**
     * 基于 {@link SmsTemplateDO#getContent()} 格式化后的内容
     */
    private String templateContent;
    /**
     * 基于 {@link SmsTemplateDO#getParams()} 输入后的参数
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> templateParams;
    /**
     * 短信 API 的模板编号
     *
     * 冗余 {@link SmsTemplateDO#getApiTemplateId()}
     */
    private String apiTemplateId;

    // ========= 手机相关字段 =========

    /**
     * 手机号
     */
    private String mobile;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 用户类型
     *
     * 枚举 {@link UserTypeEnum}
     */
    private Integer userType;

    // ========= 发送相关字段 =========

    /**
     * 发送状态
     *
     * 枚举 {@link SmsSendStatusEnum}
     */
    private Integer sendStatus;
    /**
     * 发送时间
     */
    private LocalDateTime sendTime;
    /**
     * 短信 API 发送结果的编码
     *
     * 由于第三方的错误码可能是字符串，所以使用 String 类型
     */
    private String apiSendCode;
    /**
     * 短信 API 发送失败的提示
     */
    private String apiSendMsg;
    /**
     * 短信 API 发送返回的唯一请求 ID
     *
     * 用于和短信 API 进行定位于排错
     */
    private String apiRequestId;
    /**
     * 短信 API 发送返回的序号
     *
     * 用于和短信 API 平台的发送记录关联
     */
    private String apiSerialNo;

    // ========= 接收相关字段 =========

    /**
     * 接收状态
     *
     * 枚举 {@link SmsReceiveStatusEnum}
     */
    private Integer receiveStatus;
    /**
     * 接收时间
     */
    private LocalDateTime receiveTime;
    /**
     * 短信 API 接收结果的编码
     */
    private String apiReceiveCode;
    /**
     * 短信 API 接收结果的提示
     */
    private String apiReceiveMsg;

}
