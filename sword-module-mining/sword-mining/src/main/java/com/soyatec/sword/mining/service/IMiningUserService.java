package com.soyatec.sword.mining.service;

import java.util.List;

import com.soyatec.sword.mining.domain.MiningUser;
import com.soyatec.sword.report.domain.DateCount;

public interface IMiningUserService {

	List<MiningUser> selectMiningUserList(MiningUser query);

	List<DateCount> selectRegisterUserCountsByDate();
}
