package com.github.ecsoya.sword.generator.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.ecsoya.sword.common.constant.Constants;
import com.github.ecsoya.sword.common.constant.GenConstants;
import com.github.ecsoya.sword.common.core.text.CharsetKit;
import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.exception.BusinessException;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.generator.domain.GenTable;
import com.github.ecsoya.sword.generator.domain.GenTableColumn;
import com.github.ecsoya.sword.generator.mapper.GenTableColumnMapper;
import com.github.ecsoya.sword.generator.mapper.GenTableMapper;
import com.github.ecsoya.sword.generator.service.IGenTableService;
import com.github.ecsoya.sword.generator.util.GenUtils;
import com.github.ecsoya.sword.generator.util.VelocityInitializer;
import com.github.ecsoya.sword.generator.util.VelocityUtils;

/**
 * The Class GenTableServiceImpl.
 */
@Service
@SuppressWarnings("deprecation")
public class GenTableServiceImpl implements IGenTableService {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(GenTableServiceImpl.class);

	/** The gen table mapper. */
	@Autowired
	private GenTableMapper genTableMapper;

	/** The gen table column mapper. */
	@Autowired
	private GenTableColumnMapper genTableColumnMapper;

	/**
	 * Select gen table by id.
	 *
	 * @param id the id
	 * @return the gen table
	 */
	@Override
	public GenTable selectGenTableById(Long id) {
		final GenTable genTable = genTableMapper.selectGenTableById(id);
		setTableFromOptions(genTable);
		return genTable;
	}

	/**
	 * Select gen table list.
	 *
	 * @param genTable the gen table
	 * @return the list
	 */
	@Override
	public List<GenTable> selectGenTableList(GenTable genTable) {
		return genTableMapper.selectGenTableList(genTable);
	}

	/**
	 * Select db table list.
	 *
	 * @param genTable the gen table
	 * @return the list
	 */
	@Override
	public List<GenTable> selectDbTableList(GenTable genTable) {
		return genTableMapper.selectDbTableList(genTable);
	}

	/**
	 * Select db table list by names.
	 *
	 * @param tableNames the table names
	 * @return the list
	 */
	@Override
	public List<GenTable> selectDbTableListByNames(String[] tableNames) {
		return genTableMapper.selectDbTableListByNames(tableNames);
	}

	/**
	 * Select gen table all.
	 *
	 * @return the list
	 */
	@Override
	public List<GenTable> selectGenTableAll() {
		return genTableMapper.selectGenTableAll();
	}

	/**
	 * Update gen table.
	 *
	 * @param genTable the gen table
	 */
	@Override
	@Transactional
	public void updateGenTable(GenTable genTable) {
		final String options = JSON.toJSONString(genTable.getParams());
		genTable.setOptions(options);
		final int row = genTableMapper.updateGenTable(genTable);
		if (row > 0) {
			for (final GenTableColumn cenTableColumn : genTable.getColumns()) {
				genTableColumnMapper.updateGenTableColumn(cenTableColumn);
			}
		}
	}

	/**
	 * Delete gen table by ids.
	 *
	 * @param ids the ids
	 */
	@Override
	@Transactional
	public void deleteGenTableByIds(String ids) {
		genTableMapper.deleteGenTableByIds(Convert.toLongArray(ids));
		genTableColumnMapper.deleteGenTableColumnByIds(Convert.toLongArray(ids));
	}

	/**
	 * Import gen table.
	 *
	 * @param tableList the table list
	 * @param operName  the oper name
	 */
	@Override
	@Transactional
	public void importGenTable(List<GenTable> tableList, String operName) {
		try {
			for (final GenTable table : tableList) {
				final String tableName = table.getTableName();
				GenUtils.initTable(table, operName);
				final int row = genTableMapper.insertGenTable(table);
				if (row > 0) {
					// ???????????????
					final List<GenTableColumn> genTableColumns = genTableColumnMapper
							.selectDbTableColumnsByName(tableName);
					for (final GenTableColumn column : genTableColumns) {
						GenUtils.initColumnField(column, table);
						genTableColumnMapper.insertGenTableColumn(column);
					}
				}
			}
		} catch (final Exception e) {
			throw new BusinessException("???????????????" + e.getMessage());
		}
	}

