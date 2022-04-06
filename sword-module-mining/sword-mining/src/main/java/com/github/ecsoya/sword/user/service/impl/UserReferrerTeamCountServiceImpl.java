package com.github.ecsoya.sword.user.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.user.domain.UserProfile;
import com.github.ecsoya.sword.user.domain.UserReferrer;
import com.github.ecsoya.sword.user.domain.UserReferrerTeamCount;
import com.github.ecsoya.sword.user.domain.UserTeamCount;
import com.github.ecsoya.sword.user.service.IUserProfileService;
import com.github.ecsoya.sword.user.service.IUserReferrerService;
import com.github.ecsoya.sword.user.service.IUserReferrerTeamCountService;

/**
 * The Class UserReferrerTeamCountServiceImpl.
 */
@Service
public class UserReferrerTeamCountServiceImpl implements IUserReferrerTeamCountService {

	/** The user referrer service. */
	@Autowired
	private IUserReferrerService userReferrerService;

	/** The user profile service. */
	@Autowired
	private IUserProfileService userProfileService;

	/**
	 * Select user referrer team count by user id.
	 *
	 * @param userId the user id
	 * @return the user referrer team count
	 */
	@Override
	public UserReferrerTeamCount selectUserReferrerTeamCountByUserId(Long userId) {
		if (userId == null) {
			return null;
		}
		final List<UserReferrer> referrerList = userReferrerService.selectAll();
		final UserReferrerTeamCount team = new UserReferrerTeamCount();
		team.setUserId(userId);
		final List<UserReferrer> myReferrals = referrerList.stream().filter(r -> userId.equals(r.getReferralId()))
				.collect(Collectors.toList());
		final List<Long> myReferralIds = myReferrals.stream().map(r -> r.getUserId()).collect(Collectors.toList());
		final List<Long> myUmbrellaIds = userReferrerService.selectUmbrellaUserIds(userId, referrerList);

		// 今日人数
		final UserTeamCount today = new UserTeamCount();
		today.setUserId(userId);
		today.setReferrerCount(myReferrals.stream().filter(r -> DateUtils.isDuringToday(r.getCreateTime())).count());
		today.setUmbrellaCount(referrerList.stream().filter(r -> myUmbrellaIds.contains(r.getUserId()))
				.filter(r -> DateUtils.isDuringToday(r.getCreateTime())).count());
		team.setToday(today);
		// 昨日人数
		final UserTeamCount yesterday = new UserTeamCount();
		yesterday.setUserId(userId);
		yesterday.setReferrerCount(
				myReferrals.stream().filter(r -> DateUtils.isDuringYesterday(r.getCreateTime())).count());
		yesterday.setUmbrellaCount(referrerList.stream().filter(r -> myUmbrellaIds.contains(r.getUserId()))
				.filter(r -> DateUtils.isDuringYesterday(r.getCreateTime())).count());
		team.setYesterday(yesterday);
		// 全部人数
		final UserTeamCount total = new UserTeamCount();
		total.setUserId(userId);
		total.setReferrerCount(Long.valueOf(myReferralIds.size()));
		total.setUmbrellaCount(Long.valueOf(myUmbrellaIds.size()));
		team.setTotal(total);
		return team;
	}

	/**
	 * Select user referrer team list by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	@Override
	public List<UserProfile> selectUserReferrerTeamListByUserId(Long userId) {
		return userReferrerService.selectUserReferrerListByUserId(userId);
	}

	/**
	 * Select user umbrella team list by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	@Override
	public List<UserProfile> selectUserUmbrellaTeamListByUserId(Long userId) {
		final List<UserReferrer> referrerList = userReferrerService.selectAll();
		final List<Long> myUmbrellaIds = userReferrerService.selectUmbrellaUserIds(userId, referrerList);
		return userProfileService.selectUserProfileList(myUmbrellaIds);
	}

}
