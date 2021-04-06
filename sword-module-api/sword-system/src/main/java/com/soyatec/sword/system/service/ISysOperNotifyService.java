package com.soyatec.sword.system.service;

import java.util.List;

import com.soyatec.sword.system.domain.SysOperLog;
import com.soyatec.sword.system.domain.SysOperNotify;

/**
 * 敏感操作通知Service接口
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-04-06
 */
public interface ISysOperNotifyService {
	/**
	 * 查询敏感操作通知
	 * 
	 * @param id 敏感操作通知ID
	 * @return 敏感操作通知
	 */
	public SysOperNotify selectSysOperNotifyById(Long id);

	/**
	 * 查询敏感操作通知列表
	 * 
	 * @param sysOperNotify 敏感操作通知
	 * @return 敏感操作通知集合
	 */
	public List<SysOperNotify> selectSysOperNotifyList(SysOperNotify sysOperNotify);

	/**
	 * 新增敏感操作通知
	 * 
	 * @param sysOperNotify 敏感操作通知
	 * @return 结果
	 */
	public int insertSysOperNotify(SysOperNotify sysOperNotify);

	/**
	 * 修改敏感操作通知
	 * 
	 * @param sysOperNotify 敏感操作通知
	 * @return 结果
	 */
	public int updateSysOperNotify(SysOperNotify sysOperNotify);

	/**
	 * 批量删除敏感操作通知
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteSysOperNotifyByIds(String ids);

	/**
	 * 删除敏感操作通知信息
	 * 
	 * @param id 敏感操作通知ID
	 * @return 结果
	 */
	public int deleteSysOperNotifyById(Long id);

	public void notifyByOperLog(SysOperLog operLog);
}
