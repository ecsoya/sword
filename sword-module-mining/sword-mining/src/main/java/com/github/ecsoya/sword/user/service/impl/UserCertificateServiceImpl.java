package com.github.ecsoya.sword.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.async.AsyncManager;
import com.github.ecsoya.sword.constants.IMiningConstants;
import com.github.ecsoya.sword.system.service.ISysConfigService;
import com.github.ecsoya.sword.user.domain.UserCertificate;
import com.github.ecsoya.sword.user.mapper.UserCertificateMapper;
import com.github.ecsoya.sword.user.service.IUserCertificateService;
import com.github.ecsoya.sword.wallet.service.IUserWalletService;

/**
 * The Class UserCertificateServiceImpl.
 */
@Service
public class UserCertificateServiceImpl implements IUserCertificateService {

	/** The user certificate mapper. */
	@Autowired
	private UserCertificateMapper userCertificateMapper;

	/** The config service. */
	@Autowired
	private ISysConfigService configService;

	/** The wallet service. */
	@Autowired
	private IUserWalletService walletService;

	/**
	 * Select user certificate by id.
	 *
	 * @param userId the user id
	 * @return the user certificate
	 */
	@Override
	public UserCertificate selectUserCertificateById(Long userId) {
		return userCertificateMapper.selectUserCertificateById(userId);
	}

	/**
	 * Select user certificate list.
	 *
	 * @param userCertificate the user certificate
	 * @return the list
	 */
	@Override
	public List<UserCertificate> selectUserCertificateList(UserCertificate userCertificate) {
		return userCertificateMapper.selectUserCertificateList(userCertificate);
	}

	/**
	 * Insert user certificate.
	 *
	 * @param userCertificate the user certificate
	 * @return the int
	 */
	@Override
	public int insertUserCertificate(UserCertificate userCertificate) {
		if (userCertificate.getCreateTime() == null) {
			userCertificate.setCreateTime(DateUtils.getNowDate());
		}
		return userCertificateMapper.insertUserCertificate(userCertificate);
	}

	/**
	 * Update user certificate.
	 *
	 * @param userCertificate the user certificate
	 * @return the int
	 */
	@Override
	public int updateUserCertificate(UserCertificate userCertificate) {
		userCertificate.setUpdateTime(DateUtils.getNowDate());
		return userCertificateMapper.updateUserCertificate(userCertificate);
	}

	/**
	 * Delete user certificate by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteUserCertificateByIds(String ids) {
		return userCertificateMapper.deleteUserCertificateByIds(Convert.toStrArray(ids));
	}

	/**
	 * Delete user certificate by id.
	 *
	 * @param userId the user id
	 * @return the int
	 */
	@Override
	public int deleteUserCertificateById(Long userId) {
		return userCertificateMapper.deleteUserCertificateById(userId);
	}

	/**
	 * Update user certificate status.
	 *
	 * @param userId the user id
	 * @param status the status
	 * @param remark the remark
	 * @return the int
	 */
	@Override
	public int updateUserCertificateStatus(Long userId, Integer status, String remark) {
		if (userId == null || status == null || status > UserCertificate.STATUS_SUCCESS
				|| status < UserCertificate.STATUS_NONE) {
			return 0;
		}

		final UserCertificate c = new UserCertificate();
		c.setUserId(userId);
		c.setStatus(status);
		c.setRemark(remark);
		final int rows = updateUserCertificate(c);
		if (rows > 0 && UserCertificate.STATUS_SUCCESS.equals(status)) {
			// 实名审核成功后，创建钱包地址
			AsyncManager.me().execute(new Runnable() {

				@Override
				public void run() {
					walletService.createUserWalletByUserId(userId, null, false);
				}
			});
		}
		return rows;
	}

	/**
	 * Check user certificate.
	 *
	 * @param userId the user id
	 * @param kind   the kind
	 * @return the common result
	 */
	@Override
	public CommonResult<?> checkUserCertificate(Long userId, Integer kind) {
		final String value = configService.selectConfigValueByKey(IMiningConstants.ENABLE_USER_CERTIFICATE);
		if (StringUtils.isEmpty(value) || "false".equalsIgnoreCase(value)) {
			return CommonResult.success("实名功能未启用");
		}
		if (userId == null || kind == null) {
			return CommonResult.fail("参数错误");
		}
		int ucKind = 0;
		try {
			ucKind = Integer.parseInt(value);
		} catch (final NumberFormatException e) {
			ucKind = 0;
		}
		if (!UserCertificate.checkKind(ucKind, kind)) {
			return CommonResult.success("无需检测");
		}
		final UserCertificate uc = selectUserCertificateById(userId);
		if (uc == null) {
			return CommonResult.fail("未实名认证");
		} else if (UserCertificate.STATUS_SUCCESS.equals(uc.getStatus())) {
			return CommonResult.success("实名成功");
		}
		return CommonResult.fail("尚未审核或审核失败");
	}

}
