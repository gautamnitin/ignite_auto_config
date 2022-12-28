package com.cache.annotations;

public class EntityToCacheConfigMapperTest {
    public static void main(String[] args) {
        EntityToCacheConfigMapper mapper = new EntityToCacheConfigMapper();
        CacheConfig cacheConfig = mapper.convertToCacheConfig(User.class);
        System.out.println(cacheConfig);
    }
}
