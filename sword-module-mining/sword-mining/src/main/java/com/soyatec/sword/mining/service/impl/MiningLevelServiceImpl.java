package com.soyatec.sword.mining.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soyatec.sword.common.core.text.Convert;
import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.common.utils.IdWorker;
import com.soyatec.sword.mining.domain.MiningLevel;
import com.soyatec.sword.mining.mapper.MiningLevelMapper;
import com.soyatec.sword.mining.service.IMiningLevelService;

/**
 * Mining级别Service业务层处理
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-03-15
 */
@Service
public class MiningLevelServiceImpl implements IMiningLevelService {
	@Autowired
	private MiningLevelMapper miningLevelMapper;

	/**
	 * 查询Mining级别
	 * 
	 * @param id Mining级别ID
	 * @return Mining级别
	 */
	@Override
	public MiningLevel selectMiningLevelById(Long id) {
		return miningLevelMapper.selectMiningLevelById(id);
	}

	/**
	 * 查询Mining级别列表
	 * 
	 * @param miningLevel Mining级别
	 * @return Mining级别
	 */
	@Override
	public List<MiningLevel> selectMiningLevelList(MiningLevel miningLevel) {
		return miningLevelMapper.selectMiningLevelList(miningLevel);
	}

	/**
	 * 新增Mining级别
	 * 
	 * @param miningLevel Mining级别
	 * @return 结果
	 */
	@Override
	public int insertMiningLevel(MiningLevel miningLevel) {
		if (miningLevel.getId() == null) {
			miningLevel.setId(IdWorker.getId());
		}
		if (miningLevel.getCreateTime() == null) {
			miningLevel.setCreateTime(DateUtils.getNowDate());
		}
		return miningLevelMapper.insertMiningLevel(miningLevel);
	}

	/**
	 * 修改Mining级别
	 * 
	 * @param miningLevel Mining级别
	 * @return 结果
	 */
	@Override
	public int updateMiningLevel(MiningLevel miningLevel) {
		miningLevel.setUpdateTime(DateUtils.getNowDate());
		return miningLevelMapper.updateMiningLevel(miningLevel);
	}

	/**
	 * 删除Mining级别对象
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteMiningLevelByIds(String ids) {
		return miningLevelMapper.deleteMiningLevelByIds(Convert.toStrArray(ids));
	}

	/**
	 * 删除Mining级别信息
	 * 
	 * @param id Mining级别ID
	 * @return 结果
	 */
	@Override
	public int deleteMiningLevelById(Long id) {
		return miningLevelMapper.deleteMiningLevelById(id);
	}

	@Override
	public MiningLevel selectMiningLevelByUserId(Long userId) {
		if (userId == null) {
			return null;
		}
		return miningLevelMapper.selectMiningLevelByUserId(userId);
	}

	@Override
	public List<MiningLevel> selectMiningLevelListByType(Integer type) {
		MiningLevel query = new MiningLevel();
		query.setType(type);
		return selectMiningLevelList(query);
	}
}
