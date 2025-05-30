package com.enjoyiot.eiot.component.udp.parser.uwb;


import com.enjoyiot.eiot.component.udp.DTO.UWBDataBaseDTO;
import com.enjoyiot.eiot.component.udp.enums.TagsForwardedTypeEnum;

/**
 * @title: MessageProcessor
 * @description:
 * @date: 2025/4/19
 * @author: stuil
 * @copyright: Copyright (c) 2025
 * @version: 1.0
 */

public interface UWBMessageProcessor {
    /**
     * 当前处理器支持的消息类型
     */
    TagsForwardedTypeEnum type();

    /**
     * 将原始字符串解析为具体实体
     * @throws Exception 当格式不合法时抛出
     */
    UWBDataBaseDTO parse(String dataString) throws Exception;

    /**
     * 数据处理逻辑，如推送、入库前准备
     */
    void handle(UWBDataBaseDTO data);

    /**
     * 批量持久化到数据库
     */
    void saveDataToDatabase();

}
