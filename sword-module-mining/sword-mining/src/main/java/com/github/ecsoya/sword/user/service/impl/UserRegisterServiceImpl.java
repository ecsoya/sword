package com.github.ecsoya.sword.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.config.GlobalConfig;
import com.github.ecsoya.sword.common.constant.UserConstants;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.utils.MessageUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.async.AsyncManager;
import com.github.ecsoya.sword.constants.IMiningConstants;
import com.github.ecsoya.sword.system.service.ISysConfigService;
import com.github.ecsoya.sword.system.service.ISysUserService;
import com.github.ecsoya.sword.user.domain.User;
import com.github.ecsoya.sword.user.domain.UserCertificate;
import com.github.ecsoya.sword.user.domain.UserReferrer;
import com.github.ecsoya.sword.user.service.IUserBinaryTreeService;
import com.github.ecsoya.sword.user.service.IUserCertificateService;
import com.github.ecsoya.sword.user.service.IUserReferrerService;
import com.github.ecsoya.sword.user.service.IUserRegisterService;
import com.github.ecsoya.sword.wallet.service.IUserWalletService;

/**
 * The Class UserRegisterServiceImpl.
 */
@Service
public class UserRegisterServiceImpl implements IUserRegisterService {

	/** The config service. */
	@Autowired
	private ISysConfigService configService;

	/** The referrer service. */
	@Autowired
	private IUserReferrerService referrerService;

	/** The user service. */
	@Autowired
	private ISysUserService userService;

	/** The certificate service. */
	@Autowired
	private IUserCertificateService certificateService;

	/** The wallet service. */
	@Autowired
	private IUserWalletService walletService;

	/** The binary tree service. */
	@Autowired
	private IUserBinaryTreeService binaryTreeService;

	/**
	 * Register user.
	 *
	 * @param user           the user
	 * @param referrerCode   the referrer code
	 * @param walletPassword the wallet password
	 * @return the common result
	 */
	@Override
	public CommonResult<?> registerUser(User user, String referrerCode, String walletPassword) {
		return registerUser(user, referrerCode, walletPassword, true);
	}

	/**
	 * Register user.
	 *
	 * @param user           the user
	 * @param referrerCode   the referrer code
	 * @param walletPassword the wallet password
	 * @param async          the async
	 * @return the common result
	 */
	@Override
	public CommonResult<?> registerUser(User user, String referrerCode, String walletPassword, boolean async) {
		final boolean needReferrerCode = "true" //$NON-NLS-1$
				.equalsIgnoreCase(configService.selectConfigValueByKey(IMiningConstants.REGISTER_NEED_REFERRER_CODE));
		Long referralId = null;
		if (StringUtils.isEmpty(referrerCode) && needReferrerCode) {
			return CommonResult.fail(MessageUtils.message("UserRegisterServiceImpl.1")); //$NON-NLS-1$
		} else if (needReferrerCode) {
			final UserReferrer referral = referrerService.selectUserReferrerByCode(referrerCode);
			if (referral == null) {
				return CommonResult.fail(MessageUtils.message("UserRegisterServiceImpl.2")); //$NON-NLS-1$
			}
			referralId = referral.getUserId();
		}
		if (user.getUserId() == null) {
			// user.setUserId(100000000l + StringUtils.randomNum(8));// ?????????????????????ID??????
			final boolean emailUnique = "true" //$NON-NLS-1$
					.equalsIgnoreCase(configService.selectConfigValueByKey(IMiningConstants.REGISTER_EMAIL_UNIQUE));
			final String email = user.getEmail();
			// ????????????????????????????????????
			if (GlobalConfig.getEmailCode() || emailUnique) {
				if (StringUtils.isEmpty(email)) {
					return CommonResult.fail(MessageUtils.message("UserRegisterServiceImpl.3")); //$NON-NLS-1$
				}
			}

			if (emailUnique && !UserConstants.USER_EMAIL_UNIQUE.equals(userService.checkEmailUnique(user))) {
				return CommonResult.fail(MessageUtils.message("UserRegisterServiceImpl.5")); //$NON-NLS-1$
			}

			final String mobile = user.getPhonenumber();
			final boolean mobileUnique = "true".equalsIgnoreCase( //$NON-NLS-1$
					configService.selectConfigValueByKey(IMiningConstants.REGISTER_MOBILE_UNIQUE));
			// ????????????????????????????????????
			if (!GlobalConfig.getEmailCode() || mobileUnique) {
				if (StringUtils.isEmpty(mobile)) {
					return CommonResult.fail(MessageUtils.message("UserRegisterServiceImpl.6")); //$NON-NLS-1$
				}
			}
			if (mobileUnique && !UserConstants.USER_PHONE_UNIQUE.equals(userService.checkPhoneUnique(user))) {
				return CommonResult.fail(MessageUtils.message("UserRegisterServiceImpl.8")); //$NON-NLS-1$
			}

			String msg = ""; //$NON-NLS-1$
			final String username = user.getLoginName(), password = user.getPassword();

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

	/**
	 * Post register user.
	 *
	 * @param userId         the user id
	 * @param referralId     the referral id
	 * @param referralCode   the referral code
	 * @param walletPassword the wallet password
	 * @return the common result
	 */
	@Override
	public CommonResult<?> postRegisterUser(Long userId, Long referralId, String referralCode, String walletPassword) {
		if (userId == null) {
			return CommonResult.fail();
		}
		// 1. ??????????????????
		UserReferrer userReferrer = referrerService.selectUserReferrerById(userId);
		if (userReferrer == null) {
			userReferrer = new UserReferrer();
			userReferrer.setUserId(userId);
			userReferrer.setReferralId(referralId);
			userReferrer.setReferralCode(referralCode);
			userReferrer.generateLeftCode();
			userReferrer.setLeftCodeEnabled(UserReferrer.ENABLED);
			userReferrer.generateRightCode();
			userReferrer.setRightCodeEnabled(UserReferrer.DISABLED);
			userReferrer.setBtree(UserReferrer.BTREE_UNFINISHED);
			referrerService.insertUserReferrer(userReferrer);
		} else if (referralId != null) {
			final UserReferrer update = new UserReferrer();
			update.setUserId(userId);
			update.setReferralCode(referralCode);
			update.setReferralId(referralId);
			referrerService.updateUserReferrer(update);
		}
		// 2. ????????????
		final CommonResult<?> checked = certificateService.checkUserCertificate(userId, UserCertificate.KIND_WALLET);
		walletService.createUserWalletByUserId(userId, walletPassword, checked.isSuccess());

		// 3. ???????????????
		if ("true".equals(configService.selectConfigValueByKey(IMiningConstants.USER_BINARY_TREE_ENABLE))) { //$NON-NLS-1$
			binaryTreeService.updateUserBinaryTrees();
		}
		return CommonResult.success(userId);
	}

	/**
	 * Post register user.
	 *
	 * @param userId         the user id
	 * @param referralId     the referral id
	 * @param referralCode   the referral code
	 * @param walletPassword the wallet password
	 * @param async          the async
	 * @return the common result
	 */
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
