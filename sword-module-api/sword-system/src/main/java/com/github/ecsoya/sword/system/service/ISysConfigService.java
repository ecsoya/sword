package com.github.ecsoya.sword.system.service;

import java.util.List;

import com.github.ecsoya.sword.system.domain.SysConfig;

/**
 * The Interface ISysConfigService.
 */
public interface ISysConfigService {

	/**
	 * Select config by id.
	 *
	 * @param configId the config id
	 * @return the sys config
	 */
	public SysConfig selectConfigById(Long configId);

	/**
	 * Select config value by key.
	 *
	 * @param configKey the config key
	 * @return the string
	 */
	public String selectConfigValueByKey(String configKey);

	/**
	 * Select config by key.
	 *
	 * @param configKey the config key
	 * @return the sys config
	 */
	public SysConfig selectConfigByKey(String configKey);

	/**
	 * Select config list.
	 *
	 * @param config the config
	 * @return the list
	 */
	public List<SysConfig> selectConfigList(SysConfig config);

	/**
	 * Insert config.
	 *
	 * @param config the config
	 * @return the int
	 */
	public int insertConfig(SysConfig config);

	/**
	 * Update config.
	 *
	 * @param config the config
	 * @return the int
	 */
	public int updateConfig(SysConfig config);

	/**
	 * Delete config by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteConfigByIds(String ids);

	/**
	 * Clear cache.
	 */
	public void clearCache();

	/**
	 * Check config key unique.
	 *
	 * @param config the config
	 * @return the string
	 */
	public String checkConfigKeyUnique(SysConfig config);

	/**
	 * Select config with key.
	 *
	 * @param configKey the config key
	 * @return the sys config
	 */
	public SysConfig selectConfigWithKey(String configKey);

	/**
	 * Check boolean config value.
	 *
	 * @param configKey the config key
	 * @return true, if successful
	 */
	public boolean checkBooleanConfigValue(String configKey);
}
