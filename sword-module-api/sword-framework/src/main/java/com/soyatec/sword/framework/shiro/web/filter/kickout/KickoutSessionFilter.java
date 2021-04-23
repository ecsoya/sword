package com.soyatec.sword.framework.shiro.web.filter.kickout;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soyatec.sword.common.constant.ShiroConstants;
import com.soyatec.sword.common.core.domain.AjaxResult;
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.core.domain.entity.SysUser;
import com.soyatec.sword.common.utils.ServletUtils;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.framework.shiro.util.ShiroUtils;

/**
 * 登录帐号控制过滤器
 *
 * @author Jin Liu (angryred@qq.com)
 */
public class KickoutSessionFilter extends AccessControlFilter {
	private final static ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 同一个用户最大会话数
	 **/
	private int maxSession = -1;

	/**
	 * 踢出之前登录的/之后登录的用户 默认false踢出之前登录的用户
	 **/
	private Boolean kickoutAfter = false;

	/**
	 * 踢出后到的地址
	 **/
	private String kickoutUrl;

	private SessionManager sessionManager;
	private Cache<String, Deque<Serializable>> cache;

	@Override
	protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o)
			throws Exception {
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		final Subject subject = getSubject(request, response);
		if (!subject.isAuthenticated() && !subject.isRemembered() || maxSession == -1) {
			// 如果没有登录或用户最大会话数为-1，直接进行之后的流程
			return true;
		}
		try {
			final Session session = subject.getSession();
			// 当前登录用户
			final SysUser user = ShiroUtils.getSysUser();
			final String loginName = user.getLoginName();
			final Serializable sessionId = session.getId();

			// 读取缓存用户 没有就存入
			Deque<Serializable> deque = cache.get(loginName);
			if (deque == null) {
				// 初始化队列
				deque = new ArrayDeque<Serializable>();
			}

			// 如果队列里没有此sessionId，且用户没有被踢出；放入队列
			if (!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
				// 将sessionId存入队列
				deque.push(sessionId);
				// 将用户的sessionId队列缓存
				cache.put(loginName, deque);
			}

			// 如果队列里的sessionId数超出最大会话数，开始踢人
			while (deque.size() > maxSession) {
				Serializable kickoutSessionId = null;
				// 是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；
				if (kickoutAfter) {
					// 踢出后者
					kickoutSessionId = deque.removeFirst();
				} else {
					// 踢出前者
					kickoutSessionId = deque.removeLast();
				}
				// 踢出后再更新下缓存队列
				cache.put(loginName, deque);

				try {
					// 获取被踢出的sessionId的session对象
					final Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
					if (null != kickoutSession) {
						// 设置会话的kickout属性表示踢出了
						kickoutSession.setAttribute("kickout", true);
					}
				} catch (final Exception e) {
					// 面对异常，我们选择忽略
				}
			}

			// 如果被踢出了，(前者或后者)直接退出，重定向到踢出后的地址
			if ((Boolean) session.getAttribute("kickout") != null
					&& (Boolean) session.getAttribute("kickout") == true) {
				// 退出登录
				subject.logout();
				saveRequest(request);
				return redirectToKickoutLogin(request, response);
			}
			return true;
		} catch (final Exception e) {
			return redirectToKickoutLogin(request, response);
		}
	}

	private boolean redirectToKickoutLogin(ServletRequest request, ServletResponse response) throws IOException {
		final HttpServletRequest req = (HttpServletRequest) request;
		final HttpServletResponse res = (HttpServletResponse) response;
		if (StringUtils.isEmpty(getLoginUrl())) {
			// 请求被拦截后直接返回json格式的响应数据
			response.getWriter()
					.write(JSON.toJSONString(CommonResult.fail(HttpStatus.UNAUTHORIZED.value(), "Not Login")));
			response.getWriter().flush();
			response.getWriter().close();
		} else {
			if (ServletUtils.isAjaxRequest(req)) {
				final AjaxResult ajaxResult = AjaxResult.error("您已在别处登录，请您修改密码或重新登录");
				ServletUtils.renderString(res, objectMapper.writeValueAsString(ajaxResult));
			} else {
				WebUtils.issueRedirect(request, response, kickoutUrl, null, true, false);
			}
		}
		return false;
	}

	public void setMaxSession(int maxSession) {
		this.maxSession = maxSession;
	}

	public void setKickoutAfter(boolean kickoutAfter) {
		this.kickoutAfter = kickoutAfter;
	}

	public void setKickoutUrl(String kickoutUrl) {
		this.kickoutUrl = kickoutUrl;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	// 设置Cache的key的前缀
	public void setCacheManager(CacheManager cacheManager) {
		// 必须和ehcache缓存配置中的缓存name一致
		this.cache = cacheManager.getCache(ShiroConstants.SYS_USERCACHE);
	}
}
