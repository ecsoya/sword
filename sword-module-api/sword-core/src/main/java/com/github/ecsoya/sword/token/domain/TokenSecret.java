package com.github.ecsoya.sword.token.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * 开放接口API对象 t_token_secret
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-07
 */
public class TokenSecret extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** $column.columnComment */
	private Long id;

	/** $column.columnComment */
	@Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
	private String accessKey;

	/** $column.columnComment */
	@Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
	private String secretKey;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
				.append("accessKey", getAccessKey()).append("secretKey", getSecretKey())
				.append("createTime", getCreateTime()).append("updateTime", getUpdateTime())
				.append("remark", getRemark()).toString();
	}
}
