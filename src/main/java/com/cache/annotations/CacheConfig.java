package com.cache.annotations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ignite.cache.QueryEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public List<QueryEntity> getQueryEntities() {
        List<QueryEntity> queryEntities = queryFields.stream().map(s -> {
            Class<?> fieldClassType = fieldNameAndTypeMap.get(s);
            return new QueryEntity(keyClazz, entityClazz);
        }).collect(Collectors.toList());

        return queryEntities;
    }
}
