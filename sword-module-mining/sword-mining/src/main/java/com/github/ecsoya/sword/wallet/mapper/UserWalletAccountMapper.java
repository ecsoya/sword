package com.github.ecsoya.sword.wallet.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.ecsoya.sword.wallet.domain.UserWalletAccount;

/**
 * The Interface UserWalletAccountMapper.
 */
public interface UserWalletAccountMapper {

	/**
	 * Select user wallet account by id.
	 *
	 * @param id the id
	 * @return the user wallet account
	 */
	public UserWalletAccount selectUserWalletAccountById(Long id);

	/**
	 * Select user wallet account list.
	 *
	 * @param userWalletAccount the user wallet account
	 * @return the list
	 */
	public List<UserWalletAccount> selectUserWalletAccountList(UserWalletAccount userWalletAccount);

	/**
	 * Insert user wallet account.
	 *
	 * @param userWalletAccount the user wallet account
	 * @return the int
	 */
	public int insertUserWalletAccount(UserWalletAccount userWalletAccount);

	/**
	 * Update user wallet account.
	 *
	 * @param userWalletAccount the user wallet account
	 * @return the int
	 */
	public int updateUserWalletAccount(UserWalletAccount userWalletAccount);

	/**
	 * Delete user wallet account by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteUserWalletAccountById(Long id);

	/**
	 * Delete user wallet account by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteUserWalletAccountByIds(String[] ids);

	/**
	 * Select user wallet account.
	 *
	 * @param userId the user id
	 * @param symbol the symbol
	 * @return the user wallet account
	 */
	public UserWalletAccount selectUserWalletAccount(@Param("userId") Long userId, @Param("symbol") String symbol);

	/**
	 * Lock user wallet account by id.
	 *
	 * @param id the id
	 * @return the user wallet account
	 */
	public UserWalletAccount lockUserWalletAccountById(Long id);

	/**
	 * Select user account amount.
	 *
	 * @param symbol the symbol
	 * @param kind   the kind
	 * @return the big decimal
	 */
	public BigDecimal selectUserAccountAmount(@Param("symbol") String symbol, @Param("kind") Integer kind);
}
