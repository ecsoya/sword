package com.github.ecsoya.sword.article.mapper;

import java.util.List;

import com.github.ecsoya.sword.article.domain.SwordArticle;

/**
 * The Interface SwordArticleMapper.
 */
public interface SwordArticleMapper {

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
	 * @param beeplusArticle the beeplus article
	 * @return the list
	 */
	public List<SwordArticle> selectSwordArticleList(SwordArticle beeplusArticle);

	/**
	 * Insert sword article.
	 *
	 * @param beeplusArticle the beeplus article
	 * @return the int
	 */
	public int insertSwordArticle(SwordArticle beeplusArticle);

	/**
	 * Update sword article.
	 *
	 * @param beeplusArticle the beeplus article
	 * @return the int
	 */
	public int updateSwordArticle(SwordArticle beeplusArticle);

	/**
	 * Delete sword article by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteSwordArticleById(Long id);

	/**
	 * Delete sword article by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteSwordArticleByIds(String[] ids);

	/**
	 * Select sword article categories.
	 *
	 * @return the list
	 */
	public List<String> selectSwordArticleCategories();
}
