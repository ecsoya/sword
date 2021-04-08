package com.soyatec.sword.controller.tool;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.soyatec.sword.common.config.datasource.DynamicDataSourceContextHolder;
import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.page.TableDataInfo;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.config.jdbc.JdbcTemplateSupport;
import com.soyatec.sword.config.jdbc.PageBean;

@Controller
@RequestMapping("/database")
public class DatabaseController extends BaseController {

	@Autowired
	private JdbcTemplateSupport jdbcTemplate;

	@GetMapping("/query")
	public String indices() {
		return "tool/database/query";
	}

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
		PageBean<Map<String, Object>> br = jdbcTemplate.queryForPage(sql, page);
		List<Map<String, Object>> list = br.getList();
		TableDataInfo table = new TableDataInfo(list, (int) br.getTotal());
		if (!list.isEmpty()) {
			table.setMsg(StringUtils.join(list.get(0).keySet().toArray(), ','));
		}

		return table;
	}

	@GetMapping("/tables")
	@ResponseBody
	public List<String> listTableNames(String dsType) {
		if (StringUtils.isNotEmpty(dsType)) {
			DynamicDataSourceContextHolder.setDataSourceType(dsType);
		}
		String sql = "select table_name from\n" + "		information_schema.tables"
				+ "		where table_schema = (select database())\n" + "	AND"
				+ "		table_name NOT LIKE 'qrtz_%' AND table_name NOT LIKE 'gen_%'";
		return jdbcTemplate.queryForList(sql, String.class);
	}
}
