
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

import com.enjoyiot.module.system.dal.dataobject.oauth2.OAuth2ApproveDO;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * OAuth2 批准 Service 接口
 *
 * 从功能上，和 Spring Security OAuth 的 ApprovalStoreUserApprovalHandler 的功能，记录用户针对指定客户端的授权，减少手动确定。
 *
 * @author EnjoyIot
 */
public interface OAuth2ApproveService {

    /**
     * 获得指定用户，针对指定客户端的指定授权，是否通过
     *
     * 参考 ApprovalStoreUserApprovalHandler 的 checkForPreApproval 方法
     *
     * @param userId 用户编号
     * @param userType 用户类型
     * @param clientId 客户端编号
     * @param requestedScopes 授权范围
     * @return 是否授权通过
     */
    boolean checkForPreApproval(Long userId, Integer userType, String clientId, Collection<String> requestedScopes);

    /**
     * 在用户发起批准时，基于 scopes 的选项，计算最终是否通过
     *
     * @param userId 用户编号
     * @param userType 用户类型
     * @param clientId 客户端编号
     * @param requestedScopes 授权范围
     * @return 是否授权通过
     */
    boolean updateAfterApproval(Long userId, Integer userType, String clientId, Map<String, Boolean> requestedScopes);

    /**
     * 获得用户的批准列表，排除已过期的
     *
     * @param userId 用户编号
     * @param userType 用户类型
     * @param clientId 客户端编号
     * @return 是否授权通过
     */
    List<OAuth2ApproveDO> getApproveList(Long userId, Integer userType, String clientId);

}
