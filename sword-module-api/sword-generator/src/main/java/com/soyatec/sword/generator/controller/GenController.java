package com.soyatec.sword.generator.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.soyatec.sword.common.annotation.Log;
import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.AjaxResult;
import com.soyatec.sword.common.core.domain.CxSelect;
import com.soyatec.sword.common.core.page.TableDataInfo;
import com.soyatec.sword.common.core.text.Convert;
import com.soyatec.sword.common.enums.BusinessType;
import com.soyatec.sword.generator.domain.GenTable;
import com.soyatec.sword.generator.domain.GenTableColumn;
import com.soyatec.sword.generator.service.IGenTableColumnService;
import com.soyatec.sword.generator.service.IGenTableService;

/**
 * 代码生成 操作处理
 *
 * @author Jin Liu (angryred@qq.com)
 */
@Controller
@RequestMapping("/tool/gen")
public class GenController extends BaseController {
	private final String prefix = "tool/gen";

	@Autowired
	private IGenTableService genTableService;

	@Autowired
	private IGenTableColumnService genTableColumnService;

	@GetMapping()
	public String gen() {
		return prefix + "/gen";
	}

	/**
	 * 查询代码生成列表
	 */
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo genList(GenTable genTable) {
		startPage();
		final List<GenTable> list = genTableService.selectGenTableList(genTable);
		return getDataTable(list);
	}

	/**
	 * 查询数据库列表
	 */
	@PostMapping("/db/list")
	@ResponseBody
	public TableDataInfo dataList(GenTable genTable) {
		startPage();
		final List<GenTable> list = genTableService.selectDbTableList(genTable);
		return getDataTable(list);
	}

	/**
	 * 查询数据表字段列表
	 */
	@PostMapping("/column/list")
	@ResponseBody
	public TableDataInfo columnList(GenTableColumn genTableColumn) {
		final TableDataInfo dataInfo = new TableDataInfo();
		final List<GenTableColumn> list = genTableColumnService.selectGenTableColumnListByTableId(genTableColumn);
		dataInfo.setRows(list);
		dataInfo.setTotal(list.size());
		return dataInfo;
	}

	/**
	 * 导入表结构
	 */
	@GetMapping("/importTable")
	public String importTable() {
		return prefix + "/importTable";
	}

	/**
	 * 导入表结构（保存）
	 */
	@Log(title = "代码生成", businessType = BusinessType.IMPORT)
	@PostMapping("/importTable")
	@ResponseBody
	public AjaxResult importTableSave(String tables) {
		final String[] tableNames = Convert.toStrArray(tables);
		// 查询表信息
		final List<GenTable> tableList = genTableService.selectDbTableListByNames(tableNames);
		genTableService.importGenTable(tableList, "Developer");
		return AjaxResult.success();
	}

	/**
	 * 修改代码生成业务
	 */
	@GetMapping("/edit/{tableId}")
	public String edit(@PathVariable("tableId") Long tableId, ModelMap mmap) {
		final GenTable table = genTableService.selectGenTableById(tableId);
		final List<GenTable> genTables = genTableService.selectGenTableAll();
		final List<CxSelect> cxSelect = new ArrayList<CxSelect>();
		for (final GenTable genTable : genTables) {
			if (!org.apache.commons.lang3.StringUtils.equals(table.getTableName(), genTable.getTableName())) {
				final CxSelect cxTable = new CxSelect(genTable.getTableName(),
						genTable.getTableName() + '：' + genTable.getTableComment());
				final List<CxSelect> cxColumns = new ArrayList<CxSelect>();
				for (final GenTableColumn tableColumn : genTable.getColumns()) {
					cxColumns.add(new CxSelect(tableColumn.getColumnName(),
							tableColumn.getColumnName() + '：' + tableColumn.getColumnComment()));
				}
				cxTable.setS(cxColumns);
				cxSelect.add(cxTable);
			}
		}
		mmap.put("table", table);
		mmap.put("data", JSON.toJSON(cxSelect));
		return prefix + "/edit";
	}

	/**
	 * 修改保存代码生成业务
	 */
	@Log(title = "代码生成", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@Validated GenTable genTable) {
		genTableService.validateEdit(genTable);
		genTableService.updateGenTable(genTable);
		return AjaxResult.success();
	}

	@Log(title = "代码生成", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		genTableService.deleteGenTableByIds(ids);
		return AjaxResult.success();
	}

	/**
	 * 预览代码
	 */
	@GetMapping("/preview/{tableId}")
	@ResponseBody
	public AjaxResult preview(@PathVariable("tableId") Long tableId) throws IOException {
		final Map<String, String> dataMap = genTableService.previewCode(tableId);
		return AjaxResult.success(dataMap);
	}

	/**
	 * 生成代码（下载方式）
	 */
	@Log(title = "代码生成", businessType = BusinessType.GENCODE)
	@GetMapping("/download/{tableName}")
	public void download(HttpServletResponse response, @PathVariable("tableName") String tableName) throws IOException {
		final byte[] data = genTableService.downloadCode(tableName);
		genCode(response, data);
	}

	/**
	 * 生成代码（自定义路径）
	 */
	@Log(title = "代码生成", businessType = BusinessType.GENCODE)
	@GetMapping("/genCode/{tableName}")
	@ResponseBody
	public AjaxResult genCode(@PathVariable("tableName") String tableName) {
		genTableService.generatorCode(tableName);
		return AjaxResult.success();
	}

	/**
	 * 同步数据库
	 */
	@Log(title = "代码生成", businessType = BusinessType.UPDATE)
	@GetMapping("/synchDb/{tableName}")
	@ResponseBody
	public AjaxResult synchDb(@PathVariable("tableName") String tableName) {
		genTableService.synchDb(tableName);
		return AjaxResult.success();
	}

	/**
	 * 批量生成代码
	 */
	@Log(title = "代码生成", businessType = BusinessType.GENCODE)
	@GetMapping("/batchGenCode")
	@ResponseBody
	public void batchGenCode(HttpServletResponse response, String tables) throws IOException {
		final String[] tableNames = Convert.toStrArray(tables);
		final byte[] data = genTableService.downloadCode(tableNames);
		genCode(response, data);
	}

	/**
	 * 生成zip文件
	 */
	private void genCode(HttpServletResponse response, byte[] data) throws IOException {
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\"sword.zip\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream; charset=UTF-8");
		IOUtils.write(data, response.getOutputStream());
	}
}