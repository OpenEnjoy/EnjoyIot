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
package com.enjoyiot.eiot.temporal.timescaledb.service;


import com.enjoyiot.eiot.IDbStructureData;
import com.enjoyiot.eiot.temporal.timescaledb.config.Constants;
import com.enjoyiot.eiot.temporal.timescaledb.dao.PgTemplate;
import com.enjoyiot.eiot.temporal.timescaledb.dm.FieldParser;
import com.enjoyiot.eiot.temporal.timescaledb.dm.PgField;
import com.enjoyiot.eiot.temporal.timescaledb.dm.TableManager;
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
    private PgTemplate pgTemplate;

    /**
     * 根据物模型创建超级表
     */
    @Override
    public void defineThingModel(ThingModel thingModel) {
        //获取物模型中的属性定义
        List<PgField> fields = FieldParser.parse(thingModel);
        String tbName = Constants.getProductPropertySTableName(thingModel.getProductKey());
        //生成sql
        String sql = TableManager.getCreateSTableSql(tbName,
                fields,
                new PgField("device_id", "BIGINT", -1));
        if (sql == null) {
            return;
        }
        log.info("executing sql:{}", sql);

        //执行sql
        pgTemplate.update(sql);
    }

    /**
     * 根据物模型更新超级表结构
     */
    @Override
    public void updateThingModel(ThingModel thingModel) {
        try {
            //获取旧字段信息
            String tbName = Constants.getProductPropertySTableName(thingModel.getProductKey());
            String sql = TableManager.getDescTableSql(tbName);
            List<PgField> oldFields = pgTemplate.query(sql, (rs, rowNum) ->{
                String name = rs.getString("column_name");
                String type = rs.getString("data_type").toUpperCase();
                int length = rs.getInt("length");
                if (rs.wasNull()) {
                    length = -1; // 处理 NULL 值
                }
                return new PgField(name, type, length);
            });
            List<PgField> newFields = FieldParser.parse(thingModel);
            //对比差异

            //找出新增的字段
            List<PgField> addFields = newFields.stream().filter((f) -> oldFields.stream()
                            .noneMatch(old -> old.getName().equals(f.getName())))
                    .collect(Collectors.toList());
            if (!addFields.isEmpty()) {
                sql = TableManager.getAddSTableColumnSql(tbName, addFields);
                pgTemplate.update(sql);
            }

            //找出修改的字段
            List<PgField> modifyFields = newFields.stream().filter((f) -> oldFields.stream()
                            .anyMatch(old ->
                                    old.getName().equals(f.getName()) //字段名相同
                                            //字段类型或长度不同
                                            && (!old.getType().equals(f.getType()) || old.getLength() != f.getLength())
                            ))
                    .collect(Collectors.toList());

            if (!modifyFields.isEmpty()) {
                sql = TableManager.getModifySTableColumnSql(tbName, modifyFields);
                pgTemplate.update(sql);
            }

            //找出删除的字段
            List<PgField> dropFields = oldFields.stream().filter((f) ->
                            !"time".equals(f.getName()) &&
                                    !"device_id".equals(f.getName()) && newFields.stream()
                                    //字段名不是time且没有相同字段名的
                                    .noneMatch(n -> n.getName().equals(f.getName())))
                    .collect(Collectors.toList());
            if (!dropFields.isEmpty()) {
                sql = TableManager.getDropSTableColumnSql(tbName, dropFields);
                pgTemplate.update(sql);
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
        log.info("timescaledb init db structure start");
        //创建规则日志超级表
        String sql = TableManager.getCreateSTableSql("rule_log", Arrays.asList(
                new PgField("state1", "VARCHAR", 32),
                new PgField("content", "VARCHAR", 1024),
                new PgField("success", "BOOLEAN", -1)
        ), new PgField("rule_id", "BIGINT", -1));
        pgTemplate.update(sql);


        //创建规则日志超级表
        sql = TableManager.getCreateSTableSql("task_log", Arrays.asList(
                new PgField("content", "VARCHAR", 1024),
                new PgField("success", "BOOLEAN", -1)
        ), new PgField("task_id", "BIGINT", -1));
        pgTemplate.update(sql);


        //创建物模型消息超级表
        sql = TableManager.getCreateSTableSql("thing_model_message", Arrays.asList(
                new PgField("mid", "VARCHAR", 50),
                new PgField("product_key", "VARCHAR", 50),
                new PgField("device_name", "VARCHAR", 50),
                new PgField("uid", "VARCHAR", 50),
                new PgField("type", "VARCHAR", 20),
                new PgField("identifier", "VARCHAR", 50),
                new PgField("code", "INTEGER", -1),
                new PgField("data", "VARCHAR", 1024),
                new PgField("report_time", "BIGINT", -1)
        ), new PgField("device_id", "BIGINT", -1));
        pgTemplate.update(sql);

        //创建虚拟设备日志超级表
        sql = TableManager.getCreateSTableSql("virtual_device_log", Arrays.asList(
                new PgField("virtual_device_name", "VARCHAR", 50),
                new PgField("device_total", "INTEGER", -1),
                new PgField("result", "VARCHAR", 1024)
        ), new PgField("virtual_device_id", "BIGINT", -1));
        pgTemplate.update(sql);
        log.info("timescaledb init db structure end");

    }
}
