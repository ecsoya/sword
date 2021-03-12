package com.soyatec.sword.service.impl;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.exceptions.LockableException;
import com.soyatec.sword.service.ILockService;

@Service
public class LockServiceImpl implements ILockService {

	private static final Duration DEFAULT_LOCK_EXPIRES = Duration.ofMinutes(2); // 2分钟

	@Autowired
	private StringRedisTemplate redis;

	@Override
	public boolean releaseLock(String name) {
		if (StringUtils.isEmpty(name)) {
			return false;
		}
		redis.delete(name);
		return true;
	}

	@Override
	public boolean isLocked(String name) {
		if (StringUtils.isEmpty(name)) {
			return false;
		}
		return redis.opsForValue().get(name) != null;
	}

	@Override
	public boolean tryLock(String name, Duration duration) throws LockableException {
		if (StringUtils.isEmpty(name)) {
			throw new LockableException("Empty lock name");
		}
		String lock = redis.opsForValue().get(name);
		if (lock != null) {
			throw new LockableException("Lock is using");
		}
		if (duration == null) {
			duration = DEFAULT_LOCK_EXPIRES;
		}
		redis.opsForValue().set(name, "locked", duration);
		return true;
	}

	@Override
	public boolean tryLock(String name) throws LockableException {
		return tryLock(name, DEFAULT_LOCK_EXPIRES);
	}

}
