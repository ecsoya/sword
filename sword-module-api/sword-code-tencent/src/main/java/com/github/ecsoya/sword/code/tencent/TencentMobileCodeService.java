package com.github.ecsoya.sword.code.tencent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.ecsoya.sword.common.utils.StringUtils;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;

/**
 * The Class TencentMobileCodeService.
 */
@Component
public class TencentMobileCodeService {

	/** The config. */
	@Autowired
	private TencentCodeProperties config;

	/** The Constant CHINESE_MOBILE_REGEX. */
	private static final String CHINESE_MOBILE_REGEX = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";

	/**
	 * Send code.
	 *
	 * @param templateId the template id
	 * @param code       the code
	 * @param mobiles    the mobiles
	 * @return the send sms response
	 */
	public SendSmsResponse sendCode(Long templateId, String code, String... mobiles) {
		if (mobiles == null || mobiles.length == 0 || StringUtils.isEmpty(code) || templateId == null) {
			return null;
		}
		final Credential cred = new Credential(config.getSecretId(), config.getSecretKey());
		final SmsClient client = new SmsClient(cred, config.getRegion());
		final SendSmsRequest request = new SendSmsRequest();
		request.setTemplateID(templateId.toString());
		request.setTemplateParamSet(new String[] { code });
		request.setPhoneNumberSet(addContryCode(mobiles));
		request.setSmsSdkAppid(config.getSdkAppId());
		request.setSign(config.getSignName());
		try {
			return client.SendSms(request);
		} catch (final TencentCloudSDKException e) {
			return null;
		}
	}

	/**
	 * Checks if is chinese mobile.
	 *
	 * @param mobile the mobile
	 * @return true, if is chinese mobile
	 */
	private boolean isChineseMobile(String mobile) {
		if (StringUtils.isEmpty(mobile)) {
			return false;
		}
		if (mobile.length() == 11) {
			final Pattern p = Pattern.compile(CHINESE_MOBILE_REGEX);
			final java.util.regex.Matcher m = p.matcher(mobile);
			return m.matches();
		}
		return false;
	}

	/**
	 * Adds the contry code.
	 *
	 * @param mobiles the mobiles
	 * @return the string[]
	 */
	private String[] addContryCode(String[] mobiles) {
		if (mobiles == null || mobiles.length == 0) {
			return mobiles;
		}
		List<String> result = new ArrayList<>();
		for (String number : mobiles) {
			if (isChineseMobile(number)) {
				result.add("+86" + number);
			} else {
				result.add(number);
			}
		}
		return result.toArray(new String[result.size()]);
	}

}
