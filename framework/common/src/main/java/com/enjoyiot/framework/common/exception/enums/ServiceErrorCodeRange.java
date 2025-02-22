
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
package com.enjoyiot.framework.common.exception.enums;

/**
 * 业务异常的错误码区间，解决：解决各模块错误码定义，避免重复，在此只声明不做实际使用
 *
 * 一共 10 位，分成四段
 *
 * 第一段，1 位，类型
 *      1 - 业务级别异常
 *      x - 预留
 * 第二段，3 位，系统类型
 *      001 - 用户系统
 *      002 - 商品系统
 *      003 - 订单系统
 *      004 - 支付系统
 *      005 - 优惠劵系统
 *      ... - ...
 * 第三段，3 位，模块
 *      不限制规则。
 *      一般建议，每个系统里面，可能有多个模块，可以再去做分段。以用户系统为例子：
 *          001 - OAuth2 模块
 *          002 - User 模块
 *          003 - MobileCode 模块
 * 第四段，3 位，错误码
 *       不限制规则。
 *       一般建议，每个模块自增。
 *
 * @author EnjoyIot
 */
public class ServiceErrorCodeRange {

    // 模块 infra 错误码区间 [1-001-000-000 ~ 1-002-000-000)
    // 模块 system 错误码区间 [1-002-000-000 ~ 1-003-000-000)
    // 模块 report 错误码区间 [1-003-000-000 ~ 1-004-000-000)
    // 模块 member 错误码区间 [1-004-000-000 ~ 1-005-000-000)
    // 模块 mp 错误码区间 [1-006-000-000 ~ 1-007-000-000)
    // 模块 pay 错误码区间 [1-007-000-000 ~ 1-008-000-000)
    // 模块 bpm 错误码区间 [1-009-000-000 ~ 1-010-000-000)

    // 模块 product 错误码区间 [1-008-000-000 ~ 1-009-000-000)
    // 模块 trade 错误码区间 [1-011-000-000 ~ 1-012-000-000)
    // 模块 promotion 错误码区间 [1-013-000-000 ~ 1-014-000-000)

    // 模块 crm 错误码区间 [1-020-000-000 ~ 1-021-000-000)

    // 模块 ai 错误码区间 [1-022-000-000 ~ 1-023-000-000)

}
