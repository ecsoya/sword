package com.soyatec.sword.wallet.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soyatec.sword.common.core.text.Convert;
import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.common.utils.IdWorker;
import com.soyatec.sword.common.utils.QrcodeUtils;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.common.utils.async.AsyncManager;
import com.soyatec.sword.wallet.domain.UserWalletAddress;
import com.soyatec.sword.wallet.mapper.UserWalletAddressMapper;
import com.soyatec.sword.wallet.service.IUserWalletAddressService;

/**
 * 用户钱包地址Service业务层处理
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-06
 */
@Service
public class UserWalletAddressServiceImpl implements IUserWalletAddressService {
	@Autowired
	private UserWalletAddressMapper userWalletAddressMapper;

	/**
	 * 查询用户钱包地址
	 * 
	 * @param id 用户钱包地址ID
	 * @return 用户钱包地址
	 */
	@Override
	public UserWalletAddress selectUserWalletAddressById(Long id) {
		return updateQrcodeUrl(userWalletAddressMapper.selectUserWalletAddressById(id));
	}

	/**
	 * 查询用户钱包地址列表
	 * 
	 * @param userWalletAddress 用户钱包地址
	 * @return 用户钱包地址
	 */
	@Override
	public List<UserWalletAddress> selectUserWalletAddressList(UserWalletAddress userWalletAddress) {
		List<UserWalletAddress> list = userWalletAddressMapper.selectUserWalletAddressList(userWalletAddress);
		list.forEach(walletAddress -> updateQrcodeUrl(walletAddress));
		return list;
	}

	private UserWalletAddress updateQrcodeUrl(UserWalletAddress walletAddress) {
		if (walletAddress == null) {
			return null;
		}
		if (StringUtils.isNotEmpty(walletAddress.getAddress())) {
			walletAddress.setQrcodeUrl(QrcodeUtils.generate(walletAddress.getAddress()));
			AsyncManager.me().execute(new Runnable() {

				@Override
				public void run() {
					UserWalletAddress update = new UserWalletAddress();
					update.setId(walletAddress.getId());
					update.setQrcodeUrl(walletAddress.getQrcodeUrl());
					updateUserWalletAddress(update);
				}
			});
		}
		return walletAddress;
	}

	/**
	 * 新增用户钱包地址
	 * 
	 * @param userWalletAddress 用户钱包地址
	 * @return 结果
	 */
	@Override
	public int insertUserWalletAddress(UserWalletAddress userWalletAddress) {
		if (userWalletAddress.getId() == null) {
			userWalletAddress.setId(IdWorker.getId());
		}
		if (userWalletAddress.getCreateTime() == null) {
			userWalletAddress.setCreateTime(DateUtils.getNowDate());
		}
		if (StringUtils.isNotEmpty(userWalletAddress.getAddress())) {
			userWalletAddress.setQrcodeUrl(QrcodeUtils.generate(userWalletAddress.getAddress()));
		}
		return userWalletAddressMapper.insertUserWalletAddress(userWalletAddress);
	}

	/**
	 * 修改用户钱包地址
	 * 
	 * @param userWalletAddress 用户钱包地址
	 * @return 结果
	 */
	@Override
	public int updateUserWalletAddress(UserWalletAddress userWalletAddress) {
		userWalletAddress.setUpdateTime(DateUtils.getNowDate());
		return userWalletAddressMapper.updateUserWalletAddress(userWalletAddress);
	}

	/**
	 * 删除用户钱包地址对象
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteUserWalletAddressByIds(String ids) {
		return userWalletAddressMapper.deleteUserWalletAddressByIds(Convert.toStrArray(ids));
	}

	/**
	 * 删除用户钱包地址信息
	 * 
	 * @param id 用户钱包地址ID
	 * @return 结果
	 */
	@Override
	public int deleteUserWalletAddressById(Long id) {
		return userWalletAddressMapper.deleteUserWalletAddressById(id);
	}

	@Override
	public List<UserWalletAddress> selectUserWalletAddressByUserId(Long userId, String symbol) {
		if (userId == null) {
			return Collections.emptyList();
		}
		UserWalletAddress query = new UserWalletAddress();
		query.setUserId(userId);
		query.setSymbol(symbol);
		return selectUserWalletAddressList(query);
	}
}
