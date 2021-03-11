package com.soyatec.sword.controller.tool;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.soyatec.sword.common.core.controller.BaseController;

/**
 * swagger 接口
 * 
 * @author Jin Liu (angryred@qq.com)
 */
@Controller
@RequestMapping("/tool/swagger")
public class SwaggerController extends BaseController {
	@GetMapping()
	public String index() {
		return redirect("/swagger-ui.html");
	}
}
