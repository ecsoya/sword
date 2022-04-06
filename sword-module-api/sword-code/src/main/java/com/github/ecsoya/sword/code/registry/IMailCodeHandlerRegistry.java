package com.github.ecsoya.sword.code.registry;

import java.util.function.Supplier;

import com.github.ecsoya.sword.code.handler.SendMailCodeHandler;

/**
 * The Interface IMailCodeHandlerRegistry.
 */
public interface IMailCodeHandlerRegistry extends Supplier<SendMailCodeHandler> {

}
