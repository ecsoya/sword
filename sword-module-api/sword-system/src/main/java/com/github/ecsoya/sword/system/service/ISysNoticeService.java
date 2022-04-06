package com.github.ecsoya.sword.system.service;

import java.util.List;

import com.github.ecsoya.sword.system.domain.SysNotice;

/**
 * The Interface ISysNoticeService.
 */
public interface ISysNoticeService {

	/**
	 * Select notice by id.
	 *
	 * @param noticeId the notice id
	 * @return the sys notice
	 */
	public SysNotice selectNoticeById(Long noticeId);

	/**
	 * Select notice list.
	 *
	 * @param notice the notice
	 * @return the list
	 */
	public List<SysNotice> selectNoticeList(SysNotice notice);

	/**
	 * Insert notice.
	 *
	 * @param notice the notice
	 * @return the int
	 */
	public int insertNotice(SysNotice notice);

	/**
	 * Update notice.
	 *
	 * @param notice the notice
	 * @return the int
	 */
	public int updateNotice(SysNotice notice);

	/**
	 * Delete notice by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteNoticeByIds(String ids);

	/**
	 * Gets the latest notice title.
	 *
	 * @return the latest notice title
	 */
	public String getLatestNoticeTitle();

	/**
	 * Select notice list by type.
	 *
	 * @param noticeType the notice type
	 * @return the list
	 */
	public List<SysNotice> selectNoticeListByType(String noticeType);
}
