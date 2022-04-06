package com.github.ecsoya.sword.version.service;

import java.util.List;

import com.github.ecsoya.sword.version.domain.SwordVersion;

/**
 * The Interface ISwordVersionService.
 */
public interface ISwordVersionService {

	/**
	 * Select sword version by id.
	 *
	 * @param id the id
	 * @return the sword version
	 */
	public SwordVersion selectSwordVersionById(Long id);

	/**
	 * Select sword version list.
	 *
	 * @param swordVersion the sword version
	 * @return the list
	 */
	public List<SwordVersion> selectSwordVersionList(SwordVersion swordVersion);

	/**
	 * Insert sword version.
	 *
	 * @param swordVersion the sword version
	 * @return the int
	 */
	public int insertSwordVersion(SwordVersion swordVersion);

	/**
	 * Update sword version.
	 *
	 * @param swordVersion the sword version
	 * @return the int
	 */
	public int updateSwordVersion(SwordVersion swordVersion);

	/**
	 * Delete sword version by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteSwordVersionByIds(String ids);

	/**
	 * Delete sword version by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteSwordVersionById(Long id);

	/**
	 * Select latest version.
	 *
	 * @param type the type
	 * @return the sword version
	 */
	public SwordVersion selectLatestVersion(String type);

	/**
	 * Select latest version number.
	 *
	 * @param type the type
	 * @return the long
	 */
	public Long selectLatestVersionNumber(String type);
}
