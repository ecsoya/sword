package com.github.ecsoya.sword.udun.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.ecsoya.sword.business.service.IWalletCallbackService;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.utils.IdWorker;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.bean.BeanUtils;
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
import com.github.ecsoya.sword.udun.config.UdunWalletProperties;
import com.github.ecsoya.sword.udun.constants.CoinType;
import com.github.ecsoya.sword.udun.domain.UTrade;
import com.github.ecsoya.sword.udun.domain.WalletNotify;
import com.github.ecsoya.sword.udun.utils.HttpUtil;
import com.github.ecsoya.sword.utils.MathUtils;
import com.github.ecsoya.sword.wallet.domain.UserWalletAccount;
import com.github.ecsoya.sword.wallet.service.IUserWalletAccountService;

/**
 * The Class UdunWalletCallbackService.
 */
@Service
public class UdunWalletCallbackService implements IWalletCallbackService<WalletNotify> {

	/** The properties. */
	@Autowired
	private UdunWalletProperties properties;

	/** The user deposit record service. */
	@Autowired
	private IUserDepositRecordService userDepositRecordService;

	/** The user deposit order service. */
	@Autowired
	private IUserDepositOrderService userDepositOrderService;

	/** The user wallet account service. */
	@Autowired
	private IUserWalletAccountService userWalletAccountService;

	/** The user withdrawal record service. */
	@Autowired
	private IUserWithdrawalRecordService userWithdrawalRecordService;

	/** The user withdrawal order service. */
	@Autowired
	private IUserWithdrawalOrderService userWithdrawalOrderService;

	/**
	 * Process order.
	 *
	 * @param data the data
	 * @return the common result
	 */
	@Override
	public CommonResult<?> processOrder(WalletNotify data) {
		if (data == null) {
			return CommonResult.fail("????????????");
		}
		Integer tradeType = data.getTradeType();
		CommonResult<?> result = null;
		if (WalletNotify.TYPE_DEPOSITE.equals(tradeType)) {
			result = processDepositeOrder(data);
		} else if (WalletNotify.TYPE_WITHDRAWAL.equals(tradeType)) {
			result = processWithdrawalOrder(data);
		} else {
			result = CommonResult.fail("????????????");
		}
		return result;
	}

	/**
	 * Process withdrawal order.
	 *
	 * @param data the data
	 * @return the common result
	 */
	private CommonResult<?> processWithdrawalOrder(WalletNotify data) {
		if (data == null || !WalletNotify.TYPE_WITHDRAWAL.equals(data.getTradeType())) {
			return CommonResult.fail("????????????");
		}
		UserWithdrawalOrder order = userWithdrawalOrderService.selectUserWithdrawalOrderByOrderNo(data.getOrderNo());
		if (order == null) {
			return CommonResult.fail("????????????");
		}
		Integer status = data.getStatus();
		Long height = MathUtils.parseLong(data.getBlockHigh());
		// ???????????????????????????????????????????????????????????????
		if (!UserWithdrawalOrder.STATUS_CONFIRM.equals(order.getStatus())) {
			// ??????height
			if (WalletNotify.STATUS_SUCCESS.equals(status)) {
				if (height != null && height.longValue() > 0) {
					userWithdrawalRecordService.updateUserWithdrawalRecordHeightByTxId(data.getTxId(), height);
				}
			}
			return CommonResult.success(data.getOrderNo());
		}
		Long userId = order.getUserId();
		String symbol = order.getSymbol();

		// ????????????
		if (WalletNotify.STATUS_SUCCESS.equals(status)) {
			// 1. ??????
			boolean unfreezed = userWalletAccountService.unfreezeAmount(userId, symbol, order.getAmount(),
					order.getOrderNo(), false);
			if (!unfreezed) {
				throw new TransactionException("????????????????????????");
			}
			// ???????????????
			BigDecimal fee = order.getFee();
			// 2. ??????????????????
			UserWithdrawalRecord record = new UserWithdrawalRecord();
			record.setHeight(height);
			record.setTxId(data.getTxId());
			record.setUserId(userId);
			record.setFee(fee);
			record.setWalletFee(data.getFee());
			record.setOrderNo(order.getOrderNo());
			record.setType(UserWithdrawalRecord.TYPE_WALLET);
			record.setSymbol(symbol);
			record.setAmount(order.getAmount());
			record.setStatus(UserWithdrawalRecord.STATUS_FINISHED);
			record.setRemark(data.getTradeId());
			int rows = userWithdrawalRecordService.insertUserWithdrawalRecord(record);
			if (rows <= 0) {
				throw new TransactionException("????????????????????????");
			}
			// 3. ?????????????????????
//			if (MathUtils.isValid(fee)) {
//				rows = beeplusWithdrawalService.addWithdrawalFee(userId, order.getOrderNo(), symbol, fee);
//				if (rows <= 0) {
//					throw new TransactionException("???????????????????????????");
//				}
//			}
			// 4. ????????????
			UserWithdrawalOrder update = new UserWithdrawalOrder();
			update.setId(order.getId());
			update.setStatus(UserWithdrawalOrder.STATUS_SUCCESS);
			update.setRemark("??????????????????");
			rows = userWithdrawalOrderService.updateUserWithdrawalOrder(update);
			if (rows <= 0) {
				throw new TransactionException("????????????????????????");
			}
		}
		// ?????????????????????
		else if (WalletNotify.STATUS_FALIURE.equals(status) || WalletNotify.STATUS_REJECTED.equals(status)) {
			// 1. ??????
			boolean unfreezed = userWalletAccountService.unfreezeAmount(userId, symbol, order.getAmount(),
					order.getOrderNo(), true);
			if (!unfreezed) {
				throw new TransactionException("????????????????????????");
			}
			// 2.????????????
			UserWithdrawalOrder update = new UserWithdrawalOrder();
			update.setId(order.getId());
			update.setStatus(UserWithdrawalOrder.STATUS_FAILURE);
			if (WalletNotify.STATUS_FALIURE.equals(status)) {
				update.setRemark("??????????????????");
			} else {
				update.setRemark("??????????????????");
			}
			int rows = userWithdrawalOrderService.updateUserWithdrawalOrder(update);
			if (rows <= 0) {
				throw new TransactionException("????????????????????????");
			}
		}
		return CommonResult.success(data.getOrderNo());
	}

