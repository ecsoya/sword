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

import com.github.ecsoya.sword.chat.domain.ChatMember;
import com.github.ecsoya.sword.chat.service.IChatMemberService;
import com.github.ecsoya.sword.common.annotation.Log;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.page.TableDataInfo;
import com.github.ecsoya.sword.common.enums.BusinessType;
import com.github.ecsoya.sword.common.utils.poi.ExcelUtil;

/**
 * 聊天成员Controller
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-06-03
 */
@Controller
@RequestMapping("/chat/member")
public class ChatMemberController extends BaseController {
	private String prefix = "chat/member";

	@Autowired
	private IChatMemberService chatMemberService;

	@RequiresPermissions("chat:member:view")
	@GetMapping()
	public String member() {
		return prefix + "/member";
	}

	/**
	 * 查询聊天成员列表
	 */
	@RequiresPermissions("chat:member:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ChatMember chatMember) {
		startPage();
		List<ChatMember> list = chatMemberService.selectChatMemberList(chatMember);
		return getDataTable(list);
	}

	/**
	 * 导出聊天成员列表
	 */
	@RequiresPermissions("chat:member:export")
	@Log(title = "聊天成员", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(ChatMember chatMember) {
		List<ChatMember> list = chatMemberService.selectChatMemberList(chatMember);
		ExcelUtil<ChatMember> util = new ExcelUtil<ChatMember>(ChatMember.class);
		return util.exportExcel(list, "member");
	}

	/**
	 * 新增聊天成员
	 */
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 新增保存聊天成员
	 */
	@RequiresPermissions("chat:member:add")
	@Log(title = "聊天成员", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ChatMember chatMember) {
		return toAjax(chatMemberService.insertChatMember(chatMember));
	}

	/**
	 * 修改聊天成员
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap) {
		ChatMember chatMember = chatMemberService.selectChatMemberById(id);
		mmap.put("chatMember", chatMember);
		return prefix + "/edit";
	}

	/**
	 * 修改保存聊天成员
	 */
	@RequiresPermissions("chat:member:edit")
	@Log(title = "聊天成员", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ChatMember chatMember) {
		return toAjax(chatMemberService.updateChatMember(chatMember));
	}

	/**
	 * 删除聊天成员
	 */
	@RequiresPermissions("chat:member:remove")
	@Log(title = "聊天成员", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(chatMemberService.deleteChatMemberByIds(ids));
	}
}
