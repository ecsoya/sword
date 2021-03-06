package com.github.ecsoya.sword.user.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.Vector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.service.ILockService;
import com.github.ecsoya.sword.user.domain.UserBinaryTree;
import com.github.ecsoya.sword.user.domain.UserReferrer;
import com.github.ecsoya.sword.user.mapper.UserBinaryTreeMapper;
import com.github.ecsoya.sword.user.service.IUserBinaryTreeService;
import com.github.ecsoya.sword.user.service.IUserReferrerService;

/**
 * The Class UserBinaryTreeServiceImpl.
 */
@Service
public class UserBinaryTreeServiceImpl implements IUserBinaryTreeService {

	/** The Constant PAGE_SIZE. */
	private static final int PAGE_SIZE = 10000;

	/** The user binary tree mapper. */
	@Autowired
	private UserBinaryTreeMapper userBinaryTreeMapper;

	/** The Constant LOCK_KEY. */
	private static final String LOCK_KEY = "binaryTreeLock";

	/** The lock service. */
	@Autowired
	private ILockService lockService;

	/** The referrer service. */
	@Autowired
	private IUserReferrerService referrerService;

	/**
	 * Select user binary tree by id.
	 *
	 * @param userId the user id
	 * @return the user binary tree
	 */
	@Override
	public UserBinaryTree selectUserBinaryTreeById(Long userId) {
		return userBinaryTreeMapper.selectUserBinaryTreeById(userId);
	}

	/**
	 * Select user binary tree list.
	 *
	 * @param userBinaryTree the user binary tree
	 * @return the list
	 */
	@Override
	public List<UserBinaryTree> selectUserBinaryTreeList(UserBinaryTree userBinaryTree) {
		return userBinaryTreeMapper.selectUserBinaryTreeList(userBinaryTree);
	}

	/**
	 * Insert user binary tree.
	 *
	 * @param userBinaryTree the user binary tree
	 * @return the int
	 */
	@Override
	public int insertUserBinaryTree(UserBinaryTree userBinaryTree) {
		userBinaryTree.setCreateTime(DateUtils.getNowDate());
		return userBinaryTreeMapper.insertUserBinaryTree(userBinaryTree);
	}

	/**
	 * Update user binary tree.
	 *
	 * @param userBinaryTree the user binary tree
	 * @return the int
	 */
	@Override
	public int updateUserBinaryTree(UserBinaryTree userBinaryTree) {
		userBinaryTree.setUpdateTime(DateUtils.getNowDate());
		return userBinaryTreeMapper.updateUserBinaryTree(userBinaryTree);
	}

	/**
	 * Delete user binary tree by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteUserBinaryTreeByIds(String ids) {
		return userBinaryTreeMapper.deleteUserBinaryTreeByIds(Convert.toStrArray(ids));
	}

	/**
	 * Delete user binary tree by id.
	 *
	 * @param userId the user id
	 * @return the int
	 */
	@Override
	public int deleteUserBinaryTreeById(Long userId) {
		return userBinaryTreeMapper.deleteUserBinaryTreeById(userId);
	}

	/**
	 * Update user binary trees.
	 *
	 * @return the int
	 */
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
					// ??????????????????????????????????????????
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

	/**
	 * Adds the binary tree.
	 *
	 * @param referralId the referral id
	 * @param userId     the user id
	 * @param leftZone   the left zone
	 * @return the int
	 */
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

	/**
	 * Sets the binary user to left.
	 *
	 * @param referrerId the referrer id
	 * @param leftId     the left id
	 * @return the int
	 */
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

	/**
	 * Checks if is in binary tree.
	 *
	 * @param userId the user id
	 * @return true, if is in binary tree
	 */
	private boolean isInBinaryTree(long userId) {
		return userBinaryTreeMapper.isInBinaryTree(userId) > 0;
	}

	/**
	 * Gets the binary tree.
	 *
	 * @param userId the user id
	 * @return the binary tree
	 */
	private UserBinaryTree getBinaryTree(long userId) {
		UserBinaryTree binary = selectUserBinaryTreeById(userId);
		if (binary == null) {
			binary = new UserBinaryTree();
			binary.setUserId(userId);
			insertUserBinaryTree(binary);
		}
		return binary;
	}

	/**
	 * Select all.
	 *
	 * @return the list
	 */
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

	/**
	 * Find binary tree.
	 *
	 * @param userId   the user id
	 * @param treeList the tree list
	 * @return the user binary tree
	 */
	private UserBinaryTree findBinaryTree(Long userId, List<UserBinaryTree> treeList) {
		if (userId == null || treeList == null || treeList.isEmpty()) {
			return null;
		}
		return treeList.stream().filter(tree -> userId.equals(tree.getUserId())).findFirst().orElse(null);
	}

	/**
	 * Select umbrella binary tree list.
	 *
	 * @param userId   the user id
	 * @param treeList the tree list
	 * @return the list
	 */
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

	/**
	 * Select umbrella binary tree ids.
	 *
	 * @param userId   the user id
	 * @param treeList the tree list
	 * @return the list
	 */
	@Override
	public List<Long> selectUmbrellaBinaryTreeIds(Long userId, List<UserBinaryTree> treeList) {
		return selectUmbrellaBinaryTreeList(userId, treeList).stream().map(t -> t.getUserId())
				.collect(Collectors.toList());
	}

	/**
	 * Select user binary tree by user id.
	 *
	 * @param userId   the user id
	 * @param treeList the tree list
	 * @return the user binary tree
	 */
	@Override
	public UserBinaryTree selectUserBinaryTreeByUserId(Long userId, List<UserBinaryTree> treeList) {
		if (userId == null || treeList == null || treeList.isEmpty()) {
			return null;
		}
		return treeList.stream().filter(t -> userId.equals(t.getUserId())).findFirst().orElse(null);
	}
}
