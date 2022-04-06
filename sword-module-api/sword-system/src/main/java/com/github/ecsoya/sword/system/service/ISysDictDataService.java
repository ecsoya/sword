package com.github.ecsoya.sword.system.service;

import java.util.List;

import com.github.ecsoya.sword.common.core.domain.entity.SysDictData;

/**
 * The Interface ISysDictDataService.
 */
public interface ISysDictDataService {

	/**
	 * Select dict data list.
	 *
	 * @param dictData the dict data
	 * @return the list
	 */
	public List<SysDictData> selectDictDataList(SysDictData dictData);

	/**
	 * Select dict label.
	 *
	 * @param dictType  the dict type
	 * @param dictValue the dict value
	 * @return the string
	 */
	public String selectDictLabel(String dictType, String dictValue);

	/**
	 * Select dict data by id.
	 *
	 * @param dictCode the dict code
	 * @return the sys dict data
	 */
	public SysDictData selectDictDataById(Long dictCode);

	/**
	 * Delete dict data by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteDictDataByIds(String ids);

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
}
