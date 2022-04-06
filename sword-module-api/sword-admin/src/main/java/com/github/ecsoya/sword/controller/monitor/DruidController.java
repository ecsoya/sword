package com.github.ecsoya.sword.controller.monitor;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.ecsoya.sword.common.core.controller.BaseController;

/**
 * The Class DruidController.
 */
@Controller
@RequestMapping("/monitor/data")
public class DruidController extends BaseController {

	/** The prefix. */
	private final String prefix = "/druid";

	/**
	 * Index.
	 *
	 * @return the string
	 */
	@RequiresPermissions("monitor:data:view")
	@GetMapping()
	public String index() {
		return redirect(prefix + "/index");
	}
}
