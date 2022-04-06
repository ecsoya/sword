package com.github.ecsoya.sword.wallet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.MessageUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.wallet.domain.UserWallet;
import com.github.ecsoya.sword.wallet.mapper.UserWalletMapper;
import com.github.ecsoya.sword.wallet.service.IUserWalletAccountService;
import com.github.ecsoya.sword.wallet.service.IUserWalletService;

/**
 * The Class UserWalletServiceImpl.
 */
@Service
public class UserWalletServiceImpl implements IUserWalletService {

	/** The user wallet mapper. */
	@Autowired
	private UserWalletMapper userWalletMapper;

	/** The wallet account service. */
	@Autowired
	private IUserWalletAccountService walletAccountService;

	/**
	 * Select user wallet by id.
	 *
	 * @param userId the user id
	 * @return the user wallet
	 */
	@Override
	public UserWallet selectUserWalletById(Long userId) {
		return selectUserWalletById(userId, false);
	}

	/**
	 * Select user wallet by id.
	 *
	 * @param userId       the user id
	 * @param withAccounts the with accounts
	 * @return the user wallet
	 */
	@Override
	public UserWallet selectUserWalletById(Long userId, boolean withAccounts) {
		if (userId == null) {
			return null;
		}
		final UserWallet wallet = userWalletMapper.selectUserWalletById(userId);
		if (wallet != null && withAccounts) {
			wallet.setAccounts(walletAccountService.selectUserWalletAccountListByUserId(userId));
		}
		return wallet;
	}

	/**
	 * Select user wallet list.
	 *
	 * @param userWallet the user wallet
	 * @return the list
	 */
	@Override
	public List<UserWallet> selectUserWalletList(UserWallet userWallet) {
		return userWalletMapper.selectUserWalletList(userWallet);
	}

	/**
	 * Insert user wallet.
	 *
	 * @param userWallet the user wallet
	 * @return the int
	 */
	@Override
	public int insertUserWallet(UserWallet userWallet) {
		userWallet.setCreateTime(DateUtils.getNowDate());
		return userWalletMapper.insertUserWallet(userWallet);
	}

	/**
	 * Update user wallet.
	 *
	 * @param userWallet the user wallet
	 * @return the int
	 */
	@Override
	public int updateUserWallet(UserWallet userWallet) {
		userWallet.setUpdateTime(DateUtils.getNowDate());
		return userWalletMapper.updateUserWallet(userWallet);
	}

	/**
	 * Delete user wallet by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteUserWalletByIds(String ids) {
		return userWalletMapper.deleteUserWalletByIds(Convert.toStrArray(ids));
	}

	/**
	 * Delete user wallet by id.
	 *
	 * @param userId the user id
	 * @return the int
	 */
	@Override
	public int deleteUserWalletById(Long userId) {
		return userWalletMapper.deleteUserWalletById(userId);
	}

	/**
	 * Creates the user wallet by user id.
	 *
	 * @param userId         the user id
	 * @param password       the password
	 * @param createAccounts the create accounts
	 * @return the int
	 */
	@Override
	public int createUserWalletByUserId(Long userId, String password, boolean createAccounts) {
		if (userId == null) {
			return 0;
		}
		UserWallet wallet = selectUserWalletById(userId);
		if (wallet == null) {
			wallet = new UserWallet();
			wallet.setUserId(userId);
			wallet.setSalt(StringUtils.randomNum(6));
			insertUserWallet(wallet);
		}
		if (StringUtils.isNotEmpty(password)) {
			wallet.setSalt(StringUtils.randomNum(6));
			wallet.setPassword(StringUtils.encryptPassword(userId.toString(), password, wallet.getSalt()));
			updateUserWallet(wallet);
		}
		// 创建钱包
		if (createAccounts) {
			walletAccountService.updateUserWalletAccountByUserId(userId);
		}
		return 1;
	}

	/**
	 * Verify user wallet password.
	 *
	 * @param userId   the user id
	 * @param password the password
	 * @return the common result
	 */
	@Override
	public CommonResult<?> verifyUserWalletPassword(Long userId, String password) {
		if (userId == null || StringUtils.isEmpty(password)) {
			return CommonResult.fail(MessageUtils.message("UserWalletPasswordServiceImpl.0")); //$NON-NLS-1$
		}
		final UserWallet wallet = selectUserWalletById(userId);
		if (wallet == null) {
			return CommonResult.fail(MessageUtils.message("UserWalletPasswordServiceImpl.1")); //$NON-NLS-1$
		}
		if (StringUtils.encryptPassword(userId.toString(), password, wallet.getSalt()).equals(wallet.getPassword())) {
			return CommonResult.success();
		}
		return CommonResult.fail(MessageUtils.message("UserWalletPasswordServiceImpl.2")); //$NON-NLS-1$
	}

	/**
	 * Change user wallet password.
	 *
	 * @param userId      the user id
	 * @param oldPassword the old password
	 * @param newPassword the new password
	 * @return the common result
	 */
	@Override
	public CommonResult<?> changeUserWalletPassword(Long userId, String oldPassword, String newPassword) {
		UserWallet wallet = selectUserWalletById(userId);
		if (wallet != null) {
			final CommonResult<?> verified = verifyUserWalletPassword(userId, oldPassword);
			if (!verified.isSuccess()) {
				return verified;
			} else if (StringUtils.isEmpty(newPassword) || newPassword.length() < 6) {
				return CommonResult.fail(MessageUtils.message("UserWalletPasswordServiceImpl.3")); //$NON-NLS-1$
			}
			final String salt = StringUtils.randomNum(6);
			wallet.setSalt(salt);
			wallet.setPassword(StringUtils.encryptPassword(userId.toString(), newPassword, salt));
			return CommonResult.ajax(updateUserWallet(wallet));
		} else {
			wallet = new UserWallet();
			wallet.setUserId(userId);
			final String salt = StringUtils.randomNum(6);
			wallet.setSalt(salt);
			wallet.setPassword(StringUtils.encryptPassword(userId.toString(), newPassword, salt));
			return CommonResult.ajax(insertUserWallet(wallet));
		}
	}

	/**
	 * Reset user wallet password.
	 *
	 * @param userId   the user id
	 * @param password the password
	 * @return the int
	 */
	@Override
	public int resetUserWalletPassword(Long userId, String password) {
		if (userId == null || StringUtils.isEmpty(password) || password.length() < 6) {
			return 0;
		}
		UserWallet wallet = selectUserWalletById(userId);
		if (wallet != null) {
			final String salt = StringUtils.randomNum(6);
			wallet.setSalt(salt);
			wallet.setPassword(StringUtils.encryptPassword(userId.toString(), password, salt));
			return updateUserWallet(wallet);
		} else {
			wallet = new UserWallet();
			wallet.setUserId(userId);
			final String salt = StringUtils.randomNum(6);
			wallet.setSalt(salt);
			wallet.setPassword(StringUtils.encryptPassword(userId.toString(), password, salt));
			return insertUserWallet(wallet);
		}
	}
}
