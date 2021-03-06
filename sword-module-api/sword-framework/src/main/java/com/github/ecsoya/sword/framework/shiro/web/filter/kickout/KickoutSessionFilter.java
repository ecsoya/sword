package com.github.ecsoya.sword.framework.shiro.web.filter.kickout;

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
import com.github.ecsoya.sword.common.constant.ShiroConstants;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.core.domain.entity.SysUser;
import com.github.ecsoya.sword.common.utils.ServletUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.framework.shiro.util.ShiroUtils;

/**
 * The Class KickoutSessionFilter.
 */
public class KickoutSessionFilter extends AccessControlFilter {

	/** The Constant objectMapper. */
	private final static ObjectMapper objectMapper = new ObjectMapper();

	/** The max session. */
	private int maxSession = -1;

	/** The kickout after. */
	private Boolean kickoutAfter = false;

	/** The kickout url. */
	private String kickoutUrl;

	/** The session manager. */
	private SessionManager sessionManager;

	/** The cache. */
	private Cache<String, Deque<Serializable>> cache;

	/**
	 * Checks if is access allowed.
	 *
	 * @param servletRequest  the servlet request
	 * @param servletResponse the servlet response
	 * @param o               the o
	 * @return true, if is access allowed
	 * @throws Exception the exception
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o)
			throws Exception {
		return false;
	}

	/**
	 * On access denied.
	 *
	 * @param request  the request
	 * @param response the response
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		final Subject subject = getSubject(request, response);
		if (!subject.isAuthenticated() && !subject.isRemembered() || maxSession == -1) {
			// ?????????????????????????????????????????????-1??????????????????????????????
			return true;
		}
		try {
			final Session session = subject.getSession();
			// ??????????????????
			final SysUser user = ShiroUtils.getSysUser();
			final String loginName = user.getLoginName();
			final Serializable sessionId = session.getId();

			// ?????????????????? ???????????????
			Deque<Serializable> deque = cache.get(loginName);
			if (deque == null) {
				// ???????????????
				deque = new ArrayDeque<Serializable>();
			}

			// ????????????????????????sessionId??????????????????????????????????????????
			if (!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
				// ???sessionId????????????
				deque.push(sessionId);
				// ????????????sessionId????????????
				cache.put(loginName, deque);
			}

			// ??????????????????sessionId???????????????????????????????????????
			while (deque.size() > maxSession) {
				Serializable kickoutSessionId = null;
				// ???????????????????????????????????????false?????????????????????????????????????????????????????????
				if (kickoutAfter) {
					// ????????????
					kickoutSessionId = deque.removeFirst();
				} else {
					// ????????????
					kickoutSessionId = deque.removeLast();
				}
				// ?????????????????????????????????
				cache.put(loginName, deque);

				try {
					// ??????????????????sessionId???session??????
					final Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
					if (null != kickoutSession) {
						// ???????????????kickout?????????????????????
						kickoutSession.setAttribute("kickout", true);
					}
				} catch (final Exception e) {
					// ?????????????????????????????????
				}
			}

			// ?????????????????????(???????????????)?????????????????????????????????????????????
			if ((Boolean) session.getAttribute("kickout") != null
					&& (Boolean) session.getAttribute("kickout") == true) {
				// ????????????
				subject.logout();
				saveRequest(request);
				return redirectToKickoutLogin(request, response);
			}
			return true;
		} catch (final Exception e) {
			return redirectToKickoutLogin(request, response);
		}
	}

	/**
	 * Redirect to kickout login.
	 *
	 * @param request  the request
	 * @param response the response
	 * @return true, if successful
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private boolean redirectToKickoutLogin(ServletRequest request, ServletResponse response) throws IOException {
		final HttpServletRequest req = (HttpServletRequest) request;
		final HttpServletResponse res = (HttpServletResponse) response;
		if (StringUtils.isEmpty(getLoginUrl())) {
			// ??????????????????????????????json?????????????????????
			response.getWriter()
					.write(JSON.toJSONString(CommonResult.fail(HttpStatus.UNAUTHORIZED.value(), "Not Login")));
			response.getWriter().flush();
			response.getWriter().close();
		} else {
			if (ServletUtils.isAjaxRequest(req)) {
				final AjaxResult ajaxResult = AjaxResult.error("?????????????????????????????????????????????????????????");
				ServletUtils.renderString(res, objectMapper.writeValueAsString(ajaxResult));
			} else {
				WebUtils.issueRedirect(request, response, kickoutUrl, null, true, false);
			}
		}
		return false;
	}

	/**
	 * Sets the max session.
	 *
	 * @param maxSession the new max session
	 */
	public void setMaxSession(int maxSession) {
		this.maxSession = maxSession;
	}

	/**
	 * Sets the kickout after.
	 *
	 * @param kickoutAfter the new kickout after
	 */
	public void setKickoutAfter(boolean kickoutAfter) {
		this.kickoutAfter = kickoutAfter;
	}

	/**
	 * Sets the kickout url.
	 *
	 * @param kickoutUrl the new kickout url
	 */
	public void setKickoutUrl(String kickoutUrl) {
		this.kickoutUrl = kickoutUrl;
	}

	/**
	 * Sets the session manager.
	 *
	 * @param sessionManager the new session manager
	 */
	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	/**
	 * Sets the cache manager.
	 *
	 * @param cacheManager the new cache manager
	 */
	// ??????Cache???key?????????
	public void setCacheManager(CacheManager cacheManager) {
		// ?????????ehcache????????????????????????name??????
		this.cache = cacheManager.getCache(ShiroConstants.SYS_USERCACHE);
	}
}
