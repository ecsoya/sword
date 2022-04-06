package com.github.ecsoya.sword.wallet.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
import com.github.ecsoya.sword.qrcode.QrcodeUtils;
import com.github.ecsoya.sword.user.domain.UserCertificate;
import com.github.ecsoya.sword.user.service.IUserCertificateService;
import com.github.ecsoya.sword.utils.MathUtils;
import com.github.ecsoya.sword.wallet.domain.UserWalletAccount;
import com.github.ecsoya.sword.wallet.domain.UserWalletRecord;
import com.github.ecsoya.sword.wallet.mapper.UserWalletAccountMapper;
import com.github.ecsoya.sword.wallet.service.IUserWalletAccountService;
import com.github.ecsoya.sword.wallet.service.IUserWalletRecordService;
import com.github.ecsoya.sword.wallet.service.IWalletService;

/**
 * The Class UserWalletAccountServiceImpl.
 */
@Service
public class UserWalletAccountServiceImpl implements IUserWalletAccountService {

	/** The user wallet account mapper. */
	@Autowired
	private UserWalletAccountMapper userWalletAccountMapper;

	/** The user wallet record service. */
	@Autowired
	private IUserWalletRecordService userWalletRecordService;

	/** The wallet service. */
	@Autowired
	private IWalletService walletService;

	/** The symbol service. */
	@Autowired
	private IMiningSymbolService symbolService;

	/** The certificate service. */
	@Autowired
	private IUserCertificateService certificateService;

	/**
	 * Select user wallet account by id.
	 *
	 * @param id the id
	 * @return the user wallet account
	 */
	@Override
	public UserWalletAccount selectUserWalletAccountById(Long id) {
		return userWalletAccountMapper.selectUserWalletAccountById(id);
	}

	/**
	 * Select user wallet account list.
	 *
	 * @param userWalletAccount the user wallet account
	 * @return the list
	 */
	@Override
	public List<UserWalletAccount> selectUserWalletAccountList(UserWalletAccount userWalletAccount) {
		final List<UserWalletAccount> list = userWalletAccountMapper.selectUserWalletAccountList(userWalletAccount);
		final List<MiningSymbol> symbols = symbolService.selectMiningSymbolList(new MiningSymbol());
		list.forEach(account -> {
			account.setWithdrawal(symbols.stream().filter(s -> Objects.equals(s.getSymbol(), account.getSymbol()))
					.findFirst().orElse(null));
		});
		return list;
	}

	/**
	 * Insert user wallet account.
	 *
	 * @param userWalletAccount the user wallet account
	 * @return the int
	 */
	@Override
	public int insertUserWalletAccount(UserWalletAccount userWalletAccount) {
		if (userWalletAccount.getId() == null) {
			userWalletAccount.setId(IdWorker.getId());
		}
		if (userWalletAccount.getCreateTime() == null) {
			userWalletAccount.setCreateTime(DateUtils.getNowDate());
		}
		userWalletAccount.setCreateTime(DateUtils.getNowDate());
		return userWalletAccountMapper.insertUserWalletAccount(userWalletAccount);
	}

	/**
	 * Update user wallet account.
	 *
	 * @param userWalletAccount the user wallet account
	 * @return the int
	 */
	@Override
	public int updateUserWalletAccount(UserWalletAccount userWalletAccount) {
		userWalletAccount.setUpdateTime(DateUtils.getNowDate());
		return userWalletAccountMapper.updateUserWalletAccount(userWalletAccount);
	}

	/**
	 * Delete user wallet account by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteUserWalletAccountByIds(String ids) {
		return userWalletAccountMapper.deleteUserWalletAccountByIds(Convert.toStrArray(ids));
	}

	/**
	 * Delete user wallet account by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Override
	public int deleteUserWalletAccountById(Long id) {
		return userWalletAccountMapper.deleteUserWalletAccountById(id);
	}

	/**
	 * Select user wallet account.
	 *
	 * @param userId the user id
	 * @param symbol the symbol
	 * @return the user wallet account
	 */
	@Override
	public UserWalletAccount selectUserWalletAccount(Long userId, String symbol) {
		if (userId == null || StringUtils.isEmpty(symbol)) {
			return null;
		}
		UserWalletAccount account = userWalletAccountMapper.selectUserWalletAccount(userId, symbol);
		if (account == null || StringUtils.isEmpty(account.getAddress())
				|| StringUtils.isEmpty(account.getAddressUrl())) {
			account = updateUserWalletAccountAddress(userId, symbol, symbolService.selectMiningSymbolChain(symbol));
		}
		return account;
	}

