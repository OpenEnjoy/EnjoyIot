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
package com.enjoyiot.eiot.temporal.rdb.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;

@Slf4j
public class DbTypeDetector {

    public enum DbType {
        MYSQL,
        POSTGRESQL
    }

    private static volatile DbType dbType;

    public static DbType detect(DataSource dataSource) {
        if (dbType != null) {
            return dbType;
        }

        synchronized (DbTypeDetector.class) {
            if (dbType != null) {
                return dbType;
            }

            try {
                DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
                String databaseName = metaData.getDatabaseProductName().toLowerCase();

                if (databaseName.contains("mysql")) {
                    dbType = DbType.MYSQL;
                    log.info("检测到数据库类型: MySQL");
                } else if (databaseName.contains("postgresql") || databaseName.contains("postgres")) {
                    dbType = DbType.POSTGRESQL;
                    log.info("检测到数据库类型: PostgreSQL");
                } else {
                    throw new RuntimeException("不支持的数据库类型: " + databaseName);
                }
            } catch (Exception e) {
                log.error("检测数据库类型失败", e);
                throw new RuntimeException("检测数据库类型失败", e);
            }
        }

        return dbType;
    }

    public static boolean isMySQL(DataSource dataSource) {
        return detect(dataSource) == DbType.MYSQL;
    }

    public static boolean isPostgreSQL(DataSource dataSource) {
        return detect(dataSource) == DbType.POSTGRESQL;
    }
}
