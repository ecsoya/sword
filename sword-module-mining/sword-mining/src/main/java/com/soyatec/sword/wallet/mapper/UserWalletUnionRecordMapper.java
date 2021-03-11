package com.soyatec.sword.wallet.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.soyatec.sword.wallet.domain.UserWalletUnionRecord;

/**
 * 钱包记录，联合查询Mapper接口
 * 
 * @author King Crab
 * @date 2020-12-18
 */
public interface UserWalletUnionRecordMapper {

	public List<UserWalletUnionRecord> selectWalletRecordListByUserId(@Param("userId") Long userId,
			@Param("symbol") String symbol, @Param("start") Date start, @Param("end") Date end,
			@Param("kind") Integer kind);
}
