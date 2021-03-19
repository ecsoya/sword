package com.soyatec.sword.mining.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.core.page.TableDataInfo;
import com.soyatec.sword.mining.utils.SwordMiningUtils;
import com.soyatec.sword.user.domain.UserReferrerTeam;
import com.soyatec.sword.user.service.IUserReferrerTeamService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 用户推荐码接口
 * 
 * @author ecsoya
 */
@RestController
@RequestMapping("/user/referrer/team")
@Api(tags = { "推广体系" }, description = "直推团队")
public class UserReferrerTeamController extends BaseController {

	@Autowired
	private IUserReferrerTeamService userReferrerTeamService;

	@ApiOperation(value = "获取直推团队人数", notes = "直推团队人数：\n1、total - 全部 \n2、today - 今日 \n3、yesterday - 昨日 \n4、单位为 人")
	@GetMapping
	public CommonResult<UserReferrerTeam> referral() {
		Long userId = SwordMiningUtils.getUserId();
		return CommonResult.build(userReferrerTeamService.selectUserReferrerTeamByUserId(userId));
	}

	@ApiOperation("获取直推团队列表（支持分页）")
	@GetMapping("/members")
	public TableDataInfo referralMembers(@ApiParam("当前页数，第几页") Integer pageNum, @ApiParam("每页显示多少条") Integer pageSize) {
		startPage();
		Long userId = SwordMiningUtils.getUserId();
		return getDataTable(userReferrerTeamService.selectUserReferrerTeamListByUserId(userId));
	}

}
