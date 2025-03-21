
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
package com.enjoyiot.module.system.controller.admin.sms.vo.log;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import com.enjoyiot.framework.excel.core.annotations.DictFormat;
import com.enjoyiot.framework.excel.core.convert.DictConvert;
import com.enjoyiot.framework.excel.core.convert.JsonConvert;
import com.enjoyiot.module.system.enums.DictTypeConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Schema(description = "管理后台 - 短信日志 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SmsLogRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "短信渠道编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @ExcelProperty("短信渠道编号")
    private Long channelId;

    @Schema(description = "短信渠道编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "ALIYUN")
    @ExcelProperty("短信渠道编码")
    private String channelCode;

    @Schema(description = "模板编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "20")
    @ExcelProperty("模板编号")
    private Long templateId;

    @Schema(description = "模板编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "test-01")
    @ExcelProperty("模板编码")
    private String templateCode;

    @Schema(description = "短信类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "短信类型", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.SMS_TEMPLATE_TYPE)
    private Integer templateType;

    @Schema(description = "短信内容", requiredMode = Schema.RequiredMode.REQUIRED, example = "你好，你的验证码是 1024")
    @ExcelProperty("短信内容")
    private String templateContent;

    @Schema(description = "短信参数", requiredMode = Schema.RequiredMode.REQUIRED, example = "name,code")
    @ExcelProperty(value = "短信参数", converter = JsonConvert.class)
    private Map<String, Object> templateParams;

    @Schema(description = "短信 API 的模板编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "SMS_207945135")
    @ExcelProperty("短信 API 的模板编号")
    private String apiTemplateId;

    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED, example = "15601691300")
    @ExcelProperty("手机号")
    private String mobile;

    @Schema(description = "用户编号", example = "10")
    @ExcelProperty("用户编号")
    private Long userId;

    @Schema(description = "用户类型", example = "1")
    @ExcelProperty(value = "用户类型", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.USER_TYPE)
    private Integer userType;

    @Schema(description = "发送状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "发送状态", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.SMS_SEND_STATUS)
    private Integer sendStatus;

    @Schema(description = "发送时间")
    @ExcelProperty("发送时间")
    private LocalDateTime sendTime;

    @Schema(description = "短信 API 发送结果的编码", example = "SUCCESS")
    @ExcelProperty("短信 API 发送结果的编码")
    private String apiSendCode;

    @Schema(description = "短信 API 发送失败的提示", example = "成功")
    @ExcelProperty("短信 API 发送失败的提示")
    private String apiSendMsg;

    @Schema(description = "短信 API 发送返回的唯一请求 ID", example = "3837C6D3-B96F-428C-BBB2-86135D4B5B99")
    @ExcelProperty("短信 API 发送返回的唯一请求 ID")
    private String apiRequestId;

    @Schema(description = "短信 API 发送返回的序号", example = "62923244790")
    @ExcelProperty("短信 API 发送返回的序号")
    private String apiSerialNo;

    @Schema(description = "接收状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @ExcelProperty(value = "接收状态", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.SMS_RECEIVE_STATUS)
    private Integer receiveStatus;

    @Schema(description = "接收时间")
    @ExcelProperty("接收时间")
    private LocalDateTime receiveTime;

    @Schema(description = "API 接收结果的编码", example = "DELIVRD")
    @ExcelProperty("API 接收结果的编码")
    private String apiReceiveCode;

    @Schema(description = "API 接收结果的说明", example = "用户接收成功")
    @ExcelProperty("API 接收结果的说明")
    private String apiReceiveMsg;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
