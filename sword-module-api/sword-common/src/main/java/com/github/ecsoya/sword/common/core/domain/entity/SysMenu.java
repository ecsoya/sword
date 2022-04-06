package com.github.ecsoya.sword.common.core.domain.entity;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class SysMenu.
 */
public class SysMenu extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The menu id. */
	private Long menuId;

	/** The menu name. */
	private String menuName;

	/** The parent name. */
	private String parentName;

	/** The parent id. */
	private Long parentId;

	/** The order num. */
	private String orderNum;

	/** The url. */
	private String url;

	/** The target. */
	private String target;

	/** The menu type. */
	private String menuType;

	/** The visible. */
	private String visible;

	/** The is refresh. */
	private String isRefresh;

	/** The perms. */
	private String perms;

	/** The icon. */
	private String icon;

	/** The children. */
	private List<SysMenu> children = new ArrayList<SysMenu>();

	/** The buttons. */
	private String buttons;

	/**
	 * Gets the 菜单ID.
	 *
	 * @return the 菜单ID
	 */
	public Long getMenuId() {
		return menuId;
	}

	/**
	 * Sets the 菜单ID.
	 *
	 * @param menuId the new 菜单ID
	 */
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	/**
	 * Gets the 菜单名称.
	 *
	 * @return the 菜单名称
	 */
	@NotBlank(message = "菜单名称不能为空")
	@Size(min = 0, max = 50, message = "菜单名称长度不能超过50个字符")
	public String getMenuName() {
		return menuName;
	}

	/**
	 * Sets the 菜单名称.
	 *
	 * @param menuName the new 菜单名称
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	/**
	 * Gets the 父菜单名称.
	 *
	 * @return the 父菜单名称
	 */
	public String getParentName() {
		return parentName;
	}

	/**
	 * Sets the 父菜单名称.
	 *
	 * @param parentName the new 父菜单名称
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	/**
	 * Gets the 父菜单ID.
	 *
	 * @return the 父菜单ID
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * Sets the 父菜单ID.
	 *
	 * @param parentId the new 父菜单ID
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * Gets the 显示顺序.
	 *
	 * @return the 显示顺序
	 */
	@NotBlank(message = "显示顺序不能为空")
	public String getOrderNum() {
		return orderNum;
	}

	/**
	 * Sets the 显示顺序.
	 *
	 * @param orderNum the new 显示顺序
	 */
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	/**
	 * Gets the 菜单URL.
	 *
	 * @return the 菜单URL
	 */
	@Size(min = 0, max = 200, message = "请求地址不能超过200个字符")
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the 菜单URL.
	 *
	 * @param url the new 菜单URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the 打开方式（menuItem页签 menuBlank新窗口）.
	 *
	 * @return the 打开方式（menuItem页签 menuBlank新窗口）
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * Sets the 打开方式（menuItem页签 menuBlank新窗口）.
	 *
	 * @param target the new 打开方式（menuItem页签 menuBlank新窗口）
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * Gets the 类型（M目录 C菜单 F按钮）.
	 *
	 * @return the 类型（M目录 C菜单 F按钮）
	 */
	@NotBlank(message = "菜单类型不能为空")
	public String getMenuType() {
		return menuType;
	}

	/**
	 * Sets the 类型（M目录 C菜单 F按钮）.
	 *
	 * @param menuType the new 类型（M目录 C菜单 F按钮）
	 */
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	/**
	 * Gets the 菜单状态（0显示 1隐藏）.
	 *
	 * @return the 菜单状态（0显示 1隐藏）
	 */
	public String getVisible() {
		return visible;
	}

	/**
	 * Sets the 菜单状态（0显示 1隐藏）.
	 *
	 * @param visible the new 菜单状态（0显示 1隐藏）
	 */
	public void setVisible(String visible) {
		this.visible = visible;
	}

	/**
	 * Gets the 是否刷新（0刷新 1不刷新）.
	 *
	 * @return the 是否刷新（0刷新 1不刷新）
	 */
	public String getIsRefresh() {
		return isRefresh;
	}

	/**
	 * Sets the 是否刷新（0刷新 1不刷新）.
	 *
	 * @param isRefresh the new 是否刷新（0刷新 1不刷新）
	 */
	public void setIsRefresh(String isRefresh) {
		this.isRefresh = isRefresh;
	}

	/**
	 * Gets the 权限字符串.
	 *
	 * @return the 权限字符串
	 */
	@Size(min = 0, max = 100, message = "权限标识长度不能超过100个字符")
	public String getPerms() {
		return perms;
	}

	/**
	 * Sets the 权限字符串.
	 *
	 * @param perms the new 权限字符串
	 */
	public void setPerms(String perms) {
		this.perms = perms;
	}

	/**
	 * Gets the 菜单图标.
	 *
	 * @return the 菜单图标
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * Sets the 菜单图标.
	 *
	 * @param icon the new 菜单图标
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * Gets the 子菜单.
	 *
	 * @return the 子菜单
	 */
	public List<SysMenu> getChildren() {
		return children;
	}

	/**
	 * Sets the 子菜单.
	 *
	 * @param children the new 子菜单
	 */
	public void setChildren(List<SysMenu> children) {
		this.children = children;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("menuId", getMenuId())
				.append("menuName", getMenuName()).append("parentId", getParentId()).append("orderNum", getOrderNum())
				.append("url", getUrl()).append("target", getTarget()).append("menuType", getMenuType())
				.append("visible", getVisible()).append("perms", getPerms()).append("icon", getIcon())
				.append("createBy", getCreateBy()).append("createTime", getCreateTime())
				.append("updateBy", getUpdateBy()).append("updateTime", getUpdateTime()).append("remark", getRemark())
				.toString();
	}

	/**
	 * Gets the buttons.
	 *
	 * @return the buttons
	 */
	public String getButtons() {
		return buttons;
	}

	/**
	 * Sets the buttons.
	 *
	 * @param buttons the new buttons
	 */
	public void setButtons(String buttons) {
		this.buttons = buttons;
	}
}
