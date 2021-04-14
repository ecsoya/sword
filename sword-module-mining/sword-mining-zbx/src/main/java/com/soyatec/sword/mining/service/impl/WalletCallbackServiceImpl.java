package com.soyatec.sword.mining.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.utils.IdWorker;
import com.soyatec.sword.common.utils.MessageUtils;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.common.utils.async.AsyncManager;
import com.soyatec.sword.constants.IConstants;
import com.soyatec.sword.exceptions.TransactionException;
import com.soyatec.sword.mining.service.IWalletCallbackService;
import com.soyatec.sword.order.domain.UserDepositOrder;
import com.soyatec.sword.order.domain.UserWithdrawalOrder;
import com.soyatec.sword.order.service.IUserDepositOrderService;
import com.soyatec.sword.order.service.IUserWithdrawalOrderService;
import com.soyatec.sword.record.domain.UserDepositRecord;
import com.soyatec.sword.record.domain.UserWithdrawalRecord;
import com.soyatec.sword.record.service.IUserDepositRecordService;
import com.soyatec.sword.record.service.IUserWithdrawalRecordService;
import com.soyatec.sword.utils.MathUtils;
import com.soyatec.sword.wallet.domain.UserWalletAccount;
import com.soyatec.sword.wallet.service.IUserWalletAccountService;
import com.soyatec.sword.zbx.domain.ZbxOrderRawData;

@Service
public class WalletCallbackServiceImpl implements IWalletCallbackService {

	@Autowired
	private IUserWalletAccountService userWalletAccountService;

	@Autowired
	private IUserDepositOrderService userDepositeOrderService;

	@Autowired
	private IUserDepositRecordService userDepositeRecordService;

	@Autowired
	private IUserWithdrawalOrderService userWithdrawalOrderService;

	@Autowired
	private IUserWithdrawalRecordService userWithdrawalRecordService;

	@Override
	@Transactional(rollbackFor = TransactionException.class)
	public CommonResult<?> processOrder(ZbxOrderRawData rawData) {
		if (rawData == null) {
			return CommonResult.fail("参数错误"); //$NON-NLS-1$
		}
		if (rawData.isDeposit()) {
			return processDepositeOrder(rawData);
		} else if (rawData.isWithdrawal()) {
			return processWithdrawalOrder(rawData);
		}
		return CommonResult.fail("未知类型"); //$NON-NLS-1$
	}

	private CommonResult<?> processWithdrawalOrder(ZbxOrderRawData rawData) {
		if (rawData == null || !rawData.isWithdrawal()) {
			return CommonResult.fail("无效的提币订单"); //$NON-NLS-1$
		}
		final String orderNo = rawData.getOrderNo();
		if (StringUtils.isEmpty(orderNo)) {
			return CommonResult.fail("无效的提币订单号");
		}
		final UserWithdrawalOrder order = userWithdrawalOrderService.selectUserWithdrawalOrderByOrderNo(orderNo);
		if (order == null) {
			return CommonResult.fail("无效的提币订单");
		}
		// 如果不是正在等待的订单
		else if (!UserWithdrawalOrder.STATUS_CONFIRM.equals(order.getStatus())) {
			// 更新回调状态
			if (UserWithdrawalOrder.STATUS_SUCCESS.equals(order.getStatus())) {
				setWithdrawalOrderFeedback(orderNo, UserWithdrawalOrder.FEEDBACK_SUCCESS);
			} else {
				setWithdrawalOrderFeedback(orderNo, UserWithdrawalOrder.FEEDBACK_FAILURE);
			}
			return CommonResult.success(MessageUtils.message("WithdrawalOrderServiceImpl.4")); //$NON-NLS-1$
		} else {
			final String status = rawData.getStatus();
			final String symbol = rawData.getSymbol();
			final Long orderId = order.getId();
			final Long userId = order.getUserId();
			final BigDecimal amount = order.getAmount();
			final BigDecimal fee = order.getFee();

			if (ZbxOrderRawData.WITHDRAWAL_FAILURE.equals(status)) {
				// 关闭订单
				addWithdrawalRecord(userId, amount, fee, orderNo, symbol, false, "【回调成功】提现失败"); //$NON-NLS-1$
				return result(orderId, UserWithdrawalOrder.STATUS_FAILURE, UserWithdrawalOrder.FEEDBACK_FAILURE,
						"【回调成功】提现失败", //$NON-NLS-1$
						true);
			}
			// 成功
			else if (ZbxOrderRawData.WITHDRAWAL_SUCCESS.equals(status)) {
				// 关闭订单
				addWithdrawalRecord(userId, amount, fee, orderNo, symbol, true, "【回调成功】提现成功"); //$NON-NLS-1$
				return result(orderId, UserWithdrawalOrder.STATUS_SUCCESS, UserWithdrawalOrder.FEEDBACK_SUCCESS,
						"【回调成功】提现成功", //$NON-NLS-1$
						true);
			}
			// 取消
			else if (ZbxOrderRawData.WITHDRAWAL_CANCEL.equals(status)) {
				// 提现取消，解冻
				addWithdrawalRecord(userId, amount, fee, orderNo, symbol, false, "【回调成功】提现失败"); //$NON-NLS-1$
				return result(orderId, UserWithdrawalOrder.STATUS_CANCEL, UserWithdrawalOrder.FEEDBACK_FAILURE,
						"【回调成功】提现取消", true); //$NON-NLS-1$
			}
			return result(orderId, UserWithdrawalOrder.STATUS_CONFIRM, UserWithdrawalOrder.FEEDBACK_NONE, "【回调成功】", //$NON-NLS-1$
					true);
		}
	}

