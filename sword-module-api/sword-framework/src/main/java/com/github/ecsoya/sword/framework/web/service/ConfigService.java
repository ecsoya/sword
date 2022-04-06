package com.github.ecsoya.sword.framework.web.service;

import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.config.GlobalConfig;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.spring.SpringUtils;
import com.github.ecsoya.sword.system.service.ISysConfigService;

/**
 * The Class ConfigService.
 */
@Service("config")
public class ConfigService {

	/** The config service. */
	@Autowired
	private ISysConfigService configService;

	/**
	 * Gets the key.
	 *
	 * @param configKey the config key
	 * @return the key
	 */
	public String getKey(String configKey) {
		return configService.selectConfigValueByKey(configKey);
	}

	/**
	 * Global.
	 *
	 * @param key the key
	 * @return the object
	 */
	public Object global(String key) {
		if (StringUtils.isEmpty(key)) {
			return null;
		}
		try {
			final Field f = GlobalConfig.class.getDeclaredField(key);
			f.setAccessible(true);
			return f.get(null);
		} catch (final Exception e) {
			return "";
		}
	}

	/**
	 * Profile.
	 *
	 * @param profile the profile
	 * @return true, if successful
	 */
	public boolean profile(String profile) {
		return StringUtils.equals(profile, SpringUtils.getActiveProfile());
	}
}
