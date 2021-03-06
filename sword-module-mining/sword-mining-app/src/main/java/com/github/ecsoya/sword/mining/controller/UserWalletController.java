package com.github.ecsoya.sword.mining.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ecsoya.sword.business.service.IWithdrawalNotifyService;
import com.github.ecsoya.sword.common.annotation.RepeatSubmit;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.core.page.TableDataInfo;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.async.AsyncManager;
import com.github.ecsoya.sword.constants.IMiningConstants;
import com.github.ecsoya.sword.mining.domain.MiningSymbol;
import com.github.ecsoya.sword.mining.service.IMiningSymbolService;
import com.github.ecsoya.sword.mining.utils.SwordMiningUtils;
import com.github.ecsoya.sword.order.domain.UserWithdrawalOrder;
import com.github.ecsoya.sword.order.service.IUserWithdrawalOrderService;
import com.github.ecsoya.sword.system.service.ISysConfigService;
import com.github.ecsoya.sword.utils.MathUtils;
import com.github.ecsoya.sword.utils.SwordUtils;
import com.github.ecsoya.sword.wallet.domain.UserWallet;
import com.github.ecsoya.sword.wallet.domain.UserWalletAccount;
import com.github.ecsoya.sword.wallet.domain.UserWalletRecord;
import com.github.ecsoya.sword.wallet.domain.UserWalletUnionRecord;
import com.github.ecsoya.sword.wallet.service.IUserWalletAccountService;
import com.github.ecsoya.sword.wallet.service.IUserWalletRecordService;
import com.github.ecsoya.sword.wallet.service.IUserWalletService;
import com.github.ecsoya.sword.wallet.service.IUserWalletUnionRecordService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * The Class UserWalletController.
 */
@RestController
@RequestMapping("/user/wallet")
@Api(tags = { "钱包" }, description = "账户、支付密码、提现")
public class UserWalletController extends BaseController {

	/** The user wallet service. */
	@Autowired
	public IUserWalletService userWalletService;

	/** The withdrawal order service. */
	@Autowired
	private IUserWithdrawalOrderService withdrawalOrderService;

	/** The user wallet account service. */
	@Autowired
	private IUserWalletAccountService userWalletAccountService;

	/** The wallet record service. */
	@Autowired
	private IUserWalletUnionRecordService walletRecordService;

	/** The wallet detail service. */
	@Autowired
	private IUserWalletRecordService walletDetailService;

	/** The config service. */
	@Autowired
	private ISysConfigService configService;

	/** The symbol service. */
	@Autowired
	private IMiningSymbolService symbolService;

	/** The withdrawal notify service. */
	@Autowired
	private IWithdrawalNotifyService withdrawalNotifyService;

	/**
	 * Records.
	 *
	 * @param symbol the symbol
	 * @param start  the start
	 * @param end    the end
	 * @param kind   the kind
	 * @return the table data info
	 */
	@ApiOperation(value = "查询钱包记录一", notes = "此API仅查询成功的充值和提币记录")
	@GetMapping("/records")
	public TableDataInfo records(@ApiParam(required = true) String symbol, @ApiParam("起始时间 yyyy-MM-dd") Date start,
			@ApiParam("结束时间 yyyy-MM-dd") Date end, @ApiParam(value = "1-充值，2-提币，留空为所有") Integer kind) {
		if (start != null) {
			start = DateUtils.getStartOf(start);
		}
		if (end != null) {
			end = DateUtils.getEndOf(end);
		}
		startPage("create_time desc");
		final Long userId = SwordUtils.getUserId();
		final List<UserWalletUnionRecord> list = walletRecordService.selectWalletRecordListByUserId(userId, symbol,
				start, end, kind);
		return getDataTable(list);
	}

	/**
	 * Orders.
	 *
	 * @param symbol the symbol
	 * @param start  the start
	 * @param end    the end
	 * @param kind   the kind
	 * @return the table data info
	 */
	@ApiOperation(value = "查询钱包记录二", notes = "此API查询所有的提币和充值的订单，成功与否都会查询。1. kind==1充值订单；kind==2提币订单 2. kind==1充值订单，status==0待同步，status=1充值成功；kind==2提币订单：status=0待确认，status=1取消（提币被拒绝），status=2成功（调用接口成功），status=3失败（接口调用失败），status=4确认（提币订单已确认），status=5到账（提币已到账）")
	@GetMapping("/orders")
	public TableDataInfo orders(@ApiParam(required = true) String symbol, @ApiParam("起始时间 yyyy-MM-dd") Date start,
			@ApiParam("结束时间 yyyy-MM-dd") Date end, @ApiParam(value = "1-充值，2-提币，留空为所有") Integer kind) {
		if (start != null) {
			start = DateUtils.getStartOf(start);
		}
		if (end != null) {
			end = DateUtils.getEndOf(end);
		}
		startPage("create_time desc");
		final Long userId = SwordUtils.getUserId();
		final List<UserWalletUnionRecord> list = walletRecordService.selectWalletOrderListByUserId(userId, symbol,
				start, end, kind);
		return getDataTable(list);
	}

