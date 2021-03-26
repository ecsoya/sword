package com.soyatec.sword.controller.tool;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.AjaxResult;
import com.soyatec.sword.qrcode.QrcodeUtils;

/**
 * 版本Controller
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-22
 */
@Controller
@RequestMapping("/tool/resource")
public class ResourceController extends BaseController {
	private String prefix = "tool/resource";

	@RequiresPermissions("tool:resource:view")
	@GetMapping()
	public String resource() {
		return prefix + "/resource";
	}

	/**
	 * 导出版本列表
	 */
	@PostMapping("/qrcode")
	@ResponseBody
	public AjaxResult qrcode(String content) {
		try {
			String url = QrcodeUtils.generate(content);
			AjaxResult ajax = AjaxResult.success();
			ajax.put("url", url);
			return ajax;
		} catch (Exception e) {
			return AjaxResult.error(e.getMessage());
		}
	}

}
