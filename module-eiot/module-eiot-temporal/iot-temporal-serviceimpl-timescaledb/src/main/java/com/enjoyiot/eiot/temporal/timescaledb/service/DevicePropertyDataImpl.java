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
import com.enjoyiot.eiot.temporal.timescaledb.dm.TableManager;
import com.enjoyiot.eiot.temporal.timescaledb.model.PgDeviceProperty;
import com.enjoyiot.module.eiot.api.device.DeviceApi;
import com.enjoyiot.module.eiot.api.device.dto.DeviceInfo;
import com.enjoyiot.module.eiot.api.device.dto.DeviceProperty;
import com.enjoyiot.module.eiot.api.device.dto.DevicePropertyCache;
import com.enjoyiot.module.eiot.api.thingmodel.ThingModelApi;
import com.enjoyiot.module.eiot.api.thingmodel.dto.ThingModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
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

        ThingModel thingModel = thingModelApi.getThingModelByProductKeyFromCache(device.getProductKey());
        if (thingModel == null) {
            return new ArrayList<>();
        }
        Map<String, String> fieldMap = FieldParser.parse(thingModel).stream()
                .collect(Collectors.toMap(PgField::getName, PgField::getType));
        String safeColumn = TableManager.safeIdentifier(name);
        if (!fieldMap.containsKey(safeColumn)) {
            return new ArrayList<>();
        }

        String tbName = Constants.getProductPropertySTableName(device.getProductKey());
        String sql = String.format(
                "SELECT time,%s AS value,device_id FROM %s WHERE device_id=? AND time>=? AND time<=? ORDER BY time ASC LIMIT ? OFFSET 0",
                TableManager.quoteIdent(safeColumn), TableManager.quoteIdent(tbName));
        List<PgDeviceProperty> deviceProperties = pgTemplate.query(sql,
                new BeanPropertyRowMapper<>(PgDeviceProperty.class),
                deviceId, new Timestamp(start), new Timestamp(end), size
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
        if (thingModel == null) {
            return;
        }
        List<PgField> fieldList = FieldParser.parse(thingModel);
        Map<String, String> fieldMap = fieldList.stream()
                .collect(Collectors.toMap(PgField::getName, PgField::getType));

        Map<String, DevicePropertyCache> oldProperties = deviceApi.getPropertiesFromCache(deviceId);
        Map<String, DevicePropertyCache> merged = new HashMap<>(oldProperties == null ? Map.of() : oldProperties);
        if (properties != null) {
            merged.putAll(properties);
        }

        StringBuilder sbFieldNames = new StringBuilder();
        StringBuilder sbFieldPlaces = new StringBuilder();
        List<Object> args = new ArrayList<>();
        args.add(new Timestamp(time));

        //组织sql
        merged.forEach((key, val) -> {
            String safeKey = TableManager.safeIdentifier(key);
            String fieldType = fieldMap.get(safeKey);
            if (fieldType == null) {
                return;
            }
            sbFieldNames.append(TableManager.quoteIdent(safeKey)).append(",");
            sbFieldPlaces.append("?,");
            // PostgreSQL 对类型要求很严格，所以这里需要转换
            switch (fieldType) {
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

        String tbName = Constants.getProductPropertySTableName(device.getProductKey());
        if (sbFieldNames.isEmpty()) {
            String sql = String.format("INSERT INTO %s (time,device_id) VALUES (?,?);", TableManager.quoteIdent(tbName));
            pgTemplate.update(sql, args.toArray());
            return;
        }

        sbFieldNames.deleteCharAt(sbFieldNames.length() - 1);
        sbFieldPlaces.deleteCharAt(sbFieldPlaces.length() - 1);

        String sql = String.format("INSERT INTO %s (time,%s,device_id) VALUES (?,%s,?);",
                TableManager.quoteIdent(tbName),
                sbFieldNames,
                sbFieldPlaces);

        pgTemplate.update(sql, args.toArray());
    }

}
