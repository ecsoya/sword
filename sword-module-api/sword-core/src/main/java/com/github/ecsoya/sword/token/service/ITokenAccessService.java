package com.github.ecsoya.sword.token.service;

import java.util.List;

import com.github.ecsoya.sword.token.domain.TokenAccess;

/**
 * The Interface ITokenAccessService.
 */
public interface ITokenAccessService {

	/**
	 * Select token access by id.
	 *
	 * @param operId the oper id
	 * @return the token access
	 */
	public TokenAccess selectTokenAccessById(Long operId);

	/**
	 * Select token access list.
	 *
	 * @param tokenAccess the token access
	 * @return the list
	 */
	public List<TokenAccess> selectTokenAccessList(TokenAccess tokenAccess);

	/**
	 * Insert token access.
	 *
	 * @param tokenAccess the token access
	 * @return the int
	 */
	public int insertTokenAccess(TokenAccess tokenAccess);

	/**
	 * Update token access.
	 *
	 * @param tokenAccess the token access
	 * @return the int
	 */
	public int updateTokenAccess(TokenAccess tokenAccess);

	/**
	 * Delete token access by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteTokenAccessByIds(String ids);

	/**
	 * Delete token access by id.
	 *
	 * @param operId the oper id
	 * @return the int
	 */
	public int deleteTokenAccessById(Long operId);
}
