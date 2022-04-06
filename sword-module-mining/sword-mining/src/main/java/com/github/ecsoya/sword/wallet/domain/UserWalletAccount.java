package com.github.ecsoya.sword.wallet.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;
import com.github.ecsoya.sword.constants.IConstants;
import com.github.ecsoya.sword.mining.domain.MiningSymbol;
import com.github.ecsoya.sword.utils.MathUtils;

/**
 * The Class UserWalletAccount.
 */
@JsonIgnoreProperties(allowSetters = true, ignoreUnknown = true, value = { "createTime", "updateTime", "params",
		"remark", "createBy", "updateBy", "searchValue", "id" })
public class UserWalletAccount extends BaseEntity implements IConstants {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant KIND_AMOUNT. */
	public static final Integer KIND_AMOUNT = 0;// 余额

	/** The Constant KIND_FROZEN. */
	public static final Integer KIND_FROZEN = 1;// 冻结

	/** The Constant KIND_LOCKED. */
	public static final Integer KIND_LOCKED = 2;// 锁定

	/** The Constant WITHDRAWAL_ENABLED_ON. */
	public static final Integer WITHDRAWAL_ENABLED_ON = 0;

	/** The Constant WITHDRAWAL_ENABLED_OFF. */
	public static final Integer WITHDRAWAL_ENABLED_OFF = 1;

	/** The Constant TYPE_IN. */
	public static final Integer TYPE_IN = 0; // 收入

	/** The Constant TYPE_OUT. */
	public static final Integer TYPE_OUT = 1;// 支出

	/** The Constant TYPE_ADMIN. */
	public static final Integer TYPE_ADMIN = 2;// 支出

	/** The id. */
	private Long id;

	/** The user id. */
	@Excel(name = "用户Id")
	private Long userId;

	/** The symbol. */
	@Excel(name = "币种")
	private String symbol;

	/** The chain. */
	private String chain;

	/** The address. */
	@Excel(name = "地址")
	private String address;

	/** The address url. */
	@Excel(name = "地址二维码URL")
	private String addressUrl;

	/** The amount. */
	@Excel(name = "金额")
	private BigDecimal amount;

	/** The last amount. */
	@Excel(name = "昨日金额")
	private BigDecimal lastAmount;

	/** The min hold amount. */
	@Excel(name = "最低持币量")
	private BigDecimal minHoldAmount;

	/** The frozen amount. */
	@Excel(name = "冻结金额")
	private BigDecimal frozenAmount;

	/** The locked amount. */
	@Excel(name = "锁定金额")
	private BigDecimal lockedAmount;

	/** The withdrawal enabled. */
	private Integer withdrawalEnabled;

	/** The withdrawal fee. */
	private BigDecimal withdrawalFee;

	/** The withdrawal. */
	private MiningSymbol withdrawal;

	/**
	 * Sets the iD.
	 *
	 * @param id the new iD
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the iD.
	 *
	 * @return the iD
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the 用户Id.
	 *
	 * @param userId the new 用户Id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Gets the 用户Id.
	 *
	 * @return the 用户Id
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * Sets the 币种.
	 *
	 * @param symbol the new 币种
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Gets the 币种.
	 *
	 * @return the 币种
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Sets the 地址.
	 *
	 * @param address the new 地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the 地址.
	 *
	 * @return the 地址
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the 地址二维码URL.
	 *
	 * @param addressUrl the new 地址二维码URL
	 */
	public void setAddressUrl(String addressUrl) {
		this.addressUrl = addressUrl;
	}

	/**
	 * Gets the 地址二维码URL.
	 *
	 * @return the 地址二维码URL
	 */
	public String getAddressUrl() {
		return addressUrl;
	}

	/**
	 * Sets the 金额.
	 *
	 * @param amount the new 金额
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * Gets the 金额.
	 *
	 * @return the 金额
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * Sets the 昨日金额.
	 *
	 * @param lastAmount the new 昨日金额
	 */
	public void setLastAmount(BigDecimal lastAmount) {
		this.lastAmount = lastAmount;
	}