	/**
	 * Preview code.
	 *
	 * @param tableId the table id
	 * @return the map
	 */
	@Override
	public Map<String, String> previewCode(Long tableId) {
		final Map<String, String> dataMap = new LinkedHashMap<>();
		// ???????????????
		final GenTable table = genTableMapper.selectGenTableById(tableId);
		// ?????????????????????
		setSubTable(table);
		// ?????????????????????
		setPkColumn(table);
		VelocityInitializer.initVelocity();

		final VelocityContext context = VelocityUtils.prepareContext(table);

		// ??????????????????
		final List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
		for (final String template : templates) {
			// ????????????
			final StringWriter sw = new StringWriter();
			final Template tpl = Velocity.getTemplate(template, Constants.UTF8);
			tpl.merge(context, sw);
			dataMap.put(template, sw.toString());
		}
		return dataMap;
	}

	/**
	 * Download code.
	 *
	 * @param tableName the table name
	 * @return the byte[]
	 */
	@Override
	public byte[] downloadCode(String tableName) {
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		final ZipOutputStream zip = new ZipOutputStream(outputStream);
		generatorCode(tableName, zip);
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}

	/**
	 * Generator code.
	 *
	 * @param tableName the table name
	 */
	@Override
	public void generatorCode(String tableName) {
		// ???????????????
		final GenTable table = genTableMapper.selectGenTableByName(tableName);
		// ?????????????????????
		setSubTable(table);
		// ?????????????????????
		setPkColumn(table);

		VelocityInitializer.initVelocity();

		final VelocityContext context = VelocityUtils.prepareContext(table);

		// ??????????????????
		final List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
		for (final String template : templates) {
			if (!org.apache.commons.lang3.StringUtils.contains(template, "sql.vm")) {
				// ????????????
				final StringWriter sw = new StringWriter();
				final Template tpl = Velocity.getTemplate(template, Constants.UTF8);
				tpl.merge(context, sw);
				try {
					final String path = getGenPath(table, template);
					org.apache.commons.io.FileUtils.writeStringToFile(new File(path), sw.toString(), CharsetKit.UTF_8);
				} catch (final IOException e) {
					throw new BusinessException("??????????????????????????????" + table.getTableName());
				}
			}
		}
	}

	/**
	 * Synch db.
	 *
	 * @param tableName the table name
	 */
	@Override
	@Transactional
	public void synchDb(String tableName) {
		final GenTable table = genTableMapper.selectGenTableByName(tableName);
		final List<GenTableColumn> tableColumns = table.getColumns();
		final List<String> tableColumnNames = tableColumns.stream().map(GenTableColumn::getColumnName)
				.collect(Collectors.toList());

		final List<GenTableColumn> dbTableColumns = genTableColumnMapper.selectDbTableColumnsByName(tableName);
		if (StringUtils.isEmpty(dbTableColumns)) {
			throw new BusinessException("??????????????????????????????????????????");
		}
		final List<String> dbTableColumnNames = dbTableColumns.stream().map(GenTableColumn::getColumnName)
				.collect(Collectors.toList());

		dbTableColumns.forEach(column -> {
			if (!tableColumnNames.contains(column.getColumnName())) {
				GenUtils.initColumnField(column, table);
				genTableColumnMapper.insertGenTableColumn(column);
			}
		});

		final List<GenTableColumn> delColumns = tableColumns.stream()
				.filter(column -> !dbTableColumnNames.contains(column.getColumnName())).collect(Collectors.toList());
		if (StringUtils.isNotEmpty(delColumns)) {
			genTableColumnMapper.deleteGenTableColumns(delColumns);
		}
	}

	/**
	 * Download code.
	 *
	 * @param tableNames the table names
	 * @return the byte[]
	 */
	@Override
	public byte[] downloadCode(String[] tableNames) {
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		final ZipOutputStream zip = new ZipOutputStream(outputStream);
		for (final String tableName : tableNames) {
			generatorCode(tableName, zip);
		}
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}

