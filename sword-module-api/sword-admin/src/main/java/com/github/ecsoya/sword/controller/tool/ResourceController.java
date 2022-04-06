package com.github.ecsoya.sword.controller.tool;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.qrcode.QrcodeUtils;

/**
 * The Class ResourceController.
 */
@Controller
@RequestMapping("/tool/resource")
public class ResourceController extends BaseController {

	/** The prefix. */
	private final String prefix = "tool/resource";

	/**
	 * Resource.
	 *
	 * @return the string
	 */
	@RequiresPermissions("tool:resource:view")
	@GetMapping()
	public String resource() {
		return prefix + "/resource";
	}

	/**
	 * Qrcode.
	 *
	 * @param content the content
	 * @return the ajax result
	 */
	@PostMapping("/qrcode")
	@ResponseBody
	public AjaxResult qrcode(String content) {
		try {
			final String url = QrcodeUtils.generate(content);
			final AjaxResult ajax = AjaxResult.success();
			ajax.put("url", url);
			return ajax;
		} catch (final Exception e) {
			return AjaxResult.error(e.getMessage());
		}
	}

}
