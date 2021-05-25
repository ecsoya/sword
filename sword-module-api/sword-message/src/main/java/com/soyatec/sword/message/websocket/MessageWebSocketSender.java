package com.soyatec.sword.message.websocket;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ServerEndpoint(value = "/ws/message/{userId}")
public class MessageWebSocketSender {

	private static final Logger log = LoggerFactory.getLogger(MessageWebSocketSender.class);

	// 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static AtomicInteger online = new AtomicInteger();
	private static ConcurrentHashMap<Long, Session> webSocketMap = new ConcurrentHashMap<>();

	@OnOpen
	public void onOpen(Session session, @PathParam(value = "userId") Long userId) {
		log.info("WebSocket.opened={}", userId);
		if (userId == null || session == null) {
			log.error("WebSocket.opened failed with invalid parameters");
			return;
		}
		if (webSocketMap.put(userId, session) != null) {
			online.incrementAndGet();
		}
	}

	@OnClose
	public void onClose(@PathParam(value = "userId") Long userId) {
		log.info("WebSocket.closed={}", userId);
		if (userId == null) {
			log.error("WebSocket.closed failed with invalid parameters");
			return;
		}
		if (webSocketMap.remove(userId) != null) {
			online.decrementAndGet();
		}
	}

	@OnError
	public void onError(Session session, Throwable e) {
		log.error("WebSocket.errored={}", e.getLocalizedMessage());
		e.printStackTrace();
	}

	public void dispatchMessage(String message) {
		log.info("WebSocket.message={}", message);
		log.debug("WebSocket.online={}", getOnlineCount());
		webSocketMap.forEach(10, (userId, session) -> {
			log.debug("WebSocket.sending: {}={}", userId, message);
			try {
				session.getBasicRemote().sendText(message);
			} catch (IOException e) {
				log.warn("WebSocket.sendMessageFailed to {}, error={}", userId, e.getLocalizedMessage());
			}
		});
	}

	public void asyncDispatchMessage(String message) {
		log.info("WebSocket.message={}", message);
		log.debug("WebSocket.online={}", getOnlineCount());
		webSocketMap.forEach(10, (userId, session) -> {
			log.debug("WebSocket.sending: {}={}", userId, message);
			try {
				session.getAsyncRemote().sendText(message);
			} catch (Exception e) {
				log.warn("WebSocket.sendMessageFailed to {}, error={}", userId, e.getLocalizedMessage());
			}
		});
	}

	public void dispatchMessage(String message, Long[] userIds) {
		if (userIds == null || userIds.length == 0) {
			return;
		}
		log.info("WebSocket.message={}", message);
		log.debug("WebSocket.online={}", getOnlineCount());
		List<Long> list = Arrays.asList(userIds);
		webSocketMap.entrySet().stream().filter(e -> list.contains(e.getKey())).parallel().forEach(e -> {
			Long userId = e.getKey();
			log.debug("WebSocket.sending: {}={}", userId, message);
			try {
				e.getValue().getBasicRemote().sendText(message);
			} catch (IOException e1) {
				log.warn("WebSocket.sendMessageFailed to {}, error={}", userId, e1.getLocalizedMessage());
			}
		});

	}

	public void asyncDispatchMessage(String message, Long[] userIds) {
		if (userIds == null || userIds.length == 0) {
			return;
		}
		log.info("WebSocket.message={}", message);
		log.debug("WebSocket.online={}", getOnlineCount());
		List<Long> list = Arrays.asList(userIds);
		webSocketMap.entrySet().stream().filter(e -> list.contains(e.getKey())).parallel().forEach(e -> {
			Long userId = e.getKey();
			log.debug("WebSocket.sending: {}={}", userId, message);
			try {
				e.getValue().getAsyncRemote().sendText(message);
			} catch (Exception e1) {
				log.warn("WebSocket.sendMessageFailed to {}, error={}", userId, e1.getLocalizedMessage());
			}
		});

	}

	public static synchronized int getOnlineCount() {
		return online.get();
	}
}
