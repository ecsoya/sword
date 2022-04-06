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

import com.github.ecsoya.sword.common.utils.IdWorker;

/**
 * The Interface IdentifierGenerator.
 */
public interface IdentifierGenerator {

	/**
	 * Next id.
	 *
	 * @param entity the entity
	 * @return the number
	 */
	Number nextId(Object entity);

	/**
	 * Next UUID.
	 *
	 * @param entity the entity
	 * @return the string
	 */
	default String nextUUID(Object entity) {
		return IdWorker.get32UUID();
	}
}
