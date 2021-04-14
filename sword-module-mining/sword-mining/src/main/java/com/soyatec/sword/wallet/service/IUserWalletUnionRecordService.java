package com.soyatec.sword.wallet.service;

import java.util.Date;
import java.util.List;

import com.soyatec.sword.wallet.domain.UserWalletUnionRecord;

/**
 * 钱包记录，联合查询Service接口
 *
 * @author King Crab
 * @date 2020-12-18
 */
public interface IUserWalletUnionRecordService {

	public List<UserWalletUnionRecord> selectWalletRecordListByUserId(Long userId, String symbol, Date start, Date end,
			Integer kind);
}
