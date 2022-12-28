package com.cache.annotations;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityToCacheConfigMapper {

    public CacheConfig convertToCacheConfig(Class<?> entityClass) throws JsonSerializationException {
        try {
            checkIfCacheable(entityClass);
            initializeObject(entityClass);
            return getCacheConfig(entityClass);

        } catch (Exception e) {
            throw new CacheSerializationException(e.getMessage());
        }
    }

    private CacheConfig getCacheConfig(Class<?> entityClass) {
        CacheConfig cacheConfig = new CacheConfig();
        cacheConfig.setEntityClazz(entityClass);
        populateClassLevelAnnotation(entityClass, cacheConfig);
        populateFieldLevelAnnotation(entityClass, cacheConfig);
        populateKeyFieldLevelAnnotation(cacheConfig);
        return cacheConfig;
    }


    private void populateClassLevelAnnotation(Class<?> entityClass, CacheConfig cacheConfig) {
        if (entityClass.isAnnotationPresent(IgniteCacheable.class)) {
            IgniteCacheable entityClassAnnotation = entityClass.getAnnotation(IgniteCacheable.class);
            cacheConfig.setCacheName(entityClassAnnotation.cacheName());
            cacheConfig.setTableName(entityClassAnnotation.tableName());
            cacheConfig.setKeyClazz(entityClassAnnotation.keyClazz());
            cacheConfig.setReadThrough(entityClassAnnotation.isReadThrough());
            cacheConfig.setWriteThrough(entityClassAnnotation.isWriteThrough());
            cacheConfig.setWriteBehind(entityClassAnnotation.isWriteBehind());
        }
    }

    private void populateFieldLevelAnnotation(Class<?> entityClass, CacheConfig cacheConfig) {
        Map<String, Class<?>> fieldNameAndTypeMap  = new HashMap<>();
        Map<String, String> fieldAndDbNameMap = new HashMap<>();
        List<String> queryFields = new ArrayList<>();

        for (Field field : entityClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(CacheField.class)) {
                String fieldName = field.getName();
                Class<?> fieldType = field.getType();
                fieldNameAndTypeMap.put(fieldName, fieldType);
                CacheField fieldAnnotation = field.getAnnotation(CacheField.class);
                fieldAndDbNameMap.put(fieldName, fieldAnnotation.dbName());
                if(fieldAnnotation.isQueryField()) {
                    queryFields.add(fieldName);
                }
            }
        }
        cacheConfig.setFieldNameAndTypeMap(fieldNameAndTypeMap);
        cacheConfig.setFieldAndDbNameMap(fieldAndDbNameMap);
        cacheConfig.setQueryFields(queryFields);
    }

    private void populateKeyFieldLevelAnnotation(CacheConfig cacheConfig) {
        Class<?> keyClass = cacheConfig.getKeyClazz();
        if(keyClass == null)
            return;
        if (!keyClass.isAnnotationPresent(CacheKey.class)) {
            throw new CacheSerializationException("The class " + keyClass.getSimpleName() + " is not annotated with CacheKey");
        }
        Map<String, Class<?>> keyFieldNameAndTypeMap  = new HashMap<>();
        for (Field field : keyClass.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Class<?> fieldType = field.getType();
            keyFieldNameAndTypeMap.put(fieldName, fieldType);
        }
        cacheConfig.setKeyFieldNameAndTypeMap(keyFieldNameAndTypeMap);
    }

    private void checkIfCacheable(Class<?> entityClass) {
        if (!entityClass.isAnnotationPresent(IgniteCacheable.class)) {
            throw new CacheSerializationException("The class " + entityClass.getSimpleName() + " is not annotated with IgniteCacheable");
        }
    }

    private void initializeObject(Class<?> entityClass) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        for (Method method : entityClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Init.class)) {
                method.setAccessible(true);
                method.invoke(entityClass.getDeclaredConstructor().newInstance());
            }
        }
    }

    private String getKey(Field field) {
        String value = field.getAnnotation(JsonElement.class)
                .key();
        return value.isEmpty() ? field.getName() : value;
    }
}