	/**
	 * Update user wallet account address.
	 *
	 * @param userId the user id
	 * @param symbol the symbol
	 * @param chain  the chain
	 * @return the user wallet account
	 */
	@Override
	public UserWalletAccount updateUserWalletAccountAddress(Long userId, String symbol, String chain) {
		final CommonResult<?> checked = certificateService.checkUserCertificate(userId, UserCertificate.KIND_WALLET);
		if (!checked.isSuccess()) {
			return null;
		}
		if (chain == null) {
			chain = IMiningConstants.CHAIN_DEFAULT;
		}

		MiningSymbol miningSymbol = symbolService.selectMiningSymbolById(symbol);
		if (miningSymbol == null) {
			return null;
		}

		UserWalletAccount account = userWalletAccountMapper.selectUserWalletAccount(userId, symbol);
		if (account == null) {
			account = new UserWalletAccount();
			account.setUserId(userId);
			account.setSymbol(symbol);
			if (MiningSymbol.TYPE_BITCOIN.equals(miningSymbol.getType())) {
				account.setAddress(walletService.getDepositAddressValue(symbol, chain));
			} else {
				account.setAddress(IdWorker.get32UUID());
			}
			account.setAddressUrl(generateQrcodeUrl(account.getAddress()));
			insertUserWalletAccount(account);
		} else if (StringUtils.isEmpty(account.getAddress())) {
			String address = null;
			if (MiningSymbol.TYPE_BITCOIN.equals(miningSymbol.getType())) {
				address = walletService.getDepositAddressValue(symbol, chain);
			} else {
				address = IdWorker.get32UUID();
			}
			if (StringUtils.isNotEmpty(address)) {
				account.setAddress(address);
				account.setAddressUrl(generateQrcodeUrl(account.getAddress()));
				updateUserWalletAccount(account);
			}
		} else if (StringUtils.isEmpty(account.getAddressUrl())) {
			account.setAddressUrl(generateQrcodeUrl(account.getAddress()));
			updateUserWalletAccount(account);
		}
		return account;
	}

	/**
	 * Update user wallet account by user id.
	 *
	 * @param userId the user id
	 * @return the int
	 */
	@Override
	public int updateUserWalletAccountByUserId(Long userId) {
		List<MiningSymbol> list = symbolService.selectMiningSymbolList(new MiningSymbol());
		for (MiningSymbol symbol : list) {
			updateUserWalletAccountAddress(userId, symbol.getSymbol(), symbol.getChain());
		}
		return 1;
	}

	/**
	 * Generate qrcode url.
	 *
	 * @param address the address
	 * @return the string
	 */
	private String generateQrcodeUrl(String address) {
		if (StringUtils.isEmpty(address)) {
			return null;
		}
		return QrcodeUtils.generate(address);
	}

