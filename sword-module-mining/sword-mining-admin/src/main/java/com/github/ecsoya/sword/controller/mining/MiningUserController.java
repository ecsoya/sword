package com.github.ecsoya.sword.controller.mining;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.ecsoya.sword.common.annotation.Log;
import com.github.ecsoya.sword.common.config.GlobalConfig;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.core.domain.entity.SysUser;
import com.github.ecsoya.sword.common.core.page.TableDataInfo;
import com.github.ecsoya.sword.common.enums.BusinessType;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.constants.IMiningConstants;
import com.github.ecsoya.sword.mining.domain.MiningLevel;
import com.github.ecsoya.sword.mining.domain.MiningUser;
import com.github.ecsoya.sword.mining.service.IMiningLevelService;
import com.github.ecsoya.sword.mining.service.IMiningSymbolService;
import com.github.ecsoya.sword.mining.service.IMiningUserService;
import com.github.ecsoya.sword.system.service.ISysConfigService;
import com.github.ecsoya.sword.system.service.ISysUserService;
import com.github.ecsoya.sword.user.domain.User;
import com.github.ecsoya.sword.user.domain.UserLevel;
import com.github.ecsoya.sword.user.domain.UserReferrer;
import com.github.ecsoya.sword.user.service.IUserLevelService;
import com.github.ecsoya.sword.user.service.IUserProfileService;
import com.github.ecsoya.sword.user.service.IUserReferrerService;
import com.github.ecsoya.sword.user.service.IUserRegisterService;
import com.github.ecsoya.sword.utils.MathUtils;
import com.github.ecsoya.sword.wallet.service.IUserWalletAccountService;
import com.github.ecsoya.sword.wallet.service.IUserWalletService;

/**
 * The Class MiningUserController.
 */
@Controller
@RequestMapping("/mining/user")
public class MiningUserController extends BaseController {

	/** The Constant prefix. */
	private static final String prefix = "mining/user";

	/** The mining user service. */
	@Autowired
	private IMiningUserService miningUserService;

	/** The config service. */
	@Autowired
	private ISysConfigService configService;

	/** The sys user service. */
	@Autowired
	private ISysUserService sysUserService;

	/** The referrer service. */
	@Autowired
	private IUserReferrerService referrerService;

	/** The user register service. */
	@Autowired
	private IUserRegisterService userRegisterService;

	/** The user profile service. */
	@Autowired
	private IUserProfileService userProfileService;

	/** The user wallet service. */
	@Autowired
	private IUserWalletService userWalletService;

	/** The user wallet account service. */
	@Autowired
	private IUserWalletAccountService userWalletAccountService;

	/** The user level service. */
	@Autowired
	private IUserLevelService userLevelService;

	/** The level service. */
	@Autowired
	private IMiningLevelService levelService;

	/** The data source. */
	@Autowired
	private DataSource dataSource;

	/** The symbol service. */
	@Autowired
	private IMiningSymbolService symbolService;

	/**
	 * Index.
	 *
	 * @param mmap the mmap
	 * @return the string
	 */
	@GetMapping()
	@RequiresPermissions("mining:user:view")
	public String index(ModelMap mmap) {
		mmap.put("levels", levelService.selectMiningLevelList(new MiningLevel()));
		mmap.put("accounts", symbolService.selectMiningSymbols(true));
		return prefix + "/user";
	}

	/**
	 * List.
	 *
	 * @param query the query
	 * @return the table data info
	 */
	@RequiresPermissions("mining:user:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(MiningUser query) {
		return getDataTable(miningUserService.selectMiningUserList(query));
	}

	/**
	 * Adds the.
	 *
	 * @param id   the id
	 * @param mmap the mmap
	 * @return the string
	 */
	@RequiresPermissions("mining:user:add")
	@GetMapping("/add")
	public String add(Long id, ModelMap mmap) {
		if (id == null || id.equals(0l)) {
			id = MathUtils.parseLong(configService.selectConfigValueByKey(IMiningConstants.ROOT_USER_ID));
		}
		mmap.put("parentId", id);
		mmap.put("binaryTreeEnabled", "true"
				.equalsIgnoreCase(configService.selectConfigValueByKey(IMiningConstants.USER_BINARY_TREE_ENABLE)));
		mmap.put("emailEnabled", GlobalConfig.getEmailCode());
		return prefix + "/addUser";
	}

