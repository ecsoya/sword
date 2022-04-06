package com.github.ecsoya.sword.wallet.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.ecsoya.sword.wallet.domain.UserWalletUnionRecord;

/**
 * The Interface UserWalletUnionRecordMapper.
 */
public interface UserWalletUnionRecordMapper {

	/**
	 * Select wallet record list by user id.
	 *
	 * @param userId the user id
	 * @param symbol the symbol
	 * @param start  the start
	 * @param end    the end
	 * @param kind   the kind
	 * @return the list
	 */
	public List<UserWalletUnionRecord> selectWalletRecordListByUserId(@Param("userId") Long userId,
			@Param("symbol") String symbol, @Param("start") Date start, @Param("end") Date end,
			@Param("kind") Integer kind);

	/**
	 * Select wallet order list by user id.
	 *
	 * @param userId the user id
	 * @param symbol the symbol
	 * @param start  the start
	 * @param end    the end
	 * @param kind   the kind
	 * @return the list
	 */
	public List<UserWalletUnionRecord> selectWalletOrderListByUserId(@Param("userId") Long userId,
			@Param("symbol") String symbol, @Param("start") Date start, @Param("end") Date end,
			@Param("kind") Integer kind);
}
