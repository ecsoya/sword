package com.github.ecsoya.sword.order.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.IdWorker;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.constants.IConstants;
import com.github.ecsoya.sword.constants.IMiningConstants;
import com.github.ecsoya.sword.exceptions.TransactionException;
import com.github.ecsoya.sword.mining.domain.MiningSymbol;
import com.github.ecsoya.sword.mining.service.IMiningSymbolService;
import com.github.ecsoya.sword.order.domain.UserWithdrawalManual;
import com.github.ecsoya.sword.order.domain.UserWithdrawalOrder;
import com.github.ecsoya.sword.order.mapper.UserWithdrawalOrderMapper;
import com.github.ecsoya.sword.order.service.IUserWithdrawalOrderService;
import com.github.ecsoya.sword.record.domain.UserWithdrawalRecord;
import com.github.ecsoya.sword.record.service.IUserWithdrawalRecordService;
import com.github.ecsoya.sword.user.domain.UserCertificate;
import com.github.ecsoya.sword.user.service.IUserCertificateService;
import com.github.ecsoya.sword.utils.MathUtils;
import com.github.ecsoya.sword.wallet.domain.UserWalletAccount;
import com.github.ecsoya.sword.wallet.domain.WithdrawalResponse;
import com.github.ecsoya.sword.wallet.service.IUserWalletAccountService;
import com.github.ecsoya.sword.wallet.service.IUserWalletService;
import com.github.ecsoya.sword.wallet.service.IWalletService;

/**
 * The Class UserWithdrawalOrderServiceImpl.
 */
@Service
public class UserWithdrawalOrderServiceImpl implements IUserWithdrawalOrderService {

	/** The user withdrawal order mapper. */
	@Autowired
	private UserWithdrawalOrderMapper userWithdrawalOrderMapper;

	/** The user wallet service. */
	@Autowired
	private IUserWalletService userWalletService;

	/** The user wallet account service. */
	@Autowired
	private IUserWalletAccountService userWalletAccountService;

	/** The symbol service. */
	@Autowired
	private IMiningSymbolService symbolService;

	/** The wallet service. */
	@Autowired
	private IWalletService walletService;

	/** The user withdrawal record service. */
	@Autowired
	private IUserWithdrawalRecordService userWithdrawalRecordService;

	/** The certificate service. */
	@Autowired
	private IUserCertificateService certificateService;

	/**
	 * Select user withdrawal order by id.
	 *
	 * @param id the id
	 * @return the user withdrawal order
	 */
	@Override
	public UserWithdrawalOrder selectUserWithdrawalOrderById(Long id) {
		return userWithdrawalOrderMapper.selectUserWithdrawalOrderById(id);
	}

	/**
	 * Select user withdrawal order by order no.
	 *
	 * @param orderNo the order no
	 * @return the user withdrawal order
	 */
	@Override
	public UserWithdrawalOrder selectUserWithdrawalOrderByOrderNo(String orderNo) {
		if (StringUtils.isEmpty(orderNo)) {
			return null;
		}
		return userWithdrawalOrderMapper.selectUserWithdrawalOrderByOrderNo(orderNo);
	}

	/**
	 * Select user withdrawal order list.
	 *
	 * @param userWithdrawalOrder the user withdrawal order
	 * @return the list
	 */
	@Override
	public List<UserWithdrawalOrder> selectUserWithdrawalOrderList(UserWithdrawalOrder userWithdrawalOrder) {
		return userWithdrawalOrderMapper.selectUserWithdrawalOrderList(userWithdrawalOrder);
	}

	/**
	 * Insert user withdrawal order.
	 *
	 * @param userWithdrawalOrder the user withdrawal order
	 * @return the int
	 */
	@Override
	public int insertUserWithdrawalOrder(UserWithdrawalOrder userWithdrawalOrder) {
		if (userWithdrawalOrder.getId() == null) {
			userWithdrawalOrder.setId(IdWorker.getId());
		}
		if (userWithdrawalOrder.getCreateTime() == null) {
			userWithdrawalOrder.setCreateTime(DateUtils.getNowDate());
		}
		return userWithdrawalOrderMapper.insertUserWithdrawalOrder(userWithdrawalOrder);
	}

