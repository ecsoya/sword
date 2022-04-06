package com.github.ecsoya.sword.article.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.article.domain.UserAdvise;
import com.github.ecsoya.sword.article.mapper.UserAdviseMapper;
import com.github.ecsoya.sword.article.service.IUserAdviseService;
import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.IdWorker;
import com.github.ecsoya.sword.common.utils.StringUtils;

/**
 * The Class UserAdviseServiceImpl.
 */
@Service
public class UserAdviseServiceImpl implements IUserAdviseService {

	/** The user advise mapper. */
	@Autowired
	private UserAdviseMapper userAdviseMapper;

	/**
	 * Select user advise by id.
	 *
	 * @param id the id
	 * @return the user advise
	 */
	@Override
	public UserAdvise selectUserAdviseById(Long id) {
		return userAdviseMapper.selectUserAdviseById(id);
	}

	/**
	 * Select user advise list.
	 *
	 * @param userAdvise the user advise
	 * @return the list
	 */
	@Override
	public List<UserAdvise> selectUserAdviseList(UserAdvise userAdvise) {
		return userAdviseMapper.selectUserAdviseList(userAdvise);
	}

	/**
	 * Insert user advise.
	 *
	 * @param userAdvise the user advise
	 * @return the int
	 */
	@Override
	public int insertUserAdvise(UserAdvise userAdvise) {
		if (UserAdvise.TYPE_FEEDBACK.equals(userAdvise.getType()) && StringUtils.isEmpty(userAdvise.getTitle())) {
			return -1;
		}
		if (StringUtils.isEmpty(userAdvise.getDescription())) {
			return -2;
		}
		if (userAdvise.getId() == null) {
			userAdvise.setId(IdWorker.getId());
		}
		if (userAdvise.getCreateTime() == null) {
			userAdvise.setCreateTime(DateUtils.getNowDate());
		}
		return userAdviseMapper.insertUserAdvise(userAdvise);
	}

	/**
	 * Update user advise.
	 *
	 * @param userAdvise the user advise
	 * @return the int
	 */
	@Override
	public int updateUserAdvise(UserAdvise userAdvise) {
		userAdvise.setUpdateTime(DateUtils.getNowDate());
		return userAdviseMapper.updateUserAdvise(userAdvise);
	}

	/**
	 * Delete user advise by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteUserAdviseByIds(String ids) {
		return userAdviseMapper.deleteUserAdviseByIds(Convert.toStrArray(ids));
	}

	/**
	 * Delete user advise by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Override
	public int deleteUserAdviseById(Long id) {
		return userAdviseMapper.deleteUserAdviseById(id);
	}

	/**
	 * Select user advise list by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	@Override
	public List<UserAdvise> selectUserAdviseListByUserId(Long userId) {
		if (userId == null) {
			return Collections.emptyList();
		}
		final List<UserAdvise> list = userAdviseMapper.selectUserAdviseListByUserId(userId);
		if (list == null || list.isEmpty()) {
			return Collections.emptyList();
		}
		list.forEach(ua -> ua.setReplies(userAdviseMapper.selectUserAdviseReplyList(ua.getId())));
		return list;
	}
}
