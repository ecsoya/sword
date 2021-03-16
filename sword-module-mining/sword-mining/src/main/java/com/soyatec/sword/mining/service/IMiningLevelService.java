package com.soyatec.sword.mining.service;

import java.util.List;

import com.soyatec.sword.mining.domain.MiningLevel;

/**
 * Mining级别Service接口
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-03-15
 */
public interface IMiningLevelService {
	/**
	 * 查询Mining级别
	 * 
	 * @param id Mining级别ID
	 * @return Mining级别
	 */
	public MiningLevel selectMiningLevelById(Long id);

	/**
	 * 查询Mining级别列表
	 * 
	 * @param miningLevel Mining级别
	 * @return Mining级别集合
	 */
	public List<MiningLevel> selectMiningLevelList(MiningLevel miningLevel);

	/**
	 * 新增Mining级别
	 * 
	 * @param miningLevel Mining级别
	 * @return 结果
	 */
	public int insertMiningLevel(MiningLevel miningLevel);

	/**
	 * 修改Mining级别
	 * 
	 * @param miningLevel Mining级别
	 * @return 结果
	 */
	public int updateMiningLevel(MiningLevel miningLevel);

	/**
	 * 批量删除Mining级别
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteMiningLevelByIds(String ids);

	/**
	 * 删除Mining级别信息
	 * 
	 * @param id Mining级别ID
	 * @return 结果
	 */
	public int deleteMiningLevelById(Long id);

	public MiningLevel selectMiningLevelByUserId(Long userId);

	public List<MiningLevel> selectMiningLevelListByType(Integer type);
}