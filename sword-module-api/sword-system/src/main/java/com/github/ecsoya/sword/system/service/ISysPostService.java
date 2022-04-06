package com.github.ecsoya.sword.system.service;

import java.util.List;

import com.github.ecsoya.sword.system.domain.SysPost;

/**
 * The Interface ISysPostService.
 */
public interface ISysPostService {

	/**
	 * Select post list.
	 *
	 * @param post the post
	 * @return the list
	 */
	public List<SysPost> selectPostList(SysPost post);

	/**
	 * Select post all.
	 *
	 * @return the list
	 */
	public List<SysPost> selectPostAll();

	/**
	 * Select posts by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	public List<SysPost> selectPostsByUserId(Long userId);

	/**
	 * Select post by id.
	 *
	 * @param postId the post id
	 * @return the sys post
	 */
	public SysPost selectPostById(Long postId);

	/**
	 * Delete post by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 * @throws Exception the exception
	 */
	public int deletePostByIds(String ids) throws Exception;

	/**
	 * Insert post.
	 *
	 * @param post the post
	 * @return the int
	 */
	public int insertPost(SysPost post);

	/**
	 * Update post.
	 *
	 * @param post the post
	 * @return the int
	 */
	public int updatePost(SysPost post);

	/**
	 * Count user post by id.
	 *
	 * @param postId the post id
	 * @return the int
	 */
	public int countUserPostById(Long postId);

	/**
	 * Check post name unique.
	 *
	 * @param post the post
	 * @return the string
	 */
	public String checkPostNameUnique(SysPost post);

	/**
	 * Check post code unique.
	 *
	 * @param post the post
	 * @return the string
	 */
	public String checkPostCodeUnique(SysPost post);
}
