package com.soyatec.sword.mining.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.wallet.domain.Ticker;
import com.soyatec.sword.wallet.service.IWalletService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/price")
@Api(tags = { "实时价格" }, description = "实时价格、历史价格")
public class PriceController extends BaseController {

	@Autowired
	private IWalletService walletService;

	@GetMapping("/latest")
	@ApiOperation("最新价格")
	public CommonResult<BigDecimal> latest(String symbol) {
		return CommonResult.build(walletService.getPrice(symbol));
	}

	@GetMapping("/ticker")
	@ApiOperation("最新价格详情")
	public CommonResult<Ticker> ticker(String symbol) {
		return walletService.getTicker(symbol);
	}

	/**
	 * 查询KC实时价格
	 */
	@GetMapping("/price/histogram")
	@ApiOperation("历史价格")
	public CommonResult<Map<String, Object>> price7days(String symbol) {
		if (StringUtils.isEmpty(symbol)) {
			return CommonResult.fail("symbol为空");
		}
		Map<String, Object> data = new HashMap<String, Object>();
		Date end = DateUtils.getNowDate();
		Date start = DateUtils.addDays(end, -6);
		List<Date> dates = DateUtils.getDates(start, end);
		List<Ticker> list = dates.parallelStream().map(time -> walletService.getTickerFromHistory(symbol, time))
				.collect(Collectors.toList());
		data.put("dates", dates.parallelStream().map(t -> DateUtils.parseDateToStr("yyyy/MM/dd HH:mm:ss", t))
				.collect(Collectors.toList()));
		data.put("prices", list.parallelStream().map(t -> t.getPrice().toPlainString()).collect(Collectors.toList()));
		return CommonResult.build(data);
	}
}
