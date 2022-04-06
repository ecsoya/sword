package com.github.ecsoya.sword.common.core.domain.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.annotation.Excel.ColumnType;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class SysRole.
 */
public class SysRole extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The role id. */
	@Excel(name = "角色序号", cellType = ColumnType.NUMERIC)
	private Long roleId;

	/** The role name. */
	@Excel(name = "角色名称")
	private String roleName;

	/** The role key. */
	@Excel(name = "角色权限")
	private String roleKey;

	/** The role sort. */
	@Excel(name = "角色排序", cellType = ColumnType.NUMERIC)
	private String roleSort;

	/** The data scope. */
	@Excel(name = "数据范围", readConverterExp = "1=所有数据权限,2=自定义数据权限,3=本部门数据权限,4=本部门及以下数据权限")
	private String dataScope;

	/** The status. */
	@Excel(name = "角色状态", readConverterExp = "0=正常,1=停用")
	private String status;

	/** The del flag. */
	private String delFlag;

	/** The flag. */
	private boolean flag = false;

	/** The menu ids. */
	private Long[] menuIds;

	/** The dept ids. */
	private Long[] deptIds;

	/** The index page. */
	private String indexPage;

	/**
	 * Instantiates a new sys role.
	 */
	public SysRole() {

	}

	/**
	 * Instantiates a new sys role.
	 *
	 * @param roleId 角色ID.
	 */
	public SysRole(Long roleId) {
		this.roleId = roleId;
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
	 * Checks if is admin.
	 *
	 * @return true, if is admin
	 */
	public boolean isAdmin() {
		return isAdmin(this.roleId);
	}

	/**
	 * Checks if is admin.
	 *
	 * @param roleId the role id
	 * @return true, if is admin
	 */
	public static boolean isAdmin(Long roleId) {
		return roleId != null && 1L == roleId;
	}

	/**
	 * Gets the 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限）.
	 *
	 * @return the 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限）
	 */
	public String getDataScope() {
		return dataScope;
	}

	/**
	 * Sets the 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限）.
	 *
	 * @param dataScope the new 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限）
	 */
	public void setDataScope(String dataScope) {
		this.dataScope = dataScope;
	}

	/**
	 * Gets the 角色名称.
	 *
	 * @return the 角色名称
	 */
	@NotBlank(message = "角色名称不能为空")
	@Size(min = 0, max = 30, message = "角色名称长度不能超过30个字符")
	public String getRoleName() {
		return roleName;
	}

	/**
	 * Sets the 角色名称.
	 *
	 * @param roleName the new 角色名称
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * Gets the 角色权限.
	 *
	 * @return the 角色权限
	 */
	@NotBlank(message = "权限字符不能为空")
	@Size(min = 0, max = 100, message = "权限字符长度不能超过100个字符")
	public String getRoleKey() {
		return roleKey;
	}

	/**
	 * Sets the 角色权限.
	 *
	 * @param roleKey the new 角色权限
	 */
	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}

	/**
	 * Gets the 角色排序.
	 *
	 * @return the 角色排序
	 */
	@NotBlank(message = "显示顺序不能为空")
	public String getRoleSort() {
		return roleSort;
	}

	/**
	 * Sets the 角色排序.
	 *
	 * @param roleSort the new 角色排序
	 */
	public void setRoleSort(String roleSort) {
		this.roleSort = roleSort;
	}

	/**
	 * Gets the 角色状态（0正常 1停用）.
	 *
	 * @return the 角色状态（0正常 1停用）
	 */
	public String getStatus() {
		return status;
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
	 * Sets the 角色状态（0正常 1停用）.
	 *
	 * @param status the new 角色状态（0正常 1停用）
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Checks if is 用户是否存在此角色标识 默认不存在.
	 *
	 * @return the 用户是否存在此角色标识 默认不存在
	 */
	public boolean isFlag() {
		return flag;
	}

	/**
	 * Sets the 用户是否存在此角色标识 默认不存在.
	 *
	 * @param flag the new 用户是否存在此角色标识 默认不存在
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	/**
	 * Gets the 菜单组.
	 *
	 * @return the 菜单组
	 */
	public Long[] getMenuIds() {
		return menuIds;
	}

	/**
	 * Sets the 菜单组.
	 *
	 * @param menuIds the new 菜单组
	 */
	public void setMenuIds(Long[] menuIds) {
		this.menuIds = menuIds;
	}

	/**
	 * Gets the 部门组（数据权限）.
	 *
	 * @return the 部门组（数据权限）
	 */
	public Long[] getDeptIds() {
		return deptIds;
	}

	/**
	 * Sets the 部门组（数据权限）.
	 *
	 * @param deptIds the new 部门组（数据权限）
	 */
	public void setDeptIds(Long[] deptIds) {
		this.deptIds = deptIds;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("roleId", getRoleId())
				.append("roleName", getRoleName()).append("roleKey", getRoleKey()).append("roleSort", getRoleSort())
				.append("dataScope", getDataScope()).append("status", getStatus()).append("delFlag", getDelFlag())
				.append("createBy", getCreateBy()).append("createTime", getCreateTime())
				.append("updateBy", getUpdateBy()).append("updateTime", getUpdateTime()).append("remark", getRemark())
				.toString();
	}

	/**
	 * Gets the 权限定制首页.
	 *
	 * @return the 权限定制首页
	 */
	public String getIndexPage() {
		return indexPage;
	}

	/**
	 * Sets the 权限定制首页.
	 *
	 * @param indexPage the new 权限定制首页
	 */
	public void setIndexPage(String indexPage) {
		this.indexPage = indexPage;
	}

}
