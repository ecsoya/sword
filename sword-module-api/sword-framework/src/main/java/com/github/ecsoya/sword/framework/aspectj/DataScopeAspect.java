package com.github.ecsoya.sword.framework.aspectj;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.github.ecsoya.sword.common.annotation.DataScope;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;
import com.github.ecsoya.sword.common.core.domain.entity.SysRole;
import com.github.ecsoya.sword.common.core.domain.entity.SysUser;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.framework.shiro.util.ShiroUtils;

/**
 * 数据过滤处理
 *
 * @author Jin Liu (angryred@qq.com)
 */
@Aspect
@Component
public class DataScopeAspect {
	/**
	 * 全部数据权限
	 */
	public static final String DATA_SCOPE_ALL = "1";

	/**
	 * 自定数据权限
	 */
	public static final String DATA_SCOPE_CUSTOM = "2";

	/**
	 * 部门数据权限
	 */
	public static final String DATA_SCOPE_DEPT = "3";

	/**
	 * 部门及以下数据权限
	 */
	public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";

	/**
	 * 仅本人数据权限
	 */
	public static final String DATA_SCOPE_SELF = "5";

	/**
	 * 数据权限过滤关键字
	 */
	public static final String DATA_SCOPE = "dataScope";

	// 配置织入点
	@Pointcut("@annotation(com.github.ecsoya.sword.common.annotation.DataScope)")
	public void dataScopePointCut() {
	}

	@Before("dataScopePointCut()")
	public void doBefore(JoinPoint point) throws Throwable {
		handleDataScope(point);
	}

	protected void handleDataScope(final JoinPoint joinPoint) {
		// 获得注解
		final DataScope controllerDataScope = getAnnotationLog(joinPoint);
		if (controllerDataScope == null) {
			return;
		}
		// 获取当前的用户
		final SysUser currentUser = ShiroUtils.getSysUser();
		if (currentUser != null) {
			// 如果是超级管理员，则不过滤数据
			if (!currentUser.isAdmin()) {
				dataScopeFilter(joinPoint, currentUser, controllerDataScope);
			}
		}
	}

	/**
	 * 数据范围过滤
	 *
	 * @param joinPoint 切点
	 * @param user      用户
	 * @param deptAlias 部门别名
	 * @param userAlias 用户别名
	 */
	public static void dataScopeFilter(JoinPoint joinPoint, SysUser user, DataScope dataScope) {
		StringBuilder sqlString = new StringBuilder();
		String deptAlias = dataScope.deptAlias();
		String userAlias = dataScope.userAlias();
		String userIdColumn = dataScope.userIdColumn();
		if (userIdColumn == null || userIdColumn.equals("")) {
			userIdColumn = "user_id";
		}
		for (final SysRole role : user.getRoles()) {
			final String dataScopeType = role.getDataScope();
			if (DATA_SCOPE_ALL.equals(dataScopeType)) {
				sqlString = new StringBuilder();
				break;
			} else if (DATA_SCOPE_CUSTOM.equals(dataScopeType)) {
				sqlString.append(StringUtils.format(
						" OR {}.dept_id IN ( SELECT dept_id FROM sys_role_dept WHERE role_id = {} ) ", deptAlias,
						role.getRoleId()));
			} else if (DATA_SCOPE_DEPT.equals(dataScopeType)) {
				sqlString.append(StringUtils.format(" OR {}.dept_id = {} ", deptAlias, user.getDeptId()));
			} else if (DATA_SCOPE_DEPT_AND_CHILD.equals(dataScopeType)) {
				sqlString.append(StringUtils.format(
						" OR {}.dept_id IN ( SELECT dept_id FROM sys_dept WHERE dept_id = {} or find_in_set( {} , ancestors ) )",
						deptAlias, user.getDeptId(), user.getDeptId()));
			} else if (DATA_SCOPE_SELF.equals(dataScopeType)) {
				if (org.apache.commons.lang3.StringUtils.isNotBlank(userAlias)) {
					sqlString.append(StringUtils.format(" OR {}.{} = {} ", userAlias, userIdColumn, user.getUserId()));
				} else {
					// 数据权限为仅本人且没有userAlias别名不查询任何数据
					sqlString.append(" OR 1=0 ");
				}
			}
		}

		if (org.apache.commons.lang3.StringUtils.isNotBlank(sqlString.toString())) {
			final Object params = joinPoint.getArgs()[0];
			if (StringUtils.isNotNull(params) && params instanceof BaseEntity) {
				final BaseEntity baseEntity = (BaseEntity) params;
				baseEntity.getParams().put(DATA_SCOPE, " AND (" + sqlString.substring(4) + ")");
			}
		}
	}

	/**
	 * 是否存在注解，如果存在就获取
	 */
	private DataScope getAnnotationLog(JoinPoint joinPoint) {
		final Signature signature = joinPoint.getSignature();
		final MethodSignature methodSignature = (MethodSignature) signature;
		final Method method = methodSignature.getMethod();

		if (method != null) {
			return method.getAnnotation(DataScope.class);
		}
		return null;
	}
}