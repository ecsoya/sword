package com.github.ecsoya.sword.wallet.service;

import java.util.List;

import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.wallet.domain.UserWallet;

/**
 * The Interface IUserWalletService.
 */
public interface IUserWalletService {

	/**
	 * Select user wallet by id.
	 *
	 * @param userId the user id
	 * @return the user wallet
	 */
	public UserWallet selectUserWalletById(Long userId);

	/**
	 * Select user wallet list.
	 *
	 * @param userWallet the user wallet
	 * @return the list
	 */
	public List<UserWallet> selectUserWalletList(UserWallet userWallet);

	/**
	 * Insert user wallet.
	 *
	 * @param userWallet the user wallet
	 * @return the int
	 */
	public int insertUserWallet(UserWallet userWallet);

	/**
	 * Update user wallet.
	 *
	 * @param userWallet the user wallet
	 * @return the int
	 */
	public int updateUserWallet(UserWallet userWallet);

	/**
	 * Delete user wallet by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteUserWalletByIds(String ids);

	/**
	 * Delete user wallet by id.
	 *
	 * @param userId the user id
	 * @return the int
	 */
	public int deleteUserWalletById(Long userId);

	/**
	 * Creates the user wallet by user id.
	 *
	 * @param userId         the user id
	 * @param password       the password
	 * @param createAccounts the create accounts
	 * @return the int
	 */
	public int createUserWalletByUserId(Long userId, String password, boolean createAccounts);

	/**
	 * Select user wallet by id.
	 *
	 * @param userId       the user id
	 * @param withAccounts the with accounts
	 * @return the user wallet
	 */
	public UserWallet selectUserWalletById(Long userId, boolean withAccounts);

	/**
	 * Verify user wallet password.
	 *
	 * @param userId   the user id
	 * @param password the password
	 * @return the common result
	 */
	public CommonResult<?> verifyUserWalletPassword(Long userId, String password);

	/**
	 * Change user wallet password.
	 *
	 * @param userId      the user id
	 * @param oldPassword the old password
	 * @param newPassword the new password
	 * @return the common result
	 */
	public CommonResult<?> changeUserWalletPassword(Long userId, String oldPassword, String newPassword);

	/**
	 * Reset user wallet password.
	 *
	 * @param userId   the user id
	 * @param password the password
	 * @return the int
	 */
	public int resetUserWalletPassword(Long userId, String password);
}
