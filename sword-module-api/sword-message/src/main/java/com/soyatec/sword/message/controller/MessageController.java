package com.soyatec.sword.message.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soyatec.sword.common.annotation.Log;
import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.AjaxResult;
import com.soyatec.sword.common.core.domain.entity.SysUser;
import com.soyatec.sword.common.core.page.TableDataInfo;
import com.soyatec.sword.common.enums.BusinessType;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.common.utils.bean.BeanUtils;
import com.soyatec.sword.message.domain.UserMessage;
import com.soyatec.sword.message.service.IUserMessageService;

@Controller
@RequestMapping("/message")
public class MessageController extends BaseController {

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
		return "message/message";
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

	private Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	private Long getUserId() {
		return getSysUser().getUserId().longValue();
	}

	private SysUser getSysUser() {
		SysUser user = null;
		final Object obj = getSubject().getPrincipal();
		if (StringUtils.isNotNull(obj)) {
			user = new SysUser();
			BeanUtils.copyBeanProp(user, obj);
		}
		return user;
	}
}
