package com.soyatec.sword.wallet.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.core.text.Convert;
import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.common.utils.IdWorker;
import com.soyatec.sword.common.utils.QrcodeUtils;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.constants.IMiningConstants;
import com.soyatec.sword.exceptions.LockableException;
import com.soyatec.sword.exceptions.TransactionException;
import com.soyatec.sword.mining.domain.MiningSymbol;
import com.soyatec.sword.mining.service.IMiningSymbolService;
import com.soyatec.sword.service.ILockService;
import com.soyatec.sword.user.domain.UserCertificate;
import com.soyatec.sword.user.service.IUserCertificateService;
import com.soyatec.sword.utils.MathUtils;
import com.soyatec.sword.wallet.domain.UserWalletAccount;
import com.soyatec.sword.wallet.domain.UserWalletRecord;
import com.soyatec.sword.wallet.mapper.UserWalletAccountMapper;
import com.soyatec.sword.wallet.service.IUserWalletAccountService;
import com.soyatec.sword.wallet.service.IUserWalletRecordService;
import com.soyatec.sword.wallet.service.IWalletService;

/**
 * 用户钱包账号Service业务层处理
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
@Service
public class UserWalletAccountServiceImpl implements IUserWalletAccountService {
	@Autowired
	private UserWalletAccountMapper userWalletAccountMapper;

	@Autowired
	private IUserWalletRecordService userWalletRecordService;

	@Autowired
	private IWalletService walletService;

	@Autowired
	private ILockService lockService;

	@Autowired
	private IMiningSymbolService symbolService;

	@Autowired
	private IUserCertificateService certificateService;

	/**
	 * 查询用户钱包账号
	 * 
	 * @param id 用户钱包账号ID
	 * @return 用户钱包账号
	 */
	@Override
	public UserWalletAccount selectUserWalletAccountById(Long id) {
		return userWalletAccountMapper.selectUserWalletAccountById(id);
	}

	/**
	 * 查询用户钱包账号列表
	 * 
	 * @param userWalletAccount 用户钱包账号
	 * @return 用户钱包账号
	 */
	@Override
	public List<UserWalletAccount> selectUserWalletAccountList(UserWalletAccount userWalletAccount) {
		List<UserWalletAccount> list = userWalletAccountMapper.selectUserWalletAccountList(userWalletAccount);
		list.forEach(account -> {
			account.setWithdrawal(symbolService.selectMiningSymbolById(account.getSymbol()));
		});
		return list;
	}

	/**
	 * 新增用户钱包账号
	 * 
	 * @param userWalletAccount 用户钱包账号
	 * @return 结果
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
	 * 修改用户钱包账号
	 * 
	 * @param userWalletAccount 用户钱包账号
	 * @return 结果
	 */
	@Override
	public int updateUserWalletAccount(UserWalletAccount userWalletAccount) {
		userWalletAccount.setUpdateTime(DateUtils.getNowDate());
		return userWalletAccountMapper.updateUserWalletAccount(userWalletAccount);
	}

	/**
	 * 删除用户钱包账号对象
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteUserWalletAccountByIds(String ids) {
		return userWalletAccountMapper.deleteUserWalletAccountByIds(Convert.toStrArray(ids));
	}

	/**
	 * 删除用户钱包账号信息
	 * 
	 * @param id 用户钱包账号ID
	 * @return 结果
	 */
	@Override
	public int deleteUserWalletAccountById(Long id) {
		return userWalletAccountMapper.deleteUserWalletAccountById(id);
	}

	@Override
	public UserWalletAccount selectUserWalletAccount(Long userId, String symbol) {
		if (userId == null || StringUtils.isEmpty(symbol)) {
			return null;
		}
		UserWalletAccount account = userWalletAccountMapper.selectUserWalletAccount(userId, symbol);
		if (account == null) {
			account = createUserWalletAccount(userId, symbol);
		}
		return account;
	}

	private UserWalletAccount createUserWalletAccount(Long userId, String symbol) {
		CommonResult<?> checked = certificateService.checkUserCertificate(userId, UserCertificate.KIND_WALLET);
		if (!checked.isSuccess()) {
			return null;
		}

		UserWalletAccount account = userWalletAccountMapper.selectUserWalletAccount(userId, symbol);
		if (account == null) {
			account = new UserWalletAccount();
			account.setUserId(userId);
			account.setSymbol(symbol);
			account.setAddress(walletService.getDepositAddressValue(symbol, IMiningConstants.CHAIN));
			account.setAddressUrl(generateQrcodeUrl(account.getAddress()));
			insertUserWalletAccount(account);
		} else if (StringUtils.isEmpty(account.getAddress())) {
			String address = walletService.getDepositAddressValue(symbol, IMiningConstants.CHAIN);
			if (StringUtils.isNotEmpty(address)) {
				account.setAddress(address);
				account.setAddressUrl(generateQrcodeUrl(account.getAddress()));
				updateUserWalletAccount(account);
			}
		}
		return account;
	}

	@Override
	public int updateUserWalletAccountByUserId(Long userId) {
		List<MiningSymbol> symbols = symbolService.selectMiningSymbolList(new MiningSymbol());
		for (MiningSymbol miningSymbol : symbols) {
			createUserWalletAccount(userId, miningSymbol.getSymbol());
		}
		return 1;
	}

	private String generateQrcodeUrl(String address) {
		if (StringUtils.isEmpty(address)) {
			return null;
		}
		return QrcodeUtils.generate(address);
	}

	private boolean tryLock(UserWalletAccount account) throws LockableException {
		if (account == null) {
			throw new LockableException();
		}
		return tryLock(account.getUserId());
	}

	private boolean tryLock(Long userId) throws LockableException {
		if (userId == null) {
			throw new LockableException();
		}
		return lockService.tryLock("lock-wallet-account:" + userId);
	}

	private boolean releaseLock(UserWalletAccount account) {
		if (account == null || account.getUserId() == null) {
			return false;
		}
		return releaseLock(account.getUserId());
	}

	private boolean releaseLock(Long userId) {
		if (userId == null) {
			return false;
		}
		return lockService.releaseLock("lock-wallet-account:" + userId);
	}

	@Override
	public List<UserWalletAccount> selectUserWalletAccountListByUserId(Long userId) {
		if (userId == null) {
			return Collections.emptyList();
		}
		UserWalletAccount query = new UserWalletAccount();
		query.setUserId(userId);
		return selectUserWalletAccountList(query);
	}

	@Override
	public boolean freezeAmount(UserWalletAccount walletAccount, BigDecimal amount, String orderNo,
			Integer freezeDays) {
		if (walletAccount == null || MathUtils.isEmpty(amount) || StringUtils.isEmpty(orderNo)) {
			return false;
		}
		BigDecimal walletAmount = walletAccount.getAmount();
		if (MathUtils.isEmpty(walletAmount) || MathUtils.lt(walletAmount, amount)) {
			return false;
		}
		boolean locked = false;
		try {
			locked = tryLock(walletAccount);

			Long id = walletAccount.getId();
			Long userId = walletAccount.getUserId();
			String symbol = walletAccount.getSymbol();
			UserWalletRecord record = new UserWalletRecord();
			record.setAmount(amount);
			record.setSymbol(symbol);
			record.setType(UserWalletRecord.TYPE_OUT);
			record.setKind(UserWalletRecord.KIND_FROZEN);
			record.setUserId(userId);
			record.setStatus(UserWalletRecord.STATUS_NONE);
			if (freezeDays != null) {
				record.setDays(freezeDays);
			} else {
				record.setDays(-1);
			}
			record.setOrderNo(orderNo);
			int rows = userWalletRecordService.insertUserWalletRecord(record);
			if (rows <= 0) {
				return false;
			}
			UserWalletAccount lockAccount = userWalletAccountMapper.lockUserWalletAccountById(id);
			if (lockAccount == null) {
				return false;
			}
			UserWalletAccount update = new UserWalletAccount();
			update.setId(id);
			update.setAmount(walletAmount.subtract(amount));
			update.setFrozenAmount(MathUtils.plus(amount, walletAccount.getFrozenAmount()));
			userWalletAccountMapper.updateUserWalletAccount(update);
			return true;
		} finally {
			if (locked) {
				releaseLock(walletAccount);
			}
		}

	}

	@Override
	public boolean unfreezeAmount(Long userId, String symbol, BigDecimal amount, String orderNo, boolean payBack) {
		if (userId == null || StringUtils.isEmpty(symbol) || MathUtils.isInvalid(amount)
				|| StringUtils.isEmpty(orderNo)) {
			return false;
		}
		UserWalletAccount account = selectUserWalletAccount(userId, symbol);
		if (account == null) {
			return false;
		}
		boolean isLocked = false;
		try {
			isLocked = tryLock(userId);

			UserWalletRecord query = new UserWalletRecord();
			query.setUserId(userId);
			query.setOrderNo(orderNo);
			// query.setAmount(amount);
			query.setType(UserWalletRecord.TYPE_OUT);
			query.setKind(UserWalletRecord.KIND_FROZEN);
			query.setStatus(UserWalletRecord.STATUS_NONE);
			List<UserWalletRecord> list = userWalletRecordService.selectUserWalletRecordList(query).stream()
					.filter(r -> MathUtils.equals(amount, r.getAmount())).collect(Collectors.toList());
			if (list.isEmpty()) {
				return false;
			}

			UserWalletAccount lockAccount = userWalletAccountMapper.lockUserWalletAccountById(account.getId());
			if (lockAccount == null || MathUtils.lt(lockAccount.getFrozenAmount(), amount)) {
				return false;
			}

			UserWalletAccount update = new UserWalletAccount();
			update.setId(account.getId());
			// 返还余额
			if (payBack) {
				update.setAmount(MathUtils.plus(lockAccount.getAmount(), amount));
			}
			update.setFrozenAmount(lockAccount.getFrozenAmount().subtract(amount));
			int rows = userWalletAccountMapper.updateUserWalletAccount(update);
			if (rows > 0) {
				list.forEach(r -> {
					UserWalletRecord record = new UserWalletRecord();
					record.setId(r.getId());
					record.setStatus(UserWalletRecord.STATUS_FINISHED);
					record.setRemark("已解冻【" + payBack + "】");
					userWalletRecordService.updateUserWalletRecord(record);
				});
			}
			return true;
		} finally {
			if (isLocked) {
				releaseLock(userId);
			}
		}
	}

	@Override
	public boolean unfreezeAmount(UserWalletRecord record) {
		if (record == null || !UserWalletRecord.STATUS_NONE.equals(record.getStatus())) {
			return false;
		}
		Long userId = record.getUserId();
		String symbol = record.getSymbol();
		UserWalletAccount account = selectUserWalletAccount(userId, symbol);
		if (account == null) {
			return false;
		}
		BigDecimal amount = record.getAmount();
		boolean isLocked = false;
		try {
			isLocked = tryLock(userId);

			UserWalletAccount lockAccount = userWalletAccountMapper.lockUserWalletAccountById(account.getId());
			if (lockAccount == null || MathUtils.lt(lockAccount.getFrozenAmount(), amount)) {
				return false;
			}

			UserWalletAccount update = new UserWalletAccount();
			update.setId(account.getId());
			update.setAmount(MathUtils.plus(lockAccount.getAmount(), amount));
			update.setFrozenAmount(lockAccount.getFrozenAmount().subtract(amount));
			int rows = userWalletAccountMapper.updateUserWalletAccount(update);
			if (rows > 0) {
				UserWalletRecord finish = new UserWalletRecord();
				finish.setId(record.getId());
				finish.setStatus(UserWalletRecord.STATUS_FINISHED);
				finish.setRemark("已解冻");
				userWalletRecordService.updateUserWalletRecord(finish);
			}
			return true;
		} finally {
			if (isLocked) {
				releaseLock(userId);
			}
		}
	}

	@Override
	public boolean decreaseAmount(UserWalletAccount walletAccount, BigDecimal amount, String orderNo) {
		if (walletAccount == null || MathUtils.isEmpty(amount) || StringUtils.isEmpty(orderNo)) {
			return false;
		}
		BigDecimal walletAmount = walletAccount.getAmount();
		if (MathUtils.isEmpty(walletAmount) || MathUtils.lt(walletAmount, amount)) {
			return false;
		}
		boolean isLocked = false;
		try {
			isLocked = tryLock(walletAccount);

			Long id = walletAccount.getId();
			Long userId = walletAccount.getUserId();
			String symbol = walletAccount.getSymbol();
			UserWalletRecord record = new UserWalletRecord();
			record.setAmount(amount);
			record.setSymbol(symbol);
			record.setType(UserWalletRecord.TYPE_OUT);
			record.setKind(UserWalletRecord.KIND_AMOUNT);
			record.setUserId(userId);
			record.setOrderNo(orderNo);
			int rows = userWalletRecordService.insertUserWalletRecord(record);
			if (rows <= 0) {
				return false;
			}
			UserWalletAccount lockAccount = userWalletAccountMapper.lockUserWalletAccountById(id);
			if (lockAccount == null) {
				return false;
			}
			UserWalletAccount update = new UserWalletAccount();
			update.setId(id);
			update.setAmount(walletAmount.subtract(amount));
			userWalletAccountMapper.updateUserWalletAccount(update);
			return true;
		} finally {
			if (isLocked) {
				releaseLock(walletAccount);
			}
		}
	}

	@Override
	public boolean increaseAmount(UserWalletAccount walletAccount, BigDecimal amount, String orderNo, String remark) {
		if (walletAccount == null || MathUtils.isInvalid(amount) || StringUtils.isEmpty(orderNo)) {
			return false;
		}
		boolean isLocked = false;
		try {
			isLocked = tryLock(walletAccount);

			Long id = walletAccount.getId();
			Long userId = walletAccount.getUserId();
			String symbol = walletAccount.getSymbol();
			UserWalletRecord record = new UserWalletRecord();
			record.setAmount(amount);
			record.setSymbol(symbol);
			record.setType(UserWalletRecord.TYPE_IN);
			record.setKind(UserWalletRecord.KIND_AMOUNT);
			record.setUserId(userId);
			record.setOrderNo(orderNo);
			if (StringUtils.isEmpty(remark)) {
				record.setRemark("充值");
			} else {
				record.setRemark(remark);
			}
			int rows = userWalletRecordService.insertUserWalletRecord(record);
			if (rows <= 0) {
				return false;
			}
			UserWalletAccount lockAccount = userWalletAccountMapper.lockUserWalletAccountById(id);
			if (lockAccount == null) {
				return false;
			}
			UserWalletAccount update = new UserWalletAccount();
			update.setId(id);
			update.setAmount(MathUtils.plus(lockAccount.getAmount(), amount));
			userWalletAccountMapper.updateUserWalletAccount(update);
			return true;
		} finally {
			if (isLocked) {
				releaseLock(walletAccount);
			}
		}
	}

	@Override
	public boolean updateAccount(UserWalletAccount account, Integer type, Integer kind, BigDecimal amount,
			String orderNo, String remark, Integer days) {
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

		boolean isLocked = false;
		try {
			isLocked = tryLock(account);

			Long id = account.getId();
			Long userId = account.getUserId();
			String symbol = account.getSymbol();
			UserWalletRecord record = new UserWalletRecord();
			record.setAmount(amount);
			record.setSymbol(symbol);
			record.setType(type);
			record.setKind(kind);
			record.setUserId(userId);
			record.setOrderNo(orderNo);
			record.setRemark(remark);
			record.setDays(days);
			int rows = userWalletRecordService.insertUserWalletRecord(record);
			if (rows <= 0) {
				return false;
			}
			UserWalletAccount lockAccount = userWalletAccountMapper.lockUserWalletAccountById(id);
			if (lockAccount == null) {
				return false;
			}
			UserWalletAccount update = new UserWalletAccount();
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
		} finally {
			if (isLocked) {
				releaseLock(account);
			}
		}
	}

	@Override
	public UserWalletAccount selectUserAccountWithBalance(Long userId, String symbol, BigDecimal balance) {
		UserWalletAccount account = selectUserWalletAccount(userId, symbol);
		if (account == null || !account.hasBalance(balance)) {
			return null;
		}
		return account;
	}

	@Override
	public UserWalletAccount selectUserWalletAccountByAddress(String address, String symbol) {
		if (StringUtils.isEmpty(address)) {
			return null;
		}
		UserWalletAccount query = new UserWalletAccount();
		query.setAddress(address);
		query.setSymbol(symbol);
		List<UserWalletAccount> list = selectUserWalletAccountList(query);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	@Transactional(rollbackFor = TransactionException.class)
	public int releaseLockedAmount(UserWalletRecord record) {
		if (record == null) {
			return 0;
		}
		Long userId = record.getUserId();
		BigDecimal amount = record.getAmount();
		String symbol = record.getSymbol();
		Integer type = record.getType();
		Integer kind = record.getKind();
		if (userId == null || MathUtils.isInvalid(amount) || StringUtils.isEmpty(symbol)
				|| !UserWalletRecord.TYPE_IN.equals(type) || !UserWalletRecord.KIND_LOCKED.equals(kind)
				|| !UserWalletRecord.STATUS_NONE.equals(record.getStatus())) {
			return 0;
		}
		UserWalletAccount account = selectUserWalletAccount(userId, symbol);
		if (account == null || MathUtils.lt(account.getLockedAmount(), amount)) {
			return 0;
		}
		boolean isLocked = false;
		try {
			isLocked = tryLock(account);
			UserWalletAccount lockAccount = userWalletAccountMapper.lockUserWalletAccountById(account.getId());
			if (lockAccount == null) {
				return 0;
			}
			// 1. 扣除锁定余额
			UserWalletAccount update = new UserWalletAccount();
			update.setId(account.getId());
			update.setLockedAmount(account.getLockedAmount().subtract(amount));
			int rows = updateUserWalletAccount(update);
			if (rows <= 0) {
				throw new TransactionException("decreaseLocked");
			}

			// 2. 更新钱包记录
			UserWalletRecord finish = new UserWalletRecord();
			finish.setId(record.getId());
			finish.setStatus(UserWalletRecord.STATUS_FINISHED);
			rows = userWalletRecordService.updateUserWalletRecord(finish);
			if (rows <= 0) {
				throw new TransactionException("finishRecord");
			}
		} finally {
			if (isLocked) {
				releaseLock(userId);
			}
		}
		// 3. 打钱到可用余额
		if (!updateAccount(account, UserWalletRecord.TYPE_IN, UserWalletRecord.KIND_AMOUNT, amount, record.getOrderNo(),
				"释放锁定余额", null)) {
			throw new TransactionException("releaseAmount");
		}
		return 1;
	}

	@Override
	@Transactional
	public boolean releaseLockedAmount(Long userId, String symbol, String orderNo, BigDecimal lockedAmount,
			BigDecimal releaseAmount) throws TransactionException {
		if (StringUtils.isEmpty(orderNo) || lockedAmount == null || releaseAmount == null) {
			return false;
		}
		UserWalletAccount account = selectUserWalletAccount(userId, symbol);
		if (account == null || MathUtils.lt(account.getLockedAmount(), releaseAmount)) {
			return false;
		}

		UserWalletRecord query = new UserWalletRecord();
		query.setUserId(userId);
		query.setType(UserWalletRecord.TYPE_IN);
		query.setKind(UserWalletRecord.KIND_LOCKED);
		query.setOrderNo(orderNo);
		List<UserWalletRecord> list = userWalletRecordService.selectUserWalletRecordList(query);
		if (list.isEmpty()) {
			return false;
		}
		UserWalletRecord record = list.get(0);
		if (!UserWalletRecord.STATUS_NONE.equals(record.getStatus())
				|| !MathUtils.equals(lockedAmount, record.getAmount())) {
			return false;
		}
		// 释放次数已购
		Integer days = record.getDays();
		if (days == null || days <= 0) {
			UserWalletRecord finish = new UserWalletRecord();
			finish.setId(record.getId());
			finish.setStatus(UserWalletRecord.STATUS_FINISHED);
			return userWalletRecordService.updateUserWalletRecord(finish) > 0;
		}
		boolean isLocked = false;
		try {
			isLocked = tryLock(account);
			UserWalletAccount lockAccount = userWalletAccountMapper.lockUserWalletAccountById(account.getId());
			if (lockAccount == null) {
				return false;
			}
			// 1. 扣除锁定余额
			UserWalletAccount update = new UserWalletAccount();
			update.setId(account.getId());
			update.setLockedAmount(account.getLockedAmount().subtract(releaseAmount));
			int rows = updateUserWalletAccount(update);
			if (rows <= 0) {
				throw new TransactionException("decreaseLocked");
			}

			// 2. 更新钱包记录
			UserWalletRecord finish = new UserWalletRecord();
			finish.setId(record.getId());
			finish.setDays(days - 1);
			if (days == 1) {
				finish.setStatus(UserWalletRecord.STATUS_FINISHED);
			}
			rows = userWalletRecordService.updateUserWalletRecord(finish);
			if (rows <= 0) {
				throw new TransactionException("finishRecord");
			}
		} finally {
			if (isLocked) {
				releaseLock(userId);
			}
		}
		// 3. 打钱到可用余额
		if (!updateAccount(account, UserWalletRecord.TYPE_IN, UserWalletRecord.KIND_AMOUNT, releaseAmount,
				record.getOrderNo() + "_" + days, "释放锁定余额", null)) {
			throw new TransactionException("releaseAmount");
		}
		return true;
	}

	@Override
	@Transactional
	public int adminSetAccountByUserId(Long userId, String symbol, Integer kind, BigDecimal value) {
		if (userId == null || MathUtils.isInvalid(value) || kind == null || StringUtils.isEmpty(symbol)) {
			return 0;
		}

		UserWalletAccount account = selectUserWalletAccount(userId, symbol);
		if (account == null) {
			return 0;
		}

		boolean isLocked = false;
		try {
			isLocked = tryLock(userId);
			UserWalletRecord record = new UserWalletRecord();
			record.setUserId(userId);
			record.setAmount(value);
			record.setType(UserWalletRecord.TYPE_ADMIN);
			record.setKind(kind);
			record.setSymbol(symbol);
			record.setStatus(UserWalletRecord.STATUS_FINISHED);
			record.setRemark("后台设置");
			int rows = userWalletRecordService.insertUserWalletRecord(record);
			if (rows <= 0) {
				return 0;
			}

			UserWalletAccount lockAccount = userWalletAccountMapper.lockUserWalletAccountById(account.getId());
			if (lockAccount == null) {
				return 0;
			}
			UserWalletAccount update = new UserWalletAccount();
			update.setId(account.getId());
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
		} finally {
			if (isLocked) {
				releaseLock(userId);
			}
		}
		return 1;
	}

	@Override
	public int changeUserWalletAccountWithdrawalEnabled(Long userId, String symbol, Integer enabled) {
		if (enabled == null) {
			return 0;
		}
		UserWalletAccount account = selectUserWalletAccount(userId, symbol);
		if (account == null) {
			return 0;
		}
		UserWalletAccount update = new UserWalletAccount();
		update.setId(account.getId());
		update.setWithdrawalEnabled(enabled);
		return updateUserWalletAccount(update);
	}

	@Override
	@Transactional
	public CommonResult<?> exchangeAmount(Long userId, String fromSymbol, String toSymbol, BigDecimal price,
			boolean positivePrice, BigDecimal amount) {
		if (userId == null || StringUtils.isEmpty(fromSymbol) || StringUtils.isEmpty(toSymbol)) {
			return CommonResult.fail("兑换失败");
		}
		if (StringUtils.equals(fromSymbol, toSymbol)) {
			return CommonResult.success();
		}
		if (MathUtils.isEmpty(price)) {
			return CommonResult.fail("无法获取实时价格");
		}

		if (MathUtils.isEmpty(amount)) {
			return CommonResult.fail("兑换量无效");
		}

		UserWalletAccount fromAccount = selectUserAccountWithBalance(userId, fromSymbol, amount);
		if (fromAccount == null) {
			return CommonResult.fail("余额不足");
		}
		String timeId = IdWorker.getTimeId();
		if (!updateAccount(fromAccount, UserWalletAccount.TYPE_OUT, UserWalletAccount.KIND_AMOUNT, amount, timeId,
				"币种兑换", null)) {
			return CommonResult.fail("兑换失败");
		}
		UserWalletAccount toAccount = selectUserWalletAccount(userId, toSymbol);

		BigDecimal toAmount = positivePrice ? amount.multiply(price).setScale(6, RoundingMode.HALF_UP)
				: amount.divide(price, 6, RoundingMode.HALF_UP);

		if (!updateAccount(toAccount, UserWalletAccount.TYPE_IN, UserWalletAccount.KIND_AMOUNT, toAmount, timeId,
				"币种兑换", null)) {
			// 回退
			updateAccount(fromAccount, UserWalletAccount.TYPE_IN, UserWalletAccount.KIND_AMOUNT, amount, timeId, "币种兑换",
					null);
			return CommonResult.fail("兑换失败");
		}
		return CommonResult.success();
	}

	@Override
	public BigDecimal selectUserAccountAmount(String symbol, Integer kind) {
		if (StringUtils.isEmpty(symbol) || kind == null) {
			return BigDecimal.ZERO;
		}
		return MathUtils.nullToZero(userWalletAccountMapper.selectUserAccountAmount(symbol, kind));
	}
}