	/**
	 * Select user wallet account list by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	@Override
	public List<UserWalletAccount> selectUserWalletAccountListByUserId(Long userId) {
		if (userId == null) {
			return Collections.emptyList();
		}
		final UserWalletAccount query = new UserWalletAccount();
		query.setUserId(userId);
		return selectUserWalletAccountList(query);
	}

	/**
	 * Freeze amount.
	 *
	 * @param walletAccount the wallet account
	 * @param amount        the amount
	 * @param orderNo       the order no
	 * @param freezeDays    the freeze days
	 * @param remark        the remark
	 * @param details       the details
	 * @return true, if successful
	 */
	@Override
	@Transactional
	public boolean freezeAmount(UserWalletAccount walletAccount, BigDecimal amount, String orderNo, Integer freezeDays,
			String remark, Integer details) {
		if (walletAccount == null || MathUtils.isEmpty(amount) || StringUtils.isEmpty(orderNo)) {
			return false;
		}
		final BigDecimal walletAmount = walletAccount.getAmount();
		if (MathUtils.isEmpty(walletAmount) || MathUtils.lt(walletAmount, amount)) {
			return false;
		}
		final Long id = walletAccount.getId();
		final Long userId = walletAccount.getUserId();
		final String symbol = walletAccount.getSymbol();
		final UserWalletRecord record = new UserWalletRecord();
		record.setAmount(amount);
		record.setSymbol(symbol);
		record.setType(UserWalletRecord.TYPE_OUT);
		record.setKind(UserWalletRecord.KIND_FROZEN);
		record.setUserId(userId);
		record.setStatus(IConstants.STATUS_NONE);
		record.setRemark(remark);
		record.setDetails(details);
		if (freezeDays != null) {
			record.setDays(freezeDays);
		} else {
			record.setDays(-1);
		}
		record.setOrderNo(orderNo);
		final int rows = userWalletRecordService.insertUserWalletRecord(record);
		if (rows <= 0) {
			return false;
		}
		final UserWalletAccount lockAccount = userWalletAccountMapper.lockUserWalletAccountById(id);
		if (lockAccount == null) {
			return false;
		}
		final UserWalletAccount update = new UserWalletAccount();
		update.setId(id);
		update.setAmount(lockAccount.getAmount().subtract(amount));
		update.setFrozenAmount(MathUtils.plus(amount, lockAccount.getFrozenAmount()));
		userWalletAccountMapper.updateUserWalletAccount(update);
		return true;
	}

	/**
	 * Unfreeze amount.
	 *
	 * @param userId  the user id
	 * @param symbol  the symbol
	 * @param amount  the amount
	 * @param orderNo the order no
	 * @param payBack the pay back
	 * @return true, if successful
	 */
	@Override
	@Transactional
	public boolean unfreezeAmount(Long userId, String symbol, BigDecimal amount, String orderNo, boolean payBack) {
		if (userId == null || StringUtils.isEmpty(symbol) || MathUtils.isInvalid(amount)
				|| StringUtils.isEmpty(orderNo)) {
			return false;
		}
		final UserWalletAccount account = selectUserWalletAccount(userId, symbol);
		if (account == null) {
			return false;
		}
		final UserWalletRecord query = new UserWalletRecord();
		query.setUserId(userId);
		query.setOrderNo(orderNo);
		// query.setAmount(amount);
		query.setType(UserWalletRecord.TYPE_OUT);
		query.setKind(UserWalletRecord.KIND_FROZEN);
		query.setStatus(IConstants.STATUS_NONE);
		final List<UserWalletRecord> list = userWalletRecordService.selectUserWalletRecordList(query).stream()
				.filter(r -> MathUtils.equals(amount, r.getAmount())).collect(Collectors.toList());
		if (list.isEmpty()) {
			return false;
		}

		final UserWalletAccount lockAccount = userWalletAccountMapper.lockUserWalletAccountById(account.getId());
		if (lockAccount == null || MathUtils.lt(lockAccount.getFrozenAmount(), amount)) {
			return false;
		}

		final UserWalletAccount update = new UserWalletAccount();
		update.setId(account.getId());
		// 返还余额
		if (payBack) {
			update.setAmount(MathUtils.plus(lockAccount.getAmount(), amount));
		}
		update.setFrozenAmount(lockAccount.getFrozenAmount().subtract(amount));
		final int rows = userWalletAccountMapper.updateUserWalletAccount(update);
		if (rows > 0) {
			list.forEach(r -> {
				final UserWalletRecord record = new UserWalletRecord();
				record.setId(r.getId());
				record.setStatus(IConstants.STATUS_FINISHED);
				record.setRemark("已解冻【" + payBack + "】");
				record.setKind(r.getKind());
				userWalletRecordService.updateUserWalletRecord(record);
			});
		}
		return true;
	}

