package com.github.ecsoya.sword.common.core.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.ecsoya.sword.common.utils.DateUtils;

/**
 * The Class BaseEntity.
 */
@JsonIgnoreProperties(ignoreUnknown = true, value = { "searchValue", "createBy", "updateBy" })
public class BaseEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The search value. */
	private String searchValue;

	/** The create by. */
	private String createBy;

	/** The create time. */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/** The update by. */
	private String updateBy;

	/** The update time. */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	/** The remark. */
	private String remark;

	/** The params. */
	private Map<String, Object> params;

	/**
	 * Gets the 搜索值.
	 *
	 * @return the 搜索值
	 */
	public String getSearchValue() {
		return searchValue;
	}

	/**
	 * Sets the 搜索值.
	 *
	 * @param searchValue the new 搜索值
	 */
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	/**
	 * Gets the 创建者.
	 *
	 * @return the 创建者
	 */
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * Sets the 创建者.
	 *
	 * @param createBy the new 创建者
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * Gets the 创建时间.
	 *
	 * @return the 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * Sets the 创建时间.
	 *
	 * @param createTime the new 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * Gets the 更新者.
	 *
	 * @return the 更新者
	 */
	public String getUpdateBy() {
		return updateBy;
	}

	/**
	 * Sets the 更新者.
	 *
	 * @param updateBy the new 更新者
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	/**
	 * Gets the 更新时间.
	 *
	 * @return the 更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * Sets the 更新时间.
	 *
	 * @param updateTime the new 更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * Gets the 备注.
	 *
	 * @return the 备注
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * Sets the 备注.
	 *
	 * @param remark the new 备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * Gets the 请求参数.
	 *
	 * @return the 请求参数
	 */
	public Map<String, Object> getParams() {
		if (params == null) {
			params = new HashMap<>();
		}
		return params;
	}

	/**
	 * Sets the 请求参数.
	 *
	 * @param params the new 请求参数
	 */
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	/**
	 * Sets the time params.
	 *
	 * @param startTime the start time
	 * @param endTime   the end time
	 */
	public void setTimeParams(Date startTime, Date endTime) {
		startTime = DateUtils.getStartOf(startTime);
		endTime = DateUtils.getEndOf(endTime);
		getParams().put("startTime", startTime);
		getParams().put("endTime", endTime);
	}

	/**
	 * To map.
	 *
	 * @param fields the fields
	 * @return the map
	 */
	public Map<String, String> toMap(String... fields) {
		try {
			final Map<String, String> describe = BeanUtils.describe(this);
			if (fields != null && fields.length > 0) {
				final List<String> filters = Arrays.asList(fields);
				return describe.entrySet().stream().filter(map -> filters.contains(map.getKey()))
						.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
			}
			return describe;
		} catch (final Exception e) {
			return Collections.emptyMap();
		}
	}
}
