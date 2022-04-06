package com.github.ecsoya.sword.business.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.ecsoya.sword.business.service.IWalletAddressService;

/**
 * The Class WalletTask.
 */
@Component("walletTask")
public class WalletTask {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(WalletTask.class);

	/** The wallet address service. */
	@Autowired
	private IWalletAddressService walletAddressService;

	/**
	 * Update wallet.
	 */
	public void updateWallet() {
		log.info("WalletTask.updateWallet()");
		walletAddressService.updateWalletAddress();
	}
}
