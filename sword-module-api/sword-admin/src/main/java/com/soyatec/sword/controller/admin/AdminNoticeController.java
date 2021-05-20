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
@RequestMapping("/admin/notice")
public class AdminNoticeController extends BaseController {
	private final String prefix = "admin/notice";

	@Autowired
	private ISysNoticeService noticeService;

	@RequiresPermissions("admin:notice:view")
	@GetMapping()
	public String notice() {
		return prefix + "/notice";
	}

	/**
	 * 查询公告列表
	 */
	@RequiresPermissions("admin:notice:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SysNotice notice) {
		notice.setNoticeType(SysNotice.NOTICE_TYPE_NOTICE);
		startPage();
		final List<SysNotice> list = noticeService.selectNoticeList(notice);
		return getDataTable(list);
	}

	/**
	 * 新增公告
	 */
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 新增保存公告
	 */
	@RequiresPermissions("admin:notice:add")
	@Log(title = "添加通知公告", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(SysNotice notice) {
		notice.setNoticeType(SysNotice.NOTICE_TYPE_NOTICE);
		notice.setCreateBy(ShiroUtils.getLoginName());
		int rows = noticeService.insertNotice(notice);
		if (rows > 0 && GlobalConfig.isMessageEnabled()) {
			AsyncManager.me().execute(new Runnable() {

				@Override
				public void run() {
					MessageHelper.send(notice.getNoticeId(), IMessageType.TYPE_NOTICE);
				}
			});
		}
		return toAjax(rows);
	}

	/**
	 * 修改公告
	 */
	@GetMapping("/edit/{noticeId}")
	public String edit(@PathVariable("noticeId") Long noticeId, ModelMap mmap) {
		mmap.put("notice", noticeService.selectNoticeById(noticeId));
		return prefix + "/edit";
	}

	/**
	 * 修改保存公告
	 */
	@RequiresPermissions("admin:notice:edit")
	@Log(title = "更新通知公告", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(SysNotice notice) {
		notice.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(noticeService.updateNotice(notice));
	}

	/**
	 * 删除公告
	 */
	@RequiresPermissions("admin:notice:remove")
	@Log(title = "删除通知公告", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(noticeService.deleteNoticeByIds(ids));
	}
}