	/**
	 * Unfreeze to amount.
	 *
	 * @param record the record
	 * @return true, if successful
	 */
	@Override
	@Transactional
	public boolean unfreezeToAmount(UserWalletRecord record) {
		if (record == null || !IConstants.STATUS_NONE.equals(record.getStatus())) {
			return false;
		}
		final Long userId = record.getUserId();
		final String symbol = record.getSymbol();
		final UserWalletAccount account = selectUserWalletAccount(userId, symbol);
		if (account == null) {
			return false;
		}
		final BigDecimal amount = record.getAmount();

		final UserWalletAccount lockAccount = userWalletAccountMapper.lockUserWalletAccountById(account.getId());
		if (lockAccount == null || MathUtils.lt(lockAccount.getFrozenAmount(), amount)) {
			return false;
		}

		final UserWalletAccount update = new UserWalletAccount();
		update.setId(account.getId());
		update.setAmount(MathUtils.plus(lockAccount.getAmount(), amount));
		update.setFrozenAmount(lockAccount.getFrozenAmount().subtract(amount));
		final int rows = userWalletAccountMapper.updateUserWalletAccount(update);
		if (rows > 0) {
			// 增加一条冻结余额到可用余额的记录
			UserWalletRecord amountRecord = new UserWalletRecord();
			amountRecord.setUserId(userId);
			amountRecord.setSymbol(symbol);
			amountRecord.setAmount(lockAccount.getFrozenAmount());
			amountRecord.setType(UserWalletRecord.TYPE_IN);
			amountRecord.setKind(UserWalletRecord.KIND_AMOUNT);
			amountRecord.setStatus(UserWalletRecord.STATUS_FINISHED);
			amountRecord.setRemark("Release Frozen Amount");
			amountRecord.setDetails(record.getDetails());
			userWalletRecordService.insertUserWalletRecord(amountRecord);

			final UserWalletRecord finish = new UserWalletRecord();
			finish.setId(record.getId());
			finish.setStatus(IConstants.STATUS_FINISHED);
			finish.setRemark("Release Frozen Amount");
			finish.setKind(record.getKind());
			userWalletRecordService.updateUserWalletRecord(finish);
		}
		return true;
	}

	/**
	 * Decrease amount.
	 *
	 * @param walletAccount the wallet account
	 * @param amount        the amount
	 * @param orderNo       the order no
	 * @param remark        the remark
	 * @param details       the details
	 * @return true, if successful
	 */
	@Override
	@Transactional
	public boolean decreaseAmount(UserWalletAccount walletAccount, BigDecimal amount, String orderNo, String remark,
			Integer details) {
		if (walletAccount == null || MathUtils.isEmpty(amount) || StringUtils.isEmpty(orderNo)) {
			return false;
		}
		final BigDecimal walletAmount = walletAccount.getAmount();
		if (MathUtils.isEmpty(walletAmount) || MathUtils.lt(walletAmount, amount)) {
			return false;
		}

		final Long id = walletAccount.getId();
		final Long userId = walletAccount.getUserId();
		final String symbol = walletAccount.getSymbol();
		final UserWalletRecord record = new UserWalletRecord();
		record.setAmount(amount);
		record.setSymbol(symbol);
		record.setType(UserWalletRecord.TYPE_OUT);
		record.setKind(UserWalletRecord.KIND_AMOUNT);
		record.setUserId(userId);
		record.setOrderNo(orderNo);
		record.setRemark(remark);
		record.setDetails(details);
		final int rows = userWalletRecordService.insertUserWalletRecord(record);
		if (rows <= 0) {
			return false;
		}
		final UserWalletAccount lockAccount = userWalletAccountMapper.lockUserWalletAccountById(id);
		if (lockAccount == null) {
			return false;
		}
		final UserWalletAccount update = new UserWalletAccount();
		update.setId(id);
		update.setAmount(lockAccount.getAmount().subtract(amount));
		userWalletAccountMapper.updateUserWalletAccount(update);
		return true;
	}

