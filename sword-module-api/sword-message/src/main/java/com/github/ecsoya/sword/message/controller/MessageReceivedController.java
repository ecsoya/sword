package com.github.ecsoya.sword.message.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.ecsoya.sword.common.annotation.Log;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.page.TableDataInfo;
import com.github.ecsoya.sword.common.enums.BusinessType;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.async.AsyncManager;
import com.github.ecsoya.sword.message.domain.UserMessage;
import com.github.ecsoya.sword.message.service.IUserMessageService;

@Controller
@RequestMapping("/message/received")
public class MessageReceivedController extends AbstractMessageController {

	@Autowired
	private IUserMessageService userMessageService;

	@GetMapping("/count")
	@ResponseBody
	public int getMessageCount() {
		Long userId = getUserId();
		if (userId == null) {
			return 0;
		}
		return userMessageService.selectUserMessageUnreadCount(userId);
	}

	@GetMapping()
	public String index() {
		return "message/received/message";
	}

	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(UserMessage message) {
		message.setUserId(getUserId());
		startPage();
		return getDataTable(userMessageService.selectUserMessageList(message));
	}

	@Log(title = "用户消息", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(userMessageService.deleteUserMessageByIds(ids));
	}

	@PostMapping("/markRead")
	@ResponseBody
	public AjaxResult markRead(String ids) {
		Long userId = getUserId();
		if (StringUtils.isEmpty(ids)) {
			return toAjax(userMessageService.readUserMessageByUserId(userId));
		}
		return toAjax(userMessageService.readUserMessageByIds(userId, ids));
	}

	@PostMapping("/clear")
	@ResponseBody
	public AjaxResult markRead() {
		Long userId = getUserId();
		return toAjax(userMessageService.removeAllUserMessageByUserId(userId));
	}

	@GetMapping("/detail")
	public String detail(Long id, ModelMap mmap) {
		Long userId = getUserId();
		UserMessage message = userMessageService.selectUserMessageById(id);
		if (message != null) {
			mmap.put("title", message.getTitle());
			mmap.put("content", message.getContent());
			mmap.put("date", DateUtils.toDefault(message.getCreateTime()));
			mmap.put("sender", message.getCreateBy());
			AsyncManager.me().execute(new Runnable() {

				@Override
				public void run() {
					userMessageService.readUserMessageByIds(userId, Long.toString(id));
				}
			});
		}
		return "message/received/detail";
	}

}
