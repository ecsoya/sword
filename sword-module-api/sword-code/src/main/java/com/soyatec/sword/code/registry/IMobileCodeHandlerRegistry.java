package com.soyatec.sword.code.registry;

import java.util.function.Supplier;

import com.soyatec.sword.code.handler.SendMobileCodeHandler;

public interface IMobileCodeHandlerRegistry extends Supplier<SendMobileCodeHandler> {

}
