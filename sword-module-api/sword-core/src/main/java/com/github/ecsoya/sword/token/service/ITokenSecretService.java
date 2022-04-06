package com.github.ecsoya.sword.token.service;

import java.util.List;

import com.github.ecsoya.sword.token.domain.TokenSecret;

/**
 * The Interface ITokenSecretService.
 */
public interface ITokenSecretService {

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
	 * Delete token secret by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteTokenSecretByIds(String ids);

	/**
	 * Delete token secret by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteTokenSecretById(Long id);

	/**
	 * Select token secret key.
	 *
	 * @param accessKey the access key
	 * @return the string
	 */
	public String selectTokenSecretKey(String accessKey);

	/**
	 * Generate token secret.
	 *
	 * @return the int
	 */
	public int generateTokenSecret();
}