	/**
	 * Update user withdrawal order.
	 *
	 * @param userWithdrawalOrder the user withdrawal order
	 * @return the int
	 */
	@Override
	public int updateUserWithdrawalOrder(UserWithdrawalOrder userWithdrawalOrder) {
		userWithdrawalOrder.setUpdateTime(DateUtils.getNowDate());
		return userWithdrawalOrderMapper.updateUserWithdrawalOrder(userWithdrawalOrder);
	}

	/**
	 * Delete user withdrawal order by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteUserWithdrawalOrderByIds(String ids) {
		return userWithdrawalOrderMapper.deleteUserWithdrawalOrderByIds(Convert.toStrArray(ids));
	}

	/**
	 * Delete user withdrawal order by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Override
	public int deleteUserWithdrawalOrderById(Long id) {
		return userWithdrawalOrderMapper.deleteUserWithdrawalOrderById(id);
	}

	/**
	 * Withdrawal.
	 *
	 * @param userId   the user id
	 * @param symbol   the symbol
	 * @param address  the address
	 * @param amount   the amount
	 * @param password the password
	 * @return the common result
	 * @throws TransactionException the transaction exception
	 */
	@Override
	@Transactional(rollbackFor = TransactionException.class)
	public CommonResult<UserWithdrawalOrder> withdrawal(Long userId, String symbol, String address, BigDecimal amount,
			String password) throws TransactionException {
		if (userId == null || StringUtils.isEmpty(symbol) || StringUtils.isEmpty(address) || amount == null
				|| StringUtils.isEmpty(password)) {
			return CommonResult.fail("????????????");
		}
		final CommonResult<?> checked = certificateService.checkUserCertificate(userId,
				UserCertificate.KIND_WITHDRAWAL);
		if (!checked.isSuccess()) {
			return CommonResult.fail(checked.getInfo());
		}
		final MiningSymbol swordSymbol = symbolService.selectMiningSymbolById(symbol);
		if (swordSymbol == null) {
			return CommonResult.fail("????????????");
		}
		// 1. ??????????????????
		if (!swordSymbol.checkWithdrawalEnabled()) {
			return CommonResult.fail("????????????????????????");
		}
		// 2. ?????????????????????
		final BigDecimal withdrawalMinimum = swordSymbol.getWithdrawalMinimum();
		if (MathUtils.isPositive(withdrawalMinimum) && MathUtils.lt(amount, withdrawalMinimum)) {
			return CommonResult.fail("????????????????????????");
		}
		// 3. ?????????????????????
		final BigDecimal withdrawalMaximum = swordSymbol.getWithdrawalMaximum();
		if (MathUtils.isPositive(withdrawalMaximum) && MathUtils.gt(amount, withdrawalMaximum)) {
			return CommonResult.fail("??????????????????????????????");
		}
		// 4. ???????????????
		final BigDecimal withdrawalDaily = swordSymbol.getWithdrawalDaily();
		if (MathUtils.isPositive(withdrawalDaily)) {
			final BigDecimal todayAmount = selectUserWithdrawalAmountByDate(swordSymbol.getSymbol(),
					DateUtils.getTodayStart(), DateUtils.getNowDate());
			if (MathUtils.gt(MathUtils.plus(todayAmount, amount), withdrawalDaily)) {
				return CommonResult.fail("?????????????????????????????????");
			}
		}
		// 5. ???????????????
		final BigDecimal withdrawalTotally = swordSymbol.getWithdrawalTotally();
		if (MathUtils.isPositive(withdrawalTotally)) {
			final BigDecimal totallyAmount = selectUserWithdrawalAmountByDate(swordSymbol.getSymbol(), null, null);
			if (MathUtils.gt(MathUtils.plus(totallyAmount, amount), withdrawalTotally)) {
				return CommonResult.fail("?????????????????????????????????");
			}
		}
		// 6. ??????????????????
		final CommonResult<?> verified = userWalletService.verifyUserWalletPassword(userId, password);
		if (!verified.isSuccess()) {
			return CommonResult.fail("????????????");
		}
		// 7. ???????????????????????????????????????
		final UserWalletAccount walletAccount = userWalletAccountService.selectUserWalletAccount(userId, symbol);
		if (walletAccount == null || !walletAccount.chechWithdrawalEnabled()) {
			return CommonResult.fail("????????????");
		}
		// 8. ????????????
		if (MathUtils.isEmpty(walletAccount.getAmount()) || MathUtils.lt(walletAccount.getAmount(), amount)) {
			return CommonResult.fail("????????????");
		}
		final String orderNo = IdWorker.get32UUID();

//		?????????
		BigDecimal withdrawal = amount;
		BigDecimal fee = swordSymbol.getWithdrawalFee();
		if (MathUtils.isValid(fee)) {
			final String withdrawalFeeSymbol = swordSymbol.getWithdrawalFeeSymbol();
			// ???????????????
			if (IMiningConstants.SYMBOL_USDT.equals(withdrawalFeeSymbol)) {
				fee = walletService.exchangeFromUsdt(symbol, fee);
			}
			if (MathUtils.lte(amount, fee)) {
				return CommonResult.fail("???????????????");
			}
			withdrawal = withdrawal.subtract(fee);
		}
		// ????????????
		final UserWithdrawalOrder order = new UserWithdrawalOrder();
		order.setUserId(userId);
		order.setAddress(address);
		order.setAmount(amount);
		order.setFee(fee);
		order.setWithdrawal(withdrawal);
		order.setOrderNo(orderNo);
		order.setSymbol(symbol);
		order.setStatus(UserWithdrawalOrder.STATUS_NONE);
		order.setCallable(UserWithdrawalOrder.CALLABLE_NONE);
		order.setRemark("????????????");
		final int rows = insertUserWithdrawalOrder(order);
		if (rows <= 0) {
			throw new TransactionException("????????????");
		}
		// ????????????
		if (!userWalletAccountService.freezeAmount(walletAccount, amount, orderNo, null, "????????????",
				IMiningConstants.DETAILS_WITHDRAWAL)) {
			throw new TransactionException("????????????");
		}
		return CommonResult.build(order);
	}

