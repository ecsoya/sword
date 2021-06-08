package com.soyatec.sword.chat.controller;

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

import com.soyatec.sword.chat.domain.ChatHistory;
import com.soyatec.sword.chat.service.IChatHistoryService;
import com.soyatec.sword.common.annotation.Log;
import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.AjaxResult;
import com.soyatec.sword.common.core.page.TableDataInfo;
import com.soyatec.sword.common.enums.BusinessType;
import com.soyatec.sword.common.utils.poi.ExcelUtil;

/**
 * 聊天记录Controller
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-06-03
 */
@Controller
@RequestMapping("/chat/history")
public class ChatHistoryController extends BaseController {
	private String prefix = "chat/history";

	@Autowired
	private IChatHistoryService chatHistoryService;

	@RequiresPermissions("chat:history:view")
	@GetMapping()
	public String history() {
		return prefix + "/history";
	}

	/**
	 * 查询聊天记录列表
	 */
	@RequiresPermissions("chat:history:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ChatHistory chatHistory) {
		startPage();
		List<ChatHistory> list = chatHistoryService.selectChatHistoryList(chatHistory);
		return getDataTable(list);
	}

	/**
	 * 导出聊天记录列表
	 */
	@RequiresPermissions("chat:history:export")
	@Log(title = "聊天记录", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(ChatHistory chatHistory) {
		List<ChatHistory> list = chatHistoryService.selectChatHistoryList(chatHistory);
		ExcelUtil<ChatHistory> util = new ExcelUtil<ChatHistory>(ChatHistory.class);
		return util.exportExcel(list, "history");
	}

	/**
	 * 新增聊天记录
	 */
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 新增保存聊天记录
	 */
	@RequiresPermissions("chat:history:add")
	@Log(title = "聊天记录", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ChatHistory chatHistory) {
		return toAjax(chatHistoryService.insertChatHistory(chatHistory));
	}

	/**
	 * 修改聊天记录
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap) {
		ChatHistory chatHistory = chatHistoryService.selectChatHistoryById(id);
		mmap.put("chatHistory", chatHistory);
		return prefix + "/edit";
	}

	/**
	 * 修改保存聊天记录
	 */
	@RequiresPermissions("chat:history:edit")
	@Log(title = "聊天记录", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ChatHistory chatHistory) {
		return toAjax(chatHistoryService.updateChatHistory(chatHistory));
	}

	/**
	 * 删除聊天记录
	 */
	@RequiresPermissions("chat:history:remove")
	@Log(title = "聊天记录", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(chatHistoryService.deleteChatHistoryByIds(ids));
	}
}
