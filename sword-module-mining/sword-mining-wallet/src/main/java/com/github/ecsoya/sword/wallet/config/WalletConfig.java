package com.github.ecsoya.sword.wallet.config;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;

@Configuration
@ConfigurationProperties("wallet.ticker")
public class WalletConfig {

	private String priceUrl = "https://api.gateio.ws/api/v4/spot/tickers";

	private String market = "currency_pair";

	private Map<String, String> historyFiles;

	private Map<String, Map<Date, BigDecimal>> histories;

	public Map<String, String> getHistoryFiles() {
		return historyFiles;
	}

	public void setHistoryFiles(Map<String, String> historyFiles) {
		this.historyFiles = historyFiles;
		if (historyFiles != null && !historyFiles.isEmpty()) {
			buildHistories();
		}
	}

	public Map<String, Map<Date, BigDecimal>> getHistories() {
		if (histories == null) {
			histories = new HashMap<String, Map<Date, BigDecimal>>();
		}
		return histories;
	}

	public void setHistories(Map<String, Map<Date, BigDecimal>> histories) {
		this.histories = histories;
	}

	public BigDecimal getPrice(String symbol, Date date) {
		if (StringUtils.isEmpty(symbol) || date == null || histories == null || histories.isEmpty()) {
			return null;
		}
		final Map<Date, BigDecimal> values = histories.get(symbol);
		if (values == null) {
			return null;
		}
		final Set<Entry<Date, BigDecimal>> entrySet = values.entrySet();
		for (final Entry<Date, BigDecimal> entry : entrySet) {
			if (DateUtils.dayEquals(date, entry.getKey())) {
				return entry.getValue();
			}
		}
		return null;
	}

	private void buildHistories() {
		if (historyFiles == null || historyFiles.isEmpty()) {
			return;
		}
		final Set<Entry<String, String>> entrySet = historyFiles.entrySet();
		for (final Entry<String, String> entry : entrySet) {
			try {
				final String symbol = entry.getKey();
				final String file = entry.getValue();
				final Map<Date, BigDecimal> prices = new HashMap<>();
				final ClassPathResource resource = new ClassPathResource(file);
				final List<String> lines = IOUtils.readLines(resource.getInputStream(), "utf-8");
				for (final String line : lines) {
					final String[] split = line.split("=");
					if (split.length != 2) {
						continue;
					}

					final Date date = org.apache.commons.lang3.time.DateUtils.parseDate(split[0], "yyyy-MM-dd",
							"yyyy/MM/dd", "yyyyMMdd");
					if (date == null) {
						continue;
					}
					final BigDecimal price = new BigDecimal(split[1].trim());
					if (price != null) {
						prices.put(date, price);
					}
				}
				getHistories().put(symbol, prices);
			} catch (final Exception e) {
				continue;
			}
		}
	}

	public String getPriceUrl() {
		return priceUrl;
	}

	public void setPriceUrl(String priceUrl) {
		this.priceUrl = priceUrl;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

}
