package com.github.ecsoya.sword.user.service;

import java.util.List;

import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.user.domain.UserCertificate;

/**
 * 用户实名Service接口
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-23
 */
public interface IUserCertificateService {
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
	 * 批量删除用户实名
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteUserCertificateByIds(String ids);

	/**
	 * 删除用户实名信息
	 *
	 * @param userId 用户实名ID
	 * @return 结果
	 */
	public int deleteUserCertificateById(Long userId);

	public int updateUserCertificateStatus(Long userId, Integer status, String remark);

	public CommonResult<?> checkUserCertificate(Long userId, Integer kind);

}
