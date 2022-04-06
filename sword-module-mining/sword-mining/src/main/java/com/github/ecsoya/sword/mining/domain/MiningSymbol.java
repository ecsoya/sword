package com.github.ecsoya.sword.mining.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;
import com.github.ecsoya.sword.utils.MathUtils;

/**
 * The Class MiningSymbol.
 */
@JsonIgnoreProperties(ignoreUnknown = true, value = { "createTime", "updateTime", "remark", "params", "chain" })
public class MiningSymbol extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant ENABLED. */
	public static final Integer ENABLED = 0;

	/** The Constant DISABLED. */
	public static final Integer DISABLED = 1;

	/** The Constant TYPE_BITCOIN. */
	public static final Integer TYPE_BITCOIN = 0;

	/** The Constant TYPE_MONEY. */
	public static final Integer TYPE_MONEY = 1;

	/** The symbol. */
	private String symbol;

	/** The chain. */
	@Excel(name = "链码")
	private String chain;

	/** The type. */
	private Integer type;

	/** The withdrawal fee. */
	@Excel(name = "提币手续费")
	private BigDecimal withdrawalFee;

	/** The withdrawal fee symbol. */
	private String withdrawalFeeSymbol;

	/** The withdrawal minimum. */
	@Excel(name = "提币最小")
	private BigDecimal withdrawalMinimum;

	/** The withdrawal enabled. */
	@Excel(name = "开关")
	private Integer withdrawalEnabled;

	/** The withdrawal maximum. */
	@Excel(name = "提币最多")
	private BigDecimal withdrawalMaximum;

	/** The withdrawal daily. */
	@Excel(name = "提币日合计")
	private BigDecimal withdrawalDaily;

	/** The withdrawal totally. */
	@Excel(name = "提币总合计")
	private BigDecimal withdrawalTotally;

	/** The withdrawal manual audit. */
	// 提币人工审核
	private Integer withdrawalManualAudit;

	/** The withdrawal audit limation. */
	// 提币免审额度
	private BigDecimal withdrawalAuditLimation;

	/** The withdrawal wallet audit. */
	// 提币钱包二次审核
	private Integer withdrawalWalletAudit;

	/**
	 * Sets the 名称.
	 *
	 * @param symbol the new 名称
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Gets the 名称.
	 *
	 * @return the 名称
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Sets the 链码.
	 *
	 * @param chain the new 链码
	 */
	public void setChain(String chain) {
		this.chain = chain;
	}

	/**
	 * Gets the 链码.
	 *
	 * @return the 链码
	 */
	public String getChain() {
		return chain;
	}

	/**
	 * Sets the 提币手续费.
	 *
	 * @param withdrawalFee the new 提币手续费
	 */
	public void setWithdrawalFee(BigDecimal withdrawalFee) {
		this.withdrawalFee = withdrawalFee;
	}

	/**
	 * Gets the 提币手续费.
	 *
	 * @return the 提币手续费
	 */
	public BigDecimal getWithdrawalFee() {
		return withdrawalFee;
	}

	/**
	 * Sets the 提币最小.
	 *
	 * @param withdrawalMinimum the new 提币最小
	 */
	public void setWithdrawalMinimum(BigDecimal withdrawalMinimum) {
		this.withdrawalMinimum = withdrawalMinimum;
	}

	/**
	 * Gets the 提币最小.
	 *
	 * @return the 提币最小
	 */
	public BigDecimal getWithdrawalMinimum() {
		return withdrawalMinimum;
	}

	/**
	 * Sets the 开关.
	 *
	 * @param withdrawalEnabled the new 开关
	 */
	public void setWithdrawalEnabled(Integer withdrawalEnabled) {
		this.withdrawalEnabled = withdrawalEnabled;
	}

	/**
	 * Gets the 开关.
	 *
	 * @return the 开关
	 */
	public Integer getWithdrawalEnabled() {
		return withdrawalEnabled;
	}

	/**
	 * Sets the 提币最多.
	 *
	 * @param withdrawalMaximum the new 提币最多
	 */
	public void setWithdrawalMaximum(BigDecimal withdrawalMaximum) {
		this.withdrawalMaximum = withdrawalMaximum;
	}

	/**
	 * Gets the 提币最多.
	 *
	 * @return the 提币最多
	 */
	public BigDecimal getWithdrawalMaximum() {
		return withdrawalMaximum;
	}

	/**
	 * Sets the 提币日合计.
	 *
	 * @param withdrawalDaily the new 提币日合计
	 */
	public void setWithdrawalDaily(BigDecimal withdrawalDaily) {
		this.withdrawalDaily = withdrawalDaily;
	}

	/**
	 * Gets the 提币日合计.
	 *
	 * @return the 提币日合计
	 */
	public BigDecimal getWithdrawalDaily() {
		return withdrawalDaily;
	}

	/**
	 * Sets the 提币总合计.
	 *
	 * @param withdrawalTotally the new 提币总合计
	 */
	public void setWithdrawalTotally(BigDecimal withdrawalTotally) {
		this.withdrawalTotally = withdrawalTotally;
	}

	/**
	 * Gets the 提币总合计.
	 *
	 * @return the 提币总合计
	 */
	public BigDecimal getWithdrawalTotally() {
		return withdrawalTotally;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("symbol", getSymbol())
				.append("chain", getChain()).append("withdrawalFee", getWithdrawalFee())
				.append("createTime", getCreateTime()).append("withdrawalMinimum", getWithdrawalMinimum())
				.append("withdrawalEnabled", getWithdrawalEnabled()).append("updateTime", getUpdateTime())
				.append("withdrawalMaximum", getWithdrawalMaximum()).append("withdrawalDaily", getWithdrawalDaily())
				.append("remark", getRemark()).append("withdrawalTotally", getWithdrawalTotally()).toString();
	}

	/**
	 * Check withdrawal enabled.
	 *
	 * @return true, if successful
	 */
	public boolean checkWithdrawalEnabled() {
		return ENABLED.equals(withdrawalEnabled);
	}

	/**
	 * Gets the 提币手续费单位.
	 *
	 * @return the 提币手续费单位
	 */
	public String getWithdrawalFeeSymbol() {
		return withdrawalFeeSymbol;
	}

	/**
	 * Sets the 提币手续费单位.
	 *
	 * @param withdrawalFeeSymbol the new 提币手续费单位
	 */
	public void setWithdrawalFeeSymbol(String withdrawalFeeSymbol) {
		this.withdrawalFeeSymbol = withdrawalFeeSymbol;
	}

	/**
	 * Gets the withdrawal manual audit.
	 *
	 * @return the withdrawal manual audit
	 */
	public Integer getWithdrawalManualAudit() {
		return withdrawalManualAudit;
	}

	/**
	 * Sets the withdrawal manual audit.
	 *
	 * @param withdrawalManualAudit the new withdrawal manual audit
	 */
	public void setWithdrawalManualAudit(Integer withdrawalManualAudit) {
		this.withdrawalManualAudit = withdrawalManualAudit;
	}

	/**
	 * Need withdrawal manual audit.
	 *
	 * @param amount the amount
	 * @return true, if successful
	 */
	public boolean needWithdrawalManualAudit(BigDecimal amount) {
		if (!ENABLED.equals(withdrawalManualAudit)) {
			return false;
		}
		if (MathUtils.isValid(withdrawalAuditLimation) && MathUtils.lt(amount, withdrawalAuditLimation)) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the withdrawal audit limation.
	 *
	 * @return the withdrawal audit limation
	 */
	public BigDecimal getWithdrawalAuditLimation() {
		return withdrawalAuditLimation;
	}

	/**
	 * Sets the withdrawal audit limation.
	 *
	 * @param withdrawalAuditLimation the new withdrawal audit limation
	 */
	public void setWithdrawalAuditLimation(BigDecimal withdrawalAuditLimation) {
		this.withdrawalAuditLimation = withdrawalAuditLimation;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * Gets the withdrawal wallet audit.
	 *
	 * @return the withdrawal wallet audit
	 */
	public Integer getWithdrawalWalletAudit() {
		return withdrawalWalletAudit;
	}

	/**
	 * Sets the withdrawal wallet audit.
	 *
	 * @param withdrawalWalletAudit the new withdrawal wallet audit
	 */
	public void setWithdrawalWalletAudit(Integer withdrawalWalletAudit) {
		this.withdrawalWalletAudit = withdrawalWalletAudit;
	}
}
