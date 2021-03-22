package com.soyatec.sword.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soyatec.sword.common.config.GlobalConfig;
import com.soyatec.sword.common.constant.UserConstants;
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.utils.IdWorker;
import com.soyatec.sword.common.utils.MessageUtils;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.common.utils.async.AsyncManager;
import com.soyatec.sword.constants.IMiningConstants;
import com.soyatec.sword.system.service.ISysConfigService;
import com.soyatec.sword.system.service.ISysUserService;
import com.soyatec.sword.user.domain.User;
import com.soyatec.sword.user.domain.UserCertificate;
import com.soyatec.sword.user.domain.UserReferrer;
import com.soyatec.sword.user.service.IUserBinaryTreeService;
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

	@Autowired
	private IUserBinaryTreeService binaryTreeService;

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
			return CommonResult.fail(MessageUtils.message("UserRegisterServiceImpl.1")); //$NON-NLS-1$
		} else if (needReferrerCode) {
			UserReferrer referral = referrerService.selectUserReferrerByCode(referrerCode);
			if (referral == null) {
				return CommonResult.fail(MessageUtils.message("UserRegisterServiceImpl.2")); //$NON-NLS-1$
			}
			referralId = referral.getUserId();
		}
		if (user.getUserId() == null) {
			user.setUserId(IdWorker.getId());// 避免和导入用户ID冲突
			// 使用邮箱验证码
			if (GlobalConfig.getEmailCode()) {
				String email = user.getEmail();
				if (StringUtils.isEmpty(email)) {
					return CommonResult.fail(MessageUtils.message("UserRegisterServiceImpl.3")); //$NON-NLS-1$
				}
				boolean emailUnique = "true" //$NON-NLS-1$
						.equalsIgnoreCase(configService.selectConfigValueByKey(IMiningConstants.REGISTER_EMAIL_UNIQUE));
				if (emailUnique && !UserConstants.USER_EMAIL_UNIQUE.equals(userService.checkEmailUnique(user))) {
					return CommonResult.fail(MessageUtils.message("UserRegisterServiceImpl.5")); //$NON-NLS-1$
				}

			} else {
				String mobile = user.getPhonenumber();
				if (StringUtils.isEmpty(mobile)) {
					return CommonResult.fail(MessageUtils.message("UserRegisterServiceImpl.6")); //$NON-NLS-1$
				}
				boolean mobileUnique = "true".equalsIgnoreCase( //$NON-NLS-1$
						configService.selectConfigValueByKey(IMiningConstants.REGISTER_MOBILE_UNIQUE));
				if (mobileUnique && !UserConstants.USER_PHONE_UNIQUE.equals(userService.checkPhoneUnique(user))) {
					return CommonResult.fail(MessageUtils.message("UserRegisterServiceImpl.8")); //$NON-NLS-1$
				}
			}

			String msg = "", username = user.getLoginName(), password = user.getPassword(); //$NON-NLS-1$

			if (StringUtils.isEmpty(username)) {
				msg = MessageUtils.message("UserRegisterServiceImpl.10"); //$NON-NLS-1$
			} else if (StringUtils.isEmpty(password)) {
				msg = MessageUtils.message("UserRegisterServiceImpl.11"); //$NON-NLS-1$
			} else if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
					|| password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
				msg = MessageUtils.message("UserRegisterServiceImpl.12"); //$NON-NLS-1$
			} else if (username.length() < UserConstants.USERNAME_MIN_LENGTH
					|| username.length() > UserConstants.USERNAME_MAX_LENGTH) {
				msg = MessageUtils.message("UserRegisterServiceImpl.13"); //$NON-NLS-1$
			} else if (UserConstants.USER_NAME_NOT_UNIQUE.equals(userService.checkLoginNameUnique(username))) {
				msg = MessageUtils.message("UserRegisterServiceImpl.14") + username //$NON-NLS-1$
						+ MessageUtils.message("UserRegisterServiceImpl.15"); //$NON-NLS-1$
			} else {
				user.setSalt(StringUtils.randomStr(6));
				user.setPassword(StringUtils.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
				if (!userService.registerUser(user)) {
					return CommonResult.fail(MessageUtils.message("UserRegisterServiceImpl.16")); //$NON-NLS-1$
				}
				return postRegisterUser(user.getUserId(), referralId, referrerCode, walletPassword, async);
			}
			return CommonResult.fail(msg);
		} else {
			user.setUserType(User.TYPE_USER);
			if (userService.updateUser(user) <= 0) {
				return CommonResult.fail(MessageUtils.message("UserRegisterServiceImpl.17")); //$NON-NLS-1$
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
			userReferrer.setBtree(UserReferrer.BTREE_UNFINISHED);
			referrerService.insertUserReferrer(userReferrer);
		}
		// 2. 更新钱包
		CommonResult<?> checked = certificateService.checkUserCertificate(userId, UserCertificate.KIND_WALLET);
		walletService.createUserWalletByUserId(userId, walletPassword, checked.isSuccess());

		// 3. 注册双区树
		if ("true".equals(configService.selectConfigValueByKey(IMiningConstants.USER_BINARY_TREE_ENABLE))) { //$NON-NLS-1$
			binaryTreeService.updateUserBinaryTrees();
		}
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
