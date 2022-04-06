/*
 * Copyright (c) 2011-2020, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.ecsoya.sword.common.utils.incrementer;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class Sequence.
 */
public class Sequence {

	/** The Constant AT. */
	private static final String AT = "@";

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(Sequence.class);

	/** The twepoch. */
	private final long twepoch = 1288834974657L;

	/** The worker id bits. */
	private final long workerIdBits = 5L;

	/** The datacenter id bits. */
	private final long datacenterIdBits = 5L;

	/** The max worker id. */
	private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

	/** The max datacenter id. */
	private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

	/** The sequence bits. */
	private final long sequenceBits = 12L;

	/** The worker id shift. */
	private final long workerIdShift = sequenceBits;

	/** The datacenter id shift. */
	private final long datacenterIdShift = sequenceBits + workerIdBits;

	/** The timestamp left shift. */
	private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

	/** The sequence mask. */
	private final long sequenceMask = -1L ^ (-1L << sequenceBits);

	/** The worker id. */
	private final long workerId;

	/** The datacenter id. */
	private final long datacenterId;

	/** The sequence. */
	private long sequence = 0L;

	/** The last timestamp. */
	private long lastTimestamp = -1L;

	/**
	 * Instantiates a new sequence.
	 */
	public Sequence() {
		this.datacenterId = getDatacenterId(maxDatacenterId);
		this.workerId = getMaxWorkerId(datacenterId, maxWorkerId);
	}

	/**
	 * Instantiates a new sequence.
	 *
	 * @param workerId     the worker id
	 * @param datacenterId 数据标识 ID 部分.
	 */
	public Sequence(long workerId, long datacenterId) {
		this.workerId = workerId;
		this.datacenterId = datacenterId;
	}

	/**
	 * Gets the max worker id.
	 *
	 * @param datacenterId the datacenter id
	 * @param maxWorkerId  the max worker id
	 * @return the max worker id
	 */
	protected static long getMaxWorkerId(long datacenterId, long maxWorkerId) {
		final StringBuilder mpid = new StringBuilder();
		mpid.append(datacenterId);
		final String name = ManagementFactory.getRuntimeMXBean().getName();
		if (org.apache.commons.lang3.StringUtils.isNotBlank(name)) {
			/*
			 * GET jvmPid
			 */
			mpid.append(name.split(AT)[0]);
		}
		/*
		 * MAC + PID 的 hashcode 获取16个低位
		 */
		return (mpid.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
	}

	/**
	 * Gets the datacenter id.
	 *
	 * @param maxDatacenterId the max datacenter id
	 * @return the datacenter id
	 */
	protected static long getDatacenterId(long maxDatacenterId) {
		long id = 0L;
		try {
			final InetAddress ip = InetAddress.getLocalHost();
			final NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			if (network == null) {
				id = 1L;
			} else {
				final byte[] mac = network.getHardwareAddress();
				if (null != mac) {
					id = ((0x000000FF & (long) mac[mac.length - 1])
							| (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
					id = id % (maxDatacenterId + 1);
				}
			}
		} catch (final Exception e) {
			logger.warn(" getDatacenterId: " + e.getMessage());
		}
		return id;
	}

	/**
	 * Next id.
	 *
	 * @return the long
	 */
	public synchronized long nextId() {
		long timestamp = timeGen();
		// 闰秒
		if (timestamp < lastTimestamp) {
			final long offset = lastTimestamp - timestamp;
			if (offset <= 5) {
				try {
					wait(offset << 1);
					timestamp = timeGen();
					if (timestamp < lastTimestamp) {
						throw new RuntimeException(String
								.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", offset));
					}
				} catch (final Exception e) {
					throw new RuntimeException(e);
				}
			} else {
				throw new RuntimeException(
						String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", offset));
			}
		}

		if (lastTimestamp == timestamp) {
			// 相同毫秒内，序列号自增
			sequence = (sequence + 1) & sequenceMask;
			if (sequence == 0) {
				// 同一毫秒的序列数已经达到最大
				timestamp = tilNextMillis(lastTimestamp);
			}
		} else {
			// 不同毫秒内，序列号置为 1 - 3 随机数
			sequence = ThreadLocalRandom.current().nextLong(1, 3);
		}

		lastTimestamp = timestamp;

		// 时间戳部分 | 数据中心部分 | 机器标识部分 | 序列号部分
		return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift)
				| (workerId << workerIdShift) | sequence;
	}

	/**
	 * Til next millis.
	 *
	 * @param lastTimestamp the last timestamp
	 * @return the long
	 */
	protected long tilNextMillis(long lastTimestamp) {
		long timestamp = timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = timeGen();
		}
		return timestamp;
	}

	/**
	 * Time gen.
	 *
	 * @return the long
	 */
	protected long timeGen() {
		return System.currentTimeMillis();
	}

}
