package com.github.ecsoya.sword.zbx.utils;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.security.EncryptUtil;

public class HttpUtil {

	public static String paramToUrl(Map<String, Object> data) {
		final Set<String> keySet = data.keySet();
		final String[] keyArray = keySet.toArray(new String[keySet.size()]);
		Arrays.sort(keyArray);
		final StringBuilder sb = new StringBuilder();
		for (final String k : keyArray) {
			sb.append(k).append("=").append(data.get(k).toString().trim()).append("&");
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	public static String getSignature(final Map<String, Object> data, String secretKey) {
		try {
			final Set<String> keySet = data.keySet();
			final String[] keyArray = keySet.toArray(new String[keySet.size()]);
			Arrays.sort(keyArray);
			final StringBuilder sb = new StringBuilder();
			for (final String key : keyArray) {
				if (StringUtils.isEmpty(key)) {
					continue;
				}
				final Object value = data.get(key);
				if (value == null) {
					continue;
				}
				sb.append(key).append("=").append(value.toString().trim()).append("&");
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}
			return EncryptUtil.hmacSha256(sb.toString(), secretKey);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}