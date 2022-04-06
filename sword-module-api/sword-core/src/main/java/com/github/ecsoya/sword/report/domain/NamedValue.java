package com.github.ecsoya.sword.report.domain;

/**
 * The Class NamedValue.
 */
public class NamedValue {

	/** The name. */
	private String name;

	/** The value. */
	private Object value;

	/**
	 * Instantiates a new named value.
	 *
	 * @param name  the name
	 * @param value the value
	 */
	public NamedValue(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(Object value) {
		this.value = value;
	}
}
