/*
 *
 *  * | Licensed 未经许可不能去掉「Enjoy-iot」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com
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
package com.enjoyiot.eiot.temporal.rdb.service;


import com.enjoyiot.eiot.IDbStructureData;
import com.enjoyiot.eiot.temporal.rdb.config.Constants;
import com.enjoyiot.eiot.temporal.rdb.dao.RdbTemplate;
import com.enjoyiot.eiot.temporal.rdb.dm.FieldParser;
import com.enjoyiot.eiot.temporal.rdb.dm.RdbField;
import com.enjoyiot.eiot.temporal.rdb.dm.TableManager;
import com.enjoyiot.module.eiot.api.thingmodel.dto.ThingModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DbStructureDataImpl implements IDbStructureData {

    @Autowired
    private RdbTemplate rdbTemplate;

    @Override
    public void defineThingModel(ThingModel thingModel) {
        List<RdbField> fields = FieldParser.parse(thingModel);
        String tableName = Constants.getProductPropertyTableName(thingModel.getProductKey());

        String sql = TableManager.getCreateTableSql(
                rdbTemplate.getDataSource(),
                tableName,
                fields,
                new RdbField("device_id", "BIGINT")
        );

        if (sql == null) {
            return;
        }

        log.info("创建表: {}", tableName);
        for (String s : sql.split(";")) {
            if (!s.trim().isEmpty()) {
                try {
                    rdbTemplate.update(s);
                } catch (Exception e) {
                    if (!e.getMessage().contains("Duplicate")) {
                        log.warn("执行SQL失败: {}, 错误: {}", s, e.getMessage());
                    }
                }
            }
        }
    }

    @Override
    public void updateThingModel(ThingModel thingModel) {
        try {
            String tableName = Constants.getProductPropertyTableName(thingModel.getProductKey());
            String sql = TableManager.getDescTableSql(rdbTemplate.getDataSource(), tableName);

            List<RdbField> oldFields = rdbTemplate.query(sql, (rs, rowNum) -> {
                String name = rs.getString("column_name");
                String type = rs.getString("data_type").toUpperCase();
                int length = rs.getInt("length");
                if (rs.wasNull()) {
                    length = -1;
                }
                return new RdbField(name, type, length);
            });

            List<RdbField> newFields = FieldParser.parse(thingModel);

            List<RdbField> addFields = newFields.stream()
                    .filter(f -> oldFields.stream().noneMatch(old -> old.getName().equals(f.getName())))
                    .collect(Collectors.toList());
            if (!addFields.isEmpty()) {
                sql = TableManager.getAddColumnSql(tableName, addFields);
                rdbTemplate.update(sql);
                log.info("添加字段: {}", addFields);
            }

            List<RdbField> modifyFields = newFields.stream()
                    .filter(f -> oldFields.stream()
                            .anyMatch(old -> old.getName().equals(f.getName())
                                    && (!old.getType().equals(f.getType()) || old.getLength() != f.getLength())))
                    .collect(Collectors.toList());
            if (!modifyFields.isEmpty()) {
                sql = TableManager.getModifyColumnSql(tableName, modifyFields);
                rdbTemplate.update(sql);
                log.info("修改字段: {}", modifyFields);
            }

            List<RdbField> dropFields = oldFields.stream()
                    .filter(f -> !"time".equals(f.getName())
                            && !"device_id".equals(f.getName())
                            && newFields.stream().noneMatch(n -> n.getName().equals(f.getName())))
                    .collect(Collectors.toList());
            if (!dropFields.isEmpty()) {
                sql = TableManager.getDropColumnSql(tableName, dropFields);
                rdbTemplate.update(sql);
                log.info("删除字段: {}", dropFields);
            }
        } catch (Throwable e) {
            log.error("更新物模型失败", e);
        }
    }

    @Override
    @PostConstruct
    public void initDbStructure() {
        log.info("RDB init db structure start");

        createTableIfNotExists("rule_log", Arrays.asList(
                new RdbField("state1", "VARCHAR", 32),
                new RdbField("content", "VARCHAR", 1024),
                new RdbField("success", "TINYINT", -1)
        ), new RdbField("rule_id", "BIGINT"));

        createTableIfNotExists("task_log", Arrays.asList(
                new RdbField("content", "VARCHAR", 1024),
                new RdbField("success", "TINYINT", -1)
        ), new RdbField("task_id", "BIGINT"));

        createTableIfNotExists("thing_model_message", Arrays.asList(
                new RdbField("mid", "VARCHAR", 50),
                new RdbField("product_key", "VARCHAR", 50),
                new RdbField("device_name", "VARCHAR", 50),
                new RdbField("uid", "VARCHAR", 50),
                new RdbField("type", "VARCHAR", 20),
                new RdbField("identifier", "VARCHAR", 50),
                new RdbField("code", "INT", -1),
                new RdbField("data", "VARCHAR", 1024),
                new RdbField("report_time", "BIGINT", -1)
        ), new RdbField("device_id", "BIGINT"));

        createTableIfNotExists("virtual_device_log", Arrays.asList(
                new RdbField("virtual_device_name", "VARCHAR", 50),
                new RdbField("device_total", "INT", -1),
                new RdbField("result", "VARCHAR", 1024)
        ), new RdbField("virtual_device_id", "BIGINT"));

        log.info("RDB init db structure end");
    }

    private void createTableIfNotExists(String tableName, List<RdbField> fields, RdbField... tags) {
        String sql = TableManager.getCreateTableSql(rdbTemplate.getDataSource(), tableName, fields, tags);
        if (sql == null) {
            return;
        }

        for (String s : sql.split(";")) {
            if (!s.trim().isEmpty()) {
                try {
                    rdbTemplate.update(s);
                } catch (Exception e) {
                    if (!e.getMessage().contains("Duplicate") && !e.getMessage().contains("already exists")) {
                        log.warn("执行SQL失败: {}, 错误: {}", s, e.getMessage());
                    }
                }
            }
        }
    }
}
