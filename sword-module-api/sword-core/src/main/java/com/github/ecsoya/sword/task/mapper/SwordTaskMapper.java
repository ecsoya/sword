package com.github.ecsoya.sword.task.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.ecsoya.sword.task.domain.SwordTask;

/**
 * 定时任务执行结果Mapper接口
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
public interface SwordTaskMapper {
	/**
	 * 查询定时任务执行结果
	 *
	 * @param id 定时任务执行结果ID
	 * @return 定时任务执行结果
	 */
	public SwordTask selectSwordTaskById(Long id);

	/**
	 * 查询定时任务执行结果列表
	 *
	 * @param swordTask 定时任务执行结果
	 * @return 定时任务执行结果集合
	 */
	public List<SwordTask> selectSwordTaskList(SwordTask swordTask);

	/**
	 * 新增定时任务执行结果
	 *
	 * @param swordTask 定时任务执行结果
	 * @return 结果
	 */
	public int insertSwordTask(SwordTask swordTask);

	/**
	 * 修改定时任务执行结果
	 *
	 * @param swordTask 定时任务执行结果
	 * @return 结果
	 */
	public int updateSwordTask(SwordTask swordTask);

	/**
	 * 删除定时任务执行结果
	 *
	 * @param id 定时任务执行结果ID
	 * @return 结果
	 */
	public int deleteSwordTaskById(Long id);

	/**
	 * 批量删除定时任务执行结果
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteSwordTaskByIds(String[] ids);

	public SwordTask selectSwordTaskByName(@Param("date") Date date, @Param("name") String name);
}