	/**
	 * Details.
	 *
	 * @param symbol  the symbol
	 * @param start   the start
	 * @param end     the end
	 * @param kind    the kind
	 * @param type    the type
	 * @param details the details
	 * @return the table data info
	 */
	@ApiOperation(value = "查询钱包记录三", notes = "此API查询钱包各种余额变动的所有记录，成功与否都会查询")
	@GetMapping("/details")
	public TableDataInfo details(@ApiParam(required = true) String symbol, @ApiParam("起始时间 yyyy-MM-dd") Date start,
			@ApiParam("结束时间 yyyy-MM-dd") Date end, @ApiParam(value = "0-可用余额，1-冻结余额（质押余额）2-锁定余额，留空为可用余额") Integer kind,
			@ApiParam(value = "0-收入，1-支出，2-后台设定，留空为所有") Integer type, @ApiParam(value = "具体的类型，查看备注") Integer details) {
		if (start != null) {
			start = DateUtils.getStartOf(start);
		}
		if (end != null) {
			end = DateUtils.getEndOf(end);
		}
		startPage("create_time desc");
		final Long userId = SwordUtils.getUserId();
		UserWalletRecord query = new UserWalletRecord();
		query.setSymbol(symbol);
		query.setUserId(userId);
		query.setTimeParams(start, end);
		if (kind == null) {
			kind = 0;
		}
		query.setKind(kind);
		query.setType(type);
		query.setDetails(details);

		final List<UserWalletRecord> list = walletDetailService.selectUserWalletRecordList(query);
		return getDataTable(list);
	}

	/**
	 * Info.
	 *
	 * @return the common result
	 */
	@ApiOperation("查询钱包信息")
	@GetMapping("/info")
	public CommonResult<UserWallet> info() {
		return CommonResult.build(userWalletService.selectUserWalletById(SwordUtils.getUserId(), true));
	}

	/**
	 * Account.
	 *
	 * @param symbol the symbol
	 * @return the common result
	 */
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

	/**
	 * Check password.
	 *
	 * @return the common result
	 */
	@ApiOperation("检测钱包密码是否是初始值，如果是，提醒用户修改，code==200说明正常，code==500说明异常，提示info，并跳转到修改页面")
	@PostMapping("/checkPwd")
	@RepeatSubmit
	public CommonResult<?> checkPassword() {
		final String password = configService.selectConfigValueByKey(IMiningConstants.WALLET_DEFAULT_PASSWORD);
		if (StringUtils.isNotEmpty(password)) {
			final CommonResult<?> verified = SwordMiningUtils.verifyWalletPassword(password);
			if (verified.isSuccess()) {
				return CommonResult.fail("默认支付密码，请及时修改");
			}
		}
		final Long userId = SwordUtils.getUserId();
		final UserWallet wallet = userWalletService.selectUserWalletById(userId);
		if (wallet == null || StringUtils.isEmpty(wallet.getPassword())) {
			return CommonResult.fail("未设置支付密码");
		}
		return CommonResult.success("支付密码正常");
	}

	/**
	 * Change password.
	 *
	 * @param oldPassword the old password
	 * @param newPassword the new password
	 * @param code        the code
	 * @return the common result
	 */
	@ApiOperation("更新钱包/支付密码")
	@PostMapping("/changePwd")
	@RepeatSubmit
	public CommonResult<?> changePassword(String oldPassword, String newPassword, String code) {
		final CommonResult<?> verifyCode = SwordUtils.verifyCode(code);
		if (!verifyCode.isSuccess()) {
			return CommonResult.fail(verifyCode.getInfo());
		}
		final Long userId = SwordUtils.getUserId();
		return userWalletService.changeUserWalletPassword(userId, oldPassword, newPassword);
	}

	/**
	 * Reset password.
	 *
	 * @param password the password
	 * @param code     the code
	 * @return the common result
	 */
	@ApiOperation("重置钱包/支付密码")
	@PostMapping("/resetPwd")
	@RepeatSubmit
	public CommonResult<?> resetPassword(String password, String code) {
		final CommonResult<?> verifyCode = SwordUtils.verifyCode(code);
		if (!verifyCode.isSuccess()) {
			return CommonResult.fail(verifyCode.getInfo());
		}
		final Long userId = SwordUtils.getUserId();
		return CommonResult.ajax(userWalletService.resetUserWalletPassword(userId, password));
	}

