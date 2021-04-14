package com.soyatec.sword.system.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.Deque;
import java.util.List;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soyatec.sword.common.constant.ShiroConstants;
import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.system.domain.SysUserOnline;
import com.soyatec.sword.system.mapper.SysUserOnlineMapper;
import com.soyatec.sword.system.service.ISysUserOnlineService;

/**
 * 在线用户 服务层处理
 *
 * @author Jin Liu (angryred@qq.com)
 */
@Service
public class SysUserOnlineServiceImpl implements ISysUserOnlineService {
	@Autowired
	private SysUserOnlineMapper userOnlineDao;

	@Autowired
	private CacheManager cacheManager;

	/**
	 * 通过会话序号查询信息
	 *
	 * @param sessionId 会话ID
	 * @return 在线用户信息
	 */
	@Override
	public SysUserOnline selectOnlineById(String sessionId) {
		return userOnlineDao.selectOnlineById(sessionId);
	}

	/**
	 * 通过会话序号删除信息
	 *
	 * @param sessionId 会话ID
	 * @return 在线用户信息
	 */
	@Override
	public void deleteOnlineById(String sessionId) {
		final SysUserOnline userOnline = selectOnlineById(sessionId);
		if (StringUtils.isNotNull(userOnline)) {
			userOnlineDao.deleteOnlineById(sessionId);
		}
	}

	/**
	 * 通过会话序号删除信息
	 *
	 * @param sessions 会话ID集合
	 * @return 在线用户信息
	 */
	@Override
	public void batchDeleteOnline(List<String> sessions) {
		for (final String sessionId : sessions) {
			final SysUserOnline userOnline = selectOnlineById(sessionId);
			if (StringUtils.isNotNull(userOnline)) {
				userOnlineDao.deleteOnlineById(sessionId);
			}
		}
	}

	/**
	 * 保存会话信息
	 *
	 * @param online 会话信息
	 */
	@Override
	public void saveOnline(SysUserOnline online) {
		userOnlineDao.saveOnline(online);
	}

	/**
	 * 查询会话集合
	 *
	 * @param userOnline 在线用户
	 */
	@Override
	public List<SysUserOnline> selectUserOnlineList(SysUserOnline userOnline) {
		return userOnlineDao.selectUserOnlineList(userOnline);
	}

	/**
	 * 强退用户
	 *
	 * @param sessionId 会话ID
	 */
	@Override
	public void forceLogout(String sessionId) {
		userOnlineDao.deleteOnlineById(sessionId);
	}

	/**
	 * 清理用户缓存
	 *
	 * @param loginName 登录名称
	 * @param sessionId 会话ID
	 */
	@Override
	public void removeUserCache(String loginName, String sessionId) {
		final Cache<String, Deque<Serializable>> cache = cacheManager.getCache(ShiroConstants.SYS_USERCACHE);
		final Deque<Serializable> deque = cache.get(loginName);
		if (StringUtils.isEmpty(deque) || deque.size() == 0) {
			return;
		}
		deque.remove(sessionId);
	}

	/**
	 * 查询会话集合
	 *
	 * @param expiredDate 失效日期
	 */
	@Override
	public List<SysUserOnline> selectOnlineByExpired(Date expiredDate) {
		final String lastAccessTime = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, expiredDate);
		return userOnlineDao.selectOnlineByExpired(lastAccessTime);
	}
}
