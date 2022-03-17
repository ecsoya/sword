package com.github.ecsoya.sword.mining.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.mining.domain.MiningLevel;
import com.github.ecsoya.sword.mining.service.IMiningLevelService;
import com.github.ecsoya.sword.utils.SwordUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 钱包地址接口
 *
 * @author ecsoya
 */
@RestController
@RequestMapping("/user/level")
@Api(tags = { "用户级别" }, description = "查询")
public class UserLevelController extends BaseController {

	@Autowired
	private IMiningLevelService miningLevelService;

	/**
	 * 查询所有钱包地址
	 */
	@ApiOperation("查询用户级别")
	@GetMapping
	public CommonResult<MiningLevel> info() {
		final Long userId = SwordUtils.getUserId();
		return CommonResult.build(miningLevelService.selectMiningLevelByUserId(userId));
	}

}
