package com.github.ecsoya.sword.framework.web.domain.server;

import com.github.ecsoya.sword.common.utils.Arith;

/**
 * The Class Cpu.
 */
public class Cpu {

	/** The cpu num. */
	private int cpuNum;

	/** The total. */
	private double total;

	/** The sys. */
	private double sys;

	/** The used. */
	private double used;

	/** The wait. */
	private double wait;

	/** The free. */
	private double free;

	/**
	 * Gets the cpu num.
	 *
	 * @return the cpu num
	 */
	public int getCpuNum() {
		return cpuNum;
	}

	/**
	 * Sets the cpu num.
	 *
	 * @param cpuNum the new cpu num
	 */
	public void setCpuNum(int cpuNum) {
		this.cpuNum = cpuNum;
	}

	/**
	 * Gets the total.
	 *
	 * @return the total
	 */
	public double getTotal() {
		return Arith.round(Arith.mul(total, 100), 2);
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
	 * Gets the sys.
	 *
	 * @return the sys
	 */
	public double getSys() {
		return Arith.round(Arith.mul(sys / total, 100), 2);
	}

	/**
	 * Sets the sys.
	 *
	 * @param sys the new sys
	 */
	public void setSys(double sys) {
		this.sys = sys;
	}

	/**
	 * Gets the used.
	 *
	 * @return the used
	 */
	public double getUsed() {
		return Arith.round(Arith.mul(used / total, 100), 2);
	}

	/**
	 * Sets the used.
	 *
	 * @param used the new used
	 */
	public void setUsed(double used) {
		this.used = used;
	}

	/**
	 * Gets the wait.
	 *
	 * @return the wait
	 */
	public double getWait() {
		return Arith.round(Arith.mul(wait / total, 100), 2);
	}

	/**
	 * Sets the wait.
	 *
	 * @param wait the new wait
	 */
	public void setWait(double wait) {
		this.wait = wait;
	}

	/**
	 * Gets the free.
	 *
	 * @return the free
	 */
	public double getFree() {
		return Arith.round(Arith.mul(free / total, 100), 2);
	}

	/**
	 * Sets the free.
	 *
	 * @param free the new free
	 */
	public void setFree(double free) {
		this.free = free;
	}
}
