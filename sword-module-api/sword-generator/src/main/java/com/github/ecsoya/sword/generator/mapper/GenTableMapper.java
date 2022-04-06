package com.github.ecsoya.sword.generator.mapper;

import java.util.List;

import com.github.ecsoya.sword.generator.domain.GenTable;

/**
 * The Interface GenTableMapper.
 */
public interface GenTableMapper {

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
	 * Select gen table by name.
	 *
	 * @param tableName the table name
	 * @return the gen table
	 */
	public GenTable selectGenTableByName(String tableName);

	/**
	 * Insert gen table.
	 *
	 * @param genTable the gen table
	 * @return the int
	 */
	public int insertGenTable(GenTable genTable);

	/**
	 * Update gen table.
	 *
	 * @param genTable the gen table
	 * @return the int
	 */
	public int updateGenTable(GenTable genTable);

	/**
	 * Delete gen table by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteGenTableByIds(Long[] ids);
}