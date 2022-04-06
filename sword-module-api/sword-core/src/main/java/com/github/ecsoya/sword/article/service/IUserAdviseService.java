package com.github.ecsoya.sword.article.service;

import java.util.List;

import com.github.ecsoya.sword.article.domain.UserAdvise;

/**
 * The Interface IUserAdviseService.
 */
public interface IUserAdviseService {

	/**
	 * Select user advise by id.
	 *
	 * @param id the id
	 * @return the user advise
	 */
	public UserAdvise selectUserAdviseById(Long id);

	/**
	 * Select user advise list.
	 *
	 * @param userAdvise the user advise
	 * @return the list
	 */
	public List<UserAdvise> selectUserAdviseList(UserAdvise userAdvise);

	/**
	 * Insert user advise.
	 *
	 * @param userAdvise the user advise
	 * @return the int
	 */
	public int insertUserAdvise(UserAdvise userAdvise);

	/**
	 * Update user advise.
	 *
	 * @param userAdvise the user advise
	 * @return the int
	 */
	public int updateUserAdvise(UserAdvise userAdvise);

	/**
	 * Delete user advise by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteUserAdviseByIds(String ids);

	/**
	 * Delete user advise by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteUserAdviseById(Long id);

	/**
	 * Select user advise list by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	public List<UserAdvise> selectUserAdviseListByUserId(Long userId);

}
