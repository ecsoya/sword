package com.soyatec.sword.article.mapper;

import java.util.List;

import com.soyatec.sword.article.domain.UserArticle;
import com.soyatec.sword.article.domain.UserArticleComment;

/**
 * 用户文章Mapper接口
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-02-04
 */
public interface UserArticleMapper {
	/**
	 * 查询用户文章
	 * 
	 * @param id 用户文章ID
	 * @return 用户文章
	 */
	public UserArticle selectUserArticleById(Long id);

	/**
	 * 查询用户文章列表
	 * 
	 * @param userArticle 用户文章
	 * @return 用户文章集合
	 */
	public List<UserArticle> selectUserArticleList(UserArticle userArticle);

	/**
	 * 新增用户文章
	 * 
	 * @param userArticle 用户文章
	 * @return 结果
	 */
	public int insertUserArticle(UserArticle userArticle);

	/**
	 * 修改用户文章
	 * 
	 * @param userArticle 用户文章
	 * @return 结果
	 */
	public int updateUserArticle(UserArticle userArticle);

	/**
	 * 删除用户文章
	 * 
	 * @param id 用户文章ID
	 * @return 结果
	 */
	public int deleteUserArticleById(Long id);

	/**
	 * 批量删除用户文章
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteUserArticleByIds(String[] ids);

	public List<UserArticleComment> selectUserArticleCommentList(Long articleId);
}