	/**
	 * Check login name unique.
	 *
	 * @param user the user
	 * @return the string
	 */
	@PostMapping("/checkLoginNameUnique")
	@ResponseBody
	public String checkLoginNameUnique(SysUser user) {
		return sysUserService.checkLoginNameUnique(user.getLoginName());
	}

	/**
	 * Save add.
	 *
	 * @param parentId       the parent id
	 * @param referral       the referral
	 * @param user           the user
	 * @param password       the password
	 * @param walletPassword the wallet password
	 * @return the ajax result
	 */
	@Log(title = "添加用户", businessType = BusinessType.INSERT, isSaveRequestData = true)
	@RequiresPermissions("mining:user:add")
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult saveAdd(Long parentId, Integer referral, User user, String password, String walletPassword) {
		if (parentId == null) {
			parentId = MathUtils.parseLong(configService.selectConfigValueByKey(IMiningConstants.ROOT_USER_ID));
		}
		if (referral == null && "true"
				.equalsIgnoreCase(configService.selectConfigValueByKey(IMiningConstants.USER_BINARY_TREE_ENABLE))) {
			return AjaxResult.error("添加失败");
		}
		final UserReferrer userReferrer = referrerService.selectUserReferrerById(parentId);
		if (userReferrer == null) {
			return AjaxResult.error("注册失败");
		}
		String referralCode = userReferrer.getLeftCode();
		if (referral != null && referral == 2) {
			referralCode = userReferrer.getRightCode();
		}
		final CommonResult<?> result = userRegisterService.registerUser(user, referralCode, walletPassword);
		if (result.isSuccess()) {
			return AjaxResult.success();
		}
		return AjaxResult.error(result.getInfo());
	}

	/**
	 * Edits the.
	 *
	 * @param id   the id
	 * @param mmap the mmap
	 * @return the string
	 */
	@RequiresPermissions("mining:user:edit")
	@GetMapping("/edit")
	public String edit(Long id, ModelMap mmap) {
		mmap.put("user", userProfileService.selectUserProfileById(id));
		mmap.put("levels", levelService.selectMiningLevelList(new MiningLevel()));
		mmap.put("emailEnabled", GlobalConfig.getEmailCode());
		return prefix + "/editUser";
	}

