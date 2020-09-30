package org.chance.spring.feature.cache;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-09-30 14:51:51
 */
@Service
@CacheConfig(cacheNames = "cacheName")
public class CacheServiceImpl implements CacheService {

    @Override
    @Cacheable(key = "#key")
    public String fun(String key) {
        return "cacheValue";
    }

}
