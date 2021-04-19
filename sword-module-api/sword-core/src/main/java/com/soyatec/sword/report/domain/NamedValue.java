package com.soyatec.sword.report.domain;

public class NamedValue {
	private String name;

	private Object value;

	public NamedValue(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
