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
 * The Class SwordVersionServiceImpl.
 */
@Service
public class SwordVersionServiceImpl implements ISwordVersionService {

	/** The sword version mapper. */
	@Autowired
	private SwordVersionMapper swordVersionMapper;

	/** The Constant CACHE_VERSION. */
	private static final String CACHE_VERSION = "sword:version:";

	/** The Constant types. */
	private static final List<String> types = Arrays.asList(SwordVersion.TYPE_IOS, SwordVersion.TYPE_ANDROID);

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		rebuildCache();
	}

	/**
	 * Rebuild cache.
	 */
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
	 * Select sword version by id.
	 *
	 * @param id the id
	 * @return the sword version
	 */
	@Override
	public SwordVersion selectSwordVersionById(Long id) {
		return swordVersionMapper.selectSwordVersionById(id);
	}

	/**
	 * Select sword version list.
	 *
	 * @param swordVersion the sword version
	 * @return the list
	 */
	@Override
	public List<SwordVersion> selectSwordVersionList(SwordVersion swordVersion) {
		return swordVersionMapper.selectSwordVersionList(swordVersion);
	}

	/**
	 * Insert sword version.
	 *
	 * @param swordVersion the sword version
	 * @return the int
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
	 * Update sword version.
	 *
	 * @param swordVersion the sword version
	 * @return the int
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
	 * Delete sword version by ids.
	 *
	 * @param ids the ids
	 * @return the int
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
	 * Delete sword version by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Override
	public int deleteSwordVersionById(Long id) {
		final int rows = swordVersionMapper.deleteSwordVersionById(id);
		if (rows > 0) {
			rebuildCache();
		}
		return rows;
	}

	/**
	 * Select latest version.
	 *
	 * @param type the type
	 * @return the sword version
	 */
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

	/**
	 * Select latest version number.
	 *
	 * @param type the type
	 * @return the long
	 */
	@Override
	public Long selectLatestVersionNumber(String type) {
		final SwordVersion version = selectLatestVersion(type);
		if (version == null) {
			return null;
		}
		return version.getVersion();
	}
}