	/**
	 * Process deposite order.
	 *
	 * @param data the data
	 * @return the common result
	 */
	private CommonResult<?> processDepositeOrder(WalletNotify data) {
		if (data == null || !WalletNotify.TYPE_DEPOSITE.equals(data.getTradeType())) {
			return CommonResult.fail("????????????");
		}
		Integer status = data.getStatus();
		if (!WalletNotify.STATUS_SUCCESS.equals(status)) {
			return CommonResult.success("????????????");
		}
		String txId = data.getTxId();

		UserDepositRecord record = userDepositRecordService.selectUserDepositRecordByTxId(txId);
		if (record != null) {
			return CommonResult.success("????????????");
		}

		String address = data.getAddress();
		String symbol = data.getSymbol();
		UserWalletAccount account = userWalletAccountService.selectUserWalletAccountByAddress(address, symbol);
		if (account == null) {
			return CommonResult.success("????????????");
		}
		symbol = account.getSymbol();
		BigDecimal amount = data.getAmount();
		if (MathUtils.isInvalid(amount)) {
			return CommonResult.success("????????????");
		}
		if (amount.doubleValue() < 0.1) {
			return CommonResult.success("??????????????????0.1");
		}
		Long userId = account.getUserId();
		String orderNo = IdWorker.get32UUID();
		// 1. ??????
		UserDepositOrder order = new UserDepositOrder();
		order.setOrderNo(orderNo);
		order.setUserId(userId);
		order.setAddress(address);
		order.setAmount(amount);
		order.setSymbol(symbol);
		order.setTxId(txId);
		order.setStatus(UserDepositOrder.STATUS_FINISHED);
		int rows = userDepositOrderService.insertUserDepositOrder(order);
		if (rows <= 0) {
			throw new TransactionException("????????????????????????");
		}

		// 2.??????
		boolean deposited = userWalletAccountService.increaseAmount(account, amount, orderNo, "????????????",
				IMiningConstants.DETAILS_DEPOSIT);
		if (!deposited) {
			throw new TransactionException("????????????????????????");
		}

		// 3.????????????
		record = new UserDepositRecord();
		record.setHeight(MathUtils.parseLong(data.getBlockHigh()));
		record.setTxId(data.getTxId());
		record.setUserId(userId);
		record.setOrderNo(orderNo);
		record.setType(UserDepositRecord.TYPE_WALLET);
		record.setSymbol(symbol);
		record.setAmount(amount);
		record.setStatus(UserDepositRecord.STATUS_FINISHED);
		record.setRemark("????????????");
		rows = userDepositRecordService.insertUserDepositRecord(record);
		if (rows <= 0) {
			throw new TransactionException("????????????????????????");
		}
		return CommonResult.success("????????????");
	}

	/**
	 * Parses the raw data.
	 *
	 * @param body the body
	 * @return the common result
	 */
	@Override
	public CommonResult<WalletNotify> parseRawData(String body) {
		if (StringUtils.isEmpty(body)) {
			return CommonResult.fail("EmptyJSON");
		}

		try {
			UTrade trade = JSON.parseObject(body, UTrade.class);
			WalletNotify notify = new WalletNotify();
			BeanUtils.copyBeanProp(notify, trade);
			// ??????
			BigDecimal amount = trade.getAmount();
			Integer decimals = trade.getDecimals();
			BigDecimal pow = BigDecimal.ONE;
			if (decimals != null) {
				pow = BigDecimal.valueOf(10).pow(decimals.intValue());
			}
			if (amount != null) {
				notify.setAmount(amount.divide(pow, 6, RoundingMode.HALF_UP));
			}
			BigDecimal fee = trade.getFee();
			if (fee != null) {
				notify.setFee(fee.divide(pow, 6, RoundingMode.HALF_UP));
			}
			// ??????
			String coinType = trade.getCoinType();
			CoinType coin = null;
			if (coinType != null) {
				coin = CoinType.get(coinType);
				if (coin == null) {
					coin = CoinType.get(trade.getMainCoinType());
				}
			}
			if (CoinType.TRX == coin || CoinType.TRCUSD == coin) {
				notify.setSymbol("usdt");
			} else if (coin != null) {
				notify.setSymbol(coin.name().toLowerCase());
			}
			notify.setOrderNo(trade.getBusinessId());
			return CommonResult.build(notify);
		} catch (Exception e) {
			return CommonResult.fail("NotJSON");
		}
	}

	/**
	 * Check signature.
	 *
	 * @param timestamp the timestamp
	 * @param nonce     the nonce
	 * @param body      the body
	 * @param sign      the sign
	 * @return the common result
	 */
	public CommonResult<?> checkSignature(String timestamp, String nonce, String body, String sign) {
		try {
			if (HttpUtil.checkSign(properties.getMerchantKey(), timestamp, nonce, body, sign)) {
				return CommonResult.success();
			}
		} catch (Exception e) {
		}
		return CommonResult.fail("????????????");
	}

}