	/**
	 * Select user withdrawal amount by date.
	 *
	 * @param symbol the symbol
	 * @param start  the start
	 * @param end    the end
	 * @return the big decimal
	 */
	private BigDecimal selectUserWithdrawalAmountByDate(String symbol, Date start, Date end) {
		return userWithdrawalOrderMapper.selectUserWithdrawalAmountByDate(symbol, start, end);
	}

	/**
	 * Cancel withdrawal.
	 *
	 * @param userId  the user id
	 * @param orderNo the order no
	 * @param remark  the remark
	 * @return the common result
	 * @throws TransactionException the transaction exception
	 */
	// ??????????????????
	@Override
	@Transactional(rollbackFor = TransactionException.class)
	public CommonResult<?> cancelWithdrawal(Long userId, String orderNo, String remark) throws TransactionException {
		final UserWithdrawalOrder order = selectUserWithdrawalOrderByOrderNo(orderNo);
		if (order == null || !Objects.equals(userId, order.getUserId())) {
			return CommonResult.fail("????????????");
		}
		if (!UserWithdrawalOrder.STATUS_NONE.equals(order.getStatus())) {
			return CommonResult.fail("???????????????");
		}
		final String symbol = order.getSymbol();
		final BigDecimal amount = order.getAmount();
		// ????????????
		if (!userWalletAccountService.unfreezeAmount(userId, symbol, amount, orderNo, true)) {
			return CommonResult.fail("??????????????????");
		}

		final UserWithdrawalOrder update = new UserWithdrawalOrder();
		update.setId(order.getId());
		update.setStatus(UserWithdrawalOrder.STATUS_CANCEL);
		if (StringUtils.isEmpty(remark)) {
			update.setRemark("????????????");
		} else {
			update.setRemark(remark);
		}
		final int rows = updateUserWithdrawalOrder(update);
		if (rows <= 0) {
			throw new TransactionException("????????????");
		}
		return CommonResult.build(orderNo);
	}

