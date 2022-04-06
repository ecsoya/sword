package com.github.ecsoya.sword.wallet.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.IdWorker;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.async.AsyncManager;
import com.github.ecsoya.sword.qrcode.QrcodeUtils;
import com.github.ecsoya.sword.wallet.domain.UserWalletAddress;
import com.github.ecsoya.sword.wallet.mapper.UserWalletAddressMapper;
import com.github.ecsoya.sword.wallet.service.IUserWalletAddressService;

/**
 * The Class UserWalletAddressServiceImpl.
 */
@Service
public class UserWalletAddressServiceImpl implements IUserWalletAddressService {

	/** The user wallet address mapper. */
	@Autowired
	private UserWalletAddressMapper userWalletAddressMapper;

	/**
	 * Select user wallet address by id.
	 *
	 * @param id the id
	 * @return the user wallet address
	 */
	@Override
	public UserWalletAddress selectUserWalletAddressById(Long id) {
		return updateQrcodeUrl(userWalletAddressMapper.selectUserWalletAddressById(id));
	}

	/**
	 * Select user wallet address list.
	 *
	 * @param userWalletAddress the user wallet address
	 * @return the list
	 */
	@Override
	public List<UserWalletAddress> selectUserWalletAddressList(UserWalletAddress userWalletAddress) {
		final List<UserWalletAddress> list = userWalletAddressMapper.selectUserWalletAddressList(userWalletAddress);
		list.forEach(walletAddress -> updateQrcodeUrl(walletAddress));
		return list;
	}

	/**
	 * Update qrcode url.
	 *
	 * @param walletAddress the wallet address
	 * @return the user wallet address
	 */
	private UserWalletAddress updateQrcodeUrl(UserWalletAddress walletAddress) {
		if (walletAddress == null) {
			return null;
		}
		if (StringUtils.isNotEmpty(walletAddress.getAddress())) {
			walletAddress.setQrcodeUrl(QrcodeUtils.generate(walletAddress.getAddress()));
			AsyncManager.me().execute(new Runnable() {

				@Override
				public void run() {
					final UserWalletAddress update = new UserWalletAddress();
					update.setId(walletAddress.getId());
					update.setQrcodeUrl(walletAddress.getQrcodeUrl());
					updateUserWalletAddress(update);
				}
			});
		}
		return walletAddress;
	}

	/**
	 * Insert user wallet address.
	 *
	 * @param userWalletAddress the user wallet address
	 * @return the int
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
	 * Update user wallet address.
	 *
	 * @param userWalletAddress the user wallet address
	 * @return the int
	 */
	@Override
	public int updateUserWalletAddress(UserWalletAddress userWalletAddress) {
		userWalletAddress.setUpdateTime(DateUtils.getNowDate());
		return userWalletAddressMapper.updateUserWalletAddress(userWalletAddress);
	}

	/**
	 * Delete user wallet address by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteUserWalletAddressByIds(String ids) {
		return userWalletAddressMapper.deleteUserWalletAddressByIds(Convert.toStrArray(ids));
	}

	/**
	 * Delete user wallet address by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Override
	public int deleteUserWalletAddressById(Long id) {
		return userWalletAddressMapper.deleteUserWalletAddressById(id);
	}

	/**
	 * Select user wallet address by user id.
	 *
	 * @param userId the user id
	 * @param symbol the symbol
	 * @return the list
	 */
	@Override
	public List<UserWalletAddress> selectUserWalletAddressByUserId(Long userId, String symbol) {
		if (userId == null) {
			return Collections.emptyList();
		}
		final UserWalletAddress query = new UserWalletAddress();
		query.setUserId(userId);
		query.setSymbol(symbol);
		return selectUserWalletAddressList(query);
	}
}