	/**
	 * Increase amount.
	 *
	 * @param walletAccount the wallet account
	 * @param amount        the amount
	 * @param orderNo       the order no
	 * @param remark        the remark
	 * @param details       the details
	 * @return true, if successful
	 */
	@Override
	@Transactional
	public boolean increaseAmount(UserWalletAccount walletAccount, BigDecimal amount, String orderNo, String remark,
			Integer details) {
		if (walletAccount == null || MathUtils.isInvalid(amount) || StringUtils.isEmpty(orderNo)) {
			return false;
		}

		final Long id = walletAccount.getId();
		final Long userId = walletAccount.getUserId();
		final String symbol = walletAccount.getSymbol();
		final UserWalletRecord record = new UserWalletRecord();
		record.setAmount(amount);
		record.setSymbol(symbol);
		record.setType(UserWalletRecord.TYPE_IN);
		record.setKind(UserWalletRecord.KIND_AMOUNT);
		record.setUserId(userId);
		record.setOrderNo(orderNo);
		record.setDetails(details);
		if (StringUtils.isEmpty(remark)) {
			record.setRemark("Deposit");
		} else {
			record.setRemark(remark);
		}
		final int rows = userWalletRecordService.insertUserWalletRecord(record);
		if (rows <= 0) {
			return false;
		}
		final UserWalletAccount lockAccount = userWalletAccountMapper.lockUserWalletAccountById(id);
		if (lockAccount == null) {
			return false;
		}
		final UserWalletAccount update = new UserWalletAccount();
		update.setId(id);
		update.setAmount(MathUtils.plus(lockAccount.getAmount(), amount));
		userWalletAccountMapper.updateUserWalletAccount(update);
		return true;
	}

	/**
	 * Update account.
	 *
	 * @param account the account
	 * @param type    the type
	 * @param kind    the kind
	 * @param amount  the amount
	 * @param orderNo the order no
	 * @param remark  the remark
	 * @param days    the days
	 * @param details the details
	 * @return true, if successful
	 */
	@Override
	@Transactional
	public boolean updateAccount(UserWalletAccount account, Integer type, Integer kind, BigDecimal amount,
			String orderNo, String remark, Integer days, Integer details) {
		if (account == null || type == null || kind == null || MathUtils.isInvalid(amount)
				|| StringUtils.isEmpty(orderNo)) {
			return false;
		}
		// 类型不支持
		if (!(UserWalletRecord.TYPE_IN.equals(type) || UserWalletRecord.TYPE_OUT.equals(type))) {
			return false;
		}
		// 账号类型
		if (!(UserWalletRecord.KIND_AMOUNT.equals(kind) || UserWalletRecord.KIND_FROZEN.equals(kind)
				|| UserWalletRecord.KIND_LOCKED.equals(kind))) {
			return false;
		}

		final Long id = account.getId();
		final Long userId = account.getUserId();
		final String symbol = account.getSymbol();
		final UserWalletRecord record = new UserWalletRecord();
		record.setAmount(amount);
		record.setSymbol(symbol);
		record.setType(type);
		record.setKind(kind);
		record.setUserId(userId);
		record.setOrderNo(orderNo);
		record.setRemark(remark);
		record.setDays(days);
		record.setDetails(details);
		final int rows = userWalletRecordService.insertUserWalletRecord(record);
		if (rows <= 0) {
			return false;
		}
		final UserWalletAccount lockAccount = userWalletAccountMapper.lockUserWalletAccountById(id);
		if (lockAccount == null) {
			return false;
		}
		final UserWalletAccount update = new UserWalletAccount();
		update.setId(id);
		if (UserWalletRecord.TYPE_IN.equals(type)) {
			if (UserWalletRecord.KIND_AMOUNT.equals(kind)) {
				update.setAmount(MathUtils.plus(lockAccount.getAmount(), amount));
			} else if (UserWalletRecord.KIND_FROZEN.equals(kind)) {
				update.setFrozenAmount(MathUtils.plus(lockAccount.getFrozenAmount(), amount));
			} else if (UserWalletRecord.KIND_LOCKED.equals(kind)) {
				update.setLockedAmount(MathUtils.plus(lockAccount.getLockedAmount(), amount));
			}
		} else if (UserWalletRecord.TYPE_OUT.equals(type)) {
			if (UserWalletRecord.KIND_AMOUNT.equals(kind)) {
				update.setAmount(lockAccount.getAmount().subtract(amount));
			} else if (UserWalletRecord.KIND_FROZEN.equals(kind)) {
				update.setFrozenAmount(lockAccount.getFrozenAmount().subtract(amount));
			} else if (UserWalletRecord.KIND_LOCKED.equals(kind)) {
				update.setLockedAmount(lockAccount.getLockedAmount().subtract(amount));
			}
		}
		userWalletAccountMapper.updateUserWalletAccount(update);
		return true;
	}

