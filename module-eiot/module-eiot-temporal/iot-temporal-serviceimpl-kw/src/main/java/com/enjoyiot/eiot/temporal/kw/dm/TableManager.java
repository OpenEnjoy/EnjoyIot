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
package com.enjoyiot.eiot.temporal.kw.dm;

import java.util.List;

public class TableManager {

    /**
     * 创建时序表模板（含存在判断）
     */
    private static final String CREATE_TABLE_INE_TPL = "CREATE TABLE %s (%s) TAGS (%s) PRIMARY TAGS (%s);";

    /**
     * 删除时序表
     */
    private static final String DROP_TABLE_TPL = "DROP TABLE %s;";

    /**
     * 获取表的结构信息
     */
    private static final String DESC_TB_TPL = "SHOW COLUMNS FROM %s;";

    /**
     * 时序表增加列
     */
    private static final String ALTER_TABLE_ADD_COL_TPL = "ALTER TABLE %s ADD COLUMN %s;";

    /**
     * 时序表修改列
     */
    private static final String ALTER_TABLE_MODIFY_COL_TPL = "ALTER TABLE %s ALTER COLUMN %s;";

    /**
     * 时序表删除列
     */
    private static final String ALTER_TABLE_DROP_COL_TPL = "ALTER TABLE %s DROP COLUMN %s;";

    /**
     * 获取创建表sql
     */
    public static String getCreateTableSql(String tbName, List<KwField> fields, KwField... tags) {
        if (fields.isEmpty()) {
            return null;
        }

        //生成字段片段
        StringBuilder sbField = new StringBuilder("time TIMESTAMP NOT NULL,");

        for (KwField field : fields) {
            sbField.append(FieldParser.getFieldDefine(field));
            sbField.append(",");
        }
        sbField.deleteCharAt(sbField.length() - 1);

        String fieldFrag = sbField.toString();

        //生成tag
        StringBuilder sbTag = new StringBuilder();
        for (KwField tag : tags) {
            sbTag.append(FieldParser.getFieldDefine(tag))
                    .append(" NOT NULL,");
        }
        sbTag.deleteCharAt(sbTag.length() - 1);

        //生成tag
        StringBuilder pTag = new StringBuilder();
        for (KwField tag : tags) {
            pTag.append(FieldParser.getFieldTagDefine(tag))
                    .append(",");
        }
        pTag.deleteCharAt(pTag.length() - 1);

        return String.format(CREATE_TABLE_INE_TPL, tbName, fieldFrag, sbTag, pTag);

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
    public static String getAddTableColumnSql(String tbName, List<KwField> fields) {
        StringBuilder sbAdd = new StringBuilder();
        for (KwField field : fields) {
            sbAdd.append(String.format(ALTER_TABLE_ADD_COL_TPL,
                    tbName,
                    FieldParser.getFieldDefine(field)
            ));
        }
        return sbAdd.toString();
    }

    /**
     * 获取修改字段sql
     */
    public static String getModifyTableColumnSql(String tbName, List<KwField> fields) {
        StringBuilder sbModify = new StringBuilder();
        for (KwField field : fields) {
            sbModify.append(String.format(ALTER_TABLE_MODIFY_COL_TPL,
                    tbName,
                    FieldParser.getAlterFieldDefine(field)
            ));
        }
        return sbModify.toString();
    }

    /**
     * 获取删除字段sql
     */
    public static String getDropTableColumnSql(String tbName, List<KwField> fields) {
        StringBuilder sbDrop = new StringBuilder();
        for (KwField field : fields) {
            sbDrop.append(String.format(ALTER_TABLE_DROP_COL_TPL,
                    tbName,
                    field.getName()
            ));
        }
        return sbDrop.toString();
    }

}
