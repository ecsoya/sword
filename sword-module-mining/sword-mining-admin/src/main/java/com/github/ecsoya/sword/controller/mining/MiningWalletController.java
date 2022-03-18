package com.github.ecsoya.sword.controller.mining;

import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.ecsoya.sword.common.annotation.Log;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.core.page.TableDataInfo;
import com.github.ecsoya.sword.common.enums.BusinessType;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.mining.domain.MiningSymbol;
import com.github.ecsoya.sword.mining.service.IMiningSymbolService;
import com.github.ecsoya.sword.order.domain.UserDepositOrder;
import com.github.ecsoya.sword.order.domain.UserWithdrawalManual;
import com.github.ecsoya.sword.order.domain.UserWithdrawalOrder;
import com.github.ecsoya.sword.order.service.IUserDepositOrderService;
import com.github.ecsoya.sword.order.service.IUserWithdrawalOrderService;
import com.github.ecsoya.sword.user.service.IUserProfileService;
import com.github.ecsoya.sword.wallet.domain.UserWalletAccount;
import com.github.ecsoya.sword.wallet.domain.UserWalletRecord;
import com.github.ecsoya.sword.wallet.service.IUserWalletAccountService;
import com.github.ecsoya.sword.wallet.service.IUserWalletRecordService;
import com.github.ecsoya.sword.wallet.service.IWalletService;

@Controller
@RequestMapping("/mining/wallet")
public class MiningWalletController extends BaseController {

	private static final String prefix = "mining/wallet";

	@Autowired
	private IUserDepositOrderService userDepositOrderService;

	@Autowired
	private IUserWithdrawalOrderService userWithdrawalOrderService;

	@Autowired
	private IUserWalletRecordService walletRecordService;

	@Autowired
	private IMiningSymbolService symbolService;

	@Autowired
	private IUserProfileService profileService;

	@Autowired
	private IUserWalletAccountService walletAccountService;

	@Autowired
	private IWalletService walletService;

	@RequiresPermissions("mining:wallet:withdrawal:view")
	@GetMapping("/withdrawal")
	public String withdrawal(ModelMap mmap) {
		mmap.put("symbols", symbolService.selectMiningSymbolList(new MiningSymbol()));
		mmap.put("local", walletService.isLocal());
		return prefix + "/withdrawal";
	}

	/**
	 * 查询提现订单列表
	 */
	@RequiresPermissions("mining:wallet:withdrawal:list")
	@PostMapping("/withdrawal/list")
	@ResponseBody
	public TableDataInfo list(UserWithdrawalOrder userWithdrawalOrder, Date start, Date end) {
		userWithdrawalOrder.setTimeParams(start, end);
		startPage();
		final List<UserWithdrawalOrder> list = userWithdrawalOrderService
				.selectUserWithdrawalOrderList(userWithdrawalOrder);
		final TableDataInfo table = getDataTable(list);
//		userWithdrawalOrder.setStatus(UserWithdrawalOrder.STATUS_SUCCESS);
		userWithdrawalOrder.setTimeParams(DateUtils.getLastDayStart(), DateUtils.getLastDayEnd());
		table.addParam("昨日提币", userWithdrawalOrderService.selectUserWithdrawalAmount(userWithdrawalOrder));
		userWithdrawalOrder.setTimeParams(DateUtils.getTodayStart(), null);
		table.addParam("今日提币", userWithdrawalOrderService.selectUserWithdrawalAmount(userWithdrawalOrder));
		userWithdrawalOrder.setTimeParams(start, end);
		table.addParam("区间提币", userWithdrawalOrderService.selectUserWithdrawalAmount(userWithdrawalOrder));
		userWithdrawalOrder.setTimeParams(null, null);
		table.addParam("累积提币", userWithdrawalOrderService.selectUserWithdrawalAmount(userWithdrawalOrder));
		return table;
	}

	@RequiresPermissions("mining:wallet:deposit:view")
	@GetMapping("/deposit")
	public String deposit(ModelMap mmap) {
		mmap.put("symbols", symbolService.selectMiningSymbolList(new MiningSymbol()));
		return prefix + "/deposit";
	}

