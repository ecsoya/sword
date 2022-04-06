package com.github.ecsoya.sword.mining.mapper;

import java.util.List;

import com.github.ecsoya.sword.mining.domain.MiningUser;
import com.github.ecsoya.sword.report.domain.DateCount;

/**
 * The Interface MiningUserMapper.
 */
public interface MiningUserMapper {

	/**
	 * Select mining user list.
	 *
	 * @param query the query
	 * @return the list
	 */
	List<MiningUser> selectMiningUserList(MiningUser query);

	/**
	 * Select register user counts by date.
	 *
	 * @return the list
	 */
	List<DateCount> selectRegisterUserCountsByDate();
}
