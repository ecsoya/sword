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
 * 定时任务执行结果Service业务层处理
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-18
 */
@Service
public class SwordTaskExampleServiceImpl implements ISwordTaskExampleService {
	@Autowired
	private SwordTaskExampleMapper swordTaskExampleMapper;

	/**
	 * 查询定时任务执行结果
	 *
	 * @param id 定时任务执行结果ID
	 * @return 定时任务执行结果
	 */
	@Override
	public SwordTaskExample selectSwordTaskExampleById(Long id) {
		return swordTaskExampleMapper.selectSwordTaskExampleById(id);
	}

	/**
	 * 查询定时任务执行结果列表
	 *
	 * @param swordTaskExample 定时任务执行结果
	 * @return 定时任务执行结果
	 */
	@Override
	public List<SwordTaskExample> selectSwordTaskExampleList(SwordTaskExample swordTaskExample) {
		return swordTaskExampleMapper.selectSwordTaskExampleList(swordTaskExample);
	}

	/**
	 * 新增定时任务执行结果
	 *
	 * @param swordTaskExample 定时任务执行结果
	 * @return 结果
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
	 * 修改定时任务执行结果
	 *
	 * @param swordTaskExample 定时任务执行结果
	 * @return 结果
	 */
	@Override
	public int updateSwordTaskExample(SwordTaskExample swordTaskExample) {
		swordTaskExample.setUpdateTime(DateUtils.getNowDate());
		return swordTaskExampleMapper.updateSwordTaskExample(swordTaskExample);
	}

	/**
	 * 删除定时任务执行结果对象
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteSwordTaskExampleByIds(String ids) {
		return swordTaskExampleMapper.deleteSwordTaskExampleByIds(Convert.toStrArray(ids));
	}

	/**
	 * 删除定时任务执行结果信息
	 *
	 * @param id 定时任务执行结果ID
	 * @return 结果
	 */
	@Override
	public int deleteSwordTaskExampleById(Long id) {
		return swordTaskExampleMapper.deleteSwordTaskExampleById(id);
	}

	@Override
	public SwordTaskExample selectLastSwordTaskExample() {
		return swordTaskExampleMapper.selectLastSwordTaskExample();
	}
}
