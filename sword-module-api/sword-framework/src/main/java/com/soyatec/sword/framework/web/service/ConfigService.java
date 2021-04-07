package com.soyatec.sword.framework.web.service;

import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soyatec.sword.common.config.GlobalConfig;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.system.service.ISysConfigService;

/**
 * RuoYi首创 html调用 thymeleaf 实现参数管理
 * 
 * @author Jin Liu (angryred@qq.com)
 */
@Service("config")
public class ConfigService {
	@Autowired
	private ISysConfigService configService;

	/**
	 * 根据键名查询参数配置信息
	 * 
	 * @param configKey 参数键名
	 * @return 参数键值
	 */
	public String getKey(String configKey) {
		return configService.selectConfigValueByKey(configKey);
	}

	/**
	 * 查询全局变量 {@link GlobalConfig}
	 * 
	 * @param key
	 * @return
	 */
	public Object global(String key) {
		if (StringUtils.isEmpty(key)) {
			return null;
		}
		try {
			Field f = GlobalConfig.class.getDeclaredField(key);
			f.setAccessible(true);
			return f.get(null);
		} catch (Exception e) {
			return null;

		}
	}
}
