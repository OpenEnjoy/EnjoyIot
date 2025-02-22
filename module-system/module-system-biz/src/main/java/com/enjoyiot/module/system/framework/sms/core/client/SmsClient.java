
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
package com.enjoyiot.module.system.framework.sms.core.client;

import com.enjoyiot.framework.common.core.KeyValue;
import com.enjoyiot.module.system.framework.sms.core.client.dto.SmsReceiveRespDTO;
import com.enjoyiot.module.system.framework.sms.core.client.dto.SmsSendRespDTO;
import com.enjoyiot.module.system.framework.sms.core.client.dto.SmsTemplateRespDTO;

import java.util.List;

/**
 * 短信客户端，用于对接各短信平台的 SDK，实现短信发送等功能
 *
 * @author zzf
 * @since 2021/1/25 14:14
 */
public interface SmsClient {

    /**
     * 获得渠道编号
     *
     * @return 渠道编号
     */
    Long getId();

    /**
     * 发送消息
     *
     * @param logId 日志编号
     * @param mobile 手机号
     * @param apiTemplateId 短信 API 的模板编号
     * @param templateParams 短信模板参数。通过 List 数组，保证参数的顺序
     * @return 短信发送结果
     */
    SmsSendRespDTO sendSms(Long logId, String mobile, String apiTemplateId,
                           List<KeyValue<String, Object>> templateParams) throws Throwable;

    /**
     * 解析接收短信的接收结果
     *
     * @param text 结果
     * @return 结果内容
     * @throws Throwable 当解析 text 发生异常时，则会抛出异常
     */
    List<SmsReceiveRespDTO> parseSmsReceiveStatus(String text) throws Throwable;

    /**
     * 查询指定的短信模板
     *
     * 如果查询失败，则返回 null 空
     *
     * @param apiTemplateId 短信 API 的模板编号
     * @return 短信模板
     */
    SmsTemplateRespDTO getSmsTemplate(String apiTemplateId) throws Throwable;

}
