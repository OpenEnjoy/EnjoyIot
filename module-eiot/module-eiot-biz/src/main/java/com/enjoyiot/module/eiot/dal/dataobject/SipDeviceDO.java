
/*
 *
 *  * | Licensed 未经许可不能去掉「Enjoy-iot」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com | Tel: 19918996474
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2025] [Enjoy-iot] | Tel: 19918996474
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
package com.enjoyiot.module.eiot.dal.dataobject;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.enjoyiot.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 监控设备 DO
 *
 * @author EnjoyIot
 */
@TableName("sip_device")
@KeySequence("sip_device_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SipDeviceDO extends BaseDO {

    /**
     * 设备ID
     */
    @TableId
    private Long deviceId;
    /**
     * 产品key
     */
    private String productKey;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 设备SipID
     */
    private String deviceSipId;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 厂商名称
     */
    private String manufacturer;
    /**
     * 产品型号
     */
    private String model;
    /**
     * 固件版本
     */
    private String firmware;
    /**
     * 传输模式
     */
    private String transport;
    /**
     * 流模式
     */
    private String streamMode;
    /**
     * 在线状态
     */
    private String online;
    /**
     * 注册时间
     */
    private LocalDateTime registerTime;
    /**
     * 最后上线时间
     */
    private LocalDateTime lastConnectTime;
    /**
     * 激活时间
     */
    private LocalDateTime activeTime;
    /**
     * 设备入网IP
     */
    private String ip;
    /**
     * 设备接入端口号
     */
    private Long port;
    /**
     * 设备地址
     */
    private String hostAddress;
    /**
     * 备注
     */
    private String remark;

}
