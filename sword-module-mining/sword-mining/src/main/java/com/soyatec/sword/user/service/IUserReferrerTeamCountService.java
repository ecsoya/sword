package com.soyatec.sword.user.service;

import java.util.List;

import com.soyatec.sword.user.domain.UserProfile;
import com.soyatec.sword.user.domain.UserReferrerTeamCount;

/**
 * 以直推数据为基准的团队
 *
 * @author ecsoya
 */
public interface IUserReferrerTeamCountService {

	// 获取团队人数，以直推数据为基准
	public UserReferrerTeamCount selectUserReferrerTeamCountByUserId(Long userId);

	// 获取直推团队列表
	public List<UserProfile> selectUserReferrerTeamListByUserId(Long userId);

	// 获取伞下团队列表
	public List<UserProfile> selectUserUmbrellaTeamListByUserId(Long userId);

}
