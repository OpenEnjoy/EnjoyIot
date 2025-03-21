
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
package com.enjoyiot.module.infra.controller.admin.logger.vo.apiaccesslog;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import com.enjoyiot.framework.excel.core.annotations.DictFormat;
import com.enjoyiot.framework.excel.core.convert.DictConvert;
import com.enjoyiot.module.system.enums.DictTypeConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - API 访问日志 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ApiAccessLogRespVO {

    @Schema(description = "日志主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @ExcelProperty("日志主键")
    private Long id;

    @Schema(description = "链路追踪编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "66600cb6-7852-11eb-9439-0242ac130002")
    @ExcelProperty("链路追踪编号")
    private String traceId;

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "666")
    @ExcelProperty("用户编号")
    private Long userId;

    @Schema(description = "用户类型，参见 UserTypeEnum 枚举", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "用户类型", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.USER_TYPE)
    private Integer userType;

    @Schema(description = "应用名", requiredMode = Schema.RequiredMode.REQUIRED, example = "dashboard")
    @ExcelProperty("应用名")
    private String applicationName;

    @Schema(description = "请求方法名", requiredMode = Schema.RequiredMode.REQUIRED, example = "GET")
    @ExcelProperty("请求方法名")
    private String requestMethod;

    @Schema(description = "请求地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "/xxx/yyy")
    @ExcelProperty("请求地址")
    private String requestUrl;

    @Schema(description = "请求参数")
    @ExcelProperty("请求参数")
    private String requestParams;

    @Schema(description = "响应结果")
    @ExcelProperty("响应结果")
    private String responseBody;

    @Schema(description = "用户 IP", requiredMode = Schema.RequiredMode.REQUIRED, example = "127.0.0.1")
    @ExcelProperty("用户 IP")
    private String userIp;

    @Schema(description = "浏览器 UA", requiredMode = Schema.RequiredMode.REQUIRED, example = "Mozilla/5.0")
    @ExcelProperty("浏览器 UA")
    private String userAgent;

    @Schema(description = "操作模块", requiredMode = Schema.RequiredMode.REQUIRED, example = "商品模块")
    @ExcelProperty("操作模块")
    private String operateModule;

    @Schema(description = "操作名", requiredMode = Schema.RequiredMode.REQUIRED, example = "创建商品")
    @ExcelProperty("操作名")
    private String operateName;

    @Schema(description = "操作分类", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "操作分类", converter = DictConvert.class)
    @DictFormat(com.enjoyiot.module.infra.enums.DictTypeConstants.OPERATE_TYPE)
    private Integer operateType;

    @Schema(description = "开始请求时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("开始请求时间")
    private LocalDateTime beginTime;

    @Schema(description = "结束请求时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("结束请求时间")
    private LocalDateTime endTime;

    @Schema(description = "执行时长", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    @ExcelProperty("执行时长")
    private Integer duration;

    @Schema(description = "结果码", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @ExcelProperty("结果码")
    private Integer resultCode;

    @Schema(description = "结果提示", example = "EnjoyIot，牛逼！")
    @ExcelProperty("结果提示")
    private String resultMsg;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
