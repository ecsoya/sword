package com.github.ecsoya.sword.article.service;

import java.util.List;

import com.github.ecsoya.sword.article.domain.SwordArticle;

/**
 * The Interface ISwordArticleService.
 */
public interface ISwordArticleService {

	/**
	 * Select sword article by id.
	 *
	 * @param id the id
	 * @return the sword article
	 */
	public SwordArticle selectSwordArticleById(Long id);

	/**
	 * Select sword article list.
	 *
	 * @param swordArticle the sword article
	 * @return the list
	 */
	public List<SwordArticle> selectSwordArticleList(SwordArticle swordArticle);

	/**
	 * Insert sword article.
	 *
	 * @param swordArticle the sword article
	 * @return the int
	 */
	public int insertSwordArticle(SwordArticle swordArticle);

	/**
	 * Update sword article.
	 *
	 * @param swordArticle the sword article
	 * @return the int
	 */
	public int updateSwordArticle(SwordArticle swordArticle);

	/**
	 * Delete sword article by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteSwordArticleByIds(String ids);

	/**
	 * Delete sword article by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteSwordArticleById(Long id);

	/**
	 * Select sword article categories.
	 *
	 * @return the list
	 */
	public List<String> selectSwordArticleCategories();
}
