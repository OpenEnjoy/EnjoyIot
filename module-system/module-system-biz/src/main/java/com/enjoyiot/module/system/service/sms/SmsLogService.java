
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
package com.enjoyiot.module.system.service.sms;

import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.system.controller.admin.sms.vo.log.SmsLogPageReqVO;
import com.enjoyiot.module.system.dal.dataobject.sms.SmsLogDO;
import com.enjoyiot.module.system.dal.dataobject.sms.SmsTemplateDO;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 短信日志 Service 接口
 *
 * @author zzf
 * @date 13:48 2021/3/2
 */
public interface SmsLogService {

    /**
     * 创建短信日志
     *
     * @param mobile 手机号
     * @param userId 用户编号
     * @param userType 用户类型
     * @param isSend 是否发送
     * @param template 短信模板
     * @param templateContent 短信内容
     * @param templateParams 短信参数
     * @return 发送日志编号
     */
    Long createSmsLog(String mobile, Long userId, Integer userType, Boolean isSend,
                      SmsTemplateDO template, String templateContent, Map<String, Object> templateParams);

    /**
     * 更新日志的发送结果
     *
     * @param id 日志编号
     * @param success 发送是否成功
     * @param apiSendCode 短信 API 发送结果的编码
     * @param apiSendMsg 短信 API 发送失败的提示
     * @param apiRequestId 短信 API 发送返回的唯一请求 ID
     * @param apiSerialNo 短信 API 发送返回的序号
     */
    void updateSmsSendResult(Long id, Boolean success,
                             String apiSendCode, String apiSendMsg,
                             String apiRequestId, String apiSerialNo);

    /**
     * 更新日志的接收结果
     *
     * @param id 日志编号
     * @param success 是否接收成功
     * @param receiveTime 用户接收时间
     * @param apiReceiveCode API 接收结果的编码
     * @param apiReceiveMsg API 接收结果的说明
     */
    void updateSmsReceiveResult(Long id, Boolean success,
                                LocalDateTime receiveTime, String apiReceiveCode, String apiReceiveMsg);

    /**
     * 获得短信日志分页
     *
     * @param pageReqVO 分页查询
     * @return 短信日志分页
     */
    PageResult<SmsLogDO> getSmsLogPage(SmsLogPageReqVO pageReqVO);

}
