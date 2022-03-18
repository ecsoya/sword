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
 * 币种Service业务层处理
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-08
 */
@Service
public class MiningSymbolServiceImpl implements IMiningSymbolService {
	@Autowired
	private MiningSymbolMapper miningSymbolMapper;

	/**
	 * 查询币种
	 *
	 * @param symbol 币种ID
	 * @return 币种
	 */
	@Override
	public MiningSymbol selectMiningSymbolById(String symbol) {
		return miningSymbolMapper.selectMiningSymbolById(symbol);
	}

	/**
	 * 查询币种列表
	 *
	 * @param miningSymbol 币种
	 * @return 币种
	 */
	@Override
	public List<MiningSymbol> selectMiningSymbolList(MiningSymbol miningSymbol) {
		return miningSymbolMapper.selectMiningSymbolList(miningSymbol);
	}

	/**
	 * 新增币种
	 *
	 * @param miningSymbol 币种
	 * @return 结果
	 */
	@Override
	public int insertMiningSymbol(MiningSymbol miningSymbol) {
		if (miningSymbol.getCreateTime() == null) {
			miningSymbol.setCreateTime(DateUtils.getNowDate());
		}
		return miningSymbolMapper.insertMiningSymbol(miningSymbol);
	}

	/**
	 * 修改币种
	 *
	 * @param miningSymbol 币种
	 * @return 结果
	 */
	@Override
	public int updateMiningSymbol(MiningSymbol miningSymbol) {
		miningSymbol.setUpdateTime(DateUtils.getNowDate());
		return miningSymbolMapper.updateMiningSymbol(miningSymbol);
	}

	/**
	 * 删除币种对象
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteMiningSymbolByIds(String ids) {
		return miningSymbolMapper.deleteMiningSymbolByIds(Convert.toStrArray(ids));
	}

	/**
	 * 删除币种信息
	 *
	 * @param symbol 币种ID
	 * @return 结果
	 */
	@Override
	public int deleteMiningSymbolById(String symbol) {
		return miningSymbolMapper.deleteMiningSymbolById(symbol);
	}

	@Override
	public List<String> selectMiningSymbols() {
		return selectMiningSymbols(false);
	}

	@Override
	public List<String> selectMiningSymbols(boolean onlyBitcoin) {
		Integer type = onlyBitcoin ? MiningSymbol.TYPE_BITCOIN : null;
		return miningSymbolMapper.selectMiningSymbols(type);
	}

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