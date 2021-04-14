package com.soyatec.sword.user.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.user.domain.UserProfile;
import com.soyatec.sword.user.domain.UserReferrer;
import com.soyatec.sword.user.domain.UserReferrerTeam;
import com.soyatec.sword.user.domain.UserTeamCount;
import com.soyatec.sword.user.service.IUserReferrerService;
import com.soyatec.sword.user.service.IUserReferrerTeamService;

@Service
public class UserReferrerTeamServiceImpl implements IUserReferrerTeamService {

	@Autowired
	private IUserReferrerService userReferrerService;

	@Override
	public UserReferrerTeam selectUserReferrerTeamByUserId(Long userId) {
		if (userId == null) {
			return null;
		}
		List<UserReferrer> referrerList = userReferrerService.selectAll();
		UserReferrerTeam team = new UserReferrerTeam();
		team.setUserId(userId);
		List<UserReferrer> myReferrals = referrerList.stream().filter(r -> userId.equals(r.getReferralId()))
				.collect(Collectors.toList());
		List<Long> myReferralIds = myReferrals.stream().map(r -> r.getUserId()).collect(Collectors.toList());
		List<Long> myUmbrellaIds = userReferrerService.selectUmbrellaUserIds(userId, referrerList);

		// 今日人数
		UserTeamCount today = new UserTeamCount();
		today.setUserId(userId);
		today.setReferrerCount(myReferrals.stream().filter(r -> DateUtils.isDuringToday(r.getCreateTime())).count());
		today.setUmbrellaCount(referrerList.stream().filter(r -> myUmbrellaIds.contains(r.getUserId()))
				.filter(r -> DateUtils.isDuringToday(r.getCreateTime())).count());
		team.setToday(today);
		// 昨日人数
		UserTeamCount yesterday = new UserTeamCount();
		yesterday.setUserId(userId);
		yesterday.setReferrerCount(
				myReferrals.stream().filter(r -> DateUtils.isDuringYesterday(r.getCreateTime())).count());
		yesterday.setUmbrellaCount(referrerList.stream().filter(r -> myUmbrellaIds.contains(r.getUserId()))
				.filter(r -> DateUtils.isDuringYesterday(r.getCreateTime())).count());
		team.setYesterday(yesterday);
		// 全部人数
		UserTeamCount total = new UserTeamCount();
		total.setUserId(userId);
		total.setReferrerCount(Long.valueOf(myReferralIds.size()));
		total.setUmbrellaCount(Long.valueOf(myUmbrellaIds.size()));
		team.setTotal(total);
		return team;
	}

	@Override
	public List<UserProfile> selectUserReferrerTeamListByUserId(Long userId) {
		return userReferrerService.selectUserReferrerListByUserId(userId);
	}

}
