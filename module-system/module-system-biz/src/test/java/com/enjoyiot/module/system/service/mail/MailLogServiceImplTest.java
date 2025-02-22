
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
package com.enjoyiot.module.system.service.mail;

import cn.hutool.core.map.MapUtil;
import com.enjoyiot.framework.common.enums.UserTypeEnum;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.test.core.ut.BaseDbUnitTest;
import com.enjoyiot.module.system.controller.admin.mail.vo.log.MailLogPageReqVO;
import com.enjoyiot.module.system.dal.dataobject.mail.MailAccountDO;
import com.enjoyiot.module.system.dal.dataobject.mail.MailLogDO;
import com.enjoyiot.module.system.dal.dataobject.mail.MailTemplateDO;
import com.enjoyiot.module.system.dal.mysql.mail.MailLogMapper;
import com.enjoyiot.module.system.enums.mail.MailSendStatusEnum;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.Map;

import static cn.hutool.core.util.RandomUtil.randomEle;
import static com.enjoyiot.framework.common.util.date.LocalDateTimeUtils.buildTime;
import static com.enjoyiot.framework.common.util.date.LocalDateTimeUtils.buildBetweenTime;
import static com.enjoyiot.framework.common.util.object.ObjectUtils.cloneIgnoreId;
import static com.enjoyiot.framework.test.core.util.AssertUtils.assertPojoEquals;
import static com.enjoyiot.framework.test.core.util.RandomUtils.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link MailLogServiceImpl} 的单元测试类
 *
 * @author EnjoyIot
 */
@Import(MailLogServiceImpl.class)
public class MailLogServiceImplTest extends BaseDbUnitTest {

    @Resource
    private MailLogServiceImpl mailLogService;

    @Resource
    private MailLogMapper mailLogMapper;

    @Test
    public void testCreateMailLog() {
        // 准备参数
        Long userId = randomLongId();
        Integer userType = randomEle(UserTypeEnum.values()).getValue();
        String toMail = randomEmail();
        MailAccountDO account = randomPojo(MailAccountDO.class);
        MailTemplateDO template = randomPojo(MailTemplateDO.class);
        String templateContent = randomString();
        Map<String, Object> templateParams = randomTemplateParams();
        Boolean isSend = true;
        // mock 方法

        // 调用
        Long logId = mailLogService.createMailLog(userId, userType, toMail, account, template, templateContent, templateParams, isSend);
        // 断言
        MailLogDO log = mailLogMapper.selectById(logId);
        assertNotNull(log);
        assertEquals(MailSendStatusEnum.INIT.getStatus(), log.getSendStatus());
        assertEquals(userId, log.getUserId());
        assertEquals(userType, log.getUserType());
        assertEquals(toMail, log.getToMail());
        assertEquals(account.getId(), log.getAccountId());
        assertEquals(account.getMail(), log.getFromMail());
        assertEquals(template.getId(), log.getTemplateId());
        assertEquals(template.getCode(), log.getTemplateCode());
        assertEquals(template.getNickname(), log.getTemplateNickname());
        assertEquals(template.getTitle(), log.getTemplateTitle());
        assertEquals(templateContent, log.getTemplateContent());
        assertEquals(templateParams, log.getTemplateParams());
    }

    @Test
    public void testUpdateMailSendResult_success() {
        // mock 数据
        MailLogDO log = randomPojo(MailLogDO.class, o -> {
            o.setSendStatus(MailSendStatusEnum.INIT.getStatus());
            o.setSendTime(null).setSendMessageId(null).setSendException(null)
                    .setTemplateParams(randomTemplateParams());
        });
        mailLogMapper.insert(log);
        // 准备参数
        Long logId = log.getId();
        String messageId = randomString();

        // 调用
        mailLogService.updateMailSendResult(logId, messageId, null);
        // 断言
        MailLogDO dbLog = mailLogMapper.selectById(logId);
        assertEquals(MailSendStatusEnum.SUCCESS.getStatus(), dbLog.getSendStatus());
        assertNotNull(dbLog.getSendTime());
        assertEquals(messageId, dbLog.getSendMessageId());
        assertNull(dbLog.getSendException());
    }