	/**
	 * Generator code.
	 *
	 * @param tableName the table name
	 * @param zip       the zip
	 */
	private void generatorCode(String tableName, ZipOutputStream zip) {
		// ???????????????
		final GenTable table = genTableMapper.selectGenTableByName(tableName);
		// ?????????????????????
		setSubTable(table);
		// ?????????????????????
		setPkColumn(table);

		VelocityInitializer.initVelocity();

		final VelocityContext context = VelocityUtils.prepareContext(table);

		// ??????????????????
		final List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
		for (final String template : templates) {
			// ????????????
			final StringWriter sw = new StringWriter();
			final Template tpl = Velocity.getTemplate(template, Constants.UTF8);
			tpl.merge(context, sw);
			try {
				// ?????????zip
				zip.putNextEntry(new ZipEntry(VelocityUtils.getFileName(template, table)));
				IOUtils.write(sw.toString(), zip, Constants.UTF8);
				IOUtils.closeQuietly(sw);
				zip.flush();
				zip.closeEntry();
			} catch (final IOException e) {
				log.error("??????????????????????????????" + table.getTableName(), e);
			}
		}
	}

	/**
	 * Validate edit.
	 *
	 * @param genTable the gen table
	 */
	@Override
	public void validateEdit(GenTable genTable) {
		if (GenConstants.TPL_TREE.equals(genTable.getTplCategory())) {
			final String options = JSON.toJSONString(genTable.getParams());
			final JSONObject paramsObj = JSON.parseObject(options);
			if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_CODE))) {
				throw new BusinessException("???????????????????????????");
			} else if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_PARENT_CODE))) {
				throw new BusinessException("??????????????????????????????");
			} else if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_NAME))) {
				throw new BusinessException("???????????????????????????");
			}
		} else if (GenConstants.TPL_SUB.equals(genTable.getTplCategory())) {
			if (StringUtils.isEmpty(genTable.getSubTableName())) {
				throw new BusinessException("?????????????????????????????????");
			} else if (StringUtils.isEmpty(genTable.getSubTableFkName())) {
				throw new BusinessException("????????????????????????????????????");
			}
		}
	}

	/**
	 * Sets the pk column.
	 *
	 * @param table the new pk column
	 */
	public void setPkColumn(GenTable table) {
		for (final GenTableColumn column : table.getColumns()) {
			if (column.isPk()) {
				table.setPkColumn(column);
				break;
			}
		}
		if (StringUtils.isNull(table.getPkColumn())) {
			table.setPkColumn(table.getColumns().get(0));
		}
		if (GenConstants.TPL_SUB.equals(table.getTplCategory())) {
			for (final GenTableColumn column : table.getSubTable().getColumns()) {
				if (column.isPk()) {
					table.getSubTable().setPkColumn(column);
					break;
				}
			}
			if (StringUtils.isNull(table.getSubTable().getPkColumn())) {
				table.getSubTable().setPkColumn(table.getSubTable().getColumns().get(0));
			}
		}
	}

	/**
	 * Sets the sub table.
	 *
	 * @param table the new sub table
	 */
	public void setSubTable(GenTable table) {
		final String subTableName = table.getSubTableName();
		if (StringUtils.isNotEmpty(subTableName)) {
			table.setSubTable(genTableMapper.selectGenTableByName(subTableName));
		}
	}

	/**
	 * Sets the table from options.
	 *
	 * @param genTable the new table from options
	 */
	public void setTableFromOptions(GenTable genTable) {
		final JSONObject paramsObj = JSON.parseObject(genTable.getOptions());
		if (StringUtils.isNotNull(paramsObj)) {
			final String treeCode = paramsObj.getString(GenConstants.TREE_CODE);
			final String treeParentCode = paramsObj.getString(GenConstants.TREE_PARENT_CODE);
			final String treeName = paramsObj.getString(GenConstants.TREE_NAME);
			final String parentMenuId = paramsObj.getString(GenConstants.PARENT_MENU_ID);
			final String parentMenuName = paramsObj.getString(GenConstants.PARENT_MENU_NAME);

			genTable.setTreeCode(treeCode);
			genTable.setTreeParentCode(treeParentCode);
			genTable.setTreeName(treeName);
			genTable.setParentMenuId(parentMenuId);
			genTable.setParentMenuName(parentMenuName);
		}
	}

	/**
	 * Gets the gen path.
	 *
	 * @param table    the table
	 * @param template the template
	 * @return the gen path
	 */
	public static String getGenPath(GenTable table, String template) {
		final String genPath = table.getGenPath();
		if (org.apache.commons.lang3.StringUtils.equals(genPath, "/")) {
			return System.getProperty("user.dir") + File.separator + "src" + File.separator
					+ VelocityUtils.getFileName(template, table);
		}
		return genPath + File.separator + VelocityUtils.getFileName(template, table);
	}
}