
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
package com.enjoyiot.framework.websocket.core.listener;

import com.enjoyiot.framework.websocket.core.message.JsonWebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * WebSocket 消息监听器接口
 *
 * 目的：前端发送消息给后端后，处理对应 {@link #getType()} 类型的消息
 *
 * @param <T> 泛型，消息类型
 */
public interface WebSocketMessageListener<T> {

    /**
     * 处理消息
     *
     * @param session Session
     * @param message 消息
     */
    void onMessage(WebSocketSession session, T message);

    /**
     * 获得消息类型
     *
     * @see JsonWebSocketMessage#getType()
     * @return 消息类型
     */
    String getType();

}
