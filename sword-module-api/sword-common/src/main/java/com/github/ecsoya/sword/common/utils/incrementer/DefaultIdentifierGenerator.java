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

/**
 * The Class DefaultIdentifierGenerator.
 */
public class DefaultIdentifierGenerator implements IdentifierGenerator {

	/** The sequence. */
	private final Sequence sequence;

	/**
	 * Instantiates a new default identifier generator.
	 */
	public DefaultIdentifierGenerator() {
		this.sequence = new Sequence();
	}

	/**
	 * Instantiates a new default identifier generator.
	 *
	 * @param workerId     the worker id
	 * @param dataCenterId the data center id
	 */
	public DefaultIdentifierGenerator(long workerId, long dataCenterId) {
		this.sequence = new Sequence(workerId, dataCenterId);
	}

	/**
	 * Instantiates a new default identifier generator.
	 *
	 * @param sequence the sequence
	 */
	public DefaultIdentifierGenerator(Sequence sequence) {
		this.sequence = sequence;
	}

	/**
	 * Next id.
	 *
	 * @param entity the entity
	 * @return the long
	 */
	@Override
	public Long nextId(Object entity) {
		return sequence.nextId();
	}
}
