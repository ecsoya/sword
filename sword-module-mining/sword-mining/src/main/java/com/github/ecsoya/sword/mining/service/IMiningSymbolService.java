package com.github.ecsoya.sword.mining.service;

import java.util.List;

import com.github.ecsoya.sword.mining.domain.MiningSymbol;

/**
 * The Interface IMiningSymbolService.
 */
public interface IMiningSymbolService {

	/**
	 * Select mining symbol by id.
	 *
	 * @param symbol the symbol
	 * @return the mining symbol
	 */
	public MiningSymbol selectMiningSymbolById(String symbol);

	/**
	 * Select mining symbol list.
	 *
	 * @param miningSymbol the mining symbol
	 * @return the list
	 */
	public List<MiningSymbol> selectMiningSymbolList(MiningSymbol miningSymbol);

	/**
	 * Insert mining symbol.
	 *
	 * @param miningSymbol the mining symbol
	 * @return the int
	 */
	public int insertMiningSymbol(MiningSymbol miningSymbol);

	/**
	 * Update mining symbol.
	 *
	 * @param miningSymbol the mining symbol
	 * @return the int
	 */
	public int updateMiningSymbol(MiningSymbol miningSymbol);

	/**
	 * Delete mining symbol by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteMiningSymbolByIds(String ids);

	/**
	 * Delete mining symbol by id.
	 *
	 * @param symbol the symbol
	 * @return the int
	 */
	public int deleteMiningSymbolById(String symbol);

	/**
	 * Select mining symbols.
	 *
	 * @return the list
	 */
	List<String> selectMiningSymbols();

	/**
	 * Select mining symbols.
	 *
	 * @param onlyBitcoin the only bitcoin
	 * @return the list
	 */
	List<String> selectMiningSymbols(boolean onlyBitcoin);

	/**
	 * Select mining symbol chain.
	 *
	 * @param symbol the symbol
	 * @return the string
	 */
	public String selectMiningSymbolChain(String symbol);
}
