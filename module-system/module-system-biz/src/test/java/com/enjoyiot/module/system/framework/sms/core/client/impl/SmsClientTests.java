
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
package com.enjoyiot.module.system.framework.sms.core.client.impl;

import cn.hutool.core.collection.ListUtil;
import com.enjoyiot.framework.common.core.KeyValue;
import com.enjoyiot.module.system.framework.sms.core.client.SmsClient;
import com.enjoyiot.module.system.framework.sms.core.client.dto.SmsSendRespDTO;
import com.enjoyiot.module.system.framework.sms.core.client.dto.SmsTemplateRespDTO;
import com.enjoyiot.module.system.framework.sms.core.property.SmsChannelProperties;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * 各种 {@link SmsClient} 的集成测试
 *
 * @author EnjoyIot
 */
public class SmsClientTests {

    // ========== 阿里云 ==========

    @Test
    @Disabled
    public void testAliyunSmsClient_getSmsTemplate() throws Throwable {
        SmsChannelProperties properties = new SmsChannelProperties()
                .setApiKey(System.getenv("SMS_ALIYUN_ACCESS_KEY"))
                .setApiSecret(System.getenv("SMS_ALIYUN_SECRET_KEY"));
        AliyunSmsClient client = new AliyunSmsClient(properties);
        // 准备参数
        String apiTemplateId = "SMS_207945135";
        // 调用
        SmsTemplateRespDTO template = client.getSmsTemplate(apiTemplateId);
        // 打印结果
        System.out.println(template);
    }

    @Test
    @Disabled
    public void testAliyunSmsClient_sendSms() throws Throwable {
        SmsChannelProperties properties = new SmsChannelProperties()
                .setApiKey(System.getenv("SMS_ALIYUN_ACCESS_KEY"))
                .setApiSecret(System.getenv("SMS_ALIYUN_SECRET_KEY"))
                .setSignature("Ballcat");
        AliyunSmsClient client = new AliyunSmsClient(properties);
        // 准备参数
        Long sendLogId = System.currentTimeMillis();
        String mobile = "15601691323";
        String apiTemplateId = "SMS_207945135";
        // 调用
        SmsSendRespDTO sendRespDTO = client.sendSms(sendLogId, mobile, apiTemplateId, ListUtil.of(new KeyValue<>("code", "1024")));
        // 打印结果
        System.out.println(sendRespDTO);
    }

    // ========== 腾讯云 ==========

    @Test
    @Disabled
    public void testTencentSmsClient_sendSms() throws Throwable {
        String sdkAppId = "1400500458";
        SmsChannelProperties properties = new SmsChannelProperties()
                .setApiKey(System.getenv("SMS_TENCENT_ACCESS_KEY") + " " + sdkAppId)
                .setApiSecret(System.getenv("SMS_TENCENT_SECRET_KEY"))
                .setSignature("EnjoyIot");
        TencentSmsClient client = new TencentSmsClient(properties);
        // 准备参数
        Long sendLogId = System.currentTimeMillis();
        String mobile = "15601691323";
        String apiTemplateId = "358212";
        // 调用
        SmsSendRespDTO sendRespDTO = client.sendSms(sendLogId, mobile, apiTemplateId, ListUtil.of(new KeyValue<>("code", "1024")));
        // 打印结果
        System.out.println(sendRespDTO);
    }

    @Test
    @Disabled
    public void testTencentSmsClient_getSmsTemplate() throws Throwable {
        String sdkAppId = "1400500458";
        SmsChannelProperties properties = new SmsChannelProperties()
                .setApiKey(System.getenv("SMS_TENCENT_ACCESS_KEY") + " " + sdkAppId)
                .setApiSecret(System.getenv("SMS_TENCENT_SECRET_KEY"))
                .setSignature("EnjoyIot");
        TencentSmsClient client = new TencentSmsClient(properties);
        // 准备参数
        String apiTemplateId = "358212";
        // 调用
        SmsTemplateRespDTO template = client.getSmsTemplate(apiTemplateId);
        // 打印结果
        System.out.println(template);
    }

    // ========== 华为云 ==========

    @Test
    @Disabled
    public void testHuaweiSmsClient_sendSms() throws Throwable {
        String sender = "x8824060312575";
        SmsChannelProperties properties = new SmsChannelProperties()
                .setApiKey(System.getenv("SMS_HUAWEI_ACCESS_KEY") + " " + sender)
                .setApiSecret(System.getenv("SMS_HUAWEI_SECRET_KEY"))
                .setSignature("runpu");
        HuaweiSmsClient client = new HuaweiSmsClient(properties);
        // 准备参数
        Long sendLogId = System.currentTimeMillis();
        String mobile = "17321315478";
        String apiTemplateId = "3644cdab863546a3b718d488659a99ef";
        List<KeyValue<String, Object>> templateParams = ListUtil.of(new KeyValue<>("code", "1024"));
        // 调用
        SmsSendRespDTO smsSendRespDTO = client.sendSms(sendLogId, mobile, apiTemplateId, templateParams);
        // 打印结果
        System.out.println(smsSendRespDTO);
    }

    // ========== 七牛云 ==========

    @Test
    @Disabled
    public void testQiniuSmsClient_sendSms() throws Throwable {
        SmsChannelProperties properties = new SmsChannelProperties()
                .setApiKey("SMS_QINIU_ACCESS_KEY")
                .setApiSecret("SMS_QINIU_SECRET_KEY");
        QiniuSmsClient client = new QiniuSmsClient(properties);
        // 准备参数
        Long sendLogId = System.currentTimeMillis();
        String mobile = "17321315478";
        String apiTemplateId = "3644cdab863546a3b718d488659a99ef";
        List<KeyValue<String, Object>> templateParams = ListUtil.of(new KeyValue<>("code", "1122"));
        // 调用
        SmsSendRespDTO smsSendRespDTO = client.sendSms(sendLogId, mobile, apiTemplateId, templateParams);
        // 打印结果
        System.out.println(smsSendRespDTO);
    }

    @Test
    @Disabled
    public void testQiniuSmsClient_getSmsTemplate() throws Throwable {
        SmsChannelProperties properties = new SmsChannelProperties()
                .setApiKey("SMS_QINIU_ACCESS_KEY")
                .setApiSecret("SMS_QINIU_SECRET_KEY");
        QiniuSmsClient client = new QiniuSmsClient(properties);
        // 准备参数
        String apiTemplateId = "3644cdab863546a3b718d488659a99ef";
        // 调用
        SmsTemplateRespDTO template = client.getSmsTemplate(apiTemplateId);
        // 打印结果
        System.out.println(template);
    }
}

