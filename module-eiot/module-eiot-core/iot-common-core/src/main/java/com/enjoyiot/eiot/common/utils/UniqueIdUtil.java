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
package com.enjoyiot.eiot.common.utils;

import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.atomic.AtomicInteger;

public final class UniqueIdUtil {

    private static final int MACHINE_ID = RandomUtils.nextInt(10, 99);

    private static final AtomicInteger SEQUENCE = new AtomicInteger(1000);

    public static String newRequestId() {
        return newUniqueId("");
    }

    public static String newUniqueId(String prefix) {
        int id = SEQUENCE.getAndIncrement();
        if (id >= 5000) {
            SEQUENCE.set(1000);
        }

        return prefix + MACHINE_ID + System.currentTimeMillis() + id ;
    }

}
