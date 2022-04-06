package com.github.ecsoya.sword.user.service;

import java.util.List;

import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.user.domain.UserCertificate;

/**
 * The Interface IUserCertificateService.
 */
public interface IUserCertificateService {

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
	 * Delete user certificate by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteUserCertificateByIds(String ids);

	/**
	 * Delete user certificate by id.
	 *
	 * @param userId the user id
	 * @return the int
	 */
	public int deleteUserCertificateById(Long userId);

	/**
	 * Update user certificate status.
	 *
	 * @param userId the user id
	 * @param status the status
	 * @param remark the remark
	 * @return the int
	 */
	public int updateUserCertificateStatus(Long userId, Integer status, String remark);

	/**
	 * Check user certificate.
	 *
	 * @param userId the user id
	 * @param kind   the kind
	 * @return the common result
	 */
	public CommonResult<?> checkUserCertificate(Long userId, Integer kind);

}
