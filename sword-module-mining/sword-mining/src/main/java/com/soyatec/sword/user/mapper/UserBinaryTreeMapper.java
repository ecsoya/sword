package com.soyatec.sword.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.soyatec.sword.user.domain.UserBinaryTree;

/**
 * 用户双区树Mapper接口
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
public interface UserBinaryTreeMapper {
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
	 * 删除用户双区树
	 *
	 * @param userId 用户双区树ID
	 * @return 结果
	 */
	public int deleteUserBinaryTreeById(Long userId);

	/**
	 * 批量删除用户双区树
	 *
	 * @param userIds 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteUserBinaryTreeByIds(String[] userIds);

	public int isInBinaryTree(long userId);

	public int queryUserBinaryTreeCount();

	public List<UserBinaryTree> selectUserBinaryTrees(@Param("start") Integer start, @Param("limit") Integer limit);
}
