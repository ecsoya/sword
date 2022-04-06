package com.github.ecsoya.sword.article.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.article.domain.UserArticle;
import com.github.ecsoya.sword.article.domain.UserArticleComment;
import com.github.ecsoya.sword.article.mapper.UserArticleMapper;
import com.github.ecsoya.sword.article.service.IUserArticleService;
import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.IdWorker;

/**
 * The Class UserArticleServiceImpl.
 */
@Service
public class UserArticleServiceImpl implements IUserArticleService {

	/** The user article mapper. */
	@Autowired
	private UserArticleMapper userArticleMapper;

	/**
	 * Select user article by id.
	 *
	 * @param id the id
	 * @return the user article
	 */
	@Override
	public UserArticle selectUserArticleById(Long id) {
		return userArticleMapper.selectUserArticleById(id);
	}

	/**
	 * Select user article list.
	 *
	 * @param userArticle the user article
	 * @return the list
	 */
	@Override
	public List<UserArticle> selectUserArticleList(UserArticle userArticle) {
		return userArticleMapper.selectUserArticleList(userArticle);
	}

	/**
	 * Insert user article.
	 *
	 * @param userArticle the user article
	 * @return the int
	 */
	@Override
	public int insertUserArticle(UserArticle userArticle) {
		if (userArticle.getId() == null) {
			userArticle.setId(IdWorker.getId());
		}
		if (userArticle.getCreateTime() == null) {
			userArticle.setCreateTime(DateUtils.getNowDate());
		}
		return userArticleMapper.insertUserArticle(userArticle);
	}

	/**
	 * Update user article.
	 *
	 * @param userArticle the user article
	 * @return the int
	 */
	@Override
	public int updateUserArticle(UserArticle userArticle) {
		userArticle.setUpdateTime(DateUtils.getNowDate());
		return userArticleMapper.updateUserArticle(userArticle);
	}

	/**
	 * Delete user article by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteUserArticleByIds(String ids) {
		return userArticleMapper.deleteUserArticleByIds(Convert.toStrArray(ids));
	}

	/**
	 * Delete user article by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Override
	public int deleteUserArticleById(Long id) {
		return userArticleMapper.deleteUserArticleById(id);
	}

	/**
	 * Select user article by article id.
	 *
	 * @param articleId the article id
	 * @param userId    the user id
	 * @return the user article
	 */
	@Override
	public UserArticle selectUserArticleByArticleId(Long articleId, Long userId) {
		if (articleId == null || userId == null) {
			return null;
		}
		final UserArticle query = new UserArticle();
		query.setArticleId(articleId);
		query.setUserId(userId);
		final List<UserArticle> list = selectUserArticleList(query);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		final UserArticle article = new UserArticle();
		article.setArticleId(articleId);
		article.setUserId(userId);
		insertUserArticle(article);
		return article;
	}

	/**
	 * Select user article comment list.
	 *
	 * @param articleId the article id
	 * @return the list
	 */
	@Override
	public List<UserArticleComment> selectUserArticleCommentList(Long articleId) {
		if (articleId == null) {
			return Collections.emptyList();
		}
		return userArticleMapper.selectUserArticleCommentList(articleId);
	}
}
