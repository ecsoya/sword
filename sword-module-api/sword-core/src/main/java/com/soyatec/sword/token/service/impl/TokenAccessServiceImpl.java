package com.soyatec.sword.token.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soyatec.sword.common.core.text.Convert;
import com.soyatec.sword.token.domain.TokenAccess;
import com.soyatec.sword.token.mapper.TokenAccessMapper;
import com.soyatec.sword.token.service.ITokenAccessService;

/**
 * 操作日志记录Service业务层处理
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-07
 */
@Service
public class TokenAccessServiceImpl implements ITokenAccessService {
	@Autowired
	private TokenAccessMapper tokenAccessMapper;

	/**
	 * 查询操作日志记录
	 *
	 * @param operId 操作日志记录ID
	 * @return 操作日志记录
	 */
	@Override
	public TokenAccess selectTokenAccessById(Long operId) {
		return tokenAccessMapper.selectTokenAccessById(operId);
	}

	/**
	 * 查询操作日志记录列表
	 *
	 * @param tokenAccess 操作日志记录
	 * @return 操作日志记录
	 */
	@Override
	public List<TokenAccess> selectTokenAccessList(TokenAccess tokenAccess) {
		return tokenAccessMapper.selectTokenAccessList(tokenAccess);
	}

	/**
	 * 新增操作日志记录
	 *
	 * @param tokenAccess 操作日志记录
	 * @return 结果
	 */
	@Override
	public int insertTokenAccess(TokenAccess tokenAccess) {
		return tokenAccessMapper.insertTokenAccess(tokenAccess);
	}

	/**
	 * 修改操作日志记录
	 *
	 * @param tokenAccess 操作日志记录
	 * @return 结果
	 */
	@Override
	public int updateTokenAccess(TokenAccess tokenAccess) {
		return tokenAccessMapper.updateTokenAccess(tokenAccess);
	}

	/**
	 * 删除操作日志记录对象
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteTokenAccessByIds(String ids) {
		return tokenAccessMapper.deleteTokenAccessByIds(Convert.toStrArray(ids));
	}

	/**
	 * 删除操作日志记录信息
	 *
	 * @param operId 操作日志记录ID
	 * @return 结果
	 */
	@Override
	public int deleteTokenAccessById(Long operId) {
		return tokenAccessMapper.deleteTokenAccessById(operId);
	}
}
