package com.github.ecsoya.sword.system.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.constant.Constants;
import com.github.ecsoya.sword.common.constant.UserConstants;
import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.exception.BusinessException;
import com.github.ecsoya.sword.common.utils.CacheUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.system.domain.SysConfig;
import com.github.ecsoya.sword.system.mapper.SysConfigMapper;
import com.github.ecsoya.sword.system.service.ISysConfigService;

/**
 * The Class SysConfigServiceImpl.
 */
@Service
public class SysConfigServiceImpl implements ISysConfigService {

	/** The config mapper. */
	@Autowired
	private SysConfigMapper configMapper;

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		final List<SysConfig> configsList = configMapper.selectConfigList(new SysConfig());
		for (final SysConfig config : configsList) {
			CacheUtils.put(getCacheName(), getCacheKey(config.getConfigKey()), config.getConfigValue());
		}
	}

	/**
	 * Select config by id.
	 *
	 * @param configId the config id
	 * @return the sys config
	 */
	@Override
	public SysConfig selectConfigById(Long configId) {
		final SysConfig config = new SysConfig();
		config.setConfigId(configId);
		return configMapper.selectConfig(config);
	}

	/**
	 * Select config value by key.
	 *
	 * @param configKey the config key
	 * @return the string
	 */
	@Override
	public String selectConfigValueByKey(String configKey) {
		final String configValue = Convert.toStr(CacheUtils.get(getCacheName(), getCacheKey(configKey)));
		if (StringUtils.isNotEmpty(configValue)) {
			return configValue;
		}
		final SysConfig config = new SysConfig();
		config.setConfigKey(configKey);
		final SysConfig retConfig = configMapper.selectConfig(config);
		if (StringUtils.isNotNull(retConfig)) {
			CacheUtils.put(getCacheName(), getCacheKey(configKey), retConfig.getConfigValue());
			return retConfig.getConfigValue();
		}
		return org.apache.commons.lang3.StringUtils.EMPTY;
	}

	/**
	 * Select config by key.
	 *
	 * @param configKey the config key
	 * @return the sys config
	 */
	@Override
	public SysConfig selectConfigByKey(String configKey) {
		if (StringUtils.isEmpty(configKey)) {
			return null;
		}
		final SysConfig config = new SysConfig();
		config.setConfigKey(configKey);
		return configMapper.selectConfig(config);
	}

	/**
	 * Select config list.
	 *
	 * @param config the config
	 * @return the list
	 */
	@Override
	public List<SysConfig> selectConfigList(SysConfig config) {
		return configMapper.selectConfigList(config);
	}

	/**
	 * Insert config.
	 *
	 * @param config the config
	 * @return the int
	 */
	@Override
	public int insertConfig(SysConfig config) {
		final int row = configMapper.insertConfig(config);
		if (row > 0) {
			CacheUtils.put(getCacheName(), getCacheKey(config.getConfigKey()), config.getConfigValue());
		}
		return row;
	}

	/**
	 * Update config.
	 *
	 * @param config the config
	 * @return the int
	 */
	@Override
	public int updateConfig(SysConfig config) {
		final int row = configMapper.updateConfig(config);
		if (row > 0) {
			config = configMapper.selectConfig(config);
			CacheUtils.put(getCacheName(), getCacheKey(config.getConfigKey()), config.getConfigValue());
		}
		return row;
	}

	/**
	 * Delete config by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteConfigByIds(String ids) {
		final Long[] configIds = Convert.toLongArray(ids);
		for (final Long configId : configIds) {
			final SysConfig config = selectConfigById(configId);
			if (org.apache.commons.lang3.StringUtils.equals(UserConstants.YES, config.getConfigType())) {
				throw new BusinessException(String.format("内置参数【%1$s】不能删除 ", config.getConfigKey()));
			}
		}
		final int count = configMapper.deleteConfigByIds(Convert.toStrArray(ids));
		if (count > 0) {

			CacheUtils.removeAll(getCacheName());
		}
		return count;
	}

	/**
	 * Clear cache.
	 */
	@Override
	public void clearCache() {
		CacheUtils.removeAll(getCacheName());
	}

	/**
	 * Check config key unique.
	 *
	 * @param config the config
	 * @return the string
	 */
	@Override
	public String checkConfigKeyUnique(SysConfig config) {
		final Long configId = StringUtils.isNull(config.getConfigId()) ? -1L : config.getConfigId();
		final SysConfig info = configMapper.checkConfigKeyUnique(config.getConfigKey());
		if (StringUtils.isNotNull(info) && info.getConfigId().longValue() != configId.longValue()) {
			return UserConstants.CONFIG_KEY_NOT_UNIQUE;
		}
		return UserConstants.CONFIG_KEY_UNIQUE;
	}

	/**
	 * Gets the cache name.
	 *
	 * @return the cache name
	 */
	private String getCacheName() {
		return Constants.SYS_CONFIG_CACHE;
	}

	/**
	 * Gets the cache key.
	 *
	 * @param configKey the config key
	 * @return the cache key
	 */
	private String getCacheKey(String configKey) {
		return Constants.SYS_CONFIG_KEY + configKey;
	}

	/**
	 * Select config with key.
	 *
	 * @param configKey the config key
	 * @return the sys config
	 */
	@Override
	public SysConfig selectConfigWithKey(String configKey) {
		if (StringUtils.isEmpty(configKey)) {
			return null;
		}
		final SysConfig config = new SysConfig();
		config.setConfigKey(configKey);
		return configMapper.selectConfig(config);
	}

	/**
	 * Check boolean config value.
	 *
	 * @param configKey the config key
	 * @return true, if successful
	 */
	@Override
	public boolean checkBooleanConfigValue(String configKey) {
		if (StringUtils.isEmpty(configKey)) {
			return false;
		}
		return "true".equalsIgnoreCase(selectConfigValueByKey(configKey));
	}
}
