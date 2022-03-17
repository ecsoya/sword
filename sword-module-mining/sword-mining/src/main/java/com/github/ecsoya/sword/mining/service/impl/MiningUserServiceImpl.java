package com.github.ecsoya.sword.mining.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.constants.IMiningConstants;
import com.github.ecsoya.sword.mining.domain.MiningUser;
import com.github.ecsoya.sword.mining.mapper.MiningUserMapper;
import com.github.ecsoya.sword.mining.service.IMiningUserService;
import com.github.ecsoya.sword.report.domain.DateCount;
import com.github.ecsoya.sword.system.service.ISysConfigService;
import com.github.ecsoya.sword.utils.MathUtils;
import com.github.ecsoya.sword.wallet.service.IUserWalletAccountService;

@Service
public class MiningUserServiceImpl implements IMiningUserService {

	@Autowired
	private ISysConfigService configService;

	@Autowired
	private MiningUserMapper miningUserMapper;

	@Autowired
	private IUserWalletAccountService accountService;

	@Override
	public List<MiningUser> selectMiningUserList(MiningUser query) {
		if (query == null) {
			query = new MiningUser();
		}
		final Long parentId = query.getParentId();
		if (parentId == null
				&& (StringUtils.isEmpty(query.getLoginName()) && StringUtils.isEmpty(query.getMobile())
						&& StringUtils.isEmpty(query.getEmail()) && query.getLevelId() == null
						&& StringUtils.isEmpty(query.getParams().get("endTime")))
				&& StringUtils.isEmpty(query.getParams().get("startTime"))) {
			query.setParentId(MathUtils.parseLong(configService.selectConfigValueByKey(IMiningConstants.ROOT_USER_ID)));
		}
		final List<MiningUser> list = miningUserMapper.selectMiningUserList(query);
		list.parallelStream().forEach(user -> {
			user.setAccounts(accountService.selectUserWalletAccountListByUserId(user.getUserId()));
		});
		return list;
	}

	@Override
	public List<DateCount> selectRegisterUserCountsByDate() {
		return miningUserMapper.selectRegisterUserCountsByDate();
	}

}
