package com.github.ecsoya.sword.common.echart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class EChart.
 */
public class EChart implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2169109362657437023L;

	/** The Constant TYPE_LINE. */
	public static final String TYPE_LINE = "line";

	/** The Constant TYPE_BAR. */
	public static final String TYPE_BAR = "bar";

	/** The Constant SUCCESS. */
	public static final int SUCCESS = 0;

	/** The Constant WARNING. */
	public static final int WARNING = 1;

	/** The Constant ERROR. */
	public static final int ERROR = 500;

	/** The code. */
	private int code;

	/** The msg. */
	private String msg;

	/** The type. */
	private String type; // line, bar

	/** The labels. */
	private String[] labels;

	/** The series. */
	private EChartSeries[] series;

	/** The params. */
	private Map<String, Object> params;

	/**
	 * Instantiates a new e chart.
	 *
	 * @param code 消息状态码.
	 * @param msg  消息内容.
	 * @param type the type
	 */
	public EChart(int code, String msg, String type) {
		this.code = code;
		this.msg = msg;
		this.type = type;
	}

	/**
	 * Gets the 消息状态码.
	 *
	 * @return the 消息状态码
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Sets the 消息状态码.
	 *
	 * @param code the new 消息状态码
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * Gets the 消息内容.
	 *
	 * @return the 消息内容
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * Sets the 消息内容.
	 *
	 * @param msg the new 消息内容
	 */
	public void setMsg(String msg) {
		this.msg = msg;
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
	 * Gets the labels.
	 *
	 * @return the labels
	 */
	public String[] getLabels() {
		return labels;
	}

	/**
	 * Sets the labels.
	 *
	 * @param labels the new labels
	 */
	public void setLabels(String[] labels) {
		this.labels = labels;
	}

	/**
	 * Gets the series.
	 *
	 * @return the series
	 */
	public EChartSeries[] getSeries() {
		return series;
	}

	/**
	 * Sets the series.
	 *
	 * @param series the new series
	 */
	public void setSeries(EChartSeries[] series) {
		this.series = series;
	}

	/**
	 * Adds the series.
	 *
	 * @param seriesData the series data
	 */
	public void addSeries(EChartSeries seriesData) {
		if (seriesData == null) {
			return;
		}
		if (this.series == null) {
			this.series = new EChartSeries[] { seriesData };
		} else {
			EChartSeries[] newSeries = new EChartSeries[series.length + 1];
			System.arraycopy(series, 0, newSeries, 0, series.length);
			newSeries[series.length] = seriesData;
			this.series = newSeries;
		}
	}

	/**
	 * Adds the values.
	 *
	 * @param name the name
	 * @param data the data
	 */
	public void addValues(String name, String[] data) {
		if (name == null || data == null) {
			return;
		}
		addSeries(new EChartSeries(name, type, data));
	}

	/**
	 * Gets the params.
	 *
	 * @return the params
	 */
	public Map<String, Object> getParams() {
		if (params == null) {
			params = new HashMap<>();
		}
		return params;
	}

	/**
	 * Sets the params.
	 *
	 * @param params the params
	 */
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	/**
	 * Error.
	 *
	 * @param msg the msg
	 * @return the e chart
	 */
	public static EChart error(String msg) {
		return new EChart(ERROR, msg, null);
	}

	/**
	 * Creates the bar.
	 *
	 * @return the e chart
	 */
	public static EChart createBar() {
		return new EChart(SUCCESS, null, TYPE_BAR);
	}

	/**
	 * Creates the line.
	 *
	 * @return the e chart
	 */
	public static EChart createLine() {
		return new EChart(SUCCESS, null, TYPE_LINE);
	}

}
