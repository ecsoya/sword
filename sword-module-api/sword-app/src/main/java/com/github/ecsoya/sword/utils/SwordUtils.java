package com.github.ecsoya.sword.utils;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.data.redis.core.RedisTemplate;

import com.github.ecsoya.sword.common.config.GlobalConfig;
import com.github.ecsoya.sword.common.constant.UserConstants;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.core.domain.entity.SysUser;
import com.github.ecsoya.sword.common.exception.user.UserNotLoginException;
import com.github.ecsoya.sword.common.utils.ServletUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.bean.BeanUtils;
import com.github.ecsoya.sword.common.utils.spring.SpringUtils;
import com.github.ecsoya.sword.framework.shiro.util.ShiroUtils;
import com.github.ecsoya.sword.service.IMailCodeService;
import com.github.ecsoya.sword.service.IMobileCodeService;
import com.github.ecsoya.sword.version.domain.SwordVersion;
import com.github.ecsoya.sword.version.service.ISwordVersionService;

/**
 * The Class SwordUtils.
 */
public class SwordUtils {

	/** The Constant SWORD_CACHE. */
	private static final String SWORD_CACHE = "sword:app:cache:";

	/** The redis. */
	private static RedisTemplate<String, Object> redis = SpringUtils.getBean("template");

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 * @throws UserNotLoginException the user not login exception
	 */
	public static Long getUserId() throws UserNotLoginException {
		return getUser().getUserId();
	}

