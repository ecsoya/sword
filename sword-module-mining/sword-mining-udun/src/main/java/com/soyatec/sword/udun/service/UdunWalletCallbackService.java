package com.soyatec.sword.udun.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.soyatec.sword.business.service.IWalletCallbackService;
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.utils.IdWorker;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.common.utils.bean.BeanUtils;
import com.soyatec.sword.exceptions.TransactionException;
import com.soyatec.sword.order.domain.UserDepositOrder;
import com.soyatec.sword.order.domain.UserWithdrawalOrder;
import com.soyatec.sword.order.service.IUserDepositOrderService;
import com.soyatec.sword.order.service.IUserWithdrawalOrderService;
import com.soyatec.sword.record.domain.UserDepositRecord;
import com.soyatec.sword.record.domain.UserWithdrawalRecord;
import com.soyatec.sword.record.service.IUserDepositRecordService;
import com.soyatec.sword.record.service.IUserWithdrawalRecordService;
import com.soyatec.sword.udun.config.UdunWalletProperties;
import com.soyatec.sword.udun.constants.CoinType;
import com.soyatec.sword.udun.domain.UTrade;
import com.soyatec.sword.udun.domain.WalletNotify;
import com.soyatec.sword.udun.utils.HttpUtil;
import com.soyatec.sword.utils.MathUtils;
import com.soyatec.sword.wallet.domain.UserWalletAccount;
import com.soyatec.sword.wallet.service.IUserWalletAccountService;

@Service
public class UdunWalletCallbackService implements IWalletCallbackService<WalletNotify> {

	@Autowired
	private UdunWalletProperties properties;

	@Autowired
	private IUserDepositRecordService userDepositRecordService;

	@Autowired
	private IUserDepositOrderService userDepositOrderService;

	@Autowired
	private IUserWalletAccountService userWalletAccountService;

	@Autowired
	private IUserWithdrawalRecordService userWithdrawalRecordService;

	@Autowired
	private IUserWithdrawalOrderService userWithdrawalOrderService;

	@Override
	public CommonResult<?> processOrder(WalletNotify data) {
		if (data == null) {
			return CommonResult.fail("参数错误");
		}
		Integer tradeType = data.getTradeType();
		CommonResult<?> result = null;
		if (WalletNotify.TYPE_DEPOSITE.equals(tradeType)) {
			result = processDepositeOrder(data);
		} else if (WalletNotify.TYPE_WITHDRAWAL.equals(tradeType)) {
			result = processWithdrawalOrder(data);
		} else {
			result = CommonResult.fail("未知类型");
		}
		return result;
	}

