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

import com.soyatec.sword.chat.domain.ChatRoom;
import com.soyatec.sword.chat.service.IChatRoomService;
import com.soyatec.sword.common.annotation.Log;
import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.AjaxResult;
import com.soyatec.sword.common.core.page.TableDataInfo;
import com.soyatec.sword.common.enums.BusinessType;
import com.soyatec.sword.common.utils.poi.ExcelUtil;

/**
 * 聊天室Controller
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-06-03
 */
@Controller
@RequestMapping("/chat/room")
public class ChatRoomController extends BaseController {
	private String prefix = "chat/room";

	@Autowired
	private IChatRoomService chatRoomService;

	@RequiresPermissions("chat:room:view")
	@GetMapping()
	public String room() {
		return prefix + "/room";
	}

	/**
	 * 查询聊天室列表
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
	 * 导出聊天室列表
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
	 * 新增聊天室
	 */
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 新增保存聊天室
	 */
	@RequiresPermissions("chat:room:add")
	@Log(title = "聊天室", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ChatRoom chatRoom) {
		return toAjax(chatRoomService.insertChatRoom(chatRoom));
	}

	/**
	 * 修改聊天室
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap) {
		ChatRoom chatRoom = chatRoomService.selectChatRoomById(id);
		mmap.put("chatRoom", chatRoom);
		return prefix + "/edit";
	}

	/**
	 * 修改保存聊天室
	 */
	@RequiresPermissions("chat:room:edit")
	@Log(title = "聊天室", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ChatRoom chatRoom) {
		return toAjax(chatRoomService.updateChatRoom(chatRoom));
	}

	/**
	 * 删除聊天室
	 */
	@RequiresPermissions("chat:room:remove")
	@Log(title = "聊天室", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(chatRoomService.deleteChatRoomByIds(ids));
	}
}
