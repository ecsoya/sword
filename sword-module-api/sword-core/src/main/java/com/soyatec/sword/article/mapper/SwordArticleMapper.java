package com.soyatec.sword.article.mapper;

import java.util.List;

import com.soyatec.sword.article.domain.SwordArticle;

/**
 * 文章Mapper接口
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-02-04
 */
public interface SwordArticleMapper {
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
	 * @param beeplusArticle 文章
	 * @return 文章集合
	 */
	public List<SwordArticle> selectSwordArticleList(SwordArticle beeplusArticle);

	/**
	 * 新增文章
	 *
	 * @param beeplusArticle 文章
	 * @return 结果
	 */
	public int insertSwordArticle(SwordArticle beeplusArticle);

	/**
	 * 修改文章
	 *
	 * @param beeplusArticle 文章
	 * @return 结果
	 */
	public int updateSwordArticle(SwordArticle beeplusArticle);

	/**
	 * 删除文章
	 *
	 * @param id 文章ID
	 * @return 结果
	 */
	public int deleteSwordArticleById(Long id);

	/**
	 * 批量删除文章
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteSwordArticleByIds(String[] ids);

	public List<String> selectSwordArticleCategories();
}
