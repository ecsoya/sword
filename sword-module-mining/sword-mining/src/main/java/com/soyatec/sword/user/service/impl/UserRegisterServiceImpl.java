package com.soyatec.sword.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soyatec.sword.common.constant.UserConstants;
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.common.utils.async.AsyncManager;
import com.soyatec.sword.constants.IMiningConstants;
import com.soyatec.sword.system.service.ISysConfigService;
import com.soyatec.sword.system.service.ISysUserService;
import com.soyatec.sword.user.domain.User;
import com.soyatec.sword.user.domain.UserCertificate;
import com.soyatec.sword.user.domain.UserReferrer;
import com.soyatec.sword.user.service.IUserCertificateService;
import com.soyatec.sword.user.service.IUserReferrerService;
import com.soyatec.sword.user.service.IUserRegisterService;
import com.soyatec.sword.wallet.service.IUserWalletService;

@Service
public class UserRegisterServiceImpl implements IUserRegisterService {
	@Autowired
	private ISysConfigService configService;

	@Autowired
	private IUserReferrerService referrerService;

	@Autowired
	private ISysUserService userService;

	@Autowired
	private IUserCertificateService certificateService;

	@Autowired
	private IUserWalletService walletService;

	@Override
	public CommonResult<?> registerUser(User user, String referrerCode, String walletPassword) {
		return registerUser(user, referrerCode, walletPassword, true);
	}

	@Override
	public CommonResult<?> registerUser(User user, String referrerCode, String walletPassword, boolean async) {
		boolean needReferrerCode = "true" //$NON-NLS-1$
				.equalsIgnoreCase(configService.selectConfigValueByKey(IMiningConstants.REGISTER_NEED_REFERRER_CODE));
		Long referralId = null;
		if (StringUtils.isEmpty(referrerCode) && needReferrerCode) {
			return CommonResult.fail("注册推荐码为空"); //$NON-NLS-1$
		} else if (needReferrerCode) {
			UserReferrer referral = referrerService.selectUserReferrerByCode(referrerCode);
			if (referral == null) {
				return CommonResult.fail("无效的推荐码"); //$NON-NLS-1$
			}
			referralId = referral.getUserId();
		}
		if (user.getUserId() == null) {
			String email = user.getEmail();
			boolean needEmail = "true" //$NON-NLS-1$
					.equalsIgnoreCase(configService.selectConfigValueByKey(IMiningConstants.REGISTER_NEED_EMAIL));
			if (needEmail && StringUtils.isEmpty(email)) {
				return CommonResult.fail("注册邮箱不能为空"); //$NON-NLS-1$
			}

			String mobile = user.getPhonenumber();
			boolean needMobile = "true" //$NON-NLS-1$
					.equalsIgnoreCase(configService.selectConfigValueByKey(IMiningConstants.REGISTER_NEED_MOBILE));
			if (needMobile && StringUtils.isEmpty(mobile)) {
				return CommonResult.fail("注册手机不能为空"); //$NON-NLS-1$
			}

			String msg = "", username = user.getLoginName(), password = user.getPassword(); //$NON-NLS-1$

			if (StringUtils.isEmpty(username)) {
				msg = "用户名为空"; //$NON-NLS-1$
			} else if (StringUtils.isEmpty(password)) {
				msg = "密码为空"; //$NON-NLS-1$
			} else if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
					|| password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
				msg = "密码长度5-20个字符"; //$NON-NLS-1$
			} else if (username.length() < UserConstants.USERNAME_MIN_LENGTH
					|| username.length() > UserConstants.USERNAME_MAX_LENGTH) {
				msg = "用户名2-20个字符"; //$NON-NLS-1$
			} else if (UserConstants.USER_NAME_NOT_UNIQUE.equals(userService.checkLoginNameUnique(username))) {
				msg = "用户名" + username //$NON-NLS-1$
						+ "已被占用"; //$NON-NLS-1$
			} else {
				user.setSalt(StringUtils.randomStr(6));
				user.setPassword(StringUtils.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
				if (!userService.registerUser(user)) {
					return CommonResult.fail("注册失败"); //$NON-NLS-1$
				}
				return postRegisterUser(user.getUserId(), referralId, referrerCode, walletPassword, async);
			}
			return CommonResult.fail(msg);
		} else {
			user.setUserType(User.TYPE_USER);
			if (userService.updateUser(user) <= 0) {
				return CommonResult.fail("注册失败");
			}
			return postRegisterUser(user.getUserId(), referralId, referrerCode, walletPassword, async);
		}
	}

	@Override
	public CommonResult<?> postRegisterUser(Long userId, Long referralId, String referralCode, String walletPassword) {
		if (userId == null) {
			return CommonResult.fail();
		}
		// 1. 更新推荐关系
		if (referralId != null) {
			UserReferrer userReferrer = new UserReferrer();
			userReferrer.setUserId(userId);
			userReferrer.setReferralId(referralId);
			userReferrer.setReferralCode(referralCode);
			userReferrer.generateLeftCode();
			userReferrer.setLeftCodeEnabled(UserReferrer.ENABLED);
			userReferrer.generateRightCode();
			userReferrer.setRightCodeEnabled(UserReferrer.DISABLED);
			referrerService.insertUserReferrer(userReferrer);
		}
		// 2. 更新钱包
		CommonResult<?> checked = certificateService.checkUserCertificate(userId, UserCertificate.KIND_WALLET);
		walletService.createUserWalletByUserId(userId, walletPassword, checked.isSuccess());
		return CommonResult.success(userId);
	}

	@Override
	public CommonResult<?> postRegisterUser(Long userId, Long referralId, String referralCode, String walletPassword,
			boolean async) {

		if (userId == null) {
			return CommonResult.fail();
		}
		if (async) {
			AsyncManager.me().execute(new Runnable() {

				@Override
				public void run() {
					postRegisterUser(userId, referralId, referralCode, walletPassword);
				}
			});
			return CommonResult.success();
		}
		return postRegisterUser(userId, referralId, referralCode, walletPassword);
	}

}
