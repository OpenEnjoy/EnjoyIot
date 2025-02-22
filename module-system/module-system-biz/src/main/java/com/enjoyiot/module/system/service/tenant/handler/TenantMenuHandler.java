
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
package com.enjoyiot.module.system.service.tenant.handler;

import java.util.Set;

/**
 * 租户菜单处理
 * 目的：尽量减少租户逻辑耦合到系统中
 *
 * @author EnjoyIot
 */
public interface TenantMenuHandler {

    /**
     * 基于传入的租户菜单【全】列表，进行相关逻辑的执行
     * 例如说，返回可分配菜单的时候，可以移除多余的
     *
     * @param menuIds 菜单列表
     */
    void handle(Set<Long> menuIds);

}
