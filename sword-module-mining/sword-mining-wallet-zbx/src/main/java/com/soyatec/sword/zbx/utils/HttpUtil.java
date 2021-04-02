package com.soyatec.sword.zbx.utils;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.common.utils.security.EncryptUtil;

public class HttpUtil {

	public static String paramToUrl(Map<String, Object> data) {
		Set<String> keySet = data.keySet();
		String[] keyArray = keySet.toArray(new String[keySet.size()]);
		Arrays.sort(keyArray);
		StringBuilder sb = new StringBuilder();
		for (String k : keyArray) {
			sb.append(k).append("=").append(data.get(k).toString().trim()).append("&");
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	public static String getSignature(final Map<String, Object> data, String secretKey) {
		try {
			Set<String> keySet = data.keySet();
			String[] keyArray = keySet.toArray(new String[keySet.size()]);
			Arrays.sort(keyArray);
			StringBuilder sb = new StringBuilder();
			for (String key : keyArray) {
				if (StringUtils.isEmpty(key)) {
					continue;
				}
				Object value = data.get(key);
				if (value == null) {
					continue;
				}
				sb.append(key).append("=").append(value.toString().trim()).append("&");
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}
			return EncryptUtil.hmacSha256(sb.toString(), secretKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}