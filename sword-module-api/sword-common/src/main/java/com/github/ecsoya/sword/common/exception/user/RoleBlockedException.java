package com.github.ecsoya.sword.common.exception.user;

/**
 * The Class RoleBlockedException.
 */
public class RoleBlockedException extends UserException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new role blocked exception.
	 */
	public RoleBlockedException() {
		super("role.blocked", null);
	}
}
