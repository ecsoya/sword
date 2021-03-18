package com.soyatec.sword.user.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.soyatec.sword.user.domain.UserProfile;
import com.soyatec.sword.user.domain.UserReferrer;
import com.soyatec.sword.user.domain.UserReferrerInfo;

/**
 * 用户直推Mapper接口
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
public interface UserReferrerMapper {
	/**
	 * 查询用户直推
	 * 
	 * @param userId 用户直推ID
	 * @return 用户直推
	 */
	public UserReferrer selectUserReferrerById(Long userId);

	/**
	 * 查询用户直推列表
	 * 
	 * @param userReferrer 用户直推
	 * @return 用户直推集合
	 */
	public List<UserReferrer> selectUserReferrerList(UserReferrer userReferrer);

	/**
	 * 新增用户直推
	 * 
	 * @param userReferrer 用户直推
	 * @return 结果
	 */
	public int insertUserReferrer(UserReferrer userReferrer);

	/**
	 * 修改用户直推
	 * 
	 * @param userReferrer 用户直推
	 * @return 结果
	 */
	public int updateUserReferrer(UserReferrer userReferrer);

	/**
	 * 删除用户直推
	 * 
	 * @param userId 用户直推ID
	 * @return 结果
	 */
	public int deleteUserReferrerById(Long userId);

	/**
	 * 批量删除用户直推
	 * 
	 * @param userIds 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteUserReferrerByIds(String[] userIds);

	public UserReferrer selectUserReferrerByCode(String code);

	public List<Long> selectAllUserIds();

	public List<Long> selectReferralUserIdsByUserId(Long userId);

	public List<UserReferrer> selectUserReferrerListForUpdate(String baseUrl);

	public Long selectReferralCountByUserId(@Param("userId") Long userId, @Param("start") Date start,
			@Param("end") Date end);

	public List<UserProfile> selectUserReferrerListByUserId(Long userId);

	public List<UserReferrerInfo> selectUserReferrerInfoList(UserReferrerInfo query);

}
