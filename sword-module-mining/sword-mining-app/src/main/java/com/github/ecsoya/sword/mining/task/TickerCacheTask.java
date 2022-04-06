package com.github.ecsoya.sword.mining.task;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.constants.IMiningConstants;
import com.github.ecsoya.sword.exceptions.LockableException;
import com.github.ecsoya.sword.mining.service.IMiningSymbolService;
import com.github.ecsoya.sword.service.ILockService;
import com.github.ecsoya.sword.wallet.service.IWalletService;

/**
 * The Class TickerCacheTask.
 */
@Configuration
@EnableScheduling
public class TickerCacheTask {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(TickerCacheTask.class);

	/** The Constant LOCK_NAME. */
	private static final String LOCK_NAME = "sword.tickerCacheLock";

	/** The wallet service. */
	@Autowired
	private IWalletService walletService;

	/** The symbol service. */
	@Autowired
	private IMiningSymbolService symbolService;

	/** The lock service. */
	@Autowired
	private ILockService lockService;

	/**
	 * Cache ticker.
	 */
	@Scheduled(cron = "0 */5 * * * ?")
	public void cacheTicker() {
		log.info("TickerCacheTask at {}", DateUtils.getNowDateStr());
		boolean locked = false;
		try {
			locked = lockService.tryLock(LOCK_NAME);
			final List<String> symbols = symbolService.selectMiningSymbols(true).stream()
					.filter(s -> !StringUtils.equals(IMiningConstants.SYMBOL_USDT, s)).collect(Collectors.toList());
			symbols.forEach(symbol -> walletService.updateTickerCache(symbol));
		} catch (final LockableException e) {
			log.warn("TickerCacheTask missing");
		} finally {
			if (locked) {
				lockService.releaseLock(LOCK_NAME);
			}
		}

	}
}
