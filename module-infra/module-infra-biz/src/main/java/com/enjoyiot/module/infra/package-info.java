
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
 * infra 模块，主要提供两块能力：
 * 1. 我们放基础设施的运维与管理，支撑上层的通用与核心业务。 例如说：定时任务的管理、服务器的信息等等
 * 2. 研发工具，提升研发效率与质量。 例如说：代码生成器、接口文档等等
 *
 * 1. Controller URL：以 /infra/ 开头，避免和其它 Module 冲突
 * 2. DataObject 表名：以 infra_ 开头，方便在数据库中区分
 */
package com.enjoyiot.module.infra;
