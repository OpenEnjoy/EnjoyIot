package com.enjoyiot.eiot.component.udp.enums;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description 枚举工具类
 * @date 2024/7/17
 * @author stuil
 * @version 1.0
 */

public interface GlobalAbstractBaseEnum<E extends Enum<E> & GlobalAbstractBaseEnum<E, V, T>, V, T> {

    V getValue();
    T getText();

    /**
     * 根据 value 获取枚举
     * @param enumClass 枚举类型
     * @param value 枚举的 value
     * @return 枚举实例
     */
    static <E extends Enum<E> & GlobalAbstractBaseEnum<E, V, T>, V, T> E getEnum(Class<E> enumClass, V value) {
        if (value != null) {
            for (E enumConstant : enumClass.getEnumConstants()) {
                if (enumConstant.getValue().equals(value)) {
                    return enumConstant;
                }
            }
        }
        return null;
    }

    /**
     * 枚举转 Map
     * @param enumClass 枚举类型
     * @return 转换后的 Map
     */
    static <E extends Enum<E> & GlobalAbstractBaseEnum<E, V, T>, V, T> LinkedHashMap<V, T> convertEnumToMap(Class<E> enumClass) {
        LinkedHashMap<V, T> linkedHashMap = new LinkedHashMap<>();
        for (E enumConstant : enumClass.getEnumConstants()) {
            linkedHashMap.put(enumConstant.getValue(), enumConstant.getText());
        }
        return linkedHashMap;
    }

    /**
     * 根据 value 获取枚举对应的 Map
     * @param enumClass 枚举类型
     * @param value 枚举的 value
     * @return 对应的 Map
     */
    static <E extends Enum<E> & GlobalAbstractBaseEnum<E, V, T>, V, T> LinkedHashMap<V, T> getMapByValue(Class<E> enumClass, V value) {
        LinkedHashMap<V, T> linkedHashMap = new LinkedHashMap<>();
        if (value != null) {
            E enumConstant = getEnum(enumClass, value);
            if (enumConstant != null) {
                linkedHashMap.put(enumConstant.getValue(), enumConstant.getText());
            }
        }
        return linkedHashMap;
    }

    /**
     * 根据分隔符获取枚举对象列表
     * @param enumClass 枚举类型
     * @param values 枚举的 value 字符串
     * @param delimiter 分隔符
     * @return 枚举对象列表
     */
    static <E extends Enum<E> & GlobalAbstractBaseEnum<E, V, T>, V, T> List<E> getListEnum(Class<E> enumClass, String values, String delimiter) {
        List<E> list = new ArrayList<>();
        if (StringUtils.hasText(values)) {
            List<String> stringList = Arrays.stream(values.split(delimiter))
                    .collect(Collectors.toList());
            for (String string : stringList) {
                V value = (V) parseValue(string.trim());  // 需要实现 parseValue 方法来转换为 V 类型
                E enumConstant = getEnum(enumClass, value);
                if (enumConstant != null) {
                    list.add(enumConstant);
                }
            }
        }
        return list;
    }

    /**
     * 根据分隔符获取 Map 列表
     * @param enumClass 枚举类型
     * @param values 枚举的 value 字符串
     * @param delimiter 分隔符
     * @return Map 列表
     */
    static <E extends Enum<E> & GlobalAbstractBaseEnum<E, V, T>, V, T> List<LinkedHashMap<V, T>> getListMap(Class<E> enumClass, String values, String delimiter) {
        List<LinkedHashMap<V, T>> list = new ArrayList<>();
        if (StringUtils.hasText(values)) {
            List<String> stringList = Arrays.stream(values.split(delimiter))
                    .collect(Collectors.toList());
            for (String string : stringList) {
                V value = (V) parseValue(string.trim());  // 需要实现 parseValue 方法来转换为 V 类型
                LinkedHashMap<V, T> map = getMapByValue(enumClass, value);
                if (!map.isEmpty()) {
                    list.add(map);
                }
            }
        }
        return list;
    }

    /**
     * 解析字符串为值
     * @param value 字符串值
     * @return 解析后的值
     */
    @SuppressWarnings("unchecked")
    static <V> V parseValue(String value) {
        // Implement logic to convert string to V (e.g., Integer, String)
        // This example assumes V can be Integer or String.
        try {
            return (V) Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return (V) value;
        }
    }
}