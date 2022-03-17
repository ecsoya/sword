package com.github.ecsoya.sword.wallet.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.ecsoya.sword.wallet.domain.UserWalletUnionRecord;

/**
 * 钱包记录，联合查询Mapper接口
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2020-12-18
 */
public interface UserWalletUnionRecordMapper {

	public List<UserWalletUnionRecord> selectWalletRecordListByUserId(@Param("userId") Long userId,
			@Param("symbol") String symbol, @Param("start") Date start, @Param("end") Date end,
			@Param("kind") Integer kind);

	public List<UserWalletUnionRecord> selectWalletOrderListByUserId(@Param("userId") Long userId,
			@Param("symbol") String symbol, @Param("start") Date start, @Param("end") Date end,
			@Param("kind") Integer kind);
}
