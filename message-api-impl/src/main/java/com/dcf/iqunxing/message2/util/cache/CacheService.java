package com.dcf.iqunxing.message2.util.cache;

/**
 * Cache service
 * 
 * @author zhangjiwei
 *
 */
public interface CacheService {

	/**
	 * Put cache, use default expire time
	 * 
	 * @param group
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean putCache(CacheGroup group, String key, Object value);

	/**
	 * Put cache
	 * 
	 * @param group
	 * @param key
	 * @param value
	 * @param expireSeconds
	 *            seconds. If zero, never expired
	 * @return
	 */
	public boolean putCache(CacheGroup group, String key, Object value,
			int expireSeconds);

	public boolean deleteCache(CacheGroup group, String key);

	public void clearCache(CacheGroup group);

	public Object getCache(CacheGroup group, String key);
	
    /**
     * If key exists, return false, else add to cache and return true
     * 
     * @param group
     * @param key
     * @param value
     * @param expireSeconds
     * @return
     */
    public boolean addCache(CacheGroup group, String key, Object value, int expireSeconds);
}
