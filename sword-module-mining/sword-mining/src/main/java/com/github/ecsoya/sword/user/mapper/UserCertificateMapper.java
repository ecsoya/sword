package com.github.ecsoya.sword.user.mapper;

import java.util.List;

import com.github.ecsoya.sword.user.domain.UserCertificate;

/**
 * The Interface UserCertificateMapper.
 */
public interface UserCertificateMapper {

	/**
	 * Select user certificate by id.
	 *
	 * @param userId the user id
	 * @return the user certificate
	 */
	public UserCertificate selectUserCertificateById(Long userId);

	/**
	 * Select user certificate list.
	 *
	 * @param userCertificate the user certificate
	 * @return the list
	 */
	public List<UserCertificate> selectUserCertificateList(UserCertificate userCertificate);

	/**
	 * Insert user certificate.
	 *
	 * @param userCertificate the user certificate
	 * @return the int
	 */
	public int insertUserCertificate(UserCertificate userCertificate);

	/**
	 * Update user certificate.
	 *
	 * @param userCertificate the user certificate
	 * @return the int
	 */
	public int updateUserCertificate(UserCertificate userCertificate);

	/**
	 * Delete user certificate by id.
	 *
	 * @param userId the user id
	 * @return the int
	 */
	public int deleteUserCertificateById(Long userId);

	/**
	 * Delete user certificate by ids.
	 *
	 * @param userIds the user ids
	 * @return the int
	 */
	public int deleteUserCertificateByIds(String[] userIds);
}
