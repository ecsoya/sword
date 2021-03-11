package com.soyatec.sword.controller.tool;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.soyatec.sword.common.core.controller.BaseController;

/**
 * build 表单构建
 * 
 * @author Jin Liu (angryred@qq.com)
 */
@Controller
@RequestMapping("/tool/build")
public class BuildController extends BaseController {
	private String prefix = "tool/build";

	@GetMapping()
	public String build() {
		return prefix + "/build";
	}
}