	/**
	 * Withdrawal.
	 *
	 * @param symbol   the symbol
	 * @param address  the address
	 * @param amount   the amount
	 * @param password the password
	 * @param code     the code
	 * @return the common result
	 */
	@ApiOperation(value = "提现申请", notes = "提币申请，需调用/confirm接口确认提币")
	@PostMapping("/withdrawal")
	@RepeatSubmit
	public CommonResult<UserWithdrawalOrder> withdrawal(String symbol, String address, BigDecimal amount,
			String password, String code) {
		final CommonResult<?> verifyWalletPassword = SwordMiningUtils.verifyWalletPassword(password);
		if (!verifyWalletPassword.isSuccess()) {
			return CommonResult.fail(verifyWalletPassword.getInfo());
		}
		final CommonResult<?> verifyCode = SwordUtils.verifyCode(code);
		if (!verifyCode.isSuccess()) {
			return CommonResult.fail(verifyCode.getInfo());
		}
		if (StringUtils.isEmpty(symbol) || StringUtils.isEmpty(address) || MathUtils.isEmpty(amount)
				|| StringUtils.isEmpty(password)) {
			return CommonResult.fail("参数错误");
		}
		final Long userId = SwordUtils.getUserId();
		return withdrawalOrderService.withdrawal(userId, symbol, address, amount, password);
	}

	/**
	 * Withdrawal auto.
	 *
	 * @param symbol   the symbol
	 * @param address  the address
	 * @param amount   the amount
	 * @param password the password
	 * @param code     the code
	 * @return the common result
	 */
	@ApiOperation(value = "提现申请+确认（二合一）", notes = "提币订单创建后，自动调用确认接口")
	@PostMapping("/withdrawal/auto")
	@RepeatSubmit
	public CommonResult<?> withdrawalAuto(String symbol, String address, BigDecimal amount, String password,
			String code) {
		final CommonResult<?> verifyWalletPassword = SwordMiningUtils.verifyWalletPassword(password);
		if (!verifyWalletPassword.isSuccess()) {
			return CommonResult.fail(verifyWalletPassword.getInfo());
		}
		final CommonResult<?> verifyCode = SwordUtils.verifyCode(code);
		if (!verifyCode.isSuccess()) {
			return CommonResult.fail(verifyCode.getInfo());
		}
		if (StringUtils.isEmpty(symbol) || StringUtils.isEmpty(address) || MathUtils.isEmpty(amount)
				|| StringUtils.isEmpty(password)) {
			return CommonResult.fail("参数错误");
		}
		final Long userId = SwordUtils.getUserId();
		final CommonResult<UserWithdrawalOrder> withdrawal = withdrawalOrderService.withdrawal(userId, symbol, address,
				amount, password);
		if (!withdrawal.isSuccess(true)) {
			return withdrawal;
		}
		final MiningSymbol miningSymbol = symbolService.selectMiningSymbolById(symbol);
		// 如果不需要人工审核，直接确认
		if (miningSymbol != null && !miningSymbol.needWithdrawalManualAudit(amount)) {
			final UserWithdrawalOrder order = withdrawal.getData();
			return withdrawalOrderService.confirmWithdrawal(userId, order.getOrderNo());
		} else {
			// 发消息给系统管理员
			AsyncManager.me().execute(new Runnable() {
				@Override
				public void run() {
					withdrawalNotifyService.notify(withdrawal.getData());
				}
			});
			return withdrawal;
		}
	}

	/**
	 * Cancel withdrawal.
	 *
	 * @param orderNo the order no
	 * @param code    the code
	 * @param remark  the remark
	 * @return the common result
	 */
	@ApiOperation(value = "提现取消", notes = "取消提现")
	@PostMapping("/withdrawal/cancel")
	@RepeatSubmit
	public CommonResult<?> cancelWithdrawal(String orderNo, String code, String remark) {
		final CommonResult<?> verifyCode = SwordUtils.verifyCode(code);
		if (!verifyCode.isSuccess()) {
			return CommonResult.fail(verifyCode.getInfo());
		}
		if (StringUtils.isEmpty(orderNo)) {
			return CommonResult.fail("参数错误");
		}
		final Long userId = SwordUtils.getUserId();
		return withdrawalOrderService.cancelWithdrawal(userId, orderNo, remark);
	}

	/**
	 * Confirm withdrawal.
	 *
	 * @param orderNo the order no
	 * @param code    the code
	 * @param remark  the remark
	 * @return the common result
	 */
	@ApiOperation(value = "提现确认", notes = "确认提现")
	@PostMapping("/withdrawal/confirm")
	@RepeatSubmit
	public CommonResult<?> confirmWithdrawal(String orderNo, String code, String remark) {
		final CommonResult<?> verifyCode = SwordUtils.verifyCode(code);
		if (!verifyCode.isSuccess()) {
			return CommonResult.fail(verifyCode.getInfo());
		}
		if (StringUtils.isEmpty(orderNo)) {
			return CommonResult.fail("参数错误");
		}
		final Long userId = SwordUtils.getUserId();
		final UserWithdrawalOrder order = withdrawalOrderService.selectUserWithdrawalOrderByOrderNo(orderNo);
		if (order == null || !Objects.equals(userId, order.getUserId())) {
			return CommonResult.fail("订单错误");
		}
		final MiningSymbol miningSymbol = symbolService.selectMiningSymbolById(order.getSymbol());
		if (miningSymbol != null && miningSymbol.needWithdrawalManualAudit(order.getAmount())) {
			return CommonResult.success("等待人工审核");
		}
		return withdrawalOrderService.confirmWithdrawal(userId, orderNo);
	}
}
