package com.github.ecsoya.sword.udun.http.client;

import java.util.Map;

import com.github.ecsoya.sword.udun.http.ResponseMessage;

public interface WalletClient<T> {

	ResponseMessage<T> post(String url, Map<String, String> list);

	ResponseMessage<T> get(String url);

}
