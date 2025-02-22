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
package com.enjoyiot.module.eiot.api.device.dto;


import com.enjoyiot.module.eiot.api.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceInfo  extends BaseModel {

    public static Integer ONLINE= 1;
    public static Integer OFFLINE=0;
    public static Integer STATE_NOT_ACTIVE=2;
    public static Integer STATE_LOCK=3;
    public static Integer NODE_TYPE_GATEWAY=0;
    public static Integer NODE_TYPE_DEVICE=1;
    public static Integer NODE_TYPE_SUB_DEVICE=2;
    public static Integer NODE_TYPE_VIDEO=2;

    private Long id;

    private String name;

    /**
     * 产品key
     */
    private String productKey;

    private String dn;

    /**
     * 设备型号
     */
    private String model;

    /**
     * 设备密钥
     */
    private String secret;

    private Long parentId;

    /**
     * 所属平台用户ID
     */
    private String uid;

    /**
     * 关联子用户ID列表
     */
    private List<String> subUid = new ArrayList<>();

    /**
     * 设备定位对象
     */
//    private Locate locate = new Locate();

    /**
     * 设备在离线状态
     */
    private Integer state;

    /**
     * 设备属性
     */
    private Map<String, ?> property = new HashMap<>();

    /**
     * 设备标签
     */
    private Map<String, Tag> tag = new HashMap<>();

//    /**
//     * 设备所属分组
//     */
//    private Map<String, Group> group = new HashMap<>();


    public boolean isOnline() {
        return state != null && state==1;
    }

    public void setOnline(Boolean online){
        if (online){
            this.state = 1;
            return;
        }
        this.state = 0;

    }

    /**
     * 离线时间
     */
    private Long offlineTime;
    /**
     * 在线时间
     */
    private Long onlineTime;
//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class Locate {
//
//        private String longitude;
//
//        private String latitude;
//
//    }
//
//
//
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Tag {
        private String id;
        private String name;
        private String value;
    }
//
//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class Group {
//        private String id;
//        private String name;
//    }



    private String addr;


    private String firmVersion;

    private Long tenantId;

    private String serialNo;

    private Boolean transparent;


    private Integer nodeType;

}
