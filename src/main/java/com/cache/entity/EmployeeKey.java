package com.cache.entity;

import com.cache.annotations.CacheKeyClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@CacheKeyClass
public class EmployeeKey implements CacheKey {
    private String name;
}
