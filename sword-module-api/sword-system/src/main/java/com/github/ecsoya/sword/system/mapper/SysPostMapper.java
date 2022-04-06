package com.github.ecsoya.sword.system.mapper;

import java.util.List;

import com.github.ecsoya.sword.system.domain.SysPost;

/**
 * The Interface SysPostMapper.
 */
public interface SysPostMapper {

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
	 */
	public int deletePostByIds(Long[] ids);

	/**
	 * Update post.
	 *
	 * @param post the post
	 * @return the int
	 */
	public int updatePost(SysPost post);

	/**
	 * Insert post.
	 *
	 * @param post the post
	 * @return the int
	 */
	public int insertPost(SysPost post);

	/**
	 * Check post name unique.
	 *
	 * @param postName the post name
	 * @return the sys post
	 */
	public SysPost checkPostNameUnique(String postName);

	/**
	 * Check post code unique.
	 *
	 * @param postCode the post code
	 * @return the sys post
	 */
	public SysPost checkPostCodeUnique(String postCode);
}
