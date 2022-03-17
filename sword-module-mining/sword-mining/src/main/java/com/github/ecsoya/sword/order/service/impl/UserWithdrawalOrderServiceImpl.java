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
 * 提现订单Service业务层处理
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-06
 */
@Service
public class UserWithdrawalOrderServiceImpl implements IUserWithdrawalOrderService {
	@Autowired
	private UserWithdrawalOrderMapper userWithdrawalOrderMapper;

	@Autowired
	private IUserWalletService userWalletService;

	@Autowired
	private IUserWalletAccountService userWalletAccountService;

	@Autowired
	private IMiningSymbolService symbolService;

	@Autowired
	private IWalletService walletService;

	@Autowired
	private IUserWithdrawalRecordService userWithdrawalRecordService;

	@Autowired
	private IUserCertificateService certificateService;

	/**
	 * 查询提现订单
	 *
	 * @param id 提现订单ID
	 * @return 提现订单
	 */
	@Override
	public UserWithdrawalOrder selectUserWithdrawalOrderById(Long id) {
		return userWithdrawalOrderMapper.selectUserWithdrawalOrderById(id);
	}

	@Override
	public UserWithdrawalOrder selectUserWithdrawalOrderByOrderNo(String orderNo) {
		if (StringUtils.isEmpty(orderNo)) {
			return null;
		}
		return userWithdrawalOrderMapper.selectUserWithdrawalOrderByOrderNo(orderNo);
	}

	/**
	 * 查询提现订单列表
	 *
	 * @param userWithdrawalOrder 提现订单
	 * @return 提现订单
	 */
	@Override
	public List<UserWithdrawalOrder> selectUserWithdrawalOrderList(UserWithdrawalOrder userWithdrawalOrder) {
		return userWithdrawalOrderMapper.selectUserWithdrawalOrderList(userWithdrawalOrder);
	}

	/**
	 * 新增提现订单
	 *
	 * @param userWithdrawalOrder 提现订单
	 * @return 结果
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
	 * 修改提现订单
	 *
	 * @param userWithdrawalOrder 提现订单
	 * @return 结果
	 */
	@Override
	public int updateUserWithdrawalOrder(UserWithdrawalOrder userWithdrawalOrder) {
		userWithdrawalOrder.setUpdateTime(DateUtils.getNowDate());
		return userWithdrawalOrderMapper.updateUserWithdrawalOrder(userWithdrawalOrder);
	}

	/**
	 * 删除提现订单对象
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteUserWithdrawalOrderByIds(String ids) {
		return userWithdrawalOrderMapper.deleteUserWithdrawalOrderByIds(Convert.toStrArray(ids));
	}

	/**
	 * 删除提现订单信息
	 *
	 * @param id 提现订单ID
	 * @return 结果
	 */
	@Override
	public int deleteUserWithdrawalOrderById(Long id) {
		return userWithdrawalOrderMapper.deleteUserWithdrawalOrderById(id);
	}

