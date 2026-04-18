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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

public class TableManager {

    /**
     * 创建表（含存在判断）
     */
    private static final String CREATE_TABLE_IF_NOT_EXISTS_TPL = "CREATE TABLE IF NOT EXISTS %s (%s);";

    private static final String CREATE_HYPERTABLE_TPL = "SELECT create_hypertable('%s','time', if_not_exists => TRUE);";

    /**
     * 删除表
     */
    private static final String DROP_TABLE_TPL = "DROP TABLE IF EXISTS %s;";

    /**
     * 获取表的结构信息
     */
    private static final String DESC_TB_SQL = "SELECT column_name, data_type, character_maximum_length AS length FROM information_schema.columns WHERE table_schema = current_schema() AND table_name = ? ORDER BY ordinal_position;";

    /**
     * 表增加列
     */
    private static final String ALTER_TABLE_ADD_COL_TPL = "ALTER TABLE %s ADD COLUMN %s;";

    /**
     * 表修改列类型
     */
    private static final String ALTER_TABLE_ALTER_COL_TYPE_TPL = "ALTER TABLE %s ALTER COLUMN %s TYPE %s;";

    /**
     * 表删除列
     */
    private static final String ALTER_TABLE_DROP_COL_TPL = "ALTER TABLE %s DROP COLUMN %s;";

    private static final Pattern SAFE_IDENTIFIER = Pattern.compile("[a-z0-9_]+");

    public static String safeIdentifier(String raw) {
        Objects.requireNonNull(raw, "identifier must not be null");
        String normalized = raw.toLowerCase(Locale.ROOT)
                .replace("-", "_")
                .replaceAll("[^a-z0-9_]", "_")
                .replaceAll("_+", "_")
                .replaceAll("^_+|_+$", "");
        if (normalized.isEmpty() || !SAFE_IDENTIFIER.matcher(normalized).matches()) {
            throw new IllegalArgumentException("illegal identifier: " + raw);
        }
        return normalized;
    }

    public static String quoteIdent(String identifier) {
        return "\"" + safeIdentifier(identifier) + "\"";
    }

    /**
     * 获取创建表sql
     */
    public static List<String> getCreateSTableSql(String tbName, List<PgField> fields, PgField... tags) {
        if (fields.isEmpty()) {
            return null;
        }

        String safeTbName = safeIdentifier(tbName);
        String quotedTbName = "\"" + safeTbName + "\"";

        StringBuilder sbField = new StringBuilder("\"time\" TIMESTAMPTZ,");
        for (PgField field : fields) {
            sbField.append(getFieldDefine(field)).append(",");
        }

        for (PgField tag : tags) {
            sbField.append(getFieldDefine(tag)).append(",");
        }
        sbField.deleteCharAt(sbField.length() - 1);

        List<String> sqlList = new ArrayList<>();
        sqlList.add(String.format(CREATE_TABLE_IF_NOT_EXISTS_TPL, quotedTbName, sbField));
        sqlList.add(String.format(CREATE_HYPERTABLE_TPL, safeTbName));
        return sqlList;
    }

    /**
     * 取正确的表名
     *
     * @param name 表象
     */
    public static String rightTbName(String name) {
        return safeIdentifier(name);
    }

    /**
     * 获取表详情的sql
     */
    public static String getDescTableSql() {
        return DESC_TB_SQL;
    }

    /**
     * 获取添加字段sql
     */
    public static List<String> getAddSTableColumnSql(String tbName, List<PgField> fields) {
        String quotedTbName = "\"" + safeIdentifier(tbName) + "\"";
        List<String> sqlList = new ArrayList<>();
        for (PgField field : fields) {
            sqlList.add(String.format(ALTER_TABLE_ADD_COL_TPL, quotedTbName, getFieldDefine(field)));
        }
        return sqlList;
    }

    /**
     * 获取修改字段sql
     */
    public static List<String> getModifySTableColumnSql(String tbName, List<PgField> fields) {
        String quotedTbName = "\"" + safeIdentifier(tbName) + "\"";
        List<String> sqlList = new ArrayList<>();
        for (PgField field : fields) {
            sqlList.add(String.format(ALTER_TABLE_ALTER_COL_TYPE_TPL,
                    quotedTbName,
                    "\"" + safeIdentifier(field.getName()) + "\"",
                    getTypeDefine(field)
            ));
        }
        return sqlList;
    }

    /**
     * 获取删除字段sql
     */
    public static List<String> getDropSTableColumnSql(String tbName, List<PgField> fields) {
        String quotedTbName = "\"" + safeIdentifier(tbName) + "\"";
        List<String> sqlList = new ArrayList<>();
        for (PgField field : fields) {
            sqlList.add(String.format(ALTER_TABLE_DROP_COL_TPL, quotedTbName, "\"" + safeIdentifier(field.getName()) + "\""));
        }
        return sqlList;
    }

    private static String getFieldDefine(PgField field) {
        return "\"" + safeIdentifier(field.getName()) + "\" " + getTypeDefine(field);
    }

    private static String getTypeDefine(PgField field) {
        return field.getLength() > 0
                ? String.format("%s(%d)", field.getType(), field.getLength())
                : field.getType();
    }
}
