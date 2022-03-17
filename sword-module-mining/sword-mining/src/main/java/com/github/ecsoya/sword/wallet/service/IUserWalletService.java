package com.github.ecsoya.sword.wallet.service;

import java.util.List;

import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.wallet.domain.UserWallet;

/**
 * 用户钱包Service接口
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
public interface IUserWalletService {
	/**
	 * 查询用户钱包
	 *
	 * @param userId 用户钱包ID
	 * @return 用户钱包
	 */
	public UserWallet selectUserWalletById(Long userId);

	/**
	 * 查询用户钱包列表
	 *
	 * @param userWallet 用户钱包
	 * @return 用户钱包集合
	 */
	public List<UserWallet> selectUserWalletList(UserWallet userWallet);

	/**
	 * 新增用户钱包
	 *
	 * @param userWallet 用户钱包
	 * @return 结果
	 */
	public int insertUserWallet(UserWallet userWallet);

	/**
	 * 修改用户钱包
	 *
	 * @param userWallet 用户钱包
	 * @return 结果
	 */
	public int updateUserWallet(UserWallet userWallet);

	/**
	 * 批量删除用户钱包
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteUserWalletByIds(String ids);

	/**
	 * 删除用户钱包信息
	 *
	 * @param userId 用户钱包ID
	 * @return 结果
	 */
	public int deleteUserWalletById(Long userId);

	/**
	 * 更新用户钱包
	 *
	 * @param createAccounts TODO
	 */
	public int createUserWalletByUserId(Long userId, String password, boolean createAccounts);

	public UserWallet selectUserWalletById(Long userId, boolean withAccounts);

	public CommonResult<?> verifyUserWalletPassword(Long userId, String password);

	public CommonResult<?> changeUserWalletPassword(Long userId, String oldPassword, String newPassword);

	public int resetUserWalletPassword(Long userId, String password);
}
