/*
 *
 *  * | Licensed 未经许可不能去掉「Enjoy-iot」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: dreamswang2020@foxmail.com
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2025] [Enjoy-iot]
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
package com.enjoyiot.eiot.temporal.kw.service;


import com.enjoyiot.eiot.IDbStructureData;
import com.enjoyiot.eiot.temporal.kw.config.Constants;
import com.enjoyiot.eiot.temporal.kw.dao.KwJdbcTemplate;
import com.enjoyiot.eiot.temporal.kw.dm.FieldParser;
import com.enjoyiot.eiot.temporal.kw.dm.KwField;
import com.enjoyiot.eiot.temporal.kw.dm.TableManager;
import com.enjoyiot.module.eiot.api.thingmodel.dto.ThingModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DbStructureDataImpl implements IDbStructureData {

    @Autowired
    private KwJdbcTemplate kwJdbcTemplate;

    /**
     * 根据物模型创建超级表
     */
    @Override
    public void defineThingModel(ThingModel thingModel) {
        //获取物模型中的属性定义
        List<KwField> fields = FieldParser.parse(thingModel);
        String tbName = Constants.getProductPropertyTableName(thingModel.getProductKey());
        //生成sql
        String sql = TableManager.getCreateTableSql(tbName,
                fields,
                new KwField("device_id", "INT8", -1));
        if (sql == null) {
            return;
        }
        log.info("executing sql:{}", sql);

        //执行sql
        kwJdbcTemplate.update(sql);
    }

    /**
     * 根据物模型更新超级表结构
     */
    @Override
    public void updateThingModel(ThingModel thingModel) {
        try {
            //获取旧字段信息
            String tbName = Constants.getProductPropertyTableName(thingModel.getProductKey());
            String sql = TableManager.getDescTableSql(tbName);
            List<KwField> oldFields = kwJdbcTemplate.query(sql, (rs, rowNum) -> {
                String name = rs.getString("column_name");
                String type = rs.getString("data_type").toUpperCase();
                int length = 0;
                try {
                    length = rs.getInt("length");
                } catch (Exception e) {

                }
                if (rs.wasNull()) {
                    length = -1; // 处理 NULL 值
                }
                return new KwField(name, type, length);
            });
            List<KwField> newFields = FieldParser.parse(thingModel);
            //对比差异

            //找出新增的字段
            List<KwField> addFields = newFields.stream().filter((f) -> oldFields.stream()
                            .noneMatch(old -> old.getName().equals(f.getName())))
                    .collect(Collectors.toList());
            if (!addFields.isEmpty()) {
                sql = TableManager.getAddTableColumnSql(tbName, addFields);
                kwJdbcTemplate.update(sql);
            }

            //找出修改的字段
            List<KwField> modifyFields = newFields.stream().filter((f) -> oldFields.stream()
                            .anyMatch(old ->
                                    old.getName().equals(f.getName()) //字段名相同
                                            //字段类型或长度不同
                                            && (!old.getType().equals(f.getType()) || old.getLength() != f.getLength())
                            ))
                    .collect(Collectors.toList());

            if (!modifyFields.isEmpty()) {
                sql = TableManager.getModifyTableColumnSql(tbName, modifyFields);
                kwJdbcTemplate.update(sql);
            }

            //找出删除的字段
            List<KwField> dropFields = oldFields.stream().filter((f) ->
                            !"time".equals(f.getName()) &&
                                    !"device_id".equals(f.getName()) && newFields.stream()
                                    //字段名不是time且没有相同字段名的
                                    .noneMatch(n -> n.getName().equals(f.getName())))
                    .collect(Collectors.toList());
            if (!dropFields.isEmpty()) {
                sql = TableManager.getDropTableColumnSql(tbName, dropFields);
                kwJdbcTemplate.update(sql);
            }
        } catch (Throwable e) {
            throw e;
        }
    }

    /**
     * 初始化其它数据结构
     */
    @Override
    @PostConstruct
    public void initDbStructure() {
        log.info("init ts db structure start");
        //创建规则日志超级表
        try {
            kwJdbcTemplate.queryForList(TableManager.getDescTableSql("rule_log"));
        } catch (Throwable e) {
            String sql = TableManager.getCreateTableSql("rule_log", Arrays.asList(
                    new KwField("state1", "VARCHAR", 32),
                    new KwField("content", "VARCHAR", 1024),
                    new KwField("success", "BOOL", -1)
            ), new KwField("rule_id", "INT8", -1));
            kwJdbcTemplate.update(sql);
        }

        //创建规则日志超级表
        try {
            kwJdbcTemplate.queryForList(TableManager.getDescTableSql("task_log"));
        } catch (Throwable e) {
            String sql = TableManager.getCreateTableSql("task_log", Arrays.asList(
                    new KwField("content", "VARCHAR", 1024),
                    new KwField("success", "BOOL", -1)
            ), new KwField("task_id", "INT8", -1));
            kwJdbcTemplate.update(sql);
        }

        //创建物模型消息超级表
        try {
            kwJdbcTemplate.queryForList(TableManager.getDescTableSql("thing_model_message"));
        } catch (Throwable e) {
            String sql = TableManager.getCreateTableSql("thing_model_message", Arrays.asList(
                    new KwField("mid", "VARCHAR", 50),
                    new KwField("product_key", "VARCHAR", 50),
                    new KwField("device_name", "VARCHAR", 50),
                    new KwField("uid", "VARCHAR", 50),
                    new KwField("type", "VARCHAR", 20),
                    new KwField("identifier", "VARCHAR", 50),
                    new KwField("code", "INT4", -1),
                    new KwField("data", "VARCHAR", 1024),
                    new KwField("report_time", "INT8", -1)
            ), new KwField("device_id", "INT8", -1));
            kwJdbcTemplate.update(sql);
        }


        //创建虚拟设备日志超级表
        try {
            kwJdbcTemplate.queryForList(TableManager.getDescTableSql("virtual_device_log"));
        } catch (Throwable e) {
            String sql = TableManager.getCreateTableSql("virtual_device_log", Arrays.asList(
                    new KwField("virtual_device_name", "VARCHAR", 50),
                    new KwField("device_total", "INT4", -1),
                    new KwField("result", "VARCHAR", 1024)
            ), new KwField("virtual_device_id", "INT8", -1));
            kwJdbcTemplate.update(sql);
        }

        log.info("init ts db structure end");

    }
}
