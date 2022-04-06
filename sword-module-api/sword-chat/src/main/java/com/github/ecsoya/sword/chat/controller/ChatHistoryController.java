package com.github.ecsoya.sword.chat.controller;

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

import com.github.ecsoya.sword.chat.domain.ChatHistory;
import com.github.ecsoya.sword.chat.service.IChatHistoryService;
import com.github.ecsoya.sword.common.annotation.Log;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.page.TableDataInfo;
import com.github.ecsoya.sword.common.enums.BusinessType;
import com.github.ecsoya.sword.common.utils.poi.ExcelUtil;

/**
 * The Class ChatHistoryController.
 */
@Controller
@RequestMapping("/chat/history")
public class ChatHistoryController extends BaseController {

	/** The prefix. */
	private String prefix = "chat/history";

	/** The chat history service. */
	@Autowired
	private IChatHistoryService chatHistoryService;

	/**
	 * History.
	 *
	 * @return the string
	 */
	@RequiresPermissions("chat:history:view")
	@GetMapping()
	public String history() {
		return prefix + "/history";
	}

	/**
	 * List.
	 *
	 * @param chatHistory the chat history
	 * @return the table data info
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
	 * Export.
	 *
	 * @param chatHistory the chat history
	 * @return the ajax result
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
	 * @param chatHistory the chat history
	 * @return the ajax result
	 */
	@RequiresPermissions("chat:history:add")
	@Log(title = "聊天记录", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ChatHistory chatHistory) {
		return toAjax(chatHistoryService.insertChatHistory(chatHistory));
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
		ChatHistory chatHistory = chatHistoryService.selectChatHistoryById(id);
		mmap.put("chatHistory", chatHistory);
		return prefix + "/edit";
	}

	/**
	 * Edits the save.
	 *
	 * @param chatHistory the chat history
	 * @return the ajax result
	 */
	@RequiresPermissions("chat:history:edit")
	@Log(title = "聊天记录", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ChatHistory chatHistory) {
		return toAjax(chatHistoryService.updateChatHistory(chatHistory));
	}

	/**
	 * Removes the.
	 *
	 * @param ids the ids
	 * @return the ajax result
	 */
	@RequiresPermissions("chat:history:remove")
	@Log(title = "聊天记录", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(chatHistoryService.deleteChatHistoryByIds(ids));
	}
}
