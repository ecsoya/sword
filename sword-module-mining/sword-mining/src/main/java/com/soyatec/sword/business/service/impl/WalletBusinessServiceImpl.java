package com.soyatec.sword.business.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soyatec.sword.business.service.IWalletBusinessService;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.wallet.domain.UserWalletAccount;
import com.soyatec.sword.wallet.service.IUserWalletAccountService;

@Service
public class WalletBusinessServiceImpl implements IWalletBusinessService {

	private static final Logger log = LoggerFactory.getLogger(IWalletBusinessService.class);

	@Autowired
	private IUserWalletAccountService walletAccountService;

	@Override
	public int updateWalletAddress() {
		log.info("updateWalletAddress");
		List<UserWalletAccount> accounts = walletAccountService.selectUserWalletAccountList(new UserWalletAccount())
				.stream().filter(a -> StringUtils.isEmpty(a.getAddress())).collect(Collectors.toList());
		accounts.forEach(account -> updateWalletAddress(account));
		return 1;
	}

	private int updateWalletAddress(UserWalletAccount account) {
		if (account == null) {
			return 0;
		}
		// 查询用户的钱包，检测充值地址
		walletAccountService.updateUserWalletAccountAddress(account.getUserId(), account.getSymbol());
		return 1;
	}

}
