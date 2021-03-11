package com.soyatec.sword.framework.web.service;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.cache.CacheManager;
import org.crazycake.shiro.RedisCacheManager;
import org.springframework.stereotype.Service;

import com.soyatec.sword.common.constant.Constants;
import com.soyatec.sword.common.utils.CacheUtils;
import com.soyatec.sword.common.utils.spring.SpringUtils;

/**
 * 缓存操作处理
 * 
 * @author Jin Liu (angryred@qq.com)
 */
@Service
public class CacheService {

	private static CacheManager cacheManager = SpringUtils.getBean(CacheManager.class);

	/**
	 * 获取所有缓存名称
	 * 
	 * @return 缓存列表
	 */
	public String[] getCacheNames() {
		String[] cacheNames = new String[0];
		if (cacheManager instanceof RedisCacheManager) {
			RedisCacheManager redis = (RedisCacheManager) cacheManager;
			cacheNames = getCacheNames(redis);
		}
		return ArrayUtils.removeElement(cacheNames, Constants.SYS_AUTH_CACHE);
	}

	private String[] getCacheNames(RedisCacheManager redis) {
		if (redis == null) {
			return new String[0];
		}
		try {
			Field f = RedisCacheManager.class.getDeclaredField("caches");
			f.setAccessible(true);
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) f.get(redis);
			if (map != null) {
				return map.keySet().toArray(new String[0]);
			}
		} catch (Exception e) {
		}
		return new String[0];
	}

	/**
	 * 根据缓存名称获取所有键名
	 * 
	 * @param cacheName 缓存名称
	 * @return 键名列表
	 */
	public Set<String> getCacheKeys(String cacheName) {
		return CacheUtils.getCache(cacheName).keys();
	}

	/**
	 * 根据缓存名称和键名获取内容值
	 * 
	 * @param cacheName 缓存名称
	 * @param cacheKey  键名
	 * @return 键值
	 */
	public Object getCacheValue(String cacheName, String cacheKey) {
		String simpleKey = cacheKey.replaceAll("shiro:cache:" + cacheName + ":", "");
		Object object = CacheUtils.get(cacheName, simpleKey);
		return object;
	}

	/**
	 * 根据名称删除缓存信息
	 * 
	 * @param cacheName 缓存名称
	 */
	public void clearCacheName(String cacheName) {
		CacheUtils.removeAll(cacheName);
	}

	/**
	 * 根据名称和键名删除缓存信息
	 * 
	 * @param cacheName 缓存名称
	 * @param cacheKey  键名
	 */
	public void clearCacheKey(String cacheName, String cacheKey) {
		String simpleKey = cacheKey.replaceAll("shiro:cache:" + cacheName + ":", "");
		CacheUtils.remove(cacheName, simpleKey);
	}

	/**
	 * 清理所有缓存
	 */
	public void clearAll() {
		String[] cacheNames = getCacheNames();
		for (String cacheName : cacheNames) {
			CacheUtils.removeAll(cacheName);
		}
	}
}
