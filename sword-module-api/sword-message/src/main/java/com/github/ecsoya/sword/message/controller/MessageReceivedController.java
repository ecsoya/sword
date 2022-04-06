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

/**
 * The Class MessageReceivedController.
 */
@Controller
@RequestMapping("/message/received")
public class MessageReceivedController extends AbstractMessageController {

	/** The user message service. */
	@Autowired
	private IUserMessageService userMessageService;

	/**
	 * Gets the message count.
	 *
	 * @return the message count
	 */
	@GetMapping("/count")
	@ResponseBody
	public int getMessageCount() {
		Long userId = getUserId();
		if (userId == null) {
			return 0;
		}
		return userMessageService.selectUserMessageUnreadCount(userId);
	}

	/**
	 * Index.
	 *
	 * @return the string
	 */
	@GetMapping()
	public String index() {
		return "message/received/message";
	}

	/**
	 * List.
	 *
	 * @param message the message
	 * @return the table data info
	 */
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(UserMessage message) {
		message.setUserId(getUserId());
		startPage();
		return getDataTable(userMessageService.selectUserMessageList(message));
	}

	/**
	 * Removes the.
	 *
	 * @param ids the ids
	 * @return the ajax result
	 */
	@Log(title = "用户消息", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(userMessageService.deleteUserMessageByIds(ids));
	}

	/**
	 * Mark read.
	 *
	 * @param ids the ids
	 * @return the ajax result
	 */
	@PostMapping("/markRead")
	@ResponseBody
	public AjaxResult markRead(String ids) {
		Long userId = getUserId();
		if (StringUtils.isEmpty(ids)) {
			return toAjax(userMessageService.readUserMessageByUserId(userId));
		}
		return toAjax(userMessageService.readUserMessageByIds(userId, ids));
	}

	/**
	 * Mark read.
	 *
	 * @return the ajax result
	 */
	@PostMapping("/clear")
	@ResponseBody
	public AjaxResult markRead() {
		Long userId = getUserId();
		return toAjax(userMessageService.removeAllUserMessageByUserId(userId));
	}

	/**
	 * Detail.
	 *
	 * @param id   the id
	 * @param mmap the mmap
	 * @return the string
	 */
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
