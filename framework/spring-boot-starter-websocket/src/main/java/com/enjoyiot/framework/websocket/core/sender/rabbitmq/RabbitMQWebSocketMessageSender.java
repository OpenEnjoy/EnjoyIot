
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
package com.enjoyiot.framework.websocket.core.sender.rabbitmq;

import com.enjoyiot.framework.websocket.core.sender.AbstractWebSocketMessageSender;
import com.enjoyiot.framework.websocket.core.sender.WebSocketMessageSender;
import com.enjoyiot.framework.websocket.core.session.WebSocketSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * 基于 RabbitMQ 的 {@link WebSocketMessageSender} 实现类
 *
 * @author EnjoyIot
 */
@Slf4j
public class RabbitMQWebSocketMessageSender extends AbstractWebSocketMessageSender {

    private final RabbitTemplate rabbitTemplate;

    private final TopicExchange topicExchange;

    public RabbitMQWebSocketMessageSender(WebSocketSessionManager sessionManager,
                                          RabbitTemplate rabbitTemplate,
                                          TopicExchange topicExchange) {
        super(sessionManager);
        this.rabbitTemplate = rabbitTemplate;
        this.topicExchange = topicExchange;
    }

    @Override
    public void send(Integer userType, Long userId, String messageType, String messageContent) {
        sendRabbitMQMessage(null, userId, userType, messageType, messageContent);
    }

    @Override
    public void send(Integer userType, String messageType, String messageContent) {
        sendRabbitMQMessage(null, null, userType, messageType, messageContent);
    }

    @Override
    public void send(String sessionId, String messageType, String messageContent) {
        sendRabbitMQMessage(sessionId, null, null, messageType, messageContent);
    }

    /**
     * 通过 RabbitMQ 广播消息
     *
     * @param sessionId      Session 编号
     * @param userId         用户编号
     * @param userType       用户类型
     * @param messageType    消息类型
     * @param messageContent 消息内容
     */
    private void sendRabbitMQMessage(String sessionId, Long userId, Integer userType,
                                     String messageType, String messageContent) {
        RabbitMQWebSocketMessage mqMessage = RabbitMQWebSocketMessage.builder()
                .sessionId(sessionId)
                .userId(userId)
                .userType(userType)
                .messageType(messageType)
                .messageContent(messageContent)
                .build();
        rabbitTemplate.convertAndSend(topicExchange.getName(), null, mqMessage);
    }

}
