
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
import com.enjoyiot.framework.tenant.core.db.TenantBaseDO;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 监控设备通道信息 DO
 *
 * @author EnjoyIot
 */
@TableName("sip_device_channel")
@KeySequence("sip_device_channel_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceChannelDO extends TenantBaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 租户名称
     */
    private String tenantName;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品ID
     */
    private Long userId;
    /**
     * 产品名称
     */
    private String userName;
    /**
     * 设备SipID
     */
    private String deviceSipId;
    /**
     * 通道SipID
     */
    private String channelSipId;
    /**
     * 通道名称
     */
    private String channelName;
    /**
     * 注册时间
     */
    private LocalDateTime registerTime;
    /**
     * 设备类型
     */
    private String deviceType;
    /**
     * 通道类型
     */
    private String channelType;
    /**
     * 城市编码
     */
    private String cityCode;
    /**
     * 行政区域
     */
    private String civilCode;
    /**
     * 厂商名称
     */
    private String manufacture;
    /**
     * 产品型号
     */
    private String model;
    /**
     * 设备归属
     */
    private String owner;
    /**
     * 警区
     */
    private String block;
    /**
     * 安装地址
     */
    private String address;
    /**
     * 父级id
     */
    private String parentId;
    /**
     * 设备入网IP
     */
    private String ipAddress;
    /**
     * 设备接入端口号
     */
    private Long port;
    /**
     * 密码
     */
    private String password;
    /**
     * PTZ类型
     */
    private Long pTZType;
    /**
     * PTZ类型描述字符串
     */
    private String pTZTypeText;
    /**
     * 设备状态（1-未激活，2-禁用，3-在线，4-离线）
     */
    private Integer status;
    /**
     * 设备经度
     */
    private Double longitude;
    /**
     * 设备纬度
     */
    private Double latitude;
    /**
     * 流媒体ID
     */
    private String streamId;
    /**
     * 子设备数
     */
    private Long subCount;
    /**
     * 是否有子设备（1-有, 0-没有）
     */
    private Integer parental;
    /**
     * 是否含有音频（1-有, 0-没有）
     */
    private Integer hasAudio;
    /**
     * 备注
     */
    private String remark;
    /**
     * productKey
     */
    private String productKey;
    /**
     * 机构id
     */
    private Long deptId;

}
