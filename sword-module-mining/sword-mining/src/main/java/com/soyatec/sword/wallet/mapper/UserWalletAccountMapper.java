package com.soyatec.sword.wallet.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.soyatec.sword.wallet.domain.UserWalletAccount;

/**
 * 用户钱包账号Mapper接口
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
public interface UserWalletAccountMapper {
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
	 * 删除用户钱包账号
	 *
	 * @param id 用户钱包账号ID
	 * @return 结果
	 */
	public int deleteUserWalletAccountById(Long id);

	/**
	 * 批量删除用户钱包账号
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteUserWalletAccountByIds(String[] ids);

	public UserWalletAccount selectUserWalletAccount(@Param("userId") Long userId, @Param("symbol") String symbol);

	/**
	 * 锁行
	 */
	public UserWalletAccount lockUserWalletAccountById(Long id);

	public BigDecimal selectUserAccountAmount(@Param("symbol") String symbol, @Param("kind") Integer kind);
}
