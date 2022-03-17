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
 * 用户反馈Service业务层处理
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-06
 */
@Service
public class UserAdviseServiceImpl implements IUserAdviseService {
	@Autowired
	private UserAdviseMapper userAdviseMapper;

	/**
	 * 查询用户反馈
	 *
	 * @param id 用户反馈ID
	 * @return 用户反馈
	 */
	@Override
	public UserAdvise selectUserAdviseById(Long id) {
		return userAdviseMapper.selectUserAdviseById(id);
	}

	/**
	 * 查询用户反馈列表
	 *
	 * @param userAdvise 用户反馈
	 * @return 用户反馈
	 */
	@Override
	public List<UserAdvise> selectUserAdviseList(UserAdvise userAdvise) {
		return userAdviseMapper.selectUserAdviseList(userAdvise);
	}

	/**
	 * 新增用户反馈
	 *
	 * @param userAdvise 用户反馈
	 * @return 结果
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
	 * 修改用户反馈
	 *
	 * @param userAdvise 用户反馈
	 * @return 结果
	 */
	@Override
	public int updateUserAdvise(UserAdvise userAdvise) {
		userAdvise.setUpdateTime(DateUtils.getNowDate());
		return userAdviseMapper.updateUserAdvise(userAdvise);
	}

	/**
	 * 删除用户反馈对象
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteUserAdviseByIds(String ids) {
		return userAdviseMapper.deleteUserAdviseByIds(Convert.toStrArray(ids));
	}

	/**
	 * 删除用户反馈信息
	 *
	 * @param id 用户反馈ID
	 * @return 结果
	 */
	@Override
	public int deleteUserAdviseById(Long id) {
		return userAdviseMapper.deleteUserAdviseById(id);
	}

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
