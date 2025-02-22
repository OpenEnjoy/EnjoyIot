
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
package com.enjoyiot.module.system.service.oauth2;

import com.enjoyiot.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;

import java.util.List;

/**
 * OAuth2 授予 Service 接口
 *
 * 从功能上，和 Spring Security OAuth 的 TokenGranter 的功能，提供访问令牌、刷新令牌的操作
 *
 * 将自身的 AdminUser 用户，授权给第三方应用，采用 OAuth2.0 的协议。
 *
 * 问题：为什么自身也作为一个第三方应用，也走这套流程呢？
 * 回复：当然可以这么做，采用 password 模式。考虑到大多数开发者使用不到这个特性，OAuth2.0 毕竟有一定学习成本，所以暂时没有采取这种方式。
 *
 * @author EnjoyIot
 */
public interface OAuth2GrantService {

    /**
     * 简化模式
     *
     * 对应 Spring Security OAuth2 的 ImplicitTokenGranter 功能
     *
     * @param userId 用户编号
     * @param userType 用户类型
     * @param clientId 客户端编号
     * @param scopes 授权范围
     * @return 访问令牌
     */
    OAuth2AccessTokenDO grantImplicit(Long userId, Integer userType,
                                      String clientId, List<String> scopes);

    /**
     * 授权码模式，第一阶段，获得 code 授权码
     *
     * 对应 Spring Security OAuth2 的 AuthorizationEndpoint 的 generateCode 方法
     *
     * @param userId 用户编号
     * @param userType 用户类型
     * @param clientId 客户端编号
     * @param scopes 授权范围
     * @param redirectUri 重定向 URI
     * @param state 状态
     * @return 授权码
     */
    String grantAuthorizationCodeForCode(Long userId, Integer userType,
                                         String clientId, List<String> scopes,
                                         String redirectUri, String state);

    /**
     * 授权码模式，第二阶段，获得 accessToken 访问令牌
     *
     * 对应 Spring Security OAuth2 的 AuthorizationCodeTokenGranter 功能
     *
     * @param clientId 客户端编号
     * @param code 授权码
     * @param redirectUri 重定向 URI
     * @param state 状态
     * @return 访问令牌
     */
    OAuth2AccessTokenDO grantAuthorizationCodeForAccessToken(String clientId, String code,
                                                             String redirectUri, String state);

    /**
     * 密码模式
     *
     * 对应 Spring Security OAuth2 的 ResourceOwnerPasswordTokenGranter 功能
     *
     * @param username 账号
     * @param password 密码
     * @param clientId 客户端编号
     * @param scopes 授权范围
     * @return 访问令牌
     */
    OAuth2AccessTokenDO grantPassword(String username, String password,
                                      String clientId, List<String> scopes);

    /**
     * 刷新模式
     *
     * 对应 Spring Security OAuth2 的 ResourceOwnerPasswordTokenGranter 功能
     *
     * @param refreshToken 刷新令牌
     * @param clientId 客户端编号
     * @return 访问令牌
     */
    OAuth2AccessTokenDO grantRefreshToken(String refreshToken, String clientId);

    /**
     * 客户端模式
     *
     * 对应 Spring Security OAuth2 的 ClientCredentialsTokenGranter 功能
     *
     * @param clientId 客户端编号
     * @param scopes 授权范围
     * @return 访问令牌
     */
    OAuth2AccessTokenDO grantClientCredentials(String clientId, List<String> scopes);

    /**
     * 移除访问令牌
     *
     * 对应 Spring Security OAuth2 的 ConsumerTokenServices 的 revokeToken 方法
     *
     * @param accessToken 访问令牌
     * @param clientId 客户端编号
     * @return 是否移除到
     */
    boolean revokeToken(String clientId, String accessToken);

}
