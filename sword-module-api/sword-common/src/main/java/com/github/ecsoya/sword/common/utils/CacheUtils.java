package com.github.ecsoya.sword.common.utils;

import java.util.Iterator;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ecsoya.sword.common.utils.spring.SpringUtils;

/**
 * The Class CacheUtils.
 */
public class CacheUtils {

	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(CacheUtils.class);

	/** The cache manager. */
	private static CacheManager cacheManager = SpringUtils.getBean(CacheManager.class);

	/** The Constant SYS_CACHE. */
	private static final String SYS_CACHE = "sys-cache";

	/**
	 * Gets the.
	 *
	 * @param key the key
	 * @return the object
	 */
	public static Object get(String key) {
		return get(SYS_CACHE, key);
	}

	/**
	 * Gets the.
	 *
	 * @param key          the key
	 * @param defaultValue the default value
	 * @return the object
	 */
	public static Object get(String key, Object defaultValue) {
		final Object value = get(key);
		return value != null ? value : defaultValue;
	}

	/**
	 * Put.
	 *
	 * @param key   the key
	 * @param value the value
	 */
	public static void put(String key, Object value) {
		put(SYS_CACHE, key, value);
	}

	/**
	 * Removes the.
	 *
	 * @param key the key
	 */
	public static void remove(String key) {
		remove(SYS_CACHE, key);
	}

	/**
	 * Gets the.
	 *
	 * @param cacheName the cache name
	 * @param key       the key
	 * @return the object
	 */
	public static Object get(String cacheName, String key) {
		return getCache(cacheName).get(getKey(key));
	}

	/**
	 * Gets the.
	 *
	 * @param cacheName    the cache name
	 * @param key          the key
	 * @param defaultValue the default value
	 * @return the object
	 */
	public static Object get(String cacheName, String key, Object defaultValue) {
		final Object value = get(cacheName, getKey(key));
		return value != null ? value : defaultValue;
	}

	/**
	 * Put.
	 *
	 * @param cacheName the cache name
	 * @param key       the key
	 * @param value     the value
	 */
	public static void put(String cacheName, String key, Object value) {
		getCache(cacheName).put(getKey(key), value);
	}

	/**
	 * Removes the.
	 *
	 * @param cacheName the cache name
	 * @param key       the key
	 */
	public static void remove(String cacheName, String key) {
		getCache(cacheName).remove(getKey(key));
	}

	/**
	 * Removes the all.
	 *
	 * @param cacheName the cache name
	 */
	public static void removeAll(String cacheName) {
		final Cache<String, Object> cache = getCache(cacheName);
		final Set<String> keys = cache.keys();
		for (final Iterator<String> it = keys.iterator(); it.hasNext();) {
			cache.remove(it.next());
		}
		logger.info("清理缓存： {} => {}", cacheName, keys);
	}

	/**
	 * Removes the by keys.
	 *
	 * @param keys the keys
	 */
	public static void removeByKeys(Set<String> keys) {
		removeByKeys(SYS_CACHE, keys);
	}

	/**
	 * Removes the by keys.
	 *
	 * @param cacheName the cache name
	 * @param keys      the keys
	 */
	public static void removeByKeys(String cacheName, Set<String> keys) {
		for (final Iterator<String> it = keys.iterator(); it.hasNext();) {
			remove(it.next());
		}
		logger.info("清理缓存： {} => {}", cacheName, keys);
	}

	/**
	 * Gets the key.
	 *
	 * @param key the key
	 * @return the key
	 */
	private static String getKey(String key) {
		return key;
	}

	/**
	 * Gets the cache.
	 *
	 * @param cacheName the cache name
	 * @return the cache
	 */
	public static Cache<String, Object> getCache(String cacheName) {
		final Cache<String, Object> cache = cacheManager.getCache(cacheName);
		if (cache == null) {
			throw new RuntimeException("当前系统中没有定义“" + cacheName + "”这个缓存。");
		}
		return cache;
	}

}
