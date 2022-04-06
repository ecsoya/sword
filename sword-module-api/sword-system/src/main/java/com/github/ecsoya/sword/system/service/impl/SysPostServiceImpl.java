package com.github.ecsoya.sword.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.constant.UserConstants;
import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.exception.BusinessException;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.system.domain.SysPost;
import com.github.ecsoya.sword.system.mapper.SysPostMapper;
import com.github.ecsoya.sword.system.mapper.SysUserPostMapper;
import com.github.ecsoya.sword.system.service.ISysPostService;

/**
 * The Class SysPostServiceImpl.
 */
@Service
public class SysPostServiceImpl implements ISysPostService {

	/** The post mapper. */
	@Autowired
	private SysPostMapper postMapper;

	/** The user post mapper. */
	@Autowired
	private SysUserPostMapper userPostMapper;

	/**
	 * Select post list.
	 *
	 * @param post the post
	 * @return the list
	 */
	@Override
	public List<SysPost> selectPostList(SysPost post) {
		return postMapper.selectPostList(post);
	}

	/**
	 * Select post all.
	 *
	 * @return the list
	 */
	@Override
	public List<SysPost> selectPostAll() {
		return postMapper.selectPostAll();
	}

	/**
	 * Select posts by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	@Override
	public List<SysPost> selectPostsByUserId(Long userId) {
		final List<SysPost> userPosts = postMapper.selectPostsByUserId(userId);
		final List<SysPost> posts = postMapper.selectPostAll();
		for (final SysPost post : posts) {
			for (final SysPost userRole : userPosts) {
				if (post.getPostId().longValue() == userRole.getPostId().longValue()) {
					post.setFlag(true);
					break;
				}
			}
		}
		return posts;
	}

	/**
	 * Select post by id.
	 *
	 * @param postId the post id
	 * @return the sys post
	 */
	@Override
	public SysPost selectPostById(Long postId) {
		return postMapper.selectPostById(postId);
	}

	/**
	 * Delete post by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 * @throws BusinessException the business exception
	 */
	@Override
	public int deletePostByIds(String ids) throws BusinessException {
		final Long[] postIds = Convert.toLongArray(ids);
		for (final Long postId : postIds) {
			final SysPost post = selectPostById(postId);
			if (countUserPostById(postId) > 0) {
				throw new BusinessException(String.format("%1$s已分配,不能删除", post.getPostName()));
			}
		}
		return postMapper.deletePostByIds(postIds);
	}

	/**
	 * Insert post.
	 *
	 * @param post the post
	 * @return the int
	 */
	@Override
	public int insertPost(SysPost post) {
		return postMapper.insertPost(post);
	}

	/**
	 * Update post.
	 *
	 * @param post the post
	 * @return the int
	 */
	@Override
	public int updatePost(SysPost post) {
		return postMapper.updatePost(post);
	}

	/**
	 * Count user post by id.
	 *
	 * @param postId the post id
	 * @return the int
	 */
	@Override
	public int countUserPostById(Long postId) {
		return userPostMapper.countUserPostById(postId);
	}

	/**
	 * Check post name unique.
	 *
	 * @param post the post
	 * @return the string
	 */
	@Override
	public String checkPostNameUnique(SysPost post) {
		final Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
		final SysPost info = postMapper.checkPostNameUnique(post.getPostName());
		if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue()) {
			return UserConstants.POST_NAME_NOT_UNIQUE;
		}
		return UserConstants.POST_NAME_UNIQUE;
	}

	/**
	 * Check post code unique.
	 *
	 * @param post the post
	 * @return the string
	 */
	@Override
	public String checkPostCodeUnique(SysPost post) {
		final Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
		final SysPost info = postMapper.checkPostCodeUnique(post.getPostCode());
		if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue()) {
			return UserConstants.POST_CODE_NOT_UNIQUE;
		}
		return UserConstants.POST_CODE_UNIQUE;
	}
}
