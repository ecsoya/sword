package com.github.ecsoya.sword.business.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.ecsoya.sword.business.service.IWithdrawalNotifyService;
import com.github.ecsoya.sword.code.service.IMailService;
import com.github.ecsoya.sword.common.config.GlobalConfig;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.security.DESUtil;
import com.github.ecsoya.sword.order.domain.UserWithdrawalOrder;
import com.github.ecsoya.sword.order.service.IUserWithdrawalOrderService;
import com.github.ecsoya.sword.user.service.IUserProfileService;
import com.github.ecsoya.sword.utils.MathUtils;

/**
 * The Class WithdrawalNotifyServiceImpl.
 */
@Service
public class WithdrawalNotifyServiceImpl implements IWithdrawalNotifyService {

	/** The Constant UTF_8. */
	private static final Charset UTF_8 = Charset.forName("utf-8");

	/** The Constant iv. */
	private static final byte[] iv = "21210873".getBytes(UTF_8);

	/** The Constant SWORD. */
	private static final String SWORD = "Sword*no1ti9fy!";

	/** The Constant PARAM_TYPE. */
	private static final String PARAM_TYPE = "t";

	/** The Constant PARAM_USER_ID. */
	private static final String PARAM_USER_ID = "u";

	/** The Constant PARAM_ORDER_NO. */
	private static final String PARAM_ORDER_NO = "o";

	/** The Constant TYPE_CONFIRMED. */
	private static final Integer TYPE_CONFIRMED = 0;

	/** The Constant TYPE_REJECTED. */
	private static final Integer TYPE_REJECTED = 1;

	/** The Constant PATH. */
	private static final String PATH = "/open/v1/confirm?token=";

	/** The Constant SUBJECT. */
	private static final String SUBJECT = "你收到一笔新的提币订单，需要确认！";

	/** The Constant TEMPLATES. */
	private static final String TEMPLATES = "<html><head><meta charset='UTF-8'><title>提币订单确认</title></head><body><h3>尊敬的管理员</h3><p>你收到了一笔提币订单，需要确认：<ul style='border:1px solid #ccc;border-radius:4px;padding-inline-start:14px;padding-top:8px;padding-bottom:8px'><li style='list-style:none'><label style='display:inline-block;width:80px'>用户名</label>：<label style='color:gray'>{loginName}</label><li style='list-style:none'><label style='display:inline-block;width:80px'>邮箱</label>：<label style='color:gray'>{email}</label><li style='list-style:none'><label style='display:inline-block;width:80px'>电话</label>：<label style='color:gray'>{mobile}</label><li style='list-style:none'><label style='display:inline-block;width:80px'>提币金额</label>：<label style='color:gray'>{amount}</label><li style='list-style:none'><label style='display:inline-block;width:80px'>提币地址</label>：<label style='color:gray'>{address}</label><li style='list-style:none'><label style='display:inline-block;width:80px'>提币币种</label>：<label style='color:gray'>{symbol}</label><li style='list-style:none'><label style='display:inline-block;width:80px'>提币时间</label>：<label style='color:gray'>{createTime}</label></ul><p style='color:gray'>你可以通过点击下方的链接，进行快速的<strong>确认</strong>或<strong>拒绝</strong>操作，也可以登录后台进行操作。<div style='text-align:center;padding:50px 20px'><a href='{confirmUrl}' style='color:#fff;font-size:20px;background-color:#1ab394;border:none;border-radius:8px;padding:8px 14px;display:inline-block;text-decoration:none'>确认提币</a><a href='{rejectUrl}' style='color:#fff;font-size:20px;background-color:#ed5565;border:none;border-radius:8px;padding:8px 14px;display:inline-block;text-decoration:none;margin-left:40px'>拒绝提币</a></div></body></html>";

	/** The profile service. */
	@Autowired
	private IUserProfileService profileService;

	/** The mail service. */
	@Autowired
	private IMailService mailService;

	/** The withdrawal order service. */
	@Autowired
	private IUserWithdrawalOrderService withdrawalOrderService;

