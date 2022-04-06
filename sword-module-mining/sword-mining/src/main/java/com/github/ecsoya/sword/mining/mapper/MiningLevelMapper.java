package com.github.ecsoya.sword.mining.mapper;

import java.util.List;

import com.github.ecsoya.sword.mining.domain.MiningLevel;

/**
 * The Interface MiningLevelMapper.
 */
public interface MiningLevelMapper {

	/**
	 * Select mining level by id.
	 *
	 * @param id the id
	 * @return the mining level
	 */
	public MiningLevel selectMiningLevelById(Long id);

	/**
	 * Select mining level list.
	 *
	 * @param miningLevel the mining level
	 * @return the list
	 */
	public List<MiningLevel> selectMiningLevelList(MiningLevel miningLevel);

	/**
	 * Insert mining level.
	 *
	 * @param miningLevel the mining level
	 * @return the int
	 */
	public int insertMiningLevel(MiningLevel miningLevel);

	/**
	 * Update mining level.
	 *
	 * @param miningLevel the mining level
	 * @return the int
	 */
	public int updateMiningLevel(MiningLevel miningLevel);

	/**
	 * Delete mining level by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteMiningLevelById(Long id);

	/**
	 * Delete mining level by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteMiningLevelByIds(String[] ids);

	/**
	 * Select mining level by user id.
	 *
	 * @param userId the user id
	 * @return the mining level
	 */
	public MiningLevel selectMiningLevelByUserId(Long userId);
}
