
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
package com.enjoyiot.module.system.util.oauth2;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.enjoyiot.framework.common.util.http.HttpUtils;
import com.enjoyiot.framework.security.core.util.SecurityFrameworkUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * OAuth2 相关的工具类
 *
 * @author EnjoyIot
 */
public class OAuth2Utils {

    /**
     * 构建授权码模式下，重定向的 URI
     *
     * copy from Spring Security OAuth2 的 AuthorizationEndpoint 类的 getSuccessfulRedirect 方法
     *
     * @param redirectUri 重定向 URI
     * @param authorizationCode 授权码
     * @param state 状态
     * @return 授权码模式下的重定向 URI
     */
    public static String buildAuthorizationCodeRedirectUri(String redirectUri, String authorizationCode, String state) {
        Map<String, String> query = new LinkedHashMap<>();
        query.put("code", authorizationCode);
        if (state != null) {
            query.put("state", state);
        }
        return HttpUtils.append(redirectUri, query, null, false);
    }

    /**
     * 构建简化模式下，重定向的 URI
     *
     * copy from Spring Security OAuth2 的 AuthorizationEndpoint 类的 appendAccessToken 方法
     *
     * @param redirectUri 重定向 URI
     * @param accessToken 访问令牌
     * @param state 状态
     * @param expireTime 过期时间
     * @param scopes 授权范围
     * @param additionalInformation 附加信息
     * @return 简化授权模式下的重定向 URI
     */
    public static String buildImplicitRedirectUri(String redirectUri, String accessToken, String state, LocalDateTime expireTime,
                                                  Collection<String> scopes, Map<String, Object> additionalInformation) {
        Map<String, Object> vars = new LinkedHashMap<String, Object>();
        Map<String, String> keys = new HashMap<String, String>();
        vars.put("access_token", accessToken);
        vars.put("token_type", SecurityFrameworkUtils.AUTHORIZATION_BEARER.toLowerCase());
        if (state != null) {
            vars.put("state", state);
        }
        if (expireTime != null) {
            vars.put("expires_in", getExpiresIn(expireTime));
        }
        if (CollUtil.isNotEmpty(scopes)) {
            vars.put("scope", buildScopeStr(scopes));
        }
        if (CollUtil.isNotEmpty(additionalInformation)) {
            for (String key : additionalInformation.keySet()) {
                Object value = additionalInformation.get(key);
                if (value != null) {
                    keys.put("extra_" + key, key);
                    vars.put("extra_" + key, value);
                }
            }
        }
        // Do not include the refresh token (even if there is one)
        return HttpUtils.append(redirectUri, vars, keys, true);
    }

    public static String buildUnsuccessfulRedirect(String redirectUri, String responseType, String state,
                                                   String error, String description) {
        Map<String, String> query = new LinkedHashMap<String, String>();
        query.put("error", error);
        query.put("error_description", description);
        if (state != null) {
            query.put("state", state);
        }
        return HttpUtils.append(redirectUri, query, null, !responseType.contains("code"));
    }

    public static long getExpiresIn(LocalDateTime expireTime) {
        return LocalDateTimeUtil.between(LocalDateTime.now(), expireTime, ChronoUnit.SECONDS);
    }

    public static String buildScopeStr(Collection<String> scopes) {
        return CollUtil.join(scopes, " ");
    }

    public static List<String> buildScopes(String scope) {
        return StrUtil.split(scope, ' ');
    }

}
