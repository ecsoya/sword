package com.github.ecsoya.sword.mining.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.ecsoya.sword.business.service.IWalletCallbackService;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.utils.IdWorker;
import com.github.ecsoya.sword.common.utils.MessageUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.async.AsyncManager;
import com.github.ecsoya.sword.constants.IConstants;
import com.github.ecsoya.sword.constants.IMiningConstants;
import com.github.ecsoya.sword.exceptions.TransactionException;
import com.github.ecsoya.sword.order.domain.UserDepositOrder;
import com.github.ecsoya.sword.order.domain.UserWithdrawalOrder;
import com.github.ecsoya.sword.order.service.IUserDepositOrderService;
import com.github.ecsoya.sword.order.service.IUserWithdrawalOrderService;
import com.github.ecsoya.sword.record.domain.UserDepositRecord;
import com.github.ecsoya.sword.record.domain.UserWithdrawalRecord;
import com.github.ecsoya.sword.record.service.IUserDepositRecordService;
import com.github.ecsoya.sword.record.service.IUserWithdrawalRecordService;
import com.github.ecsoya.sword.utils.MathUtils;
import com.github.ecsoya.sword.wallet.domain.UserWalletAccount;
import com.github.ecsoya.sword.wallet.service.IUserWalletAccountService;
import com.github.ecsoya.sword.zbx.domain.ZbxOrderRawData;

/**
 * The Class ZbxWalletCallbackService.
 */
@Service
public class ZbxWalletCallbackService implements IWalletCallbackService<ZbxOrderRawData> {

	/** The user wallet account service. */
	@Autowired
	private IUserWalletAccountService userWalletAccountService;

	/** The user deposite order service. */
	@Autowired
	private IUserDepositOrderService userDepositeOrderService;

	/** The user deposite record service. */
	@Autowired
	private IUserDepositRecordService userDepositeRecordService;

	/** The user withdrawal order service. */
	@Autowired
	private IUserWithdrawalOrderService userWithdrawalOrderService;

	/** The user withdrawal record service. */
	@Autowired
	private IUserWithdrawalRecordService userWithdrawalRecordService;

	/**
	 * Process order.
	 *
	 * @param rawData the raw data
	 * @return the common result
	 */
	@Override
	@Transactional(rollbackFor = TransactionException.class)
	public CommonResult<?> processOrder(ZbxOrderRawData rawData) {
		if (rawData == null) {
			return CommonResult.fail("????????????"); //$NON-NLS-1$
		}
		if (rawData.isDeposit()) {
			return processDepositeOrder(rawData);
		} else if (rawData.isWithdrawal()) {
			return processWithdrawalOrder(rawData);
		}
		return CommonResult.fail("????????????"); //$NON-NLS-1$
	}

