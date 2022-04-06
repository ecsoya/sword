package com.github.ecsoya.sword.framework.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.core.domain.entity.SysDictData;
import com.github.ecsoya.sword.system.service.ISysDictDataService;
import com.github.ecsoya.sword.system.service.ISysDictTypeService;

/**
 * The Class DictService.
 */
@Service("dict")
public class DictService {

	/** The dict type service. */
	@Autowired
	private ISysDictTypeService dictTypeService;

	/** The dict data service. */
	@Autowired
	private ISysDictDataService dictDataService;

	/**
	 * Gets the type.
	 *
	 * @param dictType the dict type
	 * @return the type
	 */
	public List<SysDictData> getType(String dictType) {
		return dictTypeService.selectDictDataByType(dictType);
	}

	/**
	 * Gets the label.
	 *
	 * @param dictType  the dict type
	 * @param dictValue the dict value
	 * @return the label
	 */
	public String getLabel(String dictType, String dictValue) {
		return dictDataService.selectDictLabel(dictType, dictValue);
	}
}
