package com.github.ecsoya.sword.user.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.async.AsyncManager;
import com.github.ecsoya.sword.constants.IMiningConstants;
import com.github.ecsoya.sword.qrcode.QrcodeUtils;
import com.github.ecsoya.sword.system.service.ISysConfigService;
import com.github.ecsoya.sword.user.domain.UserProfile;
import com.github.ecsoya.sword.user.domain.UserReferrer;
import com.github.ecsoya.sword.user.domain.UserReferrerInfo;
import com.github.ecsoya.sword.user.mapper.UserReferrerMapper;
import com.github.ecsoya.sword.user.service.IUserReferrerService;

/**
 * The Class UserReferrerServiceImpl.
 */
@Service
public class UserReferrerServiceImpl implements IUserReferrerService {

	/** The user referrer mapper. */
	@Autowired
	private UserReferrerMapper userReferrerMapper;

	/** The config service. */
	@Autowired
	private ISysConfigService configService;

	/** The base url. */
	private String baseUrl;

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		baseUrl = configService.selectConfigValueByKey(IMiningConstants.USER_REFERRAL_LINK_URL);
	}

	/**
	 * Select user referrer by id.
	 *
	 * @param userId the user id
	 * @return the user referrer
	 */
	@Override
	public UserReferrer selectUserReferrerById(Long userId) {
		UserReferrer userReferrer = userReferrerMapper.selectUserReferrerById(userId);
		if (userReferrer == null) {
			userReferrer = new UserReferrer();
			userReferrer.generateLeftCode();
			userReferrer.generateRightCode();
			userReferrer.setUserId(userId);
			insertUserReferrer(userReferrer);
		} else if (StringUtils.isEmpty(userReferrer.getLeftCodeUrl())
				|| StringUtils.isEmpty(userReferrer.getRightCodeUrl())
				|| !Objects.equals(userReferrer.getBaseUrl(), baseUrl)) {
			return refreshQrcode(userReferrer, null);
		}
		return userReferrer;
	}

	/**
	 * Select user referrer list.
	 *
	 * @param userReferrer the user referrer
	 * @return the list
	 */
	@Override
	public List<UserReferrer> selectUserReferrerList(UserReferrer userReferrer) {
		return userReferrerMapper.selectUserReferrerList(userReferrer);
	}

	/**
	 * Insert user referrer.
	 *
	 * @param userReferrer the user referrer
	 * @return the int
	 */
	@Override
	public int insertUserReferrer(UserReferrer userReferrer) {
		userReferrer.setCreateTime(DateUtils.getNowDate());
		return userReferrerMapper.insertUserReferrer(userReferrer);
	}

	/**
	 * Update user referrer.
	 *
	 * @param userReferrer the user referrer
	 * @return the int
	 */
	@Override
	public int updateUserReferrer(UserReferrer userReferrer) {
		if (userReferrer.getUserId() == null) {
			return 0;
		}
		userReferrer.setUpdateTime(DateUtils.getNowDate());
		return userReferrerMapper.updateUserReferrer(userReferrer);
	}

	/**
	 * Delete user referrer by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteUserReferrerByIds(String ids) {
		return userReferrerMapper.deleteUserReferrerByIds(Convert.toStrArray(ids));
	}

	/**
	 * Delete user referrer by id.
	 *
	 * @param userId the user id
	 * @return the int
	 */
	@Override
	public int deleteUserReferrerById(Long userId) {
		return userReferrerMapper.deleteUserReferrerById(userId);
	}

	/**
	 * Select user referrer by code.
	 *
	 * @param code the code
	 * @return the user referrer
	 */
	@Override
	public UserReferrer selectUserReferrerByCode(String code) {
		return userReferrerMapper.selectUserReferrerByCode(code);
	}

	/**
	 * Select unfinished user referrers.
	 *
	 * @return the list
	 */
	@Override
	public List<UserReferrer> selectUnfinishedUserReferrers() {
		final UserReferrer query = new UserReferrer();
		query.setBtree(UserReferrer.BTREE_UNFINISHED);
		return selectUserReferrerList(query);
	}

	/**
	 * Refresh user referrer by id.
	 *
	 * @param userId the user id
	 * @return the user referrer
	 */
	@Override
	public UserReferrer refreshUserReferrerById(Long userId) {
		return refreshQrcode(selectUserReferrerById(userId), null);
	}

	/**
	 * Refresh qrcode.
	 *
	 * @param referrer the referrer
	 * @param leftOnly the left only
	 * @return the user referrer
	 */
	private UserReferrer refreshQrcode(UserReferrer referrer, Boolean leftOnly) {
		if (referrer == null) {
			return null;
		}
		final Long userId = referrer.getUserId();
		final String baseUrl = configService.selectConfigValueByKey(IMiningConstants.USER_REFERRAL_LINK_URL);

		final UserReferrer update = new UserReferrer();
		update.setUserId(userId);

		// 刷新左区
		String leftCodeUrl = referrer.getLeftCodeUrl();
		final String leftCode = referrer.getLeftCode();
		final String leftUrl = StringUtils.isEmpty(baseUrl) ? leftCode : baseUrl + leftCode;
		leftCodeUrl = QrcodeUtils.generate(leftUrl);
		referrer.setLeftCodeUrl(leftCodeUrl);
		update.setLeftCodeUrl(leftCodeUrl);

		if (leftOnly == null || Boolean.FALSE.equals(leftOnly)) {
			// 刷新右区
			String rightCodeUrl = referrer.getRightCodeUrl();
			final String rightCode = referrer.getRightCode();
			final String rightUrl = StringUtils.isEmpty(baseUrl) ? rightCode : baseUrl + rightCode;
			rightCodeUrl = QrcodeUtils.generate(rightUrl);
			referrer.setRightCodeUrl(rightCodeUrl);
			update.setRightCodeUrl(rightCodeUrl);
		}

		update.setBaseUrl(baseUrl);
		referrer.setBaseUrl(baseUrl);

		AsyncManager.me().execute(new Runnable() {

			@Override
			public void run() {
				updateUserReferrer(update);
			}
		});

		return referrer;
	}

	/**
	 * Select all user ids.
	 *
	 * @return the list
	 */
	@Override
	public List<Long> selectAllUserIds() {
		return userReferrerMapper.selectAllUserIds();
	}

	/**
	 * Select referral user ids by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	@Override
	public List<Long> selectReferralUserIdsByUserId(Long userId) {
		if (userId == null) {
			return Collections.emptyList();
		}
		return userReferrerMapper.selectReferralUserIdsByUserId(userId);
	}

	/**
	 * Select user referrer list for update.
	 *
	 * @param baseUrl the base url
	 * @return the list
	 */
	@Override
	public List<UserReferrer> selectUserReferrerListForUpdate(String baseUrl) {
		if (baseUrl == null) {
			baseUrl = "";
		}
		return userReferrerMapper.selectUserReferrerListForUpdate(baseUrl);
	}

	/**
	 * Select referral count by user id.
	 *
	 * @param userId the user id
	 * @param start  the start
	 * @param end    the end
	 * @return the long
	 */
	@Override
	public Long selectReferralCountByUserId(Long userId, Date start, Date end) {
		if (userId == null) {
			return null;
		}
		return userReferrerMapper.selectReferralCountByUserId(userId, start, end);
	}

	/**
	 * Force update referrer code.
	 *
	 * @param userId the user id
	 * @param left   the left
	 * @return the int
	 */
	@Override
	public int forceUpdateReferrerCode(Long userId, boolean left) {
		if (userId == null) {
			return 0;
		}
		final String baseUrl = configService.selectConfigValueByKey(IMiningConstants.USER_REFERRAL_LINK_URL);
		final UserReferrer referrer = new UserReferrer();
		referrer.setUserId(userId);
		if (left) {
			referrer.generateLeftCode();
			final String leftCode = referrer.getLeftCode();
			final String leftUrl = StringUtils.isEmpty(baseUrl) ? leftCode : baseUrl + leftCode;
			referrer.setLeftCodeUrl(QrcodeUtils.generate(leftUrl));
		} else {
			referrer.generateRightCode();
			final String rightCode = referrer.getRightCode();
			final String rightUrl = StringUtils.isEmpty(baseUrl) ? rightCode : baseUrl + rightCode;
			referrer.setRightCodeUrl(QrcodeUtils.generate(rightUrl));
		}

		return updateUserReferrer(referrer);
	}

	/**
	 * Update all qrcode code.
	 */
	@Override
	public void updateAllQrcodeCode() {
		final String baseUrl = configService.selectConfigValueByKey(IMiningConstants.USER_REFERRAL_LINK_URL);
		final List<UserReferrer> list = selectUserReferrerList(new UserReferrer());
		list.parallelStream().forEach(user -> {
			final UserReferrer referrer = new UserReferrer();
			referrer.setUserId(user.getUserId());
			if (StringUtils.isEmpty(user.getLeftCode())) {
				referrer.generateLeftCode();
			}
			final String leftCode = referrer.getLeftCode();
			final String leftUrl = StringUtils.isEmpty(baseUrl) ? leftCode : baseUrl + leftCode;
			referrer.setLeftCodeUrl(QrcodeUtils.generate(leftUrl));
			if (StringUtils.isEmpty(user.getRightCode())) {
				referrer.generateRightCode();
			}
			final String rightCode = referrer.getRightCode();
			final String rightUrl = StringUtils.isEmpty(baseUrl) ? rightCode : baseUrl + rightCode;
			referrer.setRightCodeUrl(QrcodeUtils.generate(rightUrl));

			referrer.setBaseUrl(baseUrl);

			updateUserReferrer(referrer);
		});
	}

	/**
	 * Compute right code enabled.
	 *
	 * @param userId the user id
	 * @return the integer
	 */
	@Override
	public Integer computeRightCodeEnabled(Long userId) {
		if (userId == null) {
			return UserReferrer.DISABLED;
		}
		final UserReferrer referrer = selectUserReferrerById(userId);
		if (referrer == null || UserReferrer.DISABLED.equals(referrer.getLeftCodeEnabled())) {
			return UserReferrer.DISABLED;
		}
		final String leftCode = referrer.getLeftCode();
		final UserReferrer query = new UserReferrer();
		query.setReferralCode(leftCode);
		final List<UserReferrer> list = selectUserReferrerList(query);
		if (!list.isEmpty()) {
			return UserReferrer.ENABLED;
		}
		return UserReferrer.DISABLED;
	}

	/**
	 * Compute left code enabled.
	 *
	 * @param userId the user id
	 * @return the integer
	 */
	@Override
	public Integer computeLeftCodeEnabled(Long userId) {
		if (userId == null) {
			return UserReferrer.DISABLED;
		}
		return UserReferrer.ENABLED;
	}

	/**
	 * Update user referrer code enabled.
	 *
	 * @param userId the user id
	 * @return the int
	 */
	@Override
	public int updateUserReferrerCodeEnabled(Long userId) {
		if (userId == null) {
			return 0;
		}
		final UserReferrer referrer = selectUserReferrerById(userId);
		if (referrer == null) {
			return 0;
		}
		final UserReferrer update = new UserReferrer();
		if (UserReferrer.DISABLED.equals(referrer.getLeftCodeEnabled())) {
			update.setUserId(userId);
			update.setLeftCodeEnabled(computeLeftCodeEnabled(userId));
			update.setRightCodeEnabled(computeRightCodeEnabled(userId));
		}
		if (UserReferrer.DISABLED.equals(referrer.getRightCodeEnabled())) {
			update.setUserId(userId);
			update.setRightCodeEnabled(computeRightCodeEnabled(userId));
		}
		return updateUserReferrer(update);
	}

	/**
	 * Select all.
	 *
	 * @return the list
	 */
	@Override
	public List<UserReferrer> selectAll() {
		return selectUserReferrerList(new UserReferrer());
	}

	/**
	 * Select umbrella user ids.
	 *
	 * @param userId   the user id
	 * @param allUsers the all users
	 * @return the list
	 */
	@Override
	public List<Long> selectUmbrellaUserIds(Long userId, List<UserReferrer> allUsers) {
		if (userId == null || allUsers == null || allUsers.isEmpty()) {
			return Collections.emptyList();
		}
		final Vector<Long> results = new Vector<>();
		final UserReferrer root = findUserReferrer(userId, allUsers);
		if (root == null) {
			return Collections.emptyList();
		}
		final Stack<UserReferrer> stack = new Stack<UserReferrer>();
		stack.add(root);
		UserReferrer tree;
		while (!stack.isEmpty()) {
			tree = stack.pop();
			final List<UserReferrer> list = findReferralList(tree.getUserId(), allUsers);
			list.forEach(user -> {
				stack.add(user);
				final Long id = user.getUserId();
				if (!results.contains(id)) {
					results.add(id);
				}
			});
		}
		return results;
	}

	/**
	 * Find user referrer.
	 *
	 * @param userId   the user id
	 * @param allUsers the all users
	 * @return the user referrer
	 */
	private UserReferrer findUserReferrer(Long userId, List<UserReferrer> allUsers) {
		if (userId == null || allUsers == null || allUsers.isEmpty()) {
			return null;
		}
		return allUsers.stream().filter(u -> userId.equals(u.getUserId())).findFirst().orElse(null);
	}

	/**
	 * Select umbrella user ids depth firstly.
	 *
	 * @param userId   the user id
	 * @param allUsers the all users
	 * @return the list
	 */
	@Override
	public List<Long> selectUmbrellaUserIdsDepthFirstly(Long userId, List<UserReferrer> allUsers) {
		if (userId == null || allUsers == null || allUsers.isEmpty()) {
			return Collections.emptyList();
		}
		final Vector<Long> results = new Vector<>();
		final List<UserReferrer> referrers = findReferralList(userId, allUsers);
		for (final UserReferrer userReferrer : referrers) {
			final Long id = userReferrer.getUserId();
			results.add(id);
			results.addAll(selectUmbrellaUserIds(id, allUsers));
		}
		return results;
	}

	/**
	 * Find referral list.
	 *
	 * @param userId   the user id
	 * @param allUsers the all users
	 * @return the list
	 */
	private List<UserReferrer> findReferralList(Long userId, List<UserReferrer> allUsers) {
		if (userId == null || allUsers == null || allUsers.isEmpty()) {
			return Collections.emptyList();
		}
		return allUsers.stream().filter(u -> userId.equals(u.getReferralId())).collect(Collectors.toList());
	}

	/**
	 * Select user referrer list by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	@Override
	public List<UserProfile> selectUserReferrerListByUserId(Long userId) {
		if (userId == null) {
			return Collections.emptyList();
		}
		return userReferrerMapper.selectUserReferrerListByUserId(userId);
	}

	/**
	 * Select user referrer info list.
	 *
	 * @param query the query
	 * @return the list
	 */
	@Override
	public List<UserReferrerInfo> selectUserReferrerInfoList(UserReferrerInfo query) {
		return userReferrerMapper.selectUserReferrerInfoList(query);
	}
}
