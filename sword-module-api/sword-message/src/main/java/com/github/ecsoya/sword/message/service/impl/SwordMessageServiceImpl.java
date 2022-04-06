package com.github.ecsoya.sword.message.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.IdWorker;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.message.IMessageType;
import com.github.ecsoya.sword.message.domain.SwordMessage;
import com.github.ecsoya.sword.message.mapper.SwordMessageMapper;
import com.github.ecsoya.sword.message.service.ISwordMessageService;
import com.github.ecsoya.sword.message.service.IUserMessageService;

/**
 * The Class SwordMessageServiceImpl.
 */
@Service
public class SwordMessageServiceImpl implements ISwordMessageService {

	/** The sword message mapper. */
	@Autowired
	private SwordMessageMapper swordMessageMapper;

	/** The user message service. */
	@Autowired
	private IUserMessageService userMessageService;

	/**
	 * Select sword message by id.
	 *
	 * @param id the id
	 * @return the sword message
	 */
	@Override
	public SwordMessage selectSwordMessageById(Long id) {
		return swordMessageMapper.selectSwordMessageById(id);
	}

	/**
	 * Select sword message list.
	 *
	 * @param swordMessage the sword message
	 * @return the list
	 */
	@Override
	public List<SwordMessage> selectSwordMessageList(SwordMessage swordMessage) {
		return swordMessageMapper.selectSwordMessageList(swordMessage);
	}

	/**
	 * Insert sword message.
	 *
	 * @param swordMessage the sword message
	 * @return the int
	 */
	@Override
	public int insertSwordMessage(SwordMessage swordMessage) {
		if (swordMessage.getId() == null) {
			swordMessage.setId(IdWorker.getId());
		}
		if (swordMessage.getCreateTime() == null) {
			swordMessage.setCreateTime(DateUtils.getNowDate());
		}
		return swordMessageMapper.insertSwordMessage(swordMessage);
	}

	/**
	 * Update sword message.
	 *
	 * @param swordMessage the sword message
	 * @return the int
	 */
	@Override
	public int updateSwordMessage(SwordMessage swordMessage) {
		swordMessage.setUpdateTime(DateUtils.getNowDate());
		return swordMessageMapper.updateSwordMessage(swordMessage);
	}

	/**
	 * Delete sword message by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteSwordMessageByIds(String ids) {
		return swordMessageMapper.deleteSwordMessageByIds(Convert.toStrArray(ids));
	}

	/**
	 * Delete sword message by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Override
	public int deleteSwordMessageById(Long id) {
		return swordMessageMapper.deleteSwordMessageById(id);
	}

	/**
	 * Publish sword message.
	 *
	 * @param swordMessage the sword message
	 * @param userIds      the user ids
	 * @return the common result
	 */
	@Override
	public CommonResult<?> publishSwordMessage(SwordMessage swordMessage, String userIds) {
		if (swordMessage == null) {
			return CommonResult.fail("参数错误");
		}
		if (StringUtils.isEmpty(userIds)) {
			return CommonResult.ajax(insertSwordMessage(swordMessage));
		}
		Long[] ids = Convert.toLongArray(userIds);
		swordMessage.setPublish(ids.length);
		int rows = insertSwordMessage(swordMessage);
		if (rows > 0 && SwordMessage.STATUS_PUBLISHED.equals(swordMessage.getStatus())) {
			return CommonResult
					.ajax(userMessageService.publishUserMessages(swordMessage.getId(), IMessageType.TYPE_MESSAGE, ids));
		}
		return CommonResult.ajax(rows);
	}
}
