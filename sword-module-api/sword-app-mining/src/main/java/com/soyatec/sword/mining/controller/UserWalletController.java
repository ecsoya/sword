package com.soyatec.sword.mining.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soyatec.sword.common.annotation.RepeatSubmit;
import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.core.page.TableDataInfo;
import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.mining.utils.SwordMiningUtils;
import com.soyatec.sword.order.domain.UserWithdrawalOrder;
import com.soyatec.sword.order.service.IUserWithdrawalOrderService;
import com.soyatec.sword.utils.MathUtils;
import com.soyatec.sword.utils.SwordUtils;
import com.soyatec.sword.wallet.domain.UserWallet;
import com.soyatec.sword.wallet.domain.UserWalletAccount;
import com.soyatec.sword.wallet.domain.UserWalletUnionRecord;
import com.soyatec.sword.wallet.service.IUserWalletAccountService;
import com.soyatec.sword.wallet.service.IUserWalletService;
import com.soyatec.sword.wallet.service.IUserWalletUnionRecordService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/user/wallet")
@Api(tags = { "钱包" }, description = "账户、支付密码、提现")
public class UserWalletController extends BaseController {

	@Autowired
	public IUserWalletService userWalletService;

	@Autowired
	private IUserWithdrawalOrderService withdrawalOrderService;

	@Autowired
	private IUserWalletAccountService userWalletAccountService;
	@Autowired
	private IUserWalletUnionRecordService walletRecordService;

	@ApiOperation("查询钱包信息")
	@GetMapping("/records")
	public TableDataInfo records(@ApiParam(required = true) String symbol, Date start, Date end,
			@ApiParam(value = "1-充值，2-提币，留空为所有") Integer kind) {
		if (start != null) {
			start = DateUtils.getStartOf(start);
		}
		if (end != null) {
			end = DateUtils.getEndOf(end);
		}
		startPage("create_time desc");
		Long userId = SwordUtils.getUserId();
		List<UserWalletUnionRecord> list = walletRecordService.selectWalletRecordListByUserId(userId, symbol, start,
				end, kind);
		return getDataTable(list);
	}

	@ApiOperation("查询钱包信息")
	@GetMapping("/info")
	public CommonResult<UserWallet> info() {
		return CommonResult.build(userWalletService.selectUserWalletById(SwordUtils.getUserId(), true));
	}

	@ApiOperation(value = "查询钱包账户", notes = "\"data\": {\n" + "        \"userId\": \"107\", //用户ID\n"
			+ "        \"symbol\": \"fil\", \n"
			+ "        \"address\": \"f1xupjdinwm3yzolgwtx3257nzoow7wwn5rrzgtwq\", //充值地址\n"
			+ "        \"addressUrl\": \"https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com/2021/01/08/qrcode/1347469687116271618.png\", //充值地址二维码\n"
			+ "        \"amount\": \"2.778\", //可用余额\n" + "        \"lastAmount\": \"0.0\",\n"
			+ "        \"minHoldAmount\": \"0.0\", //最小持币量\n" + "        \"frozenAmount\": \"0.0\", //冻结额度\n"
			+ "        \"lockedAmount\": \"0.88\", //锁定额度\n" + "        \"withdrawalFee\": \"2\" //提币手续费\n" + "    }")
	@GetMapping("/account")
	public CommonResult<UserWalletAccount> account(String symbol) {
		return CommonResult.build(userWalletAccountService.selectUserWalletAccount(SwordUtils.getUserId(), symbol));
	}

	@ApiOperation("更新钱包/支付密码")
	@PostMapping("/changePwd")
	@RepeatSubmit
	public CommonResult<?> changePassword(String oldPassword, String newPassword, String code) {
		CommonResult<?> verifyCode = SwordUtils.verifyCode(code);
		if (!verifyCode.isSuccess()) {
			return CommonResult.fail(verifyCode.getInfo());
		}
		Long userId = SwordUtils.getUserId();
		return userWalletService.changeUserWalletPassword(userId, oldPassword, newPassword);
	}

