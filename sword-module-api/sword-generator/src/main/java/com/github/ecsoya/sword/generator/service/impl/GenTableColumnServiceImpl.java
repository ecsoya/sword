package com.github.ecsoya.sword.generator.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.generator.domain.GenTableColumn;
import com.github.ecsoya.sword.generator.mapper.GenTableColumnMapper;
import com.github.ecsoya.sword.generator.service.IGenTableColumnService;

/**
 * The Class GenTableColumnServiceImpl.
 */
@Service
public class GenTableColumnServiceImpl implements IGenTableColumnService {

	/** The gen table column mapper. */
	@Autowired
	private GenTableColumnMapper genTableColumnMapper;

	/**
	 * Select gen table column list by table id.
	 *
	 * @param genTableColumn the gen table column
	 * @return the list
	 */
	@Override
	public List<GenTableColumn> selectGenTableColumnListByTableId(GenTableColumn genTableColumn) {
		return genTableColumnMapper.selectGenTableColumnListByTableId(genTableColumn);
	}

	/**
	 * Insert gen table column.
	 *
	 * @param genTableColumn the gen table column
	 * @return the int
	 */
	@Override
	public int insertGenTableColumn(GenTableColumn genTableColumn) {
		return genTableColumnMapper.insertGenTableColumn(genTableColumn);
	}

	/**
	 * Update gen table column.
	 *
	 * @param genTableColumn the gen table column
	 * @return the int
	 */
	@Override
	public int updateGenTableColumn(GenTableColumn genTableColumn) {
		return genTableColumnMapper.updateGenTableColumn(genTableColumn);
	}

	/**
	 * Delete gen table column by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteGenTableColumnByIds(String ids) {
		return genTableColumnMapper.deleteGenTableColumnByIds(Convert.toLongArray(ids));
	}
}