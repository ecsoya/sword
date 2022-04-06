package com.github.ecsoya.sword.common.utils;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

/**
 * The Class YamlUtil.
 */
public class YamlUtil {

	/**
	 * Load yaml.
	 *
	 * @param fileName the file name
	 * @return the map
	 * @throws FileNotFoundException the file not found exception
	 */
	public static Map<?, ?> loadYaml(String fileName) throws FileNotFoundException {
		final InputStream in = YamlUtil.class.getClassLoader().getResourceAsStream(fileName);
		return StringUtils.isNotEmpty(fileName) ? (LinkedHashMap<?, ?>) new Yaml().load(in) : null;
	}

	/**
	 * Dump yaml.
	 *
	 * @param fileName the file name
	 * @param map      the map
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void dumpYaml(String fileName, Map<?, ?> map) throws IOException {
		if (StringUtils.isNotEmpty(fileName)) {
			final FileWriter fileWriter = new FileWriter(YamlUtil.class.getResource(fileName).getFile());
			final DumperOptions options = new DumperOptions();
			options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
			final Yaml yaml = new Yaml(options);
			yaml.dump(map, fileWriter);
		}
	}

	/**
	 * Gets the property.
	 *
	 * @param map          the map
	 * @param qualifiedKey the qualified key
	 * @return the property
	 */
	public static Object getProperty(Map<?, ?> map, Object qualifiedKey) {
		if (map != null && !map.isEmpty() && qualifiedKey != null) {
			final String input = String.valueOf(qualifiedKey);
			if (!"".equals(input)) {
				if (input.contains(".")) {
					final int index = input.indexOf(".");
					final String left = input.substring(0, index);
					final String right = input.substring(index + 1, input.length());
					return getProperty((Map<?, ?>) map.get(left), right);
				} else if (map.containsKey(input)) {
					return map.get(input);
				} else {
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * Sets the property.
	 *
	 * @param map          the map
	 * @param qualifiedKey the qualified key
	 * @param value        the value
	 */
	@SuppressWarnings("unchecked")
	public static void setProperty(Map<?, ?> map, Object qualifiedKey, Object value) {
		if (map != null && !map.isEmpty() && qualifiedKey != null) {
			final String input = String.valueOf(qualifiedKey);
			if (!input.equals("")) {
				if (input.contains(".")) {
					final int index = input.indexOf(".");
					final String left = input.substring(0, index);
					final String right = input.substring(index + 1, input.length());
					setProperty((Map<?, ?>) map.get(left), right, value);
				} else {
					((Map<Object, Object>) map).put(qualifiedKey, value);
				}
			}
		}
	}
}