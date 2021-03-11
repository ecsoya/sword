package com.soyatec.sword.token.mapper;

import java.util.List;

import com.soyatec.sword.token.domain.TokenAccess;

/**
 * 操作日志记录Mapper接口
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-07
 */
public interface TokenAccessMapper {
	/**
	 * 查询操作日志记录
	 * 
	 * @param operId 操作日志记录ID
	 * @return 操作日志记录
	 */
	public TokenAccess selectTokenAccessById(Long operId);

	/**
	 * 查询操作日志记录列表
	 * 
	 * @param tokenAccess 操作日志记录
	 * @return 操作日志记录集合
	 */
	public List<TokenAccess> selectTokenAccessList(TokenAccess tokenAccess);

	/**
	 * 新增操作日志记录
	 * 
	 * @param tokenAccess 操作日志记录
	 * @return 结果
	 */
	public int insertTokenAccess(TokenAccess tokenAccess);

	/**
	 * 修改操作日志记录
	 * 
	 * @param tokenAccess 操作日志记录
	 * @return 结果
	 */
	public int updateTokenAccess(TokenAccess tokenAccess);

	/**
	 * 删除操作日志记录
	 * 
	 * @param operId 操作日志记录ID
	 * @return 结果
	 */
	public int deleteTokenAccessById(Long operId);

	/**
	 * 批量删除操作日志记录
	 * 
	 * @param operIds 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteTokenAccessByIds(String[] operIds);
}
