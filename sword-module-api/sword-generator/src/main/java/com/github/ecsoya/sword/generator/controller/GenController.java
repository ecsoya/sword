package com.github.ecsoya.sword.generator.controller;

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
import com.github.ecsoya.sword.common.annotation.Log;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.domain.CxSelect;
import com.github.ecsoya.sword.common.core.page.TableDataInfo;
import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.enums.BusinessType;
import com.github.ecsoya.sword.generator.domain.GenTable;
import com.github.ecsoya.sword.generator.domain.GenTableColumn;
import com.github.ecsoya.sword.generator.service.IGenTableColumnService;
import com.github.ecsoya.sword.generator.service.IGenTableService;

/**
 * The Class GenController.
 */
@Controller
@RequestMapping("/tool/gen")
public class GenController extends BaseController {

	/** The prefix. */
	private final String prefix = "tool/gen";

	/** The gen table service. */
	@Autowired
	private IGenTableService genTableService;

	/** The gen table column service. */
	@Autowired
	private IGenTableColumnService genTableColumnService;

	/**
	 * Gen.
	 *
	 * @return the string
	 */
	@GetMapping()
	public String gen() {
		return prefix + "/gen";
	}

	/**
	 * Gen list.
	 *
	 * @param genTable the gen table
	 * @return the table data info
	 */
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo genList(GenTable genTable) {
		startPage();
		final List<GenTable> list = genTableService.selectGenTableList(genTable);
		return getDataTable(list);
	}

	/**
	 * Data list.
	 *
	 * @param genTable the gen table
	 * @return the table data info
	 */
	@PostMapping("/db/list")
	@ResponseBody
	public TableDataInfo dataList(GenTable genTable) {
		startPage();
		final List<GenTable> list = genTableService.selectDbTableList(genTable);
		return getDataTable(list);
	}

	/**
	 * Column list.
	 *
	 * @param genTableColumn the gen table column
	 * @return the table data info
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
	 * Import table.
	 *
	 * @return the string
	 */
	@GetMapping("/importTable")
	public String importTable() {
		return prefix + "/importTable";
	}

	/**
	 * Import table save.
	 *
	 * @param tables the tables
	 * @return the ajax result
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
	 * Edits the.
	 *
	 * @param tableId the table id
	 * @param mmap    the mmap
	 * @return the string
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
	 * Edits the save.
	 *
	 * @param genTable the gen table
	 * @return the ajax result
	 */
	@Log(title = "代码生成", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@Validated GenTable genTable) {
		genTableService.validateEdit(genTable);
		genTableService.updateGenTable(genTable);
		return AjaxResult.success();
	}

	/**
	 * Removes the.
	 *
	 * @param ids the ids
	 * @return the ajax result
	 */
	@Log(title = "代码生成", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		genTableService.deleteGenTableByIds(ids);
		return AjaxResult.success();
	}

	/**
	 * Preview.
	 *
	 * @param tableId the table id
	 * @return the ajax result
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@GetMapping("/preview/{tableId}")
	@ResponseBody
	public AjaxResult preview(@PathVariable("tableId") Long tableId) throws IOException {
		final Map<String, String> dataMap = genTableService.previewCode(tableId);
		return AjaxResult.success(dataMap);
	}

	/**
	 * Download.
	 *
	 * @param response  the response
	 * @param tableName the table name
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Log(title = "代码生成", businessType = BusinessType.GENCODE)
	@GetMapping("/download/{tableName}")
	public void download(HttpServletResponse response, @PathVariable("tableName") String tableName) throws IOException {
		final byte[] data = genTableService.downloadCode(tableName);
		genCode(response, data);
	}

	/**
	 * Gen code.
	 *
	 * @param tableName the table name
	 * @return the ajax result
	 */
	@Log(title = "代码生成", businessType = BusinessType.GENCODE)
	@GetMapping("/genCode/{tableName}")
	@ResponseBody
	public AjaxResult genCode(@PathVariable("tableName") String tableName) {
		genTableService.generatorCode(tableName);
		return AjaxResult.success();
	}

	/**
	 * Synch db.
	 *
	 * @param tableName the table name
	 * @return the ajax result
	 */
	@Log(title = "代码生成", businessType = BusinessType.UPDATE)
	@GetMapping("/synchDb/{tableName}")
	@ResponseBody
	public AjaxResult synchDb(@PathVariable("tableName") String tableName) {
		genTableService.synchDb(tableName);
		return AjaxResult.success();
	}

	/**
	 * Batch gen code.
	 *
	 * @param response the response
	 * @param tables   the tables
	 * @throws IOException Signals that an I/O exception has occurred.
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
	 * Gen code.
	 *
	 * @param response the response
	 * @param data     the data
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void genCode(HttpServletResponse response, byte[] data) throws IOException {
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\"sword.zip\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream; charset=UTF-8");
		IOUtils.write(data, response.getOutputStream());
	}
}