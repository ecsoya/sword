package com.github.ecsoya.sword.mining.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ecsoya.sword.common.annotation.RepeatSubmit;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.utils.SwordUtils;
import com.github.ecsoya.sword.wallet.domain.UserWalletAddress;
import com.github.ecsoya.sword.wallet.service.IUserWalletAddressService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * The Class UserAddressController.
 */
@RestController
@RequestMapping("/user/address")
@Api(tags = { "提币地址" }, description = "增、删、改、查")
public class UserAddressController extends BaseController {

	/** The user address service. */
	@Autowired
	private IUserWalletAddressService userAddressService;

	/**
	 * List.
	 *
	 * @param symbol the symbol
	 * @return the common result
	 */
	@ApiOperation("提币地址列表")
	@GetMapping("/list")
	public CommonResult<List<UserWalletAddress>> list(String symbol) {
		final Long userId = SwordUtils.getUserId();
		return CommonResult.build(userAddressService.selectUserWalletAddressByUserId(userId, symbol));
	}

	/**
	 * Adds the.
	 *
	 * @param name    the name
	 * @param symbol  the symbol
	 * @param address the address
	 * @return the common result
	 */
	@ApiOperation("添加提币地址")
	@PostMapping("/add")
	@RepeatSubmit
	public CommonResult<?> add(String name, String symbol, String address) {
		if (StringUtils.isEmpty(address) || StringUtils.isEmpty(symbol) || StringUtils.isEmpty(name)) {
			return CommonResult.fail("Empty Address");
		}
		final UserWalletAddress walletAddress = new UserWalletAddress();
		walletAddress.setSymbol(symbol);
		walletAddress.setAddress(address);
		walletAddress.setName(name);
		walletAddress.setUserId(SwordUtils.getUserId());
		return CommonResult.ajax(userAddressService.insertUserWalletAddress(walletAddress));
	}

	/**
	 * Edits the.
	 *
	 * @param name    the name
	 * @param id      the id
	 * @param address the address
	 * @return the common result
	 */
	@ApiOperation("更新提币地址")
	@PostMapping("/edit")
	@RepeatSubmit
	public CommonResult<?> edit(String name, Long id, String address) {
		if (id == null) {
			return CommonResult.fail();
		}
		final UserWalletAddress walletAddress = new UserWalletAddress();
		walletAddress.setId(id);
		walletAddress.setAddress(address);
		walletAddress.setName(name);
		return CommonResult.ajax(userAddressService.updateUserWalletAddress(walletAddress));
	}

	/**
	 * Removes the.
	 *
	 * @param ids the ids
	 * @return the common result
	 */
	@ApiOperation("删除提币地址")
	@PostMapping("/remove")
	@RepeatSubmit
	public CommonResult<?> remove(String ids) {
		if (StringUtils.isEmpty(ids)) {
			return CommonResult.fail();
		}
		return CommonResult.ajax(userAddressService.deleteUserWalletAddressByIds(ids));
	}
}
