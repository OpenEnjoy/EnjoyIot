/*
 * Copyright [2025] [Enjoy-iot]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.enjoyiot.module.eiot.convert;

import cn.hutool.core.util.StrUtil;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.common.util.json.JsonUtils;
import com.enjoyiot.module.eiot.api.shadow.dto.DeviceShadowDTO;
import com.enjoyiot.module.eiot.controller.admin.shadow.vo.DeviceShadowRespVO;
import com.enjoyiot.module.eiot.dal.dataobject.shadow.DeviceShadowDO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 设备影子 Convert
 *
 * @author EnjoyIot
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface DeviceShadowConvert {

    DeviceShadowConvert INSTANCE = Mappers.getMapper(DeviceShadowConvert.class);

    @Mappings({
            @Mapping(source = "desired", target = "desired", qualifiedByName = "stringToMap"),
            @Mapping(source = "reported", target = "reported", qualifiedByName = "stringToMap"),
            @Mapping(source = "metadata", target = "metadata", qualifiedByName = "stringToMap")
    })
    DeviceShadowRespVO convertToRespVO(DeviceShadowDO bean);

    List<DeviceShadowRespVO> convertList(List<DeviceShadowDO> list);

    PageResult<DeviceShadowRespVO> convertPage(PageResult<DeviceShadowDO> page);

    @Named("stringToMap")
    default Map<String, Object> stringToMap(String jsonString) {
        if (StrUtil.isBlank(jsonString)) {
            return Collections.emptyMap();
        }
        return JsonUtils.parseObject(jsonString, Map.class);
    }

    /**
     * 转换 DO 为 DTO（用于 API）
     */
    default DeviceShadowDTO convertToDTO(DeviceShadowDO shadow) {
        if (shadow == null) {
            return null;
        }

        return DeviceShadowDTO.builder()
                .id(shadow.getId())
                .deviceId(shadow.getDeviceId())
                .productKey(shadow.getProductKey())
                .dn(shadow.getDn())
                .desired(stringToMap(shadow.getDesired()))
                .reported(stringToMap(shadow.getReported()))
                .metadata(parseMetadata(shadow.getMetadata()))
                .version(shadow.getVersion())
                .lastDesiredTime(shadow.getLastDesiredTime())
                .lastReportedTime(shadow.getLastReportedTime())
                .createTime(shadow.getCreateTime())
                .updateTime(shadow.getUpdateTime())
                .build();
    }

    /**
     * 解析元数据
     */
    default DeviceShadowDTO.ShadowMetadata parseMetadata(String metadataJson) {
        if (StrUtil.isBlank(metadataJson)) {
            return null;
        }
        
        Map<String, Object> metadataMap = JsonUtils.parseObject(metadataJson, Map.class);
        if (metadataMap == null || metadataMap.isEmpty()) {
            return null;
        }

        DeviceShadowDTO.ShadowMetadata metadata = new DeviceShadowDTO.ShadowMetadata();
        
        // 解析 desired 元数据
        if (metadataMap.containsKey("desired")) {
            Map<String, Map<String, Object>> desiredMeta = (Map<String, Map<String, Object>>) metadataMap.get("desired");
            Map<String, DeviceShadowDTO.PropertyMetadata> desiredMetadata =
                new java.util.HashMap<>();
            
            if (desiredMeta != null) {
                desiredMeta.forEach((key, value) -> {
                    Long timestamp = value.get("timestamp") instanceof Number 
                        ? ((Number) value.get("timestamp")).longValue() 
                        : null;
                    desiredMetadata.put(key, DeviceShadowDTO.PropertyMetadata.builder()
                        .timestamp(timestamp)
                        .build());
                });
            }
            metadata.setDesired(desiredMetadata);
        }
        
        // 解析 reported 元数据
        if (metadataMap.containsKey("reported")) {
            Map<String, Map<String, Object>> reportedMeta = (Map<String, Map<String, Object>>) metadataMap.get("reported");
            Map<String, DeviceShadowDTO.PropertyMetadata> reportedMetadata =
                new java.util.HashMap<>();
            
            if (reportedMeta != null) {
                reportedMeta.forEach((key, value) -> {
                    Long timestamp = value.get("timestamp") instanceof Number 
                        ? ((Number) value.get("timestamp")).longValue() 
                        : null;
                    reportedMetadata.put(key, DeviceShadowDTO.PropertyMetadata.builder()
                        .timestamp(timestamp)
                        .build());
                });
            }
            metadata.setReported(reportedMetadata);
        }
        
        return metadata;
    }
}
