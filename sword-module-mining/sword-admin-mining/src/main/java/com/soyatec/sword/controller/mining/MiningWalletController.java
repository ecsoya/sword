package com.soyatec.sword.controller.mining;

import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.page.TableDataInfo;
import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.order.domain.UserDepositOrder;
import com.soyatec.sword.order.domain.UserWithdrawalOrder;
import com.soyatec.sword.order.service.IUserDepositOrderService;
import com.soyatec.sword.order.service.IUserWithdrawalOrderService;

@Controller
@RequestMapping("/mining/wallet")
public class MiningWalletController extends BaseController {

	private static final String prefix = "mining/wallet";

	@Autowired
	private IUserDepositOrderService userDepositOrderService;

	@Autowired
	private IUserWithdrawalOrderService userWithdrawalOrderService;

	@RequiresPermissions("mining:wallet:withdrawal:view")
	@GetMapping("/withdrawal")
	public String withdrawal() {
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
		List<UserWithdrawalOrder> list = userWithdrawalOrderService.selectUserWithdrawalOrderList(userWithdrawalOrder);
		TableDataInfo table = getDataTable(list);
		userWithdrawalOrder.setStatus(UserWithdrawalOrder.STATUS_SUCCESS);
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
	public String deposit() {
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
		List<UserDepositOrder> list = userDepositOrderService.selectUserDepositOrderList(userDepositOrder);
		TableDataInfo table = getDataTable(list);
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
}