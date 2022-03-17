package com.github.ecsoya.sword.controller.mining;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.ecsoya.sword.common.annotation.Log;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.page.SelectDataInfo;
import com.github.ecsoya.sword.common.core.page.TableDataInfo;
import com.github.ecsoya.sword.common.enums.BusinessType;
import com.github.ecsoya.sword.common.utils.poi.ExcelUtil;
import com.github.ecsoya.sword.user.domain.Country;
import com.github.ecsoya.sword.user.domain.UserCertificate;
import com.github.ecsoya.sword.user.domain.UserProfile;
import com.github.ecsoya.sword.user.service.IUserCertificateService;
import com.github.ecsoya.sword.user.service.IUserProfileService;
import com.github.pagehelper.page.PageMethod;

/**
 * 用户实名Controller
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-23
 */
@Controller
@RequestMapping("/mining/certificate")
public class MiningCertificateController extends BaseController {
	private final String prefix = "mining/certificate";

	@Autowired
	private IUserCertificateService userCertificateService;

	@Autowired
	private IUserProfileService userService;

	@RequiresPermissions("mining:certificate:view")
	@GetMapping()
	public String certificate(ModelMap mmap) {
		mmap.put("countries", Country.list());
		return prefix + "/certificate";
	}

	/**
	 * 查询用户实名列表
	 */
	@RequiresPermissions("mining:certificate:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(UserCertificate userCertificate) {
		startPage();
		final List<UserCertificate> list = userCertificateService.selectUserCertificateList(userCertificate);
		return getDataTable(list);
	}

	/**
	 * 导出用户实名列表
	 */
	@RequiresPermissions("mining:certificate:export")
	@Log(title = "用户实名", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(UserCertificate userCertificate) {
		final List<UserCertificate> list = userCertificateService.selectUserCertificateList(userCertificate);
		final ExcelUtil<UserCertificate> util = new ExcelUtil<UserCertificate>(UserCertificate.class);
		return util.exportExcel(list, "certificate");
	}

	/**
	 * 新增用户实名
	 */
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 新增保存用户实名
	 */
	@RequiresPermissions("mining:certificate:add")
	@Log(title = "用户实名", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(UserCertificate userCertificate) {
		return toAjax(userCertificateService.insertUserCertificate(userCertificate));
	}

	/**
	 * 修改用户实名
	 */
	@GetMapping("/edit/{userId}")
	public String edit(@PathVariable("userId") Long userId, ModelMap mmap) {
		final UserCertificate userCertificate = userCertificateService.selectUserCertificateById(userId);
		mmap.put("userCertificate", userCertificate);
		return prefix + "/edit";
	}

	/**
	 * 修改保存用户实名
	 */
	@RequiresPermissions("mining:certificate:edit")
	@Log(title = "用户实名", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(UserCertificate userCertificate) {
		return toAjax(userCertificateService.updateUserCertificate(userCertificate));
	}

	/**
	 * 删除用户实名
	 */
	@RequiresPermissions("mining:certificate:remove")
	@Log(title = "用户实名", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(userCertificateService.deleteUserCertificateByIds(ids));
	}

	/**
	 * 查询二级分类，Select2组件专用
	 */
	@GetMapping("users")
	@ResponseBody
	public SelectDataInfo listCategories(String search, Integer page) {
		PageMethod.startPage(page, 10);
		final List<UserProfile> list = userService.fuzzySearchUserList(search);
		final SelectDataInfo build = SelectDataInfo.build(list, page, "userId", "loginName", UserProfile.class);
		return build;
	}

	@RequiresPermissions("mining:certificate:edit")
	@Log(title = "用户实名审核通过", businessType = BusinessType.UPDATE)
	@PostMapping("/pass")
	@ResponseBody
	public AjaxResult pass(Long userId) {
		return toAjax(
				userCertificateService.updateUserCertificateStatus(userId, UserCertificate.STATUS_SUCCESS, "审核通过"));
	}

	@RequiresPermissions("mining:certificate:edit")
	@Log(title = "用户实名审核拒绝", businessType = BusinessType.UPDATE)
	@PostMapping("/deny")
	@ResponseBody
	public AjaxResult deny(Long userId, String remark) {
		return toAjax(
				userCertificateService.updateUserCertificateStatus(userId, UserCertificate.STATUS_FAILURE, remark));
	}

}
