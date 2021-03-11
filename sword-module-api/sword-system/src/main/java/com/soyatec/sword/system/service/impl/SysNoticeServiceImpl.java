package com.soyatec.sword.system.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soyatec.sword.common.core.text.Convert;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.system.domain.SysNotice;
import com.soyatec.sword.system.mapper.SysNoticeMapper;
import com.soyatec.sword.system.service.ISysNoticeService;

/**
 * 公告 服务层实现
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2018-06-25
 */
@Service
public class SysNoticeServiceImpl implements ISysNoticeService {
	@Autowired
	private SysNoticeMapper noticeMapper;

	/**
	 * 查询公告信息
	 * 
	 * @param noticeId 公告ID
	 * @return 公告信息
	 */
	@Override
	public SysNotice selectNoticeById(Long noticeId) {
		return noticeMapper.selectNoticeById(noticeId);
	}

	/**
	 * 查询公告列表
	 * 
	 * @param notice 公告信息
	 * @return 公告集合
	 */
	@Override
	public List<SysNotice> selectNoticeList(SysNotice notice) {
		return noticeMapper.selectNoticeList(notice);
	}

	/**
	 * 新增公告
	 * 
	 * @param notice 公告信息
	 * @return 结果
	 */
	@Override
	public int insertNotice(SysNotice notice) {
		return noticeMapper.insertNotice(notice);
	}

	/**
	 * 修改公告
	 * 
	 * @param notice 公告信息
	 * @return 结果
	 */
	@Override
	public int updateNotice(SysNotice notice) {
		return noticeMapper.updateNotice(notice);
	}

	/**
	 * 删除公告对象
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteNoticeByIds(String ids) {
		return noticeMapper.deleteNoticeByIds(Convert.toStrArray(ids));
	}

	@Override
	public String getLatestNoticeTitle() {
		List<SysNotice> list = selectNoticeListByType(SysNotice.NOTICE_TYPE_NOTICE);
		if (list.isEmpty()) {
			return "";
		}
		StringBuffer buf = new StringBuffer();
		for (SysNotice notice : list) {
			if (buf.length() != 0) {
				buf.append("；");
			}
			buf.append(notice.getNoticeTitle());
		}
		return new String(buf);
	}

	@Override
	public List<SysNotice> selectNoticeListByType(String noticeType) {
		if (StringUtils.isEmpty(noticeType)) {
			return Collections.emptyList();
		}
		SysNotice query = new SysNotice();
		query.setNoticeType(noticeType);
		query.setStatus(SysNotice.STATUS_NORMAL);
		return selectNoticeList(query);
	}
}
