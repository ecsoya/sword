package com.github.ecsoya.sword.wallet.mapper;

import java.util.List;

import com.github.ecsoya.sword.wallet.domain.UserWalletAddress;

/**
 * The Interface UserWalletAddressMapper.
 */
public interface UserWalletAddressMapper {

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
	 * Delete user wallet address by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteUserWalletAddressById(Long id);

	/**
	 * Delete user wallet address by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteUserWalletAddressByIds(String[] ids);
}