	/**
	 * Confirm withdrawal.
	 *
	 * @param orderNos the order nos
	 * @return the common result
	 * @throws TransactionException the transaction exception
	 */
	@Override
	@Transactional(rollbackFor = TransactionException.class)
	public CommonResult<?> confirmWithdrawal(String orderNos) throws TransactionException {
		if (StringUtils.isEmpty(orderNos)) {
			return CommonResult.fail("????????????");
		}
		final String[] array = Convert.toStrArray(orderNos);
		int rows = 0;
		for (final String orderNo : array) {
			final UserWithdrawalOrder order = selectUserWithdrawalOrderByOrderNo(orderNo);
			if (order == null) {
				continue;
			}
			try {
				final CommonResult<?> confirmed = confirmWithdrawal(order.getUserId(), orderNo);
				if (confirmed.isSuccess()) {
					rows++;
				}
			} catch (final TransactionException e) {
				// ??????????????????
				final UserWithdrawalOrder update = new UserWithdrawalOrder();
				update.setId(order.getId());
				update.setStatus(UserWithdrawalOrder.STATUS_CONFIRM);
				update.setRemark("????????????");
				updateUserWithdrawalOrder(update);
			}
		}
		return CommonResult.ajax(rows);
	}

	/**
	 * Confirm withdrawal.
	 *
	 * @param userId  the user id
	 * @param orderNo the order no
	 * @return the common result
	 * @throws TransactionException the transaction exception
	 */
	@Override
	@Transactional
	public CommonResult<?> confirmWithdrawal(Long userId, String orderNo) throws TransactionException {
		final UserWithdrawalOrder order = selectUserWithdrawalOrderByOrderNo(orderNo);
		if (order == null || !Objects.equals(userId, order.getUserId())) {
			return CommonResult.fail("????????????");
		}
		// ??????????????????
		if (!UserWithdrawalOrder.STATUS_NONE.equals(order.getStatus())
				|| !UserWithdrawalOrder.CALLABLE_NONE.equals(order.getCallable())) {
			return CommonResult.fail("???????????????");
		}
		// ????????????
		if (walletService == null) {
			return CommonResult.fail("??????????????????");
		}
		// ??????????????????
		final BigDecimal withdrawal = order.getWithdrawal();
		final String address = order.getAddress();
		final String symbol = order.getSymbol();
		MiningSymbol miningSymbol = symbolService.selectMiningSymbolById(symbol);
		final String chain = miningSymbol.getChain();
		final boolean auto = MiningSymbol.ENABLED.equals(miningSymbol.getWithdrawalWalletAudit());
		final CommonResult<WithdrawalResponse> dispatched = walletService.withdrawal(orderNo, symbol, chain, address,
				"", withdrawal, auto);
		final Long id = order.getId();
		if (!dispatched.isSuccess()) {
			// ??????????????????
			userWalletAccountService.unfreezeAmount(userId, symbol, order.getAmount(), orderNo, true);
			// ????????????
			final UserWithdrawalOrder update = new UserWithdrawalOrder();
			update.setId(id);
			update.setStatus(UserWithdrawalOrder.STATUS_FAILURE);
			update.setRemark("???????????????" + dispatched.getInfo() + "???");
			updateUserWithdrawalOrder(update);
			return CommonResult.fail(dispatched.getInfo());
		}
		final WithdrawalResponse data = dispatched.getData();
		if (data != null) {
			final int status = data.getStatus();
			BigDecimal walletFee = data.getFees();
			if (WithdrawalResponse.STATUS_SUCCESS.equals(status)) {
				// ????????????
				addWithdrawalRecord(userId, order.getAmount(), order.getFee(), walletFee, orderNo, symbol, true,
						"?????????????????????"); //$NON-NLS-1$
				return result(id, UserWithdrawalOrder.STATUS_SUCCESS, UserWithdrawalOrder.FEEDBACK_NONE, "????????????", true); //$NON-NLS-1$
			} else if (WithdrawalResponse.STATUS_FAILURE.equals(status)) {
				// ????????????
				addWithdrawalRecord(userId, order.getAmount(), order.getFee(), walletFee, orderNo, symbol, false,
						"?????????????????????"); //$NON-NLS-1$
				return result(id, UserWithdrawalOrder.STATUS_FAILURE, UserWithdrawalOrder.FEEDBACK_NONE, "????????????", false); //$NON-NLS-1$
			}
			// ????????????
			else if (WithdrawalResponse.STATUS_MANUAL.equals(status)) {
				// ????????????
				return result(id, UserWithdrawalOrder.STATUS_MANUAL_START, UserWithdrawalOrder.FEEDBACK_NONE, "????????????",
						true);
			}
			// ????????????
			return result(id, UserWithdrawalOrder.STATUS_CONFIRM, UserWithdrawalOrder.FEEDBACK_NONE, "???????????????????????????", true);
		}
		return result(id, UserWithdrawalOrder.STATUS_CONFIRM, UserWithdrawalOrder.FEEDBACK_NONE, "???????????????????????????", true);
	}

