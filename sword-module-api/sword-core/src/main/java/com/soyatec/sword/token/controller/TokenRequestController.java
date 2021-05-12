package com.soyatec.sword.token.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.common.utils.security.EncryptUtil;
import com.soyatec.sword.common.utils.spring.SpringUtils;
import com.soyatec.sword.token.domain.TokenRequest;
import com.soyatec.sword.token.service.ITokenSecretService;

public class TokenRequestController {

	protected CommonResult<?> checkRequest(TokenRequest request) {
		if (request == null) {
			return CommonResult.fail("参数错误");
		}
		ITokenSecretService accessService = SpringUtils.getBean(ITokenSecretService.class);
		String accessKey = request.getAccessKey();
		if (StringUtils.isEmpty(accessKey)) {
			return CommonResult.fail("参数错误：AccessKey不能为空");
		}
		String secretKey = accessService.selectTokenSecretKey(accessKey);
		if (StringUtils.isEmpty(secretKey)) {
			return CommonResult.fail("参数错误：无效的AccessKey");
		}

		String sign = request.getSign();
		if (StringUtils.isEmpty(sign)) {
			return CommonResult.fail("参数错误：签名错误");
		}
		String data = request.getData();
		if (StringUtils.isEmpty(data)) {
			return CommonResult.fail("参数错误：无效的data");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("accessKey", accessKey);
		params.put("data", data);
		final String target = getSignature(params, secretKey);
		if (!sign.equals(target)) {
			return CommonResult.fail("API签名验证未通过");
		}
		return CommonResult.success();
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
