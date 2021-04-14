package com.soyatec.sword.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.core.text.Convert;
import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.common.utils.async.AsyncManager;
import com.soyatec.sword.constants.IMiningConstants;
import com.soyatec.sword.system.service.ISysConfigService;
import com.soyatec.sword.user.domain.UserCertificate;
import com.soyatec.sword.user.mapper.UserCertificateMapper;
import com.soyatec.sword.user.service.IUserCertificateService;
import com.soyatec.sword.wallet.service.IUserWalletService;

/**
 * 用户实名Service业务层处理
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-23
 */
@Service
public class UserCertificateServiceImpl implements IUserCertificateService {
	@Autowired
	private UserCertificateMapper userCertificateMapper;

	@Autowired
	private ISysConfigService configService;

	@Autowired
	private IUserWalletService walletService;

	/**
	 * 查询用户实名
	 *
	 * @param userId 用户实名ID
	 * @return 用户实名
	 */
	@Override
	public UserCertificate selectUserCertificateById(Long userId) {
		return userCertificateMapper.selectUserCertificateById(userId);
	}

	/**
	 * 查询用户实名列表
	 *
	 * @param userCertificate 用户实名
	 * @return 用户实名
	 */
	@Override
	public List<UserCertificate> selectUserCertificateList(UserCertificate userCertificate) {
		return userCertificateMapper.selectUserCertificateList(userCertificate);
	}

	/**
	 * 新增用户实名
	 *
	 * @param userCertificate 用户实名
	 * @return 结果
	 */
	@Override
	public int insertUserCertificate(UserCertificate userCertificate) {
		if (userCertificate.getCreateTime() == null) {
			userCertificate.setCreateTime(DateUtils.getNowDate());
		}
		return userCertificateMapper.insertUserCertificate(userCertificate);
	}

	/**
	 * 修改用户实名
	 *
	 * @param userCertificate 用户实名
	 * @return 结果
	 */
	@Override
	public int updateUserCertificate(UserCertificate userCertificate) {
		userCertificate.setUpdateTime(DateUtils.getNowDate());
		return userCertificateMapper.updateUserCertificate(userCertificate);
	}

	/**
	 * 删除用户实名对象
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteUserCertificateByIds(String ids) {
		return userCertificateMapper.deleteUserCertificateByIds(Convert.toStrArray(ids));
	}

	/**
	 * 删除用户实名信息
	 *
	 * @param userId 用户实名ID
	 * @return 结果
	 */
	@Override
	public int deleteUserCertificateById(Long userId) {
		return userCertificateMapper.deleteUserCertificateById(userId);
	}

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
