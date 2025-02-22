
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
package com.enjoyiot.module.infra.service.db;

import com.baomidou.mybatisplus.generator.config.po.TableInfo;

import java.util.List;

/**
 * 数据库表 Service
 *
 * @author EnjoyIot
 */
public interface DatabaseTableService {

    /**
     * 获得表列表，基于表名称 + 表描述进行模糊匹配
     *
     * @param dataSourceConfigId 数据源配置的编号
     * @param nameLike 表名称，模糊匹配
     * @param commentLike 表描述，模糊匹配
     * @return 表列表
     */
    List<TableInfo> getTableList(Long dataSourceConfigId, String nameLike, String commentLike);

    /**
     * 获得指定表名
     *
     * @param dataSourceConfigId 数据源配置的编号
     * @param tableName 表名称
     * @return 表
     */
    TableInfo getTable(Long dataSourceConfigId, String tableName);

}
