package com.soyatec.sword.code.registry;

import java.util.function.Supplier;

import com.soyatec.sword.code.handler.SendMailCodeHandler;

public interface IMailCodeHandlerRegistry extends Supplier<SendMailCodeHandler> {

}