	/**
	 * Notify.
	 *
	 * @param order the order
	 * @return the int
	 */
	@Override
	public int notify(UserWithdrawalOrder order) {
		if (order == null) {
			return -1;
		}
		Long userId = order.getUserId();
		String content = TEMPLATES.replaceAll("\\{loginName\\}", profileService.selectUserLoginNameByUserId(userId))
				.replaceAll("\\{email\\}", profileService.selectUserEmailById(userId))
				.replaceAll("\\{mobile\\}", profileService.selectUserMobileById(userId))
				.replaceAll("\\{amount\\}", order.getAmount().toPlainString())
				.replaceAll("\\{address\\}", order.getAddress())
				.replaceAll("\\{symbol\\}", order.getSymbol().toUpperCase())
				.replaceAll("\\{createTime\\}", DateUtils.toDefault(order.getCreateTime()))
				.replaceAll("\\{confirmUrl\\}", getNotifyUrl(order, TYPE_CONFIRMED))
				.replaceAll("\\{rejectUrl\\}", getNotifyUrl(order, TYPE_REJECTED));
		String subject = "【" + GlobalConfig.getName() + "】" + SUBJECT;
		mailService.sendEmail(profileService.selectUserEmailByUsername("admin"), subject, content);
		return 1;
	}

	/**
	 * Gets the notify url.
	 *
	 * @param order the order
	 * @param type  the type
	 * @return the notify url
	 */
	private String getNotifyUrl(UserWithdrawalOrder order, Integer type) {
		String value = "";
		try {
			value = URLEncoder.encode(encrypt(order.getUserId(), order.getOrderNo(), type), UTF_8.name());
		} catch (UnsupportedEncodingException e) {
		}
		return GlobalConfig.getGateway() + PATH + value;
	}

	/**
	 * Confirm order.
	 *
	 * @param token the token
	 * @return the common result
	 */
	@Override
	public CommonResult<?> confirmOrder(String token) {
		if (StringUtils.isEmpty(token) || token.length() < 5) {
			return CommonResult.fail("参数错误");
		}
		Map<String, Object> params = decrypt(token);
		if (params == null || params.size() != 3) {
			return CommonResult.fail("格式错误【1】");
		}
		try {
			Integer type = (Integer) params.get(PARAM_TYPE);
			Long userId = (Long) params.get(PARAM_USER_ID);
			String orderNo = (String) params.get(PARAM_ORDER_NO);
			if (TYPE_CONFIRMED.equals(type)) {
				CommonResult<?> confirmWithdrawal = withdrawalOrderService.confirmWithdrawal(userId, orderNo);
				if (confirmWithdrawal.isSuccess()) {
					return CommonResult.create(200, "审核成功", "success");
				}
				return CommonResult.create(500, "审核失败", confirmWithdrawal.getInfo());
			} else {
				CommonResult<?> cancelWithdrawal = withdrawalOrderService.cancelWithdrawal(userId, orderNo, "提币拒绝（邮件）");
				if (cancelWithdrawal.isSuccess()) {
					return CommonResult.create(200, "拒绝成功", "success");
				}
				return CommonResult.create(500, "拒绝失败", cancelWithdrawal.getInfo());
			}
		} catch (Exception e) {
			return CommonResult.fail("格式错误【2】");
		}
	}

	/**
	 * Decrypt.
	 *
	 * @param token the token
	 * @return the map
	 */
	private static Map<String, Object> decrypt(String token) {
		if (StringUtils.isEmpty(token) || token.length() < 5) {
			return Collections.emptyMap();
		}
		try {
			Map<String, Object> map = new HashMap<>();
			String prefix = token.substring(0, 4);
			String suffix = token.substring(4);
			map.put(PARAM_TYPE, MathUtils.parseInteger(prefix.substring(3)));
			String decrypt = DESUtil.decrypt(prefix + SWORD, suffix, iv);
			JSONObject json = JSON.parseObject(decrypt);
			map.put(PARAM_USER_ID, json.getLong(PARAM_USER_ID));
			map.put(PARAM_ORDER_NO, json.getString(PARAM_ORDER_NO));
			return map;
		} catch (Exception e) {
			return Collections.emptyMap();
		}
	}

	/**
	 * Encrypt.
	 *
	 * @param userId  the user id
	 * @param orderNo the order no
	 * @param type    the type
	 * @return the string
	 */
	private static String encrypt(Long userId, String orderNo, Integer type) {
		if (userId == null || orderNo == null || type == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(PARAM_USER_ID, userId);
		map.put(PARAM_ORDER_NO, orderNo);
		String prefix = StringUtils.randomStr(3) + type;
		String encrypt = DESUtil.encrypt(prefix + SWORD, JSON.toJSONString(map), iv);
		return prefix + encrypt;
	}

}