	private CommonResult<?> processWithdrawalOrder(WalletNotify data) {
		if (data == null || !WalletNotify.TYPE_WITHDRAWAL.equals(data.getTradeType())) {
			return CommonResult.fail("参数错误");
		}
		UserWithdrawalOrder order = userWithdrawalOrderService.selectUserWithdrawalOrderByOrderNo(data.getOrderNo());
		if (order == null) {
			return CommonResult.fail("订单错误");
		}
		Integer status = data.getStatus();
		Long height = MathUtils.parseLong(data.getBlockHigh());
		// 不是已确认状态，说明已经处理过了，不再处理
		if (!UserWithdrawalOrder.STATUS_CONFIRM.equals(order.getStatus())) {
			// 更新height
			if (WalletNotify.STATUS_SUCCESS.equals(status)) {
				if (height != null && height.longValue() > 0) {
					userWithdrawalRecordService.updateUserWithdrawalRecordHeightByTxId(data.getTxId(), height);
				}
			}
			return CommonResult.success(data.getOrderNo());
		}
		Long userId = order.getUserId();
		String symbol = order.getSymbol();

		// 交易成功
		if (WalletNotify.STATUS_SUCCESS.equals(status)) {
			// 1. 扣款
			boolean unfreezed = userWalletAccountService.unfreezeAmount(userId, symbol, order.getAmount(),
					order.getOrderNo(), false);
			if (!unfreezed) {
				throw new TransactionException("内部错误【解冻】");
			}
			// 内部手续费
			BigDecimal fee = order.getFee();
			// 2. 生成提现记录
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
				throw new TransactionException("内部错误【记录】");
			}
			// 3. 处理内部手续费
//			if (MathUtils.isValid(fee)) {
//				rows = beeplusWithdrawalService.addWithdrawalFee(userId, order.getOrderNo(), symbol, fee);
//				if (rows <= 0) {
//					throw new TransactionException("内部错误【手续费】");
//				}
//			}
			// 4. 更新订单
			UserWithdrawalOrder update = new UserWithdrawalOrder();
			update.setId(order.getId());
			update.setStatus(UserWithdrawalOrder.STATUS_SUCCESS);
			update.setRemark("钱包交易成功");
			rows = userWithdrawalOrderService.updateUserWithdrawalOrder(update);
			if (rows <= 0) {
				throw new TransactionException("内部错误【订单】");
			}
		}
		// 交易失败或拒绝
		else if (WalletNotify.STATUS_FALIURE.equals(status) || WalletNotify.STATUS_REJECTED.equals(status)) {
			// 1. 还钱
			boolean unfreezed = userWalletAccountService.unfreezeAmount(userId, symbol, order.getAmount(),
					order.getOrderNo(), true);
			if (!unfreezed) {
				throw new TransactionException("内部错误【解冻】");
			}
			// 2.更新订单
			UserWithdrawalOrder update = new UserWithdrawalOrder();
			update.setId(order.getId());
			update.setStatus(UserWithdrawalOrder.STATUS_FAILURE);
			if (WalletNotify.STATUS_FALIURE.equals(status)) {
				update.setRemark("钱包交易失败");
			} else {
				update.setRemark("钱包审核失败");
			}
			int rows = userWithdrawalOrderService.updateUserWithdrawalOrder(update);
			if (rows <= 0) {
				throw new TransactionException("内部错误【订单】");
			}
		}
		return CommonResult.success(data.getOrderNo());
	}

	private CommonResult<?> processDepositeOrder(WalletNotify data) {
		if (data == null || !WalletNotify.TYPE_DEPOSITE.equals(data.getTradeType())) {
			return CommonResult.fail("参数错误");
		}
		Integer status = data.getStatus();
		if (!WalletNotify.STATUS_SUCCESS.equals(status)) {
			return CommonResult.success("状态错误");
		}
		String txId = data.getTxId();

		UserDepositRecord record = userDepositRecordService.selectUserDepositRecordByTxId(txId);
		if (record != null) {
			return CommonResult.success("处理成功");
		}

		String address = data.getAddress();
		String symbol = data.getSymbol();
		UserWalletAccount account = userWalletAccountService.selectUserWalletAccountByAddress(address, symbol);
		if (account == null) {
			return CommonResult.success("地址错误");
		}
		symbol = account.getSymbol();
		BigDecimal amount = data.getAmount();
		if (MathUtils.isInvalid(amount)) {
			return CommonResult.success("金额错误");
		}
		if (amount.doubleValue() < 0.1) {
			return CommonResult.success("充值金额小于0.1");
		}
		Long userId = account.getUserId();
		String orderNo = IdWorker.get32UUID();
		// 1. 订单
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
			throw new TransactionException("内部错误【订单】");
		}

		// 2.钱包
		boolean deposited = userWalletAccountService.increaseAmount(account, amount, orderNo, null);
		if (!deposited) {
			throw new TransactionException("内部错误【钱包】");
		}

		// 3.生成记录
		record = new UserDepositRecord();
		record.setHeight(MathUtils.parseLong(data.getBlockHigh()));
		record.setTxId(data.getTxId());
		record.setUserId(userId);
		record.setOrderNo(orderNo);
		record.setType(UserDepositRecord.TYPE_WALLET);
		record.setSymbol(symbol);
		record.setAmount(amount);
		record.setStatus(UserDepositRecord.STATUS_FINISHED);
		record.setRemark("充值成功");
		rows = userDepositRecordService.insertUserDepositRecord(record);
		if (rows <= 0) {
			throw new TransactionException("内部错误【记录】");
		}
		return CommonResult.success("充值成功");
	}

	@Override
	public CommonResult<WalletNotify> parseRawData(String body) {
		if (StringUtils.isEmpty(body)) {
			return CommonResult.fail("EmptyJSON");
		}

		try {
			UTrade trade = JSON.parseObject(body, UTrade.class);
			WalletNotify notify = new WalletNotify();
			BeanUtils.copyBeanProp(notify, trade);
			// 精度
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
			// 币种
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

	public CommonResult<?> checkSignature(String timestamp, String nonce, String body, String sign) {
		try {
			if (HttpUtil.checkSign(properties.getMerchantKey(), timestamp, nonce, body, sign)) {
				return CommonResult.success();
			}
		} catch (Exception e) {
		}
		return CommonResult.fail("签名错误");
	}

}
