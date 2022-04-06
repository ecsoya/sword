package com.github.ecsoya.sword.common.echart;

/**
 * The Class EChartSeries.
 */
public class EChartSeries {

	/** The name. */
	private String name;

	/** The type. */
	private String type;

	/** The data. */
	private String[] data;

	/** The label. */
	private boolean label = true;

	/** The smooth. */
	private boolean smooth = true;

	/**
	 * Instantiates a new e chart series.
	 *
	 * @param name the name
	 * @param type the type
	 * @param data the data
	 */
	public EChartSeries(String name, String type, String[] data) {
		this.name = name;
		this.type = type;
		this.data = data;
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
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public String[] getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(String[] data) {
		this.data = data;
	}

	/**
	 * Checks if is label.
	 *
	 * @return true, if is label
	 */
	public boolean isLabel() {
		return label;
	}

	/**
	 * Sets the label.
	 *
	 * @param label the new label
	 */
	public void setLabel(boolean label) {
		this.label = label;
	}

	/**
	 * Checks if is smooth.
	 *
	 * @return true, if is smooth
	 */
	public boolean isSmooth() {
		return smooth;
	}

	/**
	 * Sets the smooth.
	 *
	 * @param smooth the new smooth
	 */
	public void setSmooth(boolean smooth) {
		this.smooth = smooth;
	}

}
