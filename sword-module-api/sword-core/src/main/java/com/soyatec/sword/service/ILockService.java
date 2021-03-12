package com.soyatec.sword.service;

import java.time.Duration;

import com.soyatec.sword.exceptions.LockableException;

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