	/**
	 * Edits the save.
	 *
	 * @param userId         the user id
	 * @param email          the email
	 * @param mobile         the mobile
	 * @param password       the password
	 * @param walletPassword the wallet password
	 * @return the ajax result
	 */
	@RequiresPermissions("mining:user:edit")
	@Log(title = "更新用户信息", businessType = BusinessType.UPDATE, isSaveRequestData = true)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Long userId, String email, String mobile, String password, String walletPassword) {
		if (userId == null) {
			return AjaxResult.error();
		}
		if (StringUtils.isNotEmpty(password)) {
			final CommonResult<?> result = userProfileService.resetUserPassword(userId, password);
			if (!result.isSuccess()) {
				return AjaxResult.error(result.getInfo());
			}
		}
		if (StringUtils.isNotEmpty(walletPassword)) {
			final int result = userWalletService.resetUserWalletPassword(userId, walletPassword);
			if (result <= 0) {
				return AjaxResult.error("更新钱包密码失败");
			}
		}
		return AjaxResult.success();
	}

	/**
	 * Edits the wallet.
	 *
	 * @param userId the user id
	 * @param value  the value
	 * @param kind   the kind
	 * @param symbol the symbol
	 * @return the ajax result
	 */
	@RequiresPermissions("mining:user:edit")
	@Log(title = "更新用户钱包", businessType = BusinessType.UPDATE, isSaveRequestData = true)
	@PostMapping("/setWalletAmount")
	@ResponseBody
	public AjaxResult editWallet(Long userId, BigDecimal value, Integer kind, String symbol) {
		if (userId == null || MathUtils.isInvalid(value) || kind == null || StringUtils.isEmpty(symbol)) {
			return AjaxResult.error("参数错误");
		}

		return toAjax(userWalletAccountService.adminSetAccountByUserId(userId, symbol, kind, value));
	}

	/**
	 * Change status.
	 *
	 * @param user the user
	 * @return the ajax result
	 */
	@Log(title = "更改用户状态", businessType = BusinessType.UPDATE, isSaveRequestData = true)
	@RequiresPermissions("mining:user:edit")
	@PostMapping("/changeStatus")
	@ResponseBody
	public AjaxResult changeStatus(SysUser user) {
		return toAjax(sysUserService.changeStatus(user));
	}

	/**
	 * Removes the level.
	 *
	 * @param userId the user id
	 * @return the ajax result
	 */
	@Log(title = "删除用户等级", businessType = BusinessType.DELETE, isSaveRequestData = true)
	@RequiresPermissions("mining:user:edit")
	@PostMapping("/removeLevel")
	@ResponseBody
	public AjaxResult removeLevel(Long userId) {
		return toAjax(userLevelService.deleteUserLevelById(userId));
	}

	/**
	 * Sets the level.
	 *
	 * @param userId the user id
	 * @param id     the id
	 * @return the ajax result
	 */
	@Log(title = "设置用户等级", businessType = BusinessType.UPDATE, isSaveRequestData = true)
	@RequiresPermissions("mining:user:edit")
	@PostMapping("/setLevel")
	@ResponseBody
	public AjaxResult setLevel(Long userId, Long id) {
		final UserLevel userLevel = new UserLevel();
		userLevel.setUserId(userId);
		userLevel.setLevelId(id);
		userLevel.setType(UserLevel.TYPE_ADMIN);
		return toAjax(userLevelService.insertUserLevel(userLevel));
	}

	/**
	 * Clean.
	 *
	 * @return the ajax result
	 */
	@Log(title = "系统重置", businessType = BusinessType.CLEAN, isSaveRequestData = true)
	@RequiresPermissions("mining:user:edit")
	@PostMapping("/clean")
	@ResponseBody
	public AjaxResult clean() {
		if (DateUtils.getNowDate().after(DateUtils.defaultDate("2021-01-23 00:00:00"))) {
			return AjaxResult.error("牛逼PLUS，你是怎么想的？");
		}
		InputStreamReader reader = null;
		final ClassPathResource resource = new ClassPathResource("sql/cleanTable.sql");
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			final ScriptRunner runner = new ScriptRunner(conn);
			reader = new InputStreamReader(resource.getInputStream(), "utf-8");
			runner.runScript(reader);
		} catch (final Exception e) {
			e.printStackTrace();
			return AjaxResult.error("处理失败，请稍后再试！");
		} finally {
			try {
				reader.close();
				conn.close();
			} catch (final Exception e) {
			}
		}
		return AjaxResult.success("处理成功");
	}

	/**
	 * Change withdrawal enabled.
	 *
	 * @param userId  the user id
	 * @param symbol  the symbol
	 * @param enabled the enabled
	 * @return the ajax result
	 */
	@Log(title = "启停用户提币", businessType = BusinessType.UPDATE, isSaveRequestData = true)
	@RequiresPermissions("mining:user:edit")
	@PostMapping("/changeWithdrawalEnabled")
	@ResponseBody
	public AjaxResult changeWithdrawalEnabled(Long userId, String symbol, Integer enabled) {
		return AjaxResult
				.success(userWalletAccountService.changeUserWalletAccountWithdrawalEnabled(userId, symbol, enabled));
	}

	/**
	 * Update user mobile.
	 *
	 * @param userId the user id
	 * @param mobile the mobile
	 * @return the ajax result
	 */
	@Log(title = "修改用户手机", businessType = BusinessType.UPDATE, isSaveRequestData = true)
	@RequiresPermissions("mining:user:edit")
	@PostMapping("/updateUserMobile")
	@ResponseBody
	public AjaxResult updateUserMobile(Long userId, String mobile) {
		return userProfileService.updateUserMobile(userId, mobile);
	}

	/**
	 * Update user email.
	 *
	 * @param userId the user id
	 * @param email  the email
	 * @return the ajax result
	 */
	@Log(title = "修改用户邮箱", businessType = BusinessType.UPDATE, isSaveRequestData = true)
	@RequiresPermissions("mining:user:edit")
	@PostMapping("/updateUserEmail")
	@ResponseBody
	public AjaxResult updateUserEmail(Long userId, String email) {
		return userProfileService.updateUserEmail(userId, email);
	}
}
