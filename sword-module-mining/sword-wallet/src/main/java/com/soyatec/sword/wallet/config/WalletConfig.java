package com.soyatec.sword.wallet.config;

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

import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.common.utils.StringUtils;

@Configuration
@ConfigurationProperties("wallet.ticker")
public class WalletConfig {

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
		Map<Date, BigDecimal> values = histories.get(symbol);
		Set<Entry<Date, BigDecimal>> entrySet = values.entrySet();
		for (Entry<Date, BigDecimal> entry : entrySet) {
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
		Set<Entry<String, String>> entrySet = historyFiles.entrySet();
		for (Entry<String, String> entry : entrySet) {
			try {
				String symbol = entry.getKey();
				String file = entry.getValue();
				Map<Date, BigDecimal> prices = new HashMap<>();
				ClassPathResource resource = new ClassPathResource(file);
				List<String> lines = IOUtils.readLines(resource.getInputStream(), "utf-8");
				for (String line : lines) {
					String[] split = line.split("=");
					if (split.length != 2) {
						continue;
					}

					Date date = DateUtils.parseDate(split[0], "yyyy-MM-dd", "yyyy/MM/dd", "yyyyMMdd");
					if (date == null) {
						continue;
					}
					BigDecimal price = new BigDecimal(split[1].trim());
					if (price != null) {
						prices.put(date, price);
					}
				}
				getHistories().put(symbol, prices);
			} catch (Exception e) {
				continue;
			}
		}
	}

}
