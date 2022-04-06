package com.github.ecsoya.sword.task.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.IdWorker;
import com.github.ecsoya.sword.task.domain.SwordTaskExample;
import com.github.ecsoya.sword.task.mapper.SwordTaskExampleMapper;
import com.github.ecsoya.sword.task.service.ISwordTaskExampleService;

/**
 * The Class SwordTaskExampleServiceImpl.
 */
@Service
public class SwordTaskExampleServiceImpl implements ISwordTaskExampleService {

	/** The sword task example mapper. */
	@Autowired
	private SwordTaskExampleMapper swordTaskExampleMapper;

	/**
	 * Select sword task example by id.
	 *
	 * @param id the id
	 * @return the sword task example
	 */
	@Override
	public SwordTaskExample selectSwordTaskExampleById(Long id) {
		return swordTaskExampleMapper.selectSwordTaskExampleById(id);
	}

	/**
	 * Select sword task example list.
	 *
	 * @param swordTaskExample the sword task example
	 * @return the list
	 */
	@Override
	public List<SwordTaskExample> selectSwordTaskExampleList(SwordTaskExample swordTaskExample) {
		return swordTaskExampleMapper.selectSwordTaskExampleList(swordTaskExample);
	}

	/**
	 * Insert sword task example.
	 *
	 * @param swordTaskExample the sword task example
	 * @return the int
	 */
	@Override
	public int insertSwordTaskExample(SwordTaskExample swordTaskExample) {
		if (swordTaskExample.getId() == null) {
			swordTaskExample.setId(IdWorker.getId());
		}
		if (swordTaskExample.getCreateTime() == null) {
			swordTaskExample.setCreateTime(DateUtils.getNowDate());
		}
		return swordTaskExampleMapper.insertSwordTaskExample(swordTaskExample);
	}

	/**
	 * Update sword task example.
	 *
	 * @param swordTaskExample the sword task example
	 * @return the int
	 */
	@Override
	public int updateSwordTaskExample(SwordTaskExample swordTaskExample) {
		swordTaskExample.setUpdateTime(DateUtils.getNowDate());
		return swordTaskExampleMapper.updateSwordTaskExample(swordTaskExample);
	}

	/**
	 * Delete sword task example by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteSwordTaskExampleByIds(String ids) {
		return swordTaskExampleMapper.deleteSwordTaskExampleByIds(Convert.toStrArray(ids));
	}

	/**
	 * Delete sword task example by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Override
	public int deleteSwordTaskExampleById(Long id) {
		return swordTaskExampleMapper.deleteSwordTaskExampleById(id);
	}

	/**
	 * Select last sword task example.
	 *
	 * @return the sword task example
	 */
	@Override
	public SwordTaskExample selectLastSwordTaskExample() {
		return swordTaskExampleMapper.selectLastSwordTaskExample();
	}
}
