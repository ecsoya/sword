package com.github.ecsoya.sword.wallet.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.wallet.domain.UserWalletUnionRecord;
import com.github.ecsoya.sword.wallet.mapper.UserWalletUnionRecordMapper;
import com.github.ecsoya.sword.wallet.service.IUserWalletUnionRecordService;

/**
 * The Class UserWalletUnionRecordServiceImpl.
 */
@Service
public class UserWalletUnionRecordServiceImpl implements IUserWalletUnionRecordService {

	/** The mapper. */
	@Autowired
	private UserWalletUnionRecordMapper mapper;

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
	@Override
	public List<UserWalletUnionRecord> selectWalletRecordListByUserId(Long userId, String symbol, Date start, Date end,
			Integer kind) {
		return mapper.selectWalletRecordListByUserId(userId, symbol, start, end, kind);
	}

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
	@Override
	public List<UserWalletUnionRecord> selectWalletOrderListByUserId(Long userId, String symbol, Date start, Date end,
			Integer kind) {
		return mapper.selectWalletOrderListByUserId(userId, symbol, start, end, kind);
	}

}
