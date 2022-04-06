package com.github.ecsoya.sword.framework.web.service;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.cache.CacheManager;
import org.crazycake.shiro.RedisCacheManager;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.constant.Constants;
import com.github.ecsoya.sword.common.utils.CacheUtils;
import com.github.ecsoya.sword.common.utils.spring.SpringUtils;

/**
 * The Class CacheService.
 */
@Service
public class CacheService {

	/** The cache manager. */
	private static CacheManager cacheManager = SpringUtils.getBean(CacheManager.class);

	/**
	 * Gets the cache names.
	 *
	 * @return the cache names
	 */
	public String[] getCacheNames() {
		String[] cacheNames = new String[0];
		if (cacheManager instanceof RedisCacheManager) {
			final RedisCacheManager redis = (RedisCacheManager) cacheManager;
			cacheNames = getCacheNames(redis);
		}
		return ArrayUtils.removeElement(cacheNames, Constants.SYS_AUTH_CACHE);
	}

	/**
	 * Gets the cache names.
	 *
	 * @param redis the redis
	 * @return the cache names
	 */
	private String[] getCacheNames(RedisCacheManager redis) {
		if (redis == null) {
			return new String[0];
		}
		try {
			final Field f = RedisCacheManager.class.getDeclaredField("caches");
			f.setAccessible(true);
			@SuppressWarnings("unchecked")
			final Map<String, Object> map = (Map<String, Object>) f.get(redis);
			if (map != null) {
				return map.keySet().toArray(new String[0]);
			}
		} catch (final Exception e) {
		}
		return new String[0];
	}

	/**
	 * Gets the cache keys.
	 *
	 * @param cacheName the cache name
	 * @return the cache keys
	 */
	public Set<String> getCacheKeys(String cacheName) {
		return CacheUtils.getCache(cacheName).keys();
	}

	/**
	 * Gets the cache value.
	 *
	 * @param cacheName the cache name
	 * @param cacheKey  the cache key
	 * @return the cache value
	 */
	public Object getCacheValue(String cacheName, String cacheKey) {
		final String simpleKey = cacheKey.replaceAll("shiro:cache:" + cacheName + ":", "");
		final Object object = CacheUtils.get(cacheName, simpleKey);
		return object;
	}

	/**
	 * Clear cache name.
	 *
	 * @param cacheName the cache name
	 */
	public void clearCacheName(String cacheName) {
		CacheUtils.removeAll(cacheName);
	}

	/**
	 * Clear cache key.
	 *
	 * @param cacheName the cache name
	 * @param cacheKey  the cache key
	 */
	public void clearCacheKey(String cacheName, String cacheKey) {
		final String simpleKey = cacheKey.replaceAll("shiro:cache:" + cacheName + ":", "");
		CacheUtils.remove(cacheName, simpleKey);
	}

	/**
	 * Clear all.
	 */
	public void clearAll() {
		final String[] cacheNames = getCacheNames();
		for (final String cacheName : cacheNames) {
			CacheUtils.removeAll(cacheName);
		}
	}
}
