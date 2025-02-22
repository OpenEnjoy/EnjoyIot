
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
package com.enjoyiot.module.infra.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.query.DefaultQuery;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;

import java.util.List;

public class DefaultDatabaseQueryTest {

    public static void main(String[] args) {
//        DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder("jdbc:oracle:thin:@127.0.0.1:1521:xe",
//                "root", "123456").build();
        DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder("jdbc:postgresql://127.0.0.1:5432/ruoyi-vue-pro",
                "root", "123456").build();
//        StrategyConfig strategyConfig = new StrategyConfig.Builder().build();

        ConfigBuilder builder = new ConfigBuilder(null, dataSourceConfig, null, null, null, null);

        DefaultQuery query = new DefaultQuery(builder);

        long time = System.currentTimeMillis();
        List<TableInfo> tableInfos = query.queryTables();
        for (TableInfo tableInfo : tableInfos) {
            if (StrUtil.startWithAny(tableInfo.getName().toLowerCase(), "act_", "flw_", "qrtz_")) {
                continue;
            }
            System.out.println(String.format("CREATE SEQUENCE %s_seq MINVALUE 1;", tableInfo.getName()));
//            System.out.println(String.format("DELETE FROM %s WHERE deleted = '1';", tableInfo.getName()));
        }
        System.out.println(tableInfos.size());
        System.out.println(System.currentTimeMillis() - time);
    }

}