    @Test
    public void testUpdateMailSendResult_exception() {
        // mock 数据
        MailLogDO log = randomPojo(MailLogDO.class, o -> {
            o.setSendStatus(MailSendStatusEnum.INIT.getStatus());
            o.setSendTime(null).setSendMessageId(null).setSendException(null)
                    .setTemplateParams(randomTemplateParams());
        });
        mailLogMapper.insert(log);
        // 准备参数
        Long logId = log.getId();
        Exception exception = new NullPointerException("测试异常");

        // 调用
        mailLogService.updateMailSendResult(logId, null, exception);
        // 断言
        MailLogDO dbLog = mailLogMapper.selectById(logId);
        assertEquals(MailSendStatusEnum.FAILURE.getStatus(), dbLog.getSendStatus());
        assertNotNull(dbLog.getSendTime());
        assertNull(dbLog.getSendMessageId());
        assertEquals("NullPointerException: 测试异常", dbLog.getSendException());
    }

    @Test
    public void testGetMailLog() {
        // mock 数据
        MailLogDO dbMailLog = randomPojo(MailLogDO.class, o -> o.setTemplateParams(randomTemplateParams()));
        mailLogMapper.insert(dbMailLog);
        // 准备参数
        Long id = dbMailLog.getId();

        // 调用
        MailLogDO mailLog = mailLogService.getMailLog(id);
        // 断言
        assertPojoEquals(dbMailLog, mailLog);
    }

    @Test
    public void testGetMailLogPage() {
       // mock 数据
       MailLogDO dbMailLog = randomPojo(MailLogDO.class, o -> { // 等会查询到
           o.setUserId(1L);
           o.setUserType(UserTypeEnum.ADMIN.getValue());
           o.setToMail("768@qq.com");
           o.setAccountId(10L);
           o.setTemplateId(100L);
           o.setSendStatus(MailSendStatusEnum.INIT.getStatus());
           o.setSendTime(buildTime(2023, 2, 10));
           o.setTemplateParams(randomTemplateParams());
       });
       mailLogMapper.insert(dbMailLog);
       // 测试 userId 不匹配
       mailLogMapper.insert(cloneIgnoreId(dbMailLog, o -> o.setUserId(2L)));
       // 测试 userType 不匹配
       mailLogMapper.insert(cloneIgnoreId(dbMailLog, o -> o.setUserType(UserTypeEnum.MEMBER.getValue())));
       // 测试 toMail 不匹配
       mailLogMapper.insert(cloneIgnoreId(dbMailLog, o -> o.setToMail("788@.qq.com")));
       // 测试 accountId 不匹配
       mailLogMapper.insert(cloneIgnoreId(dbMailLog, o -> o.setAccountId(11L)));
       // 测试 templateId 不匹配
       mailLogMapper.insert(cloneIgnoreId(dbMailLog, o -> o.setTemplateId(101L)));
       // 测试 sendStatus 不匹配
       mailLogMapper.insert(cloneIgnoreId(dbMailLog, o -> o.setSendStatus(MailSendStatusEnum.SUCCESS.getStatus())));
       // 测试 sendTime 不匹配
       mailLogMapper.insert(cloneIgnoreId(dbMailLog, o -> o.setSendTime(buildTime(2023, 3, 10))));
       // 准备参数
       MailLogPageReqVO reqVO = new MailLogPageReqVO();
       reqVO.setUserId(1L);
       reqVO.setUserType(UserTypeEnum.ADMIN.getValue());
       reqVO.setToMail("768");
       reqVO.setAccountId(10L);
       reqVO.setTemplateId(100L);
       reqVO.setSendStatus(MailSendStatusEnum.INIT.getStatus());
       reqVO.setSendTime((buildBetweenTime(2023, 2, 1, 2023, 2, 15)));

       // 调用
       PageResult<MailLogDO> pageResult = mailLogService.getMailLogPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbMailLog, pageResult.getList().get(0));
    }

    private static Map<String, Object> randomTemplateParams() {
        return MapUtil.<String, Object>builder().put(randomString(), randomString())
                .put(randomString(), randomString()).build();
    }
}
