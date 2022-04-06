package com.github.ecsoya.sword.generator.service;

import java.util.List;

import com.github.ecsoya.sword.generator.domain.GenTableColumn;

/**
 * The Interface IGenTableColumnService.
 */
public interface IGenTableColumnService {

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
	 * Delete gen table column by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteGenTableColumnByIds(String ids);
}
