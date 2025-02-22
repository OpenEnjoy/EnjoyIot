
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
package com.enjoyiot.module.infra.api.logger.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * API 错误日志
 *
 * @author EnjoyIot
 */
@Data
public class ApiErrorLogCreateReqDTO {

    /**
     * 链路编号
     */
    private String traceId;
    /**
     * 账号编号
     */
    private Long userId;
    /**
     * 用户类型
     */
    private Integer userType;
    /**
     * 应用名
     */
    @NotNull(message = "应用名不能为空")
    private String applicationName;

    /**
     * 请求方法名
     */
    @NotNull(message = "http 请求方法不能为空")
    private String requestMethod;
    /**
     * 访问地址
     */
    @NotNull(message = "访问地址不能为空")
    private String requestUrl;
    /**
     * 请求参数
     */
    @NotNull(message = "请求参数不能为空")
    private String requestParams;
    /**
     * 用户 IP
     */
    @NotNull(message = "ip 不能为空")
    private String userIp;
    /**
     * 浏览器 UA
     */
    @NotNull(message = "User-Agent 不能为空")
    private String userAgent;

    /**
     * 异常时间
     */
    @NotNull(message = "异常时间不能为空")
    private LocalDateTime exceptionTime;
    /**
     * 异常名
     */
    @NotNull(message = "异常名不能为空")
    private String exceptionName;
    /**
     * 异常发生的类全名
     */
    @NotNull(message = "异常发生的类全名不能为空")
    private String exceptionClassName;
    /**
     * 异常发生的类文件
     */
    @NotNull(message = "异常发生的类文件不能为空")
    private String exceptionFileName;
    /**
     * 异常发生的方法名
     */
    @NotNull(message = "异常发生的方法名不能为空")
    private String exceptionMethodName;
    /**
     * 异常发生的方法所在行
     */
    @NotNull(message = "异常发生的方法所在行不能为空")
    private Integer exceptionLineNumber;
    /**
     * 异常的栈轨迹异常的栈轨迹
     */
    @NotNull(message = "异常的栈轨迹不能为空")
    private String exceptionStackTrace;
    /**
     * 异常导致的根消息
     */
    @NotNull(message = "异常导致的根消息不能为空")
    private String exceptionRootCauseMessage;
    /**
     * 异常导致的消息
     */
    @NotNull(message = "异常导致的消息不能为空")
    private String exceptionMessage;


}
