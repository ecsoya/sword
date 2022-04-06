package com.github.ecsoya.sword.common.utils;

import java.util.List;

import org.springframework.stereotype.Component;

import com.github.ecsoya.sword.common.constant.Constants;
import com.github.ecsoya.sword.common.core.domain.entity.SysDictData;

/**
 * The Class DictUtils.
 */
@Component
public class DictUtils {

	/** The Constant SEPARATOR. */
	public static final String SEPARATOR = ",";

	/**
	 * Sets the dict cache.
	 *
	 * @param key       the key
	 * @param dictDatas the dict datas
	 */
	public static void setDictCache(String key, List<SysDictData> dictDatas) {
		CacheUtils.put(getCacheName(), getCacheKey(key), dictDatas);
	}

	/**
	 * Gets the dict cache.
	 *
	 * @param key the key
	 * @return the dict cache
	 */
	public static List<SysDictData> getDictCache(String key) {
		final Object cacheObj = CacheUtils.get(getCacheName(), getCacheKey(key));
		if (StringUtils.isNotNull(cacheObj)) {
			final List<SysDictData> DictDatas = StringUtils.cast(cacheObj);
			return DictDatas;
		}
		return null;
	}

	/**
	 * Gets the dict label.
	 *
	 * @param dictType  the dict type
	 * @param dictValue the dict value
	 * @return the dict label
	 */
	public static String getDictLabel(String dictType, String dictValue) {
		return getDictLabel(dictType, dictValue, SEPARATOR);
	}

	/**
	 * Gets the dict value.
	 *
	 * @param dictType  the dict type
	 * @param dictLabel the dict label
	 * @return the dict value
	 */
	public static String getDictValue(String dictType, String dictLabel) {
		return getDictValue(dictType, dictLabel, SEPARATOR);
	}

	/**
	 * Gets the dict label.
	 *
	 * @param dictType  the dict type
	 * @param dictValue the dict value
	 * @param separator the separator
	 * @return the dict label
	 */
	public static String getDictLabel(String dictType, String dictValue, String separator) {
		final StringBuilder propertyString = new StringBuilder();
		final List<SysDictData> datas = getDictCache(dictType);

		if (org.apache.commons.lang3.StringUtils.containsAny(separator, dictValue) && StringUtils.isNotEmpty(datas)) {
			for (final SysDictData dict : datas) {
				for (final String value : dictValue.split(separator)) {
					if (value.equals(dict.getDictValue())) {
						propertyString.append(dict.getDictLabel() + separator);
						break;
					}
				}
			}
		} else {
			for (final SysDictData dict : datas) {
				if (dictValue.equals(dict.getDictValue())) {
					return dict.getDictLabel();
				}
			}
		}
		return org.apache.commons.lang3.StringUtils.stripEnd(propertyString.toString(), separator);
	}

	/**
	 * Gets the dict value.
	 *
	 * @param dictType  the dict type
	 * @param dictLabel the dict label
	 * @param separator the separator
	 * @return the dict value
	 */
	public static String getDictValue(String dictType, String dictLabel, String separator) {
		final StringBuilder propertyString = new StringBuilder();
		final List<SysDictData> datas = getDictCache(dictType);

		if (org.apache.commons.lang3.StringUtils.containsAny(separator, dictLabel) && StringUtils.isNotEmpty(datas)) {
			for (final SysDictData dict : datas) {
				for (final String label : dictLabel.split(separator)) {
					if (label.equals(dict.getDictLabel())) {
						propertyString.append(dict.getDictValue() + separator);
						break;
					}
				}
			}
		} else {
			for (final SysDictData dict : datas) {
				if (dictLabel.equals(dict.getDictLabel())) {
					return dict.getDictValue();
				}
			}
		}
		return org.apache.commons.lang3.StringUtils.stripEnd(propertyString.toString(), separator);
	}

	/**
	 * Clear dict cache.
	 */
	public static void clearDictCache() {
		CacheUtils.removeAll(getCacheName());
	}

	/**
	 * Gets the cache name.
	 *
	 * @return the cache name
	 */
	public static String getCacheName() {
		return Constants.SYS_DICT_CACHE;
	}

	/**
	 * Gets the cache key.
	 *
	 * @param configKey the config key
	 * @return the cache key
	 */
	public static String getCacheKey(String configKey) {
		return Constants.SYS_DICT_KEY + configKey;
	}
}
