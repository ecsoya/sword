package com.github.ecsoya.sword.token.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class TokenSecret.
 */
public class TokenSecret extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The access key. */
	@Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
	private String accessKey;

	/** The secret key. */
	@Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
	private String secretKey;

	/**
	 * Sets the $column.
	 *
	 * @param id the new $column
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the $column.
	 *
	 * @return the $column
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the $column.
	 *
	 * @param accessKey the new $column
	 */
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	/**
	 * Gets the $column.
	 *
	 * @return the $column
	 */
	public String getAccessKey() {
		return accessKey;
	}

	/**
	 * Sets the $column.
	 *
	 * @param secretKey the new $column
	 */
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	/**
	 * Gets the $column.
	 *
	 * @return the $column
	 */
	public String getSecretKey() {
		return secretKey;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
				.append("accessKey", getAccessKey()).append("secretKey", getSecretKey())
				.append("createTime", getCreateTime()).append("updateTime", getUpdateTime())
				.append("remark", getRemark()).toString();
	}
}
