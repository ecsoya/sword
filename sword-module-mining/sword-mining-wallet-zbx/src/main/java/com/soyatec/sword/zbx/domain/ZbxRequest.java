package com.soyatec.sword.zbx.domain;

import java.util.HashMap;
import java.util.Map;

import com.soyatec.sword.common.utils.StringUtils;

public class ZbxRequest {

	private String symbol;

	private Map<String, Object> params = new HashMap<>();

	public ZbxRequest(String symbol) {
		this.symbol = symbol;
		addParam("symbol", symbol);
	}

	public String getSymbol() {
		return symbol;
	}

	public ZbxRequest addParam(String key, Object value) {
		params.put(key, value);
		return this;
	}

	public ZbxRequest addParams(Map<String, Object> newParams) {
		if (newParams != null) {
			params.putAll(newParams);
		}
		return this;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public static ZbxRequest newRequest(String symbol) {
		return new ZbxRequest(symbol);
	}

	public boolean isValid() {
		return StringUtils.isNotEmpty(symbol);
	}

	public boolean match(String symbol) {
		if (this.symbol == null) {
			return symbol == null;
		}
		return this.symbol.equals(symbol);
	}
}
