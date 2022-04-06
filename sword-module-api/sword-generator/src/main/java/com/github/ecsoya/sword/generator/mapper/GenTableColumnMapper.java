package com.github.ecsoya.sword.generator.mapper;

import java.util.List;

import com.github.ecsoya.sword.generator.domain.GenTableColumn;

/**
 * The Interface GenTableColumnMapper.
 */
public interface GenTableColumnMapper {

	/**
	 * Select db table columns by name.
	 *
	 * @param tableName the table name
	 * @return the list
	 */
	public List<GenTableColumn> selectDbTableColumnsByName(String tableName);

	/**
	 * Select gen table column list by table id.
	 *
	 * @param genTableColumn the gen table column
	 * @return the list
	 */
	public List<GenTableColumn> selectGenTableColumnListByTableId(GenTableColumn genTableColumn);

	/**
	 * Insert gen table column.
	 *
	 * @param genTableColumn the gen table column
	 * @return the int
	 */
	public int insertGenTableColumn(GenTableColumn genTableColumn);

	/**
	 * Update gen table column.
	 *
	 * @param genTableColumn the gen table column
	 * @return the int
	 */
	public int updateGenTableColumn(GenTableColumn genTableColumn);

	/**
	 * Delete gen table columns.
	 *
	 * @param genTableColumns the gen table columns
	 * @return the int
	 */
	public int deleteGenTableColumns(List<GenTableColumn> genTableColumns);

	/**
	 * Delete gen table column by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteGenTableColumnByIds(Long[] ids);
}
