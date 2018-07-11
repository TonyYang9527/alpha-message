package com.dcf.iqunxing.message2.util.cache;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * 
 * Handel all cache operations
 * 
 * @author zhangjiwei
 *
 */
@Service
public class CacheServiceImpl implements CacheService, ApplicationContextAware {

  private static org.slf4j.Logger log = LoggerFactory.getLogger("CacheStat");

  @Autowired
  private CacheStat stat;

  private MemcachedCache memcachedCache;

  @Autowired
  private LocalCache localCache;

  @Value("${cache.centralized}")
  protected boolean centralized = false;

  protected int defaultExpiredSeconds = 60 * 3600;

  // memcached support 30 days max
  protected static final int MAX_MEMCACHED_EXPIRED_TIME = 30 * 24 * 3600;

  // max lengh of key
  protected static final int MAX_KEY_LENGTH = 200;

  @PostConstruct
  public void init() {
    log.info("Centralized Cache: {}", centralized);
  }

  @Override
  public boolean putCache(CacheGroup group, String key, Object value) {
    return putCache(group, key, value, defaultExpiredSeconds);
  }

  @Override
  public boolean putCache(final CacheGroup group, final String key, final Object value,
      final int expireSeconds) {
    if (expireSeconds > MAX_MEMCACHED_EXPIRED_TIME) {
      throw new IllegalArgumentException("Memcached support max 30 days expire time" + " now is "
          + expireSeconds);
    }
    if (key.length() > MAX_KEY_LENGTH) {
      throw new IllegalArgumentException("Max length of cache key is " + MAX_KEY_LENGTH
          + " now is " + key.length());
    }
    stat.putStart();
    try {
      if (centralized) {
        return memcachedCache.putCache(group, key, value, expireSeconds);
      } else {
        return localCache.putCache(group, key, value, expireSeconds);
      }
    } catch (Exception e) {
      log.error("PutCache error " + group.getValue() + "," + key + "," + value + ","
          + expireSeconds, e);
      return false;
    } finally {
      stat.putEnd();
    }
  }

  @Override
  public Object getCache(final CacheGroup group, final String key) {
    stat.getStart();
    try {
      Object obj = null;
      if (centralized) {
        obj = memcachedCache.getCache(group, key);
      } else {
        obj = localCache.getCache(group, key);
      }
      if (obj != null) {
        stat.getHit();
      }
      return obj;
    } catch (Exception e) {
      log.error("GetCache error " + group.getValue() + "," + key, e);
      return null;
    } finally {
      stat.getEnd();
    }
  }

  @Override
  public boolean deleteCache(final CacheGroup group, final String key) {
    try {
      if (centralized) {
        return memcachedCache.deleteCache(group, key);
      } else {
        return localCache.deleteCache(group, key);
      }
    } catch (Exception e) {
      log.error("DeleteCache error " + group.getValue() + "," + key, e);
      return false;
    }
  }

  @Override
  public void clearCache(CacheGroup group) {
    try {
      if (centralized) {
        memcachedCache.clearCache(group);
      } else {
        localCache.clearCache(group);
      }
    } catch (Exception e) {
      log.error("ClearCache error " + group.getValue(), e);
      return;
    }
  }

  @Override
  public void setApplicationContext(ApplicationContext context) throws BeansException {
    if (centralized)
      memcachedCache = context.getBean(MemcachedCache.class);
  }
  
  @Override
  public boolean addCache(CacheGroup group, String key, Object value, int expireSeconds) {
    try {
      if (centralized) {
        return memcachedCache.addCache(group, key, value, expireSeconds);
      } else {
        return localCache.addCache(group, key, value, expireSeconds);
      }
    } catch (Exception e) {
      log.error("addCache error " + group.getValue(), e);
      return false;
    }
  }
}
