package com.soyatec.sword.framework.aspectj;

import java.util.Objects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.soyatec.sword.common.annotation.DataSource;
import com.soyatec.sword.common.config.datasource.DynamicDataSourceContextHolder;
import com.soyatec.sword.common.utils.StringUtils;

/**
 * 多数据源处理
 *
 * @author Jin Liu (angryred@qq.com)
 */
@Aspect
@Order(1)
@Component
public class DataSourceAspect {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Pointcut("@annotation(com.soyatec.sword.common.annotation.DataSource)"
			+ "|| @within(com.soyatec.sword.common.annotation.DataSource)")
	public void dsPointCut() {

	}

	@Around("dsPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		final DataSource dataSource = getDataSource(point);

		if (StringUtils.isNotNull(dataSource)) {
			DynamicDataSourceContextHolder.setDataSourceType(dataSource.value().name());
		}

		try {
			return point.proceed();
		} finally {
			// 销毁数据源 在执行方法之后
			DynamicDataSourceContextHolder.clearDataSourceType();
		}
	}

	/**
	 * 获取需要切换的数据源
	 */
	public DataSource getDataSource(ProceedingJoinPoint point) {
		final MethodSignature signature = (MethodSignature) point.getSignature();
		final DataSource dataSource = AnnotationUtils.findAnnotation(signature.getMethod(), DataSource.class);
		if (Objects.nonNull(dataSource)) {
			return dataSource;
		}

		return AnnotationUtils.findAnnotation(signature.getDeclaringType(), DataSource.class);
	}
}
