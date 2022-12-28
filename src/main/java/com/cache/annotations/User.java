package com.cache.annotations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@IgniteCacheable(cacheName = "UserCache",
        tableName = "USER",
        isReadThrough = false,
        isWriteThrough = false,
        isWriteBehind = false,
        keyClazz = UserKey.class
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @CacheField(dbName = "id", isQueryField = true)
    private String id;
    @CacheField(dbName = "first_name", isQueryField = true)
    private String firstName;
    @CacheField(dbName = "last_name", isQueryField = true)
    private String lastName;
}
