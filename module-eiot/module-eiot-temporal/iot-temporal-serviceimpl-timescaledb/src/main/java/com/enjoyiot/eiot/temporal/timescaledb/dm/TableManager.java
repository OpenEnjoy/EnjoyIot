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
package com.enjoyiot.eiot.temporal.timescaledb.dm;

import java.util.List;

public class TableManager {

    /**
     * 创建超级表（含存在判断）
     * 注意：WITH语法创建超级表不支持 TimescaleDB v2.19.3 及更低版本
     */
    private static final String CREATE_STABLE_INE_TPL = "CREATE TABLE IF NOT EXISTS %s (%s) WITH (tsdb.hypertable, tsdb.partition_column='time', tsdb.segmentby = '%s');";

    /**
     * 删除超级表
     */
    private static final String DROP_STABLE_TPL = "DROP TABLE IF EXISTS %s;";

    /**
     * 获取表的结构信息
     */
    private static final String DESC_TB_TPL = "SELECT column_name, data_type, character_maximum_length AS length FROM information_schema.columns WHERE table_name = '%s' ORDER BY ordinal_position;";

    /**
     * 超级表增加列
     */
    private static final String ALTER_STABLE_ADD_COL_TPL = "ALTER TABLE %s ADD COLUMN %s;";

    /**
     * 超级表修改列
     */
    private static final String ALTER_STABLE_MODIFY_COL_TPL = "ALTER TABLE %s MODIFY COLUMN %s;";

    /**
     * 超级表删除列
     */
    private static final String ALTER_STABLE_DROP_COL_TPL = "ALTER TABLE %s DROP COLUMN %s;";

    /**
     * 获取创建表sql
     */
    public static String getCreateSTableSql(String tbName, List<PgField> fields, PgField... tags) {
        if (fields.isEmpty()) {
            return null;
        }

        //生成字段片段
        StringBuilder sbField = new StringBuilder("time TIMESTAMPTZ,");

        for (PgField field : fields) {
            sbField.append(FieldParser.getFieldDefine(field));
            sbField.append(",");
        }

        //生成tag
        StringBuilder sbIndex = new StringBuilder();
        for (PgField tag : tags) {
            sbField.append(FieldParser.getFieldDefine(tag))
                    .append(",");
            sbIndex.append(tag.getName()).append(",");
        }
        sbField.deleteCharAt(sbField.length() - 1);
        sbIndex.deleteCharAt(sbIndex.length() - 1);

        return String.format(CREATE_STABLE_INE_TPL, tbName, sbField, sbIndex);

    }

    /**
     * 取正确的表名
     *
     * @param name 表象
     */
    public static String rightTbName(String name) {
        return name.toLowerCase().replace("-", "_");
    }

    /**
     * 获取表详情的sql
     */
    public static String getDescTableSql(String tbName) {
        return String.format(DESC_TB_TPL, tbName);
    }

    /**
     * 获取添加字段sql
     */
    public static String getAddSTableColumnSql(String tbName, List<PgField> fields) {
        StringBuilder sbAdd = new StringBuilder();
        for (PgField field : fields) {
            sbAdd.append(String.format(ALTER_STABLE_ADD_COL_TPL,
                    tbName,
                    FieldParser.getFieldDefine(field)
            ));
        }
        return sbAdd.toString();
    }

    /**
     * 获取修改字段sql
     */
    public static String getModifySTableColumnSql(String tbName, List<PgField> fields) {
        StringBuilder sbModify = new StringBuilder();
        for (PgField field : fields) {
            sbModify.append(String.format(ALTER_STABLE_MODIFY_COL_TPL,
                    tbName,
                    FieldParser.getFieldDefine(field)
            ));
        }
        return sbModify.toString();
    }

    /**
     * 获取删除字段sql
     */
    public static String getDropSTableColumnSql(String tbName, List<PgField> fields) {
        StringBuilder sbDrop = new StringBuilder();
        for (PgField field : fields) {
            sbDrop.append(String.format(ALTER_STABLE_DROP_COL_TPL,
                    tbName,
                    field.getName()
            ));
        }
        return sbDrop.toString();
    }

}
