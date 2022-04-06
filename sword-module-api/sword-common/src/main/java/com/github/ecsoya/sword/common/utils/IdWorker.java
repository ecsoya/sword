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
package com.github.ecsoya.sword.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import com.github.ecsoya.sword.common.utils.incrementer.DefaultIdentifierGenerator;
import com.github.ecsoya.sword.common.utils.incrementer.IdentifierGenerator;

/**
 * The Class IdWorker.
 */
public class IdWorker {

	/** The Constant DASH. */
	private static final String DASH = "-";

	/** The Constant EMPTY. */
	private static final String EMPTY = "";

	/** The identifier generator. */
	private static IdentifierGenerator IDENTIFIER_GENERATOR = new DefaultIdentifierGenerator();

	/** The Constant MILLISECOND. */
	public static final DateTimeFormatter MILLISECOND = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public static long getId() {
		return getId(new Object());
	}

	/**
	 * Gets the id.
	 *
	 * @param entity the entity
	 * @return the id
	 */
	public static long getId(Object entity) {
		return IDENTIFIER_GENERATOR.nextId(entity).longValue();
	}

	/**
	 * Gets the id str.
	 *
	 * @return the id str
	 */
	public static String getIdStr() {
		return getIdStr(new Object());
	}

	/**
	 * Gets the id str.
	 *
	 * @param entity the entity
	 * @return the id str
	 */
	public static String getIdStr(Object entity) {
		return IDENTIFIER_GENERATOR.nextId(entity).toString();
	}

	/**
	 * Gets the millisecond.
	 *
	 * @return the millisecond
	 */
	public static String getMillisecond() {
		return LocalDateTime.now().format(MILLISECOND);
	}

	/**
	 * Gets the time id.
	 *
	 * @return the time id
	 */
	public static String getTimeId() {
		return getMillisecond() + getIdStr();
	}

	/**
	 * Inits the sequence.
	 *
	 * @param workerId     the worker id
	 * @param dataCenterId the data center id
	 */
	public static void initSequence(long workerId, long dataCenterId) {
		IDENTIFIER_GENERATOR = new DefaultIdentifierGenerator(workerId, dataCenterId);
	}

	/**
	 * Sets the identifier generator.
	 *
	 * @param identifierGenerator the new identifier generator
	 */
	public static void setIdentifierGenerator(IdentifierGenerator identifierGenerator) {
		IDENTIFIER_GENERATOR = identifierGenerator;
	}

	/**
	 * Gets the 32uuid.
	 *
	 * @return the 32uuid
	 */
	public static String get32UUID() {
		final ThreadLocalRandom random = ThreadLocalRandom.current();
		return new UUID(random.nextLong(), random.nextLong()).toString().replace(DASH, EMPTY);
	}
}
