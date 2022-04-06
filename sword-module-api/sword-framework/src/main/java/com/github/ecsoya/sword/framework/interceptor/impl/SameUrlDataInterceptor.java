package com.github.ecsoya.sword.framework.interceptor.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.github.ecsoya.sword.common.json.JSON;
import com.github.ecsoya.sword.framework.interceptor.RepeatSubmitInterceptor;

/**
 * The Class SameUrlDataInterceptor.
 */
@Component
public class SameUrlDataInterceptor extends RepeatSubmitInterceptor {

	/** The repeat params. */
	public final String REPEAT_PARAMS = "repeatParams";

	/** The repeat time. */
	public final String REPEAT_TIME = "repeatTime";

	/** The session repeat key. */
	public final String SESSION_REPEAT_KEY = "repeatData";

	/** The interval time. */
	private int intervalTime = 10;

	/**
	 * Sets the interval time.
	 *
	 * @param intervalTime the new interval time
	 */
	public void setIntervalTime(int intervalTime) {
		this.intervalTime = intervalTime;
	}

	/**
	 * Checks if is repeat submit.
	 *
	 * @param request the request
	 * @return true, if is repeat submit
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean isRepeatSubmit(HttpServletRequest request) throws Exception {
		// 本次参数及系统时间
		final String nowParams = JSON.marshal(request.getParameterMap());
		final Map<String, Object> nowDataMap = new HashMap<String, Object>();
		nowDataMap.put(REPEAT_PARAMS, nowParams);
		nowDataMap.put(REPEAT_TIME, System.currentTimeMillis());

		// 请求地址（作为存放session的key值）
		final String url = request.getRequestURI();

		final HttpSession session = request.getSession();
		final Object sessionObj = session.getAttribute(SESSION_REPEAT_KEY);
		if (sessionObj != null) {
			final Map<String, Object> sessionMap = (Map<String, Object>) sessionObj;
			if (sessionMap.containsKey(url)) {
				final Map<String, Object> preDataMap = (Map<String, Object>) sessionMap.get(url);
				if (compareParams(nowDataMap, preDataMap) && compareTime(nowDataMap, preDataMap)) {
					return true;
				}
			}
		}
		final Map<String, Object> sessionMap = new HashMap<String, Object>();
		sessionMap.put(url, nowDataMap);
		session.setAttribute(SESSION_REPEAT_KEY, sessionMap);
		return false;
	}

	/**
	 * Compare params.
	 *
	 * @param nowMap the now map
	 * @param preMap the pre map
	 * @return true, if successful
	 */
	private boolean compareParams(Map<String, Object> nowMap, Map<String, Object> preMap) {
		final String nowParams = (String) nowMap.get(REPEAT_PARAMS);
		final String preParams = (String) preMap.get(REPEAT_PARAMS);
		return nowParams.equals(preParams);
	}

	/**
	 * Compare time.
	 *
	 * @param nowMap the now map
	 * @param preMap the pre map
	 * @return true, if successful
	 */
	private boolean compareTime(Map<String, Object> nowMap, Map<String, Object> preMap) {
		final long time1 = (Long) nowMap.get(REPEAT_TIME);
		final long time2 = (Long) preMap.get(REPEAT_TIME);
		if ((time1 - time2) < (this.intervalTime * 1000)) {
			return true;
		}
		return false;
	}
}
