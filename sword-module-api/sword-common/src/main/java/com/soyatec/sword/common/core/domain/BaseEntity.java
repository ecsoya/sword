package com.soyatec.sword.common.core.domain;

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
import com.soyatec.sword.common.utils.DateUtils;

/**
 * Entity基类
 *
 * @author Jin Liu (angryred@qq.com)
 */
@JsonIgnoreProperties(ignoreUnknown = true, value = { "searchValue", "createBy", "updateBy" })
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 搜索值 */
	private String searchValue;

	/** 创建者 */
	private String createBy;

	/** 创建时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/** 更新者 */
	private String updateBy;

	/** 更新时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	/** 备注 */
	private String remark;

	/** 请求参数 */
	private Map<String, Object> params;

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Map<String, Object> getParams() {
		if (params == null) {
			params = new HashMap<>();
		}
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public void setTimeParams(Date startTime, Date endTime) {
		startTime = DateUtils.getStartOf(startTime);
		endTime = DateUtils.getEndOf(endTime);
		getParams().put("startTime", startTime);
		getParams().put("endTime", endTime);
	}

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
