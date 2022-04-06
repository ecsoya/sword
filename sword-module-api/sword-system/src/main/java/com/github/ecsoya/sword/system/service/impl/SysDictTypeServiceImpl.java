package com.github.ecsoya.sword.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.ecsoya.sword.common.constant.UserConstants;
import com.github.ecsoya.sword.common.core.domain.Ztree;
import com.github.ecsoya.sword.common.core.domain.entity.SysDictData;
import com.github.ecsoya.sword.common.core.domain.entity.SysDictType;
import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.exception.BusinessException;
import com.github.ecsoya.sword.common.utils.DictUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.system.mapper.SysDictDataMapper;
import com.github.ecsoya.sword.system.mapper.SysDictTypeMapper;
import com.github.ecsoya.sword.system.service.ISysDictTypeService;

/**
 * The Class SysDictTypeServiceImpl.
 */
@Service
public class SysDictTypeServiceImpl implements ISysDictTypeService {

	/** The dict type mapper. */
	@Autowired
	private SysDictTypeMapper dictTypeMapper;

	/** The dict data mapper. */
	@Autowired
	private SysDictDataMapper dictDataMapper;

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		final List<SysDictType> dictTypeList = dictTypeMapper.selectDictTypeAll();
		for (final SysDictType dictType : dictTypeList) {
			final List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(dictType.getDictType());
			DictUtils.setDictCache(dictType.getDictType(), dictDatas);
		}
	}

	/**
	 * Select dict type list.
	 *
	 * @param dictType the dict type
	 * @return the list
	 */
	@Override
	public List<SysDictType> selectDictTypeList(SysDictType dictType) {
		return dictTypeMapper.selectDictTypeList(dictType);
	}

	/**
	 * Select dict type all.
	 *
	 * @return the list
	 */
	@Override
	public List<SysDictType> selectDictTypeAll() {
		return dictTypeMapper.selectDictTypeAll();
	}

	/**
	 * Select dict data by type.
	 *
	 * @param dictType the dict type
	 * @return the list
	 */
	@Override
	public List<SysDictData> selectDictDataByType(String dictType) {
		List<SysDictData> dictDatas = DictUtils.getDictCache(dictType);
		if (StringUtils.isNotEmpty(dictDatas)) {
			return dictDatas;
		}
		dictDatas = dictDataMapper.selectDictDataByType(dictType);
		if (StringUtils.isNotEmpty(dictDatas)) {
			DictUtils.setDictCache(dictType, dictDatas);
			return dictDatas;
		}
		return null;
	}

	/**
	 * Select dict type by id.
	 *
	 * @param dictId the dict id
	 * @return the sys dict type
	 */
	@Override
	public SysDictType selectDictTypeById(Long dictId) {
		return dictTypeMapper.selectDictTypeById(dictId);
	}

	/**
	 * Select dict type by type.
	 *
	 * @param dictType the dict type
	 * @return the sys dict type
	 */
	@Override
	public SysDictType selectDictTypeByType(String dictType) {
		return dictTypeMapper.selectDictTypeByType(dictType);
	}

	/**
	 * Delete dict type by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteDictTypeByIds(String ids) {
		final Long[] dictIds = Convert.toLongArray(ids);
		for (final Long dictId : dictIds) {
			final SysDictType dictType = selectDictTypeById(dictId);
			if (dictDataMapper.countDictDataByType(dictType.getDictType()) > 0) {
				throw new BusinessException(String.format("%1$s已分配,不能删除", dictType.getDictName()));
			}
		}
		final int count = dictTypeMapper.deleteDictTypeByIds(dictIds);
		if (count > 0) {
			DictUtils.clearDictCache();
		}
		return count;
	}

	/**
	 * Clear cache.
	 */
	@Override
	public void clearCache() {
		DictUtils.clearDictCache();
	}

	/**
	 * Insert dict type.
	 *
	 * @param dictType the dict type
	 * @return the int
	 */
	@Override
	public int insertDictType(SysDictType dictType) {
		final int row = dictTypeMapper.insertDictType(dictType);
		if (row > 0) {
			DictUtils.clearDictCache();
		}
		return row;
	}

	/**
	 * Update dict type.
	 *
	 * @param dictType the dict type
	 * @return the int
	 */
	@Override
	@Transactional
	public int updateDictType(SysDictType dictType) {
		final SysDictType oldDict = dictTypeMapper.selectDictTypeById(dictType.getDictId());
		dictDataMapper.updateDictDataType(oldDict.getDictType(), dictType.getDictType());
		final int row = dictTypeMapper.updateDictType(dictType);
		if (row > 0) {
			DictUtils.clearDictCache();
		}
		return row;
	}

	/**
	 * Check dict type unique.
	 *
	 * @param dict the dict
	 * @return the string
	 */
	@Override
	public String checkDictTypeUnique(SysDictType dict) {
		final Long dictId = StringUtils.isNull(dict.getDictId()) ? -1L : dict.getDictId();
		final SysDictType dictType = dictTypeMapper.checkDictTypeUnique(dict.getDictType());
		if (StringUtils.isNotNull(dictType) && dictType.getDictId().longValue() != dictId.longValue()) {
			return UserConstants.DICT_TYPE_NOT_UNIQUE;
		}
		return UserConstants.DICT_TYPE_UNIQUE;
	}

	/**
	 * Select dict tree.
	 *
	 * @param dictType the dict type
	 * @return the list
	 */
	@Override
	public List<Ztree> selectDictTree(SysDictType dictType) {
		final List<Ztree> ztrees = new ArrayList<Ztree>();
		final List<SysDictType> dictList = dictTypeMapper.selectDictTypeList(dictType);
		for (final SysDictType dict : dictList) {
			if (UserConstants.DICT_NORMAL.equals(dict.getStatus())) {
				final Ztree ztree = new Ztree();
				ztree.setId(dict.getDictId());
				ztree.setName(transDictName(dict));
				ztree.setTitle(dict.getDictType());
				ztrees.add(ztree);
			}
		}
		return ztrees;
	}

	/**
	 * Trans dict name.
	 *
	 * @param dictType the dict type
	 * @return the string
	 */
	public String transDictName(SysDictType dictType) {
		final StringBuffer sb = new StringBuffer();
		sb.append("(" + dictType.getDictName() + ")");
		sb.append("&nbsp;&nbsp;&nbsp;" + dictType.getDictType());
		return sb.toString();
	}
}
