package com.soyatec.sword.user.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.Vector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soyatec.sword.common.core.text.Convert;
import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.service.ILockService;
import com.soyatec.sword.user.domain.UserBinaryTree;
import com.soyatec.sword.user.domain.UserReferrer;
import com.soyatec.sword.user.mapper.UserBinaryTreeMapper;
import com.soyatec.sword.user.service.IUserBinaryTreeService;
import com.soyatec.sword.user.service.IUserReferrerService;

/**
 * 用户双区树Service业务层处理
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
@Service
public class UserBinaryTreeServiceImpl implements IUserBinaryTreeService {

	private static final int PAGE_SIZE = 10000;

	@Autowired
	private UserBinaryTreeMapper userBinaryTreeMapper;

	private static final String LOCK_KEY = "binaryTreeLock";

	@Autowired
	private ILockService lockService;

	@Autowired
	private IUserReferrerService referrerService;

	/**
	 * 查询用户双区树
	 *
	 * @param userId 用户双区树ID
	 * @return 用户双区树
	 */
	@Override
	public UserBinaryTree selectUserBinaryTreeById(Long userId) {
		return userBinaryTreeMapper.selectUserBinaryTreeById(userId);
	}

	/**
	 * 查询用户双区树列表
	 *
	 * @param userBinaryTree 用户双区树
	 * @return 用户双区树
	 */
	@Override
	public List<UserBinaryTree> selectUserBinaryTreeList(UserBinaryTree userBinaryTree) {
		return userBinaryTreeMapper.selectUserBinaryTreeList(userBinaryTree);
	}

	/**
	 * 新增用户双区树
	 *
	 * @param userBinaryTree 用户双区树
	 * @return 结果
	 */
	@Override
	public int insertUserBinaryTree(UserBinaryTree userBinaryTree) {
		userBinaryTree.setCreateTime(DateUtils.getNowDate());
		return userBinaryTreeMapper.insertUserBinaryTree(userBinaryTree);
	}

	/**
	 * 修改用户双区树
	 *
	 * @param userBinaryTree 用户双区树
	 * @return 结果
	 */
	@Override
	public int updateUserBinaryTree(UserBinaryTree userBinaryTree) {
		userBinaryTree.setUpdateTime(DateUtils.getNowDate());
		return userBinaryTreeMapper.updateUserBinaryTree(userBinaryTree);
	}

	/**
	 * 删除用户双区树对象
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteUserBinaryTreeByIds(String ids) {
		return userBinaryTreeMapper.deleteUserBinaryTreeByIds(Convert.toStrArray(ids));
	}

	/**
	 * 删除用户双区树信息
	 *
	 * @param userId 用户双区树ID
	 * @return 结果
	 */
	@Override
	public int deleteUserBinaryTreeById(Long userId) {
		return userBinaryTreeMapper.deleteUserBinaryTreeById(userId);
	}

	@Override
	public int updateUserBinaryTrees() {
		boolean isLocked = false;
		try {
			isLocked = lockService.tryLock(LOCK_KEY);

			final List<UserReferrer> list = referrerService.selectUnfinishedUserReferrers();
			list.forEach(user -> {
				final UserReferrer update = new UserReferrer();
				update.setUserId(user.getUserId());
				final int btree = addBinaryTree(user.getReferralId(), user.getUserId(), user.isInLeftZone());
				update.setBtree(btree);
				if (btree > 0) {
					// 只要推荐了一个，说明右区开放
					update.setLeftCodeEnabled(referrerService.computeLeftCodeEnabled(user.getUserId()));
					update.setRightCodeEnabled(referrerService.computeRightCodeEnabled(user.getUserId()));
				}
				referrerService.updateUserReferrer(update);
			});
			return list.size();
		} catch (final Exception e) {
			return 0;
		} finally {
			if (isLocked) {
				lockService.releaseLock(LOCK_KEY);
			}
		}
	}

	private int addBinaryTree(Long referralId, Long userId, boolean leftZone) {
		synchronized (this) {
			if (referralId == null || referralId == userId) {
				return 0;
			}
			if (isInBinaryTree(userId)) {
				return 1;
			}
			UserBinaryTree binary = getBinaryTree(referralId);
			if (binary == null) {
				binary = new UserBinaryTree();
				binary.setUserId(referralId);
				binary.setLeftId(userId);
				binary.setLeftTime(DateUtils.getNowDate());
				return insertUserBinaryTree(binary);
			}
			if (!leftZone && binary.isRightEmpty()) {
				binary.setRightId(userId);
				binary.setRightTime(DateUtils.getNowDate());
				return updateUserBinaryTree(binary);
			} else if (binary.isLeftEmpty()) {
				binary.setLeftId(userId);
				binary.setLeftTime(DateUtils.getNowDate());
				return updateUserBinaryTree(binary);
			}
			return setBinaryUserToLeft(leftZone ? binary.getLeftId() : binary.getRightId(), userId);
		}
	}

	private int setBinaryUserToLeft(Long referrerId, Long leftId) {
		if (referrerId == null || leftId == null) {
			return 0;
		}
		final List<UserBinaryTree> treeList = selectAll();
		Long userId = referrerId;
		UserBinaryTree binary = findBinaryTree(userId, treeList);
		while (binary != null && !binary.isLeftEmpty()) {
			userId = binary.getLeftId();
			binary = findBinaryTree(userId, treeList);
		}
		if (binary == null) {
			binary = new UserBinaryTree();
			binary.setUserId(userId);
			binary.setLeftId(leftId);
			binary.setLeftTime(DateUtils.getNowDate());
			return insertUserBinaryTree(binary);
		}
		binary.setLeftId(leftId);
		binary.setLeftTime(DateUtils.getNowDate());
		return updateUserBinaryTree(binary);
	}

	private boolean isInBinaryTree(long userId) {
		return userBinaryTreeMapper.isInBinaryTree(userId) > 0;
	}

	private UserBinaryTree getBinaryTree(long userId) {
		UserBinaryTree binary = selectUserBinaryTreeById(userId);
		if (binary == null) {
			binary = new UserBinaryTree();
			binary.setUserId(userId);
			insertUserBinaryTree(binary);
		}
		return binary;
	}

	@Override
	public List<UserBinaryTree> selectAll() {
		final int count = userBinaryTreeMapper.queryUserBinaryTreeCount();
		if (count <= 0) {
			return Collections.emptyList();
		}
		final List<UserBinaryTree> result = Collections.synchronizedList(new ArrayList<UserBinaryTree>());
		synchronized (result) {
			final long maxPage = Math.max(1, count / PAGE_SIZE);
			for (int currentPage = 0; currentPage < maxPage; currentPage++) {
				result.addAll(userBinaryTreeMapper.selectUserBinaryTrees(currentPage, PAGE_SIZE));
			}
			return result;
		}
	}

	private UserBinaryTree findBinaryTree(Long userId, List<UserBinaryTree> treeList) {
		if (userId == null || treeList == null || treeList.isEmpty()) {
			return null;
		}
		return treeList.stream().filter(tree -> userId.equals(tree.getUserId())).findFirst().orElse(null);
	}

	@Override
	public List<UserBinaryTree> selectUmbrellaBinaryTreeList(Long userId, List<UserBinaryTree> treeList) {
		if (userId == null || treeList == null || treeList.isEmpty()) {
			return Collections.emptyList();
		}
		final Vector<UserBinaryTree> results = new Vector<UserBinaryTree>();
		final UserBinaryTree root = findBinaryTree(userId, treeList);
		if (root == null) {
			return Collections.emptyList();
		}
		final Stack<UserBinaryTree> stack = new Stack<UserBinaryTree>();
		UserBinaryTree tree = root;
		while (tree != null || !stack.isEmpty()) {
			while (tree != null) {
				stack.push(tree);
				final Long leftId = tree.getLeftId();
				if (leftId != null) {
					tree = findBinaryTree(leftId, treeList);
					if (tree == null) {
						tree = new UserBinaryTree();
						tree.setUserId(leftId);
					}
					results.add(tree);
				} else {
					tree = null;
				}

			}
			if (!stack.isEmpty()) {
				final UserBinaryTree pop = stack.pop();
				final Long rightId = pop.getRightId();
				if (rightId != null) {
					tree = findBinaryTree(rightId, treeList);
					if (tree == null) {
						tree = new UserBinaryTree();
						tree.setUserId(rightId);
					}
					results.add(tree);
				} else {
					tree = null;
				}
			}
		}
		return results;
	}

	@Override
	public List<Long> selectUmbrellaBinaryTreeIds(Long userId, List<UserBinaryTree> treeList) {
		return selectUmbrellaBinaryTreeList(userId, treeList).stream().map(t -> t.getUserId())
				.collect(Collectors.toList());
	}

	@Override
	public UserBinaryTree selectUserBinaryTreeByUserId(Long userId, List<UserBinaryTree> treeList) {
		if (userId == null || treeList == null || treeList.isEmpty()) {
			return null;
		}
		return treeList.stream().filter(t -> userId.equals(t.getUserId())).findFirst().orElse(null);
	}
}
