package com.github.ecsoya.sword.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.ecsoya.sword.user.domain.UserBinaryTree;

/**
 * The Interface UserBinaryTreeMapper.
 */
public interface UserBinaryTreeMapper {

	/**
	 * Select user binary tree by id.
	 *
	 * @param userId the user id
	 * @return the user binary tree
	 */
	public UserBinaryTree selectUserBinaryTreeById(Long userId);

	/**
	 * Select user binary tree list.
	 *
	 * @param userBinaryTree the user binary tree
	 * @return the list
	 */
	public List<UserBinaryTree> selectUserBinaryTreeList(UserBinaryTree userBinaryTree);

	/**
	 * Insert user binary tree.
	 *
	 * @param userBinaryTree the user binary tree
	 * @return the int
	 */
	public int insertUserBinaryTree(UserBinaryTree userBinaryTree);

	/**
	 * Update user binary tree.
	 *
	 * @param userBinaryTree the user binary tree
	 * @return the int
	 */
	public int updateUserBinaryTree(UserBinaryTree userBinaryTree);

	/**
	 * Delete user binary tree by id.
	 *
	 * @param userId the user id
	 * @return the int
	 */
	public int deleteUserBinaryTreeById(Long userId);

	/**
	 * Delete user binary tree by ids.
	 *
	 * @param userIds the user ids
	 * @return the int
	 */
	public int deleteUserBinaryTreeByIds(String[] userIds);

	/**
	 * Checks if is in binary tree.
	 *
	 * @param userId the user id
	 * @return the int
	 */
	public int isInBinaryTree(long userId);

	/**
	 * Query user binary tree count.
	 *
	 * @return the int
	 */
	public int queryUserBinaryTreeCount();

	/**
	 * Select user binary trees.
	 *
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 */
	public List<UserBinaryTree> selectUserBinaryTrees(@Param("start") Integer start, @Param("limit") Integer limit);
}
