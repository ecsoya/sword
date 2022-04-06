package com.github.ecsoya.sword.common.core.domain.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.annotation.Excel.ColumnType;
import com.github.ecsoya.sword.common.annotation.Excel.Type;
import com.github.ecsoya.sword.common.annotation.Excels;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class SysUser.
 */
public class SysUser extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The user id. */
	@Excel(name = "用户序号", cellType = ColumnType.NUMERIC, prompt = "用户编号")
	private Long userId;

	/** The dept id. */
	@Excel(name = "部门编号", type = Type.IMPORT)
	private Long deptId;

	/** The parent id. */
	private Long parentId;

	/** The role id. */
	private Long roleId;

	/** The login name. */
	@Excel(name = "登录名称")
	private String loginName;

	/** The user name. */
	@Excel(name = "用户名称")
	private String userName;

	/** The user type. */
	private String userType;

	/** The email. */
	@Excel(name = "用户邮箱")
	private String email;

	/** The phonenumber. */
	@Excel(name = "手机号码")
	private String phonenumber;

	/** The sex. */
	@Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
	private String sex;

	/** The avatar. */
	private String avatar;

	/** The password. */
	private String password;

	/** The salt. */
	private String salt;

	/** The status. */
	@Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
	private String status;

	/** The del flag. */
	private String delFlag;

	/** The login ip. */
	@Excel(name = "最后登录IP", type = Type.EXPORT)
	private String loginIp;

	/** The login date. */
	@Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
	private Date loginDate;

	/** The pwd update date. */
	private Date pwdUpdateDate;

	/** The dept. */
	@Excels({ @Excel(name = "部门名称", targetAttr = "deptName", type = Type.EXPORT),
			@Excel(name = "部门负责人", targetAttr = "leader", type = Type.EXPORT) })
	private SysDept dept;

	/** The roles. */
	private List<SysRole> roles;

	/** The role ids. */
	private Long[] roleIds;

	/**
	 * Instantiates a new sys user.
	 */
	public SysUser() {

	}

	/**
	 * Instantiates a new sys user.
	 *
	 * @param userId 用户ID.
	 */
	public SysUser(Long userId) {
		this.userId = userId;
	}

	/**
	 * Gets the 用户ID.
	 *
	 * @return the 用户ID
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * Sets the 用户ID.
	 *
	 * @param userId the new 用户ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Checks if is admin.
	 *
	 * @return true, if is admin
	 */
	public boolean isAdmin() {
		return isAdmin(this.userId);
	}

	/**
	 * Checks if is admin.
	 *
	 * @param userId the user id
	 * @return true, if is admin
	 */
	public static boolean isAdmin(Long userId) {
		return userId != null && 1L == userId;
	}

	/**
	 * Gets the 部门ID.
	 *
	 * @return the 部门ID
	 */
	public Long getDeptId() {
		return deptId;
	}

	/**
	 * Sets the 部门ID.
	 *
	 * @param deptId the new 部门ID
	 */
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	/**
	 * Gets the 部门父ID.
	 *
	 * @return the 部门父ID
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * Sets the 部门父ID.
	 *
	 * @param parentId the new 部门父ID
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * Gets the 角色ID.
	 *
	 * @return the 角色ID
	 */
	public Long getRoleId() {
		return roleId;
	}

	/**
	 * Sets the 角色ID.
	 *
	 * @param roleId the new 角色ID
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * Gets the 登录名称.
	 *
	 * @return the 登录名称
	 */
	@NotBlank(message = "登录账号不能为空")
	@Size(min = 0, max = 30, message = "登录账号长度不能超过30个字符")
	public String getLoginName() {
		return loginName;
	}

	/**
	 * Sets the 登录名称.
	 *
	 * @param loginName the new 登录名称
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * Gets the 用户名称.
	 *
	 * @return the 用户名称
	 */
	@Size(min = 0, max = 30, message = "用户昵称长度不能超过30个字符")
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the 用户名称.
	 *
	 * @param userName the new 用户名称
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the 用户类型.
	 *
	 * @return the 用户类型
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * Sets the 用户类型.
	 *
	 * @param userType the new 用户类型
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * Gets the 用户邮箱.
	 *
	 * @return the 用户邮箱
	 */
	@Email(message = "邮箱格式不正确")
	@Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the 用户邮箱.
	 *
	 * @param email the new 用户邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the 手机号码.
	 *
	 * @return the 手机号码
	 */
	@Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
	public String getPhonenumber() {
		return phonenumber;
	}

	/**
	 * Sets the 手机号码.
	 *
	 * @param phonenumber the new 手机号码
	 */
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	/**
	 * Gets the 用户性别.
	 *
	 * @return the 用户性别
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * Sets the 用户性别.
	 *
	 * @param sex the new 用户性别
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * Gets the 用户头像.
	 *
	 * @return the 用户头像
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * Sets the 用户头像.
	 *
	 * @param avatar the new 用户头像
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	/**
	 * Gets the 密码.
	 *
	 * @return the 密码
	 */
	@JsonIgnore
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the 密码.
	 *
	 * @param password the new 密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the 盐加密.
	 *
	 * @return the 盐加密
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * Sets the 盐加密.
	 *
	 * @param salt the new 盐加密
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * Gets the 帐号状态（0正常 1停用）.
	 *
	 * @return the 帐号状态（0正常 1停用）
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the 帐号状态（0正常 1停用）.
	 *
	 * @param status the new 帐号状态（0正常 1停用）
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the 删除标志（0代表存在 2代表删除）.
	 *
	 * @return the 删除标志（0代表存在 2代表删除）
	 */
	public String getDelFlag() {
		return delFlag;
	}

	/**
	 * Sets the 删除标志（0代表存在 2代表删除）.
	 *
	 * @param delFlag the new 删除标志（0代表存在 2代表删除）
	 */
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	/**
	 * Gets the 最后登录IP.
	 *
	 * @return the 最后登录IP
	 */
	public String getLoginIp() {
		return loginIp;
	}

	/**
	 * Sets the 最后登录IP.
	 *
	 * @param loginIp the new 最后登录IP
	 */
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	/**
	 * Gets the 最后登录时间.
	 *
	 * @return the 最后登录时间
	 */
	public Date getLoginDate() {
		return loginDate;
	}

	/**
	 * Sets the 最后登录时间.
	 *
	 * @param loginDate the new 最后登录时间
	 */
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	/**
	 * Gets the 密码最后更新时间.
	 *
	 * @return the 密码最后更新时间
	 */
	public Date getPwdUpdateDate() {
		return pwdUpdateDate;
	}

	/**
	 * Sets the 密码最后更新时间.
	 *
	 * @param pwdUpdateDate the new 密码最后更新时间
	 */
	public void setPwdUpdateDate(Date pwdUpdateDate) {
		this.pwdUpdateDate = pwdUpdateDate;
	}

	/**
	 * Gets the 部门对象.
	 *
	 * @return the 部门对象
	 */
	public SysDept getDept() {
		if (dept == null) {
			dept = new SysDept();
		}
		return dept;
	}

	/**
	 * Sets the 部门对象.
	 *
	 * @param dept the new 部门对象
	 */
	public void setDept(SysDept dept) {
		this.dept = dept;
	}

	/**
	 * Gets the roles.
	 *
	 * @return the roles
	 */
	public List<SysRole> getRoles() {
		return roles;
	}

	/**
	 * Sets the roles.
	 *
	 * @param roles the new roles
	 */
	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}

	/**
	 * Gets the 角色组.
	 *
	 * @return the 角色组
	 */
	public Long[] getRoleIds() {
		return roleIds;
	}

	/**
	 * Sets the 角色组.
	 *
	 * @param roleIds the new 角色组
	 */
	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("userId", getUserId())
				.append("deptId", getDeptId()).append("loginName", getLoginName()).append("userName", getUserName())
				.append("userType", getUserType()).append("email", getEmail()).append("phonenumber", getPhonenumber())
				.append("sex", getSex()).append("avatar", getAvatar()).append("password", getPassword())
				.append("salt", getSalt()).append("status", getStatus()).append("delFlag", getDelFlag())
				.append("loginIp", getLoginIp()).append("loginDate", getLoginDate()).append("createBy", getCreateBy())
				.append("createTime", getCreateTime()).append("updateBy", getUpdateBy())
				.append("updateTime", getUpdateTime()).append("remark", getRemark()).append("dept", getDept())
				.append("roles", getRoles()).toString();
	}
}
