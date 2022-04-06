package com.github.ecsoya.sword.token.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.IdWorker;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.uuid.UUID;
import com.github.ecsoya.sword.token.domain.TokenSecret;
import com.github.ecsoya.sword.token.mapper.TokenSecretMapper;
import com.github.ecsoya.sword.token.service.ITokenSecretService;

/**
 * The Class TokenSecretServiceImpl.
 */
@Service
public class TokenSecretServiceImpl implements ITokenSecretService {

	/** The token secret mapper. */
	@Autowired
	private TokenSecretMapper tokenSecretMapper;

	/**
	 * Select token secret by id.
	 *
	 * @param id the id
	 * @return the token secret
	 */
	@Override
	public TokenSecret selectTokenSecretById(Long id) {
		return tokenSecretMapper.selectTokenSecretById(id);
	}

	/**
	 * Select token secret list.
	 *
	 * @param tokenSecret the token secret
	 * @return the list
	 */
	@Override
	public List<TokenSecret> selectTokenSecretList(TokenSecret tokenSecret) {
		return tokenSecretMapper.selectTokenSecretList(tokenSecret);
	}

	/**
	 * Insert token secret.
	 *
	 * @param tokenSecret the token secret
	 * @return the int
	 */
	@Override
	public int insertTokenSecret(TokenSecret tokenSecret) {
		if (tokenSecret.getId() == null) {
			tokenSecret.setId(IdWorker.getId());
		}
		if (tokenSecret.getCreateTime() == null) {
			tokenSecret.setCreateTime(DateUtils.getNowDate());
		}
		return tokenSecretMapper.insertTokenSecret(tokenSecret);
	}

	/**
	 * Update token secret.
	 *
	 * @param tokenSecret the token secret
	 * @return the int
	 */
	@Override
	public int updateTokenSecret(TokenSecret tokenSecret) {
		tokenSecret.setUpdateTime(DateUtils.getNowDate());
		return tokenSecretMapper.updateTokenSecret(tokenSecret);
	}

	/**
	 * Delete token secret by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteTokenSecretByIds(String ids) {
		return tokenSecretMapper.deleteTokenSecretByIds(Convert.toStrArray(ids));
	}

	/**
	 * Delete token secret by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Override
	public int deleteTokenSecretById(Long id) {
		return tokenSecretMapper.deleteTokenSecretById(id);
	}

	/**
	 * Select token secret key.
	 *
	 * @param accessKey the access key
	 * @return the string
	 */
	@Override
	public String selectTokenSecretKey(String accessKey) {
		if (StringUtils.isEmpty(accessKey)) {
			return null;
		}
		return tokenSecretMapper.selectTokenSecretKey(accessKey);
	}

	/**
	 * Generate token secret.
	 *
	 * @return the int
	 */
	@Override
	public int generateTokenSecret() {
		final TokenSecret token = new TokenSecret();
		token.setAccessKey(UUID.fastUUID().toString());
		token.setSecretKey(IdWorker.get32UUID());
		return insertTokenSecret(token);
	}
}
