package com.github.ecsoya.sword.user.service;

import java.util.List;

import com.github.ecsoya.sword.user.domain.UserProfile;
import com.github.ecsoya.sword.user.domain.UserReferrerTeamCount;

/**
 * The Interface IUserReferrerTeamCountService.
 */
public interface IUserReferrerTeamCountService {

	/**
	 * Select user referrer team count by user id.
	 *
	 * @param userId the user id
	 * @return the user referrer team count
	 */
	// 获取团队人数，以直推数据为基准
	public UserReferrerTeamCount selectUserReferrerTeamCountByUserId(Long userId);

	/**
	 * Select user referrer team list by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	// 获取直推团队列表
	public List<UserProfile> selectUserReferrerTeamListByUserId(Long userId);

	/**
	 * Select user umbrella team list by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	// 获取伞下团队列表
	public List<UserProfile> selectUserUmbrellaTeamListByUserId(Long userId);

}