	/**
	 * Adds the withdrawal record.
	 *
	 * @param userId    the user id
	 * @param amount    the amount
	 * @param fee       the fee
	 * @param walletFee the wallet fee
	 * @param orderNo   the order no
	 * @param symbol    the symbol
	 * @param success   the success
	 * @param remark    the remark
	 * @return true, if successful
	 */
	private boolean addWithdrawalRecord(Long userId, BigDecimal amount, BigDecimal fee, BigDecimal walletFee,
			String orderNo, String symbol, boolean success, String remark) {
		return addWithdrawalRecord(userId, amount, fee, walletFee, orderNo, symbol, success, remark, null);
	}

	/**
	 * Adds the withdrawal record.
	 *
	 * @param userId    the user id
	 * @param amount    the amount
	 * @param fee       the fee
	 * @param walletFee the wallet fee
	 * @param orderNo   the order no
	 * @param symbol    the symbol
	 * @param success   the success
	 * @param remark    the remark
	 * @param txId      the tx id
	 * @return true, if successful
	 */
	private boolean addWithdrawalRecord(Long userId, BigDecimal amount, BigDecimal fee, BigDecimal walletFee,
			String orderNo, String symbol, boolean success, String remark, String txId) {
		// 1. ??????
		final boolean unfreezed = userWalletAccountService.unfreezeAmount(userId, symbol, amount, orderNo, !success);
		if (!unfreezed) {
			throw new TransactionException("????????????");
		}
		// 2. ????????????
		final UserWithdrawalRecord record = new UserWithdrawalRecord();
		record.setUserId(userId);
		record.setFee(fee);
		record.setWalletFee(walletFee);
		record.setOrderNo(orderNo);
		record.setType(UserWithdrawalRecord.TYPE_WALLET);
		record.setSymbol(symbol);
		record.setAmount(amount);
		record.setStatus(IConstants.STATUS_FINISHED);
		record.setRemark(remark);
		record.setTxId(txId);
		if (userWithdrawalRecordService.insertUserWithdrawalRecord(record) <= 0) {
			throw new TransactionException("????????????");
		}
		return true;
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
		update.setCallable(UserWithdrawalOrder.CALLABLE_FINISHED);
		updateUserWithdrawalOrder(update);
		if (success) {
			return CommonResult.success(update);
		}
		return CommonResult.fail(remark);
	}

