package com.github.ecsoya.sword.business.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.business.service.IWalletAddressService;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.constants.IMiningConstants;
import com.github.ecsoya.sword.mining.domain.MiningSymbol;
import com.github.ecsoya.sword.mining.service.IMiningSymbolService;
import com.github.ecsoya.sword.wallet.domain.UserWalletAccount;
import com.github.ecsoya.sword.wallet.service.IUserWalletAccountService;
import com.google.common.base.Objects;

/**
 * The Class WalletAddressServiceImpl.
 */
@Service
public class WalletAddressServiceImpl implements IWalletAddressService {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(IWalletAddressService.class);

	/** The wallet account service. */
	@Autowired
	private IUserWalletAccountService walletAccountService;

	/** The symbol service. */
	@Autowired
	private IMiningSymbolService symbolService;

	/**
	 * Update wallet address.
	 *
	 * @return the int
	 */
	@Override
	public int updateWalletAddress() {
		log.info("updateWalletAddress");
		final List<UserWalletAccount> accounts = walletAccountService
				.selectUserWalletAccountList(new UserWalletAccount()).stream()
				.filter(a -> StringUtils.isEmpty(a.getAddress())).collect(Collectors.toList());
		List<MiningSymbol> symbolList = symbolService.selectMiningSymbolList(new MiningSymbol());
		accounts.forEach(account -> updateWalletAddress(account, symbolList));
		return 1;
	}

	/**
	 * Update wallet address.
	 *
	 * @param account    the account
	 * @param symbolList the symbol list
	 * @return the int
	 */
	private int updateWalletAddress(UserWalletAccount account, List<MiningSymbol> symbolList) {
		if (account == null) {
			return 0;
		}
		// 查询用户的钱包，检测充值地址
		Long userId = account.getUserId();
		String symbol = account.getSymbol();
		MiningSymbol ms = symbolList.stream().filter(s -> Objects.equal(s.getSymbol(), symbol)).findFirst()
				.orElse(null);
		String chain = IMiningConstants.CHAIN_DEFAULT;
		if (ms != null && StringUtils.isNotEmpty(ms.getChain())) {
			chain = ms.getChain();
		}
		walletAccountService.updateUserWalletAccountAddress(userId, symbol, chain);
		return 1;
	}

}
