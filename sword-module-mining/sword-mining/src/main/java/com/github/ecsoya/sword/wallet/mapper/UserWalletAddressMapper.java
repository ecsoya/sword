package com.github.ecsoya.sword.wallet.mapper;

import java.util.List;

import com.github.ecsoya.sword.wallet.domain.UserWalletAddress;

/**
 * 用户钱包地址Mapper接口
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
public interface UserWalletAddressMapper {
	/**
	 * 查询用户钱包地址
	 *
	 * @param id 用户钱包地址ID
	 * @return 用户钱包地址
	 */
	public UserWalletAddress selectUserWalletAddressById(Long id);

	/**
	 * 查询用户钱包地址列表
	 *
	 * @param userWalletAddress 用户钱包地址
	 * @return 用户钱包地址集合
	 */
	public List<UserWalletAddress> selectUserWalletAddressList(UserWalletAddress userWalletAddress);

	/**
	 * 新增用户钱包地址
	 *
	 * @param userWalletAddress 用户钱包地址
	 * @return 结果
	 */
	public int insertUserWalletAddress(UserWalletAddress userWalletAddress);

	/**
	 * 修改用户钱包地址
	 *
	 * @param userWalletAddress 用户钱包地址
	 * @return 结果
	 */
	public int updateUserWalletAddress(UserWalletAddress userWalletAddress);

	/**
	 * 删除用户钱包地址
	 *
	 * @param id 用户钱包地址ID
	 * @return 结果
	 */
	public int deleteUserWalletAddressById(Long id);

	/**
	 * 批量删除用户钱包地址
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteUserWalletAddressByIds(String[] ids);
}
