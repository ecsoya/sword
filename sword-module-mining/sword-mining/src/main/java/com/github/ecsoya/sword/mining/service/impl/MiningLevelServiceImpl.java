package com.github.ecsoya.sword.mining.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.IdWorker;
import com.github.ecsoya.sword.mining.domain.MiningLevel;
import com.github.ecsoya.sword.mining.mapper.MiningLevelMapper;
import com.github.ecsoya.sword.mining.service.IMiningLevelService;

/**
 * The Class MiningLevelServiceImpl.
 */
@Service
public class MiningLevelServiceImpl implements IMiningLevelService {

	/** The mining level mapper. */
	@Autowired
	private MiningLevelMapper miningLevelMapper;

	/**
	 * Select mining level by id.
	 *
	 * @param id the id
	 * @return the mining level
	 */
	@Override
	public MiningLevel selectMiningLevelById(Long id) {
		return miningLevelMapper.selectMiningLevelById(id);
	}

	/**
	 * Select mining level list.
	 *
	 * @param miningLevel the mining level
	 * @return the list
	 */
	@Override
	public List<MiningLevel> selectMiningLevelList(MiningLevel miningLevel) {
		return miningLevelMapper.selectMiningLevelList(miningLevel);
	}

	/**
	 * Insert mining level.
	 *
	 * @param miningLevel the mining level
	 * @return the int
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
	 * Update mining level.
	 *
	 * @param miningLevel the mining level
	 * @return the int
	 */
	@Override
	public int updateMiningLevel(MiningLevel miningLevel) {
		miningLevel.setUpdateTime(DateUtils.getNowDate());
		return miningLevelMapper.updateMiningLevel(miningLevel);
	}

	/**
	 * Delete mining level by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteMiningLevelByIds(String ids) {
		return miningLevelMapper.deleteMiningLevelByIds(Convert.toStrArray(ids));
	}

	/**
	 * Delete mining level by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Override
	public int deleteMiningLevelById(Long id) {
		return miningLevelMapper.deleteMiningLevelById(id);
	}

	/**
	 * Select mining level by user id.
	 *
	 * @param userId the user id
	 * @return the mining level
	 */
	@Override
	public MiningLevel selectMiningLevelByUserId(Long userId) {
		if (userId == null) {
			return null;
		}
		return miningLevelMapper.selectMiningLevelByUserId(userId);
	}

	/**
	 * Select mining level list by type.
	 *
	 * @param type the type
	 * @return the list
	 */
	@Override
	public List<MiningLevel> selectMiningLevelListByType(Integer type) {
		final MiningLevel query = new MiningLevel();
		query.setType(type);
		return selectMiningLevelList(query);
	}
}
