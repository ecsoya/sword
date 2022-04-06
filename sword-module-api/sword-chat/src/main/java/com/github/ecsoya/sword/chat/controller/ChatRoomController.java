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

import com.github.ecsoya.sword.chat.domain.ChatRoom;
import com.github.ecsoya.sword.chat.service.IChatRoomService;
import com.github.ecsoya.sword.common.annotation.Log;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.page.TableDataInfo;
import com.github.ecsoya.sword.common.enums.BusinessType;
import com.github.ecsoya.sword.common.utils.poi.ExcelUtil;

/**
 * The Class ChatRoomController.
 */
@Controller
@RequestMapping("/chat/room")
public class ChatRoomController extends BaseController {

	/** The prefix. */
	private String prefix = "chat/room";

	/** The chat room service. */
	@Autowired
	private IChatRoomService chatRoomService;

	/**
	 * Room.
	 *
	 * @return the string
	 */
	@RequiresPermissions("chat:room:view")
	@GetMapping()
	public String room() {
		return prefix + "/room";
	}

	/**
	 * List.
	 *
	 * @param chatRoom the chat room
	 * @return the table data info
	 */
	@RequiresPermissions("chat:room:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ChatRoom chatRoom) {
		startPage();
		List<ChatRoom> list = chatRoomService.selectChatRoomList(chatRoom);
		return getDataTable(list);
	}

	/**
	 * Export.
	 *
	 * @param chatRoom the chat room
	 * @return the ajax result
	 */
	@RequiresPermissions("chat:room:export")
	@Log(title = "聊天室", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(ChatRoom chatRoom) {
		List<ChatRoom> list = chatRoomService.selectChatRoomList(chatRoom);
		ExcelUtil<ChatRoom> util = new ExcelUtil<ChatRoom>(ChatRoom.class);
		return util.exportExcel(list, "room");
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
	 * @param chatRoom the chat room
	 * @return the ajax result
	 */
	@RequiresPermissions("chat:room:add")
	@Log(title = "聊天室", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ChatRoom chatRoom) {
		return toAjax(chatRoomService.insertChatRoom(chatRoom));
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
		ChatRoom chatRoom = chatRoomService.selectChatRoomById(id);
		mmap.put("chatRoom", chatRoom);
		return prefix + "/edit";
	}

	/**
	 * Edits the save.
	 *
	 * @param chatRoom the chat room
	 * @return the ajax result
	 */
	@RequiresPermissions("chat:room:edit")
	@Log(title = "聊天室", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ChatRoom chatRoom) {
		return toAjax(chatRoomService.updateChatRoom(chatRoom));
	}

	/**
	 * Removes the.
	 *
	 * @param ids the ids
	 * @return the ajax result
	 */
	@RequiresPermissions("chat:room:remove")
	@Log(title = "聊天室", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(chatRoomService.deleteChatRoomByIds(ids));
	}
}
