package com.github.ecsoya.sword.framework.aspectj;

import java.lang.reflect.Method;
import java.util.Map;

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

import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import com.github.ecsoya.sword.common.annotation.Log;
import com.github.ecsoya.sword.common.core.domain.entity.SysUser;
import com.github.ecsoya.sword.common.enums.BusinessStatus;
import com.github.ecsoya.sword.common.json.JSON;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.ServletUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.async.AsyncManager;
import com.github.ecsoya.sword.framework.manager.factory.AsyncFactory;
import com.github.ecsoya.sword.framework.shiro.util.ShiroUtils;
import com.github.ecsoya.sword.system.domain.SysOperLog;

/**
 * The Class LogAspect.
 */
@Aspect
@Component
public class LogAspect {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

	/** The Constant EXCLUDE_PROPERTIES. */
	public static final String[] EXCLUDE_PROPERTIES = { "password", "oldPassword", "newPassword", "confirmPassword" };

	/**
	 * Log point cut.
	 */
	// 配置织入点
	@Pointcut("@annotation(com.github.ecsoya.sword.common.annotation.Log)")
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
			final Log controllerLog = getAnnotationLog(joinPoint);
			if (controllerLog == null) {
				return;
			}

			// 获取当前的用户
			final SysUser currentUser = ShiroUtils.getSysUser();

			// *========数据库日志=========*//
			final SysOperLog operLog = new SysOperLog();
			operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
			operLog.setOperTime(DateUtils.getNowDate());
			// 请求的地址
			final String ip = ShiroUtils.getIp();
			operLog.setOperIp(ip);
			// 返回参数
			operLog.setJsonResult(StringUtils.substring(JSON.marshal(jsonResult), 0, 2000));

			operLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
			if (currentUser != null) {
				operLog.setOperName(currentUser.getLoginName());
				if (StringUtils.isNotNull(currentUser.getDept())
						&& StringUtils.isNotEmpty(currentUser.getDept().getDeptName())) {
					operLog.setDeptName(currentUser.getDept().getDeptName());
				}
			}

			if (e != null) {
				operLog.setStatus(BusinessStatus.FAIL.ordinal());
				operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
			}
			// 设置方法名称
			final String className = joinPoint.getTarget().getClass().getName();
			final String methodName = joinPoint.getSignature().getName();
			operLog.setMethod(className + "." + methodName + "()");
			// 设置请求方式
			operLog.setRequestMethod(ServletUtils.getRequest().getMethod());
			// 处理设置注解上的参数
			getControllerMethodDescription(controllerLog, operLog);
			// 保存数据库
			AsyncManager.me().execute(AsyncFactory.recordOper(operLog));
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
	public void getControllerMethodDescription(Log log, SysOperLog operLog) throws Exception {
		// 设置action动作
		operLog.setBusinessType(log.businessType().ordinal());
		// 设置标题
		operLog.setTitle(log.title());
		// 设置操作人类别
		operLog.setOperatorType(log.operatorType().ordinal());
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
	 * @throws Exception the exception
	 */
	private void setRequestValue(SysOperLog operLog) throws Exception {
		final Map<String, String[]> map = ServletUtils.getRequest().getParameterMap();
		if (StringUtils.isNotEmpty(map)) {
			final PropertyPreFilters.MySimplePropertyPreFilter excludefilter = new PropertyPreFilters().addFilter();
			excludefilter.addExcludes(EXCLUDE_PROPERTIES);
			final String params = com.alibaba.fastjson.JSON.toJSONString(map, excludefilter);
			operLog.setOperParam(StringUtils.substring(params, 0, 2000));
		}
	}

	/**
	 * Gets the annotation log.
	 *
	 * @param joinPoint the join point
	 * @return the annotation log
	 * @throws Exception the exception
	 */
	private Log getAnnotationLog(JoinPoint joinPoint) throws Exception {
		final Signature signature = joinPoint.getSignature();
		final MethodSignature methodSignature = (MethodSignature) signature;
		final Method method = methodSignature.getMethod();

		if (method != null) {
			return method.getAnnotation(Log.class);
		}
		return null;
	}
}
