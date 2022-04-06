package com.github.ecsoya.sword.system.mapper;

import java.util.List;

import com.github.ecsoya.sword.system.domain.SysNotice;

/**
 * The Interface SysNoticeMapper.
 */
public interface SysNoticeMapper {

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
	 * @param noticeIds the notice ids
	 * @return the int
	 */
	public int deleteNoticeByIds(String[] noticeIds);
}