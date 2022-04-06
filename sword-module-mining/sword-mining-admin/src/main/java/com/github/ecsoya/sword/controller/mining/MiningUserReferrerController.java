package com.github.ecsoya.sword.controller.mining;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.ecsoya.sword.common.annotation.Log;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.page.TableDataInfo;
import com.github.ecsoya.sword.common.enums.BusinessType;
import com.github.ecsoya.sword.user.domain.UserReferrerInfo;
import com.github.ecsoya.sword.user.service.IUserReferrerService;

/**
 * The Class MiningUserReferrerController.
 */
@Controller
@RequestMapping("/mining/user/referrer")
public class MiningUserReferrerController extends BaseController {

	/** The Constant prefix. */
	private static final String prefix = "mining/user";

	/** The user referrer service. */
	@Autowired
	private IUserReferrerService userReferrerService;

	/**
	 * List.
	 *
	 * @return the string
	 */
	@RequiresPermissions("mining:user:referrer:view")
	@GetMapping
	public String list() {
		return prefix + "/referrer";
	}

	/**
	 * Table.
	 *
	 * @param query the query
	 * @return the table data info
	 */
	@RequiresPermissions("mining:user:referrer:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo table(UserReferrerInfo query) {
		startPage();

		final List<UserReferrerInfo> list = userReferrerService.selectUserReferrerInfoList(query);
		return getDataTable(list);
	}

	/**
	 * Import data.
	 *
	 * @param file the file
	 * @return the ajax result
	 * @throws Exception the exception
	 */
	@Log(title = "导入外部推荐关系", businessType = BusinessType.IMPORT)
	@RequiresPermissions("mining:user:referrer:import")
	@PostMapping("/importData")
	@ResponseBody
	public AjaxResult importData(MultipartFile file) throws Exception {
		return error("不支持导入");
	}

}
