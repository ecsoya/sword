package com.soyatec.sword.user.mapper;

import java.util.List;

import com.soyatec.sword.user.domain.UserCertificate;

/**
 * 用户实名Mapper接口
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-23
 */
public interface UserCertificateMapper {
	/**
	 * 查询用户实名
	 *
	 * @param userId 用户实名ID
	 * @return 用户实名
	 */
	public UserCertificate selectUserCertificateById(Long userId);

	/**
	 * 查询用户实名列表
	 *
	 * @param userCertificate 用户实名
	 * @return 用户实名集合
	 */
	public List<UserCertificate> selectUserCertificateList(UserCertificate userCertificate);

	/**
	 * 新增用户实名
	 *
	 * @param userCertificate 用户实名
	 * @return 结果
	 */
	public int insertUserCertificate(UserCertificate userCertificate);

	/**
	 * 修改用户实名
	 *
	 * @param userCertificate 用户实名
	 * @return 结果
	 */
	public int updateUserCertificate(UserCertificate userCertificate);

	/**
	 * 删除用户实名
	 *
	 * @param userId 用户实名ID
	 * @return 结果
	 */
	public int deleteUserCertificateById(Long userId);

	/**
	 * 批量删除用户实名
	 *
	 * @param userIds 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteUserCertificateByIds(String[] userIds);
}