	/**
	 * Select user account with balance.
	 *
	 * @param userId  the user id
	 * @param symbol  the symbol
	 * @param balance the balance
	 * @return the user wallet account
	 */
	@Override
	public UserWalletAccount selectUserAccountWithBalance(Long userId, String symbol, BigDecimal balance) {
		final UserWalletAccount account = selectUserWalletAccount(userId, symbol);
		if (account == null || !account.hasBalance(balance)) {
			return null;
		}
		return account;
	}

	/**
	 * Select user wallet account by address.
	 *
	 * @param address the address
	 * @param symbol  the symbol
	 * @return the user wallet account
	 */
	@Override
	public UserWalletAccount selectUserWalletAccountByAddress(String address, String symbol) {
		if (StringUtils.isEmpty(address)) {
			return null;
		}
		final UserWalletAccount query = new UserWalletAccount();
		query.setAddress(address);
		query.setSymbol(symbol);
		final List<UserWalletAccount> list = selectUserWalletAccountList(query);
		return list.isEmpty() ? null : list.get(0);
	}

	/**
	 * Release locked amount.
	 *
	 * @param record the record
	 * @return the int
	 */
	@Override
	@Transactional(rollbackFor = TransactionException.class)
	public int releaseLockedAmount(UserWalletRecord record) {
		if (record == null) {
			return 0;
		}
		final Long userId = record.getUserId();
		final BigDecimal amount = record.getAmount();
		final String symbol = record.getSymbol();
		final Integer type = record.getType();
		final Integer kind = record.getKind();
		if (userId == null || MathUtils.isInvalid(amount) || StringUtils.isEmpty(symbol)
				|| !UserWalletRecord.TYPE_IN.equals(type) || !UserWalletRecord.KIND_LOCKED.equals(kind)
				|| !IConstants.STATUS_NONE.equals(record.getStatus())) {
			return 0;
		}
		final UserWalletAccount account = selectUserWalletAccount(userId, symbol);
		if (account == null || MathUtils.lt(account.getLockedAmount(), amount)) {
			return 0;
		}
		final UserWalletAccount lockAccount = userWalletAccountMapper.lockUserWalletAccountById(account.getId());
		if (lockAccount == null) {
			return 0;
		}
		// 1. 扣除锁定余额
		final UserWalletAccount update = new UserWalletAccount();
		update.setId(account.getId());
		update.setLockedAmount(account.getLockedAmount().subtract(amount));
		int rows = updateUserWalletAccount(update);
		if (rows <= 0) {
			throw new TransactionException("decreaseLocked");
		}

		// 2. 更新钱包记录
		final UserWalletRecord finish = new UserWalletRecord();
		finish.setId(record.getId());
		finish.setStatus(IConstants.STATUS_FINISHED);
		finish.setKind(record.getKind());
		rows = userWalletRecordService.updateUserWalletRecord(finish);
		if (rows <= 0) {
			throw new TransactionException("finishRecord");
		}
		// 3. 打钱到可用余额
		if (!updateAccount(account, UserWalletRecord.TYPE_IN, UserWalletRecord.KIND_AMOUNT, amount, record.getOrderNo(),
				"【线性释放】（锁定余额）", null, record.getDetails())) {
			throw new TransactionException("releaseAmount");
		}
		return 1;
	}

