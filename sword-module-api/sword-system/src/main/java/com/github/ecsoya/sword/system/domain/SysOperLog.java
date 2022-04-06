package com.github.ecsoya.sword.system.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.annotation.Excel.ColumnType;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class SysOperLog.
 */
public class SysOperLog extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The oper id. */
	@Excel(name = "操作序号", cellType = ColumnType.NUMERIC)
	private Long operId;

	/** The title. */
	@Excel(name = "操作模块")
	private String title;

	/** The business type. */
	@Excel(name = "业务类型", readConverterExp = "0=其它,1=新增,2=修改,3=删除,4=授权,5=导出,6=导入,7=强退,8=生成代码,9=清空数据")
	private Integer businessType;

	/** The business types. */
	private Integer[] businessTypes;

	/** The method. */
	@Excel(name = "请求方法")
	private String method;

	/** The request method. */
	@Excel(name = "请求方式")
	private String requestMethod;

	/** The operator type. */
	@Excel(name = "操作类别", readConverterExp = "0=其它,1=后台用户,2=手机端用户")
	private Integer operatorType;

	/** The oper name. */
	@Excel(name = "操作人员")
	private String operName;

	/** The dept name. */
	@Excel(name = "部门名称")
	private String deptName;

	/** The oper url. */
	@Excel(name = "请求地址")
	private String operUrl;

	/** The oper ip. */
	@Excel(name = "操作地址")
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
	@Excel(name = "状态", readConverterExp = "0=正常,1=异常")
	private Integer status;

	/** The error msg. */
	@Excel(name = "错误消息")
	private String errorMsg;

	/** The oper time. */
	@Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
	private Date operTime;

	/**
	 * Gets the 日志主键.
	 *
	 * @return the 日志主键
	 */
	public Long getOperId() {
		return operId;
	}

	/**
	 * Sets the 日志主键.
	 *
	 * @param operId the new 日志主键
	 */
	public void setOperId(Long operId) {
		this.operId = operId;
	}

	/**
	 * Gets the 操作模块.
	 *
	 * @return the 操作模块
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the 操作模块.
	 *
	 * @param title the new 操作模块
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the 业务类型（0其它 1新增 2修改 3删除）.
	 *
	 * @return the 业务类型（0其它 1新增 2修改 3删除）
	 */
	public Integer getBusinessType() {
		return businessType;
	}

	/**
	 * Sets the 业务类型（0其它 1新增 2修改 3删除）.
	 *
	 * @param businessType the new 业务类型（0其它 1新增 2修改 3删除）
	 */
	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	/**
	 * Gets the 业务类型数组.
	 *
	 * @return the 业务类型数组
	 */
	public Integer[] getBusinessTypes() {
		return businessTypes;
	}

	/**
	 * Sets the 业务类型数组.
	 *
	 * @param businessTypes the new 业务类型数组
	 */
	public void setBusinessTypes(Integer[] businessTypes) {
		this.businessTypes = businessTypes;
	}

	/**
	 * Gets the 请求方法.
	 *
	 * @return the 请求方法
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * Sets the 请求方法.
	 *
	 * @param method the new 请求方法
	 */
	public void setMethod(String method) {
		this.method = method;
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
	 * Sets the 请求方式.
	 *
	 * @param requestMethod the new 请求方式
	 */
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	/**
	 * Gets the 操作类别（0其它 1后台用户 2手机端用户）.
	 *
	 * @return the 操作类别（0其它 1后台用户 2手机端用户）
	 */
	public Integer getOperatorType() {
		return operatorType;
	}

	/**
	 * Sets the 操作类别（0其它 1后台用户 2手机端用户）.
	 *
	 * @param operatorType the new 操作类别（0其它 1后台用户 2手机端用户）
	 */
	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}

	/**
	 * Gets the 操作人员.
	 *
	 * @return the 操作人员
	 */
	public String getOperName() {
		return operName;
	}

	/**
	 * Sets the 操作人员.
	 *
	 * @param operName the new 操作人员
	 */
	public void setOperName(String operName) {
		this.operName = operName;
	}

	/**
	 * Gets the 部门名称.
	 *
	 * @return the 部门名称
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * Sets the 部门名称.
	 *
	 * @param deptName the new 部门名称
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * Gets the 请求url.
	 *
	 * @return the 请求url
	 */
	public String getOperUrl() {
		return operUrl;
	}

	/**
	 * Sets the 请求url.
	 *
	 * @param operUrl the new 请求url
	 */
	public void setOperUrl(String operUrl) {
		this.operUrl = operUrl;
	}

	/**
	 * Gets the 操作地址.
	 *
	 * @return the 操作地址
	 */
	public String getOperIp() {
		return operIp;
	}

	/**
	 * Sets the 操作地址.
	 *
	 * @param operIp the new 操作地址
	 */
	public void setOperIp(String operIp) {
		this.operIp = operIp;
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
	 * Sets the 操作地点.
	 *
	 * @param operLocation the new 操作地点
	 */
	public void setOperLocation(String operLocation) {
		this.operLocation = operLocation;
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
	 * Sets the 请求参数.
	 *
	 * @param operParam the new 请求参数
	 */
	public void setOperParam(String operParam) {
		this.operParam = operParam;
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
	 * Sets the 返回参数.
	 *
	 * @param jsonResult the new 返回参数
	 */
	public void setJsonResult(String jsonResult) {
		this.jsonResult = jsonResult;
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
	 * Sets the 操作状态（0正常 1异常）.
	 *
	 * @param status the new 操作状态（0正常 1异常）
	 */
	public void setStatus(Integer status) {
		this.status = status;
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
	 * Sets the 错误消息.
	 *
	 * @param errorMsg the new 错误消息
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
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
	 * Sets the 操作时间.
	 *
	 * @param operTime the new 操作时间
	 */
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("operId", getOperId())
				.append("title", getTitle()).append("businessType", getBusinessType())
				.append("businessTypes", getBusinessTypes()).append("method", getMethod())
				.append("requestMethod", getRequestMethod()).append("operatorType", getOperatorType())
				.append("operName", getOperName()).append("deptName", getDeptName()).append("operUrl", getOperUrl())
				.append("operIp", getOperIp()).append("operLocation", getOperLocation())
				.append("operParam", getOperParam()).append("status", getStatus()).append("errorMsg", getErrorMsg())
				.append("operTime", getOperTime()).toString();
	}
}
