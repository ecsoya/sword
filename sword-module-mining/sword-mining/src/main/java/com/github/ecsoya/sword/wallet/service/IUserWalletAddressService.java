package com.github.ecsoya.sword.wallet.service;

import java.util.List;

import com.github.ecsoya.sword.wallet.domain.UserWalletAddress;

/**
 * The Interface IUserWalletAddressService.
 */
public interface IUserWalletAddressService {

	/**
	 * Select user wallet address by id.
	 *
	 * @param id the id
	 * @return the user wallet address
	 */
	public UserWalletAddress selectUserWalletAddressById(Long id);

	/**
	 * Select user wallet address list.
	 *
	 * @param userWalletAddress the user wallet address
	 * @return the list
	 */
	public List<UserWalletAddress> selectUserWalletAddressList(UserWalletAddress userWalletAddress);

	/**
	 * Insert user wallet address.
	 *
	 * @param userWalletAddress the user wallet address
	 * @return the int
	 */
	public int insertUserWalletAddress(UserWalletAddress userWalletAddress);

	/**
	 * Update user wallet address.
	 *
	 * @param userWalletAddress the user wallet address
	 * @return the int
	 */
	public int updateUserWalletAddress(UserWalletAddress userWalletAddress);

	/**
	 * Delete user wallet address by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteUserWalletAddressByIds(String ids);

	/**
	 * Delete user wallet address by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteUserWalletAddressById(Long id);

	/**
	 * Select user wallet address by user id.
	 *
	 * @param userId the user id
	 * @param symbol the symbol
	 * @return the list
	 */
	public List<UserWalletAddress> selectUserWalletAddressByUserId(Long userId, String symbol);
}
