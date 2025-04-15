/*
 *
 *  * | Licensed 未经许可不能去掉「Enjoy-iot」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2025] [Enjoy-iot]
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

package com.enjoyiot.eiot.message.listener;


import com.enjoyiot.framework.common.util.json.JsonUtils;
import com.enjoyiot.eiot.message.event.MessageEvent;
import com.enjoyiot.eiot.message.model.EmailConfig;
import com.enjoyiot.module.eiot.api.alert.dto.Message;
import com.sun.mail.util.MailSSLSocketFactory;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * author: EnjoyIot
 * date: 2023-05-08 15:09
 * description:
 **/
@Slf4j
@Component
public class EmailEventListener implements MessageEventListener {

    @SneakyThrows
    @Override
    @EventListener(classes = MessageEvent.class, condition = "#event.message.channelCode=='Email'")
    public void doEvent(MessageEvent event) {
        Message message = event.getMessage();
        String channelConfig = message.getChannelConfig();
        EmailConfig emailConfig = JsonUtils.parseObject(channelConfig, EmailConfig.class);

        JavaMailSenderImpl jms = new JavaMailSenderImpl();
        jms.setHost(emailConfig.getHost());
        jms.setUsername(emailConfig.getUserName());
        jms.setPassword(emailConfig.getPassWord());
        jms.setDefaultEncoding("utf-8");
        Properties p = new Properties();
        p.setProperty("mail.smtp.auth", String.valueOf(null == emailConfig.getSmtpAuth() || emailConfig.getSmtpAuth()));
        //这里开启ssl加密
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        p.put("mail.smtp.ssl.enable","true");
        p.put("mail.smtp.ssl.socketFactory",sf);
        jms.setJavaMailProperties(p);
        MimeMessage mimeMessage = jms.createMimeMessage();
        try {
            String content = message.getFormatContent();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            //收件人
            String[] split = emailConfig.getTo().split(",");
            messageHelper.setTo(split);
            //标题
            messageHelper.setSubject(content.substring(0, Math.min(20, content.length())));
            //内容
            messageHelper.setText(content, true);
            //发件人
            messageHelper.setFrom(emailConfig.getFrom());
            jms.send(mimeMessage);
        } catch (Exception e) {
            log.error("发送邮件失败.", e);
        }
    }
}
