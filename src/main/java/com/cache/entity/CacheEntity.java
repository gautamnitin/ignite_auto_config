package com.cache.entity;

import java.io.Serializable;

public interface CacheEntity <K extends CacheKey> extends Serializable {
    K getKey();
}
