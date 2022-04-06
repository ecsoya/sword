package com.github.ecsoya.sword.zbx.domain;

import java.util.HashMap;
import java.util.Map;

import com.github.ecsoya.sword.common.utils.StringUtils;

/**
 * The Class ZbxRequest.
 */
public class ZbxRequest {

	/** The symbol. */
	private final String symbol;

	/** The params. */
	private final Map<String, Object> params = new HashMap<>();

	/**
	 * Instantiates a new zbx request.
	 *
	 * @param symbol the symbol
	 */
	public ZbxRequest(String symbol) {
		this.symbol = symbol;
		addParam("symbol", symbol);
	}

	/**
	 * Gets the symbol.
	 *
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Adds the param.
	 *
	 * @param key   the key
	 * @param value the value
	 * @return the zbx request
	 */
	public ZbxRequest addParam(String key, Object value) {
		params.put(key, value);
		return this;
	}

	/**
	 * Adds the params.
	 *
	 * @param newParams the new params
	 * @return the zbx request
	 */
	public ZbxRequest addParams(Map<String, Object> newParams) {
		if (newParams != null) {
			params.putAll(newParams);
		}
		return this;
	}

	/**
	 * Gets the params.
	 *
	 * @return the params
	 */
	public Map<String, Object> getParams() {
		return params;
	}

	/**
	 * New request.
	 *
	 * @param symbol the symbol
	 * @return the zbx request
	 */
	public static ZbxRequest newRequest(String symbol) {
		return new ZbxRequest(symbol);
	}

	/**
	 * Checks if is valid.
	 *
	 * @return true, if is valid
	 */
	public boolean isValid() {
		return StringUtils.isNotEmpty(symbol);
	}

	/**
	 * Match.
	 *
	 * @param symbol the symbol
	 * @return true, if successful
	 */
	public boolean match(String symbol) {
		if (this.symbol == null) {
			return symbol == null;
		}
		return this.symbol.equals(symbol);
	}
}
