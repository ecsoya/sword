package com.github.ecsoya.sword.framework.shiro.service;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.github.ecsoya.sword.common.constant.Constants;
import com.github.ecsoya.sword.common.constant.ShiroConstants;
import com.github.ecsoya.sword.common.core.domain.entity.SysUser;
import com.github.ecsoya.sword.common.exception.user.UserPasswordNotMatchException;
import com.github.ecsoya.sword.common.exception.user.UserPasswordRetryLimitExceedException;
import com.github.ecsoya.sword.common.password.PasswordMatcher;
import com.github.ecsoya.sword.common.utils.MessageUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.async.AsyncManager;
import com.github.ecsoya.sword.framework.manager.factory.AsyncFactory;

/**
 * The Class SysPasswordService.
 */
@Component
public class SysPasswordService {

	/** The cache manager. */
	@Autowired
	private CacheManager cacheManager;

	/** The login record cache. */
	private Cache<String, AtomicInteger> loginRecordCache;

	/** The max retry count. */
	@Value(value = "${user.password.maxRetryCount}")
	private String maxRetryCount;

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		loginRecordCache = cacheManager.getCache(ShiroConstants.LOGINRECORDCACHE);
	}

	/**
	 * Validate.
	 *
	 * @param user     the user
	 * @param password the password
	 */
	public void validate(SysUser user, String password) {
		final String loginName = user.getLoginName();

		AtomicInteger retryCount = loginRecordCache.get(loginName);

		if (retryCount == null) {
			retryCount = new AtomicInteger(0);
			loginRecordCache.put(loginName, retryCount);
		}
		if (retryCount.incrementAndGet() > Integer.valueOf(maxRetryCount).intValue()) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL,
					MessageUtils.message("user.password.retry.limit.exceed", maxRetryCount)));
			throw new UserPasswordRetryLimitExceedException(Integer.valueOf(maxRetryCount).intValue());
		}

		if (!matches(user, password)) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL,
					MessageUtils.message("user.password.retry.limit.count", retryCount)));
			loginRecordCache.put(loginName, retryCount);
			throw new UserPasswordNotMatchException();
		} else {
			clearLoginRecordCache(loginName);
		}
	}

	/**
	 * Matches.
	 *
	 * @param user        the user
	 * @param newPassword the new password
	 * @return true, if successful
	 */
	public boolean matches(SysUser user, String newPassword) {
		return PasswordMatcher.matches(user, newPassword);
//		return user.getPassword().equals(encryptPassword(user.getLoginName(), newPassword, user.getSalt()));
	}

	/**
	 * Clear login record cache.
	 *
	 * @param loginName the login name
	 */
	public void clearLoginRecordCache(String loginName) {
		loginRecordCache.remove(loginName);
	}

	/**
	 * Encrypt password.
	 *
	 * @param loginName the login name
	 * @param password  the password
	 * @param salt      the salt
	 * @return the string
	 */
	public String encryptPassword(String loginName, String password, String salt) {
		SysUser user = new SysUser();
		user.setLoginName(loginName);
		user.setSalt(salt);
		user.setPassword(password);
		return PasswordMatcher.encryptPassword(user, password);
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
//		System.out.println(StringUtils.encryptPassword("develop", "qwer1234", "111111"));
//		System.out.println(StringUtils.encryptPassword("admin", "qwer1234", "222222"));
		System.out.println(StringUtils.encryptPassword("MF111", "123456", "NAY73I"));
	}
}
