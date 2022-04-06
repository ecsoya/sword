package com.github.ecsoya.sword.system.mapper;

import java.util.List;

import com.github.ecsoya.sword.system.domain.SysUserPost;

/**
 * The Interface SysUserPostMapper.
 */
public interface SysUserPostMapper {

	/**
	 * Delete user post by user id.
	 *
	 * @param userId the user id
	 * @return the int
	 */
	public int deleteUserPostByUserId(Long userId);

	/**
	 * Count user post by id.
	 *
	 * @param postId the post id
	 * @return the int
	 */
	public int countUserPostById(Long postId);

	/**
	 * Delete user post.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteUserPost(Long[] ids);

	/**
	 * Batch user post.
	 *
	 * @param userPostList the user post list
	 * @return the int
	 */
	public int batchUserPost(List<SysUserPost> userPostList);
}
