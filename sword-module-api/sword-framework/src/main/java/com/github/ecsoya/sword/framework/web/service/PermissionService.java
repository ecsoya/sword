package com.github.ecsoya.sword.framework.web.service;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * The Class PermissionService.
 */
@Service("permission")
public class PermissionService {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(PermissionService.class);

	/** The Constant NOACCESS. */
	public static final String NOACCESS = "hidden";

	/** The Constant ROLE_DELIMETER. */
	private static final String ROLE_DELIMETER = ",";

	/** The Constant PERMISSION_DELIMETER. */
	private static final String PERMISSION_DELIMETER = ",";

	/**
	 * Checks for permi.
	 *
	 * @param permission the permission
	 * @return the string
	 */
	public String hasPermi(String permission) {
		return isPermitted(permission) ? org.apache.commons.lang3.StringUtils.EMPTY : NOACCESS;
	}

	/**
	 * Lacks permi.
	 *
	 * @param permission the permission
	 * @return the string
	 */
	public String lacksPermi(String permission) {
		return isLacksPermitted(permission) ? org.apache.commons.lang3.StringUtils.EMPTY : NOACCESS;
	}

	/**
	 * Checks for any permi.
	 *
	 * @param permissions the permissions
	 * @return the string
	 */
	public String hasAnyPermi(String permissions) {
		return hasAnyPermissions(permissions, PERMISSION_DELIMETER) ? org.apache.commons.lang3.StringUtils.EMPTY
				: NOACCESS;
	}

	/**
	 * Checks for role.
	 *
	 * @param role the role
	 * @return the string
	 */
	public String hasRole(String role) {
		return isRole(role) ? org.apache.commons.lang3.StringUtils.EMPTY : NOACCESS;
	}

	/**
	 * Lacks role.
	 *
	 * @param role the role
	 * @return the string
	 */
	public String lacksRole(String role) {
		return isLacksRole(role) ? org.apache.commons.lang3.StringUtils.EMPTY : NOACCESS;
	}

	/**
	 * Checks for any roles.
	 *
	 * @param roles the roles
	 * @return the string
	 */
	public String hasAnyRoles(String roles) {
		return isAnyRoles(roles, ROLE_DELIMETER) ? org.apache.commons.lang3.StringUtils.EMPTY : NOACCESS;
	}

	/**
	 * Checks if is user.
	 *
	 * @return true, if is user
	 */
	public boolean isUser() {
		final Subject subject = SecurityUtils.getSubject();
		return subject != null && subject.getPrincipal() != null;
	}

	/**
	 * Checks if is permitted.
	 *
	 * @param permission the permission
	 * @return true, if is permitted
	 */
	public boolean isPermitted(String permission) {
		return SecurityUtils.getSubject().isPermitted(permission);
	}

	/**
	 * Checks if is lacks permitted.
	 *
	 * @param permission the permission
	 * @return true, if is lacks permitted
	 */
	public boolean isLacksPermitted(String permission) {
		return isPermitted(permission) != true;
	}

	/**
	 * Checks for any permissions.
	 *
	 * @param permissions the permissions
	 * @return true, if successful
	 */
	public boolean hasAnyPermissions(String permissions) {
		return hasAnyPermissions(permissions, PERMISSION_DELIMETER);
	}

	/**
	 * Checks for any permissions.
	 *
	 * @param permissions the permissions
	 * @param delimeter   the delimeter
	 * @return true, if successful
	 */
	public boolean hasAnyPermissions(String permissions, String delimeter) {
		final Subject subject = SecurityUtils.getSubject();

		if (subject != null) {
			if (delimeter == null || delimeter.length() == 0) {
				delimeter = PERMISSION_DELIMETER;
			}

			for (final String permission : permissions.split(delimeter)) {
				if (permission != null && subject.isPermitted(permission.trim()) == true) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Checks if is role.
	 *
	 * @param role the role
	 * @return true, if is role
	 */
	public boolean isRole(String role) {
		return SecurityUtils.getSubject().hasRole(role);
	}

	/**
	 * Checks if is lacks role.
	 *
	 * @param role the role
	 * @return true, if is lacks role
	 */
	public boolean isLacksRole(String role) {
		return isRole(role) != true;
	}

	/**
	 * Checks if is any roles.
	 *
	 * @param roles the roles
	 * @return true, if is any roles
	 */
	public boolean isAnyRoles(String roles) {
		return isAnyRoles(roles, ROLE_DELIMETER);
	}

	/**
	 * Checks if is any roles.
	 *
	 * @param roles     the roles
	 * @param delimeter the delimeter
	 * @return true, if is any roles
	 */
	public boolean isAnyRoles(String roles, String delimeter) {
		final Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			if (delimeter == null || delimeter.length() == 0) {
				delimeter = ROLE_DELIMETER;
			}

			for (final String role : roles.split(delimeter)) {
				if (subject.hasRole(role.trim()) == true) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Gets the principal property.
	 *
	 * @param property the property
	 * @return the principal property
	 */
	public Object getPrincipalProperty(String property) {
		final Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			final Object principal = subject.getPrincipal();
			try {
				final BeanInfo bi = Introspector.getBeanInfo(principal.getClass());
				for (final PropertyDescriptor pd : bi.getPropertyDescriptors()) {
					if (pd.getName().equals(property) == true) {
						return pd.getReadMethod().invoke(principal, (Object[]) null);
					}
				}
			} catch (final Exception e) {
				log.error("Error reading property [{}] from principal of type [{}]", property,
						principal.getClass().getName());
			}
		}
		return null;
	}
}
