package com.github.ecsoya.sword.user.service;

import java.util.List;

import com.github.ecsoya.sword.user.domain.UserBinaryTree;

/**
 * The Interface IUserBinaryTreeService.
 */
public interface IUserBinaryTreeService {

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
	 * Delete user binary tree by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteUserBinaryTreeByIds(String ids);

	/**
	 * Delete user binary tree by id.
	 *
	 * @param userId the user id
	 * @return the int
	 */
	public int deleteUserBinaryTreeById(Long userId);

	/**
	 * Update user binary trees.
	 *
	 * @return the int
	 */
	public int updateUserBinaryTrees();

	/**
	 * Select all.
	 *
	 * @return the list
	 */
	public List<UserBinaryTree> selectAll();

	/**
	 * Select umbrella binary tree list.
	 *
	 * @param userId   the user id
	 * @param treeList the tree list
	 * @return the list
	 */
	public List<UserBinaryTree> selectUmbrellaBinaryTreeList(Long userId, List<UserBinaryTree> treeList);

	/**
	 * Select umbrella binary tree ids.
	 *
	 * @param userId   the user id
	 * @param treeList the tree list
	 * @return the list
	 */
	public List<Long> selectUmbrellaBinaryTreeIds(Long userId, List<UserBinaryTree> treeList);

	/**
	 * Select user binary tree by user id.
	 *
	 * @param userId   the user id
	 * @param treeList the tree list
	 * @return the user binary tree
	 */
	public UserBinaryTree selectUserBinaryTreeByUserId(Long userId, List<UserBinaryTree> treeList);
}
