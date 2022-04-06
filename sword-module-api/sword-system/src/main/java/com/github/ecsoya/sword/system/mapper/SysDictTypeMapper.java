package com.github.ecsoya.sword.system.mapper;

import java.util.List;

import com.github.ecsoya.sword.common.core.domain.entity.SysDictType;

/**
 * The Interface SysDictTypeMapper.
 */
public interface SysDictTypeMapper {

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
	 * Delete dict type by id.
	 *
	 * @param dictId the dict id
	 * @return the int
	 */
	public int deleteDictTypeById(Long dictId);

	/**
	 * Delete dict type by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteDictTypeByIds(Long[] ids);

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
	 * @return the sys dict type
	 */
	public SysDictType checkDictTypeUnique(String dictType);
}
