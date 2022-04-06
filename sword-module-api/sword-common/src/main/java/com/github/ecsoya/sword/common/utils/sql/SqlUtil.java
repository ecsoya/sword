package com.github.ecsoya.sword.common.utils.sql;

import com.github.ecsoya.sword.common.exception.base.BaseException;
import com.github.ecsoya.sword.common.utils.StringUtils;

/**
 * The Class SqlUtil.
 */
public class SqlUtil {

	/** The sql pattern. */
	public static String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,\\.]+";

	/**
	 * Escape order by sql.
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String escapeOrderBySql(String value) {
		if (StringUtils.isNotEmpty(value) && !isValidOrderBySql(value)) {
			throw new BaseException("参数不符合规范，不能进行查询");
		}
		return value;
	}

	/**
	 * Checks if is valid order by sql.
	 *
	 * @param value the value
	 * @return true, if is valid order by sql
	 */
	public static boolean isValidOrderBySql(String value) {
		return value.matches(SQL_PATTERN);
	}
}
