package com.soyatec.sword.task.service;

import java.util.List;

import com.soyatec.sword.task.domain.SwordTaskExample;

/**
 * 定时任务执行结果Service接口
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-18
 */
public interface ISwordTaskExampleService {
	/**
	 * 查询定时任务执行结果
	 * 
	 * @param id 定时任务执行结果ID
	 * @return 定时任务执行结果
	 */
	public SwordTaskExample selectSwordTaskExampleById(Long id);

	/**
	 * 查询定时任务执行结果列表
	 * 
	 * @param swordTaskExample 定时任务执行结果
	 * @return 定时任务执行结果集合
	 */
	public List<SwordTaskExample> selectSwordTaskExampleList(SwordTaskExample swordTaskExample);

	/**
	 * 新增定时任务执行结果
	 * 
	 * @param swordTaskExample 定时任务执行结果
	 * @return 结果
	 */
	public int insertSwordTaskExample(SwordTaskExample swordTaskExample);

	/**
	 * 修改定时任务执行结果
	 * 
	 * @param swordTaskExample 定时任务执行结果
	 * @return 结果
	 */
	public int updateSwordTaskExample(SwordTaskExample swordTaskExample);

	/**
	 * 批量删除定时任务执行结果
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteSwordTaskExampleByIds(String ids);

	/**
	 * 删除定时任务执行结果信息
	 * 
	 * @param id 定时任务执行结果ID
	 * @return 结果
	 */
	public int deleteSwordTaskExampleById(Long id);

	public SwordTaskExample selectLastSwordTaskExample();
}
