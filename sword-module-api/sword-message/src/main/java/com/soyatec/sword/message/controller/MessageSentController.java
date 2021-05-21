package com.soyatec.sword.message.controller;

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
import com.soyatec.sword.common.core.domain.AjaxResult;
import com.soyatec.sword.common.core.page.TableDataInfo;
import com.soyatec.sword.common.enums.BusinessType;
import com.soyatec.sword.message.domain.SwordMessage;
import com.soyatec.sword.message.service.ISwordMessageService;

@Controller
@RequestMapping("/message/sent")
public class MessageSentController extends AbstractMessageController {

	private static final String prefix = "message/sent";

	@Autowired
	private ISwordMessageService messageService;

	@RequiresPermissions("message:sent:view")
	@GetMapping()
	public String index() {
		return prefix + "/message";
	}

	@RequiresPermissions("message:sent:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SwordMessage message) {
		message.setUserId(getUserId());
		startPage();
		return getDataTable(messageService.selectSwordMessageList(message));
	}

	@RequiresPermissions("message:sent:remove")
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(messageService.deleteSwordMessageByIds(ids));
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
	@RequiresPermissions("message:sent:add")
	@Log(title = "消息", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(SwordMessage swordMessage) {
		swordMessage.setUserId(getUserId());
		return toAjax(messageService.insertSwordMessage(swordMessage));
	}

	/**
	 * 修改消息
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap) {
		SwordMessage swordMessage = messageService.selectSwordMessageById(id);
		mmap.put("swordMessage", swordMessage);
		return prefix + "/edit";
	}

	/**
	 * 修改保存消息
	 */
	@RequiresPermissions("message:sent:edit")
	@Log(title = "消息", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(SwordMessage swordMessage) {
		return toAjax(messageService.updateSwordMessage(swordMessage));
	}

}
