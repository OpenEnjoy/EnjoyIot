package com.enjoyiot.eiot.temporal.es.config;

import org.dromara.easyes.common.property.EasyEsDynamicProperties;
import org.dromara.easyes.common.property.EasyEsProperties;
import org.dromara.easyes.spring.MapperScannerConfigurer;
import org.dromara.easyes.spring.annotation.EsMapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;

import java.util.HashMap;
import java.util.Map;

/**
 * spring不用xml配置的平替
 * @author lzh
 * @since 2.1.0
 */
@EsMapperScan("${platform.info.base-package}.eiot")
@ComponentScan("org.dromara.easyes")
@Configuration
public class EasyEsSpringConfig {

    @Value("${easy-es.address}")
    private String esAddress;

    @Bean
    public EasyEsProperties easyEsProperties() {
        EasyEsProperties easyEsProperties = new EasyEsProperties();
        // 以下是easy-es的配置项
        easyEsProperties.setAddress(esAddress);
        return easyEsProperties;
    }

    @Bean
    public EasyEsDynamicProperties easyEsDynamicProperties() {
        EasyEsDynamicProperties easyEsDynamicProperties = new EasyEsDynamicProperties();
        // 多数据源配置
        Map<String, EasyEsProperties> datasource = new HashMap<>();
        // 这里你可以配置多个数据源，在mapper中可通过@EsDS("key")注解来指定数据源
        easyEsDynamicProperties.setDatasource(datasource);
        return easyEsDynamicProperties;
    }


}
