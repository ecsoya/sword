package com.soyatec.sword.mining.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soyatec.sword.common.core.text.Convert;
import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.mining.domain.MiningSymbol;
import com.soyatec.sword.mining.mapper.MiningSymbolMapper;
import com.soyatec.sword.mining.service.IMiningSymbolService;

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
		return miningSymbolMapper.selectMiningSymbols();
	}
}
