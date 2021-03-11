package com.soyatec.sword.token.aspect;

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
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.enums.BusinessStatus;
import com.soyatec.sword.common.utils.AddressUtils;
import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.common.utils.IpUtils;
import com.soyatec.sword.common.utils.ServletUtils;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.common.utils.async.AsyncManager;
import com.soyatec.sword.common.utils.spring.SpringUtils;
import com.soyatec.sword.token.annotation.TokenAccessLog;
import com.soyatec.sword.token.domain.TokenAccess;
import com.soyatec.sword.token.service.ITokenAccessService;

/**
 * 操作日志记录处理
 * 
 * @author AngryRED (jin.liu@soyatec.com)
 */
@Aspect
@Component
public class TokenAccessAspect {
	private static final Logger log = LoggerFactory.getLogger(TokenAccessAspect.class);

	// 配置织入点
	@Pointcut("@annotation(com.soyatec.sword.token.annotation.TokenAccessLog)")
	public void logPointCut() {
	}

	/**
	 * 处理完请求后执行
	 *
	 * @param joinPoint 切点
	 */
	@AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
	public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
		handleLog(joinPoint, null, jsonResult);
	}

	/**
	 * 拦截异常操作
	 * 
	 * @param joinPoint 切点
	 * @param e         异常
	 */
	@AfterThrowing(value = "logPointCut()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
		handleLog(joinPoint, e, null);
	}

	protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
		try {
			// 获得注解
			TokenAccessLog controllerLog = getAnnotationLog(joinPoint);
			if (controllerLog == null) {
				return;
			}
			String description = controllerLog.description();
			// *========数据库日志=========*//
			TokenAccess operLog = new TokenAccess();
			operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
			// 请求的地址
			HttpServletRequest request = ServletUtils.getRequest();
			String ip = IpUtils.getIpAddr(request);
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
				CommonResult<?> cr = (CommonResult<?>) jsonResult;
				if (cr.isSuccess()) {
					operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
				} else {
					operLog.setStatus(BusinessStatus.FAIL.ordinal());
					operLog.setErrorMsg(cr.getInfo());
				}
			}
			// 设置方法名称
			if (StringUtils.isNotEmpty(description)) {
				String className = joinPoint.getTarget().getClass().getName();
				String methodName = joinPoint.getSignature().getName();
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
		} catch (Exception exp) {
			// 记录本地异常日志
			log.error("==前置通知异常==");
			log.error("异常信息:{}", exp.getMessage());
			exp.printStackTrace();
		}
	}

	/**
	 * 获取注解中对方法的描述信息 用于Controller层注解
	 * 
	 * @param log     日志
	 * @param operLog 操作日志
	 * @throws Exception
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
	 * 获取请求的参数，放到log中
	 * 
	 * @param operLog
	 * @param request
	 */
	private void setRequestValue(TokenAccess operLog) {
		HttpServletRequest request = ServletUtils.getRequest();
		Map<String, String[]> map = request == null ? Collections.emptyMap() : request.getParameterMap();
		String params = JSON.toJSONString(map);
		operLog.setOperParam(StringUtils.substring(params, 0, 2000));
	}

	/**
	 * 是否存在注解，如果存在就获取
	 */
	private TokenAccessLog getAnnotationLog(JoinPoint joinPoint) throws Exception {
		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();

		if (method != null) {
			return method.getAnnotation(TokenAccessLog.class);
		}
		return null;
	}
}
