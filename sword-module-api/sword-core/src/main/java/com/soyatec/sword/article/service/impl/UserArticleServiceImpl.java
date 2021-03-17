package com.soyatec.sword.article.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soyatec.sword.article.domain.UserArticle;
import com.soyatec.sword.article.domain.UserArticleComment;
import com.soyatec.sword.article.mapper.UserArticleMapper;
import com.soyatec.sword.article.service.IUserArticleService;
import com.soyatec.sword.common.core.text.Convert;
import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.common.utils.IdWorker;

/**
 * 用户文章Service业务层处理
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-02-04
 */
@Service
public class UserArticleServiceImpl implements IUserArticleService {
	@Autowired
	private UserArticleMapper userArticleMapper;

	/**
	 * 查询用户文章
	 * 
	 * @param id 用户文章ID
	 * @return 用户文章
	 */
	@Override
	public UserArticle selectUserArticleById(Long id) {
		return userArticleMapper.selectUserArticleById(id);
	}

	/**
	 * 查询用户文章列表
	 * 
	 * @param userArticle 用户文章
	 * @return 用户文章
	 */
	@Override
	public List<UserArticle> selectUserArticleList(UserArticle userArticle) {
		return userArticleMapper.selectUserArticleList(userArticle);
	}

	/**
	 * 新增用户文章
	 * 
	 * @param userArticle 用户文章
	 * @return 结果
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
	 * 修改用户文章
	 * 
	 * @param userArticle 用户文章
	 * @return 结果
	 */
	@Override
	public int updateUserArticle(UserArticle userArticle) {
		userArticle.setUpdateTime(DateUtils.getNowDate());
		return userArticleMapper.updateUserArticle(userArticle);
	}

	/**
	 * 删除用户文章对象
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteUserArticleByIds(String ids) {
		return userArticleMapper.deleteUserArticleByIds(Convert.toStrArray(ids));
	}

	/**
	 * 删除用户文章信息
	 * 
	 * @param id 用户文章ID
	 * @return 结果
	 */
	@Override
	public int deleteUserArticleById(Long id) {
		return userArticleMapper.deleteUserArticleById(id);
	}

	@Override
	public UserArticle selectUserArticleByArticleId(Long articleId, Long userId) {
		if (articleId == null || userId == null) {
			return null;
		}
		UserArticle query = new UserArticle();
		query.setArticleId(articleId);
		query.setUserId(userId);
		List<UserArticle> list = selectUserArticleList(query);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		UserArticle article = new UserArticle();
		article.setArticleId(articleId);
		article.setUserId(userId);
		insertUserArticle(article);
		return article;
	}

	@Override
	public List<UserArticleComment> selectUserArticleCommentList(Long articleId) {
		if (articleId == null) {
			return Collections.emptyList();
		}
		return userArticleMapper.selectUserArticleCommentList(articleId);
	}
}
