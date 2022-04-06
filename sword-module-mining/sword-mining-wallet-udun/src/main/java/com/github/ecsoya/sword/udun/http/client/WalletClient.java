package com.github.ecsoya.sword.udun.http.client;

import java.util.Map;

import com.github.ecsoya.sword.udun.http.ResponseMessage;

/**
 * The Interface WalletClient.
 *
 * @param <T> the generic type
 */
public interface WalletClient<T> {

	/**
	 * Post.
	 *
	 * @param url  the url
	 * @param list the list
	 * @return the response message
	 */
	ResponseMessage<T> post(String url, Map<String, String> list);

	/**
	 * Gets the.
	 *
	 * @param url the url
	 * @return the response message
	 */
	ResponseMessage<T> get(String url);

}
