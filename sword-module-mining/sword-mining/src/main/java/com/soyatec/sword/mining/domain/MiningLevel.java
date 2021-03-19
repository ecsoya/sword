package com.soyatec.sword.mining.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.soyatec.sword.common.annotation.Excel;
import com.soyatec.sword.common.core.domain.BaseEntity;

/**
 * Mining级别对象 t_mining_level
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-03-15
 */
public class MiningLevel extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** ID */
	private Long id;

	/** 名称 */
	@Excel(name = "名称")
	private String name;

	/** 比例 */
	@Excel(name = "比例")
	private BigDecimal rate;

	/** 最小值 */
	@Excel(name = "最小值")
	private BigDecimal minAmount;

	/** 最大值 */
	@Excel(name = "最大值")
	private BigDecimal maxAmount;

	/** 直推 */
	@Excel(name = "直推")
	private BigDecimal referralAmount;

	/** 伞下 */
	@Excel(name = "伞下")
	private BigDecimal umbrellaAmount;

	/** 子级别人数 */
	@Excel(name = "子级别人数")
	private Integer childLevelCount;

	/** 类型 */
	@Excel(name = "类型")
	private Integer type;

	/** 子级别ID */
	@Excel(name = "子级别ID")
	private Long childLevelId;

	/** 种类 */
	@Excel(name = "种类")
	private Integer kind;

	/** 状态 */
	@Excel(name = "状态")
	private Integer status;

	private Integer userCount;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	public BigDecimal getMinAmount() {
		return minAmount;
	}

	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	public void setReferralAmount(BigDecimal referralAmount) {
		this.referralAmount = referralAmount;
	}

	public BigDecimal getReferralAmount() {
		return referralAmount;
	}

	public void setUmbrellaAmount(BigDecimal umbrellaAmount) {
		this.umbrellaAmount = umbrellaAmount;
	}

	public BigDecimal getUmbrellaAmount() {
		return umbrellaAmount;
	}

	public void setChildLevelCount(Integer childLevelCount) {
		this.childLevelCount = childLevelCount;
	}

	public Integer getChildLevelCount() {
		return childLevelCount;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}

	public void setChildLevelId(Long childLevelId) {
		this.childLevelId = childLevelId;
	}

	public Long getChildLevelId() {
		return childLevelId;
	}

	public void setKind(Integer kind) {
		this.kind = kind;
	}

	public Integer getKind() {
		return kind;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId()).append("name", getName())
				.append("rate", getRate()).append("minAmount", getMinAmount()).append("maxAmount", getMaxAmount())
				.append("referralAmount", getReferralAmount()).append("umbrellaAmount", getUmbrellaAmount())
				.append("childLevelCount", getChildLevelCount()).append("type", getType())
				.append("childLevelId", getChildLevelId()).append("kind", getKind()).append("status", getStatus())
				.append("createTime", getCreateTime()).append("updateTime", getUpdateTime())
				.append("remark", getRemark()).toString();
	}

	public Integer getUserCount() {
		return userCount;
	}

	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}
}
