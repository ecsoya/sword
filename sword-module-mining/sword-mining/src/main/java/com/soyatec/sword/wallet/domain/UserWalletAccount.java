package com.soyatec.sword.wallet.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.soyatec.sword.common.annotation.Excel;
import com.soyatec.sword.common.core.domain.BaseEntity;
import com.soyatec.sword.constants.IConstants;
import com.soyatec.sword.mining.domain.MiningSymbol;
import com.soyatec.sword.utils.MathUtils;

/**
 * 用户钱包账号对象 t_user_wallet_account
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
@JsonIgnoreProperties(allowSetters = true, ignoreUnknown = true, value = { "createTime", "updateTime", "params",
		"remark", "createBy", "updateBy", "searchValue", "id" })
public class UserWalletAccount extends BaseEntity implements IConstants {
	private static final long serialVersionUID = 1L;

	public static final Integer KIND_AMOUNT = 0;// 余额
	public static final Integer KIND_FROZEN = 1;// 冻结
	public static final Integer KIND_LOCKED = 2;// 锁定

	public static final Integer WITHDRAWAL_ENABLED_ON = 0;
	public static final Integer WITHDRAWAL_ENABLED_OFF = 1;

	public static final Integer TYPE_IN = 0; // 收入
	public static final Integer TYPE_OUT = 1;// 支出
	public static final Integer TYPE_ADMIN = 2;// 支出

	/** ID */
	private Long id;

	/** 用户Id */
	@Excel(name = "用户Id")
	private Long userId;

	/** 币种 */
	@Excel(name = "币种")
	private String symbol;

	private String chain;

	/** 地址 */
	@Excel(name = "地址")
	private String address;

	/** 地址二维码URL */
	@Excel(name = "地址二维码URL")
	private String addressUrl;

	/** 金额 */
	@Excel(name = "金额")
	private BigDecimal amount;

	/** 昨日金额 */
	@Excel(name = "昨日金额")
	private BigDecimal lastAmount;

	/** 最低持币量 */
	@Excel(name = "最低持币量")
	private BigDecimal minHoldAmount;

	/** 冻结金额 */
	@Excel(name = "冻结金额")
	private BigDecimal frozenAmount;

	/** 锁定金额 */
	@Excel(name = "锁定金额")
	private BigDecimal lockedAmount;

	/**
	 * 提币开关
	 */
	private Integer withdrawalEnabled;

	private BigDecimal withdrawalFee;

	private MiningSymbol withdrawal;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setAddressUrl(String addressUrl) {
		this.addressUrl = addressUrl;
	}

	public String getAddressUrl() {
		return addressUrl;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setLastAmount(BigDecimal lastAmount) {
		this.lastAmount = lastAmount;
	}

	public BigDecimal getLastAmount() {
		return lastAmount;
	}

	public void setMinHoldAmount(BigDecimal minHoldAmount) {
		this.minHoldAmount = minHoldAmount;
	}

	public BigDecimal getMinHoldAmount() {
		return minHoldAmount;
	}

	public void setFrozenAmount(BigDecimal frozenAmount) {
		this.frozenAmount = frozenAmount;
	}

	public BigDecimal getFrozenAmount() {
		return frozenAmount;
	}

	public void setLockedAmount(BigDecimal lockedAmount) {
		this.lockedAmount = lockedAmount;
	}

	public BigDecimal getLockedAmount() {
		return lockedAmount;
	}

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

	public BigDecimal getWithdrawalFee() {
		return withdrawalFee;
	}

	public void setWithdrawalFee(BigDecimal withdrawalFee) {
		this.withdrawalFee = withdrawalFee;
	}

	public Integer getWithdrawalEnabled() {
		return withdrawalEnabled;
	}

	public void setWithdrawalEnabled(Integer withdrawalEnabled) {
		this.withdrawalEnabled = withdrawalEnabled;
	}

	public boolean chechWithdrawalEnabled() {
		return WITHDRAWAL_ENABLED_ON.equals(withdrawalEnabled);
	}

	public MiningSymbol getWithdrawal() {
		return withdrawal;
	}

	public void setWithdrawal(MiningSymbol withdrawal) {
		this.withdrawal = withdrawal;
	}

	public String getChain() {
		return chain;
	}

	public void setChain(String chain) {
		this.chain = chain;
	}

}
