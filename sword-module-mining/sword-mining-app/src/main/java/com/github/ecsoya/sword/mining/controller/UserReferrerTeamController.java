package com.github.ecsoya.sword.mining.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.core.page.TableDataInfo;
import com.github.ecsoya.sword.user.domain.UserReferrerTeamCount;
import com.github.ecsoya.sword.user.service.IUserReferrerTeamCountService;
import com.github.ecsoya.sword.utils.SwordUtils;

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
@Api(tags = { "推广体系" }, description = "支持仅有直推模式的推广体系")
public class UserReferrerTeamController extends BaseController {

	@Autowired
	private IUserReferrerTeamCountService referrerTeamCountService;

	@ApiOperation(value = "获取直推团队人数", notes = "直推团队人数：\n1、total - 全部 \n2、today - 今日 \n3、yesterday - 昨日 \n4、单位为 人")
	@GetMapping()
	public CommonResult<UserReferrerTeamCount> team() {
		final Long userId = SwordUtils.getUserId();
		return CommonResult.build(referrerTeamCountService.selectUserReferrerTeamCountByUserId(userId));
	}

	@ApiOperation(value = "获取直推团队列表（支持分页）", notes = "此处只取直推团队")
	@GetMapping("/list")
	public TableDataInfo list(@ApiParam("当前页数，第几页") Integer pageNum, @ApiParam("每页显示多少条") Integer pageSize) {
		startPage("create_time desc");
		final Long userId = SwordUtils.getUserId();
		return getDataTable(referrerTeamCountService.selectUserReferrerTeamListByUserId(userId));
	}

	@ApiOperation(value = "获取伞下团队列表（不支持分页）", notes = "此处获取全部伞下团队")
	@GetMapping("/umbrellas")
	public TableDataInfo umbrellas() {
		startPage("create_time desc");
		final Long userId = SwordUtils.getUserId();
		return getDataTable(referrerTeamCountService.selectUserUmbrellaTeamListByUserId(userId));
	}

}
