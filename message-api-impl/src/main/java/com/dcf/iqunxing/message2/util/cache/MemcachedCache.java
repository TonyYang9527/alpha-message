package com.dcf.iqunxing.message2.util.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientCallable;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * DO NOT use this class directly. Use CacheService instead
 * 
 * @author zhangjiwei
 *
 */
@Repository
class MemcachedCache {

	private static org.slf4j.Logger log = LoggerFactory.getLogger("CacheStat");

	protected MemcachedClient[] memcachedClients;

	public void setMemcachedClients(MemcachedClient[] memcachedClients)
			throws Exception {
		this.memcachedClients = memcachedClients;
	}

	public boolean putCache(final CacheGroup group, final String key,
			final Object value, final int expireTime) {
		final String valueStr = JSON.toJSONString(value,
				SerializerFeature.WriteClassName);
		boolean result = false;
		for (MemcachedClient memcachedClient : memcachedClients) {
			try {
				memcachedClient.withNamespace(group.getValue(),
						new MemcachedClientCallable<Boolean>() {

							public Boolean call(MemcachedClient client)
									throws MemcachedException,
									InterruptedException, TimeoutException {
								return client.set(key, expireTime, valueStr);

							}
						});
				result = true;
			} catch (Exception e) {
				log.error("PutCache to memcached error " + group.getValue()
						+ "," + key + "," + value + "," + expireTime, e);
			}
		}
		return result;
	}

	@SuppressWarnings({
            "rawtypes", "unchecked"
    })
    public Object getCache(final CacheGroup group, final String key) {
		for (MemcachedClient memcachedClient : memcachedClients) {
			String valueStr = null;
			try {
				valueStr = memcachedClient.withNamespace(group.getValue(),
						new MemcachedClientCallable<String>() {

							public String call(MemcachedClient client)
									throws MemcachedException,
									InterruptedException, TimeoutException {
								return client.get(key);
							}
						});
			} catch (Exception e) {
				log.error("GetCache from memcached error " + group.getValue()
						+ "," + key, e);
			}
			if (valueStr == null) {
				continue;
			}
			Object obj = JSON.parse(valueStr);
			// patch, do not return json model to outer, jiwei
			if (obj instanceof JSONArray) {
				List list = new ArrayList();
                list.addAll((JSONArray) obj);
				obj = list;
			}
			return obj;
		}
		return null;
	}

	public boolean deleteCache(final CacheGroup group, final String key) {
		boolean result = false;
		for (MemcachedClient memcachedClient : memcachedClients) {
			try {
				memcachedClient.withNamespace(group.getValue(),
						new MemcachedClientCallable<Boolean>() {

							public Boolean call(MemcachedClient client)
									throws MemcachedException,
									InterruptedException, TimeoutException {
								return client.delete(key);
							}
						});
				result = true;
			} catch (Exception e) {
				log.error(
						"DeleteCache from memcached error " + group.getValue()
								+ "," + key, e);
			}
		}
		return result;
	}

	public void clearCache(CacheGroup group) {
		for (MemcachedClient memcachedClient : memcachedClients) {
			try {
				memcachedClient.invalidateNamespace(group.getValue());
			} catch (Exception e) {
				log.error("ClearCache memcached error " + group.getValue(), e);
			}
		}
	}
	
    public boolean addCache(final CacheGroup group, final String key, final Object value, final int expireTime) {
        boolean result = false;
        final String valueStr = JSON.toJSONString(value, SerializerFeature.WriteClassName);
        for (MemcachedClient memcachedClient : memcachedClients) {
            try {
                result = memcachedClient.withNamespace(group.getValue(), new MemcachedClientCallable<Boolean>() {

                    public Boolean call(MemcachedClient client) throws MemcachedException, InterruptedException, TimeoutException {
                        return client.add(key, expireTime, valueStr);
                    }
                });
            } catch (Exception e) {
                log.error("AddCache to memcached error " + group.getValue() + "," + key + "," + value + "," + expireTime, e);
            }
        }

        return result;
    }
}
