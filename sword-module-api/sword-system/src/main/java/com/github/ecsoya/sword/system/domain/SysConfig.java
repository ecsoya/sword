package com.github.ecsoya.sword.system.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.annotation.Excel.ColumnType;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class SysConfig.
 */
public class SysConfig extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The config id. */
	@Excel(name = "参数主键", cellType = ColumnType.NUMERIC)
	private Long configId;

	/** The config name. */
	@Excel(name = "参数名称")
	private String configName;

	/** The config key. */
	@Excel(name = "参数键名")
	private String configKey;

	/** The config value. */
	@Excel(name = "参数键值")
	private String configValue;

	/** The config type. */
	@Excel(name = "系统内置", readConverterExp = "Y=是,N=否")
	private String configType;

	/**
	 * Gets the 参数主键.
	 *
	 * @return the 参数主键
	 */
	public Long getConfigId() {
		return configId;
	}

	/**
	 * Sets the 参数主键.
	 *
	 * @param configId the new 参数主键
	 */
	public void setConfigId(Long configId) {
		this.configId = configId;
	}

	/**
	 * Gets the 参数名称.
	 *
	 * @return the 参数名称
	 */
	@NotBlank(message = "参数名称不能为空")
	@Size(min = 0, max = 100, message = "参数名称不能超过100个字符")
	public String getConfigName() {
		return configName;
	}

	/**
	 * Sets the 参数名称.
	 *
	 * @param configName the new 参数名称
	 */
	public void setConfigName(String configName) {
		this.configName = configName;
	}

	/**
	 * Gets the 参数键名.
	 *
	 * @return the 参数键名
	 */
	@NotBlank(message = "参数键名长度不能为空")
	@Size(min = 0, max = 100, message = "参数键名长度不能超过100个字符")
	public String getConfigKey() {
		return configKey;
	}

	/**
	 * Sets the 参数键名.
	 *
	 * @param configKey the new 参数键名
	 */
	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}

	/**
	 * Gets the 参数键值.
	 *
	 * @return the 参数键值
	 */
	@NotBlank(message = "参数键值不能为空")
	@Size(min = 0, max = 500, message = "参数键值长度不能超过500个字符")
	public String getConfigValue() {
		return configValue;
	}

	/**
	 * Sets the 参数键值.
	 *
	 * @param configValue the new 参数键值
	 */
	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	/**
	 * Gets the 系统内置（Y是 N否）.
	 *
	 * @return the 系统内置（Y是 N否）
	 */
	public String getConfigType() {
		return configType;
	}

	/**
	 * Sets the 系统内置（Y是 N否）.
	 *
	 * @param configType the new 系统内置（Y是 N否）
	 */
	public void setConfigType(String configType) {
		this.configType = configType;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("configId", getConfigId())
				.append("configName", getConfigName()).append("configKey", getConfigKey())
				.append("configValue", getConfigValue()).append("configType", getConfigType())
				.append("createBy", getCreateBy()).append("createTime", getCreateTime())
				.append("updateBy", getUpdateBy()).append("updateTime", getUpdateTime()).append("remark", getRemark())
				.toString();
	}
}
