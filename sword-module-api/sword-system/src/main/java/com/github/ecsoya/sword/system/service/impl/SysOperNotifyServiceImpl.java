package com.github.ecsoya.sword.system.service.impl;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.domain.entity.SysUser;
import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.IdWorker;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.reflect.ReflectUtils;
import com.github.ecsoya.sword.system.domain.SysOperLog;
import com.github.ecsoya.sword.system.domain.SysOperNotify;
import com.github.ecsoya.sword.system.mapper.SysOperNotifyMapper;
import com.github.ecsoya.sword.system.service.ISysOperNotifyService;
import com.github.ecsoya.sword.system.service.ISysUserService;

/**
 * The Class SysOperNotifyServiceImpl.
 */
@Service
public class SysOperNotifyServiceImpl implements ISysOperNotifyService {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(ISysOperNotifyService.class);

	/** The sys oper notify mapper. */
	@Autowired
	private SysOperNotifyMapper sysOperNotifyMapper;

//	@Autowired(required = false)
//	private IMailService mailService;
//	@Autowired(required = false)
//	private IMobileService mobileService;

	/** The user service. */
	@Autowired
	private ISysUserService userService;

	/** The prefix. */
	@Value("${server.servlet.context-path:}")
	private String prefix;

	/**
	 * Select sys oper notify by id.
	 *
	 * @param id the id
	 * @return the sys oper notify
	 */
	@Override
	public SysOperNotify selectSysOperNotifyById(Long id) {
		return sysOperNotifyMapper.selectSysOperNotifyById(id);
	}

	/**
	 * Select sys oper notify list.
	 *
	 * @param sysOperNotify the sys oper notify
	 * @return the list
	 */
	@Override
	public List<SysOperNotify> selectSysOperNotifyList(SysOperNotify sysOperNotify) {
		return sysOperNotifyMapper.selectSysOperNotifyList(sysOperNotify);
	}

	/**
	 * Insert sys oper notify.
	 *
	 * @param sysOperNotify the sys oper notify
	 * @return the int
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
	 * Update sys oper notify.
	 *
	 * @param sysOperNotify the sys oper notify
	 * @return the int
	 */
	@Override
	public int updateSysOperNotify(SysOperNotify sysOperNotify) {
		sysOperNotify.setUpdateTime(DateUtils.getNowDate());
		return sysOperNotifyMapper.updateSysOperNotify(sysOperNotify);
	}

	/**
	 * Delete sys oper notify by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteSysOperNotifyByIds(String ids) {
		return sysOperNotifyMapper.deleteSysOperNotifyByIds(Convert.toStrArray(ids));
	}

	/**
	 * Delete sys oper notify by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Override
	public int deleteSysOperNotifyById(Long id) {
		return sysOperNotifyMapper.deleteSysOperNotifyById(id);
	}

	/**
	 * Select notifies by type.
	 *
	 * @param type the type
	 * @param url  the url
	 * @return the sys oper notify
	 */
	private SysOperNotify selectNotifiesByType(Integer type, String url) {
		final SysOperNotify query = new SysOperNotify();
		query.setType(type);
		final List<SysOperNotify> list = selectSysOperNotifyList(query);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.stream().filter(n -> {
			if (url.equals(prefix + n.getOperateUrl())) {
				return true;
			}
			final String[] array = Convert.toStrArray(n.getOperateUrl());
			if (array.length > 1) {
				for (final String s : array) {
					if (url.equals(prefix + s)) {
						return true;
					}
				}
			}
			return false;
		}).findFirst().orElse(null);
	}

	/**
	 * Notify by oper log.
	 *
	 * @param operLog the oper log
	 */
	@Override
	public void notifyByOperLog(SysOperLog operLog) {
		if (operLog == null || StringUtils.isEmpty(operLog.getOperUrl())) {
			return;
		}
		final String json = operLog.getJsonResult();
		if (StringUtils.isEmpty(json)) {
			return;
		}
		final AjaxResult ajax = JSON.parseObject(json, AjaxResult.class);
		if (ajax == null || ajax.getCode() != 0) {
			return;
		}
		final String url = operLog.getOperUrl();
		final SysUser admin = getAdmin(operLog);
		if (admin == null) {
			return;
		}
//		log.info("Notify: url={}, admin={}", url, admin.getLoginName());
//		final SysOperNotify emailNotify = selectNotifiesByType(SysOperNotify.TYPE_EMAIL, url);
//		if (emailNotify != null) {
//			sendNotifyByEmail(emailNotify, operLog);
//		}
//
//		final SysOperNotify mobileNotify = selectNotifiesByType(SysOperNotify.TYPE_MOBILE, url);
//		if (mobileNotify != null) {
//			sendNotifyByMobile(mobileNotify, operLog);
//		}
	}

//	private void sendNotifyByMobile(SysOperNotify notify, SysOperLog operLog) {
//		if (notify == null || operLog == null) {
//			return;
//		}
//		final SysUser admin = getAdmin(operLog);
//		if (admin == null) {
//			return;
//		}
//		final String content = formatTemplate(notify, operLog);
//		final String mobile = admin.getPhonenumber();
//		log.debug("Notify: mobile={}, content={}", mobile, content);
//		if (GlobalConfig.isNotifyEnabled() && mobileService != null) {
//			mobileService.sendMessage(mobile, content);
//		}
//	}
//
//	private void sendNotifyByEmail(SysOperNotify notify, SysOperLog operLog) {
//		if (notify == null || operLog == null) {
//			return;
//		}
//		final SysUser admin = getAdmin(operLog);
//		if (admin == null) {
//			return;
//		}
//		final String content = formatTemplate(notify, operLog);
//		final String email = admin.getEmail();
//		final String subject = notify.getSubject();
//		log.debug("Notify: email={}, subject={}, content={}", email, subject, content);
//		if (GlobalConfig.isNotifyEnabled() && mailService != null) {
//			mailService.sendEmail(email, subject, content);
//		}
//	}

	/**
	 * Gets the admin.
	 *
	 * @param operLog the oper log
	 * @return the admin
	 */
	private SysUser getAdmin(SysOperLog operLog) {
		if (operLog == null) {
			return null;
		}
		final String admin = operLog.getOperName();
		if (StringUtils.isEmpty(admin)) {
			return null;
		}
		return userService.selectUserByLoginName(admin);
	}

	/**
	 * Format template.
	 *
	 * @param notify  the notify
	 * @param operLog the oper log
	 * @return the string
	 */
	private String formatTemplate(SysOperNotify notify, SysOperLog operLog) {
		if (notify == null || operLog == null) {
			return null;
		}
		String template = notify.getTemplate();
		if (StringUtils.isEmpty(template)) {
			return null;
		}
		final List<Field> fields = ReflectUtils.getAllFields(SysOperLog.class);
		for (final Field field : fields) {
			final String name = field.getName();
			field.setAccessible(true);
			try {
				final Object value = field.get(operLog);
				String target = value == null ? "" : value.toString();
				if (value instanceof Date) {
					target = DateUtils.toDefault((Date) value);
				}
				template = template.replaceAll("\\{" + name + "\\}", target);
			} catch (final Exception e) {
				continue;
			}
		}
		return template;
	}
}