	/**
	 * Release locked amount.
	 *
	 * @param userId        the user id
	 * @param symbol        the symbol
	 * @param orderNo       the order no
	 * @param lockedAmount  the locked amount
	 * @param releaseAmount the release amount
	 * @return true, if successful
	 * @throws TransactionException the transaction exception
	 */
	@Override
	@Transactional
	public boolean releaseLockedAmount(Long userId, String symbol, String orderNo, BigDecimal lockedAmount,
			BigDecimal releaseAmount) throws TransactionException {
		if (StringUtils.isEmpty(orderNo) || lockedAmount == null || releaseAmount == null) {
			return false;
		}
		final UserWalletAccount account = selectUserWalletAccount(userId, symbol);
		if (account == null || MathUtils.lt(account.getLockedAmount(), releaseAmount)) {
			return false;
		}

		final UserWalletRecord query = new UserWalletRecord();
		query.setUserId(userId);
		query.setType(UserWalletRecord.TYPE_IN);
		query.setKind(UserWalletRecord.KIND_LOCKED);
		query.setOrderNo(orderNo);
		final UserWalletRecord record = userWalletRecordService.selectUserWalletRecordOne(query);
		if (record == null || !IConstants.STATUS_NONE.equals(record.getStatus())
				|| !MathUtils.equals(lockedAmount, record.getAmount())) {
			return false;
		}
		// 释放次数已购
		final Integer days = record.getDays();
		if (days == null || days <= 0) {
			final UserWalletRecord finish = new UserWalletRecord();
			finish.setId(record.getId());
			finish.setStatus(IConstants.STATUS_FINISHED);
			finish.setKind(record.getKind());
			return userWalletRecordService.updateUserWalletRecord(finish) > 0;
		}
		final UserWalletAccount lockAccount = userWalletAccountMapper.lockUserWalletAccountById(account.getId());
		if (lockAccount == null) {
			return false;
		}
		// 1. 扣除锁定余额
		final UserWalletAccount update = new UserWalletAccount();
		update.setId(lockAccount.getId());
		update.setLockedAmount(lockAccount.getLockedAmount().subtract(releaseAmount));
		int rows = updateUserWalletAccount(update);
		if (rows <= 0) {
			throw new TransactionException("decreaseLocked");
		}

		// 2. 更新钱包记录
		final UserWalletRecord finish = new UserWalletRecord();
		finish.setId(record.getId());
		finish.setDays(days - 1);
		finish.setKind(record.getKind());
		if (days == 1) {
			finish.setStatus(IConstants.STATUS_FINISHED);
		}
		rows = userWalletRecordService.updateUserWalletRecord(finish);
		if (rows <= 0) {
			throw new TransactionException("finishRecord");
		}
		// 3. 打钱到可用余额
		if (!updateAccount(account, UserWalletRecord.TYPE_IN, UserWalletRecord.KIND_AMOUNT, releaseAmount,
				record.getOrderNo() + "_" + days, "【释放锁定余额】（第" + days + "天）", null, record.getDetails())) {
			throw new TransactionException("releaseAmount");
		}
		return true;
	}

	/**
	 * Admin set account by user id.
	 *
	 * @param userId the user id
	 * @param symbol the symbol
	 * @param kind   the kind
	 * @param value  the value
	 * @return the int
	 */
	@Override
	@Transactional
	public int adminSetAccountByUserId(Long userId, String symbol, Integer kind, BigDecimal value) {
		if (userId == null || MathUtils.isInvalid(value) || kind == null || StringUtils.isEmpty(symbol)) {
			return 0;
		}

		final UserWalletAccount account = selectUserWalletAccount(userId, symbol);
		if (account == null) {
			return 0;
		}

		final UserWalletRecord record = new UserWalletRecord();
		record.setUserId(userId);
		record.setAmount(value);
		record.setType(UserWalletRecord.TYPE_ADMIN);
		record.setKind(kind);
		record.setSymbol(symbol);
		record.setStatus(IConstants.STATUS_FINISHED);
		record.setRemark("Admin Setting");
		int rows = userWalletRecordService.insertUserWalletRecord(record);
		if (rows <= 0) {
			return 0;
		}

		final UserWalletAccount lockAccount = userWalletAccountMapper.lockUserWalletAccountById(account.getId());
		if (lockAccount == null) {
			return 0;
		}
		final UserWalletAccount update = new UserWalletAccount();
		update.setId(lockAccount.getId());
		if (UserWalletRecord.KIND_AMOUNT.equals(kind)) {
			update.setAmount(value);
		} else if (UserWalletRecord.KIND_FROZEN.equals(kind)) {
			update.setFrozenAmount(value);
		} else if (UserWalletRecord.KIND_LOCKED.equals(kind)) {
			update.setLockedAmount(value);
		} else {
			throw new TransactionException("不支持");
		}
		rows = updateUserWalletAccount(update);
		if (rows <= 0) {
			throw new TransactionException("account");
		}
		return 1;
	}

