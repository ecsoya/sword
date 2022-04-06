package com.github.ecsoya.sword.message.mapper;

import java.util.List;

import com.github.ecsoya.sword.message.domain.SwordMessage;

/**
 * The Interface SwordMessageMapper.
 */
public interface SwordMessageMapper {

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
	 * Delete sword message by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteSwordMessageById(Long id);

	/**
	 * Delete sword message by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteSwordMessageByIds(String[] ids);
}
