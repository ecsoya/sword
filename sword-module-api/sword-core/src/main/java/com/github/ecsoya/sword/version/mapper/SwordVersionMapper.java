package com.github.ecsoya.sword.version.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.ecsoya.sword.version.domain.SwordVersion;

/**
 * 版本Mapper接口
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-22
 */
public interface SwordVersionMapper {
	/**
	 * 查询版本
	 *
	 * @param id 版本ID
	 * @return 版本
	 */
	public SwordVersion selectSwordVersionById(Long id);

	/**
	 * 查询版本列表
	 *
	 * @param swordVersion 版本
	 * @return 版本集合
	 */
	public List<SwordVersion> selectSwordVersionList(SwordVersion swordVersion);

	/**
	 * 新增版本
	 *
	 * @param swordVersion 版本
	 * @return 结果
	 */
	public int insertSwordVersion(SwordVersion swordVersion);

	/**
	 * 修改版本
	 *
	 * @param swordVersion 版本
	 * @return 结果
	 */
	public int updateSwordVersion(SwordVersion swordVersion);

	/**
	 * 删除版本
	 *
	 * @param id 版本ID
	 * @return 结果
	 */
	public int deleteSwordVersionById(Long id);

	/**
	 * 批量删除版本
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteSwordVersionByIds(String[] ids);

	public SwordVersion selectLatestSwordVersion(@Param("type") String type);
}
