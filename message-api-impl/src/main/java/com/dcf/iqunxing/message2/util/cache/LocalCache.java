package com.dcf.iqunxing.message2.util.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

/**
 * DO NOT use this class directly. Use CacheService instead
 * 
 * @author zhangjiwei
 */
@Repository
class LocalCache {

    @SuppressWarnings("rawtypes")
    protected ConcurrentMap<String, ConcurrentMap> cache = new ConcurrentHashMap<String, ConcurrentMap>();

    public boolean putCache(final CacheGroup group, final String key, final Object value, final int expireTime) {
        getLocalCache(group).put(key, new Object[] {
                value, expireTime * 1000l + System.currentTimeMillis()
        });
        return true;
    }

    public Object getCache(final CacheGroup group, final String key) {
        Object[] o = getLocalCache(group).get(key);
        if (o != null) {
            if (System.currentTimeMillis() > (Long) o[1]) {
                getLocalCache(group).remove(key);
                return null;
            } else {
                return (Object) o[0];
            }
        } else {
            return null;
        }
    }

    public boolean deleteCache(final CacheGroup group, final String key) {
        return getLocalCache(group).remove(key) != null;
    }

    public void clearCache(CacheGroup group) {
        getLocalCache(group).clear();
    }

    @SuppressWarnings("unchecked")
    protected ConcurrentMap<String, Object[]> getLocalCache(CacheGroup group) {
        ConcurrentMap<String, Object[]> map = cache.get(group.getValue());
        if (map != null) {
            return map;
        }

        cache.putIfAbsent(group.getValue(), new ConcurrentHashMap<String, Object[]>());

        return cache.get(group.getValue());
    }

    public boolean addCache(CacheGroup group, String key, Object value, int expireSeconds) {
        if (cache.containsKey(group.getValue()) && cache.get(group.getValue()).containsKey(key)) {
            return false;
        }
        return putCache(group, key, value, expireSeconds);
    }
}
