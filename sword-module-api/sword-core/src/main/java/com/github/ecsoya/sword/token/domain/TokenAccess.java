package com.github.ecsoya.sword.token.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class TokenAccess.
 */
public class TokenAccess extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The oper id. */
	private Long operId;

	/** The title. */
	@Excel(name = "模块标题")
	private String title;

	/** The method. */
	@Excel(name = "方法名称")
	private String method;

	/** The request method. */
	@Excel(name = "请求方式")
	private String requestMethod;

	/** The oper url. */
	@Excel(name = "请求URL")
	private String operUrl;

	/** The oper ip. */
	@Excel(name = "主机地址")
	private String operIp;

	/** The oper location. */
	@Excel(name = "操作地点")
	private String operLocation;

	/** The oper param. */
	@Excel(name = "请求参数")
	private String operParam;

	/** The json result. */
	@Excel(name = "返回参数")
	private String jsonResult;

	/** The status. */
	@Excel(name = "操作状态", readConverterExp = "0=正常,1=异常")
	private Integer status;

	/** The error msg. */
	@Excel(name = "错误消息")
	private String errorMsg;

	/** The oper time. */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd")
	private Date operTime;

	/**
	 * Sets the 日志主键.
	 *
	 * @param operId the new 日志主键
	 */
	public void setOperId(Long operId) {
		this.operId = operId;
	}

	/**
	 * Gets the 日志主键.
	 *
	 * @return the 日志主键
	 */
	public Long getOperId() {
		return operId;
	}

	/**
	 * Sets the 模块标题.
	 *
	 * @param title the new 模块标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the 模块标题.
	 *
	 * @return the 模块标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the 方法名称.
	 *
	 * @param method the new 方法名称
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * Gets the 方法名称.
	 *
	 * @return the 方法名称
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * Sets the 请求方式.
	 *
	 * @param requestMethod the new 请求方式
	 */
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	/**
	 * Gets the 请求方式.
	 *
	 * @return the 请求方式
	 */
	public String getRequestMethod() {
		return requestMethod;
	}

	/**
	 * Sets the 请求URL.
	 *
	 * @param operUrl the new 请求URL
	 */
	public void setOperUrl(String operUrl) {
		this.operUrl = operUrl;
	}

	/**
	 * Gets the 请求URL.
	 *
	 * @return the 请求URL
	 */
	public String getOperUrl() {
		return operUrl;
	}

	/**
	 * Sets the 主机地址.
	 *
	 * @param operIp the new 主机地址
	 */
	public void setOperIp(String operIp) {
		this.operIp = operIp;
	}

	/**
	 * Gets the 主机地址.
	 *
	 * @return the 主机地址
	 */
	public String getOperIp() {
		return operIp;
	}

	/**
	 * Sets the 操作地点.
	 *
	 * @param operLocation the new 操作地点
	 */
	public void setOperLocation(String operLocation) {
		this.operLocation = operLocation;
	}

	/**
	 * Gets the 操作地点.
	 *
	 * @return the 操作地点
	 */
	public String getOperLocation() {
		return operLocation;
	}

	/**
	 * Sets the 请求参数.
	 *
	 * @param operParam the new 请求参数
	 */
	public void setOperParam(String operParam) {
		this.operParam = operParam;
	}

	/**
	 * Gets the 请求参数.
	 *
	 * @return the 请求参数
	 */
	public String getOperParam() {
		return operParam;
	}

	/**
	 * Sets the 返回参数.
	 *
	 * @param jsonResult the new 返回参数
	 */
	public void setJsonResult(String jsonResult) {
		this.jsonResult = jsonResult;
	}

	/**
	 * Gets the 返回参数.
	 *
	 * @return the 返回参数
	 */
	public String getJsonResult() {
		return jsonResult;
	}

	/**
	 * Sets the 操作状态（0正常 1异常）.
	 *
	 * @param status the new 操作状态（0正常 1异常）
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * Gets the 操作状态（0正常 1异常）.
	 *
	 * @return the 操作状态（0正常 1异常）
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * Sets the 错误消息.
	 *
	 * @param errorMsg the new 错误消息
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * Gets the 错误消息.
	 *
	 * @return the 错误消息
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * Sets the 操作时间.
	 *
	 * @param operTime the new 操作时间
	 */
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	/**
	 * Gets the 操作时间.
	 *
	 * @return the 操作时间
	 */
	public Date getOperTime() {
		return operTime;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("operId", getOperId())
				.append("title", getTitle()).append("method", getMethod()).append("requestMethod", getRequestMethod())
				.append("operUrl", getOperUrl()).append("operIp", getOperIp()).append("operLocation", getOperLocation())
				.append("operParam", getOperParam()).append("jsonResult", getJsonResult()).append("status", getStatus())
				.append("errorMsg", getErrorMsg()).append("operTime", getOperTime()).toString();
	}
}
