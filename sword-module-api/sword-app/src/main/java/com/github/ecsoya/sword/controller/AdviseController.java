package com.github.ecsoya.sword.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ecsoya.sword.article.domain.UserAdvise;
import com.github.ecsoya.sword.article.service.IUserAdviseService;
import com.github.ecsoya.sword.common.annotation.RepeatSubmit;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.utils.SwordUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * The Class AdviseController.
 */
@RestController
@RequestMapping("/user/advise")
@Api(tags = { "反馈" }, description = "用户反馈")
public class AdviseController extends BaseController {

	/** The advise service. */
	@Autowired
	private IUserAdviseService adviseService;

	/**
	 * List.
	 *
	 * @return the common result
	 */
	@ApiOperation("查询用户的所有的反馈")
	@GetMapping("/list")
	public CommonResult<List<UserAdvise>> list() {
		final Long userId = SwordUtils.getUserId();
		return CommonResult.build(adviseService.selectUserAdviseListByUserId(userId));
	}

	/**
	 * Adds the.
	 *
	 * @param title       the title
	 * @param description the description
	 * @return the common result
	 */
	@ApiOperation("添加用户反馈")
	@PostMapping("/add")
	public CommonResult<?> add(String title, String description) {
		final UserAdvise advise = new UserAdvise();
		advise.setTitle(title);
		advise.setDescription(description);
		advise.setUserId(SwordUtils.getUserId());
		advise.setType(UserAdvise.TYPE_FEEDBACK);
		final int rows = adviseService.insertUserAdvise(advise);
		if (rows == -1) {
			return CommonResult.fail("标题不能为空");
		} else if (rows == -2) {
			return CommonResult.fail("描述不能为空");
		}
		return CommonResult.ajax(rows);
	}

	/**
	 * Reply.
	 *
	 * @param parentId    the parent id
	 * @param description the description
	 * @return the common result
	 */
	@ApiOperation("回复用户反馈")
	@PostMapping("/reply")
	@RepeatSubmit
	public CommonResult<?> reply(Long parentId, String description) {
		final UserAdvise advise = new UserAdvise();
		advise.setParentId(parentId);
		advise.setDescription(description);
		advise.setUserId(SwordUtils.getUserId());
		advise.setType(UserAdvise.TYPE_REPLY);
		final int rows = adviseService.insertUserAdvise(advise);
		if (rows == -2) {
			return CommonResult.fail("描述不能为空");
		}
		return CommonResult.ajax(rows);
	}
}
