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

package com.enjoyiot.eiot;


import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.framework.common.pojo.PageResult;

import java.util.List;

public interface IThingModelMessageData {

    /**
     * 按消息类型和标识符取设备消息
     *
     * @param deviceId   设备id
     * @param type       消息类型
     * @param identifier 标识符
     * @param page       页码
     * @param size       页大小
     */
    PageResult<ThingModelMessage> findByTypeAndIdentifier(Long deviceId, String type,
                                                          String identifier, int page, int size);

    /**
     * 按用户统计时间段内每小时上报次数
     *
     * @param uid   用户id
     * @param start 开始时间戳
     * @param end   结束时间戳
     */
    List<TimeData> getDeviceMessageStatsWithUid(String uid, long start, long end);

    /**
     * 按用户统计时间段内上行消息
     * @param uid   用户id
     * @param start 开始时间戳
     * @param end   结束时间戳
     */
    List<TimeData> getDeviceUpMessageStatsWithUid(String uid, Long start, Long end);

    /**
     * 按用户统计时间段内下行
     * @param uid   用户id
     * @param start 开始时间戳
     * @param end   结束时间戳
     */
    List<TimeData> getDeviceDownMessageStatsWithUid(String uid, Long start, Long end);


    /**
     * 查询指定设备集类型并按时间倒序
     *
     * @param deviceIds   设备ids
     * @param type       消息类型
     * @param identifier 标识符
     * @param page       页码
     * @param size       页大小
     */
    PageResult<ThingModelMessage> findByTypeAndDeviceIds(List<Long> deviceIds, String type,
                                                      String identifier, int page, int size);

    void add(ThingModelMessage msg);

    long count();
}
