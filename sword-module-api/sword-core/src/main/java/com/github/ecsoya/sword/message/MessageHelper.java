package com.github.ecsoya.sword.message;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * The Class MessageHelper.
 */
public class MessageHelper {

	/** The Constant loader. */
	private static final ServiceLoader<IMessageService> loader = ServiceLoader.load(IMessageService.class);

	/**
	 * Gets the message service.
	 *
	 * @return the message service
	 */
	private static IMessageService getMessageService() {
		Iterator<IMessageService> it = loader.iterator();
		if (it.hasNext()) {
			return it.next();
		}
		return null;
	}

	/**
	 * Send.
	 *
	 * @param messageId the message id
	 * @param type      the type
	 * @param userIds   the user ids
	 * @return the int
	 */
	public static int send(Long messageId, Integer type, Long... userIds) {
		IMessageService service = getMessageService();
		if (service != null) {
			return service.publishUserMessages(messageId, type, userIds);
		}
		return 0;
	}

	/**
	 * Send.
	 *
	 * @param message the message
	 * @param userIds the user ids
	 * @return the int
	 */
	public static int send(String message, Long... userIds) {
		IMessageService service = getMessageService();
		if (service != null) {
			return service.publishUserMessages(message, userIds);
		}
		return 0;
	}
}