	/**
	 * Cancel withdrawal.
	 *
	 * @param orderNos the order nos
	 * @param remark   the remark
	 * @return the common result
	 */
	@Override
	public CommonResult<?> cancelWithdrawal(String orderNos, String remark) {
		if (StringUtils.isEmpty(orderNos)) {
			return CommonResult.fail("????????????");
		}
		if (StringUtils.isEmpty(remark)) {
			remark = "??????????????????";
		}
		final String[] array = Convert.toStrArray(orderNos);
		int rows = 0;
		for (final String orderNo : array) {
			final UserWithdrawalOrder order = selectUserWithdrawalOrderByOrderNo(orderNo);
			if (order == null) {
				continue;
			}
			try {
				final CommonResult<?> cancel = cancelWithdrawal(order.getUserId(), orderNo, remark);
				if (cancel.isSuccess()) {
					rows++;
				}
			} catch (final TransactionException e) {
				// ??????????????????
				final UserWithdrawalOrder update = new UserWithdrawalOrder();
				update.setId(order.getId());
				update.setStatus(UserWithdrawalOrder.STATUS_CANCEL);
				update.setRemark(remark);
				updateUserWithdrawalOrder(update);
			}
		}
		return CommonResult.ajax(rows);
	}

	/**
	 * Select user withdrawal amount.
	 *
	 * @param userWithdrawalOrder the user withdrawal order
	 * @return the big decimal
	 */
	@Override
	public BigDecimal selectUserWithdrawalAmount(UserWithdrawalOrder userWithdrawalOrder) {
		return MathUtils.nullToZero(userWithdrawalOrderMapper.selectUserWithdrawalAmount(userWithdrawalOrder));
	}

	/**
	 * Select user withdrawal amount.
	 *
	 * @param symbol the symbol
	 * @return the big decimal
	 */
	@Override
	public BigDecimal selectUserWithdrawalAmount(String symbol) {
		final UserWithdrawalOrder query = new UserWithdrawalOrder();
		query.setSymbol(symbol);
		return selectUserWithdrawalAmount(query);
	}

	/**
	 * Select user withdrawal fee amount.
	 *
	 * @param symbol the symbol
	 * @return the big decimal
	 */
	@Override
	public BigDecimal selectUserWithdrawalFeeAmount(String symbol) {
		if (StringUtils.isEmpty(symbol)) {
			return BigDecimal.ZERO;
		}
		return MathUtils.nullToZero(userWithdrawalOrderMapper.selectUserWithdrawalFeeAmount(symbol));
	}

	/**
	 * Manual withdrawal record.
	 *
	 * @param manual the manual
	 * @return the common result
	 */
	@Override
	public CommonResult<?> manualWithdrawalRecord(UserWithdrawalManual manual) {
		if (manual == null || StringUtils.isEmpty(manual.getId()) || manual.getStatus() == null) {
			return CommonResult.fail("????????????");
		}
		Long id = manual.getId();
		UserWithdrawalOrder order = userWithdrawalOrderMapper.selectUserWithdrawalOrderById(id);
		if (order == null) {
			return CommonResult.fail("???????????????????????????????????????");
		}
		if (!UserWithdrawalManual.STATUS_MANUAL_START.equals(order.getStatus())) {
			return CommonResult.success("???????????????");
		}
		Integer status = manual.getStatus();
		String txId = manual.getTxId();
		if (UserWithdrawalManual.STATUS_MANUAL_SUCCESS.equals(status) && StringUtils.isEmpty(txId)) {
			return CommonResult.fail("????????????????????????");
		}
		String remark = manual.getRemark();
		UserWithdrawalOrder update = new UserWithdrawalOrder();
		update.setId(id);
		update.setStatus(status);
		update.setRemark(remark);

		int rows = userWithdrawalOrderMapper.updateUserWithdrawalOrder(update);
		if (rows <= 0) {
			return CommonResult.fail("?????????????????????????????????");
		}
		addWithdrawalRecord(order.getUserId(), order.getAmount(), order.getFee(), order.getWithdrawal(),
				order.getOrderNo(), order.getSymbol(), UserWithdrawalManual.STATUS_MANUAL_SUCCESS.equals(status),
				remark, txId);
		return CommonResult.success();
	}
}
