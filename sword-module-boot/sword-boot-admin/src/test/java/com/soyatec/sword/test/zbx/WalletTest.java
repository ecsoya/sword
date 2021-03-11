package com.soyatec.sword.test.zbx;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.wallet.domain.Address;
import com.soyatec.sword.wallet.domain.Ticker;
import com.soyatec.sword.wallet.service.IWalletService;

@SpringBootTest(value = "spring.profiles.active=local")
public class WalletTest {
	@Autowired
	private IWalletService walletService;

	@Test
	public void test1() {
		CommonResult<Address> depositAddress = walletService.getDepositAddress("usdt", null);
		System.out.println(depositAddress);
	}

	@Test
	public void test2() {
		CommonResult<Ticker> ticker = walletService.getTicker("kc");
		System.out.println(ticker);
	}
}
