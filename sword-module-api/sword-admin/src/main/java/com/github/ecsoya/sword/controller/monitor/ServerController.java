package com.github.ecsoya.sword.controller.monitor;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.framework.web.domain.Server;

/**
 * The Class ServerController.
 */
@Controller
@RequestMapping("/monitor/server")
public class ServerController extends BaseController {

	/** The prefix. */
	private final String prefix = "monitor/server";

	/**
	 * Server.
	 *
	 * @param mmap the mmap
	 * @return the string
	 * @throws Exception the exception
	 */
	@RequiresPermissions("monitor:server:view")
	@GetMapping()
	public String server(ModelMap mmap) throws Exception {
		final Server server = new Server();
		server.copyTo();
		mmap.put("server", server);
		return prefix + "/server";
	}
}
