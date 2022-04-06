package com.github.ecsoya.sword.user.service;

import java.util.Date;
import java.util.List;

import com.github.ecsoya.sword.user.domain.UserProfile;
import com.github.ecsoya.sword.user.domain.UserReferrer;
import com.github.ecsoya.sword.user.domain.UserReferrerInfo;

/**
 * The Interface IUserReferrerService.
 */
public interface IUserReferrerService {

	/**
	 * Select user referrer by id.
	 *
	 * @param userId the user id
	 * @return the user referrer
	 */
	public UserReferrer selectUserReferrerById(Long userId);

	/**
	 * Select user referrer list.
	 *
	 * @param userReferrer the user referrer
	 * @return the list
	 */
	public List<UserReferrer> selectUserReferrerList(UserReferrer userReferrer);

	/**
	 * Insert user referrer.
	 *
	 * @param userReferrer the user referrer
	 * @return the int
	 */
	public int insertUserReferrer(UserReferrer userReferrer);

	/**
	 * Update user referrer.
	 *
	 * @param userReferrer the user referrer
	 * @return the int
	 */
	public int updateUserReferrer(UserReferrer userReferrer);

	/**
	 * Delete user referrer by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteUserReferrerByIds(String ids);

	/**
	 * Delete user referrer by id.
	 *
	 * @param userId the user id
	 * @return the int
	 */
	public int deleteUserReferrerById(Long userId);

	/**
	 * Select user referrer by code.
	 *
	 * @param code the code
	 * @return the user referrer
	 */
	public UserReferrer selectUserReferrerByCode(String code);

	/**
	 * Select unfinished user referrers.
	 *
	 * @return the list
	 */
	public List<UserReferrer> selectUnfinishedUserReferrers();

	/**
	 * Refresh user referrer by id.
	 *
	 * @param userId the user id
	 * @return the user referrer
	 */
	public UserReferrer refreshUserReferrerById(Long userId);

	/**
	 * Select all user ids.
	 *
	 * @return the list
	 */
	public List<Long> selectAllUserIds();

	/**
	 * Select referral user ids by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	public List<Long> selectReferralUserIdsByUserId(Long userId);

	/**
	 * Select user referrer list for update.
	 *
	 * @param baseUrl the base url
	 * @return the list
	 */
	public List<UserReferrer> selectUserReferrerListForUpdate(String baseUrl);

	/**
	 * Select referral count by user id.
	 *
	 * @param userId the user id
	 * @param start  the start
	 * @param end    the end
	 * @return the long
	 */
	public Long selectReferralCountByUserId(Long userId, Date start, Date end);

	/**
	 * Force update referrer code.
	 *
	 * @param userId the user id
	 * @param left   the left
	 * @return the int
	 */
	public int forceUpdateReferrerCode(Long userId, boolean left);

	/**
	 * Update all qrcode code.
	 */
	public void updateAllQrcodeCode();

	/**
	 * Compute right code enabled.
	 *
	 * @param userId the user id
	 * @return the integer
	 */
	public Integer computeRightCodeEnabled(Long userId);

	/**
	 * Compute left code enabled.
	 *
	 * @param userId the user id
	 * @return the integer
	 */
	public Integer computeLeftCodeEnabled(Long userId);

	/**
	 * Update user referrer code enabled.
	 *
	 * @param userId the user id
	 * @return the int
	 */
	public int updateUserReferrerCodeEnabled(Long userId);

	/**
	 * Select all.
	 *
	 * @return the list
	 */
	public List<UserReferrer> selectAll();

	/**
	 * Select umbrella user ids.
	 *
	 * @param userId   the user id
	 * @param allUsers the all users
	 * @return the list
	 */
	public List<Long> selectUmbrellaUserIds(Long userId, List<UserReferrer> allUsers);

	/**
	 * Select umbrella user ids depth firstly.
	 *
	 * @param userId   the user id
	 * @param allUsers the all users
	 * @return the list
	 */
	public List<Long> selectUmbrellaUserIdsDepthFirstly(Long userId, List<UserReferrer> allUsers);

	/**
	 * Select user referrer list by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	public List<UserProfile> selectUserReferrerListByUserId(Long userId);

	/**
	 * Select user referrer info list.
	 *
	 * @param query the query
	 * @return the list
	 */
	public List<UserReferrerInfo> selectUserReferrerInfoList(UserReferrerInfo query);
}
