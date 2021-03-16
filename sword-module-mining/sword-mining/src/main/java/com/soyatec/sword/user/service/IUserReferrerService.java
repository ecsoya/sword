package com.soyatec.sword.user.service;

import java.util.Date;
import java.util.List;

import com.soyatec.sword.user.domain.UserReferrer;

/**
 * 用户直推Service接口
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
public interface IUserReferrerService {
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
	 * 批量删除用户直推
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteUserReferrerByIds(String ids);

	/**
	 * 删除用户直推信息
	 * 
	 * @param userId 用户直推ID
	 * @return 结果
	 */
	public int deleteUserReferrerById(Long userId);

	public UserReferrer selectUserReferrerByCode(String code);

	/**
	 */
	public List<UserReferrer> selectUnfinishedUserReferrers();

	public UserReferrer refreshUserReferrerById(Long userId);

	public List<Long> selectAllUserIds();

	public List<Long> selectReferralUserIdsByUserId(Long userId);

	public List<UserReferrer> selectUserReferrerListForUpdate(String baseUrl);

	/**
	 * 查询直推用户数量
	 */
	public Long selectReferralCountByUserId(Long userId, Date start, Date end);

	/**
	 * 强制更新推荐码及二维码
	 */
	public int forceUpdateReferrerCode(Long userId, boolean left);

	public void updateAllQrcodeCode();

	/**
	 * 右区推荐码是否开放
	 */
	public Integer computeRightCodeEnabled(Long userId);

	/**
	 * 左区推荐码是否开放
	 */
	public Integer computeLeftCodeEnabled(Long userId);

	/**
	 * 更新推荐码是否可用
	 */
	public int updateUserReferrerCodeEnabled(Long userId);

	public List<UserReferrer> selectAll();

	/**
	 * 查询所有的伞下用户
	 */
	public List<Long> selectUmbrellaUserIds(Long userId, List<UserReferrer> allUsers);

	/**
	 * 查询所有的伞下用户
	 */
	public List<Long> selectUmbrellaUserIdsDepthFirstly(Long userId, List<UserReferrer> allUsers);
}
