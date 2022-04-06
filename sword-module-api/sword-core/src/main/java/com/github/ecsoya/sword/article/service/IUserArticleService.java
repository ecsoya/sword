package com.github.ecsoya.sword.article.service;

import java.util.List;

import com.github.ecsoya.sword.article.domain.UserArticle;
import com.github.ecsoya.sword.article.domain.UserArticleComment;

/**
 * The Interface IUserArticleService.
 */
public interface IUserArticleService {

	/**
	 * Select user article by id.
	 *
	 * @param id the id
	 * @return the user article
	 */
	public UserArticle selectUserArticleById(Long id);

	/**
	 * Select user article list.
	 *
	 * @param userArticle the user article
	 * @return the list
	 */
	public List<UserArticle> selectUserArticleList(UserArticle userArticle);

	/**
	 * Insert user article.
	 *
	 * @param userArticle the user article
	 * @return the int
	 */
	public int insertUserArticle(UserArticle userArticle);

	/**
	 * Update user article.
	 *
	 * @param userArticle the user article
	 * @return the int
	 */
	public int updateUserArticle(UserArticle userArticle);

	/**
	 * Delete user article by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteUserArticleByIds(String ids);

	/**
	 * Delete user article by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteUserArticleById(Long id);

	/**
	 * Select user article by article id.
	 *
	 * @param articleId the article id
	 * @param userId    the user id
	 * @return the user article
	 */
	public UserArticle selectUserArticleByArticleId(Long articleId, Long userId);

	/**
	 * Select user article comment list.
	 *
	 * @param articleId the article id
	 * @return the list
	 */
	public List<UserArticleComment> selectUserArticleCommentList(Long articleId);
}
