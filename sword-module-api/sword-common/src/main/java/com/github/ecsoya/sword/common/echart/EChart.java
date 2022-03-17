package com.github.ecsoya.sword.common.echart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class EChart implements Serializable {

	private static final long serialVersionUID = 2169109362657437023L;

	public static final String TYPE_LINE = "line";
	public static final String TYPE_BAR = "bar";

	/** 成功 */
	public static final int SUCCESS = 0;
	public static final int WARNING = 1;
	public static final int ERROR = 500;

	/** 消息状态码 */
	private int code;

	/** 消息内容 */
	private String msg;

	private String type; // line, bar

	private String[] labels;

	private EChartSeries[] series;

	private Map<String, Object> params;

	public EChart(int code, String msg, String type) {
		this.code = code;
		this.msg = msg;
		this.type = type;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String[] getLabels() {
		return labels;
	}

	public void setLabels(String[] labels) {
		this.labels = labels;
	}

	public EChartSeries[] getSeries() {
		return series;
	}

	public void setSeries(EChartSeries[] series) {
		this.series = series;
	}

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

	public void addValues(String name, String[] data) {
		if (name == null || data == null) {
			return;
		}
		addSeries(new EChartSeries(name, type, data));
	}

	public Map<String, Object> getParams() {
		if (params == null) {
			params = new HashMap<>();
		}
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public static EChart error(String msg) {
		return new EChart(ERROR, msg, null);
	}

	public static EChart createBar() {
		return new EChart(SUCCESS, null, TYPE_BAR);
	}

	public static EChart createLine() {
		return new EChart(SUCCESS, null, TYPE_LINE);
	}

}
