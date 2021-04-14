//package com.soyatec.sword.controller.monitor;
//
//import java.util.List;
//
//import org.apache.shiro.authz.annotation.Logical;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.soyatec.sword.common.annotation.Log;
//import com.soyatec.sword.common.core.controller.BaseController;
//import com.soyatec.sword.common.core.domain.AjaxResult;
//import com.soyatec.sword.common.core.page.TableDataInfo;
//import com.soyatec.sword.common.core.text.Convert;
//import com.soyatec.sword.common.enums.BusinessType;
//import com.soyatec.sword.common.enums.OnlineStatus;
//import com.soyatec.sword.framework.shiro.session.OnlineSession;
//import com.soyatec.sword.framework.shiro.session.OnlineSessionDAO;
//import com.soyatec.sword.framework.shiro.util.ShiroUtils;
//import com.soyatec.sword.system.domain.SysUserOnline;
//import com.soyatec.sword.system.service.ISysUserOnlineService;
//
///**
// * 在线用户监控
// *
// * @author Jin Liu (angryred@qq.com)
// */
//@Controller
//@RequestMapping("/monitor/online")
//public class SysUserOnlineController extends BaseController {
//	private String prefix = "monitor/online";
//
//	@Autowired
//	private ISysUserOnlineService userOnlineService;
//
//	@Autowired
//	private OnlineSessionDAO onlineSessionDAO;
//
//	@RequiresPermissions("monitor:online:view")
//	@GetMapping()
//	public String online() {
//		return prefix + "/online";
//	}
//
//	@RequiresPermissions("monitor:online:list")
//	@PostMapping("/list")
//	@ResponseBody
//	public TableDataInfo list(SysUserOnline userOnline) {
//		startPage();
//		List<SysUserOnline> list = userOnlineService.selectUserOnlineList(userOnline);
//		return getDataTable(list);
//	}
//
//	@RequiresPermissions(value = { "monitor:online:batchForceLogout",
//			"monitor:online:forceLogout" }, logical = Logical.OR)
//	@Log(title = "在线用户", businessType = BusinessType.FORCE)
//	@PostMapping("/batchForceLogout")
//	@ResponseBody
//	public AjaxResult batchForceLogout(String ids) {
//		for (String sessionId : Convert.toStrArray(ids)) {
//			SysUserOnline online = userOnlineService.selectOnlineById(sessionId);
//			if (online == null) {
//				return error("用户已下线");
//			}
//			OnlineSession onlineSession = (OnlineSession) onlineSessionDAO.readSession(online.getSessionId());
//			if (onlineSession == null) {
//				return error("用户已下线");
//			}
//			if (sessionId.equals(ShiroUtils.getSessionId())) {
//				return error("当前登录用户无法强退");
//			}
//			onlineSessionDAO.delete(onlineSession);
//			online.setStatus(OnlineStatus.off_line);
//			userOnlineService.saveOnline(online);
//			userOnlineService.removeUserCache(online.getLoginName(), sessionId);
//		}
//		return success();
//	}
//}
