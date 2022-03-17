package com.github.ecsoya.sword.mining.mapper;

import java.util.List;

import com.github.ecsoya.sword.mining.domain.MiningUser;
import com.github.ecsoya.sword.report.domain.DateCount;

/**
 * Mining级别Mapper接口
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-03-15
 */
public interface MiningUserMapper {

	List<MiningUser> selectMiningUserList(MiningUser query);

	List<DateCount> selectRegisterUserCountsByDate();
}
