package com.github.ecsoya.sword.mining.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.constants.IMiningConstants;
import com.github.ecsoya.sword.mining.domain.MiningSymbol;
import com.github.ecsoya.sword.mining.mapper.MiningSymbolMapper;
import com.github.ecsoya.sword.mining.service.IMiningSymbolService;

/**
 * The Class MiningSymbolServiceImpl.
 */
@Service
public class MiningSymbolServiceImpl implements IMiningSymbolService {

	/** The mining symbol mapper. */
	@Autowired
	private MiningSymbolMapper miningSymbolMapper;

	/**
	 * Select mining symbol by id.
	 *
	 * @param symbol the symbol
	 * @return the mining symbol
	 */
	@Override
	public MiningSymbol selectMiningSymbolById(String symbol) {
		return miningSymbolMapper.selectMiningSymbolById(symbol);
	}

	/**
	 * Select mining symbol list.
	 *
	 * @param miningSymbol the mining symbol
	 * @return the list
	 */
	@Override
	public List<MiningSymbol> selectMiningSymbolList(MiningSymbol miningSymbol) {
		return miningSymbolMapper.selectMiningSymbolList(miningSymbol);
	}

	/**
	 * Insert mining symbol.
	 *
	 * @param miningSymbol the mining symbol
	 * @return the int
	 */
	@Override
	public int insertMiningSymbol(MiningSymbol miningSymbol) {
		if (miningSymbol.getCreateTime() == null) {
			miningSymbol.setCreateTime(DateUtils.getNowDate());
		}
		return miningSymbolMapper.insertMiningSymbol(miningSymbol);
	}

	/**
	 * Update mining symbol.
	 *
	 * @param miningSymbol the mining symbol
	 * @return the int
	 */
	@Override
	public int updateMiningSymbol(MiningSymbol miningSymbol) {
		miningSymbol.setUpdateTime(DateUtils.getNowDate());
		return miningSymbolMapper.updateMiningSymbol(miningSymbol);
	}

	/**
	 * Delete mining symbol by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteMiningSymbolByIds(String ids) {
		return miningSymbolMapper.deleteMiningSymbolByIds(Convert.toStrArray(ids));
	}

	/**
	 * Delete mining symbol by id.
	 *
	 * @param symbol the symbol
	 * @return the int
	 */
	@Override
	public int deleteMiningSymbolById(String symbol) {
		return miningSymbolMapper.deleteMiningSymbolById(symbol);
	}

	/**
	 * Select mining symbols.
	 *
	 * @return the list
	 */
	@Override
	public List<String> selectMiningSymbols() {
		return selectMiningSymbols(false);
	}

	/**
	 * Select mining symbols.
	 *
	 * @param onlyBitcoin the only bitcoin
	 * @return the list
	 */
	@Override
	public List<String> selectMiningSymbols(boolean onlyBitcoin) {
		Integer type = onlyBitcoin ? MiningSymbol.TYPE_BITCOIN : null;
		return miningSymbolMapper.selectMiningSymbols(type);
	}

	/**
	 * Select mining symbol chain.
	 *
	 * @param symbol the symbol
	 * @return the string
	 */
	@Override
	public String selectMiningSymbolChain(String symbol) {
		if (StringUtils.isEmpty(symbol)) {
			return null;
		}
		String chain = miningSymbolMapper.selectMiningSymbolChain(symbol);
		if (chain == null) {
			chain = IMiningConstants.CHAIN_DEFAULT;
		}
		return chain;
	}
}
