package com.github.ecsoya.sword.common.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.utils.StringUtils;

public class LongArrayTypeHandler extends BaseTypeHandler<Long[]> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Long[] parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i, StringUtils.arrayToString(parameter));
	}

	@Override
	public Long[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return Convert.toLongArray(rs.getString(columnName));
	}

	@Override
	public Long[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return Convert.toLongArray(rs.getString(columnIndex));
	}

	@Override
	public Long[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return Convert.toLongArray(cs.getString(columnIndex));
	}

}