	/**
	 * Gets the 昨日金额.
	 *
	 * @return the 昨日金额
	 */
	public BigDecimal getLastAmount() {
		return lastAmount;
	}

	/**
	 * Sets the 最低持币量.
	 *
	 * @param minHoldAmount the new 最低持币量
	 */
	public void setMinHoldAmount(BigDecimal minHoldAmount) {
		this.minHoldAmount = minHoldAmount;
	}

	/**
	 * Gets the 最低持币量.
	 *
	 * @return the 最低持币量
	 */
	public BigDecimal getMinHoldAmount() {
		return minHoldAmount;
	}

	/**
	 * Sets the 冻结金额.
	 *
	 * @param frozenAmount the new 冻结金额
	 */
	public void setFrozenAmount(BigDecimal frozenAmount) {
		this.frozenAmount = frozenAmount;
	}

	/**
	 * Gets the 冻结金额.
	 *
	 * @return the 冻结金额
	 */
	public BigDecimal getFrozenAmount() {
		return frozenAmount;
	}

	/**
	 * Sets the 锁定金额.
	 *
	 * @param lockedAmount the new 锁定金额
	 */
	public void setLockedAmount(BigDecimal lockedAmount) {
		this.lockedAmount = lockedAmount;
	}

	/**
	 * Gets the 锁定金额.
	 *
	 * @return the 锁定金额
	 */
	public BigDecimal getLockedAmount() {
		return lockedAmount;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
				.append("userId", getUserId()).append("symbol", getSymbol()).append("address", getAddress())
				.append("addressUrl", getAddressUrl()).append("amount", getAmount())
				.append("lastAmount", getLastAmount()).append("minHoldAmount", getMinHoldAmount())
				.append("frozenAmount", getFrozenAmount()).append("lockedAmount", getLockedAmount())
				.append("createTime", getCreateTime()).append("updateTime", getUpdateTime())
				.append("remark", getRemark()).toString();
	}

	/**
	 * Checks for balance.
	 *
	 * @param balance the balance
	 * @return true, if successful
	 */
	@JsonIgnore
	public boolean hasBalance(BigDecimal balance) {
		if (MathUtils.isEmpty(balance)) {
			return true;
		}
		if (MathUtils.isEmpty(amount)) {
			return false;
		}
		return MathUtils.lte(balance, amount);
	}

	/**
	 * Gets the withdrawal fee.
	 *
	 * @return the withdrawal fee
	 */
	public BigDecimal getWithdrawalFee() {
		return withdrawalFee;
	}

	/**
	 * Sets the withdrawal fee.
	 *
	 * @param withdrawalFee the new withdrawal fee
	 */
	public void setWithdrawalFee(BigDecimal withdrawalFee) {
		this.withdrawalFee = withdrawalFee;
	}

	/**
	 * Gets the 提币开关.
	 *
	 * @return the 提币开关
	 */
	public Integer getWithdrawalEnabled() {
		return withdrawalEnabled;
	}

	/**
	 * Sets the 提币开关.
	 *
	 * @param withdrawalEnabled the new 提币开关
	 */
	public void setWithdrawalEnabled(Integer withdrawalEnabled) {
		this.withdrawalEnabled = withdrawalEnabled;
	}

	/**
	 * Chech withdrawal enabled.
	 *
	 * @return true, if successful
	 */
	public boolean chechWithdrawalEnabled() {
		return WITHDRAWAL_ENABLED_ON.equals(withdrawalEnabled);
	}

	/**
	 * Gets the withdrawal.
	 *
	 * @return the withdrawal
	 */
	public MiningSymbol getWithdrawal() {
		return withdrawal;
	}

	/**
	 * Sets the withdrawal.
	 *
	 * @param withdrawal the new withdrawal
	 */
	public void setWithdrawal(MiningSymbol withdrawal) {
		this.withdrawal = withdrawal;
	}

	/**
	 * Gets the chain.
	 *
	 * @return the chain
	 */
	public String getChain() {
		return chain;
	}

	/**
	 * Sets the chain.
	 *
	 * @param chain the new chain
	 */
	public void setChain(String chain) {
		this.chain = chain;
	}

}
