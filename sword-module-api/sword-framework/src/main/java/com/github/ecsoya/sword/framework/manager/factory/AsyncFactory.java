package com.github.ecsoya.sword.framework.manager.factory;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ecsoya.sword.common.constant.Constants;
import com.github.ecsoya.sword.common.utils.AddressUtils;
import com.github.ecsoya.sword.common.utils.LogUtils;
import com.github.ecsoya.sword.common.utils.ServletUtils;
import com.github.ecsoya.sword.common.utils.spring.SpringUtils;
import com.github.ecsoya.sword.framework.shiro.session.OnlineSession;
import com.github.ecsoya.sword.framework.shiro.util.ShiroUtils;
import com.github.ecsoya.sword.system.domain.SysLogininfor;
import com.github.ecsoya.sword.system.domain.SysOperLog;
import com.github.ecsoya.sword.system.domain.SysUserOnline;
import com.github.ecsoya.sword.system.service.ISysOperLogService;
import com.github.ecsoya.sword.system.service.ISysUserOnlineService;
import com.github.ecsoya.sword.system.service.impl.SysLogininforServiceImpl;

import eu.bitwalker.useragentutils.UserAgent;

/**
 * A factory for creating Async objects.
 */
public class AsyncFactory {

	/** The Constant sys_user_logger. */
	private static final Logger sys_user_logger = LoggerFactory.getLogger("sys-user");

	/**
	 * Sync session to db.
	 *
	 * @param session the session
	 * @return the timer task
	 */
	public static TimerTask syncSessionToDb(final OnlineSession session) {
		return new TimerTask() {
			@Override
			public void run() {
				final SysUserOnline online = new SysUserOnline();
				online.setSessionId(String.valueOf(session.getId()));
				online.setDeptName(session.getDeptName());
				online.setLoginName(session.getLoginName());
				online.setStartTimestamp(session.getStartTimestamp());
				online.setLastAccessTime(session.getLastAccessTime());
				online.setExpireTime(session.getTimeout());
				online.setIpaddr(session.getHost());
				online.setLoginLocation(AddressUtils.getRealAddressByIP(session.getHost()));
				online.setBrowser(session.getBrowser());
				online.setOs(session.getOs());
				online.setStatus(session.getStatus());
				SpringUtils.getBean(ISysUserOnlineService.class).saveOnline(online);

			}
		};
	}

	/**
	 * Record oper.
	 *
	 * @param operLog the oper log
	 * @return the timer task
	 */
	public static TimerTask recordOper(final SysOperLog operLog) {
		return new TimerTask() {
			@Override
			public void run() {
				// 远程查询操作地点
				operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
				SpringUtils.getBean(ISysOperLogService.class).insertOperlog(operLog);
			}
		};
	}

	/**
	 * Record logininfor.
	 *
	 * @param username the username
	 * @param status   the status
	 * @param message  the message
	 * @param args     the args
	 * @return the timer task
	 */
	public static TimerTask recordLogininfor(final String username, final String status, final String message,
			final Object... args) {
		final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
		final String ip = ShiroUtils.getIp();
		return new TimerTask() {
			@Override
			public void run() {
				final String address = AddressUtils.getRealAddressByIP(ip);
				final StringBuilder s = new StringBuilder();
				s.append(LogUtils.getBlock(ip));
				s.append(address);
				s.append(LogUtils.getBlock(username));
				s.append(LogUtils.getBlock(status));
				s.append(LogUtils.getBlock(message));
				// 打印信息到日志
				sys_user_logger.info(s.toString(), args);
				// 获取客户端操作系统
				final String os = userAgent.getOperatingSystem().getName();
				// 获取客户端浏览器
				final String browser = userAgent.getBrowser().getName();
				// 封装对象
				final SysLogininfor logininfor = new SysLogininfor();
				logininfor.setLoginName(username);
				logininfor.setIpaddr(ip);
				logininfor.setLoginLocation(address);
				logininfor.setBrowser(browser);
				logininfor.setOs(os);
				logininfor.setMsg(message);
				// 日志状态
				if (org.apache.commons.lang3.StringUtils.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT,
						Constants.REGISTER)) {
					logininfor.setStatus(Constants.SUCCESS);
				} else if (Constants.LOGIN_FAIL.equals(status)) {
					logininfor.setStatus(Constants.FAIL);
				}
				// 插入数据
				SpringUtils.getBean(SysLogininforServiceImpl.class).insertLogininfor(logininfor);
			}
		};
	}
}
