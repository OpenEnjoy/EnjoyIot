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


import cn.hutool.core.convert.Convert;
import com.enjoyiot.eiot.IDevicePropertyData;
import com.enjoyiot.eiot.temporal.timescaledb.config.Constants;
import com.enjoyiot.eiot.temporal.timescaledb.dao.PgTemplate;
import com.enjoyiot.eiot.temporal.timescaledb.dm.FieldParser;
import com.enjoyiot.eiot.temporal.timescaledb.dm.PgField;
import com.enjoyiot.eiot.temporal.timescaledb.model.PgDeviceProperty;
import com.enjoyiot.module.eiot.api.device.DeviceApi;
import com.enjoyiot.module.eiot.api.device.dto.DeviceInfo;
import com.enjoyiot.module.eiot.api.device.dto.DeviceProperty;
import com.enjoyiot.module.eiot.api.device.dto.DevicePropertyCache;
import com.enjoyiot.module.eiot.api.thingmodel.ThingModelApi;
import com.enjoyiot.module.eiot.api.thingmodel.dto.ThingModel;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PGTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DevicePropertyDataImpl implements IDevicePropertyData {

    @Autowired
    private PgTemplate pgTemplate;

    @Resource
    private DeviceApi deviceApi;

    @Resource
    private ThingModelApi thingModelApi;

    @Override
    public List<DeviceProperty> findDevicePropertyHistory(Long deviceId, String name, long start, long end, int size) {
        DeviceInfo device = deviceApi.getDeviceInfoFromCache(deviceId);
        if (device == null) {
            return new ArrayList<>();
        }

        String tbName = Constants.getProductPropertySTableName(device.getProductKey());
        List<PgDeviceProperty> deviceProperties = pgTemplate.query(String.format(
                        "SELECT time,%s as value,device_id FROM %s WHERE device_id=? AND time>=? AND time<=? " +
                                "ORDER BY time ASC LIMIT %d OFFSET 0",
                        name.toLowerCase(), tbName, size),
                new BeanPropertyRowMapper<>(PgDeviceProperty.class),
                deviceId, new PGTimestamp(start), new PGTimestamp(end)
        );
        return deviceProperties.stream().map(p -> new DeviceProperty(
                        p.getTime().toString(),
                        p.getDeviceId().toString(),
                        name,
                        p.getValue(),
                        p.getTime().getTime()))
                .collect(Collectors.toList());
    }

    @Override
    public void addProperties(Long deviceId, Map<String, DevicePropertyCache> properties, long time) {
        DeviceInfo device = deviceApi.getDeviceInfoFromCache(deviceId);

        if (device == null) {
            return;
        }
        ThingModel thingModel = thingModelApi.getThingModelByProductKeyFromCache(device.getProductKey());
        List<PgField> fieldList = FieldParser.parse(thingModel);
        Map<String, String> fidldMap = fieldList.stream().collect(Collectors.toMap(PgField::getName, PgField::getType));
        //获取设备旧属性
        Map<String, DevicePropertyCache> oldProperties = deviceApi.getPropertiesFromCache(deviceId);
        //用新属性覆盖
        oldProperties.putAll(properties);

        StringBuilder sbFieldNames = new StringBuilder();
        StringBuilder sbFieldPlaces = new StringBuilder();
        List<Object> args = new ArrayList<>();
        args.add(new PGTimestamp(time));

        //组织sql
        oldProperties.forEach((key, val) -> {
            sbFieldNames.append(key)
                    .append(",");
            sbFieldPlaces.append("?,");
            // PostgreSQL 对类型要求很严格，所以这里需要转换
            switch (fidldMap.get(key)) {
                case "INTEGER":
                    args.add(Convert.toInt(val.getValue()));
                    break;
                case "SMALLINT":
                    args.add(Convert.toShort(val.getValue()));
                    break;
                case "DOUBLE PRECISION":
                    args.add(Convert.toDouble(val.getValue()));
                    break;
                case "BOOLEAN":
                    args.add(Convert.toBool(val.getValue()));
                    break;
                case "VARCHAR":
                    args.add(Convert.toStr(val.getValue()));
                    break;
                default:
                    args.add(val.getValue());
                    break;
            }

        });
        args.add(deviceId);

        sbFieldNames.deleteCharAt(sbFieldNames.length() - 1);
        sbFieldPlaces.deleteCharAt(sbFieldPlaces.length() - 1);

        String sql = String.format("INSERT INTO %s (time,%s,device_id) VALUES (?,%s,?);",
                Constants.getProductPropertySTableName(device.getProductKey()),
                sbFieldNames,
                sbFieldPlaces);

        pgTemplate.update(sql, args.toArray());
    }

}
