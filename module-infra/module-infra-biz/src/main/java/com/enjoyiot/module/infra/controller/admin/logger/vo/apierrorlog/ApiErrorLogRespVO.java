
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
package com.enjoyiot.module.infra.controller.admin.logger.vo.apierrorlog;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import com.enjoyiot.framework.excel.core.annotations.DictFormat;
import com.enjoyiot.framework.excel.core.convert.DictConvert;
import com.enjoyiot.module.infra.enums.DictTypeConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - API 错误日志 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ApiErrorLogRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @ExcelProperty("编号")
    private Integer id;

    @Schema(description = "链路追踪编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "66600cb6-7852-11eb-9439-0242ac130002")
    @ExcelProperty("链路追踪编号")
    private String traceId;

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "666")
    @ExcelProperty("用户编号")
    private Integer userId;

    @Schema(description = "用户类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "用户类型", converter = DictConvert.class)
    @DictFormat(com.enjoyiot.module.system.enums.DictTypeConstants.USER_TYPE)
    private Integer userType;

    @Schema(description = "应用名", requiredMode = Schema.RequiredMode.REQUIRED, example = "dashboard")
    @ExcelProperty("应用名")
    private String applicationName;

    @Schema(description = "请求方法名", requiredMode = Schema.RequiredMode.REQUIRED, example = "GET")
    @ExcelProperty("请求方法名")
    private String requestMethod;

    @Schema(description = "请求地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "/xx/yy")
    @ExcelProperty("请求地址")
    private String requestUrl;

    @Schema(description = "请求参数", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("请求参数")
    private String requestParams;

    @Schema(description = "用户 IP", requiredMode = Schema.RequiredMode.REQUIRED, example = "127.0.0.1")
    @ExcelProperty("用户 IP")
    private String userIp;

    @Schema(description = "浏览器 UA", requiredMode = Schema.RequiredMode.REQUIRED, example = "Mozilla/5.0")
    @ExcelProperty("浏览器 UA")
    private String userAgent;

    @Schema(description = "异常发生时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("异常发生时间")
    private LocalDateTime exceptionTime;

    @Schema(description = "异常名", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("异常名")
    private String exceptionName;

    @Schema(description = "异常导致的消息", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("异常导致的消息")
    private String exceptionMessage;

    @Schema(description = "异常导致的根消息", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("异常导致的根消息")
    private String exceptionRootCauseMessage;

    @Schema(description = "异常的栈轨迹", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("异常的栈轨迹")
    private String exceptionStackTrace;

    @Schema(description = "异常发生的类全名", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("异常发生的类全名")
    private String exceptionClassName;

    @Schema(description = "异常发生的类文件", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("异常发生的类文件")
    private String exceptionFileName;

    @Schema(description = "异常发生的方法名", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("异常发生的方法名")
    private String exceptionMethodName;

    @Schema(description = "异常发生的方法所在行", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("异常发生的方法所在行")
    private Integer exceptionLineNumber;

    @Schema(description = "处理状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @ExcelProperty(value = "处理状态", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.API_ERROR_LOG_PROCESS_STATUS)
    private Integer processStatus;

    @Schema(description = "处理时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("处理时间")
    private LocalDateTime processTime;

    @Schema(description = "处理用户编号", example = "233")
    @ExcelProperty("处理用户编号")
    private Integer processUserId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
