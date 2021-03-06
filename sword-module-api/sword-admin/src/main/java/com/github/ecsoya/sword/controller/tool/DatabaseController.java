package com.github.ecsoya.sword.controller.tool;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.ecsoya.sword.common.config.datasource.DynamicDataSourceContextHolder;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.page.TableDataInfo;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.config.jdbc.JdbcTemplateSupport;
import com.github.ecsoya.sword.config.jdbc.PageBean;
import com.github.pagehelper.Page;

/**
 * The Class DatabaseController.
 */
@Controller
@RequestMapping("/database")
public class DatabaseController extends BaseController {

	/** The jdbc template. */
	@Autowired
	private JdbcTemplateSupport jdbcTemplate;

	/**
	 * Indices.
	 *
	 * @return the string
	 */
	@GetMapping("/query")
	public String indices() {
		return "tool/database/query";
	}

	/**
	 * Perform query.
	 *
	 * @param dsType the ds type
	 * @param sql    the sql
	 * @return the table data info
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/query")
	@ResponseBody
	public TableDataInfo performQuery(String dsType, String sql) {
		if (StringUtils.isEmpty(sql)) {
			return new TableDataInfo();
		}
		DynamicDataSourceContextHolder.setDataSourceType(dsType);
		Page<Map<String, Object>> page = (Page<Map<String, Object>>) startPage();
		if (page == null) {
			page = new Page<>(1, 10);
		}
		final PageBean<Map<String, Object>> br = jdbcTemplate.queryForPage(sql, page);
		final List<Map<String, Object>> list = br.getList();
		final TableDataInfo table = new TableDataInfo(list, (int) br.getTotal());
		if (!list.isEmpty()) {
			table.setMsg(org.apache.commons.lang3.StringUtils.join(list.get(0).keySet().toArray(), ','));
		}

		return table;
	}

	/**
	 * List table names.
	 *
	 * @param dsType the ds type
	 * @return the list
	 */
	@GetMapping("/tables")
	@ResponseBody
	public List<String> listTableNames(String dsType) {
		if (StringUtils.isNotEmpty(dsType)) {
			DynamicDataSourceContextHolder.setDataSourceType(dsType);
		}
		final String sql = "select table_name from\n" + "		information_schema.tables"
				+ "		where table_schema = (select database())\n" + "	AND"
				+ "		table_name NOT LIKE 'qrtz_%' AND table_name NOT LIKE 'gen_%'";
		return jdbcTemplate.queryForList(sql, String.class);
	}
}
