package com.github.ecsoya.sword.config.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import com.github.pagehelper.Page;

/**
 * The Class JdbcTemplateSupport.
 */
public class JdbcTemplateSupport extends JdbcTemplate {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(JdbcTemplateSupport.class);

	/**
	 * Instantiates a new jdbc template support.
	 */
	public JdbcTemplateSupport() {
	}

	/**
	 * Instantiates a new jdbc template support.
	 *
	 * @param dataSource the data source
	 */
	public JdbcTemplateSupport(DataSource dataSource) {
		super(dataSource);
	}

	/**
	 * Instantiates a new jdbc template support.
	 *
	 * @param dataSource the data source
	 * @param lazyInit   the lazy init
	 */
	public JdbcTemplateSupport(DataSource dataSource, boolean lazyInit) {
		super(dataSource, lazyInit);
	}

	/**
	 * Query for page.
	 *
	 * @param sql        the sql
	 * @param pagination the pagination
	 * @return the page bean
	 * @throws DataAccessException the data access exception
	 */
	public PageBean<Map<String, Object>> queryForPage(String sql, Page<Map<String, Object>> pagination)
			throws DataAccessException {
		return queryForPage(sql, pagination, getColumnMapRowMapper());
	}

	/**
	 * Query for page.
	 *
	 * @param <T>        the generic type
	 * @param sql        the sql
	 * @param pagination the pagination
	 * @param rowMapper  the row mapper
	 * @return the page bean
	 * @throws DataAccessException the data access exception
	 */
	public <T> PageBean<T> queryForPage(String sql, Page<T> pagination, RowMapper<T> rowMapper)
			throws DataAccessException {

		return queryForPage(sql, pagination, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement preparedStatement) throws SQLException {
				return;
			}
		}, rowMapper);
	}

	/**
	 * Query for page.
	 *
	 * @param <T>        the generic type
	 * @param sql        the sql
	 * @param pagination the pagination
	 * @param var2       the var 2
	 * @param var3       the var 3
	 * @return the page bean
	 * @throws DataAccessException the data access exception
	 */
	public <T> PageBean<T> queryForPage(String sql, Page<T> pagination, PreparedStatementSetter var2, RowMapper<T> var3)
			throws DataAccessException {

		final PageBean<T> result = new PageBean<T>();

		// ??????????????????
		final String countSql = "select count(1) as count from (" + sql + ") temp";
		log.info("countSql {}", countSql);
		final List<Integer> countList = super.query(countSql, var2, new RowMapper<Integer>() {
			@Override
			public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
				return new Integer(resultSet.getInt("count"));
			}
		});

		result.setTotal(countList.get(0));
		result.setSize(countList.get(0));
		result.setPageNum(pagination.getPageNum());
		result.setPageSize(pagination.getPageSize());

		final int pageCount = result.getSize() % result.getPageSize();
		result.setPages(pageCount == 0 ? (result.getSize() / result.getPageSize())
				: (result.getSize() / result.getPageSize() + 1));

		sql += parseLimit(result);
		log.info("queryLimitSql {}", sql);

		final List<T> data = super.query(sql, var2, var3);
		result.setList(data);
		return result;
	}

	/**
	 * Parses the limit.
	 *
	 * @param <T>        the generic type
	 * @param pagination the pagination
	 * @return the string
	 */
	private <T> String parseLimit(PageBean<T> pagination) {

		final StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(" ");
		stringBuffer.append("limit");
		stringBuffer.append(" ");
		if (pagination.getPageNum() == 0) {
			stringBuffer.append("0");
		} else {
			stringBuffer.append((pagination.getPageNum() - 1) * pagination.getPageSize());
		}
		stringBuffer.append(",");
		stringBuffer.append(pagination.getPageSize());

		return stringBuffer.toString();
	}
}