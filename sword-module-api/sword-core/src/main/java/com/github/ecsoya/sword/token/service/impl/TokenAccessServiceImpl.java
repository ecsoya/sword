package com.github.ecsoya.sword.token.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.token.domain.TokenAccess;
import com.github.ecsoya.sword.token.mapper.TokenAccessMapper;
import com.github.ecsoya.sword.token.service.ITokenAccessService;

/**
 * The Class TokenAccessServiceImpl.
 */
@Service
public class TokenAccessServiceImpl implements ITokenAccessService {

	/** The token access mapper. */
	@Autowired
	private TokenAccessMapper tokenAccessMapper;

	/**
	 * Select token access by id.
	 *
	 * @param operId the oper id
	 * @return the token access
	 */
	@Override
	public TokenAccess selectTokenAccessById(Long operId) {
		return tokenAccessMapper.selectTokenAccessById(operId);
	}

	/**
	 * Select token access list.
	 *
	 * @param tokenAccess the token access
	 * @return the list
	 */
	@Override
	public List<TokenAccess> selectTokenAccessList(TokenAccess tokenAccess) {
		return tokenAccessMapper.selectTokenAccessList(tokenAccess);
	}

	/**
	 * Insert token access.
	 *
	 * @param tokenAccess the token access
	 * @return the int
	 */
	@Override
	public int insertTokenAccess(TokenAccess tokenAccess) {
		return tokenAccessMapper.insertTokenAccess(tokenAccess);
	}

	/**
	 * Update token access.
	 *
	 * @param tokenAccess the token access
	 * @return the int
	 */
	@Override
	public int updateTokenAccess(TokenAccess tokenAccess) {
		return tokenAccessMapper.updateTokenAccess(tokenAccess);
	}

	/**
	 * Delete token access by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteTokenAccessByIds(String ids) {
		return tokenAccessMapper.deleteTokenAccessByIds(Convert.toStrArray(ids));
	}

	/**
	 * Delete token access by id.
	 *
	 * @param operId the oper id
	 * @return the int
	 */
	@Override
	public int deleteTokenAccessById(Long operId) {
		return tokenAccessMapper.deleteTokenAccessById(operId);
	}
}
