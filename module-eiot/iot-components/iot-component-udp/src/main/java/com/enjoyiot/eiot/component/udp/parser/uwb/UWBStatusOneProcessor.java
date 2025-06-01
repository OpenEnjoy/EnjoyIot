package com.enjoyiot.eiot.component.udp.parser.uwb;

import com.alibaba.fastjson.JSON;
import com.enjoyiot.eiot.component.udp.DTO.UWBDataBaseDTO;
import com.enjoyiot.eiot.component.udp.DTO.UWBForwardedDataStatusOneDTO;
import com.enjoyiot.eiot.component.udp.enums.TagsForwardedTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @title: DisplayProcessor（status1）
 * @description:
 * @date: 2025/4/19
 * @author: stuil
 * @copyright: Copyright (c) 2025
 * @version: 1.0
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UWBStatusOneProcessor implements UWBMessageProcessor {
    private final ConcurrentMap<String, UWBForwardedDataStatusOneDTO> buffer = new ConcurrentHashMap<>();
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private static final int BATCH_SIZE = 500;

    @Override
    public TagsForwardedTypeEnum type() {
        return TagsForwardedTypeEnum.STATUS1;
    }

    @Override
    public UWBDataBaseDTO parse(String dataString) {
        try {
            return UWBMessageParserFactory.parse(dataString);
        } catch (Exception e) {
            log.error("STATUS1 解析失败: {}", dataString, e);
            return new UWBForwardedDataStatusOneDTO();
        }
    }

    @Override
    public void handle(UWBDataBaseDTO data) {
        if (data instanceof UWBForwardedDataStatusOneDTO) {
            UWBForwardedDataStatusOneDTO uwbForwardedDataStatusOne = (UWBForwardedDataStatusOneDTO) data;
            buffer.put(uwbForwardedDataStatusOne.getDevId()+":"
                    + (StringUtils.hasText(uwbForwardedDataStatusOne.getSubType())?uwbForwardedDataStatusOne.getSubType():"subType"),
                    uwbForwardedDataStatusOne);
        }
    }

    @Override
    public void saveDataToDatabase() {
        // todo  按需求入库
    }


  //  @Scheduled(fixedDelayString = "${netty.data.locationTagUdp.timeIntervalMs:5000}")
    public void flush() {
       // saveDataToDatabase();
    }

    public static UWBForwardedDataStatusOneDTO dataToStatusOne(String[] parts) {
        if (parts == null) {
            log.error("STATUS1 数据格式无效[parts == null]:");
            return new UWBForwardedDataStatusOneDTO();
        }
        // status1 的数据当前有两种 基站和标签
        // 标签三种
        // status1:[LEN],TAG,[DEVID],[TIMESTAMP],[VBAT],[LAYID],[VELO]
        // status1:[LEN],TAG,[DEVID],[TIMESTAMP],[VBAT],[LAYID],[VELO],[SOS]
        // status1:[LEN],TAG,[DEVID],[TIMESTAMP],[VBAT],[LAYID],[VELO],[SOS],[NAME],[HWV],[FWV]
        // 基站两种
        // status1:[LEN],ANC,[DEVID],[TIMESTAMP],[IPV4]
        // status1:[LEN],ANC,[DEVID],[TIMESTAMP],[IPV4],[HWV],[FWV],[RGNID],[X],[Y],[Z],[NAME]
        try {
            String subtype = parts[1];
            if ("TAG".equals(subtype)) {
                return parseTag(parts);
            } else if ("ANC".equals(subtype)) {
                return parseAnc(parts);
            } else {
                log.error("未知 STATUS1 子类型: {}", subtype);
                return new UWBForwardedDataStatusOneDTO();
            }
        } catch (NumberFormatException e) {
            log.error("STATUS1 数值转换异常: {}", Arrays.toString(parts), e);
            return new UWBForwardedDataStatusOneDTO();
        }
    }
    /**
     * 标签处理
     * @param parts
     * @date 2025/4/21 9:54
     * @return {@link  }
     * @author stuil
     */

    private static UWBForwardedDataStatusOneDTO parseTag(String[] parts) {
        if(ObjectUtils.isEmpty(parts) || parts.length < 7 ){
            log.error(String.format("数据格式无效[dataToStatusOneTag],%s", JSON.toJSONString(parts)));
            return new UWBForwardedDataStatusOneDTO();
        }
        UWBForwardedDataStatusOneDTO uwbForwardedDataStatusOneDTO = new UWBForwardedDataStatusOneDTO();
        uwbForwardedDataStatusOneDTO.setLen(Integer.parseInt(parts[0].trim()));
        uwbForwardedDataStatusOneDTO.setSubType(parts[1].trim());
        uwbForwardedDataStatusOneDTO.setDevId(parts[2].trim());
        uwbForwardedDataStatusOneDTO.setUwbTimestamp(parts[3].trim());
        uwbForwardedDataStatusOneDTO.setVbat(parts[4].trim());
        uwbForwardedDataStatusOneDTO.setLayId(Integer.parseInt(parts[5].trim()));
        uwbForwardedDataStatusOneDTO.setVelo(parts[6].trim()+","+parts[7].trim()+","+parts[8].trim());
        uwbForwardedDataStatusOneDTO.setVeloOne(parts[6].trim());
        uwbForwardedDataStatusOneDTO.setVeloTwo(parts[7].trim());
        uwbForwardedDataStatusOneDTO.setVeloThree(parts[8].trim());
        if(parts.length == 9){
            // status1:[LEN],TAG,[DEVID],[TIMESTAMP],[VBAT],[LAYID],[VELO]
            return uwbForwardedDataStatusOneDTO;
        }
        if(parts.length == 10){
            // status1:[LEN],TAG,[DEVID],[TIMESTAMP],[VBAT],[LAYID],[VELO],[SOS]
            uwbForwardedDataStatusOneDTO.setSos(parts[9].trim());
            return uwbForwardedDataStatusOneDTO;
        }
        if(parts.length == 13){
            // status1:[LEN],TAG,[DEVID],[TIMESTAMP],[VBAT],[LAYID],[VELO],[SOS],[NAME],[HWV],[FWV]
            uwbForwardedDataStatusOneDTO.setName(parts[10].trim());
            uwbForwardedDataStatusOneDTO.setHwv(parts[11].trim());
            uwbForwardedDataStatusOneDTO.setFwv(parts[12].trim());
            return uwbForwardedDataStatusOneDTO;
        }
        return new UWBForwardedDataStatusOneDTO();
    }


    /**
     * 基站处理
     * @param parts
     * @date 2025/4/21 9:54
     * @return {@link  }
     * @author stuil
     */
    private static UWBForwardedDataStatusOneDTO parseAnc(String[] parts) {
        if(ObjectUtils.isEmpty(parts) || parts.length < 5 ){
            log.error(String.format("数据格式无效[dataToStatusOneAnc],%s", JSON.toJSONString(parts)));
            return new UWBForwardedDataStatusOneDTO();
        }
        UWBForwardedDataStatusOneDTO uwbForwardedDataStatusOneDTO = new UWBForwardedDataStatusOneDTO();
        uwbForwardedDataStatusOneDTO.setLen(Integer.parseInt(parts[0].trim()));
        uwbForwardedDataStatusOneDTO.setSubType(parts[1].trim());
        uwbForwardedDataStatusOneDTO.setDevId(parts[2].trim());
        uwbForwardedDataStatusOneDTO.setUwbTimestamp(parts[3].trim());
        uwbForwardedDataStatusOneDTO.setIpv4(parts[4].trim());
        if(parts.length == 5){
            // status1:[LEN],ANC,[DEVID],[TIMESTAMP],[IPV4]
            return uwbForwardedDataStatusOneDTO;
        }
        if(parts.length == 12){
            // status1:[LEN],ANC,[DEVID],[TIMESTAMP],[IPV4],[HWV],[FWV],[RGNID],[X],[Y],[Z],[NAME]
            uwbForwardedDataStatusOneDTO.setHwv(parts[5].trim());
            uwbForwardedDataStatusOneDTO.setFwv(parts[6].trim());
            uwbForwardedDataStatusOneDTO.setRgnid(parts[7].trim());
            uwbForwardedDataStatusOneDTO.setX(parts[8].trim());
            uwbForwardedDataStatusOneDTO.setY(parts[9].trim());
            uwbForwardedDataStatusOneDTO.setZ(parts[10].trim());
            uwbForwardedDataStatusOneDTO.setName(parts[11].trim());
            return uwbForwardedDataStatusOneDTO;
        }
        return new UWBForwardedDataStatusOneDTO();
    }
}