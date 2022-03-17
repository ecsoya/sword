package com.github.ecsoya.sword.token.mapper;

import java.util.List;

import com.github.ecsoya.sword.token.domain.TokenSecret;

/**
 * 开放接口APIMapper接口
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-07
 */
public interface TokenSecretMapper {
	/**
	 * 查询开放接口API
	 *
	 * @param id 开放接口APIID
	 * @return 开放接口API
	 */
	public TokenSecret selectTokenSecretById(Long id);

	/**
	 * 查询开放接口API列表
	 *
	 * @param tokenSecret 开放接口API
	 * @return 开放接口API集合
	 */
	public List<TokenSecret> selectTokenSecretList(TokenSecret tokenSecret);

	/**
	 * 新增开放接口API
	 *
	 * @param tokenSecret 开放接口API
	 * @return 结果
	 */
	public int insertTokenSecret(TokenSecret tokenSecret);

	/**
	 * 修改开放接口API
	 *
	 * @param tokenSecret 开放接口API
	 * @return 结果
	 */
	public int updateTokenSecret(TokenSecret tokenSecret);

	/**
	 * 删除开放接口API
	 *
	 * @param id 开放接口APIID
	 * @return 结果
	 */
	public int deleteTokenSecretById(Long id);

	/**
	 * 批量删除开放接口API
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteTokenSecretByIds(String[] ids);

	public String selectTokenSecretKey(String accessKey);
}
