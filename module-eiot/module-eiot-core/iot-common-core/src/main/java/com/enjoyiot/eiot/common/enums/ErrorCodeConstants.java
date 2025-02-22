
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
package com.enjoyiot.eiot.common.enums;

import com.enjoyiot.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {

    // ========== 产品信息 2_006_001_000 ==========

    ErrorCode PRODUCT_NOT_EXISTS = new ErrorCode(2_007_001_000, "产品不存在");

    // ========== 设备信息 2_006_002_000 ==========
    ErrorCode DEVICE_INFO_NOT_EXISTS = new ErrorCode(2_007_002_000, "设备信息不存在");
    ErrorCode DN_NOT_EXISTS = new ErrorCode(2_007_002_000, "设备信息不存在");
    ErrorCode DEVICE_CLIENT_WRONG = new ErrorCode(2_007_002_000, "client_id异常");
    ErrorCode DN_WRONG = new ErrorCode(2_007_002_000, "DN错误");
    ErrorCode DEVICE_PASSWORD_WRONG = new ErrorCode(2_007_002_000, "密码错误");
    ErrorCode DEVICE_AUTH_EXCEPTION = new ErrorCode(2_007_002_000, "认证异常");

    // ========== 产品物模型 2_006_003_000 ==========
    ErrorCode NOT_EXISTS = new ErrorCode(2_007_003_000, "信息不存在");

    //
    ErrorCode PARAMS_EXCEPTION = new ErrorCode(2_007_002_000, "参数错误");
    ErrorCode DEVICE_ACTION_FAILED = new ErrorCode(2_007_002_000, "设备动作执行失败");
    ErrorCode ROUTER_NOT_EXISTS = new ErrorCode(2_007_002_000, "未找到设备路由");
    ErrorCode COMPONENT_NOT_EXISTS = new ErrorCode(2_007_002_000, "设备组件未找到");

//
    ErrorCode SEND_REQUEST_ERROR = new ErrorCode(2_007_007_000, "发送请求失败");
    ErrorCode DATA_BLANK = new ErrorCode(2_007_007_000, "规则引擎符号为空");

    // 时序数据库数据库
    ErrorCode FILED_DEFINE = new ErrorCode(2_007_007_000, "字段定义异常");
    ErrorCode TABLE_DEFINE = new ErrorCode(2_007_007_000, "表定义异常");
    ErrorCode TABLE_GET = new ErrorCode(2_007_007_000, "表获取异常");
    ErrorCode COLUMN_ADD = new ErrorCode(2_007_007_000, "添加字段异常");
    ErrorCode COLUMN_UPDATE = new ErrorCode(2_007_007_000, "字段修改异常");
    ErrorCode COLUMN_DEL = new ErrorCode(2_007_007_000, "字段删除异常");



}
