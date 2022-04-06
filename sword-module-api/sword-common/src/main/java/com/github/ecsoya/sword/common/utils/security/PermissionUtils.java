package com.github.ecsoya.sword.common.utils.security;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ecsoya.sword.common.constant.PermissionConstants;
import com.github.ecsoya.sword.common.utils.MessageUtils;

/**
 * The Class PermissionUtils.
 */
public class PermissionUtils {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(PermissionUtils.class);

	/** The Constant VIEW_PERMISSION. */
	public static final String VIEW_PERMISSION = "no.view.permission";

	/** The Constant CREATE_PERMISSION. */
	public static final String CREATE_PERMISSION = "no.create.permission";

	/** The Constant UPDATE_PERMISSION. */
	public static final String UPDATE_PERMISSION = "no.update.permission";

	/** The Constant DELETE_PERMISSION. */
	public static final String DELETE_PERMISSION = "no.delete.permission";

	/** The Constant EXPORT_PERMISSION. */
	public static final String EXPORT_PERMISSION = "no.export.permission";

	/** The Constant PERMISSION. */
	public static final String PERMISSION = "no.permission";

	/**
	 * Gets the msg.
	 *
	 * @param permissionsStr the permissions str
	 * @return the msg
	 */
	public static String getMsg(String permissionsStr) {
		final String permission = StringUtils.substringBetween(permissionsStr, "[", "]");
		String msg = MessageUtils.message(PERMISSION, permission);
		if (StringUtils.endsWithIgnoreCase(permission, PermissionConstants.ADD_PERMISSION)) {
			msg = MessageUtils.message(CREATE_PERMISSION, permission);
		} else if (StringUtils.endsWithIgnoreCase(permission, PermissionConstants.EDIT_PERMISSION)) {
			msg = MessageUtils.message(UPDATE_PERMISSION, permission);
		} else if (StringUtils.endsWithIgnoreCase(permission, PermissionConstants.REMOVE_PERMISSION)) {
			msg = MessageUtils.message(DELETE_PERMISSION, permission);
		} else if (StringUtils.endsWithIgnoreCase(permission, PermissionConstants.EXPORT_PERMISSION)) {
			msg = MessageUtils.message(EXPORT_PERMISSION, permission);
		} else if (StringUtils.endsWithAny(permission,
				new String[] { PermissionConstants.VIEW_PERMISSION, PermissionConstants.LIST_PERMISSION })) {
			msg = MessageUtils.message(VIEW_PERMISSION, permission);
		}
		return msg;
	}

	/**
	 * Gets the principal property.
	 *
	 * @param property the property
	 * @return the principal property
	 */
	public static Object getPrincipalProperty(String property) {
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
