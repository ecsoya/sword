package com.soyatec.sword.mining.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.utils.SwordUtils;
import com.soyatec.sword.wallet.domain.UserWalletAddress;
import com.soyatec.sword.wallet.service.IUserWalletAddressService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 钱包地址接口
 * 
 * @author ecsoya
 */
@RestController
@RequestMapping("/user/address")
@Api(tags = { "提币地址" }, description = "增、删、改、查")
public class UserAddressController extends BaseController {

	@Autowired
	private IUserWalletAddressService userAddressService;

	/**
	 * 查询所有钱包地址
	 */
	@ApiOperation("提币地址列表")
	@GetMapping("/list")
	public CommonResult<List<UserWalletAddress>> list(String symbol) {
		Long userId = SwordUtils.getUserId();
		return CommonResult.build(userAddressService.selectUserWalletAddressByUserId(userId, symbol));
	}

	@ApiOperation("添加提币地址")
	@PostMapping("/add")
	public CommonResult<?> add(String name, String symbol, String address) {
		if (StringUtils.isEmpty(address) || StringUtils.isEmpty(symbol) || StringUtils.isEmpty(name)) {
			return CommonResult.fail("Empty Address");
		}
		UserWalletAddress walletAddress = new UserWalletAddress();
		walletAddress.setSymbol(symbol);
		walletAddress.setAddress(address);
		walletAddress.setName(name);
		walletAddress.setUserId(SwordUtils.getUserId());
		return CommonResult.ajax(userAddressService.insertUserWalletAddress(walletAddress));
	}

	/**
	 * 更新提现或转账地址
	 * 
	 */
	@ApiOperation("更新提币地址")
	@PostMapping("/edit")
	public CommonResult<?> edit(String name, Long id, String address) {
		if (id == null) {
			return CommonResult.fail();
		}
		UserWalletAddress walletAddress = new UserWalletAddress();
		walletAddress.setId(id);
		walletAddress.setAddress(address);
		walletAddress.setName(name);
		return CommonResult.ajax(userAddressService.updateUserWalletAddress(walletAddress));
	}

	/**
	 * 删除提现或转账地址
	 * 
	 */
	@ApiOperation("删除提币地址")
	@PostMapping("/remove")
	public CommonResult<?> remove(String ids) {
		if (StringUtils.isEmpty(ids)) {
			return CommonResult.fail();
		}
		return CommonResult.ajax(userAddressService.deleteUserWalletAddressByIds(ids));
	}
}
