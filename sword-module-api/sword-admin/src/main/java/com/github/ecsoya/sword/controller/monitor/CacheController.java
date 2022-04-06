package com.github.ecsoya.sword.controller.monitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.framework.web.service.CacheService;

/**
 * The Class CacheController.
 */
@Controller
@RequestMapping("/monitor/cache")
public class CacheController extends BaseController {

	/** The prefix. */
	private final String prefix = "monitor/cache";

	/** The cache service. */
	@Autowired
	private CacheService cacheService;

	/**
	 * Cache.
	 *
	 * @param mmap the mmap
	 * @return the string
	 */
	@GetMapping()
	public String cache(ModelMap mmap) {
		mmap.put("cacheNames", cacheService.getCacheNames());
		return prefix + "/cache";
	}

	/**
	 * Gets the cache names.
	 *
	 * @param fragment the fragment
	 * @param mmap     the mmap
	 * @return the cache names
	 */
	@PostMapping("/getNames")
	public String getCacheNames(String fragment, ModelMap mmap) {
		mmap.put("cacheNames", cacheService.getCacheNames());
		return prefix + "/cache::" + fragment;
	}

	/**
	 * Gets the cache keys.
	 *
	 * @param fragment  the fragment
	 * @param cacheName the cache name
	 * @param mmap      the mmap
	 * @return the cache keys
	 */
	@PostMapping("/getKeys")
	public String getCacheKeys(String fragment, String cacheName, ModelMap mmap) {
		mmap.put("cacheName", cacheName);
		mmap.put("cacheKyes", cacheService.getCacheKeys(cacheName));
		return prefix + "/cache::" + fragment;
	}

	/**
	 * Gets the cache value.
	 *
	 * @param fragment  the fragment
	 * @param cacheName the cache name
	 * @param cacheKey  the cache key
	 * @param mmap      the mmap
	 * @return the cache value
	 */
	@PostMapping("/getValue")
	public String getCacheValue(String fragment, String cacheName, String cacheKey, ModelMap mmap) {
		mmap.put("cacheName", cacheName);
		mmap.put("cacheKey", cacheKey);
		mmap.put("cacheValue", cacheService.getCacheValue(cacheName, cacheKey));
		return prefix + "/cache::" + fragment;
	}

	/**
	 * Clear cache name.
	 *
	 * @param cacheName the cache name
	 * @param mmap      the mmap
	 * @return the ajax result
	 */
	@PostMapping("/clearCacheName")
	@ResponseBody
	public AjaxResult clearCacheName(String cacheName, ModelMap mmap) {
		cacheService.clearCacheName(cacheName);
		return AjaxResult.success();
	}

	/**
	 * Clear cache key.
	 *
	 * @param cacheName the cache name
	 * @param cacheKey  the cache key
	 * @param mmap      the mmap
	 * @return the ajax result
	 */
	@PostMapping("/clearCacheKey")
	@ResponseBody
	public AjaxResult clearCacheKey(String cacheName, String cacheKey, ModelMap mmap) {
		cacheService.clearCacheKey(cacheName, cacheKey);
		return AjaxResult.success();
	}

	/**
	 * Clear all.
	 *
	 * @param mmap the mmap
	 * @return the ajax result
	 */
	@GetMapping("/clearAll")
	@ResponseBody
	public AjaxResult clearAll(ModelMap mmap) {
		cacheService.clearAll();
		return AjaxResult.success();
	}
}