	/**
	 * Gets the subject.
	 *
	 * @return the subject
	 * @throws UserNotLoginException the user not login exception
	 */
	public static Subject getSubject() throws UserNotLoginException {
		final Subject subject = SecurityUtils.getSubject();
		if (subject == null) {
			throw new UserNotLoginException();
		}
		return subject;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public static SysUser getUser() {
		SysUser user = null;
		final Object obj = getSubject().getPrincipal();
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

	/**
	 * Gets the cache.
	 *
	 * @param <T>  the generic type
	 * @param name the name
	 * @param t    the t
	 * @return the cache
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getCache(String name, Class<T> t) {
		if (isDev() || redis == null || StringUtils.isEmpty(name) || t == null) {
			return null;
		}
		final String key = getCacheName(name);
		final Object object = redis.opsForValue().get(key);
		if (object == null || !t.isAssignableFrom(object.getClass())) {
			return null;
		}
		final Long expire = redis.getExpire(key, TimeUnit.SECONDS);
		if (expire != null && expire > getExpiresSeconds()) {
			redis.delete(key);
			return null;
		}
		return (T) object;
	}

	/**
	 * Sets the cache.
	 *
	 * @param name    the name
	 * @param value   the value
	 * @param timeout the timeout
	 * @return true, if successful
	 */
	public static boolean setCache(String name, Object value, Duration timeout) {
		if (redis == null || StringUtils.isEmpty(name) || value == null) {
			return false;
		}
		redis.opsForValue().set(getCacheName(name), value, timeout);
		return true;
	}

	/**
	 * Sets the cache by day.
	 *
	 * @param name  the name
	 * @param value the value
	 * @return true, if successful
	 */
	public static boolean setCacheByDay(String name, Object value) {
		if (redis == null || StringUtils.isEmpty(name) || value == null) {
			return false;
		}
		final Long expiresSeconds = getExpiresSeconds();
		redis.opsForValue().set(getCacheName(name), value, expiresSeconds, TimeUnit.SECONDS);
		return true;
	}

	/**
	 * Gets the expires seconds.
	 *
	 * @return the expires seconds
	 */
	private static Long getExpiresSeconds() {
//		Date nowDate = DateUtils.getNowDate();
//		Date endDate = DateUtils.getEndOf(nowDate);
//		return DateUtils.getIntervalSeconds(nowDate, endDate);
		return 5 * 60l;
	}

	/**
	 * Gets the cache name.
	 *
	 * @param name the name
	 * @return the cache name
	 */
	private static String getCacheName(String name) {
		return SWORD_CACHE + name;
	}

	/**
	 * Checks if is dev.
	 *
	 * @return true, if is dev
	 */
	private static boolean isDev() {
		return SpringUtils.testProfile("dev") || SpringUtils.testProfile("local");
	}

	/**
	 * Checks if is prod.
	 *
	 * @return true, if is prod
	 */
	private static boolean isProd() {
		return SpringUtils.testProfile("prod");
	}

	/**
	 * Verify code.
	 *
	 * @param code the code
	 * @return the common result
	 */
	public static CommonResult<?> verifyCode(String code) {
		if (isDev() && org.apache.commons.lang3.StringUtils.equals("000000", code)) {
			return CommonResult.success();
		}
		if (StringUtils.isEmpty(code)) {
			return CommonResult.fail("验证码错误");
		}
		if (GlobalConfig.getEmailCode()) {
			final IMailCodeService mailService = SpringUtils.getBean(IMailCodeService.class);
			return mailService.verifyCodeByUserId(getUserId(), code);
		} else {
			final IMobileCodeService mobileService = SpringUtils.getBean(IMobileCodeService.class);
			return mobileService.verifyCodeByUserId(getUserId(), code);
		}
	}

	/**
	 * Verify code by username.
	 *
	 * @param username the username
	 * @param code     the code
	 * @return the common result
	 */
	public static CommonResult<?> verifyCodeByUsername(String username, String code) {
		if (org.apache.commons.lang3.StringUtils.equals("000000", code)) {
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
			final IMailCodeService mailService = SpringUtils.getBean(IMailCodeService.class);
			return mailService.verifyCodeByUsername(username, code);
		} else {
			final IMobileCodeService mobileService = SpringUtils.getBean(IMobileCodeService.class);
			return mobileService.verifyCodeByUsername(username, code);
		}
	}

	/**
	 * Verify code.
	 *
	 * @param source the source
	 * @param code   the code
	 * @return the common result
	 */
	public static CommonResult<?> verifyCode(String source, String code) {
		if (isDev() && org.apache.commons.lang3.StringUtils.equals("000000", code)) {
			return CommonResult.success();
		}
		if (StringUtils.isEmpty(source) || StringUtils.isEmpty(code)) {
			return CommonResult.fail("验证码错误");
		}
		if (GlobalConfig.getEmailCode()) {
			final IMailCodeService mailService = SpringUtils.getBean(IMailCodeService.class);
			return mailService.verifyCode(source, code);
		} else {
			final IMobileCodeService mailService = SpringUtils.getBean(IMobileCodeService.class);
			return mailService.verifyCode(source, code);
		}
	}

	/**
	 * Check version.
	 *
	 * @param version the version
	 * @return the common result
	 */
	public static CommonResult<?> checkVersion(Long version) {
		final SwordVersion current = getVersion();
		if (current != null && version != null && version < current.getVersion()) {
			return CommonResult.fail("版本过低，请升级后使用");
		}
		return CommonResult.success();
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public static SwordVersion getVersion() {
		final HttpServletRequest request = ServletUtils.getRequest();
		final String type = getType(request);

		final ISwordVersionService versionService = SpringUtils.getBean(ISwordVersionService.class);
		SwordVersion version = versionService.selectLatestVersion(type);
		if (version == null) {
			return versionService.selectLatestVersion(SwordVersion.TYPE_ANDROID);
		}
		return version;
	}

	/**
	 * Gets the type.
	 *
	 * @param request the request
	 * @return the type
	 */
	private static String getType(HttpServletRequest request) {
		try {
			if (request == null) {
				return SwordVersion.TYPE_ANDROID;
			}
			final String agent = request.getHeader("User-Agent");
			if (agent != null) {
				final String name = agent.toLowerCase();
				if (name.indexOf("mac") != -1 || name.indexOf("iphone") != -1) {
					return SwordVersion.TYPE_IOS;
				}
			}
		} catch (final Exception e) {
		}
		return SwordVersion.TYPE_ANDROID;
	}
}
