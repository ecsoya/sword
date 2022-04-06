package com.github.ecsoya.sword.article.mapper;

import java.util.List;

import com.github.ecsoya.sword.article.domain.UserArticle;
import com.github.ecsoya.sword.article.domain.UserArticleComment;

/**
 * The Interface UserArticleMapper.
 */
public interface UserArticleMapper {

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
	 * Delete user article by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteUserArticleById(Long id);

	/**
	 * Delete user article by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteUserArticleByIds(String[] ids);

	/**
	 * Select user article comment list.
	 *
	 * @param articleId the article id
	 * @return the list
	 */
	public List<UserArticleComment> selectUserArticleCommentList(Long articleId);
}
