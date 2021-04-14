package com.soyatec.sword.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soyatec.sword.common.constant.UserConstants;
import com.soyatec.sword.common.core.domain.Ztree;
import com.soyatec.sword.common.core.domain.entity.SysDictData;
import com.soyatec.sword.common.core.domain.entity.SysDictType;
import com.soyatec.sword.common.core.text.Convert;
import com.soyatec.sword.common.exception.BusinessException;
import com.soyatec.sword.common.utils.DictUtils;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.system.mapper.SysDictDataMapper;
import com.soyatec.sword.system.mapper.SysDictTypeMapper;
import com.soyatec.sword.system.service.ISysDictTypeService;

/**
 * 字典 业务层处理
 *
 * @author Jin Liu (angryred@qq.com)
 */
@Service
public class SysDictTypeServiceImpl implements ISysDictTypeService {
	@Autowired
	private SysDictTypeMapper dictTypeMapper;

	@Autowired
	private SysDictDataMapper dictDataMapper;

	/**
	 * 项目启动时，初始化字典到缓存
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
	 * 根据条件分页查询字典类型
	 *
	 * @param dictType 字典类型信息
	 * @return 字典类型集合信息
	 */
	@Override
	public List<SysDictType> selectDictTypeList(SysDictType dictType) {
		return dictTypeMapper.selectDictTypeList(dictType);
	}

	/**
	 * 根据所有字典类型
	 *
	 * @return 字典类型集合信息
	 */
	@Override
	public List<SysDictType> selectDictTypeAll() {
		return dictTypeMapper.selectDictTypeAll();
	}

	/**
	 * 根据字典类型查询字典数据
	 *
	 * @param dictType 字典类型
	 * @return 字典数据集合信息
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
	 * 根据字典类型ID查询信息
	 *
	 * @param dictId 字典类型ID
	 * @return 字典类型
	 */
	@Override
	public SysDictType selectDictTypeById(Long dictId) {
		return dictTypeMapper.selectDictTypeById(dictId);
	}

	/**
	 * 根据字典类型查询信息
	 *
	 * @param dictType 字典类型
	 * @return 字典类型
	 */
	@Override
	public SysDictType selectDictTypeByType(String dictType) {
		return dictTypeMapper.selectDictTypeByType(dictType);
	}

	/**
	 * 批量删除字典类型
	 *
	 * @param ids 需要删除的数据
	 * @return 结果
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
	 * 清空缓存数据
	 */
	@Override
	public void clearCache() {
		DictUtils.clearDictCache();
	}

	/**
	 * 新增保存字典类型信息
	 *
	 * @param dictType 字典类型信息
	 * @return 结果
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
	 * 修改保存字典类型信息
	 *
	 * @param dictType 字典类型信息
	 * @return 结果
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
	 * 校验字典类型称是否唯一
	 *
	 * @param dict 字典类型
	 * @return 结果
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
	 * 查询字典类型树
	 *
	 * @param dictType 字典类型
	 * @return 所有字典类型
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

	public String transDictName(SysDictType dictType) {
		final StringBuffer sb = new StringBuffer();
		sb.append("(" + dictType.getDictName() + ")");
		sb.append("&nbsp;&nbsp;&nbsp;" + dictType.getDictType());
		return sb.toString();
	}
}
