
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

/**
 * 流媒体服务器配置 DO
 *
 * @author EnjoyIot
 */
@TableName("media_server")
@KeySequence("media_server_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaServerDO extends TenantBaseDO {

    /**
     * 流媒体配置ID
     */
    @TableId
    private Long id;
    /**
     * 服务器标识
     */
    private String serverId;
    /**
     * 租户名称
     */
    private String tenantName;
    /**
     * 使能开关
     */
    private Boolean enabled;
    /**
     * 默认播放协议
     */
    private String protocol;
    /**
     * 服务器ip
     */
    private String ip;
    /**
     * 服务器域名
     */
    private String domain;
    /**
     * 回调服务器地址
     */
    private String hookurl;
    /**
     * 流媒体密钥
     */
    private String secret;
    /**
     * http端口
     */
    private Integer portHttp;
    /**
     * https端口
     */
    private Integer portHttps;
    /**
     * rtmp端口
     */
    private Integer portRtmp;
    /**
     * rtsp端口
     */
    private Integer portRtsp;
    /**
     * RTP收流端口
     */
    private Integer rtpProxyPort;
    /**
     * 是否使用多端口模式
     */
    private Boolean rtpEnable;
    /**
     * rtp端口范围
     */
    private String rtpPortRange;
    /**
     * 录像服务端口
     */
    private Integer recordPort;
    /**
     * 是否自动同步配置ZLM
     */
    private Boolean autoConfig;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    /**
     * ws端口
     */
    private Integer portWs;

}
