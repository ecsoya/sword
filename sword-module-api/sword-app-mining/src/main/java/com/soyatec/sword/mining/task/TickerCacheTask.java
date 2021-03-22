package com.soyatec.sword.mining.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.mining.service.IMiningSymbolService;
import com.soyatec.sword.wallet.service.IWalletService;

@Configuration
@EnableScheduling
public class TickerCacheTask {

	private static final Logger log = LoggerFactory.getLogger(TickerCacheTask.class);

	@Autowired
	private IWalletService walletService;

	@Autowired
	private IMiningSymbolService symbolService;

	@Scheduled(cron = "0 */5 * * * ?")
	public void cacheKcPrice() {
		log.info("TickerCacheTask at {}", DateUtils.getNowDateStr());
		List<String> symbols = symbolService.selectMiningSymbols();
		symbols.forEach(symbol -> walletService.updateTickerCache(symbol));
	}
}
