package com.soyatec.sword.mining.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.constants.IMiningConstants;
import com.soyatec.sword.mining.domain.MiningUser;
import com.soyatec.sword.mining.mapper.MiningUserMapper;
import com.soyatec.sword.mining.service.IMiningUserService;
import com.soyatec.sword.report.domain.DateCount;
import com.soyatec.sword.system.service.ISysConfigService;
import com.soyatec.sword.utils.MathUtils;
import com.soyatec.sword.wallet.service.IUserWalletAccountService;

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
		Long parentId = query.getParentId();
		if (parentId == null
				&& (StringUtils.isEmpty(query.getLoginName()) && StringUtils.isEmpty(query.getMobile())
						&& StringUtils.isEmpty(query.getEmail()) && query.getLevelId() == null
						&& StringUtils.isEmpty(query.getParams().get("endTime")))
				&& StringUtils.isEmpty(query.getParams().get("startTime"))) {
			query.setParentId(MathUtils.parseLong(configService.selectConfigValueByKey(IMiningConstants.ROOT_USER_ID)));
		}
		List<MiningUser> list = miningUserMapper.selectMiningUserList(query);
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
