package com.github.ecsoya.sword.code.registry;

import java.util.function.Supplier;

import com.github.ecsoya.sword.code.handler.SendMobileCodeHandler;

/**
 * The Interface IMobileCodeHandlerRegistry.
 */
public interface IMobileCodeHandlerRegistry extends Supplier<SendMobileCodeHandler> {

}
