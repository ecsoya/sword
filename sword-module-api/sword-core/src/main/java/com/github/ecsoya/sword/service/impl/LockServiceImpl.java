package com.github.ecsoya.sword.service.impl;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.exceptions.LockableException;
import com.github.ecsoya.sword.service.ILockService;

/**
 * The Class LockServiceImpl.
 */
@Service
public class LockServiceImpl implements ILockService {

	/** The Constant DEFAULT_LOCK_EXPIRES. */
	private static final Duration DEFAULT_LOCK_EXPIRES = Duration.ofMinutes(2); // 2分钟

	/** The redis. */
	@Autowired
	private StringRedisTemplate redis;

	/**
	 * Release lock.
	 *
	 * @param name the name
	 * @return true, if successful
	 */
	@Override
	public boolean releaseLock(String name) {
		if (StringUtils.isEmpty(name)) {
			return false;
		}
		redis.delete(name);
		return true;
	}

	/**
	 * Checks if is locked.
	 *
	 * @param name the name
	 * @return true, if is locked
	 */
	@Override
	public boolean isLocked(String name) {
		if (StringUtils.isEmpty(name)) {
			return false;
		}
		return redis.opsForValue().get(name) != null;
	}

	/**
	 * Try lock.
	 *
	 * @param name     the name
	 * @param duration the duration
	 * @return true, if successful
	 * @throws LockableException the lockable exception
	 */
	@Override
	public boolean tryLock(String name, Duration duration) throws LockableException {
		if (StringUtils.isEmpty(name)) {
			throw new LockableException("Empty lock name");
		}
		final String lock = redis.opsForValue().get(name);
		if (lock != null) {
			throw new LockableException("Lock is using");
		}
		if (duration == null) {
			duration = DEFAULT_LOCK_EXPIRES;
		}
		redis.opsForValue().set(name, "locked", duration);
		return true;
	}

	/**
	 * Try lock.
	 *
	 * @param name the name
	 * @return true, if successful
	 * @throws LockableException the lockable exception
	 */
	@Override
	public boolean tryLock(String name) throws LockableException {
		return tryLock(name, DEFAULT_LOCK_EXPIRES);
	}

}
