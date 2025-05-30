package com.enjoyiot.eiot.component.udp.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @title: MapToEntityUtil
 * @description: Map----Entity 互相转化
 * @date: 2024/7/26
 * @author: stuil
 * @copyright: Copyright (c) 2024
 * @version: 1.0
 */

public class MapToEntityUtil {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 将 Map 转换为指定类型的实体类对象
     *
     * @param map      源 Map 对象
     * @param clazz    目标实体类的 Class 对象
     * @param <T>      目标实体类的类型
     * @return         转换后的实体类对象
     * @throws IllegalAccessException if unable to access the field
     * @throws InstantiationException if unable to instantiate the class
     */
    public static <T> T convert(Map<String, Object> map, Class<T> clazz)
            throws IllegalAccessException, InstantiationException {
        if (map == null || clazz == null) {
            return null;
        }

        // 创建目标类的实例
        T instance = clazz.newInstance();

        // 获取所有字段
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            // 获取 Map 中对应字段的值
            Object value = map.get(field.getName());

            // 跳过 null 值或者 Map 中没有该 key 的情况
            if (value == null) {
                continue;
            }

            // 如果字段的类型与值的类型不匹配，进行转换
            try {
                Class<?> fieldType = field.getType();

                if (fieldType == String.class) {
                    field.set(instance, value.toString());
                } else if (fieldType == Integer.class || fieldType == int.class) {
                    field.set(instance, Integer.parseInt(value.toString()));
                } else if (fieldType == Double.class || fieldType == double.class) {
                    field.set(instance, Double.parseDouble(value.toString()));
                } else if (fieldType == Float.class || fieldType == float.class) {
                    field.set(instance, Float.parseFloat(value.toString()));
                } else if (fieldType == Long.class || fieldType == long.class) {
                    field.set(instance, Long.parseLong(value.toString()));
                } else if (fieldType == Date.class) {
                    try {
                        field.set(instance, DATE_FORMAT.parse(value.toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                        // 处理日期解析异常
                    }
                } else if (fieldType == Map.class) {
                    // 处理 Map 类型字段
                    Map<?, ?> mapValue = (Map<?, ?>) value;
                    Class<?> mapKeyType = Object.class; // 假设键类型为 Object
                    Class<?> mapValueType = Object.class; // 假设值类型为 Object
                    // 尝试解析 Map 类型字段的泛型
                    if (field.getGenericType() instanceof ParameterizedType) {
                        ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                        mapKeyType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                        mapValueType = (Class<?>) parameterizedType.getActualTypeArguments()[1];
                    }
                    // 创建 Map 实例并填充
                    Map<Object, Object> mapInstance = (Map<Object, Object>) field.getType().newInstance();
                    for (Map.Entry<?, ?> entry : mapValue.entrySet()) {
                        Object key = convertObject(entry.getKey(), mapKeyType);
                        Object val = convertObject(entry.getValue(), mapValueType);
                        mapInstance.put(key, val);
                    }
                    field.set(instance, mapInstance);
                } else if (fieldType == List.class) {
                    // 处理 List 类型字段
                    List<?> listValue = (List<?>) value;
                    Class<?> listItemType = Object.class; // 假设元素类型为 Object
                    // 尝试解析 List 类型字段的泛型
                    if (field.getGenericType() instanceof ParameterizedType) {
                        ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                        listItemType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                    }
                    // 创建 List 实例并填充
                    List<Object> listInstance = new ArrayList<>();
                    for (Object item : listValue) {
                        listInstance.add(convertObject(item, listItemType));
                    }
                    field.set(instance, listInstance);
                } else {
                    // 对于其他未处理的类型，可以根据需要扩展
                    field.set(instance, value);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                // 处理数字格式异常
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                // 处理 Map 或 List 实例化异常
            }
        }

        return instance;
    }

    /**
     * 将对象转换为指定类型
     *
     * @param obj       源对象
     * @param targetType 目标类型
     * @return          转换后的对象
     */
    private static Object convertObject(Object obj, Class<?> targetType) {
        if (targetType == String.class) {
            return obj.toString();
        } else if (targetType == Integer.class || targetType == int.class) {
            return Integer.parseInt(obj.toString());
        } else if (targetType == Double.class || targetType == double.class) {
            return Double.parseDouble(obj.toString());
        } else if (targetType == Float.class || targetType == float.class) {
            return Float.parseFloat(obj.toString());
        } else if (targetType == Long.class || targetType == long.class) {
            return Long.parseLong(obj.toString());
        } else if (targetType == Date.class) {
            try {
                return DATE_FORMAT.parse(obj.toString());
            } catch (ParseException e) {
                e.printStackTrace();
                // 处理日期解析异常
            }
        }
        // 对于其他未处理的类型，直接返回原对象
        return obj;
    }
    /**
     * 将 Map 转换为指定类型的实体类对象
     *
     * @param map         包含实体类字段值的 Map
     * @param entityClass 目标实体类的 Class 对象
     * @param <T>         实体类的类型
     * @return 转换后的实体类对象
     * @throws Exception 如果反射操作失败或实体类没有无参构造函数
     */
    public static <T> T  mapToEntity(Map<String, Object> map, Class<T> entityClass) throws Exception {
        // 创建目标实体类实例
        T entity = entityClass.getDeclaredConstructor().newInstance();

        // 获取实体类的所有字段
        Field[] fields = entityClass.getDeclaredFields();

        // 遍历字段并从 Map 中设置值
        for (Field field : fields) {
            // 设置私有字段可访问
            field.setAccessible(true);
            // 获取字段名
            String fieldName = field.getName();
            // 从 Map 中获取字段值并设置到实体类中
            if (map.containsKey(fieldName)) {
                Object value = map.get(fieldName);
                String v1= value.toString();
                field.set(entity, v1);
            }
        }

        return entity;
    }

    /**
     * 将实体类对象转换为 Map
     *
     * @param entity 需要转换的实体类对象
     * @return 包含实体类字段值的 Map
     * @throws Exception 如果反射操作失败
     */
    public static Map<String, Object> entityToMap(Object entity) throws Exception {
        // 创建一个新的 HashMap 用于存储字段值
        Map<String, Object> map = new HashMap<>();
        // 获取实体类的 Class 对象
        Class<?> entityClass = entity.getClass();
        // 获取实体类的所有字段
        Field[] fields = entityClass.getDeclaredFields();
        // 遍历字段并将字段值放入 Map 中
        for (Field field : fields) {
            // 设置私有字段可访问
            field.setAccessible(true);
            // 获取字段名
            String fieldName = field.getName();
            // 获取字段值
            Object value = field.get(entity);
            // 将字段名和值放入 Map 中
            map.put(fieldName, value);
        }

        return map;
    }
}
