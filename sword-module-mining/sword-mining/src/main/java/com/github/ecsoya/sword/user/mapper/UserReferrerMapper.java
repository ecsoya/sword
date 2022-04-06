package com.github.ecsoya.sword.user.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.ecsoya.sword.user.domain.UserProfile;
import com.github.ecsoya.sword.user.domain.UserReferrer;
import com.github.ecsoya.sword.user.domain.UserReferrerInfo;

/**
 * The Interface UserReferrerMapper.
 */
public interface UserReferrerMapper {

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
	 * Delete user referrer by id.
	 *
	 * @param userId the user id
	 * @return the int
	 */
	public int deleteUserReferrerById(Long userId);

	/**
	 * Delete user referrer by ids.
	 *
	 * @param userIds the user ids
	 * @return the int
	 */
	public int deleteUserReferrerByIds(String[] userIds);

	/**
	 * Select user referrer by code.
	 *
	 * @param code the code
	 * @return the user referrer
	 */
	public UserReferrer selectUserReferrerByCode(String code);

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
	public Long selectReferralCountByUserId(@Param("userId") Long userId, @Param("start") Date start,
			@Param("end") Date end);

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