	@Override
	@Transactional(rollbackFor = TransactionException.class)
	public CommonResult<UserWithdrawalOrder> withdrawal(Long userId, String symbol, String address, BigDecimal amount,
			String password) throws TransactionException {
		if (userId == null || StringUtils.isEmpty(symbol) || StringUtils.isEmpty(address) || amount == null
				|| StringUtils.isEmpty(password)) {
			return CommonResult.fail("参数错误");
		}
		final CommonResult<?> checked = certificateService.checkUserCertificate(userId,
				UserCertificate.KIND_WITHDRAWAL);
		if (!checked.isSuccess()) {
			return CommonResult.fail(checked.getInfo());
		}
		final MiningSymbol swordSymbol = symbolService.selectMiningSymbolById(symbol);
		if (swordSymbol == null) {
			return CommonResult.fail("币种错误");
		}
		// 1. 全局提币通道
		if (!swordSymbol.checkWithdrawalEnabled()) {
			return CommonResult.fail("提币通道尚未开放");
		}
		// 2. 单笔最小提币量
		final BigDecimal withdrawalMinimum = swordSymbol.getWithdrawalMinimum();
		if (MathUtils.isPositive(withdrawalMinimum) && MathUtils.lt(amount, withdrawalMinimum)) {
			return CommonResult.fail("未达到最小提币额");
		}
		// 3. 单笔最大提币量
		final BigDecimal withdrawalMaximum = swordSymbol.getWithdrawalMaximum();
		if (MathUtils.isPositive(withdrawalMaximum) && MathUtils.gt(amount, withdrawalMaximum)) {
			return CommonResult.fail("超过了最大单笔提币额");
		}
		// 4. 单日提币量
		final BigDecimal withdrawalDaily = swordSymbol.getWithdrawalDaily();
		if (MathUtils.isPositive(withdrawalDaily)) {
			final BigDecimal todayAmount = selectUserWithdrawalAmountByDate(swordSymbol.getSymbol(),
					DateUtils.getTodayStart(), DateUtils.getNowDate());
			if (MathUtils.gt(MathUtils.plus(todayAmount, amount), withdrawalDaily)) {
				return CommonResult.fail("今日提币已达到最大限额");
			}
		}
		// 5. 累积提币量
		final BigDecimal withdrawalTotally = swordSymbol.getWithdrawalTotally();
		if (MathUtils.isPositive(withdrawalTotally)) {
			final BigDecimal totallyAmount = selectUserWithdrawalAmountByDate(swordSymbol.getSymbol(), null, null);
			if (MathUtils.gt(MathUtils.plus(totallyAmount, amount), withdrawalTotally)) {
				return CommonResult.fail("累积提币已达到最大限额");
			}
		}
		// 6. 钱包密码确认
		final CommonResult<?> verified = userWalletService.verifyUserWalletPassword(userId, password);
		if (!verified.isSuccess()) {
			return CommonResult.fail("密码错误");
		}
		// 7. 账户检测，自身提币是否冻结
		final UserWalletAccount walletAccount = userWalletAccountService.selectUserWalletAccount(userId, symbol);
		if (walletAccount == null || !walletAccount.chechWithdrawalEnabled()) {
			return CommonResult.fail("钱包异常");
		}
		// 8. 余额检测
		if (MathUtils.isEmpty(walletAccount.getAmount()) || MathUtils.lt(walletAccount.getAmount(), amount)) {
			return CommonResult.fail("余额不足");
		}
		final String orderNo = IdWorker.get32UUID();

//		手续费
		BigDecimal withdrawal = amount;
		BigDecimal fee = swordSymbol.getWithdrawalFee();
		if (MathUtils.isValid(fee)) {
			final String withdrawalFeeSymbol = swordSymbol.getWithdrawalFeeSymbol();
			// 手续费兑换
			if (IMiningConstants.SYMBOL_USDT.equals(withdrawalFeeSymbol)) {
				fee = walletService.exchangeFromUsdt(symbol, fee);
			}
			if (MathUtils.lte(amount, fee)) {
				return CommonResult.fail("手续费不足");
			}
			withdrawal = withdrawal.subtract(fee);
		}
		// 生成订单
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
		order.setRemark("提币申请");
		final int rows = insertUserWithdrawalOrder(order);
		if (rows <= 0) {
			throw new TransactionException("内部错误");
		}
		// 冻结资产
		if (!userWalletAccountService.freezeAmount(walletAccount, amount, orderNo, null, "【提币】",
				IMiningConstants.DETAILS_WITHDRAWAL)) {
			throw new TransactionException("交易繁忙");
		}
		return CommonResult.build(order);
	}

	private BigDecimal selectUserWithdrawalAmountByDate(String symbol, Date start, Date end) {
		return userWithdrawalOrderMapper.selectUserWithdrawalAmountByDate(symbol, start, end);
	}

