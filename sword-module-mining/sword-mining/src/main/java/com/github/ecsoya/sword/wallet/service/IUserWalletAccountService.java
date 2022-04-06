package com.github.ecsoya.sword.wallet.service;

import java.math.BigDecimal;
import java.util.List;

import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.exceptions.TransactionException;
import com.github.ecsoya.sword.wallet.domain.UserWalletAccount;
import com.github.ecsoya.sword.wallet.domain.UserWalletRecord;

/**
 * The Interface IUserWalletAccountService.
 */
public interface IUserWalletAccountService {

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
	 * Delete user wallet account by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteUserWalletAccountByIds(String ids);

	/**
	 * Delete user wallet account by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteUserWalletAccountById(Long id);

	/**
	 * Select user wallet account.
	 *
	 * @param userId the user id
	 * @param symbol the symbol
	 * @return the user wallet account
	 */
	public UserWalletAccount selectUserWalletAccount(Long userId, String symbol);

	/**
	 * Update user wallet account by user id.
	 *
	 * @param userId the user id
	 * @return the int
	 */
	public int updateUserWalletAccountByUserId(Long userId);

	/**
	 * Select user wallet account list by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	public List<UserWalletAccount> selectUserWalletAccountListByUserId(Long userId);

	/**
	 * Freeze amount.
	 *
	 * @param walletAccount the wallet account
	 * @param amount        the amount
	 * @param orderNo       the order no
	 * @param freezeDays    the freeze days
	 * @param remark        the remark
	 * @param details       the details
	 * @return true, if successful
	 */
	public boolean freezeAmount(UserWalletAccount walletAccount, BigDecimal amount, String orderNo, Integer freezeDays,
			String remark, Integer details);

	/**
	 * Unfreeze amount.
	 *
	 * @param userId  the user id
	 * @param symbol  the symbol
	 * @param amount  the amount
	 * @param orderNo the order no
	 * @param payBack the pay back
	 * @return true, if successful
	 */
	public boolean unfreezeAmount(Long userId, String symbol, BigDecimal amount, String orderNo, boolean payBack);

	/**
	 * Select user account with balance.
	 *
	 * @param userId  the user id
	 * @param symbol  the symbol
	 * @param balance the balance
	 * @return the user wallet account
	 */
	public UserWalletAccount selectUserAccountWithBalance(Long userId, String symbol, BigDecimal balance);

	/**
	 * Decrease amount.
	 *
	 * @param walletAccount the wallet account
	 * @param amount        the amount
	 * @param orderNo       the order no
	 * @param remark        the remark
	 * @param details       the details
	 * @return true, if successful
	 */
	public boolean decreaseAmount(UserWalletAccount walletAccount, BigDecimal amount, String orderNo, String remark,
			Integer details);

	/**
	 * Select user wallet account by address.
	 *
	 * @param address the address
	 * @param symbol  the symbol
	 * @return the user wallet account
	 */
	public UserWalletAccount selectUserWalletAccountByAddress(String address, String symbol);

	/**
	 * Increase amount.
	 *
	 * @param account the account
	 * @param amount  the amount
	 * @param orderNo the order no
	 * @param remark  the remark
	 * @param details the details
	 * @return true, if successful
	 */
	public boolean increaseAmount(UserWalletAccount account, BigDecimal amount, String orderNo, String remark,
			Integer details);

	/**
	 * Update account.
	 *
	 * @param account the account
	 * @param type    the type
	 * @param kind    the kind
	 * @param amount  the amount
	 * @param orderNo the order no
	 * @param remark  the remark
	 * @param days    the days
	 * @param details the details
	 * @return true, if successful
	 */
	public boolean updateAccount(UserWalletAccount account, Integer type, Integer kind, BigDecimal amount,
			String orderNo, String remark, Integer days, Integer details);

	/**
	 * Release locked amount.
	 *
	 * @param record the record
	 * @return the int
	 */
	public int releaseLockedAmount(UserWalletRecord record);

	/**
	 * Release locked amount.
	 *
	 * @param userId        the user id
	 * @param symbol        the symbol
	 * @param orderNo       the order no
	 * @param lockedAmount  the locked amount
	 * @param releaseAmount the release amount
	 * @return true, if successful
	 * @throws TransactionException the transaction exception
	 */
	public boolean releaseLockedAmount(Long userId, String symbol, String orderNo, BigDecimal lockedAmount,
			BigDecimal releaseAmount) throws TransactionException;

	/**
	 * Admin set account by user id.
	 *
	 * @param userId the user id
	 * @param symbol the symbol
	 * @param kind   the kind
	 * @param value  the value
	 * @return the int
	 */
	public int adminSetAccountByUserId(Long userId, String symbol, Integer kind, BigDecimal value);

	/**
	 * Change user wallet account withdrawal enabled.
	 *
	 * @param userId  the user id
	 * @param symbol  the symbol
	 * @param enabled the enabled
	 * @return the int
	 */
	public int changeUserWalletAccountWithdrawalEnabled(Long userId, String symbol, Integer enabled);

	/**
	 * Exchange amount.
	 *
	 * @param userId        the user id
	 * @param fromSymbol    the from symbol
	 * @param toSymbol      the to symbol
	 * @param price         the price
	 * @param positivePrice the positive price
	 * @param amount        the amount
	 * @param details       the details
	 * @return the common result
	 */
	public CommonResult<?> exchangeAmount(Long userId, String fromSymbol, String toSymbol, BigDecimal price,
			boolean positivePrice, BigDecimal amount, Integer details);

	/**
	 * Unfreeze to amount.
	 *
	 * @param userWalletRecord the user wallet record
	 * @return true, if successful
	 */
	public boolean unfreezeToAmount(UserWalletRecord userWalletRecord);

	/**
	 * Select user account amount.
	 *
	 * @param symbol the symbol
	 * @param kind   the kind
	 * @return the big decimal
	 */
	public BigDecimal selectUserAccountAmount(String symbol, Integer kind);

	/**
	 * Update user wallet account address.
	 *
	 * @param userId the user id
	 * @param symbol the symbol
	 * @param chain  the chain
	 * @return the user wallet account
	 */
	public UserWalletAccount updateUserWalletAccountAddress(Long userId, String symbol, String chain);

}
