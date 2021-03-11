package com.soyatec.sword.wallet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.core.text.Convert;
import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.common.utils.MessageUtils;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.wallet.domain.UserWallet;
import com.soyatec.sword.wallet.mapper.UserWalletMapper;
import com.soyatec.sword.wallet.service.IUserWalletAccountService;
import com.soyatec.sword.wallet.service.IUserWalletService;

/**
 * 用户钱包Service业务层处理
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
@Service
public class UserWalletServiceImpl implements IUserWalletService {
	@Autowired
	private UserWalletMapper userWalletMapper;

	@Autowired
	private IUserWalletAccountService walletAccountService;

	/**
	 * 查询用户钱包
	 * 
	 * @param userId 用户钱包ID
	 * @return 用户钱包
	 */
	@Override
	public UserWallet selectUserWalletById(Long userId) {
		return selectUserWalletById(userId, false);
	}

	@Override
	public UserWallet selectUserWalletById(Long userId, boolean withAccounts) {
		if (userId == null) {
			return null;
		}
		UserWallet wallet = userWalletMapper.selectUserWalletById(userId);
		if (wallet != null && withAccounts) {
			wallet.setAccounts(walletAccountService.selectUserWalletAccountListByUserId(userId));
		}
		return wallet;
	}

	/**
	 * 查询用户钱包列表
	 * 
	 * @param userWallet 用户钱包
	 * @return 用户钱包
	 */
	@Override
	public List<UserWallet> selectUserWalletList(UserWallet userWallet) {
		return userWalletMapper.selectUserWalletList(userWallet);
	}

	/**
	 * 新增用户钱包
	 * 
	 * @param userWallet 用户钱包
	 * @return 结果
	 */
	@Override
	public int insertUserWallet(UserWallet userWallet) {
		userWallet.setCreateTime(DateUtils.getNowDate());
		return userWalletMapper.insertUserWallet(userWallet);
	}

	/**
	 * 修改用户钱包
	 * 
	 * @param userWallet 用户钱包
	 * @return 结果
	 */
	@Override
	public int updateUserWallet(UserWallet userWallet) {
		userWallet.setUpdateTime(DateUtils.getNowDate());
		return userWalletMapper.updateUserWallet(userWallet);
	}

	/**
	 * 删除用户钱包对象
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteUserWalletByIds(String ids) {
		return userWalletMapper.deleteUserWalletByIds(Convert.toStrArray(ids));
	}

	/**
	 * 删除用户钱包信息
	 * 
	 * @param userId 用户钱包ID
	 * @return 结果
	 */
	@Override
	public int deleteUserWalletById(Long userId) {
		return userWalletMapper.deleteUserWalletById(userId);
	}

	@Override
	public int createUserWalletByUserId(Long userId, String password, boolean createAccounts) {
		if (userId == null) {
			return 0;
		}
		UserWallet wallet = selectUserWalletById(userId);
		if (wallet == null) {
			wallet = new UserWallet();
			wallet.setUserId(userId);
			wallet.setSalt(StringUtils.randomNum(6));
			insertUserWallet(wallet);
		}
		if (StringUtils.isNotEmpty(password)) {
			wallet.setSalt(StringUtils.randomNum(6));
			wallet.setPassword(StringUtils.encryptPassword(userId.toString(), password, wallet.getSalt()));
			updateUserWallet(wallet);
		}
		// 创建钱包
		if (createAccounts) {
			walletAccountService.updateUserWalletAccountByUserId(userId);
		}
		return 1;
	}

	@Override
	public CommonResult<?> verifyUserWalletPassword(Long userId, String password) {
		if (userId == null || StringUtils.isEmpty(password)) {
			return CommonResult.fail(MessageUtils.message("UserWalletPasswordServiceImpl.0")); //$NON-NLS-1$
		}
		UserWallet wallet = selectUserWalletById(userId);
		if (wallet == null) {
			return CommonResult.fail(MessageUtils.message("UserWalletPasswordServiceImpl.1")); //$NON-NLS-1$
		}
		if (StringUtils.encryptPassword(userId.toString(), password, wallet.getSalt()).equals(wallet.getPassword())) {
			return CommonResult.success();
		}
		return CommonResult.fail(MessageUtils.message("UserWalletPasswordServiceImpl.2")); //$NON-NLS-1$
	}

	@Override
	public CommonResult<?> changeUserWalletPassword(Long userId, String oldPassword, String newPassword) {
		UserWallet wallet = selectUserWalletById(userId);
		if (wallet != null) {
			CommonResult<?> verified = verifyUserWalletPassword(userId, oldPassword);
			if (!verified.isSuccess()) {
				return verified;
			} else if (StringUtils.isEmpty(newPassword) || newPassword.length() < 6) {
				return CommonResult.fail(MessageUtils.message("UserWalletPasswordServiceImpl.3")); //$NON-NLS-1$
			}
			String salt = StringUtils.randomNum(6);
			wallet.setSalt(salt);
			wallet.setPassword(StringUtils.encryptPassword(userId.toString(), newPassword, salt));
			return CommonResult.ajax(updateUserWallet(wallet));
		} else {
			wallet = new UserWallet();
			wallet.setUserId(userId);
			String salt = StringUtils.randomNum(6);
			wallet.setSalt(salt);
			wallet.setPassword(StringUtils.encryptPassword(userId.toString(), newPassword, salt));
			return CommonResult.ajax(insertUserWallet(wallet));
		}
	}

	@Override
	public int resetUserWalletPassword(Long userId, String password) {
		if (userId == null || StringUtils.isEmpty(password) || password.length() < 6) {
			return 0;
		}
		UserWallet wallet = selectUserWalletById(userId);
		if (wallet != null) {
			String salt = StringUtils.randomNum(6);
			wallet.setSalt(salt);
			wallet.setPassword(StringUtils.encryptPassword(userId.toString(), password, salt));
			return updateUserWallet(wallet);
		} else {
			wallet = new UserWallet();
			wallet.setUserId(userId);
			String salt = StringUtils.randomNum(6);
			wallet.setSalt(salt);
			wallet.setPassword(StringUtils.encryptPassword(userId.toString(), password, salt));
			return insertUserWallet(wallet);
		}
	}
}
