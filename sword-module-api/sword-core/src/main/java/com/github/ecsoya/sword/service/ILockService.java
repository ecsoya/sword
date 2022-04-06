package com.github.ecsoya.sword.service;

import java.time.Duration;

import com.github.ecsoya.sword.exceptions.LockableException;

/**
 * The Interface ILockService.
 */
public interface ILockService {

	/**
	 * Release lock.
	 *
	 * @param name the name
	 * @return true, if successful
	 */
	boolean releaseLock(String name);

	/**
	 * Checks if is locked.
	 *
	 * @param name the name
	 * @return true, if is locked
	 */
	boolean isLocked(String name);

	/**
	 * Try lock.
	 *
	 * @param name     the name
	 * @param duration the duration
	 * @return true, if successful
	 * @throws LockableException the lockable exception
	 */
	boolean tryLock(String name, Duration duration) throws LockableException;

	/**
	 * Try lock.
	 *
	 * @param name the name
	 * @return true, if successful
	 * @throws LockableException the lockable exception
	 */
	boolean tryLock(String name) throws LockableException;
}
