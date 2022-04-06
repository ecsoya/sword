package com.github.ecsoya.sword.mining.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class MiningLevel.
 */
public class MiningLevel extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The name. */
	@Excel(name = "名称")
	private String name;

	/** The rate. */
	@Excel(name = "比例")
	private BigDecimal rate;

	/** The min amount. */
	@Excel(name = "最小值")
	private BigDecimal minAmount;

	/** The max amount. */
	@Excel(name = "最大值")
	private BigDecimal maxAmount;

	/** The referral amount. */
	@Excel(name = "直推")
	private BigDecimal referralAmount;

	/** The umbrella amount. */
	@Excel(name = "伞下")
	private BigDecimal umbrellaAmount;

	/** The child level count. */
	@Excel(name = "子级别人数")
	private Integer childLevelCount;

	/** The type. */
	@Excel(name = "类型")
	private Integer type;

	/** The child level id. */
	@Excel(name = "子级别ID")
	private Long childLevelId;

	/** The kind. */
	@Excel(name = "种类")
	private Integer kind;

	/** The status. */
	@Excel(name = "状态")
	private Integer status;

	/** The user count. */
	private Integer userCount;

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
	 * Sets the 名称.
	 *
	 * @param name the new 名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the 名称.
	 *
	 * @return the 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the 比例.
	 *
	 * @param rate the new 比例
	 */
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	/**
	 * Gets the 比例.
	 *
	 * @return the 比例
	 */
	public BigDecimal getRate() {
		return rate;
	}

	/**
	 * Sets the 最小值.
	 *
	 * @param minAmount the new 最小值
	 */
	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	/**
	 * Gets the 最小值.
	 *
	 * @return the 最小值
	 */
	public BigDecimal getMinAmount() {
		return minAmount;
	}

	/**
	 * Sets the 最大值.
	 *
	 * @param maxAmount the new 最大值
	 */
	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

	/**
	 * Gets the 最大值.
	 *
	 * @return the 最大值
	 */
	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	/**
	 * Sets the 直推.
	 *
	 * @param referralAmount the new 直推
	 */
	public void setReferralAmount(BigDecimal referralAmount) {
		this.referralAmount = referralAmount;
	}

	/**
	 * Gets the 直推.
	 *
	 * @return the 直推
	 */
	public BigDecimal getReferralAmount() {
		return referralAmount;
	}

	/**
	 * Sets the 伞下.
	 *
	 * @param umbrellaAmount the new 伞下
	 */
	public void setUmbrellaAmount(BigDecimal umbrellaAmount) {
		this.umbrellaAmount = umbrellaAmount;
	}

	/**
	 * Gets the 伞下.
	 *
	 * @return the 伞下
	 */
	public BigDecimal getUmbrellaAmount() {
		return umbrellaAmount;
	}

	/**
	 * Sets the 子级别人数.
	 *
	 * @param childLevelCount the new 子级别人数
	 */
	public void setChildLevelCount(Integer childLevelCount) {
		this.childLevelCount = childLevelCount;
	}

	/**
	 * Gets the 子级别人数.
	 *
	 * @return the 子级别人数
	 */
	public Integer getChildLevelCount() {
		return childLevelCount;
	}

	/**
	 * Sets the 类型.
	 *
	 * @param type the new 类型
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * Gets the 类型.
	 *
	 * @return the 类型
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * Sets the 子级别ID.
	 *
	 * @param childLevelId the new 子级别ID
	 */
	public void setChildLevelId(Long childLevelId) {
		this.childLevelId = childLevelId;
	}

	/**
	 * Gets the 子级别ID.
	 *
	 * @return the 子级别ID
	 */
	public Long getChildLevelId() {
		return childLevelId;
	}

	/**
	 * Sets the 种类.
	 *
	 * @param kind the new 种类
	 */
	public void setKind(Integer kind) {
		this.kind = kind;
	}

	/**
	 * Gets the 种类.
	 *
	 * @return the 种类
	 */
	public Integer getKind() {
		return kind;
	}

	/**
	 * Sets the 状态.
	 *
	 * @param status the new 状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * Gets the 状态.
	 *
	 * @return the 状态
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
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

	/**
	 * Gets the user count.
	 *
	 * @return the user count
	 */
	public Integer getUserCount() {
		return userCount;
	}

	/**
	 * Sets the user count.
	 *
	 * @param userCount the new user count
	 */
	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}
}
