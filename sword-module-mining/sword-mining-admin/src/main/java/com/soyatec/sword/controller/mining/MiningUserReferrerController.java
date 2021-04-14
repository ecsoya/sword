package com.soyatec.sword.controller.mining;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.soyatec.sword.common.annotation.Log;
import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.AjaxResult;
import com.soyatec.sword.common.core.page.TableDataInfo;
import com.soyatec.sword.common.enums.BusinessType;
import com.soyatec.sword.user.domain.UserReferrerInfo;
import com.soyatec.sword.user.service.IUserReferrerService;

@Controller
@RequestMapping("/mining/user/referrer")
public class MiningUserReferrerController extends BaseController {

	private static final String prefix = "mining/user";

	@Autowired
	private IUserReferrerService userReferrerService;

	@RequiresPermissions("mining:user:referrer:view")
	@GetMapping
	public String list() {
		return prefix + "/referrer";
	}

	@RequiresPermissions("mining:user:referrer:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo table(UserReferrerInfo query) {
		startPage();

		final List<UserReferrerInfo> list = userReferrerService.selectUserReferrerInfoList(query);
		return getDataTable(list);
	}

	@Log(title = "导入外部推荐关系", businessType = BusinessType.IMPORT)
	@RequiresPermissions("mining:user:referrer:import")
	@PostMapping("/importData")
	@ResponseBody
	public AjaxResult importData(MultipartFile file) throws Exception {
		return error("不支持导入");
	}

}
