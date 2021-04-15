package com.soyatec.sword.wallet.service;

import java.util.Date;
import java.util.List;

import com.soyatec.sword.wallet.domain.UserWalletUnionRecord;

/**
 * 钱包记录，联合查询Service接口
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2020-12-18
 */
public interface IUserWalletUnionRecordService {

	/**
	 * 仅查询成功的充值和提币记录
	 */
	public List<UserWalletUnionRecord> selectWalletRecordListByUserId(Long userId, String symbol, Date start, Date end,
			Integer kind);

	/**
	 * 查询所有的充值和提币订单
	 */
	public List<UserWalletUnionRecord> selectWalletOrderListByUserId(Long userId, String symbol, Date start, Date end,
			Integer kind);
}
