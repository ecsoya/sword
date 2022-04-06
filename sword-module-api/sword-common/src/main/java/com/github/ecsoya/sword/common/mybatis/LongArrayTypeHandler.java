package com.github.ecsoya.sword.common.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.utils.StringUtils;

/**
 * The Class LongArrayTypeHandler.
 */
public class LongArrayTypeHandler extends BaseTypeHandler<Long[]> {

	/**
	 * Sets the non null parameter.
	 *
	 * @param ps        the ps
	 * @param i         the i
	 * @param parameter the parameter
	 * @param jdbcType  the jdbc type
	 * @throws SQLException the SQL exception
	 */
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Long[] parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i, StringUtils.arrayToString(parameter));
	}

	/**
	 * Gets the nullable result.
	 *
	 * @param rs         the rs
	 * @param columnName the column name
	 * @return the nullable result
	 * @throws SQLException the SQL exception
	 */
	@Override
	public Long[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return Convert.toLongArray(rs.getString(columnName));
	}

	/**
	 * Gets the nullable result.
	 *
	 * @param rs          the rs
	 * @param columnIndex the column index
	 * @return the nullable result
	 * @throws SQLException the SQL exception
	 */
	@Override
	public Long[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return Convert.toLongArray(rs.getString(columnIndex));
	}

	/**
	 * Gets the nullable result.
	 *
	 * @param cs          the cs
	 * @param columnIndex the column index
	 * @return the nullable result
	 * @throws SQLException the SQL exception
	 */
	@Override
	public Long[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return Convert.toLongArray(cs.getString(columnIndex));
	}

}
