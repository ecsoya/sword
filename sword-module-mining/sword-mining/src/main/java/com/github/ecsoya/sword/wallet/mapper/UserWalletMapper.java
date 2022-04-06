package com.github.ecsoya.sword.wallet.mapper;

import java.util.List;

import com.github.ecsoya.sword.wallet.domain.UserWallet;

/**
 * The Interface UserWalletMapper.
 */
public interface UserWalletMapper {

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
	 * Delete user wallet by id.
	 *
	 * @param userId the user id
	 * @return the int
	 */
	public int deleteUserWalletById(Long userId);

	/**
	 * Delete user wallet by ids.
	 *
	 * @param userIds the user ids
	 * @return the int
	 */
	public int deleteUserWalletByIds(String[] userIds);
}
