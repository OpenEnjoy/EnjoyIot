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
package com.enjoyiot.eiot.temporal.rdb.dm;

import com.enjoyiot.eiot.temporal.rdb.config.DbTypeDetector;

import javax.sql.DataSource;
import java.util.List;

public class TableManager {

    public static String getCreateTableSql(DataSource dataSource, String tableName, List<RdbField> fields, RdbField... tags) {
        if (fields.isEmpty()) {
            return null;
        }

        StringBuilder sbField = new StringBuilder();
        sbField.append("time BIGINT,");

        for (RdbField field : fields) {
            sbField.append(FieldParser.getFieldDefine(field)).append(",");
        }

        for (RdbField tag : tags) {
            sbField.append(FieldParser.getFieldDefine(tag)).append(",");
        }
        sbField.deleteCharAt(sbField.length() - 1);

        String createTableSql = String.format("CREATE TABLE IF NOT EXISTS %s (%s)", tableName, sbField);

        DbTypeDetector.DbType dbType = DbTypeDetector.detect(dataSource);
        if (dbType == DbTypeDetector.DbType.MYSQL) {
            createTableSql += " ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
        }

        String indexSql = getCreateIndexSql(dataSource, tableName, tags);

        return createTableSql + ";" + indexSql;
    }

    private static String getCreateIndexSql(DataSource dataSource, String tableName, RdbField... tags) {
        if (tags == null || tags.length == 0) {
            return "";
        }

        StringBuilder sbIndex = new StringBuilder();
        for (RdbField tag : tags) {
            sbIndex.append("CREATE INDEX idx_").append(tableName).append("_").append(tag.getName())
                    .append(" ON ").append(tableName).append(" (").append(tag.getName()).append(");");
        }

        return sbIndex.toString();
    }

    public static String getDescTableSql(DataSource dataSource, String tableName) {
        DbTypeDetector.DbType dbType = DbTypeDetector.detect(dataSource);
        if (dbType == DbTypeDetector.DbType.MYSQL) {
            return String.format("SELECT COLUMN_NAME as column_name, DATA_TYPE as data_type, CHARACTER_MAXIMUM_LENGTH as length " +
                    "FROM information_schema.COLUMNS WHERE TABLE_NAME = '%s' ORDER BY ORDINAL_POSITION", tableName);
        } else {
            return String.format("SELECT column_name, data_type, character_maximum_length AS length " +
                    "FROM information_schema.columns WHERE table_name = '%s' ORDER BY ordinal_position", tableName);
        }
    }

    public static String getAddColumnSql(String tableName, List<RdbField> fields) {
        StringBuilder sbAdd = new StringBuilder();
        for (RdbField field : fields) {
            sbAdd.append(String.format("ALTER TABLE %s ADD COLUMN %s;",
                    tableName,
                    FieldParser.getFieldDefine(field)
            ));
        }
        return sbAdd.toString();
    }

    public static String getModifyColumnSql(String tableName, List<RdbField> fields) {
        StringBuilder sbModify = new StringBuilder();
        for (RdbField field : fields) {
            sbModify.append(String.format("ALTER TABLE %s MODIFY COLUMN %s;",
                    tableName,
                    FieldParser.getFieldDefine(field)
            ));
        }
        return sbModify.toString();
    }

    public static String getDropColumnSql(String tableName, List<RdbField> fields) {
        StringBuilder sbDrop = new StringBuilder();
        for (RdbField field : fields) {
            sbDrop.append(String.format("ALTER TABLE %s DROP COLUMN %s;",
                    tableName,
                    field.getName()
            ));
        }
        return sbDrop.toString();
    }

    public static String rightTableName(String name) {
        return name.toLowerCase().replace("-", "_");
    }
}
