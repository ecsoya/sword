package com.github.ecsoya.sword.system.service;

import java.util.List;

import com.github.ecsoya.sword.common.core.domain.Ztree;
import com.github.ecsoya.sword.common.core.domain.entity.SysDictData;
import com.github.ecsoya.sword.common.core.domain.entity.SysDictType;

/**
 * The Interface ISysDictTypeService.
 */
public interface ISysDictTypeService {

	/**
	 * Select dict type list.
	 *
	 * @param dictType the dict type
	 * @return the list
	 */
	public List<SysDictType> selectDictTypeList(SysDictType dictType);

	/**
	 * Select dict type all.
	 *
	 * @return the list
	 */
	public List<SysDictType> selectDictTypeAll();

	/**
	 * Select dict data by type.
	 *
	 * @param dictType the dict type
	 * @return the list
	 */
	public List<SysDictData> selectDictDataByType(String dictType);

	/**
	 * Select dict type by id.
	 *
	 * @param dictId the dict id
	 * @return the sys dict type
	 */
	public SysDictType selectDictTypeById(Long dictId);

	/**
	 * Select dict type by type.
	 *
	 * @param dictType the dict type
	 * @return the sys dict type
	 */
	public SysDictType selectDictTypeByType(String dictType);

	/**
	 * Delete dict type by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteDictTypeByIds(String ids);

	/**
	 * Clear cache.
	 */
	public void clearCache();

	/**
	 * Insert dict type.
	 *
	 * @param dictType the dict type
	 * @return the int
	 */
	public int insertDictType(SysDictType dictType);

	/**
	 * Update dict type.
	 *
	 * @param dictType the dict type
	 * @return the int
	 */
	public int updateDictType(SysDictType dictType);

	/**
	 * Check dict type unique.
	 *
	 * @param dictType the dict type
	 * @return the string
	 */
	public String checkDictTypeUnique(SysDictType dictType);

	/**
	 * Select dict tree.
	 *
	 * @param dictType the dict type
	 * @return the list
	 */
	public List<Ztree> selectDictTree(SysDictType dictType);
}
