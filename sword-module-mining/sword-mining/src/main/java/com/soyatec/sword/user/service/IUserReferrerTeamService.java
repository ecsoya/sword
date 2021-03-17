package com.soyatec.sword.user.service;

import java.util.List;

import com.soyatec.sword.user.domain.UserProfile;
import com.soyatec.sword.user.domain.UserReferrerTeam;

public interface IUserReferrerTeamService {

	// 获取直推团队人数
	public UserReferrerTeam selectUserReferrerTeamByUserId(Long userId);

	// 获取直推团队列表
	public List<UserProfile> selectUserReferrerTeamListByUserId(Long userId);

}
