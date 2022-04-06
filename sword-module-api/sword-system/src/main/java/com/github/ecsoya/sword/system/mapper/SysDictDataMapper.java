package com.github.ecsoya.sword.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.ecsoya.sword.common.core.domain.entity.SysDictData;

/**
 * The Interface SysDictDataMapper.
 */
public interface SysDictDataMapper {

	/**
	 * Select dict data list.
	 *
	 * @param dictData the dict data
	 * @return the list
	 */
	public List<SysDictData> selectDictDataList(SysDictData dictData);

	/**
	 * Select dict data by type.
	 *
	 * @param dictType the dict type
	 * @return the list
	 */
	public List<SysDictData> selectDictDataByType(String dictType);

	/**
	 * Select dict label.
	 *
	 * @param dictType  the dict type
	 * @param dictValue the dict value
	 * @return the string
	 */
	public String selectDictLabel(@Param("dictType") String dictType, @Param("dictValue") String dictValue);

	/**
	 * Select dict data by id.
	 *
	 * @param dictCode the dict code
	 * @return the sys dict data
	 */
	public SysDictData selectDictDataById(Long dictCode);

	/**
	 * Count dict data by type.
	 *
	 * @param dictType the dict type
	 * @return the int
	 */
	public int countDictDataByType(String dictType);

	/**
	 * Delete dict data by id.
	 *
	 * @param dictCode the dict code
	 * @return the int
	 */
	public int deleteDictDataById(Long dictCode);

	/**
	 * Delete dict data by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteDictDataByIds(String[] ids);

	/**
	 * Insert dict data.
	 *
	 * @param dictData the dict data
	 * @return the int
	 */
	public int insertDictData(SysDictData dictData);

	/**
	 * Update dict data.
	 *
	 * @param dictData the dict data
	 * @return the int
	 */
	public int updateDictData(SysDictData dictData);

	/**
	 * Update dict data type.
	 *
	 * @param oldDictType the old dict type
	 * @param newDictType the new dict type
	 * @return the int
	 */
	public int updateDictDataType(@Param("oldDictType") String oldDictType, @Param("newDictType") String newDictType);
}
