package com.github.ecsoya.sword.wallet.mapper;

import java.util.List;

import com.github.ecsoya.sword.wallet.domain.UserWallet;

/**
 * 用户钱包Mapper接口
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
public interface UserWalletMapper {
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
	 * 删除用户钱包
	 *
	 * @param userId 用户钱包ID
	 * @return 结果
	 */
	public int deleteUserWalletById(Long userId);

	/**
	 * 批量删除用户钱包
	 *
	 * @param userIds 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteUserWalletByIds(String[] userIds);
}
