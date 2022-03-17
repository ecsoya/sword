package com.github.ecsoya.sword.udun.config;

import java.util.Stack;

public class UdunWalletAddress {

	private Stack<String> fil = new Stack<>();
	private Stack<String> usdt = new Stack<>();

	public void addFil(String addr) {
		if (addr == null || addr.equals("")) {
			return;
		}
		if (fil.contains(addr)) {
			return;
		}
		fil.add(addr);
	}

	public void addUsdt(String addr) {
		if (addr == null || addr.equals("")) {
			return;
		}
		if (usdt.contains(addr)) {
			return;
		}
		usdt.add(addr);
	}

	public String getFil() {
		if (fil.isEmpty()) {
			return null;
		}
		return fil.pop();
	}

	public String getUsdt() {
		if (usdt.isEmpty()) {
			return null;
		}
		return usdt.pop();
	}

	public String getAddress(String symbol) {
		if ("fil".equals(symbol)) {
			return getFil();
		} else if ("usdt".equals(symbol)) {
			return getUsdt();
		}
		return null;
	}
}
