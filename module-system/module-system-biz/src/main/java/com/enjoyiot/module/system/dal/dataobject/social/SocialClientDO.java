
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
package com.enjoyiot.module.system.dal.dataobject.social;

import com.enjoyiot.framework.common.enums.CommonStatusEnum;
import com.enjoyiot.framework.common.enums.UserTypeEnum;
import com.enjoyiot.framework.tenant.core.db.TenantBaseDO;
import com.enjoyiot.module.system.enums.social.SocialTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xingyuv.jushauth.config.AuthConfig;
import lombok.*;

/**
 * 社交客户端 DO
 *
 * 对应 {@link AuthConfig} 配置，满足不同租户，有自己的客户端配置，实现社交（三方）登录
 *
 * @author EnjoyIot
 */
@TableName(value = "system_social_client", autoResultMap = true)
@KeySequence("system_social_client_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialClientDO extends TenantBaseDO {

    /**
     * 编号，自增
     */
    @TableId
    private Long id;
    /**
     * 应用名
     */
    private String name;
    /**
     * 社交类型
     *
     * 枚举 {@link SocialTypeEnum}
     */
    private Integer socialType;
    /**
     * 用户类型
     *
     * 目的：不同用户类型，对应不同的小程序，需要自己的配置
     *
     * 枚举 {@link UserTypeEnum}
     */
    private Integer userType;
    /**
     * 状态
     *
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;

    /**
     * 客户端 id
     */
    private String clientId;
    /**
     * 客户端 Secret
     */
    private String clientSecret;

    /**
     * 代理编号
     *
     * 目前只有部分“社交类型”在使用：
     * 1. 企业微信：对应授权方的网页应用 ID
     */
    private String agentId;

}
