package com.github.ecsoya.sword.common.core.domain;

import java.io.Serializable;

/**
 * The Class Ztree.
 */
public class Ztree implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The p id. */
	private Long pId;

	/** The name. */
	private String name;

	/** The title. */
	private String title;

	/** The checked. */
	private boolean checked = false;

	/** The open. */
	private boolean open = false;

	/** The nocheck. */
	private boolean nocheck = false;

	/**
	 * Gets the 节点ID.
	 *
	 * @return the 节点ID
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the 节点ID.
	 *
	 * @param id the new 节点ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the p id.
	 *
	 * @return the p id
	 */
	public Long getpId() {
		return pId;
	}

	/**
	 * Sets the p id.
	 *
	 * @param pId the new p id
	 */
	public void setpId(Long pId) {
		this.pId = pId;
	}

	/**
	 * Gets the 节点名称.
	 *
	 * @return the 节点名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the 节点名称.
	 *
	 * @param name the new 节点名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the 节点标题.
	 *
	 * @return the 节点标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the 节点标题.
	 *
	 * @param title the new 节点标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Checks if is 是否勾选.
	 *
	 * @return the 是否勾选
	 */
	public boolean isChecked() {
		return checked;
	}

	/**
	 * Sets the 是否勾选.
	 *
	 * @param checked the new 是否勾选
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	/**
	 * Checks if is 是否展开.
	 *
	 * @return the 是否展开
	 */
	public boolean isOpen() {
		return open;
	}

	/**
	 * Sets the 是否展开.
	 *
	 * @param open the new 是否展开
	 */
	public void setOpen(boolean open) {
		this.open = open;
	}

	/**
	 * Checks if is 是否能勾选.
	 *
	 * @return the 是否能勾选
	 */
	public boolean isNocheck() {
		return nocheck;
	}

	/**
	 * Sets the 是否能勾选.
	 *
	 * @param nocheck the new 是否能勾选
	 */
	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}
}
