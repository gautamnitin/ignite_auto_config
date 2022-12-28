package com.cache.entity;

import com.cache.annotations.CacheField;
import com.cache.annotations.IgniteCacheable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IgniteCacheable(cacheName = "EmployeeCache", tableName = "EMPLOYEE", keyClazz = EmployeeKey.class)
public class Employee implements CacheEntity<EmployeeKey> {

    @CacheField(isQueryField = true, dbName = "ID")
    private String id;
    @CacheField(isQueryField = true, dbName = "name")
    private String name;

    @Override
    public EmployeeKey getKey() {
        return EmployeeKey.builder().name(name).build();
    }
}
