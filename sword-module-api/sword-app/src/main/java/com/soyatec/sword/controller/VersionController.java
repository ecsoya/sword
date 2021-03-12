package com.soyatec.sword.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.framework.shiro.util.ShiroUtils;
import com.soyatec.sword.utils.SwordUtils;
import com.soyatec.sword.version.domain.SwordVersion;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/open/version")
@Api(tags = { "版本" }, description = "版本验证")
public class VersionController extends BaseController {

	@ApiOperation("APP版本查询")
	@GetMapping("/latest")
	public CommonResult<SwordVersion> latestVersion(HttpServletRequest request, Long current) {
		SwordVersion version = SwordUtils.getVersion();
		if (version != null && current != null && current.longValue() < version.getVersion()) {
			ShiroUtils.logout();
		}
		return CommonResult.build(version);
	}

}
