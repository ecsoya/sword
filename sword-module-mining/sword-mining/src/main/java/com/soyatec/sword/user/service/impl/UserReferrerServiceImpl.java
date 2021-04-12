package com.soyatec.sword.user.service.impl;

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

import com.soyatec.sword.common.core.text.Convert;
import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.common.utils.async.AsyncManager;
import com.soyatec.sword.constants.IMiningConstants;
import com.soyatec.sword.qrcode.QrcodeUtils;
import com.soyatec.sword.system.service.ISysConfigService;
import com.soyatec.sword.user.domain.UserProfile;
import com.soyatec.sword.user.domain.UserReferrer;
import com.soyatec.sword.user.domain.UserReferrerInfo;
import com.soyatec.sword.user.mapper.UserReferrerMapper;
import com.soyatec.sword.user.service.IUserReferrerService;

/**
 * 用户直推Service业务层处理
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
@Service
public class UserReferrerServiceImpl implements IUserReferrerService {

	@Autowired
	private UserReferrerMapper userReferrerMapper;

	@Autowired
	private ISysConfigService configService;

	private String baseUrl;

	@PostConstruct
	public void init() {
		baseUrl = configService.selectConfigValueByKey(IMiningConstants.USER_REFERRAL_LINK_URL);
	}

	/**
	 * 查询用户直推
	 * 
	 * @param userId 用户直推ID
	 * @return 用户直推
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
	 * 查询用户直推列表
	 * 
	 * @param userReferrer 用户直推
	 * @return 用户直推
	 */
	@Override
	public List<UserReferrer> selectUserReferrerList(UserReferrer userReferrer) {
		return userReferrerMapper.selectUserReferrerList(userReferrer);
	}

	/**
	 * 新增用户直推
	 * 
	 * @param userReferrer 用户直推
	 * @return 结果
	 */
	@Override
	public int insertUserReferrer(UserReferrer userReferrer) {
		userReferrer.setCreateTime(DateUtils.getNowDate());
		return userReferrerMapper.insertUserReferrer(userReferrer);
	}

	/**
	 * 修改用户直推
	 * 
	 * @param userReferrer 用户直推
	 * @return 结果
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
	 * 删除用户直推对象
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteUserReferrerByIds(String ids) {
		return userReferrerMapper.deleteUserReferrerByIds(Convert.toStrArray(ids));
	}

	/**
	 * 删除用户直推信息
	 * 
	 * @param userId 用户直推ID
	 * @return 结果
	 */
	@Override
	public int deleteUserReferrerById(Long userId) {
		return userReferrerMapper.deleteUserReferrerById(userId);
	}

	@Override
	public UserReferrer selectUserReferrerByCode(String code) {
		return userReferrerMapper.selectUserReferrerByCode(code);
	}

	@Override
	public List<UserReferrer> selectUnfinishedUserReferrers() {
		UserReferrer query = new UserReferrer();
		query.setBtree(UserReferrer.BTREE_UNFINISHED);
		return selectUserReferrerList(query);
	}

	@Override
	public UserReferrer refreshUserReferrerById(Long userId) {
		return refreshQrcode(selectUserReferrerById(userId), null);
	}

	private UserReferrer refreshQrcode(UserReferrer referrer, Boolean leftOnly) {
		if (referrer == null) {
			return null;
		}
		Long userId = referrer.getUserId();
		String baseUrl = configService.selectConfigValueByKey(IMiningConstants.USER_REFERRAL_LINK_URL);

		UserReferrer update = new UserReferrer();
		update.setUserId(userId);

		// 刷新左区
		String leftCodeUrl = referrer.getLeftCodeUrl();
		String leftCode = referrer.getLeftCode();
		String leftUrl = StringUtils.isEmpty(baseUrl) ? leftCode : baseUrl + leftCode;
		leftCodeUrl = QrcodeUtils.generate(leftUrl);
		referrer.setLeftCodeUrl(leftCodeUrl);
		update.setLeftCodeUrl(leftCodeUrl);

		if (leftOnly == null || Boolean.FALSE.equals(leftOnly)) {
			// 刷新右区
			String rightCodeUrl = referrer.getRightCodeUrl();
			String rightCode = referrer.getRightCode();
			String rightUrl = StringUtils.isEmpty(baseUrl) ? rightCode : baseUrl + rightCode;
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

	@Override
	public List<Long> selectAllUserIds() {
		return userReferrerMapper.selectAllUserIds();
	}

	@Override
	public List<Long> selectReferralUserIdsByUserId(Long userId) {
		if (userId == null) {
			return Collections.emptyList();
		}
		return userReferrerMapper.selectReferralUserIdsByUserId(userId);
	}

	@Override
	public List<UserReferrer> selectUserReferrerListForUpdate(String baseUrl) {
		if (baseUrl == null) {
			baseUrl = "";
		}
		return userReferrerMapper.selectUserReferrerListForUpdate(baseUrl);
	}

	@Override
	public Long selectReferralCountByUserId(Long userId, Date start, Date end) {
		if (userId == null) {
			return null;
		}
		return userReferrerMapper.selectReferralCountByUserId(userId, start, end);
	}

	@Override
	public int forceUpdateReferrerCode(Long userId, boolean left) {
		if (userId == null) {
			return 0;
		}
		String baseUrl = configService.selectConfigValueByKey(IMiningConstants.USER_REFERRAL_LINK_URL);
		UserReferrer referrer = new UserReferrer();
		referrer.setUserId(userId);
		if (left) {
			referrer.generateLeftCode();
			String leftCode = referrer.getLeftCode();
			String leftUrl = StringUtils.isEmpty(baseUrl) ? leftCode : baseUrl + leftCode;
			referrer.setLeftCodeUrl(QrcodeUtils.generate(leftUrl));
		} else {
			referrer.generateRightCode();
			String rightCode = referrer.getRightCode();
			String rightUrl = StringUtils.isEmpty(baseUrl) ? rightCode : baseUrl + rightCode;
			referrer.setRightCodeUrl(QrcodeUtils.generate(rightUrl));
		}

		return updateUserReferrer(referrer);
	}

	@Override
	public void updateAllQrcodeCode() {
		String baseUrl = configService.selectConfigValueByKey(IMiningConstants.USER_REFERRAL_LINK_URL);
		List<UserReferrer> list = selectUserReferrerList(new UserReferrer());
		list.parallelStream().forEach(user -> {
			UserReferrer referrer = new UserReferrer();
			referrer.setUserId(user.getUserId());
			if (StringUtils.isEmpty(user.getLeftCode())) {
				referrer.generateLeftCode();
			}
			String leftCode = referrer.getLeftCode();
			String leftUrl = StringUtils.isEmpty(baseUrl) ? leftCode : baseUrl + leftCode;
			referrer.setLeftCodeUrl(QrcodeUtils.generate(leftUrl));
			if (StringUtils.isEmpty(user.getRightCode())) {
				referrer.generateRightCode();
			}
			String rightCode = referrer.getRightCode();
			String rightUrl = StringUtils.isEmpty(baseUrl) ? rightCode : baseUrl + rightCode;
			referrer.setRightCodeUrl(QrcodeUtils.generate(rightUrl));

			referrer.setBaseUrl(baseUrl);

			updateUserReferrer(referrer);
		});
	}

	@Override
	public Integer computeRightCodeEnabled(Long userId) {
		if (userId == null) {
			return UserReferrer.DISABLED;
		}
		UserReferrer referrer = selectUserReferrerById(userId);
		if (referrer == null || UserReferrer.DISABLED.equals(referrer.getLeftCodeEnabled())) {
			return UserReferrer.DISABLED;
		}
		String leftCode = referrer.getLeftCode();
		UserReferrer query = new UserReferrer();
		query.setReferralCode(leftCode);
		List<UserReferrer> list = selectUserReferrerList(query);
		if (!list.isEmpty()) {
			return UserReferrer.ENABLED;
		}
		return UserReferrer.DISABLED;
	}

	@Override
	public Integer computeLeftCodeEnabled(Long userId) {
		if (userId == null) {
			return UserReferrer.DISABLED;
		}
		return UserReferrer.ENABLED;
	}

	@Override
	public int updateUserReferrerCodeEnabled(Long userId) {
		if (userId == null) {
			return 0;
		}
		UserReferrer referrer = selectUserReferrerById(userId);
		if (referrer == null) {
			return 0;
		}
		UserReferrer update = new UserReferrer();
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

	@Override
	public List<UserReferrer> selectAll() {
		return selectUserReferrerList(new UserReferrer());
	}

	@Override
	public List<Long> selectUmbrellaUserIds(Long userId, List<UserReferrer> allUsers) {
		if (userId == null || allUsers == null || allUsers.isEmpty()) {
			return Collections.emptyList();
		}
		Vector<Long> results = new Vector<>();
		UserReferrer root = findUserReferrer(userId, allUsers);
		if (root == null) {
			return Collections.emptyList();
		}
		Stack<UserReferrer> stack = new Stack<UserReferrer>();
		stack.add(root);
		UserReferrer tree;
		while (!stack.isEmpty()) {
			tree = stack.pop();
			List<UserReferrer> list = findReferralList(tree.getUserId(), allUsers);
			list.forEach(user -> {
				stack.add(user);
				Long id = user.getUserId();
				if (!results.contains(id)) {
					results.add(id);
				}
			});
		}
		return results;
	}

	private UserReferrer findUserReferrer(Long userId, List<UserReferrer> allUsers) {
		if (userId == null || allUsers == null || allUsers.isEmpty()) {
			return null;
		}
		return allUsers.stream().filter(u -> userId.equals(u.getUserId())).findFirst().orElse(null);
	}

	@Override
	public List<Long> selectUmbrellaUserIdsDepthFirstly(Long userId, List<UserReferrer> allUsers) {
		if (userId == null || allUsers == null || allUsers.isEmpty()) {
			return Collections.emptyList();
		}
		Vector<Long> results = new Vector<>();
		List<UserReferrer> referrers = findReferralList(userId, allUsers);
		for (UserReferrer userReferrer : referrers) {
			Long id = userReferrer.getUserId();
			results.add(id);
			results.addAll(selectUmbrellaUserIds(id, allUsers));
		}
		return results;
	}

	private List<UserReferrer> findReferralList(Long userId, List<UserReferrer> allUsers) {
		if (userId == null || allUsers == null || allUsers.isEmpty()) {
			return Collections.emptyList();
		}
		return allUsers.stream().filter(u -> userId.equals(u.getReferralId())).collect(Collectors.toList());
	}

	@Override
	public List<UserProfile> selectUserReferrerListByUserId(Long userId) {
		if (userId == null) {
			return Collections.emptyList();
		}
		return userReferrerMapper.selectUserReferrerListByUserId(userId);
	}

	@Override
	public List<UserReferrerInfo> selectUserReferrerInfoList(UserReferrerInfo query) {
		return userReferrerMapper.selectUserReferrerInfoList(query);
	}
}