	// 后台审核取消
	@Override
	@Transactional(rollbackFor = TransactionException.class)
	public CommonResult<?> cancelWithdrawal(Long userId, String orderNo, String remark) throws TransactionException {
		final UserWithdrawalOrder order = selectUserWithdrawalOrderByOrderNo(orderNo);
		if (order == null || !Objects.equals(userId, order.getUserId())) {
			return CommonResult.fail("无效订单");
		}
		if (!UserWithdrawalOrder.STATUS_NONE.equals(order.getStatus())) {
			return CommonResult.fail("订单已处理");
		}
		final String symbol = order.getSymbol();
		final BigDecimal amount = order.getAmount();
		// 资产解冻
		if (!userWalletAccountService.unfreezeAmount(userId, symbol, amount, orderNo, true)) {
			return CommonResult.fail("解冻资金失败");
		}

		final UserWithdrawalOrder update = new UserWithdrawalOrder();
		update.setId(order.getId());
		update.setStatus(UserWithdrawalOrder.STATUS_CANCEL);
		if (StringUtils.isEmpty(remark)) {
			update.setRemark("取消成功");
		} else {
			update.setRemark(remark);
		}
		final int rows = updateUserWithdrawalOrder(update);
		if (rows <= 0) {
			throw new TransactionException("内部错误");
		}
		return CommonResult.build(orderNo);
	}

