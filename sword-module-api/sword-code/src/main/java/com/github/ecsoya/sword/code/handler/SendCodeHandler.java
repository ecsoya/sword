package com.github.ecsoya.sword.code.handler;

public interface SendCodeHandler {
	/**
	 * 优先级越高，越先执行
	 */
	int getPriority();
}
