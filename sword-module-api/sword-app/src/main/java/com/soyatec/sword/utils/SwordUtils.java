package com.soyatec.sword.utils;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.data.redis.core.RedisTemplate;

import com.soyatec.sword.common.config.GlobalConfig;
import com.soyatec.sword.common.constant.UserConstants;
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.core.domain.entity.SysUser;
import com.soyatec.sword.common.exception.user.UserNotLoginException;
import com.soyatec.sword.common.utils.ServletUtils;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.common.utils.bean.BeanUtils;
import com.soyatec.sword.common.utils.spring.SpringUtils;
import com.soyatec.sword.framework.shiro.util.ShiroUtils;
import com.soyatec.sword.service.IMailCodeService;
import com.soyatec.sword.service.IMobileCodeService;
import com.soyatec.sword.version.domain.SwordVersion;
import com.soyatec.sword.version.service.ISwordVersionService;

public class SwordUtils {

	private static final String SWORD_CACHE = "sword:app:cache:";

	private static RedisTemplate<String, Object> redis = SpringUtils.getBean("template");

	public static Long getUserId() throws UserNotLoginException {
		return getUser().getUserId();
	}

	public static Subject getSubject() throws UserNotLoginException {
		Subject subject = SecurityUtils.getSubject();
		if (subject == null) {
			throw new UserNotLoginException();
		}
		return subject;
	}

	public static SysUser getUser() {
		SysUser user = null;
		Object obj = getSubject().getPrincipal();
		if (StringUtils.isNotNull(obj)) {
			user = new SysUser();
			BeanUtils.copyBeanProp(user, obj);
			if (!(UserConstants.REGISTER_USER_TYPE.equals(user.getUserType()))) {
				ShiroUtils.logout();
				throw new UserNotLoginException();// 不是正确的用户
			}
			return user;
		}
		throw new UserNotLoginException();
	}

	@SuppressWarnings("unchecked")
	public static <T> T getCache(String name, Class<T> t) {
		if (isDev() || redis == null || StringUtils.isEmpty(name) || t == null) {
			return null;
		}
		String key = getCacheName(name);
		Object object = redis.opsForValue().get(key);
		if (object == null || !t.isAssignableFrom(object.getClass())) {
			return null;
		}
		Long expire = redis.getExpire(key, TimeUnit.SECONDS);
		if (expire != null && expire > getExpiresSeconds()) {
			redis.delete(key);
			return null;
		}
		return (T) object;
	}

	public static boolean setCache(String name, Object value, Duration timeout) {
		if (redis == null || StringUtils.isEmpty(name) || value == null) {
			return false;
		}
		redis.opsForValue().set(getCacheName(name), value, timeout);
		return true;
	}

	public static boolean setCacheByDay(String name, Object value) {
		if (redis == null || StringUtils.isEmpty(name) || value == null) {
			return false;
		}
		Long expiresSeconds = getExpiresSeconds();
		redis.opsForValue().set(getCacheName(name), value, expiresSeconds, TimeUnit.SECONDS);
		return true;
	}

	private static Long getExpiresSeconds() {
//		Date nowDate = DateUtils.getNowDate();
//		Date endDate = DateUtils.getEndOf(nowDate);
//		return DateUtils.getIntervalSeconds(nowDate, endDate);
		return 5 * 60l;
	}

	private static String getCacheName(String name) {
		return SWORD_CACHE + name;
	}

	private static boolean isDev() {
		return SpringUtils.testProfile("dev");
	}

	private static boolean isProd() {
		return SpringUtils.testProfile("prod");
	}

	public static CommonResult<?> verifyCode(String code) {
		if (isDev() && StringUtils.equals("000000", code)) {
			return CommonResult.success();
		}
		if (StringUtils.isEmpty(code)) {
			return CommonResult.fail("验证码错误");
		}
		if (GlobalConfig.getEmailCode()) {
			IMailCodeService mailService = SpringUtils.getBean(IMailCodeService.class);
			return mailService.verifyCodeByUserId(getUserId(), code);
		} else {
			IMobileCodeService mobileService = SpringUtils.getBean(IMobileCodeService.class);
			return mobileService.verifyCodeByUserId(getUserId(), code);
		}
	}

	public static CommonResult<?> verifyCodeByUsername(String username, String code) {
		if (StringUtils.equals("000000", code)) {
			if (isDev()) {
				return CommonResult.success();
			} else if (isProd() && "test001".equals(username)) {
				return CommonResult.success();
			}
		}
		if (StringUtils.isEmpty(code)) {
			return CommonResult.fail("验证码错误");
		}
		if (GlobalConfig.getEmailCode()) {
			IMailCodeService mailService = SpringUtils.getBean(IMailCodeService.class);
			return mailService.verifyCodeByUsername(username, code);
		} else {
			IMobileCodeService mobileService = SpringUtils.getBean(IMobileCodeService.class);
			return mobileService.verifyCodeByUsername(username, code);
		}
	}

	public static CommonResult<?> verifyCode(String source, String code) {
		if (isDev() && StringUtils.equals("000000", code)) {
			return CommonResult.success();
		}
		if (StringUtils.isEmpty(source) || StringUtils.isEmpty(code)) {
			return CommonResult.fail("验证码错误");
		}
		if (GlobalConfig.getEmailCode()) {
			IMailCodeService mailService = SpringUtils.getBean(IMailCodeService.class);
			return mailService.verifyCode(source, code);
		} else {
			IMobileCodeService mailService = SpringUtils.getBean(IMobileCodeService.class);
			return mailService.verifyCode(source, code);
		}
	}

	public static CommonResult<?> checkVersion(Long version) {
		SwordVersion current = getVersion();
		if (current != null && version != null && version < current.getVersion()) {
			return CommonResult.fail("版本过低，请升级后使用");
		}
		return CommonResult.success();
	}

	public static SwordVersion getVersion() {
		HttpServletRequest request = ServletUtils.getRequest();
		String type = getType(request);

		ISwordVersionService versionService = SpringUtils.getBean(ISwordVersionService.class);
		return versionService.selectLatestVersion(type);
	}

	private static String getType(HttpServletRequest request) {
		if (request == null) {
			return SwordVersion.TYPE_ANDROID;
		}
		String agent = request.getHeader("User-Agent");
		if (agent != null) {
			String name = agent.toLowerCase();
			if (name.indexOf("mac") != -1 || name.indexOf("iphone") != -1) {
				return SwordVersion.TYPE_IOS;
			}
		}
		return SwordVersion.TYPE_ANDROID;
	}
}
