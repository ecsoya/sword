package com.github.ecsoya.sword.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.system.domain.SysOperLog;
import com.github.ecsoya.sword.system.mapper.SysOperLogMapper;
import com.github.ecsoya.sword.system.service.ISysOperLogService;
import com.github.ecsoya.sword.system.service.ISysOperNotifyService;

/**
 * The Class SysOperLogServiceImpl.
 */
@Service
public class SysOperLogServiceImpl implements ISysOperLogService {

	/** The oper log mapper. */
	@Autowired
	private SysOperLogMapper operLogMapper;

	/** The oper notify service. */
	@Autowired
	private ISysOperNotifyService operNotifyService;

	/**
	 * Insert operlog.
	 *
	 * @param operLog the oper log
	 */
	@Override
	public void insertOperlog(SysOperLog operLog) {
		operLogMapper.insertOperlog(operLog);
		operNotifyService.notifyByOperLog(operLog);
	}

	/**
	 * Select oper log list.
	 *
	 * @param operLog the oper log
	 * @return the list
	 */
	@Override
	public List<SysOperLog> selectOperLogList(SysOperLog operLog) {
		return operLogMapper.selectOperLogList(operLog);
	}

	/**
	 * Delete oper log by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteOperLogByIds(String ids) {
		return operLogMapper.deleteOperLogByIds(Convert.toStrArray(ids));
	}

	/**
	 * Select oper log by id.
	 *
	 * @param operId the oper id
	 * @return the sys oper log
	 */
	@Override
	public SysOperLog selectOperLogById(Long operId) {
		return operLogMapper.selectOperLogById(operId);
	}

	/**
	 * Clean oper log.
	 */
	@Override
	public void cleanOperLog() {
		operLogMapper.cleanOperLog();
	}
}