	/**
	 * Change user wallet account withdrawal enabled.
	 *
	 * @param userId  the user id
	 * @param symbol  the symbol
	 * @param enabled the enabled
	 * @return the int
	 */
	@Override
	public int changeUserWalletAccountWithdrawalEnabled(Long userId, String symbol, Integer enabled) {
		if (enabled == null) {
			return 0;
		}
		final UserWalletAccount account = selectUserWalletAccount(userId, symbol);
		if (account == null) {
			return 0;
		}
		final UserWalletAccount update = new UserWalletAccount();
		update.setId(account.getId());
		update.setWithdrawalEnabled(enabled);
		return updateUserWalletAccount(update);
	}

	/**
	 * Exchange amount.
	 *
	 * @param userId        the user id
	 * @param fromSymbol    the from symbol
	 * @param toSymbol      the to symbol
	 * @param price         the price
	 * @param positivePrice the positive price
	 * @param amount        the amount
	 * @param details       the details
	 * @return the common result
	 */
	@Override
	@Transactional
	public CommonResult<?> exchangeAmount(Long userId, String fromSymbol, String toSymbol, BigDecimal price,
			boolean positivePrice, BigDecimal amount, Integer details) {
		if (userId == null || StringUtils.isEmpty(fromSymbol) || StringUtils.isEmpty(toSymbol)) {
			return CommonResult.fail("兑换失败");
		}
		if (org.apache.commons.lang3.StringUtils.equals(fromSymbol, toSymbol)) {
			return CommonResult.success();
		}
		if (MathUtils.isEmpty(price)) {
			return CommonResult.fail("无法获取实时价格");
		}

		if (MathUtils.isEmpty(amount)) {
			return CommonResult.fail("兑换量无效");
		}

		final UserWalletAccount fromAccount = selectUserAccountWithBalance(userId, fromSymbol, amount);
		if (fromAccount == null) {
			return CommonResult.fail("余额不足");
		}
		final String timeId = IdWorker.getTimeId();
		if (!updateAccount(fromAccount, UserWalletAccount.TYPE_OUT, UserWalletAccount.KIND_AMOUNT, amount, timeId,
				"币种兑换", null, IMiningConstants.DETAILS_EXCHANGE)) {
			return CommonResult.fail("兑换失败");
		}
		final UserWalletAccount toAccount = selectUserWalletAccount(userId, toSymbol);

		final BigDecimal toAmount = positivePrice ? amount.multiply(price).setScale(6, RoundingMode.HALF_UP)
				: amount.divide(price, 6, RoundingMode.HALF_UP);

		if (!updateAccount(toAccount, UserWalletAccount.TYPE_IN, UserWalletAccount.KIND_AMOUNT, toAmount, timeId,
				"币种兑换", null, details)) {
			// 回退
			updateAccount(fromAccount, UserWalletAccount.TYPE_IN, UserWalletAccount.KIND_AMOUNT, amount, timeId, "币种兑换",
					null, details);
			return CommonResult.fail("兑换失败");
		}
		return CommonResult.success();
	}

	/**
	 * Select user account amount.
	 *
	 * @param symbol the symbol
	 * @param kind   the kind
	 * @return the big decimal
	 */
	@Override
	public BigDecimal selectUserAccountAmount(String symbol, Integer kind) {
		if (StringUtils.isEmpty(symbol) || kind == null) {
			return BigDecimal.ZERO;
		}
		return MathUtils.nullToZero(userWalletAccountMapper.selectUserAccountAmount(symbol, kind));
	}
}
