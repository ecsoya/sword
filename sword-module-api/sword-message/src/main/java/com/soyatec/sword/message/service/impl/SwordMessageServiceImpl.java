package com.soyatec.sword.message.service.impl;

import java.util.List;
import com.soyatec.sword.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.soyatec.sword.common.utils.IdWorker;
import com.soyatec.sword.message.mapper.SwordMessageMapper;
import com.soyatec.sword.message.domain.SwordMessage;
import com.soyatec.sword.message.service.ISwordMessageService;
import com.soyatec.sword.common.core.text.Convert;

/**
 * 消息Service业务层处理
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-05-20
 */
@Service
public class SwordMessageServiceImpl implements ISwordMessageService {
	@Autowired
	private SwordMessageMapper swordMessageMapper;

	/**
	 * 查询消息
	 * 
	 * @param id 消息ID
	 * @return 消息
	 */
	@Override
	public SwordMessage selectSwordMessageById(Long id) {
		return swordMessageMapper.selectSwordMessageById(id);
	}

	/**
	 * 查询消息列表
	 * 
	 * @param swordMessage 消息
	 * @return 消息
	 */
	@Override
	public List<SwordMessage> selectSwordMessageList(SwordMessage swordMessage) {
		return swordMessageMapper.selectSwordMessageList(swordMessage);
	}

	/**
	 * 新增消息
	 * 
	 * @param swordMessage 消息
	 * @return 结果
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
	 * 修改消息
	 * 
	 * @param swordMessage 消息
	 * @return 结果
	 */
	@Override
	public int updateSwordMessage(SwordMessage swordMessage) {
		swordMessage.setUpdateTime(DateUtils.getNowDate());
		return swordMessageMapper.updateSwordMessage(swordMessage);
	}

	/**
	 * 删除消息对象
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteSwordMessageByIds(String ids) {
		return swordMessageMapper.deleteSwordMessageByIds(Convert.toStrArray(ids));
	}

	/**
	 * 删除消息信息
	 * 
	 * @param id 消息ID
	 * @return 结果
	 */
	@Override
	public int deleteSwordMessageById(Long id) {
		return swordMessageMapper.deleteSwordMessageById(id);
	}
}
