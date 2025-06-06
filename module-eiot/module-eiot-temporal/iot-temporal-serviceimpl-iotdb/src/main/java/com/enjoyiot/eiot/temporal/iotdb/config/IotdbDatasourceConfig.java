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
package com.enjoyiot.eiot.temporal.iotdb.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.iotdb.session.pool.SessionPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class IotdbDatasourceConfig {

    @Value("${spring.iotdb-datasource.url}")
    private String url;

    @Value("${spring.iotdb-datasource.username}")
    private String username;

    @Value("${spring.iotdb-datasource.password}")
    private String password;

    @Value("${spring.iotdb-datasource.baseDb}")
    private String baseDb;

    @Bean("iotdbTemplate")
    public SessionPool iotdbTemplate() {
        SessionPool sessionPool =
                new SessionPool.Builder()
                        .nodeUrls(Collections.singletonList(url))
                        .user(username)
                        .password(password)
                        .maxSize(3)
                        .build();
        return sessionPool;
    }

    @Bean("iotdbJdbcTemplate")
    public IotdbTemplate iotdbJdbcTemplate() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:iotdb://47.104.95.120:16667/");
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName("org.apache.iotdb.jdbc.IoTDBDriver");
        return new IotdbTemplate(dataSource);
    }

    public String getThingModelMessageTemplateName(){
        return "thing_model_message";
    }
    public String getBaseDb() {
        return baseDb;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
