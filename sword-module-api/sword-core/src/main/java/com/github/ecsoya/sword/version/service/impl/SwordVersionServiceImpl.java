package com.github.ecsoya.sword.version.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.utils.CacheUtils;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.IdWorker;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.version.domain.SwordVersion;
import com.github.ecsoya.sword.version.mapper.SwordVersionMapper;
import com.github.ecsoya.sword.version.service.ISwordVersionService;
import com.github.pagehelper.page.PageMethod;

/**
 * 版本Service业务层处理
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-22
 */
@Service
public class SwordVersionServiceImpl implements ISwordVersionService {
	@Autowired
	private SwordVersionMapper swordVersionMapper;

	private static final String CACHE_VERSION = "sword:version:";
	private static final List<String> types = Arrays.asList(SwordVersion.TYPE_IOS, SwordVersion.TYPE_ANDROID);

	@PostConstruct
	public void init() {
		rebuildCache();
	}

	private void rebuildCache() {
		for (final String type : types) {
			final SwordVersion version = swordVersionMapper.selectLatestSwordVersion(type);
			if (version == null) {
				continue;
			}
			CacheUtils.put(CACHE_VERSION, type, version);
		}
	}

	/**
	 * 查询版本
	 *
	 * @param id 版本ID
	 * @return 版本
	 */
	@Override
	public SwordVersion selectSwordVersionById(Long id) {
		return swordVersionMapper.selectSwordVersionById(id);
	}

	/**
	 * 查询版本列表
	 *
	 * @param swordVersion 版本
	 * @return 版本
	 */
	@Override
	public List<SwordVersion> selectSwordVersionList(SwordVersion swordVersion) {
		return swordVersionMapper.selectSwordVersionList(swordVersion);
	}

	/**
	 * 新增版本
	 *
	 * @param swordVersion 版本
	 * @return 结果
	 */
	@Override
	public int insertSwordVersion(SwordVersion swordVersion) {
		if (swordVersion.getId() == null) {
			swordVersion.setId(IdWorker.getId());
		}
		if (swordVersion.getCreateTime() == null) {
			swordVersion.setCreateTime(DateUtils.getNowDate());
		}
		final int rows = swordVersionMapper.insertSwordVersion(swordVersion);
		if (rows > 0) {
			rebuildCache();
		}
		return rows;
	}

	/**
	 * 修改版本
	 *
	 * @param swordVersion 版本
	 * @return 结果
	 */
	@Override
	public int updateSwordVersion(SwordVersion swordVersion) {
		swordVersion.setUpdateTime(DateUtils.getNowDate());
		final int rows = swordVersionMapper.updateSwordVersion(swordVersion);
		if (rows > 0) {
			rebuildCache();
		}
		return rows;
	}

	/**
	 * 删除版本对象
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteSwordVersionByIds(String ids) {
		final int rows = swordVersionMapper.deleteSwordVersionByIds(Convert.toStrArray(ids));
		if (rows > 0) {
			rebuildCache();
		}
		return rows;
	}

	/**
	 * 删除版本信息
	 *
	 * @param id 版本ID
	 * @return 结果
	 */
	@Override
	public int deleteSwordVersionById(Long id) {
		final int rows = swordVersionMapper.deleteSwordVersionById(id);
		if (rows > 0) {
			rebuildCache();
		}
		return rows;
	}

	@Override
	public SwordVersion selectLatestVersion(String type) {
		if (StringUtils.isEmpty(type)) {
			return null;
		}
		final Object version = CacheUtils.get(CACHE_VERSION, type);
		if (version instanceof SwordVersion) {
			return (SwordVersion) version;
		}
		PageMethod.clearPage();
		return swordVersionMapper.selectLatestSwordVersion(type);
	}

	@Override
	public Long selectLatestVersionNumber(String type) {
		final SwordVersion version = selectLatestVersion(type);
		if (version == null) {
			return null;
		}
		return version.getVersion();
	}
}
