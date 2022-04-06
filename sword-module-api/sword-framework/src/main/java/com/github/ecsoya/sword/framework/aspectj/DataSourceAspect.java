package com.github.ecsoya.sword.framework.aspectj;

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

import com.github.ecsoya.sword.common.annotation.DataSource;
import com.github.ecsoya.sword.common.config.datasource.DynamicDataSourceContextHolder;
import com.github.ecsoya.sword.common.utils.StringUtils;

/**
 * The Class DataSourceAspect.
 */
@Aspect
@Order(1)
@Component
public class DataSourceAspect {

	/** The logger. */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Ds point cut.
	 */
	@Pointcut("@annotation(com.github.ecsoya.sword.common.annotation.DataSource)"
			+ "|| @within(com.github.ecsoya.sword.common.annotation.DataSource)")
	public void dsPointCut() {

	}

	/**
	 * Around.
	 *
	 * @param point the point
	 * @return the object
	 * @throws Throwable the throwable
	 */
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
	 * Gets the data source.
	 *
	 * @param point the point
	 * @return the data source
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
