package com.soyatec.sword.controller.admin;

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

import com.soyatec.sword.common.annotation.Log;
import com.soyatec.sword.common.config.GlobalConfig;
import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.AjaxResult;
import com.soyatec.sword.common.core.page.TableDataInfo;
import com.soyatec.sword.common.enums.BusinessType;
import com.soyatec.sword.common.utils.async.AsyncManager;
import com.soyatec.sword.framework.shiro.util.ShiroUtils;
import com.soyatec.sword.message.IMessageType;
import com.soyatec.sword.message.MessageHelper;
import com.soyatec.sword.system.domain.SysNotice;
import com.soyatec.sword.system.service.ISysNoticeService;

@Controller
@RequestMapping("/admin/inform")
public class AdminInformController extends BaseController {
	private final String prefix = "admin/inform";

	@Autowired
	private ISysNoticeService noticeService;

	@RequiresPermissions("admin:inform:view")
	@GetMapping()
	public String inform() {
		return prefix + "/inform";
	}

	/**
	 * 查询消息列表
	 */
	@RequiresPermissions("admin:inform:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SysNotice inform) {
		inform.setNoticeType(SysNotice.NOTICE_TYPE_INFORM);
		startPage();
		final List<SysNotice> list = noticeService.selectNoticeList(inform);
		return getDataTable(list);
	}

	/**
	 * 新增消息
	 */
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 新增保存消息
	 */
	@RequiresPermissions("admin:inform:add")
	@Log(title = "系统消息", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(SysNotice inform) {
		inform.setNoticeType(SysNotice.NOTICE_TYPE_INFORM);
		inform.setCreateBy(ShiroUtils.getLoginName());
		int rows = noticeService.insertNotice(inform);
		if (rows > 0 && GlobalConfig.isMessageEnabled()) {
			AsyncManager.me().execute(new Runnable() {

				@Override
				public void run() {
					MessageHelper.send(inform.getNoticeId(), IMessageType.TYPE_INFORM);
				}
			});
		}
		return toAjax(rows);
	}

	/**
	 * 修改消息
	 */
	@GetMapping("/edit/{noticeId}")
	public String edit(@PathVariable("noticeId") Long noticeId, ModelMap mmap) {
		mmap.put("inform", noticeService.selectNoticeById(noticeId));
		return prefix + "/edit";
	}

	/**
	 * 修改保存消息
	 */
	@RequiresPermissions("admin:inform:edit")
	@Log(title = "系统消息", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(SysNotice inform) {
		inform.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(noticeService.updateNotice(inform));
	}

	/**
	 * 删除消息
	 */
	@RequiresPermissions("admin:inform:remove")
	@Log(title = "系统消息", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(noticeService.deleteNoticeByIds(ids));
	}
}
