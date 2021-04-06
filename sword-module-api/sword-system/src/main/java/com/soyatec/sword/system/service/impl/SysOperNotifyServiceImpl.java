package com.soyatec.sword.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soyatec.sword.code.service.IMailService;
import com.soyatec.sword.code.service.IMobileService;
import com.soyatec.sword.common.core.text.Convert;
import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.common.utils.IdWorker;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.system.domain.SysOperLog;
import com.soyatec.sword.system.domain.SysOperNotify;
import com.soyatec.sword.system.mapper.SysOperNotifyMapper;
import com.soyatec.sword.system.service.ISysOperNotifyService;

/**
 * 敏感操作通知Service业务层处理
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-04-06
 */
@Service
public class SysOperNotifyServiceImpl implements ISysOperNotifyService {
	@Autowired
	private SysOperNotifyMapper sysOperNotifyMapper;

	@Autowired(required = false)
	private IMailService mailService;
	@Autowired(required = false)
	private IMobileService mobileService;

	/**
	 * 查询敏感操作通知
	 * 
	 * @param id 敏感操作通知ID
	 * @return 敏感操作通知
	 */
	@Override
	public SysOperNotify selectSysOperNotifyById(Long id) {
		return sysOperNotifyMapper.selectSysOperNotifyById(id);
	}

	/**
	 * 查询敏感操作通知列表
	 * 
	 * @param sysOperNotify 敏感操作通知
	 * @return 敏感操作通知
	 */
	@Override
	public List<SysOperNotify> selectSysOperNotifyList(SysOperNotify sysOperNotify) {
		return sysOperNotifyMapper.selectSysOperNotifyList(sysOperNotify);
	}

	/**
	 * 新增敏感操作通知
	 * 
	 * @param sysOperNotify 敏感操作通知
	 * @return 结果
	 */
	@Override
	public int insertSysOperNotify(SysOperNotify sysOperNotify) {
		if (sysOperNotify.getId() == null) {
			sysOperNotify.setId(IdWorker.getId());
		}
		if (sysOperNotify.getCreateTime() == null) {
			sysOperNotify.setCreateTime(DateUtils.getNowDate());
		}
		return sysOperNotifyMapper.insertSysOperNotify(sysOperNotify);
	}

	/**
	 * 修改敏感操作通知
	 * 
	 * @param sysOperNotify 敏感操作通知
	 * @return 结果
	 */
	@Override
	public int updateSysOperNotify(SysOperNotify sysOperNotify) {
		sysOperNotify.setUpdateTime(DateUtils.getNowDate());
		return sysOperNotifyMapper.updateSysOperNotify(sysOperNotify);
	}

	/**
	 * 删除敏感操作通知对象
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteSysOperNotifyByIds(String ids) {
		return sysOperNotifyMapper.deleteSysOperNotifyByIds(Convert.toStrArray(ids));
	}

	/**
	 * 删除敏感操作通知信息
	 * 
	 * @param id 敏感操作通知ID
	 * @return 结果
	 */
	@Override
	public int deleteSysOperNotifyById(Long id) {
		return sysOperNotifyMapper.deleteSysOperNotifyById(id);
	}

	private SysOperNotify selectNotifiesByType(Integer type, String url) {
		SysOperNotify query = new SysOperNotify();
		query.setType(type);
		query.setOperateUrl(url);
		List<SysOperNotify> list = selectSysOperNotifyList(query);
		return list == null || list.isEmpty() ? null : list.get(0);
	}

	@Override
	public void notifyByOperLog(SysOperLog operLog) {
		if (operLog == null || StringUtils.isEmpty(operLog.getOperUrl())) {
			return;
		}
		String url = operLog.getOperUrl();
		if (mailService != null) {
			SysOperNotify notify = selectNotifiesByType(SysOperNotify.TYPE_EMAIL, url);
			if (notify != null) {
				sendNotifyByEmail(notify, operLog);
			}
		}

		if (mobileService != null) {
			SysOperNotify notify = selectNotifiesByType(SysOperNotify.TYPE_EMAIL, url);
			if (notify != null) {
				sendNotifyByMobile(notify, operLog);
			}
		}
	}

	private void sendNotifyByMobile(SysOperNotify notify, SysOperLog operLog) {

	}

	private void sendNotifyByEmail(SysOperNotify notify, SysOperLog operLog) {

	}
}
