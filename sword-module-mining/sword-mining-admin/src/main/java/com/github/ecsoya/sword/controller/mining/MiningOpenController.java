package com.github.ecsoya.sword.controller.mining;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ecsoya.sword.business.service.IWithdrawalNotifyService;
import com.github.ecsoya.sword.common.annotation.RepeatSubmit;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.token.annotation.TokenAccessLog;

@CrossOrigin(origins = { "*" }, maxAge = 3600)
@RestController
@RequestMapping(value = "/open/v1", produces = "application/json;charset=utf-8")
public class MiningOpenController {
	private static Logger log = LoggerFactory.getLogger(MiningOpenController.class);

	@Autowired
	private IWithdrawalNotifyService notifyService;

	@TokenAccessLog(title = "提币审核", description = "/open/v1/confirm")
	@GetMapping("/confirm")
	@RepeatSubmit
	public CommonResult<?> confirm(String token) {
		log.info("提币审核开放接口调用：" + token);
		return notifyService.confirmOrder(token);
	}
}
