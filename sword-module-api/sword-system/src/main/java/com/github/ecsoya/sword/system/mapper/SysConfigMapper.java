package com.github.ecsoya.sword.system.mapper;

import java.util.List;

import com.github.ecsoya.sword.system.domain.SysConfig;

/**
 * The Interface SysConfigMapper.
 */
public interface SysConfigMapper {

	/**
	 * Select config.
	 *
	 * @param config the config
	 * @return the sys config
	 */
	public SysConfig selectConfig(SysConfig config);

	/**
	 * Select config list.
	 *
	 * @param config the config
	 * @return the list
	 */
	public List<SysConfig> selectConfigList(SysConfig config);

	/**
	 * Check config key unique.
	 *
	 * @param configKey the config key
	 * @return the sys config
	 */
	public SysConfig checkConfigKeyUnique(String configKey);

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
	 * @param configIds the config ids
	 * @return the int
	 */
	public int deleteConfigByIds(String[] configIds);
}