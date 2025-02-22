
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
/**
 * 对于工具类的选择，优先查找 Hutool 中有没对应的方法
 * 如果没有，则自己封装对应的工具类，以 Utils 结尾，用于区分
 *
 * ps：如果担心 Hutool 存在坑的问题，可以阅读 Hutool 的实现源码，以确保可靠性。并且，可以补充相关的单元测试。
 */
package com.enjoyiot.framework.common.util;
