package com.soyatec.sword.business.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soyatec.sword.business.service.IWalletAddressService;

@Component("walletTask")
public class WalletTask {

	private static final Logger log = LoggerFactory.getLogger(WalletTask.class);

	@Autowired
	private IWalletAddressService walletAddressService;

	public void updateWallet() {
		log.info("WalletTask.updateWallet()");
		walletAddressService.updateWalletAddress();
	}
}