	/**
	 * 查询充值订单列表
	 */
	@RequiresPermissions("mining:wallet:deposit:list")
	@PostMapping("/deposit/list")
	@ResponseBody
	public TableDataInfo list(UserDepositOrder userDepositOrder, Date start, Date end) {
		userDepositOrder.setTimeParams(start, end);
		startPage();
		final List<UserDepositOrder> list = userDepositOrderService.selectUserDepositOrderList(userDepositOrder);
		final TableDataInfo table = getDataTable(list);
		userDepositOrder.setTimeParams(DateUtils.getLastDayStart(), DateUtils.getLastDayEnd());
		table.addParam("昨日充值", userDepositOrderService.selectUserDepositAmount(userDepositOrder));
		userDepositOrder.setTimeParams(DateUtils.getTodayStart(), null);
		table.addParam("今日充值", userDepositOrderService.selectUserDepositAmount(userDepositOrder));
		userDepositOrder.setTimeParams(start, end);
		table.addParam("区间充值", userDepositOrderService.selectUserDepositAmount(userDepositOrder));
		userDepositOrder.setTimeParams(null, null);
		table.addParam("累积充值", userDepositOrderService.selectUserDepositAmount(userDepositOrder));
		return table;
	}

	@RequiresPermissions("mining:wallet:withdrawal:edit")
	@Log(title = "审核通过提现订单", businessType = BusinessType.UPDATE)
	@PostMapping("/withdrawal/checkPassed")
	@ResponseBody
	public AjaxResult checkPassed(String ids) {
		final CommonResult<?> result = userWithdrawalOrderService.confirmWithdrawal(ids);
		if (result.isSuccess()) {
			return AjaxResult.success();
		}
		return AjaxResult.error(result.getInfo());
	}

	@RequiresPermissions("mining:wallet:withdrawal:edit")
	@Log(title = "审核拒绝提现订单", businessType = BusinessType.UPDATE)
	@PostMapping("/withdrawal/checkDenied")
	@ResponseBody
	public AjaxResult checkDenied(String ids, String remark) {
		final CommonResult<?> result = userWithdrawalOrderService.cancelWithdrawal(ids, remark);
		if (result.isSuccess()) {
			return AjaxResult.success();
		}
		return AjaxResult.error(result.getInfo());
	}

	@RequiresPermissions("mining:wallet:record:view")
	@GetMapping("/record")
	public String record(ModelMap mmap) {
		mmap.put("symbols", symbolService.selectMiningSymbolList(new MiningSymbol()));
		return prefix + "/record";
	}

	/**
	 * 查询提现订单列表
	 */
	@RequiresPermissions("mining:wallet:record:list")
	@PostMapping("/record/list")
	@ResponseBody
	public TableDataInfo record(UserWalletRecord record, Date start, Date end) {
		record.setTimeParams(start, end);
		startPage();
		if (record.getKind() == null) {
			record.setKind(UserWalletRecord.KIND_AMOUNT); // 默认为可用余额
		}
		final List<UserWalletRecord> list = walletRecordService.selectUserWalletRecordList(record);
		final TableDataInfo table = getDataTable(list);
		if (StringUtils.isNotEmpty(record.getLoginName()) && StringUtils.isNotEmpty(record.getSymbol())) {
			Long userId = profileService.selectUserIdByUsername(record.getLoginName());
			if (userId != null) {
				UserWalletAccount account = walletAccountService.selectUserWalletAccount(userId, record.getSymbol());
				if (account != null) {
					table.addParam("可用余额", account.getAmount());
					table.addParam("冻结余额", account.getFrozenAmount());
					table.addParam("锁定余额", account.getLockedAmount());
				}
			}
		}
		return table;
	}

	@RequiresPermissions("mining:wallet:withdrawal:view")
	@GetMapping("/withdrawal/manualRecord")
	public String manualRecord(Long id, ModelMap mmap) {
		mmap.put("order", userWithdrawalOrderService.selectUserWithdrawalOrderById(id));
		return prefix + "/manualRecord";
	}

	@RequiresPermissions("mining:wallet:withdrawal:edit")
	@Log(title = "手工核销提现订单", businessType = BusinessType.UPDATE)
	@PostMapping("/withdrawal/manualRecord")
	@ResponseBody
	public AjaxResult manualRecord(UserWithdrawalManual manual) {
		final CommonResult<?> result = userWithdrawalOrderService.manualWithdrawalRecord(manual);
		if (result.isSuccess()) {
			return AjaxResult.success();
		}
		return AjaxResult.error(result.getInfo());
	}
}