package com.github.ecsoya.sword.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.framework.shiro.util.ShiroUtils;
import com.github.ecsoya.sword.utils.SwordUtils;
import com.github.ecsoya.sword.version.domain.SwordVersion;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * The Class VersionController.
 */
@RestController
@RequestMapping("/open/version")
@Api(tags = { "版本" }, description = "版本验证")
public class VersionController extends BaseController {

	/**
	 * Latest version.
	 *
	 * @param request the request
	 * @param current the current
	 * @return the common result
	 */
	@ApiOperation("APP版本查询")
	@GetMapping("/latest")
	public CommonResult<SwordVersion> latestVersion(HttpServletRequest request, Long current) {
		final SwordVersion version = SwordUtils.getVersion();
		if (version != null && current != null && current.longValue() < version.getVersion()) {
			ShiroUtils.logout();
		}
		return CommonResult.build(version);
	}

}
