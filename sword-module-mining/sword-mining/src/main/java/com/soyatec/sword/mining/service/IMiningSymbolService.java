package com.soyatec.sword.mining.service;

import java.util.List;

import com.soyatec.sword.mining.domain.MiningSymbol;

/**
 * 币种Service接口
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-08
 */
public interface IMiningSymbolService {
	/**
	 * 查询币种
	 *
	 * @param symbol 币种ID
	 * @return 币种
	 */
	public MiningSymbol selectMiningSymbolById(String symbol);

	/**
	 * 查询币种列表
	 *
	 * @param miningSymbol 币种
	 * @return 币种集合
	 */
	public List<MiningSymbol> selectMiningSymbolList(MiningSymbol miningSymbol);

	/**
	 * 新增币种
	 *
	 * @param miningSymbol 币种
	 * @return 结果
	 */
	public int insertMiningSymbol(MiningSymbol miningSymbol);

	/**
	 * 修改币种
	 *
	 * @param miningSymbol 币种
	 * @return 结果
	 */
	public int updateMiningSymbol(MiningSymbol miningSymbol);

	/**
	 * 批量删除币种
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteMiningSymbolByIds(String ids);

	/**
	 * 删除币种信息
	 *
	 * @param symbol 币种ID
	 * @return 结果
	 */
	public int deleteMiningSymbolById(String symbol);

	List<String> selectMiningSymbols();

	List<String> selectMiningSymbols(boolean onlyBitcoin);

	public String selectMiningSymbolChain(String symbol);
}
