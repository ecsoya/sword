package com.github.ecsoya.sword.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.core.domain.entity.SysDictData;
import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.utils.DictUtils;
import com.github.ecsoya.sword.system.mapper.SysDictDataMapper;
import com.github.ecsoya.sword.system.service.ISysDictDataService;

/**
 * The Class SysDictDataServiceImpl.
 */
@Service
public class SysDictDataServiceImpl implements ISysDictDataService {

	/** The dict data mapper. */
	@Autowired
	private SysDictDataMapper dictDataMapper;

	/**
	 * Select dict data list.
	 *
	 * @param dictData the dict data
	 * @return the list
	 */
	@Override
	public List<SysDictData> selectDictDataList(SysDictData dictData) {
		return dictDataMapper.selectDictDataList(dictData);
	}

	/**
	 * Select dict label.
	 *
	 * @param dictType  the dict type
	 * @param dictValue the dict value
	 * @return the string
	 */
	@Override
	public String selectDictLabel(String dictType, String dictValue) {
		return dictDataMapper.selectDictLabel(dictType, dictValue);
	}

	/**
	 * Select dict data by id.
	 *
	 * @param dictCode the dict code
	 * @return the sys dict data
	 */
	@Override
	public SysDictData selectDictDataById(Long dictCode) {
		return dictDataMapper.selectDictDataById(dictCode);
	}

	/**
	 * Delete dict data by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteDictDataByIds(String ids) {
		final int row = dictDataMapper.deleteDictDataByIds(Convert.toStrArray(ids));
		if (row > 0) {
			DictUtils.clearDictCache();
		}
		return row;
	}

	/**
	 * Insert dict data.
	 *
	 * @param dictData the dict data
	 * @return the int
	 */
	@Override
	public int insertDictData(SysDictData dictData) {
		final int row = dictDataMapper.insertDictData(dictData);
		if (row > 0) {
			DictUtils.clearDictCache();
		}
		return row;
	}

	/**
	 * Update dict data.
	 *
	 * @param dictData the dict data
	 * @return the int
	 */
	@Override
	public int updateDictData(SysDictData dictData) {
		final int row = dictDataMapper.updateDictData(dictData);
		if (row > 0) {
			DictUtils.clearDictCache();
		}
		return row;
	}
}
