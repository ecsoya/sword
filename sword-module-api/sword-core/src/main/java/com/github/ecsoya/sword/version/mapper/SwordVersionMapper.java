package com.github.ecsoya.sword.version.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.ecsoya.sword.version.domain.SwordVersion;

/**
 * The Interface SwordVersionMapper.
 */
public interface SwordVersionMapper {

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
	 * Delete sword version by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteSwordVersionById(Long id);

	/**
	 * Delete sword version by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteSwordVersionByIds(String[] ids);

	/**
	 * Select latest sword version.
	 *
	 * @param type the type
	 * @return the sword version
	 */
	public SwordVersion selectLatestSwordVersion(@Param("type") String type);
}
