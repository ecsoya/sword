package com.github.ecsoya.sword.mining.mapper;

import java.util.List;

import com.github.ecsoya.sword.mining.domain.MiningSymbol;

/**
 * The Interface MiningSymbolMapper.
 */
public interface MiningSymbolMapper {

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
	 * Delete mining symbol by id.
	 *
	 * @param symbol the symbol
	 * @return the int
	 */
	public int deleteMiningSymbolById(String symbol);

	/**
	 * Delete mining symbol by ids.
	 *
	 * @param symbols the symbols
	 * @return the int
	 */
	public int deleteMiningSymbolByIds(String[] symbols);

	/**
	 * Select mining symbols.
	 *
	 * @param type the type
	 * @return the list
	 */
	public List<String> selectMiningSymbols(Integer type);

	/**
	 * Select mining symbol chain.
	 *
	 * @param symbol the symbol
	 * @return the string
	 */
	public String selectMiningSymbolChain(String symbol);
}
