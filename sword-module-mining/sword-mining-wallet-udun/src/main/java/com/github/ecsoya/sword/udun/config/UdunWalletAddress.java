package com.github.ecsoya.sword.udun.config;

import java.util.Stack;

/**
 * The Class UdunWalletAddress.
 */
public class UdunWalletAddress {

	/** The fil. */
	private Stack<String> fil = new Stack<>();

	/** The usdt. */
	private Stack<String> usdt = new Stack<>();

	/**
	 * Adds the fil.
	 *
	 * @param addr the addr
	 */
	public void addFil(String addr) {
		if (addr == null || addr.equals("")) {
			return;
		}
		if (fil.contains(addr)) {
			return;
		}
		fil.add(addr);
	}

	/**
	 * Adds the usdt.
	 *
	 * @param addr the addr
	 */
	public void addUsdt(String addr) {
		if (addr == null || addr.equals("")) {
			return;
		}
		if (usdt.contains(addr)) {
			return;
		}
		usdt.add(addr);
	}

	/**
	 * Gets the fil.
	 *
	 * @return the fil
	 */
	public String getFil() {
		if (fil.isEmpty()) {
			return null;
		}
		return fil.pop();
	}

	/**
	 * Gets the usdt.
	 *
	 * @return the usdt
	 */
	public String getUsdt() {
		if (usdt.isEmpty()) {
			return null;
		}
		return usdt.pop();
	}

	/**
	 * Gets the address.
	 *
	 * @param symbol the symbol
	 * @return the address
	 */
	public String getAddress(String symbol) {
		if ("fil".equals(symbol)) {
			return getFil();
		} else if ("usdt".equals(symbol)) {
			return getUsdt();
		}
		return null;
	}
}
