package com.soyatec.sword.message.service;

import java.util.List;
import com.soyatec.sword.message.domain.SwordMessage;

/**
 * 消息Service接口
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-05-20
 */
public interface ISwordMessageService {
	/**
	 * 查询消息
	 * 
	 * @param id 消息ID
	 * @return 消息
	 */
	public SwordMessage selectSwordMessageById(Long id);

	/**
	 * 查询消息列表
	 * 
	 * @param swordMessage 消息
	 * @return 消息集合
	 */
	public List<SwordMessage> selectSwordMessageList(SwordMessage swordMessage);

	/**
	 * 新增消息
	 * 
	 * @param swordMessage 消息
	 * @return 结果
	 */
	public int insertSwordMessage(SwordMessage swordMessage);

	/**
	 * 修改消息
	 * 
	 * @param swordMessage 消息
	 * @return 结果
	 */
	public int updateSwordMessage(SwordMessage swordMessage);

	/**
	 * 批量删除消息
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteSwordMessageByIds(String ids);

	/**
	 * 删除消息信息
	 * 
	 * @param id 消息ID
	 * @return 结果
	 */
	public int deleteSwordMessageById(Long id);
}
