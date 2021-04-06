package com.soyatec.sword.mining.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.soyatec.sword.common.annotation.Excel;
import com.soyatec.sword.common.core.domain.BaseEntity;

/**
 * 币种对象 t_sword_symbol
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-08
 */
@JsonIgnoreProperties(ignoreUnknown = true, value = { "createTime", "updateTime", "remark", "params", "chain" })
public class MiningSymbol extends BaseEntity {
	private static final long serialVersionUID = 1L;

	public static final Integer ENABLED = 0;
	public static final Integer DISABLED = 1;

	/** 名称 */
	private String symbol;

	/** 链码 */
	@Excel(name = "链码")
	private String chain;

	/** 提币手续费 */
	@Excel(name = "提币手续费")
	private BigDecimal withdrawalFee;

	/** 提币手续费单位 */
	private String withdrawalFeeSymbol;

	/** 提币最小 */
	@Excel(name = "提币最小")
	private BigDecimal withdrawalMinimum;

	/** 开关 */
	@Excel(name = "开关")
	private Integer withdrawalEnabled;

	/** 提币最多 */
	@Excel(name = "提币最多")
	private BigDecimal withdrawalMaximum;

	/** 提币日合计 */
	@Excel(name = "提币日合计")
	private BigDecimal withdrawalDaily;

	/** 提币总合计 */
	@Excel(name = "提币总合计")
	private BigDecimal withdrawalTotally;

	// 提币人工审核
	private Integer withdrawalManualAudit;

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setChain(String chain) {
		this.chain = chain;
	}

	public String getChain() {
		return chain;
	}

	public void setWithdrawalFee(BigDecimal withdrawalFee) {
		this.withdrawalFee = withdrawalFee;
	}

	public BigDecimal getWithdrawalFee() {
		return withdrawalFee;
	}

	public void setWithdrawalMinimum(BigDecimal withdrawalMinimum) {
		this.withdrawalMinimum = withdrawalMinimum;
	}

	public BigDecimal getWithdrawalMinimum() {
		return withdrawalMinimum;
	}

	public void setWithdrawalEnabled(Integer withdrawalEnabled) {
		this.withdrawalEnabled = withdrawalEnabled;
	}

	public Integer getWithdrawalEnabled() {
		return withdrawalEnabled;
	}

	public void setWithdrawalMaximum(BigDecimal withdrawalMaximum) {
		this.withdrawalMaximum = withdrawalMaximum;
	}

	public BigDecimal getWithdrawalMaximum() {
		return withdrawalMaximum;
	}

	public void setWithdrawalDaily(BigDecimal withdrawalDaily) {
		this.withdrawalDaily = withdrawalDaily;
	}

	public BigDecimal getWithdrawalDaily() {
		return withdrawalDaily;
	}

	public void setWithdrawalTotally(BigDecimal withdrawalTotally) {
		this.withdrawalTotally = withdrawalTotally;
	}

	public BigDecimal getWithdrawalTotally() {
		return withdrawalTotally;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("symbol", getSymbol())
				.append("chain", getChain()).append("withdrawalFee", getWithdrawalFee())
				.append("createTime", getCreateTime()).append("withdrawalMinimum", getWithdrawalMinimum())
				.append("withdrawalEnabled", getWithdrawalEnabled()).append("updateTime", getUpdateTime())
				.append("withdrawalMaximum", getWithdrawalMaximum()).append("withdrawalDaily", getWithdrawalDaily())
				.append("remark", getRemark()).append("withdrawalTotally", getWithdrawalTotally()).toString();
	}

	public boolean checkWithdrawalEnabled() {
		return ENABLED.equals(withdrawalEnabled);
	}

	public String getWithdrawalFeeSymbol() {
		return withdrawalFeeSymbol;
	}

	public void setWithdrawalFeeSymbol(String withdrawalFeeSymbol) {
		this.withdrawalFeeSymbol = withdrawalFeeSymbol;
	}

	public Integer getWithdrawalManualAudit() {
		return withdrawalManualAudit;
	}

	public void setWithdrawalManualAudit(Integer withdrawalManualAudit) {
		this.withdrawalManualAudit = withdrawalManualAudit;
	}

	public boolean needWithdrawalManualAudit() {
		return ENABLED.equals(withdrawalManualAudit);
	}
}
