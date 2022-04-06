package com.github.ecsoya.sword.common.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

/**
 * The Class MapDataUtil.
 */
public class MapDataUtil {

	/**
	 * Convert data map.
	 *
	 * @param request the request
	 * @return the map
	 */
	public static Map<String, Object> convertDataMap(HttpServletRequest request) {
		final Map<String, String[]> properties = request.getParameterMap();
		final Map<String, Object> returnMap = new HashMap<String, Object>();
		final Iterator<?> entries = properties.entrySet().iterator();
		Map.Entry<?, ?> entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Entry<?, ?>) entries.next();
			name = (String) entry.getKey();
			final Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				final String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}
}