	@Override
	@Transactional(rollbackFor = TransactionException.class)
	public CommonResult<?> confirmWithdrawal(String orderNos) throws TransactionException {
		if (StringUtils.isEmpty(orderNos)) {
			return CommonResult.fail("参数错误");
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
				// 再次设置同意
				final UserWithdrawalOrder update = new UserWithdrawalOrder();
				update.setId(order.getId());
				update.setStatus(UserWithdrawalOrder.STATUS_CONFIRM);
				update.setRemark("同意提币");
				updateUserWithdrawalOrder(update);
			}
		}
		return CommonResult.ajax(rows);
	}

	@Override
	@Transactional
	public CommonResult<?> confirmWithdrawal(Long userId, String orderNo) throws TransactionException {
		final UserWithdrawalOrder order = selectUserWithdrawalOrderByOrderNo(orderNo);
		if (order == null || !Objects.equals(userId, order.getUserId())) {
			return CommonResult.fail("订单错误");
		}
		// 防止重复提交
		if (!UserWithdrawalOrder.STATUS_NONE.equals(order.getStatus())
				|| !UserWithdrawalOrder.CALLABLE_NONE.equals(order.getCallable())) {
			return CommonResult.fail("订单已处理");
		}
		// 发起交易
		if (walletService == null) {
			return CommonResult.fail("暂不支持提币");
		}
		// 实际提币额度
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
			// 调用接口失败
			userWalletAccountService.unfreezeAmount(userId, symbol, order.getAmount(), orderNo, true);
			// 更新订单
			final UserWithdrawalOrder update = new UserWithdrawalOrder();
			update.setId(id);
			update.setStatus(UserWithdrawalOrder.STATUS_FAILURE);
			update.setRemark("提币失败【" + dispatched.getInfo() + "】");
			updateUserWithdrawalOrder(update);
			return CommonResult.fail(dispatched.getInfo());
		}
		final WithdrawalResponse data = dispatched.getData();
		if (data != null) {
			final int status = data.getStatus();
			BigDecimal walletFee = data.getFees();
			if (WithdrawalResponse.STATUS_SUCCESS.equals(status)) {
				// 内部转账
				addWithdrawalRecord(userId, order.getAmount(), order.getFee(), walletFee, orderNo, symbol, true,
						"交易所内部转账"); //$NON-NLS-1$
				return result(id, UserWithdrawalOrder.STATUS_SUCCESS, UserWithdrawalOrder.FEEDBACK_NONE, "提现成功", true); //$NON-NLS-1$
			} else if (WithdrawalResponse.STATUS_FAILURE.equals(status)) {
				// 转账失败
				addWithdrawalRecord(userId, order.getAmount(), order.getFee(), walletFee, orderNo, symbol, false,
						"交易所转账失败"); //$NON-NLS-1$
				return result(id, UserWithdrawalOrder.STATUS_FAILURE, UserWithdrawalOrder.FEEDBACK_NONE, "提现失败", false); //$NON-NLS-1$
			}
			// 线下转账
			else if (WithdrawalResponse.STATUS_MANUAL.equals(status)) {
				// 等待回调
				return result(id, UserWithdrawalOrder.STATUS_MANUAL_START, UserWithdrawalOrder.FEEDBACK_NONE, "提币申请",
						true);
			}
			// 等待回调
			return result(id, UserWithdrawalOrder.STATUS_CONFIRM, UserWithdrawalOrder.FEEDBACK_NONE, "转账成功，等待确认", true);
		}
		return result(id, UserWithdrawalOrder.STATUS_CONFIRM, UserWithdrawalOrder.FEEDBACK_NONE, "转账成功，等待确认", true);
	}

	private boolean addWithdrawalRecord(Long userId, BigDecimal amount, BigDecimal fee, BigDecimal walletFee,
			String orderNo, String symbol, boolean success, String remark) {
		return addWithdrawalRecord(userId, amount, fee, walletFee, orderNo, symbol, success, remark, null);
	}

	private boolean addWithdrawalRecord(Long userId, BigDecimal amount, BigDecimal fee, BigDecimal walletFee,
			String orderNo, String symbol, boolean success, String remark, String txId) {
		// 1. 扣款
		final boolean unfreezed = userWalletAccountService.unfreezeAmount(userId, symbol, amount, orderNo, !success);
		if (!unfreezed) {
			throw new TransactionException("钱包错误");
		}
		// 2. 生成记录
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
			throw new TransactionException("内部错误");
		}
		return true;
	}

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

	@Override
	public CommonResult<?> cancelWithdrawal(String orderNos, String remark) {
		if (StringUtils.isEmpty(orderNos)) {
			return CommonResult.fail("参数错误");
		}
		if (StringUtils.isEmpty(remark)) {
			remark = "后台审核拒绝";
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
				// 再次设置取消
				final UserWithdrawalOrder update = new UserWithdrawalOrder();
				update.setId(order.getId());
				update.setStatus(UserWithdrawalOrder.STATUS_CANCEL);
				update.setRemark(remark);
				updateUserWithdrawalOrder(update);
			}
		}
		return CommonResult.ajax(rows);
	}

	@Override
	public BigDecimal selectUserWithdrawalAmount(UserWithdrawalOrder userWithdrawalOrder) {
		return MathUtils.nullToZero(userWithdrawalOrderMapper.selectUserWithdrawalAmount(userWithdrawalOrder));
	}

	@Override
	public BigDecimal selectUserWithdrawalAmount(String symbol) {
		final UserWithdrawalOrder query = new UserWithdrawalOrder();
		query.setSymbol(symbol);
		return selectUserWithdrawalAmount(query);
	}

	@Override
	public BigDecimal selectUserWithdrawalFeeAmount(String symbol) {
		if (StringUtils.isEmpty(symbol)) {
			return BigDecimal.ZERO;
		}
		return MathUtils.nullToZero(userWithdrawalOrderMapper.selectUserWithdrawalFeeAmount(symbol));
	}

	@Override
	public CommonResult<?> manualWithdrawalRecord(UserWithdrawalManual manual) {
		if (manual == null || StringUtils.isEmpty(manual.getId()) || manual.getStatus() == null) {
			return CommonResult.fail("参数错误");
		}
		Long id = manual.getId();
		UserWithdrawalOrder order = userWithdrawalOrderMapper.selectUserWithdrawalOrderById(id);
		if (order == null) {
			return CommonResult.fail("获取订单失败，请刷新后再试");
		}
		if (!UserWithdrawalManual.STATUS_MANUAL_START.equals(order.getStatus())) {
			return CommonResult.success("订单已处理");
		}
		Integer status = manual.getStatus();
		String txId = manual.getTxId();
		if (UserWithdrawalManual.STATUS_MANUAL_SUCCESS.equals(status) && StringUtils.isEmpty(txId)) {
			return CommonResult.fail("交易单号不能为空");
		}
		String remark = manual.getRemark();
		UserWithdrawalOrder update = new UserWithdrawalOrder();
		update.setId(id);
		update.setStatus(status);
		update.setRemark(remark);

		int rows = userWithdrawalOrderMapper.updateUserWithdrawalOrder(update);
		if (rows <= 0) {
			return CommonResult.fail("内部错误，请刷新后再试");
		}
		addWithdrawalRecord(order.getUserId(), order.getAmount(), order.getFee(), order.getWithdrawal(),
				order.getOrderNo(), order.getSymbol(), UserWithdrawalManual.STATUS_MANUAL_SUCCESS.equals(status),
				remark, txId);
		return CommonResult.success();
	}
}
