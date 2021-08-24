package com.soyatec.sword.udun.http.client;

import java.util.Map;

import com.soyatec.sword.udun.http.ResponseMessage;

public interface WalletClient<T> {

	ResponseMessage<T> post(String url, Map<String, String> list);

	ResponseMessage<T> get(String url);

}
