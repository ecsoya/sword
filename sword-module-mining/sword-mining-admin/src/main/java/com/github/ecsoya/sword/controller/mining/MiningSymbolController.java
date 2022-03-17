package com.github.ecsoya.sword.controller.mining;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.ecsoya.sword.common.annotation.Log;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.page.TableDataInfo;
import com.github.ecsoya.sword.common.enums.BusinessType;
import com.github.ecsoya.sword.mining.domain.MiningSymbol;
import com.github.ecsoya.sword.mining.service.IMiningSymbolService;

/**
 * 币种Controller
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-18
 */
@Controller
@RequestMapping("/mining/symbol")
public class MiningSymbolController extends BaseController {
	private final String prefix = "mining/symbol";

	@Autowired
	private IMiningSymbolService miningSymbolService;

	@RequiresPermissions("mining:symbol:view")
	@GetMapping()
	public String symbol() {
		return prefix + "/symbol";
	}

	/**
	 * 查询币种列表
	 */
	@RequiresPermissions("mining:symbol:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(MiningSymbol miningSymbol) {
		startPage();
		final List<MiningSymbol> list = miningSymbolService.selectMiningSymbolList(miningSymbol);
		return getDataTable(list);
	}

	/**
	 * 修改币种
	 */
	@GetMapping("/edit/{symbol}")
	public String edit(@PathVariable("symbol") String symbol, ModelMap mmap) {
		final MiningSymbol miningSymbol = miningSymbolService.selectMiningSymbolById(symbol);
		mmap.put("miningSymbol", miningSymbol);
		return prefix + "/edit";
	}

	/**
	 * 修改保存币种
	 */
	@RequiresPermissions("mining:symbol:edit")
	@Log(title = "修改提币设置", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(MiningSymbol miningSymbol) {
		return toAjax(miningSymbolService.updateMiningSymbol(miningSymbol));
	}

	/**
	 * 修改保存币种
	 */
	@RequiresPermissions("mining:symbol:edit")
	@Log(title = "启停提币开关", businessType = BusinessType.UPDATE)
	@PostMapping("/changeStatus")
	@ResponseBody
	public AjaxResult changeStatus(String symbol, Integer withdrawalEnabled) {
		final MiningSymbol miningSymbol = new MiningSymbol();
		miningSymbol.setSymbol(symbol);
		miningSymbol.setWithdrawalEnabled(withdrawalEnabled);
		return toAjax(miningSymbolService.updateMiningSymbol(miningSymbol));
	}

	/**
	 * 修改保存币种
	 */
	@RequiresPermissions("mining:symbol:edit")
	@Log(title = "启停提币人工审核", businessType = BusinessType.UPDATE)
	@PostMapping("/changeManualAudit")
	@ResponseBody
	public AjaxResult changeManualAudit(String symbol, Integer withdrawalManualAudit) {
		final MiningSymbol miningSymbol = new MiningSymbol();
		miningSymbol.setSymbol(symbol);
		miningSymbol.setWithdrawalManualAudit(withdrawalManualAudit);
		return toAjax(miningSymbolService.updateMiningSymbol(miningSymbol));
	}

	/**
	 * 修改保存币种
	 */
	@RequiresPermissions("mining:symbol:edit")
	@Log(title = "启停提币钱包二次审核", businessType = BusinessType.UPDATE)
	@PostMapping("/changeWalletAudit")
	@ResponseBody
	public AjaxResult changeWalletlAudit(String symbol, Integer withdrawalWalletAudit) {
		final MiningSymbol miningSymbol = new MiningSymbol();
		miningSymbol.setSymbol(symbol);
		miningSymbol.setWithdrawalWalletAudit(withdrawalWalletAudit);
		return toAjax(miningSymbolService.updateMiningSymbol(miningSymbol));
	}

}
