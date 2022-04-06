package com.github.ecsoya.sword.message.controller;

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
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.page.TableDataInfo;
import com.github.ecsoya.sword.common.enums.BusinessType;
import com.github.ecsoya.sword.message.domain.SwordMessage;
import com.github.ecsoya.sword.message.service.ISwordMessageService;

/**
 * The Class MessageSentController.
 */
@Controller
@RequestMapping("/message/sent")
public class MessageSentController extends AbstractMessageController {

	/** The Constant prefix. */
	private static final String prefix = "message/sent";

	/** The message service. */
	@Autowired
	private ISwordMessageService messageService;

	/**
	 * Index.
	 *
	 * @return the string
	 */
	@RequiresPermissions("message:sent:view")
	@GetMapping()
	public String index() {
		return prefix + "/message";
	}

	/**
	 * List.
	 *
	 * @param message the message
	 * @return the table data info
	 */
	@RequiresPermissions("message:sent:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SwordMessage message) {
		message.setUserId(getUserId());
		startPage();
		return getDataTable(messageService.selectSwordMessageList(message));
	}

	/**
	 * Removes the.
	 *
	 * @param ids the ids
	 * @return the ajax result
	 */
	@RequiresPermissions("message:sent:remove")
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(messageService.deleteSwordMessageByIds(ids));
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
	 * @param swordMessage the sword message
	 * @return the ajax result
	 */
	@RequiresPermissions("message:sent:add")
	@Log(title = "消息", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(SwordMessage swordMessage) {
		swordMessage.setUserId(getUserId());
		swordMessage.setCreateBy(getUserName());
		return toAjax(messageService.insertSwordMessage(swordMessage));
	}

	/**
	 * Edits the.
	 *
	 * @param id   the id
	 * @param mmap the mmap
	 * @return the string
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap) {
		SwordMessage swordMessage = messageService.selectSwordMessageById(id);
		mmap.put("swordMessage", swordMessage);
		return prefix + "/edit";
	}

	/**
	 * Edits the save.
	 *
	 * @param swordMessage the sword message
	 * @return the ajax result
	 */
	@RequiresPermissions("message:sent:edit")
	@Log(title = "消息", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(SwordMessage swordMessage) {
		return toAjax(messageService.updateSwordMessage(swordMessage));
	}

}
