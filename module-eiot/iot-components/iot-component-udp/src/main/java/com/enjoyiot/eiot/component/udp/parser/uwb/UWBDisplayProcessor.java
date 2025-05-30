package com.enjoyiot.eiot.component.udp.parser.uwb;

import com.alibaba.fastjson.JSON;
import com.enjoyiot.eiot.component.udp.DTO.UWBDataBaseDTO;
import com.enjoyiot.eiot.component.udp.DTO.UWBForwardedDataDisplayDTO;
import com.enjoyiot.eiot.component.udp.enums.TagsForwardedTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @title: DisplayProcessor
 * @description:
 * @date: 2025/4/19
 * @author: stuil
 * @copyright: Copyright (c) 2025
 * @version: 1.0
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UWBDisplayProcessor implements UWBMessageProcessor {

    private final ConcurrentMap<String, UWBForwardedDataDisplayDTO> buffer = new ConcurrentHashMap<>();
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private static final int BATCH_SIZE = 500;

    @Override
    public TagsForwardedTypeEnum type() {
        return TagsForwardedTypeEnum.DISPLAY;
    }

    @Override
    public UWBDataBaseDTO parse(String dataString) {
        try {
            return UWBMessageParserFactory.parse(dataString);
        } catch (Exception e) {
            log.error("DISPLAY 解析失败: {}", dataString, e);
            return new UWBForwardedDataDisplayDTO();
        }
    }

    @Override
    public void handle(UWBDataBaseDTO data) {
        if (data instanceof UWBForwardedDataDisplayDTO) {
            UWBForwardedDataDisplayDTO uwbForwardedDataDisplay = (UWBForwardedDataDisplayDTO) data;
            buffer.put(uwbForwardedDataDisplay.getDevId(), uwbForwardedDataDisplay);
        }
    }

    @Override
    public void saveDataToDatabase() {
        // todo  按需求入库
    }

    //@Scheduled(fixedDelayString = "${netty.data.locationTagUdp.timeIntervalMs:5000}")
    public void flush() {
       // saveDataToDatabase();
    }

    /**
     * 从拆分数组构建 Display 实体
     */
    public static UWBForwardedDataDisplayDTO dataToDisplay(String[] parts) {
        // 因为已经去掉了 :  部分，剩下dataLen 长度
        try {
            // Display的数据有两种
            // display:[LEN],[DEVID],[SEQ],[TIMESTAMP],[LAYID],[POS] 和
            // display:[LEN],[DEVID],[SEQ],[TIMESTAMP],[LAYID],[POS],[GID]
            if (!ObjectUtils.isEmpty(parts) && (parts.length == 8 || parts.length == 9)) {
                UWBForwardedDataDisplayDTO displayEntity = new UWBForwardedDataDisplayDTO();
                displayEntity.setLen(Integer.valueOf(parts[0].trim()));
                displayEntity.setDevId(parts[1].trim());
                displayEntity.setSeq(Integer.parseInt(parts[2].trim()));
                displayEntity.setUwbTimestamp(parts[3].trim());
                displayEntity.setLayId(Integer.valueOf(parts[4].trim()));
                displayEntity.setPos(parts[5].trim() + "," + parts[6].trim() + "," + parts[7].trim());
                displayEntity.setPosX(parts[5].trim());
                displayEntity.setPosY(parts[6].trim());
                displayEntity.setPosZ(parts[7].trim());
                if (parts.length == 9) {
                    displayEntity.setGid(Integer.valueOf(parts[8].trim()));
                }
                return displayEntity;
            } else {
                log.error(String.format("数据格式无效,%s", JSON.toJSONString(parts)));
                return new UWBForwardedDataDisplayDTO();
            }
        } catch (NumberFormatException e) {
            log.error(String.format("数据格式异常，%s", JSON.toJSONString(parts)));
            return new UWBForwardedDataDisplayDTO();
        }
    }
}
