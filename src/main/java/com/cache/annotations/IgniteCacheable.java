package com.cache.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface IgniteCacheable {
    String cacheName() default "";
    String tableName() default "";
    boolean isReadThrough() default false;
    boolean isWriteThrough() default false;
    boolean isWriteBehind() default false;
    Class<?> keyClazz() default String.class;
}
