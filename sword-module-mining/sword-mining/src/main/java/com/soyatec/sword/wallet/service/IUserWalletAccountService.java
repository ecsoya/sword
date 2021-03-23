package com.soyatec.sword.wallet.service;

import java.math.BigDecimal;
import java.util.List;

import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.exceptions.TransactionException;
import com.soyatec.sword.wallet.domain.UserWalletAccount;
import com.soyatec.sword.wallet.domain.UserWalletRecord;

/**
 * 用户钱包账号Service接口
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
public interface IUserWalletAccountService {
	/**
	 * 查询用户钱包账号
	 * 
	 * @param id 用户钱包账号ID
	 * @return 用户钱包账号
	 */
	public UserWalletAccount selectUserWalletAccountById(Long id);

	/**
	 * 查询用户钱包账号列表
	 * 
	 * @param userWalletAccount 用户钱包账号
	 * @return 用户钱包账号集合
	 */
	public List<UserWalletAccount> selectUserWalletAccountList(UserWalletAccount userWalletAccount);

	/**
	 * 新增用户钱包账号
	 * 
	 * @param userWalletAccount 用户钱包账号
	 * @return 结果
	 */
	public int insertUserWalletAccount(UserWalletAccount userWalletAccount);

	/**
	 * 修改用户钱包账号
	 * 
	 * @param userWalletAccount 用户钱包账号
	 * @return 结果
	 */
	public int updateUserWalletAccount(UserWalletAccount userWalletAccount);

	/**
	 * 批量删除用户钱包账号
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteUserWalletAccountByIds(String ids);

	/**
	 * 删除用户钱包账号信息
	 * 
	 * @param id 用户钱包账号ID
	 * @return 结果
	 */
	public int deleteUserWalletAccountById(Long id);

	public UserWalletAccount selectUserWalletAccount(Long userId, String symbol);

	public int updateUserWalletAccountByUserId(Long userId);

	public List<UserWalletAccount> selectUserWalletAccountListByUserId(Long userId);

	/**
	 * 冻结资产
	 */
	public boolean freezeAmount(UserWalletAccount walletAccount, BigDecimal amount, String orderNo, Integer freezeDays);

	/**
	 * 资金解冻
	 */
	public boolean unfreezeAmount(Long userId, String symbol, BigDecimal amount, String orderNo, boolean payBack);

	/**
	 * 查询并验证余额
	 */
	public UserWalletAccount selectUserAccountWithBalance(Long userId, String symbol, BigDecimal balance);

	/**
	 * 消费
	 */
	public boolean decreaseAmount(UserWalletAccount walletAccount, BigDecimal amount, String orderNo);

	public UserWalletAccount selectUserWalletAccountByAddress(String address, String symbol);

	/**
	 * 充值
	 */
	public boolean increaseAmount(UserWalletAccount account, BigDecimal amount, String orderNo, String remark);

	/**
	 * 更新钱包
	 */
	public boolean updateAccount(UserWalletAccount account, Integer type, Integer kind, BigDecimal amount,
			String orderNo, String remark, Integer days);

	/**
	 * 释放锁定账户到余额
	 */
	public int releaseLockedAmount(UserWalletRecord record);

	/**
	 * 释放锁定账户到余额
	 */
	public boolean releaseLockedAmount(Long userId, String symbol, String orderNo, BigDecimal lockedAmount,
			BigDecimal releaseAmount) throws TransactionException;

	/**
	 * 后台设置余额
	 */
	public int adminSetAccountByUserId(Long userId, String symbol, Integer kind, BigDecimal value);

	public int changeUserWalletAccountWithdrawalEnabled(Long userId, String symbol, Integer enabled);

	/**
	 * 内部换算
	 */
	public CommonResult<?> exchangeAmount(Long userId, String fromSymbol, String toSymbol, BigDecimal price,
			boolean positivePrice, BigDecimal amount);

	public boolean unfreezeAmount(UserWalletRecord userWalletRecord);

	public BigDecimal selectUserAccountAmount(String symbol, Integer kind);

	public UserWalletAccount updateUserWalletAccountAddress(Long userId, String symbol);

}
