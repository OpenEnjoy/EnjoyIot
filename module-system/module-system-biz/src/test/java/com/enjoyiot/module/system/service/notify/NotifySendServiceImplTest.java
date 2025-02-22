
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
package com.enjoyiot.module.system.service.notify;

import cn.hutool.core.map.MapUtil;
import com.enjoyiot.framework.common.enums.CommonStatusEnum;
import com.enjoyiot.framework.common.enums.UserTypeEnum;
import com.enjoyiot.framework.test.core.ut.BaseMockitoUnitTest;
import com.enjoyiot.module.system.dal.dataobject.notify.NotifyTemplateDO;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static cn.hutool.core.util.RandomUtil.randomEle;
import static com.enjoyiot.framework.test.core.util.AssertUtils.assertServiceException;
import static com.enjoyiot.framework.test.core.util.RandomUtils.*;
import static com.enjoyiot.module.system.enums.ErrorCodeConstants.NOTICE_NOT_FOUND;
import static com.enjoyiot.module.system.enums.ErrorCodeConstants.NOTIFY_SEND_TEMPLATE_PARAM_MISS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class NotifySendServiceImplTest extends BaseMockitoUnitTest {

    @InjectMocks
    private NotifySendServiceImpl notifySendService;

    @Mock
    private NotifyTemplateService notifyTemplateService;
    @Mock
    private NotifyMessageService notifyMessageService;

    @Test
    public void testSendSingleNotifyToAdmin() {
        // 准备参数
        Long userId = randomLongId();
        String templateCode = randomString();
        Map<String, Object> templateParams = MapUtil.<String, Object>builder().put("code", "1234")
                .put("op", "login").build();
        // mock NotifyTemplateService 的方法
        NotifyTemplateDO template = randomPojo(NotifyTemplateDO.class, o -> {
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
            o.setContent("验证码为{code}, 操作为{op}");
            o.setParams(Lists.newArrayList("code", "op"));
        });
        when(notifyTemplateService.getNotifyTemplateByCodeFromCache(eq(templateCode))).thenReturn(template);
        String content = randomString();
        when(notifyTemplateService.formatNotifyTemplateContent(eq(template.getContent()), eq(templateParams)))
                .thenReturn(content);
        // mock NotifyMessageService 的方法
        Long messageId = randomLongId();
        when(notifyMessageService.createNotifyMessage(eq(userId), eq(UserTypeEnum.ADMIN.getValue()),
                eq(template), eq(content), eq(templateParams))).thenReturn(messageId);

        // 调用
        Long resultMessageId = notifySendService.sendSingleNotifyToAdmin(userId, templateCode, templateParams);
        // 断言
        assertEquals(messageId, resultMessageId);
    }

    @Test
    public void testSendSingleNotifyToMember() {
        // 准备参数
        Long userId = randomLongId();
        String templateCode = randomString();
        Map<String, Object> templateParams = MapUtil.<String, Object>builder().put("code", "1234")
                .put("op", "login").build();
        // mock NotifyTemplateService 的方法
        NotifyTemplateDO template = randomPojo(NotifyTemplateDO.class, o -> {
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
            o.setContent("验证码为{code}, 操作为{op}");
            o.setParams(Lists.newArrayList("code", "op"));
        });
        when(notifyTemplateService.getNotifyTemplateByCodeFromCache(eq(templateCode))).thenReturn(template);
        String content = randomString();
        when(notifyTemplateService.formatNotifyTemplateContent(eq(template.getContent()), eq(templateParams)))
                .thenReturn(content);
        // mock NotifyMessageService 的方法
        Long messageId = randomLongId();
        when(notifyMessageService.createNotifyMessage(eq(userId), eq(UserTypeEnum.MEMBER.getValue()),
                eq(template), eq(content), eq(templateParams))).thenReturn(messageId);

        // 调用
        Long resultMessageId = notifySendService.sendSingleNotifyToMember(userId, templateCode, templateParams);
        // 断言
        assertEquals(messageId, resultMessageId);
    }

    /**
     * 发送成功，当短信模板开启时
     */
    @Test
    public void testSendSingleNotify_successWhenMailTemplateEnable() {
        // 准备参数
        Long userId = randomLongId();
        Integer userType = randomEle(UserTypeEnum.values()).getValue();
        String templateCode = randomString();
        Map<String, Object> templateParams = MapUtil.<String, Object>builder().put("code", "1234")
                .put("op", "login").build();
        // mock NotifyTemplateService 的方法
        NotifyTemplateDO template = randomPojo(NotifyTemplateDO.class, o -> {
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
            o.setContent("验证码为{code}, 操作为{op}");
            o.setParams(Lists.newArrayList("code", "op"));
        });
        when(notifyTemplateService.getNotifyTemplateByCodeFromCache(eq(templateCode))).thenReturn(template);
        String content = randomString();
        when(notifyTemplateService.formatNotifyTemplateContent(eq(template.getContent()), eq(templateParams)))
                .thenReturn(content);
        // mock NotifyMessageService 的方法
        Long messageId = randomLongId();
        when(notifyMessageService.createNotifyMessage(eq(userId), eq(userType),
                eq(template), eq(content), eq(templateParams))).thenReturn(messageId);

        // 调用
        Long resultMessageId = notifySendService.sendSingleNotify(userId, userType, templateCode, templateParams);
        // 断言
        assertEquals(messageId, resultMessageId);
    }

    /**
     * 发送成功，当短信模板关闭时
     */
    @Test
    public void testSendSingleMail_successWhenSmsTemplateDisable() {
        // 准备参数
        Long userId = randomLongId();
        Integer userType = randomEle(UserTypeEnum.values()).getValue();
        String templateCode = randomString();
        Map<String, Object> templateParams = MapUtil.<String, Object>builder().put("code", "1234")
                .put("op", "login").build();
        // mock NotifyTemplateService 的方法
        NotifyTemplateDO template = randomPojo(NotifyTemplateDO.class, o -> {
            o.setStatus(CommonStatusEnum.DISABLE.getStatus());
            o.setContent("验证码为{code}, 操作为{op}");
            o.setParams(Lists.newArrayList("code", "op"));
        });
        when(notifyTemplateService.getNotifyTemplateByCodeFromCache(eq(templateCode))).thenReturn(template);

        // 调用
        Long resultMessageId = notifySendService.sendSingleNotify(userId, userType, templateCode, templateParams);
        // 断言
        assertNull(resultMessageId);
        verify(notifyTemplateService, never()).formatNotifyTemplateContent(anyString(), anyMap());
        verify(notifyMessageService, never()).createNotifyMessage(anyLong(), anyInt(), any(), anyString(), anyMap());
    }

    @Test
    public void testCheckMailTemplateValid_notExists() {
        // 准备参数
        String templateCode = randomString();
        // mock 方法

        // 调用，并断言异常
        assertServiceException(() -> notifySendService.validateNotifyTemplate(templateCode),
                NOTICE_NOT_FOUND);
    }

    @Test
    public void testCheckTemplateParams_paramMiss() {
        // 准备参数
        NotifyTemplateDO template = randomPojo(NotifyTemplateDO.class,
                o -> o.setParams(Lists.newArrayList("code")));
        Map<String, Object> templateParams = new HashMap<>();
        // mock 方法

        // 调用，并断言异常
        assertServiceException(() -> notifySendService.validateTemplateParams(template, templateParams),
                NOTIFY_SEND_TEMPLATE_PARAM_MISS, "code");
    }

    @Test
    public void testSendBatchNotify() {
        // 准备参数
        // mock 方法

        // 调用
        UnsupportedOperationException exception = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> notifySendService.sendBatchNotify(null, null, null, null, null)
        );
        // 断言
        assertEquals("暂时不支持该操作，感兴趣可以实现该功能哟！", exception.getMessage());
    }

}
