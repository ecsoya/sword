package com.soyatec.sword.article.service;

import java.util.List;

import com.soyatec.sword.article.domain.UserAdvise;

/**
 * 用户反馈Service接口
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
public interface IUserAdviseService {
	/**
	 * 查询用户反馈
	 * 
	 * @param id 用户反馈ID
	 * @return 用户反馈
	 */
	public UserAdvise selectUserAdviseById(Long id);

	/**
	 * 查询用户反馈列表
	 * 
	 * @param userAdvise 用户反馈
	 * @return 用户反馈集合
	 */
	public List<UserAdvise> selectUserAdviseList(UserAdvise userAdvise);

	/**
	 * 新增用户反馈
	 * 
	 * @param userAdvise 用户反馈
	 * @return 结果
	 */
	public int insertUserAdvise(UserAdvise userAdvise);

	/**
	 * 修改用户反馈
	 * 
	 * @param userAdvise 用户反馈
	 * @return 结果
	 */
	public int updateUserAdvise(UserAdvise userAdvise);

	/**
	 * 批量删除用户反馈
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteUserAdviseByIds(String ids);

	/**
	 * 删除用户反馈信息
	 * 
	 * @param id 用户反馈ID
	 * @return 结果
	 */
	public int deleteUserAdviseById(Long id);

	public List<UserAdvise> selectUserAdviseListByUserId(Long userId);

}