package com.soyatec.sword.system.service.impl;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.soyatec.sword.code.service.IMailService;
import com.soyatec.sword.code.service.IMobileService;
import com.soyatec.sword.common.config.GlobalConfig;
import com.soyatec.sword.common.core.domain.AjaxResult;
import com.soyatec.sword.common.core.domain.entity.SysUser;
import com.soyatec.sword.common.core.text.Convert;
import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.common.utils.IdWorker;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.common.utils.reflect.ReflectUtils;
import com.soyatec.sword.system.domain.SysOperLog;
import com.soyatec.sword.system.domain.SysOperNotify;
import com.soyatec.sword.system.mapper.SysOperNotifyMapper;
import com.soyatec.sword.system.service.ISysOperNotifyService;
import com.soyatec.sword.system.service.ISysUserService;

/**
 * 敏感操作通知Service业务层处理
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-04-06
 */
@Service
public class SysOperNotifyServiceImpl implements ISysOperNotifyService {

	private static final Logger log = LoggerFactory.getLogger(ISysOperNotifyService.class);

	@Autowired
	private SysOperNotifyMapper sysOperNotifyMapper;

	@Autowired(required = false)
	private IMailService mailService;
	@Autowired(required = false)
	private IMobileService mobileService;

	@Autowired
	private ISysUserService userService;

	@Value("${server.servlet.context-path:}")
	private String prefix;

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
		List<SysOperNotify> list = selectSysOperNotifyList(query);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.stream().filter(n -> {
			if (url.equals(prefix + n.getOperateUrl())) {
				return true;
			}
			String[] array = Convert.toStrArray(n.getOperateUrl());
			if (array.length > 1) {
				for (String s : array) {
					if (url.equals(prefix + s)) {
						return true;
					}
				}
			}
			return false;
		}).findFirst().orElse(null);
	}

	@Override
	public void notifyByOperLog(SysOperLog operLog) {
		if (operLog == null || StringUtils.isEmpty(operLog.getOperUrl())) {
			return;
		}
		String json = operLog.getJsonResult();
		if (StringUtils.isEmpty(json)) {
			return;
		}
		AjaxResult ajax = JSON.parseObject(json, AjaxResult.class);
		if (ajax == null || ajax.getCode() != 0) {
			return;
		}
		String url = operLog.getOperUrl();
		SysUser admin = getAdmin(operLog);
		if (admin == null) {
			return;
		}
		log.info("Notify: url={}, admin={}", url, admin.getLoginName());
		SysOperNotify emailNotify = selectNotifiesByType(SysOperNotify.TYPE_EMAIL, url);
		if (emailNotify != null) {
			sendNotifyByEmail(emailNotify, operLog);
		}

		SysOperNotify mobileNotify = selectNotifiesByType(SysOperNotify.TYPE_MOBILE, url);
		if (mobileNotify != null) {
			sendNotifyByMobile(mobileNotify, operLog);
		}
	}

	private void sendNotifyByMobile(SysOperNotify notify, SysOperLog operLog) {
		if (notify == null || operLog == null) {
			return;
		}
		SysUser admin = getAdmin(operLog);
		if (admin == null) {
			return;
		}
		String content = formatTemplate(notify, operLog);
		String mobile = admin.getPhonenumber();
		log.debug("Notify: mobile={}, content={}", mobile, content);
		if (GlobalConfig.isNotifyEnabled() && mobileService != null) {
			mobileService.sendMessage(mobile, content);
		}
	}

	private void sendNotifyByEmail(SysOperNotify notify, SysOperLog operLog) {
		if (notify == null || operLog == null) {
			return;
		}
		SysUser admin = getAdmin(operLog);
		if (admin == null) {
			return;
		}
		String content = formatTemplate(notify, operLog);
		String email = admin.getEmail();
		String subject = notify.getSubject();
		log.debug("Notify: email={}, subject={}, content={}", email, subject, content);
		if (GlobalConfig.isNotifyEnabled() && mailService != null) {
			mailService.sendEmail(email, subject, content);
		}
	}

	private SysUser getAdmin(SysOperLog operLog) {
		if (operLog == null) {
			return null;
		}
		String admin = operLog.getOperName();
		if (StringUtils.isEmpty(admin)) {
			return null;
		}
		return userService.selectUserByLoginName(admin);
	}

	private String formatTemplate(SysOperNotify notify, SysOperLog operLog) {
		if (notify == null || operLog == null) {
			return null;
		}
		String template = notify.getTemplate();
		if (StringUtils.isEmpty(template)) {
			return null;
		}
		List<Field> fields = ReflectUtils.getAllFields(SysOperLog.class);
		for (Field field : fields) {
			String name = field.getName();
			field.setAccessible(true);
			try {
				Object value = field.get(operLog);
				String target = value == null ? "" : value.toString();
				if (value instanceof Date) {
					target = DateUtils.toDefault((Date) value);
				}
				template = template.replaceAll("\\{" + name + "\\}", target);
			} catch (Exception e) {
				continue;
			}
		}
		return template;
	}
}
