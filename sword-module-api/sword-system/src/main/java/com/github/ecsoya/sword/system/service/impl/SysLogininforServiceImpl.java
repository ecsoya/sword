package com.github.ecsoya.sword.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.system.domain.SysLogininfor;
import com.github.ecsoya.sword.system.mapper.SysLogininforMapper;
import com.github.ecsoya.sword.system.service.ISysLogininforService;

/**
 * The Class SysLogininforServiceImpl.
 */
@Service
public class SysLogininforServiceImpl implements ISysLogininforService {

	/** The logininfor mapper. */
	@Autowired
	private SysLogininforMapper logininforMapper;

	/**
	 * Insert logininfor.
	 *
	 * @param logininfor the logininfor
	 */
	@Override
	public void insertLogininfor(SysLogininfor logininfor) {
		logininforMapper.insertLogininfor(logininfor);
	}

	/**
	 * Select logininfor list.
	 *
	 * @param logininfor the logininfor
	 * @return the list
	 */
	@Override
	public List<SysLogininfor> selectLogininforList(SysLogininfor logininfor) {
		return logininforMapper.selectLogininforList(logininfor);
	}

	/**
	 * Delete logininfor by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteLogininforByIds(String ids) {
		return logininforMapper.deleteLogininforByIds(Convert.toStrArray(ids));
	}

	/**
	 * Clean logininfor.
	 */
	@Override
	public void cleanLogininfor() {
		logininforMapper.cleanLogininfor();
	}
}
