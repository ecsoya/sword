package com.github.ecsoya.sword.generator.service;

import java.util.List;
import java.util.Map;

import com.github.ecsoya.sword.generator.domain.GenTable;

/**
 * The Interface IGenTableService.
 */
public interface IGenTableService {

	/**
	 * Select gen table list.
	 *
	 * @param genTable the gen table
	 * @return the list
	 */
	public List<GenTable> selectGenTableList(GenTable genTable);

	/**
	 * Select db table list.
	 *
	 * @param genTable the gen table
	 * @return the list
	 */
	public List<GenTable> selectDbTableList(GenTable genTable);

	/**
	 * Select db table list by names.
	 *
	 * @param tableNames the table names
	 * @return the list
	 */
	public List<GenTable> selectDbTableListByNames(String[] tableNames);

	/**
	 * Select gen table all.
	 *
	 * @return the list
	 */
	public List<GenTable> selectGenTableAll();

	/**
	 * Select gen table by id.
	 *
	 * @param id the id
	 * @return the gen table
	 */
	public GenTable selectGenTableById(Long id);

	/**
	 * Update gen table.
	 *
	 * @param genTable the gen table
	 */
	public void updateGenTable(GenTable genTable);

	/**
	 * Delete gen table by ids.
	 *
	 * @param ids the ids
	 */
	public void deleteGenTableByIds(String ids);

	/**
	 * Import gen table.
	 *
	 * @param tableList the table list
	 * @param operName  the oper name
	 */
	public void importGenTable(List<GenTable> tableList, String operName);

	/**
	 * Preview code.
	 *
	 * @param tableId the table id
	 * @return the map
	 */
	public Map<String, String> previewCode(Long tableId);

	/**
	 * Download code.
	 *
	 * @param tableName the table name
	 * @return the byte[]
	 */
	public byte[] downloadCode(String tableName);

	/**
	 * Generator code.
	 *
	 * @param tableName the table name
	 */
	public void generatorCode(String tableName);

	/**
	 * Synch db.
	 *
	 * @param tableName the table name
	 */
	public void synchDb(String tableName);

	/**
	 * Download code.
	 *
	 * @param tableNames the table names
	 * @return the byte[]
	 */
	public byte[] downloadCode(String[] tableNames);

	/**
	 * Validate edit.
	 *
	 * @param genTable the gen table
	 */
	public void validateEdit(GenTable genTable);
}
