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
package com.enjoyiot.eiot.ruleengine.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PathValueResolver {

    private static final Pattern TOKEN_PATTERN = Pattern.compile("^([^\\[\\]]*)(?:\\[(\\*|\\d+)\\])?$");

    private PathValueResolver() {
    }

    public static List<Object> resolveValues(Map<?, ?> source, String path) {
        if (source == null || StringUtils.isBlank(path)) {
            return Collections.emptyList();
        }
        if (source.containsKey(path)) {
            return Collections.singletonList(source.get(path));
        }
        String[] tokens = path.split("\\.");
        List<Object> current = new ArrayList<>();
        current.add(source);
        for (String token : tokens) {
            List<Object> next = new ArrayList<>();
            for (Object node : current) {
                next.addAll(resolveToken(node, token));
            }
            if (next.isEmpty()) {
                return Collections.emptyList();
            }
            current = next;
        }
        return current;
    }

    private static List<Object> resolveToken(Object node, String token) {
        if (node == null) {
            return Collections.emptyList();
        }
        Matcher matcher = TOKEN_PATTERN.matcher(token);
        if (!matcher.matches()) {
            return Collections.emptyList();
        }
        String field = matcher.group(1);
        String index = matcher.group(2);
        Object target = node;
        if (StringUtils.isNotBlank(field)) {
            if (!(target instanceof Map)) {
                return Collections.emptyList();
            }
            target = ((Map<?, ?>) target).get(field);
        }
        if (target == null) {
            return Collections.emptyList();
        }
        if (index == null) {
            return Collections.singletonList(target);
        }
        return resolveIndexed(target, index);
    }

    private static List<Object> resolveIndexed(Object node, String index) {
        if ("*".equals(index)) {
            return toList(node);
        }
        int idx;
        try {
            idx = Integer.parseInt(index);
        } catch (NumberFormatException e) {
            return Collections.emptyList();
        }
        if (node instanceof List) {
            List<?> list = (List<?>) node;
            if (idx < 0 || idx >= list.size()) {
                return Collections.emptyList();
            }
            return Collections.singletonList(list.get(idx));
        }
        if (node.getClass().isArray()) {
            int len = Array.getLength(node);
            if (idx < 0 || idx >= len) {
                return Collections.emptyList();
            }
            return Collections.singletonList(Array.get(node, idx));
        }
        return Collections.emptyList();
    }

    private static List<Object> toList(Object node) {
        if (node instanceof List) {
            return new ArrayList<>((List<?>) node);
        }
        if (node.getClass().isArray()) {
            int len = Array.getLength(node);
            List<Object> values = new ArrayList<>(len);
            for (int i = 0; i < len; i++) {
                values.add(Array.get(node, i));
            }
            return values;
        }
        return Collections.emptyList();
    }
}

