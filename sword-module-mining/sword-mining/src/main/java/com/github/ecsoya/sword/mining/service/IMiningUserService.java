package com.github.ecsoya.sword.mining.service;

import java.util.List;

import com.github.ecsoya.sword.mining.domain.MiningUser;
import com.github.ecsoya.sword.report.domain.DateCount;

public interface IMiningUserService {

	List<MiningUser> selectMiningUserList(MiningUser query);

	List<DateCount> selectRegisterUserCountsByDate();
}
