package com.github.ecsoya.sword.token.mapper;

import java.util.List;

import com.github.ecsoya.sword.token.domain.TokenSecret;

/**
 * The Interface TokenSecretMapper.
 */
public interface TokenSecretMapper {

	/**
	 * Select token secret by id.
	 *
	 * @param id the id
	 * @return the token secret
	 */
	public TokenSecret selectTokenSecretById(Long id);

	/**
	 * Select token secret list.
	 *
	 * @param tokenSecret the token secret
	 * @return the list
	 */
	public List<TokenSecret> selectTokenSecretList(TokenSecret tokenSecret);

	/**
	 * Insert token secret.
	 *
	 * @param tokenSecret the token secret
	 * @return the int
	 */
	public int insertTokenSecret(TokenSecret tokenSecret);

	/**
	 * Update token secret.
	 *
	 * @param tokenSecret the token secret
	 * @return the int
	 */
	public int updateTokenSecret(TokenSecret tokenSecret);

	/**
	 * Delete token secret by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteTokenSecretById(Long id);

	/**
	 * Delete token secret by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteTokenSecretByIds(String[] ids);

	/**
	 * Select token secret key.
	 *
	 * @param accessKey the access key
	 * @return the string
	 */
	public String selectTokenSecretKey(String accessKey);
}
