package com.soyatec.sword.user.service;

import java.util.List;

import com.soyatec.sword.user.domain.UserBinaryTree;

/**
 * 用户双区树Service接口
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
public interface IUserBinaryTreeService {
	/**
	 * 查询用户双区树
	 *
	 * @param userId 用户双区树ID
	 * @return 用户双区树
	 */
	public UserBinaryTree selectUserBinaryTreeById(Long userId);

	/**
	 * 查询用户双区树列表
	 *
	 * @param userBinaryTree 用户双区树
	 * @return 用户双区树集合
	 */
	public List<UserBinaryTree> selectUserBinaryTreeList(UserBinaryTree userBinaryTree);

	/**
	 * 新增用户双区树
	 *
	 * @param userBinaryTree 用户双区树
	 * @return 结果
	 */
	public int insertUserBinaryTree(UserBinaryTree userBinaryTree);

	/**
	 * 修改用户双区树
	 *
	 * @param userBinaryTree 用户双区树
	 * @return 结果
	 */
	public int updateUserBinaryTree(UserBinaryTree userBinaryTree);

	/**
	 * 批量删除用户双区树
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteUserBinaryTreeByIds(String ids);

	/**
	 * 删除用户双区树信息
	 *
	 * @param userId 用户双区树ID
	 * @return 结果
	 */
	public int deleteUserBinaryTreeById(Long userId);

	/**
	 * 加锁更新所有未完成的树
	 */
	public int updateUserBinaryTrees();

	public List<UserBinaryTree> selectAll();

	public List<UserBinaryTree> selectUmbrellaBinaryTreeList(Long userId, List<UserBinaryTree> treeList);

	public List<Long> selectUmbrellaBinaryTreeIds(Long userId, List<UserBinaryTree> treeList);

	public UserBinaryTree selectUserBinaryTreeByUserId(Long userId, List<UserBinaryTree> treeList);
}
