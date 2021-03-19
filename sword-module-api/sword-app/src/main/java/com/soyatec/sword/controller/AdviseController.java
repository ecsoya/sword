package com.soyatec.sword.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soyatec.sword.article.domain.UserAdvise;
import com.soyatec.sword.article.service.IUserAdviseService;
import com.soyatec.sword.common.annotation.RepeatSubmit;
import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.utils.SwordUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user/advise")
@Api(tags = { "反馈" }, description = "用户反馈")
public class AdviseController extends BaseController {

	@Autowired
	private IUserAdviseService adviseService;

	/**
	 * 查询用户的所有的反馈
	 */
	@ApiOperation("查询用户的所有的反馈")
	@GetMapping("/list")
	public CommonResult<List<UserAdvise>> list() {
		Long userId = SwordUtils.getUserId();
		return CommonResult.build(adviseService.selectUserAdviseListByUserId(userId));
	}

	@ApiOperation("添加用户反馈")
	@PostMapping("/add")
	public CommonResult<?> add(String title, String description) {
		UserAdvise advise = new UserAdvise();
		advise.setTitle(title);
		advise.setDescription(description);
		advise.setUserId(SwordUtils.getUserId());
		advise.setType(UserAdvise.TYPE_FEEDBACK);
		int rows = adviseService.insertUserAdvise(advise);
		if (rows == -1) {
			return CommonResult.fail("标题不能为空");
		} else if (rows == -2) {
			return CommonResult.fail("描述不能为空");
		}
		return CommonResult.ajax(rows);
	}

	@ApiOperation("回复用户反馈")
	@PostMapping("/reply")
	@RepeatSubmit
	public CommonResult<?> reply(Long parentId, String description) {
		UserAdvise advise = new UserAdvise();
		advise.setParentId(parentId);
		advise.setDescription(description);
		advise.setUserId(SwordUtils.getUserId());
		advise.setType(UserAdvise.TYPE_REPLY);
		int rows = adviseService.insertUserAdvise(advise);
		if (rows == -2) {
			return CommonResult.fail("描述不能为空");
		}
		return CommonResult.ajax(rows);
	}
}
