package com.cache.annotations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CacheConfig {
    private String cacheName;
    private String tableName;
    private boolean isReadThrough = false;
    private boolean isWriteThrough = false;
    private boolean isWriteBehind = false;
    private Class<?> keyClazz;
    private Class<?> entityClazz;
    private Map<String, String> fieldAndDbNameMap;
    private Map<String, Class<?>> fieldNameAndTypeMap;
    private Map<String, Class<?>> keyFieldNameAndTypeMap;
    private List<String> queryFields;
}
