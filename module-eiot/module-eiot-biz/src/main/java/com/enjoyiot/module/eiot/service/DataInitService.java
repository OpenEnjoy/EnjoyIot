package com.enjoyiot.module.eiot.service;

import com.enjoyiot.eiot.IDbStructureData;
import com.enjoyiot.framework.common.util.json.JsonUtils;
import com.enjoyiot.framework.tenant.core.util.TenantUtils;
import com.enjoyiot.module.eiot.api.thingmodel.dto.ThingModel;
import com.enjoyiot.module.eiot.convert.ThingModelConvert;
import com.enjoyiot.module.eiot.dal.mysql.thingmodel.ThingModelMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Timer;
import java.util.TimerTask;

@Slf4j
@Service
public class DataInitService implements SmartInitializingSingleton {

    @Value("${init.data.flag:true}")
    private boolean initDataFlg;

    @Resource
    private ThingModelMapper thingModelMapper;

    @Resource
    private IDbStructureData dbStructureData;


    @Override
    public void afterSingletonsInstantiated() {
        //等redis实例化后再执行
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                TenantUtils.executeIgnore(() -> {

                    try {

                        if (!initDataFlg) {
                            log.debug("无需初始化数据");
                            return;
                        }

                        initProductData();
                        log.info("init data finished.");

                    } catch (
                            Exception e) {
                        log.error("init error", e);
                    }

                });

            }
        }, 1000);

    }

    private void initProductData() {
        log.info("数据初始化-时序数据库表结构");

        thingModelMapper.selectList().forEach(thingModelDO -> {
            try{
                ThingModel thingModel = ThingModelConvert.INSTANCE.convert(thingModelDO);
                if (thingModelDO != null && thingModel != null) {
                    thingModel.setModel(JsonUtils.parseObject(thingModelDO.getModel(), ThingModel.Model.class));
                }
                dbStructureData.defineThingModel(thingModel);
            }catch (Exception e){
                log.error("初始化时序数据库,表结构失败:",e,"产品key:",thingModelDO.getProductKey());
            }
        });
    }


}
