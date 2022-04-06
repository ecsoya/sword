package com.github.ecsoya.sword.system.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.system.domain.SysNotice;
import com.github.ecsoya.sword.system.mapper.SysNoticeMapper;
import com.github.ecsoya.sword.system.service.ISysNoticeService;

/**
 * The Class SysNoticeServiceImpl.
 */
@Service
public class SysNoticeServiceImpl implements ISysNoticeService {

	/** The notice mapper. */
	@Autowired
	private SysNoticeMapper noticeMapper;

	/**
	 * Select notice by id.
	 *
	 * @param noticeId the notice id
	 * @return the sys notice
	 */
	@Override
	public SysNotice selectNoticeById(Long noticeId) {
		return noticeMapper.selectNoticeById(noticeId);
	}

	/**
	 * Select notice list.
	 *
	 * @param notice the notice
	 * @return the list
	 */
	@Override
	public List<SysNotice> selectNoticeList(SysNotice notice) {
		return noticeMapper.selectNoticeList(notice);
	}

	/**
	 * Insert notice.
	 *
	 * @param notice the notice
	 * @return the int
	 */
	@Override
	public int insertNotice(SysNotice notice) {
		return noticeMapper.insertNotice(notice);
	}

	/**
	 * Update notice.
	 *
	 * @param notice the notice
	 * @return the int
	 */
	@Override
	public int updateNotice(SysNotice notice) {
		return noticeMapper.updateNotice(notice);
	}

	/**
	 * Delete notice by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteNoticeByIds(String ids) {
		return noticeMapper.deleteNoticeByIds(Convert.toStrArray(ids));
	}

	/**
	 * Gets the latest notice title.
	 *
	 * @return the latest notice title
	 */
	@Override
	public String getLatestNoticeTitle() {
		final List<SysNotice> list = selectNoticeListByType(SysNotice.NOTICE_TYPE_NOTICE);
		if (list.isEmpty()) {
			return "";
		}
		final StringBuffer buf = new StringBuffer();
		for (final SysNotice notice : list) {
			if (buf.length() != 0) {
				buf.append("；");
			}
			buf.append(notice.getNoticeTitle());
		}
		return new String(buf);
	}

	/**
	 * Select notice list by type.
	 *
	 * @param noticeType the notice type
	 * @return the list
	 */
	@Override
	public List<SysNotice> selectNoticeListByType(String noticeType) {
		if (StringUtils.isEmpty(noticeType)) {
			return Collections.emptyList();
		}
		final SysNotice query = new SysNotice();
		query.setNoticeType(noticeType);
		query.setStatus(SysNotice.STATUS_NORMAL);
		return selectNoticeList(query);
	}
}
