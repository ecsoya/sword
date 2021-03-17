package com.soyatec.sword.article.service;

import java.util.List;

import com.soyatec.sword.article.domain.SwordArticle;

/**
 * 文章Service接口
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-02-04
 */
public interface ISwordArticleService {
	/**
	 * 查询文章
	 * 
	 * @param id 文章ID
	 * @return 文章
	 */
	public SwordArticle selectSwordArticleById(Long id);

	/**
	 * 查询文章列表
	 * 
	 * @param swordArticle 文章
	 * @return 文章集合
	 */
	public List<SwordArticle> selectSwordArticleList(SwordArticle swordArticle);

	/**
	 * 新增文章
	 * 
	 * @param swordArticle 文章
	 * @return 结果
	 */
	public int insertSwordArticle(SwordArticle swordArticle);

	/**
	 * 修改文章
	 * 
	 * @param swordArticle 文章
	 * @return 结果
	 */
	public int updateSwordArticle(SwordArticle swordArticle);

	/**
	 * 批量删除文章
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteSwordArticleByIds(String ids);

	/**
	 * 删除文章信息
	 * 
	 * @param id 文章ID
	 * @return 结果
	 */
	public int deleteSwordArticleById(Long id);

	public List<String> selectSwordArticleCategories();
}
