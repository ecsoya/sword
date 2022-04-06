package com.github.ecsoya.sword.controller.admin;

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
import com.github.ecsoya.sword.common.config.GlobalConfig;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.page.TableDataInfo;
import com.github.ecsoya.sword.common.enums.BusinessType;
import com.github.ecsoya.sword.common.utils.async.AsyncManager;
import com.github.ecsoya.sword.framework.shiro.util.ShiroUtils;
import com.github.ecsoya.sword.message.IMessageType;
import com.github.ecsoya.sword.message.MessageHelper;
import com.github.ecsoya.sword.system.domain.SysNotice;
import com.github.ecsoya.sword.system.service.ISysNoticeService;

/**
 * The Class AdminNoticeController.
 */
@Controller
@RequestMapping("/admin/notice")
public class AdminNoticeController extends BaseController {

	/** The prefix. */
	private final String prefix = "admin/notice";

	/** The notice service. */
	@Autowired
	private ISysNoticeService noticeService;

	/**
	 * Notice.
	 *
	 * @return the string
	 */
	@RequiresPermissions("admin:notice:view")
	@GetMapping()
	public String notice() {
		return prefix + "/notice";
	}

	/**
	 * List.
	 *
	 * @param notice the notice
	 * @return the table data info
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
	 * Adds the.
	 *
	 * @return the string
	 */
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * Adds the save.
	 *
	 * @param notice the notice
	 * @return the ajax result
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
	 * Edits the.
	 *
	 * @param noticeId the notice id
	 * @param mmap     the mmap
	 * @return the string
	 */
	@GetMapping("/edit/{noticeId}")
	public String edit(@PathVariable("noticeId") Long noticeId, ModelMap mmap) {
		mmap.put("notice", noticeService.selectNoticeById(noticeId));
		return prefix + "/edit";
	}

	/**
	 * Edits the save.
	 *
	 * @param notice the notice
	 * @return the ajax result
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
	 * Removes the.
	 *
	 * @param ids the ids
	 * @return the ajax result
	 */
	@RequiresPermissions("admin:notice:remove")
	@Log(title = "删除通知公告", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(noticeService.deleteNoticeByIds(ids));
	}
}