	private boolean addWithdrawalRecord(Long userId, BigDecimal amount, BigDecimal fee, String orderNo, String symbol,
			boolean success, String remark) {
		// 1. 扣款
		final boolean unfreezed = userWalletAccountService.unfreezeAmount(userId, symbol, amount, orderNo, !success);
		if (!unfreezed) {
			throw new TransactionException("钱包错误");
		}
		// 2. 生成记录
		final UserWithdrawalRecord record = new UserWithdrawalRecord();
		record.setUserId(userId);
		record.setFee(fee);
		record.setOrderNo(orderNo);
		record.setType(UserWithdrawalRecord.TYPE_WALLET);
		record.setSymbol(symbol);
		record.setAmount(amount);
		record.setStatus(IConstants.STATUS_FINISHED);
		record.setRemark(remark);
		if (userWithdrawalRecordService.insertUserWithdrawalRecord(record) <= 0) {
			throw new TransactionException("内部错误");
		}
		return true;
	}

	private int setWithdrawalOrderFeedback(String orderNo, Integer feedback) {
		final UserWithdrawalOrder order = userWithdrawalOrderService.selectUserWithdrawalOrderByOrderNo(orderNo);
		if (order == null) {
			return 0;
		}
		final UserWithdrawalOrder update = new UserWithdrawalOrder();
		update.setId(order.getId());
		update.setFeedback(feedback);
		update.setStatus(UserWithdrawalOrder.STATUS_NOTIFIED);
		return userWithdrawalOrderService.updateUserWithdrawalOrder(update);
	}

	private CommonResult<?> result(Long orderId, Integer status, Integer feedback, String remark, boolean success) {
		final UserWithdrawalOrder update = new UserWithdrawalOrder();
		update.setId(orderId);
		update.setStatus(status);
		update.setFeedback(feedback);
		update.setRemark(remark);
		userWithdrawalOrderService.updateUserWithdrawalOrder(update);
		if (success) {
			return CommonResult.success(update);
		}
		return CommonResult.fail(remark);
	}

	private CommonResult<?> processDepositeOrder(ZbxOrderRawData rawData) {
		if (rawData == null || !rawData.isDeposit()) {
			return CommonResult.fail("无效的充值订单"); //$NON-NLS-1$
		}
		final String status = rawData.getStatus();
		if (!ZbxOrderRawData.DEPOSIT_SUCCESS.equals(status)) {
			return CommonResult.success("非充值成功订单");
		}
		// 此为充值交易ID，可唯一识别充值订单
		final String txId = rawData.getTxId();
		UserDepositRecord record = userDepositeRecordService.selectUserDepositRecordByTxId(txId);
		if (record != null) {
			return CommonResult.success("重复订单");
		}

		final String symbol = rawData.getSymbol();
		final String address = rawData.getAddress();
		final UserWalletAccount account = userWalletAccountService.selectUserWalletAccountByAddress(address, symbol);
		if (account == null) {
			return CommonResult.fail("无效的充值地址"); //$NON-NLS-1$
		}
		final Long userId = account.getUserId();
		final BigDecimal amount = MathUtils.parse(rawData.getAmount());
		if (MathUtils.isInvalid(amount)) {
			return CommonResult.fail("充值金额错误"); //$NON-NLS-1$
		}

		// 充值金额小于0.001不于到账
		if (MathUtils.lt(amount, BigDecimal.valueOf(0.001))) {
			return CommonResult.success("充值金额小于0.001");
		}

		final String orderNo = IdWorker.get32UUID();
		// 1. 生成订单
		final UserDepositOrder order = new UserDepositOrder();
		order.setOrderNo(orderNo);
		order.setUserId(userId);
		order.setAddress(address);
		order.setAmount(amount);
		order.setSymbol(symbol);
		order.setTxId(txId);
		order.setStatus(IConstants.STATUS_FINISHED);
		int rows = userDepositeOrderService.insertUserDepositOrder(order);
		if (rows <= 0) {
			throw new TransactionException("内部错误【订单】");
		}

		// 2.更新钱包
		final boolean deposited = userWalletAccountService.increaseAmount(account, amount, orderNo, "充值");
		if (!deposited) {
			throw new TransactionException("内部错误【钱包】");
		}

		// 3.生成记录
		record = new UserDepositRecord();
		record.setTxId(txId);
		record.setUserId(userId);
		record.setOrderNo(orderNo);
		record.setType(UserDepositRecord.TYPE_WALLET);
		record.setSymbol(symbol);
		record.setAmount(amount);
		record.setStatus(IConstants.STATUS_FINISHED);
		record.setRemark("充值成功");
		rows = userDepositeRecordService.insertUserDepositRecord(record);
		if (rows <= 0) {
			throw new TransactionException("内部错误【记录】");
		}
		// 或许是内部转账订单，尝试刷新提币到账状态
		AsyncManager.me().execute(new Runnable() {
			@Override
			public void run() {
				setWithdrawalOrderFeedback(orderNo, UserWithdrawalOrder.FEEDBACK_SUCCESS);
			}
		});
		return CommonResult.success("充值成功");

	}

}
