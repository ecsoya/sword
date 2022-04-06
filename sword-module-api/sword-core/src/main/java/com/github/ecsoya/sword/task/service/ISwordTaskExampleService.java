package com.github.ecsoya.sword.task.service;

import java.util.List;

import com.github.ecsoya.sword.task.domain.SwordTaskExample;

/**
 * The Interface ISwordTaskExampleService.
 */
public interface ISwordTaskExampleService {

	/**
	 * Select sword task example by id.
	 *
	 * @param id the id
	 * @return the sword task example
	 */
	public SwordTaskExample selectSwordTaskExampleById(Long id);

	/**
	 * Select sword task example list.
	 *
	 * @param swordTaskExample the sword task example
	 * @return the list
	 */
	public List<SwordTaskExample> selectSwordTaskExampleList(SwordTaskExample swordTaskExample);

	/**
	 * Insert sword task example.
	 *
	 * @param swordTaskExample the sword task example
	 * @return the int
	 */
	public int insertSwordTaskExample(SwordTaskExample swordTaskExample);

	/**
	 * Update sword task example.
	 *
	 * @param swordTaskExample the sword task example
	 * @return the int
	 */
	public int updateSwordTaskExample(SwordTaskExample swordTaskExample);

	/**
	 * Delete sword task example by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteSwordTaskExampleByIds(String ids);

	/**
	 * Delete sword task example by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteSwordTaskExampleById(Long id);

	/**
	 * Select last sword task example.
	 *
	 * @return the sword task example
	 */
	public SwordTaskExample selectLastSwordTaskExample();
}
