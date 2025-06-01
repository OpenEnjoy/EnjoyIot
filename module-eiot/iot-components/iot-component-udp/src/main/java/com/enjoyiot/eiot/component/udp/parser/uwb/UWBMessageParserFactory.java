package com.enjoyiot.eiot.component.udp.parser.uwb;

import com.enjoyiot.eiot.component.udp.DTO.UWBDataBaseDTO;
import com.enjoyiot.eiot.component.udp.enums.TagsForwardedTypeEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.EnumMap;

/**
 * @title: UWBMessageParserFactory
 * @description:
 * @date: 2025/4/19
 * @author: stuil
 * @copyright: Copyright (c) 2025
 * @version: 1.0
 */
@Slf4j
public class UWBMessageParserFactory {
    private static final EnumMap<TagsForwardedTypeEnum, MessageParserFunction> parserMap =
            new EnumMap<>(TagsForwardedTypeEnum.class);

    static {
        // 注册内置解析函数
        registerParser(TagsForwardedTypeEnum.DISPLAY, UWBDisplayProcessor::dataToDisplay);
        registerParser(TagsForwardedTypeEnum.STATUS1, UWBStatusOneProcessor::dataToStatusOne);
    }

    public static void registerParser(TagsForwardedTypeEnum type, MessageParserFunction function) {
        parserMap.put(type, function);
    }

    public static UWBDataBaseDTO parse(String data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        data = data.trim();
        int idx = data.indexOf(':');
        if (idx <= 0) {
            log.warn("格式错误，不含冒号或位置不对: {}", data);
            return null;
        }

        String key = data.substring(0, idx).trim();
        String payload = data.substring(idx + 1).trim();
        // split 时同时去除各项前后空白
        String[] parts = payload.split("\\s*,\\s*");

        TagsForwardedTypeEnum type = TagsForwardedTypeEnum.getEnum(key);
        if (type == null) {
            log.warn("未知消息类型: {}", key);
            return null;
        }

        MessageParserFunction func = parserMap.get(type);
        if (func == null) {
            log.warn("未注册解析函数: {}", type);
            return null;
        }

        try {
            return func.apply(parts);
        } catch (Exception e) {
            log.error("解析异常(type={}): {}", type, Arrays.toString(parts), e);
            return null;
        }
    }

    /**
     * 解析函数接口：String[] -> UWBDataBaseDTO
     */
    @FunctionalInterface
    public interface MessageParserFunction {
        UWBDataBaseDTO apply(String[] parts);
    }
}