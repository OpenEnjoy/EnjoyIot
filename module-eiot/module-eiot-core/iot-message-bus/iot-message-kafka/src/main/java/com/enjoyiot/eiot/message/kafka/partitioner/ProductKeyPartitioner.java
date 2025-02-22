
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
package com.enjoyiot.eiot.message.kafka.partitioner;


import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.utils.Utils;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 根据 produceKey进行分区
 *
 * @author clickear
 */
public class ProductKeyPartitioner implements Partitioner {

    public void ProductPartitioner() {

    }

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        // 返回分区数量
        int partitionCount = cluster.partitionsForTopic(topic).size();

        if (value instanceof ThingModelMessage) {
            ThingModelMessage message = (ThingModelMessage) value;
            if (message == null || message.getProductKey() == null) {
                return 0;
            }

            // 使用murmur2算法计算 hashcode。
            int hashCode = Utils.toPositive(Utils.murmur2(message.getProductKey().getBytes(StandardCharsets.UTF_8)));
            return hashCode % partitionCount;
        }

        // 默认分区
        return 0;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
