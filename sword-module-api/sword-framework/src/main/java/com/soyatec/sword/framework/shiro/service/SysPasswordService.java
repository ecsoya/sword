package com.soyatec.sword.framework.shiro.service;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.soyatec.sword.common.constant.Constants;
import com.soyatec.sword.common.constant.ShiroConstants;
import com.soyatec.sword.common.core.domain.entity.SysUser;
import com.soyatec.sword.common.exception.user.UserPasswordNotMatchException;
import com.soyatec.sword.common.exception.user.UserPasswordRetryLimitExceedException;
import com.soyatec.sword.common.utils.MessageUtils;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.common.utils.async.AsyncManager;
import com.soyatec.sword.framework.manager.factory.AsyncFactory;

/**
 * 登录密码方法
 * 
 * @author Jin Liu (angryred@qq.com)
 */
@Component
public class SysPasswordService {
	@Autowired
	private CacheManager cacheManager;

	private Cache<String, AtomicInteger> loginRecordCache;

	@Value(value = "${user.password.maxRetryCount}")
	private String maxRetryCount;

	@PostConstruct
	public void init() {
		loginRecordCache = cacheManager.getCache(ShiroConstants.LOGINRECORDCACHE);
	}

	public void validate(SysUser user, String password) {
		String loginName = user.getLoginName();

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

	public boolean matches(SysUser user, String newPassword) {
		return user.getPassword().equals(encryptPassword(user.getLoginName(), newPassword, user.getSalt()));
	}

	public void clearLoginRecordCache(String loginName) {
		loginRecordCache.remove(loginName);
	}

	public String encryptPassword(String loginName, String password, String salt) {
		return StringUtils.encryptPassword(loginName, password, salt);
	}

	public static void main(String[] args) {
//		System.out.println(StringUtils.encryptPassword("develop", "qwer1234", "111111"));
//		System.out.println(StringUtils.encryptPassword("admin", "qwer1234", "222222"));
		System.out.println(StringUtils.encryptPassword("MF111", "123456", "NAY73I"));
	}
}
