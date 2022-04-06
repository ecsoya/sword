package com.github.ecsoya.sword.framework.web.domain.server;

import com.github.ecsoya.sword.common.utils.Arith;

/**
 * The Class Mem.
 */
public class Mem {

	/** The total. */
	private double total;

	/** The used. */
	private double used;

	/** The free. */
	private double free;

	/**
	 * Gets the total.
	 *
	 * @return the total
	 */
	public double getTotal() {
		return Arith.div(total, (1024 * 1024 * 1024), 2);
	}

	/**
	 * Sets the total.
	 *
	 * @param total the new total
	 */
	public void setTotal(long total) {
		this.total = total;
	}

	/**
	 * Gets the used.
	 *
	 * @return the used
	 */
	public double getUsed() {
		return Arith.div(used, (1024 * 1024 * 1024), 2);
	}

	/**
	 * Sets the used.
	 *
	 * @param used the new used
	 */
	public void setUsed(long used) {
		this.used = used;
	}

	/**
	 * Gets the free.
	 *
	 * @return the free
	 */
	public double getFree() {
		return Arith.div(free, (1024 * 1024 * 1024), 2);
	}

	/**
	 * Sets the free.
	 *
	 * @param free the new free
	 */
	public void setFree(long free) {
		this.free = free;
	}

	/**
	 * Gets the usage.
	 *
	 * @return the usage
	 */
	public double getUsage() {
		return Arith.mul(Arith.div(used, total, 4), 100);
	}
}
