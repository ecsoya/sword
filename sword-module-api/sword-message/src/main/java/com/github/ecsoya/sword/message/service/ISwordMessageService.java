package com.github.ecsoya.sword.message.service;

import java.util.List;

import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.message.domain.SwordMessage;

/**
 * The Interface ISwordMessageService.
 */
public interface ISwordMessageService {

	/**
	 * Select sword message by id.
	 *
	 * @param id the id
	 * @return the sword message
	 */
	public SwordMessage selectSwordMessageById(Long id);

	/**
	 * Select sword message list.
	 *
	 * @param swordMessage the sword message
	 * @return the list
	 */
	public List<SwordMessage> selectSwordMessageList(SwordMessage swordMessage);

	/**
	 * Insert sword message.
	 *
	 * @param swordMessage the sword message
	 * @return the int
	 */
	public int insertSwordMessage(SwordMessage swordMessage);

	/**
	 * Update sword message.
	 *
	 * @param swordMessage the sword message
	 * @return the int
	 */
	public int updateSwordMessage(SwordMessage swordMessage);

	/**
	 * Delete sword message by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteSwordMessageByIds(String ids);

	/**
	 * Delete sword message by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteSwordMessageById(Long id);

	/**
	 * Publish sword message.
	 *
	 * @param swordMessage the sword message
	 * @param userIds      the user ids
	 * @return the common result
	 */
	public CommonResult<?> publishSwordMessage(SwordMessage swordMessage, String userIds);
}
