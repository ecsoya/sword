package com.github.ecsoya.sword.controller.admin;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.ecsoya.sword.common.annotation.Log;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.page.TableDataInfo;
import com.github.ecsoya.sword.common.enums.BusinessType;
import com.github.ecsoya.sword.common.utils.poi.ExcelUtil;
import com.github.ecsoya.sword.token.domain.TokenSecret;
import com.github.ecsoya.sword.token.service.ITokenSecretService;

/**
 * The Class AdminTokenController.
 */
@Controller
@RequestMapping("/admin/token")
public class AdminTokenController extends BaseController {

	/** The prefix. */
	private final String prefix = "admin/token";

	/** The token secret service. */
	@Autowired
	private ITokenSecretService tokenSecretService;

	/**
	 * Token.
	 *
	 * @return the string
	 */
	@RequiresPermissions("admin:token:view")
	@GetMapping()
	public String token() {
		return prefix + "/token";
	}

	/**
	 * List.
	 *
	 * @param tokenSecret the token secret
	 * @return the table data info
	 */
	@RequiresPermissions("admin:token:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(TokenSecret tokenSecret) {
		startPage();
		final List<TokenSecret> list = tokenSecretService.selectTokenSecretList(tokenSecret);
		return getDataTable(list);
	}

	/**
	 * Export.
	 *
	 * @param tokenSecret the token secret
	 * @return the ajax result
	 */
	@RequiresPermissions("admin:token:export")
	@Log(title = "开放接口API", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(TokenSecret tokenSecret) {
		final List<TokenSecret> list = tokenSecretService.selectTokenSecretList(tokenSecret);
		final ExcelUtil<TokenSecret> util = new ExcelUtil<TokenSecret>(TokenSecret.class);
		return util.exportExcel(list, "token");
	}

	/**
	 * Adds the save.
	 *
	 * @return the ajax result
	 */
	@RequiresPermissions("admin:token:add")
	@Log(title = "开放接口API", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave() {
		return toAjax(tokenSecretService.generateTokenSecret());
	}

	/**
	 * Removes the.
	 *
	 * @param ids the ids
	 * @return the ajax result
	 */
	@RequiresPermissions("admin:token:remove")
	@Log(title = "开放接口API", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(tokenSecretService.deleteTokenSecretByIds(ids));
	}
}
