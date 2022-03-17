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
 * 开放接口APIService业务层处理
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-07
 */
@Service
public class TokenSecretServiceImpl implements ITokenSecretService {
	@Autowired
	private TokenSecretMapper tokenSecretMapper;

	/**
	 * 查询开放接口API
	 *
	 * @param id 开放接口APIID
	 * @return 开放接口API
	 */
	@Override
	public TokenSecret selectTokenSecretById(Long id) {
		return tokenSecretMapper.selectTokenSecretById(id);
	}

	/**
	 * 查询开放接口API列表
	 *
	 * @param tokenSecret 开放接口API
	 * @return 开放接口API
	 */
	@Override
	public List<TokenSecret> selectTokenSecretList(TokenSecret tokenSecret) {
		return tokenSecretMapper.selectTokenSecretList(tokenSecret);
	}

	/**
	 * 新增开放接口API
	 *
	 * @param tokenSecret 开放接口API
	 * @return 结果
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
	 * 修改开放接口API
	 *
	 * @param tokenSecret 开放接口API
	 * @return 结果
	 */
	@Override
	public int updateTokenSecret(TokenSecret tokenSecret) {
		tokenSecret.setUpdateTime(DateUtils.getNowDate());
		return tokenSecretMapper.updateTokenSecret(tokenSecret);
	}

	/**
	 * 删除开放接口API对象
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteTokenSecretByIds(String ids) {
		return tokenSecretMapper.deleteTokenSecretByIds(Convert.toStrArray(ids));
	}

	/**
	 * 删除开放接口API信息
	 *
	 * @param id 开放接口APIID
	 * @return 结果
	 */
	@Override
	public int deleteTokenSecretById(Long id) {
		return tokenSecretMapper.deleteTokenSecretById(id);
	}

	@Override
	public String selectTokenSecretKey(String accessKey) {
		if (StringUtils.isEmpty(accessKey)) {
			return null;
		}
		return tokenSecretMapper.selectTokenSecretKey(accessKey);
	}

	@Override
	public int generateTokenSecret() {
		final TokenSecret token = new TokenSecret();
		token.setAccessKey(UUID.fastUUID().toString());
		token.setSecretKey(IdWorker.get32UUID());
		return insertTokenSecret(token);
	}
}
