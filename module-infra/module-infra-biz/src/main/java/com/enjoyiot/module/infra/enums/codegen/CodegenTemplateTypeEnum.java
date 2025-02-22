
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
package com.enjoyiot.module.infra.enums.codegen;

import com.enjoyiot.framework.common.util.object.ObjectUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 代码生成模板类型
 *
 * @author EnjoyIot
 */
@AllArgsConstructor
@Getter
public enum CodegenTemplateTypeEnum {

    ONE(1), // 单表（增删改查）
    TREE(2), // 树表（增删改查）

    MASTER_NORMAL(10), // 主子表 - 主表 - 普通模式
    MASTER_ERP(11), // 主子表 - 主表 - ERP 模式
    MASTER_INNER(12), // 主子表 - 主表 - 内嵌模式
    SUB(15), // 主子表 - 子表
    ;

    /**
     * 类型
     */
    private final Integer type;

    /**
     * 是否为主表
     *
     * @param type 类型
     * @return 是否主表
     */
    public static boolean isMaster(Integer type) {
        return ObjectUtils.equalsAny(type,
                MASTER_NORMAL.type, MASTER_ERP.type, MASTER_INNER.type);
    }

    /**
     * 是否为树表
     *
     * @param type 类型
     * @return 是否树表
     */
    public static boolean isTree(Integer type) {
        return Objects.equals(type, TREE.type);
    }

}
