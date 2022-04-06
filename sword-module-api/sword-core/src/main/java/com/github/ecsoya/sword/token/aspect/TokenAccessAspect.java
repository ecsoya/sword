package com.github.ecsoya.sword.token.aspect;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.enums.BusinessStatus;
import com.github.ecsoya.sword.common.utils.AddressUtils;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.IpUtils;
import com.github.ecsoya.sword.common.utils.ServletUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.async.AsyncManager;
import com.github.ecsoya.sword.common.utils.spring.SpringUtils;
import com.github.ecsoya.sword.token.annotation.TokenAccessLog;
import com.github.ecsoya.sword.token.domain.TokenAccess;
import com.github.ecsoya.sword.token.service.ITokenAccessService;

/**
 * The Class TokenAccessAspect.
 */
@Aspect
@Component
public class TokenAccessAspect {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(TokenAccessAspect.class);

	/**
	 * Log point cut.
	 */
	// 配置织入点
	@Pointcut("@annotation(com.github.ecsoya.sword.token.annotation.TokenAccessLog)")
	public void logPointCut() {
	}

	/**
	 * Do after returning.
	 *
	 * @param joinPoint  the join point
	 * @param jsonResult the json result
	 */
	@AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
	public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
		handleLog(joinPoint, null, jsonResult);
	}

	/**
	 * Do after throwing.
	 *
	 * @param joinPoint the join point
	 * @param e         the e
	 */
	@AfterThrowing(value = "logPointCut()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
		handleLog(joinPoint, e, null);
	}

	/**
	 * Handle log.
	 *
	 * @param joinPoint  the join point
	 * @param e          the e
	 * @param jsonResult the json result
	 */
	protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
		try {
			// 获得注解
			final TokenAccessLog controllerLog = getAnnotationLog(joinPoint);
			if (controllerLog == null) {
				return;
			}
			final String description = controllerLog.description();
			// *========数据库日志=========*//
			final TokenAccess operLog = new TokenAccess();
			operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
			// 请求的地址
			final HttpServletRequest request = ServletUtils.getRequest();
			final String ip = IpUtils.getIpAddr(request);
			operLog.setOperIp(ip);
			// 返回参数
			operLog.setJsonResult(JSON.toJSONString(jsonResult));

			if (request != null) {
				operLog.setOperUrl(request.getRequestURI());
			}

			if (e != null) {
				operLog.setStatus(BusinessStatus.FAIL.ordinal());
				operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
			} else if (jsonResult instanceof CommonResult<?>) {
				final CommonResult<?> cr = (CommonResult<?>) jsonResult;
				if (cr.isSuccess()) {
					operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
				} else {
					operLog.setStatus(BusinessStatus.FAIL.ordinal());
					operLog.setErrorMsg(cr.getInfo());
				}
			}
			// 设置方法名称
			if (StringUtils.isNotEmpty(description)) {
				final String className = joinPoint.getTarget().getClass().getName();
				final String methodName = joinPoint.getSignature().getName();
				operLog.setMethod(className + "." + methodName + "()");
			} else {
				operLog.setMethod(description);
			}
			// 设置请求方式
			if (request != null) {
				operLog.setRequestMethod(request.getMethod());
			}
			// 处理设置注解上的参数
			getControllerMethodDescription(controllerLog, operLog);
			// 保存数据库
			AsyncManager.me().execute(new Runnable() {

				@Override
				public void run() {
					operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
					SpringUtils.getBean(ITokenAccessService.class).insertTokenAccess(operLog);
				}
			});
			operLog.setOperTime(DateUtils.getNowDate());
		} catch (final Exception exp) {
			// 记录本地异常日志
			log.error("==前置通知异常==");
			log.error("异常信息:{}", exp.getMessage());
			exp.printStackTrace();
		}
	}

	/**
	 * Gets the controller method description.
	 *
	 * @param log     the log
	 * @param operLog the oper log
	 * @throws Exception the exception
	 */
	public void getControllerMethodDescription(TokenAccessLog log, TokenAccess operLog) throws Exception {
		// 设置标题
		operLog.setTitle(log.title());
		// 设置操作人类别
		// 是否需要保存request，参数和值
		if (log.isSaveRequestData()) {
			// 获取参数的信息，传入到数据库中。
			setRequestValue(operLog);
		}
	}

	/**
	 * Sets the request value.
	 *
	 * @param operLog the new request value
	 */
	private void setRequestValue(TokenAccess operLog) {
		final HttpServletRequest request = ServletUtils.getRequest();
		final Map<String, String[]> map = request == null ? Collections.emptyMap() : request.getParameterMap();
		final String params = JSON.toJSONString(map);
		operLog.setOperParam(StringUtils.substring(params, 0, 2000));
	}

	/**
	 * Gets the annotation log.
	 *
	 * @param joinPoint the join point
	 * @return the annotation log
	 * @throws Exception the exception
	 */
	private TokenAccessLog getAnnotationLog(JoinPoint joinPoint) throws Exception {
		final Signature signature = joinPoint.getSignature();
		final MethodSignature methodSignature = (MethodSignature) signature;
		final Method method = methodSignature.getMethod();

		if (method != null) {
			return method.getAnnotation(TokenAccessLog.class);
		}
		return null;
	}
}
