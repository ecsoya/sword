package com.github.ecsoya.sword.message;

import java.util.Iterator;
import java.util.ServiceLoader;

public class MessageHelper {

	private static final ServiceLoader<IMessageService> loader = ServiceLoader.load(IMessageService.class);

	private static IMessageService getMessageService() {
		Iterator<IMessageService> it = loader.iterator();
		if (it.hasNext()) {
			return it.next();
		}
		return null;
	}

	public static int send(Long messageId, Integer type, Long... userIds) {
		IMessageService service = getMessageService();
		if (service != null) {
			return service.publishUserMessages(messageId, type, userIds);
		}
		return 0;
	}

	public static int send(String message, Long... userIds) {
		IMessageService service = getMessageService();
		if (service != null) {
			return service.publishUserMessages(message, userIds);
		}
		return 0;
	}
}
