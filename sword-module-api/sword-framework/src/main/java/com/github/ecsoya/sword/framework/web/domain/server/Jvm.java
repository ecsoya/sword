package com.github.ecsoya.sword.framework.web.domain.server;

import java.lang.management.ManagementFactory;

import com.github.ecsoya.sword.common.utils.Arith;
import com.github.ecsoya.sword.common.utils.DateUtils;

/**
 * The Class Jvm.
 */
public class Jvm {

	/** The total. */
	private double total;

	/** The max. */
	private double max;

	/** The free. */
	private double free;

	/** The version. */
	private String version;

	/** The home. */
	private String home;

	/**
	 * Gets the total.
	 *
	 * @return the total
	 */
	public double getTotal() {
		return Arith.div(total, (1024 * 1024), 2);
	}

	/**
	 * Sets the total.
	 *
	 * @param total the new total
	 */
	public void setTotal(double total) {
		this.total = total;
	}

	/**
	 * Gets the max.
	 *
	 * @return the max
	 */
	public double getMax() {
		return Arith.div(max, (1024 * 1024), 2);
	}

	/**
	 * Sets the max.
	 *
	 * @param max the new max
	 */
	public void setMax(double max) {
		this.max = max;
	}

	/**
	 * Gets the free.
	 *
	 * @return the free
	 */
	public double getFree() {
		return Arith.div(free, (1024 * 1024), 2);
	}

	/**
	 * Sets the free.
	 *
	 * @param free the new free
	 */
	public void setFree(double free) {
		this.free = free;
	}

	/**
	 * Gets the used.
	 *
	 * @return the used
	 */
	public double getUsed() {
		return Arith.div(total - free, (1024 * 1024), 2);
	}

	/**
	 * Gets the usage.
	 *
	 * @return the usage
	 */
	public double getUsage() {
		return Arith.mul(Arith.div(total - free, total, 4), 100);
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return ManagementFactory.getRuntimeMXBean().getVmName();
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Gets the home.
	 *
	 * @return the home
	 */
	public String getHome() {
		return home;
	}

	/**
	 * Sets the home.
	 *
	 * @param home the new home
	 */
	public void setHome(String home) {
		this.home = home;
	}

	/**
	 * Gets the start time.
	 *
	 * @return the start time
	 */
	public String getStartTime() {
		return DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, DateUtils.getServerStartDate());
	}

	/**
	 * Gets the run time.
	 *
	 * @return the run time
	 */
	public String getRunTime() {
		return DateUtils.getDatePoor(DateUtils.getNowDate(), DateUtils.getServerStartDate());
	}
}
