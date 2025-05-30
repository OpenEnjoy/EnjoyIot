package com.enjoyiot.eiot.component.udp.parser.uwb;

import com.alibaba.fastjson.JSON;
import com.enjoyiot.eiot.component.udp.DTO.UWBDataBaseDTO;
import com.enjoyiot.eiot.component.udp.DTO.UWBForwardedDataDisplayDTO;
import com.enjoyiot.eiot.component.udp.enums.TagsForwardedTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @title: UWBForwardedDataProcessing
 * @description: UWB数据转发解析
 * @date: 2025/4/19
 * @author: stuil
 * @copyright: Copyright (c) 2025
 * @version: 1.0
 */
@Component
@Slf4j
public class UWBForwardedDataProcessing {


    /**
     * 根据前缀自动分发解析
     */
    public static UWBDataBaseDTO parsingData(String dataString) {
        return UWBMessageParserFactory.parse(dataString);
    }



    public static Map<String, Object> posDisplayTOMap(UWBForwardedDataDisplayDTO uwbForwardedDataDisplayDTO) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            if (ObjectUtils.isEmpty(uwbForwardedDataDisplayDTO)) {
                throw new NullPointerException("uwbForwardedDataDisplayEntity cannot be null");
            }
            String[] strings = uwbForwardedDataDisplayDTO.getPos().split(",");
            if (ObjectUtils.isEmpty(strings) || strings.length < 3) {
                throw new IllegalArgumentException("Invalid input array: 'strings' must have at least three elements.");
            }
            // SpringUtils.getBean(RedisCache.class).getCacheObject(getCacheKey(key));
           /* Long x = NumberUtil.mul(strings[0], "10").longValue();
            Long y = NumberUtil.mul(strings[1], "10").longValue();
            Long z = NumberUtil.mul(strings[2], "10").longValue();*/
            String ids = uwbForwardedDataDisplayDTO.getDevId();
            resultMap.put("dataType", TagsForwardedTypeEnum.DISPLAY);
            resultMap.put("time", uwbForwardedDataDisplayDTO.getUwbTimestamp());
            resultMap.put("deviceSpecies", uwbForwardedDataDisplayDTO.getDeviceSpecies());
            resultMap.put("x", uwbForwardedDataDisplayDTO.getPosX());
            resultMap.put("y", uwbForwardedDataDisplayDTO.getPosZ());
            resultMap.put("z", uwbForwardedDataDisplayDTO.getPosY());
            resultMap.put("ids", ids);
            // 使用 resultMap 进一步处理
        } catch (IllegalArgumentException | NullPointerException e) {
            log.error(String.format("数据格式异常，%s", JSON.toJSONString(uwbForwardedDataDisplayDTO)));
            log.error("数据->IllegalArgumentException | NullPointerException", e);
            // 处理非法参数或空指针异常
            return Collections.emptyMap();
        } catch (Exception e) {
            // 处理其他可能的异常
            log.error(String.format("数据格式异常，%s", JSON.toJSONString(uwbForwardedDataDisplayDTO)));
            log.error("数据->Exception", e);
            return Collections.emptyMap();
        }
        return resultMap;
    }
    public static Map<String, Object> posDisplayTOMapByString(String uwbDisplayData) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            if (!StringUtils.hasText(uwbDisplayData)) {
                throw new NullPointerException("uwbDisplayData cannot be null");
            }
            Map<String, Object> resultMapUwb = JSON.parseObject(uwbDisplayData, Map.class);
            // SpringUtils.getBean(RedisCache.class).getCacheObject(getCacheKey(key));
           /* Long x = NumberUtil.mul(strings[0], "10").longValue();
            Long y = NumberUtil.mul(strings[1], "10").longValue();
            Long z = NumberUtil.mul(strings[2], "10").longValue();*/
            String ids = resultMapUwb.getOrDefault("devId", "").toString();
            resultMap.put("dataType", TagsForwardedTypeEnum.DISPLAY);
            resultMap.put("time", resultMapUwb.getOrDefault("uwbTimestamp", "").toString());
            resultMap.put("deviceSpecies", resultMapUwb.getOrDefault("deviceSpecies", 0));
            resultMap.put("x", resultMapUwb.getOrDefault("posX", "").toString());
            resultMap.put("y", resultMapUwb.getOrDefault("posZ", "").toString());
            resultMap.put("z", resultMapUwb.getOrDefault("posY", "").toString());
            resultMap.put("devId",  ids);
            resultMap.put("ids", ids);
            // 使用 resultMap 进一步处理
        } catch (IllegalArgumentException | NullPointerException e) {
            log.error(String.format("数据格式异常，%s", uwbDisplayData));
            log.error("数据->IllegalArgumentException | NullPointerException", e);
            // 处理非法参数或空指针异常
            return Collections.emptyMap();
        } catch (Exception e) {
            // 处理其他可能的异常
            log.error(String.format("数据格式异常，%s", uwbDisplayData));
            log.error("数据->Exception", e);
            return Collections.emptyMap();
        }
        return resultMap;
    }
}