	/**
	 * Process withdrawal order.
	 *
	 * @param rawData the raw data
	 * @return the common result
	 */
	private CommonResult<?> processWithdrawalOrder(ZbxOrderRawData rawData) {
		if (rawData == null || !rawData.isWithdrawal()) {
			return CommonResult.fail("?????????????????????"); //$NON-NLS-1$
		}
		final String orderNo = rawData.getOrderNo();
		if (StringUtils.isEmpty(orderNo)) {
			return CommonResult.fail("????????????????????????");
		}
		final UserWithdrawalOrder order = userWithdrawalOrderService.selectUserWithdrawalOrderByOrderNo(orderNo);
		if (order == null) {
			return CommonResult.fail("?????????????????????");
		}
		// ?????????????????????????????????
		else if (!UserWithdrawalOrder.STATUS_CONFIRM.equals(order.getStatus())) {
			// ??????????????????
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
				// ????????????
				addWithdrawalRecord(userId, amount, fee, orderNo, symbol, false, "??????????????????????????????"); //$NON-NLS-1$
				return result(orderId, UserWithdrawalOrder.STATUS_FAILURE, UserWithdrawalOrder.FEEDBACK_FAILURE,
						"??????????????????????????????", //$NON-NLS-1$
						true);
			}
			// ??????
			else if (ZbxOrderRawData.WITHDRAWAL_SUCCESS.equals(status)) {
				// ????????????
				addWithdrawalRecord(userId, amount, fee, orderNo, symbol, true, "??????????????????????????????"); //$NON-NLS-1$
				return result(orderId, UserWithdrawalOrder.STATUS_SUCCESS, UserWithdrawalOrder.FEEDBACK_SUCCESS,
						"??????????????????????????????", //$NON-NLS-1$
						true);
			}
			// ??????
			else if (ZbxOrderRawData.WITHDRAWAL_CANCEL.equals(status)) {
				// ?????????????????????
				addWithdrawalRecord(userId, amount, fee, orderNo, symbol, false, "??????????????????????????????"); //$NON-NLS-1$
				return result(orderId, UserWithdrawalOrder.STATUS_CANCEL, UserWithdrawalOrder.FEEDBACK_FAILURE,
						"??????????????????????????????", true); //$NON-NLS-1$
			}
			return result(orderId, UserWithdrawalOrder.STATUS_CONFIRM, UserWithdrawalOrder.FEEDBACK_NONE, "??????????????????", //$NON-NLS-1$
					true);
		}
	}

	/**
	 * Adds the withdrawal record.
	 *
	 * @param userId  the user id
	 * @param amount  the amount
	 * @param fee     the fee
	 * @param orderNo the order no
	 * @param symbol  the symbol
	 * @param success the success
	 * @param remark  the remark
	 * @return true, if successful
	 */
	private boolean addWithdrawalRecord(Long userId, BigDecimal amount, BigDecimal fee, String orderNo, String symbol,
			boolean success, String remark) {
		// 1. ??????
		final boolean unfreezed = userWalletAccountService.unfreezeAmount(userId, symbol, amount, orderNo, !success);
		if (!unfreezed) {
			throw new TransactionException("????????????");
		}
		// 2. ????????????
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
			throw new TransactionException("????????????");
		}
		return true;
	}

	/**
	 * Sets the withdrawal order feedback.
	 *
	 * @param orderNo  the order no
	 * @param feedback the feedback
	 * @return the int
	 */
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

	/**
	 * Result.
	 *
	 * @param orderId  the order id
	 * @param status   the status
	 * @param feedback the feedback
	 * @param remark   the remark
	 * @param success  the success
	 * @return the common result
	 */
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

	/**
	 * Process deposite order.
	 *
	 * @param rawData the raw data
	 * @return the common result
	 */
	private CommonResult<?> processDepositeOrder(ZbxOrderRawData rawData) {
		if (rawData == null || !rawData.isDeposit()) {
			return CommonResult.fail("?????????????????????"); //$NON-NLS-1$
		}
		final String status = rawData.getStatus();
		if (!ZbxOrderRawData.DEPOSIT_SUCCESS.equals(status)) {
			return CommonResult.success("?????????????????????");
		}
		// ??????????????????ID??????????????????????????????
		final String txId = rawData.getTxId();
		UserDepositRecord record = userDepositeRecordService.selectUserDepositRecordByTxId(txId);
		if (record != null) {
			return CommonResult.success("????????????");
		}

		final String symbol = rawData.getSymbol();
		final String address = rawData.getAddress();
		final UserWalletAccount account = userWalletAccountService.selectUserWalletAccountByAddress(address, symbol);
		if (account == null) {
			return CommonResult.fail("?????????????????????"); //$NON-NLS-1$
		}
		final Long userId = account.getUserId();
		final BigDecimal amount = MathUtils.parse(rawData.getAmount());
		if (MathUtils.isInvalid(amount)) {
			return CommonResult.fail("??????????????????"); //$NON-NLS-1$
		}

		// ??????????????????0.001????????????
		if (MathUtils.lt(amount, BigDecimal.valueOf(0.001))) {
			return CommonResult.success("??????????????????0.001");
		}

		final String orderNo = IdWorker.get32UUID();
		// 1. ????????????
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
			throw new TransactionException("????????????????????????");
		}

		// 2.????????????
		final boolean deposited = userWalletAccountService.increaseAmount(account, amount, orderNo, "????????????",
				IMiningConstants.DETAILS_DEPOSIT);
		if (!deposited) {
			throw new TransactionException("????????????????????????");
		}

		// 3.????????????
		record = new UserDepositRecord();
		record.setTxId(txId);
		record.setUserId(userId);
		record.setOrderNo(orderNo);
		record.setType(UserDepositRecord.TYPE_WALLET);
		record.setSymbol(symbol);
		record.setAmount(amount);
		record.setStatus(IConstants.STATUS_FINISHED);
		record.setRemark("External Deposit Success");
		rows = userDepositeRecordService.insertUserDepositRecord(record);
		if (rows <= 0) {
			throw new TransactionException("????????????????????????");
		}
		// ????????????????????????????????????????????????????????????
		AsyncManager.me().execute(new Runnable() {
			@Override
			public void run() {
				setWithdrawalOrderFeedback(orderNo, UserWithdrawalOrder.FEEDBACK_SUCCESS);
			}
		});
		return CommonResult.success("????????????");

	}

}
