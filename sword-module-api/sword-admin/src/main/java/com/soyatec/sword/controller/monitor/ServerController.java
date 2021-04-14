package com.soyatec.sword.controller.monitor;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.framework.web.domain.Server;

/**
 * 服务器监控
 *
 * @author Jin Liu (angryred@qq.com)
 */
@Controller
@RequestMapping("/monitor/server")
public class ServerController extends BaseController {
	private final String prefix = "monitor/server";

	@RequiresPermissions("monitor:server:view")
	@GetMapping()
	public String server(ModelMap mmap) throws Exception {
		final Server server = new Server();
		server.copyTo();
		mmap.put("server", server);
		return prefix + "/server";
	}
}
