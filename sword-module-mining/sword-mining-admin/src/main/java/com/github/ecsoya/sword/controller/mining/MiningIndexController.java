package com.github.ecsoya.sword.controller.mining;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.reflect.ReflectUtils;
import com.github.ecsoya.sword.mining.service.IMiningUserService;
import com.github.ecsoya.sword.report.domain.DateCount;
import com.github.ecsoya.sword.wallet.domain.Ticker;
import com.github.ecsoya.sword.wallet.service.IWalletService;

@Controller
@RequestMapping("/mining/index")
public class MiningIndexController extends BaseController {

	@Autowired
	private IMiningUserService miningUserService;

	@Autowired
	private IWalletService walletService;

	/**
	 * 查询KC实时价格
	 */
	@GetMapping("/price/histogram")
	@ResponseBody
	public AjaxResult price7days(String symbol) {
		if (StringUtils.isEmpty(symbol)) {
			return error("symbol为空");
		}
		final AjaxResult map = AjaxResult.success();
		final Date end = DateUtils.getNowDate();
		final Date start = org.apache.commons.lang3.time.DateUtils.addDays(end, -6);
		final List<Date> dates = DateUtils.getDates(start, end);
		final List<Ticker> list = dates.parallelStream().map(time -> walletService.getTickerFromHistory(symbol, time))
				.collect(Collectors.toList());
		map.put("dates", dates.parallelStream().map(t -> DateUtils.parseDateToStr("yyyy/MM/dd", t))
				.collect(Collectors.toList()));
		map.put("prices", list.parallelStream().map(t -> t.getPrice().toPlainString()).collect(Collectors.toList()));
		return map;
	}

	@GetMapping("/price")
	@ResponseBody
	public AjaxResult price(String symbol) {
		if (StringUtils.isEmpty(symbol)) {
			return AjaxResult.error("Symbol");
		}
		final AjaxResult samples = AjaxResult.success();
		final CommonResult<Ticker> ticker = walletService.getTicker(symbol);
		if (ticker.isSuccess(true)) {
			final Ticker data = ticker.getData();
			if (data != null) {
				final List<Field> fields = ReflectUtils.getAllFields(Ticker.class);
				for (final Field field : fields) {
					field.setAccessible(true);
					final String name = field.getName();
					try {
						final Object value = field.get(data);
						if (value == null) {
							continue;
						}
						if ("rate".equals(name) && value instanceof BigDecimal) {
							final BigDecimal rate = (BigDecimal) value;
							if (rate.doubleValue() > 0) {
								samples.put("price-rate",
										"<div class='text-navy'> +" + rate.toPlainString() + "%</div>");
							} else {
								samples.put("price-rate",
										"<div class='text-danger'> " + rate.toPlainString() + "%</div>");
							}
						} else {
							samples.put("price-" + name, value);
						}
					} catch (final Exception e) {
						continue;
					}
				}
			}
		}

		samples.put("date", DateUtils.getNowDateStr());
		return samples;
	}

	@GetMapping("/regusers")
	@ResponseBody
	public AjaxResult regusers() {
		final AjaxResult map = AjaxResult.success();
		final List<DateCount> values = miningUserService.selectRegisterUserCountsByDate();
		map.put("dates", values.stream().map(p -> DateUtils.parseDateToStr("yy/MM/dd", p.getDate()))
				.collect(Collectors.toList()));
		map.put("counts", values.stream().map(p -> p.getCount()).collect(Collectors.toList()));
		return map;
	}
}
