package com.github.ecsoya.sword.service;

import java.time.Duration;

import com.github.ecsoya.sword.exceptions.LockableException;

/**
 * 锁Service接口
 *
 * @author AngryRED
 * @date 2020-09-21
 */
public interface ILockService {

	boolean releaseLock(String name);

	boolean isLocked(String name);

	boolean tryLock(String name, Duration duration) throws LockableException;

	boolean tryLock(String name) throws LockableException;
}