	@ApiOperation("重置钱包/支付密码")
	@PostMapping("/resetPwd")
	@RepeatSubmit
	public CommonResult<?> resetPassword(String password, String code) {
		CommonResult<?> verifyCode = SwordUtils.verifyCode(code);
		if (!verifyCode.isSuccess()) {
			return CommonResult.fail(verifyCode.getInfo());
		}
		Long userId = SwordUtils.getUserId();
		return CommonResult.ajax(userWalletService.resetUserWalletPassword(userId, password));
	}

	@ApiOperation(value = "提现申请", notes = "提币申请，需调用/confirm接口确认提币")
	@PostMapping("/withdrawal")
	@RepeatSubmit
	public CommonResult<UserWithdrawalOrder> withdrawal(String symbol, String address, BigDecimal amount,
			String password, String code) {
		CommonResult<?> verifyWalletPassword = SwordMiningUtils.verifyWalletPassword(password);
		if (!verifyWalletPassword.isSuccess()) {
			return CommonResult.fail(verifyWalletPassword.getInfo());
		}
		CommonResult<?> verifyCode = SwordUtils.verifyCode(code);
		if (!verifyCode.isSuccess()) {
			return CommonResult.fail(verifyCode.getInfo());
		}
		if (StringUtils.isEmpty(symbol) || StringUtils.isEmpty(address) || MathUtils.isEmpty(amount)
				|| StringUtils.isEmpty(password)) {
			return CommonResult.fail("参数错误");
		}
		Long userId = SwordUtils.getUserId();
		return withdrawalOrderService.withdrawal(userId, symbol, address, amount, password);
	}

	@ApiOperation(value = "提现申请+确认（二合一）", notes = "提币订单创建后，自动调用确认接口")
	@PostMapping("/withdrawal/auto")
	@RepeatSubmit
	public CommonResult<?> withdrawalAuto(String symbol, String address, BigDecimal amount, String password,
			String code) {
		CommonResult<?> verifyWalletPassword = SwordMiningUtils.verifyWalletPassword(password);
		if (!verifyWalletPassword.isSuccess()) {
			return CommonResult.fail(verifyWalletPassword.getInfo());
		}
		CommonResult<?> verifyCode = SwordUtils.verifyCode(code);
		if (!verifyCode.isSuccess()) {
			return CommonResult.fail(verifyCode.getInfo());
		}
		if (StringUtils.isEmpty(symbol) || StringUtils.isEmpty(address) || MathUtils.isEmpty(amount)
				|| StringUtils.isEmpty(password)) {
			return CommonResult.fail("参数错误");
		}
		Long userId = SwordUtils.getUserId();
		CommonResult<UserWithdrawalOrder> withdrawal = withdrawalOrderService.withdrawal(userId, symbol, address,
				amount, password);
		if (!withdrawal.isSuccess(true)) {
			return withdrawal;
		}
		UserWithdrawalOrder order = withdrawal.getData();
		return withdrawalOrderService.confirmWithdrawal(userId, order.getOrderNo());
	}

	@ApiOperation(value = "提现取消", notes = "取消提现")
	@PostMapping("/withdrawal/cancel")
	@RepeatSubmit
	public CommonResult<?> cancelWithdrawal(String orderNo, String code, String remark) {
		CommonResult<?> verifyCode = SwordUtils.verifyCode(code);
		if (!verifyCode.isSuccess()) {
			return CommonResult.fail(verifyCode.getInfo());
		}
		if (StringUtils.isEmpty(orderNo)) {
			return CommonResult.fail("参数错误");
		}
		Long userId = SwordUtils.getUserId();
		return withdrawalOrderService.cancelWithdrawal(userId, orderNo, remark);
	}

	@ApiOperation(value = "提现确认", notes = "确认提现")
	@PostMapping("/withdrawal/confirm")
	@RepeatSubmit
	public CommonResult<?> confirmWithdrawal(String orderNo, String code, String remark) {
		CommonResult<?> verifyCode = SwordUtils.verifyCode(code);
		if (!verifyCode.isSuccess()) {
			return CommonResult.fail(verifyCode.getInfo());
		}
		if (StringUtils.isEmpty(orderNo)) {
			return CommonResult.fail("参数错误");
		}
		Long userId = SwordUtils.getUserId();
		return withdrawalOrderService.confirmWithdrawal(userId, orderNo);
	}
}
