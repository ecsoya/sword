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
 * The Class AdminInformController.
 */
@Controller
@RequestMapping("/admin/inform")
public class AdminInformController extends BaseController {

	/** The prefix. */
	private final String prefix = "admin/inform";

	/** The notice service. */
	@Autowired
	private ISysNoticeService noticeService;

	/**
	 * Inform.
	 *
	 * @return the string
	 */
	@RequiresPermissions("admin:inform:view")
	@GetMapping()
	public String inform() {
		return prefix + "/inform";
	}

	/**
	 * List.
	 *
	 * @param inform the inform
	 * @return the table data info
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
	 * @param inform the inform
	 * @return the ajax result
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
	 * Edits the.
	 *
	 * @param noticeId the notice id
	 * @param mmap     the mmap
	 * @return the string
	 */
	@GetMapping("/edit/{noticeId}")
	public String edit(@PathVariable("noticeId") Long noticeId, ModelMap mmap) {
		mmap.put("inform", noticeService.selectNoticeById(noticeId));
		return prefix + "/edit";
	}

	/**
	 * Edits the save.
	 *
	 * @param inform the inform
	 * @return the ajax result
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
	 * Removes the.
	 *
	 * @param ids the ids
	 * @return the ajax result
	 */
	@RequiresPermissions("admin:inform:remove")
	@Log(title = "系统消息", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(noticeService.deleteNoticeByIds(ids));
	}
}
