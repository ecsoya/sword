package com.github.ecsoya.sword.controller.system;

import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.ecsoya.sword.common.config.GlobalConfig;
import com.github.ecsoya.sword.common.config.ServerConfig;
import com.github.ecsoya.sword.common.constant.ShiroConstants;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.domain.entity.SysMenu;
import com.github.ecsoya.sword.common.core.domain.entity.SysRole;
import com.github.ecsoya.sword.common.core.domain.entity.SysUser;
import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.utils.CookieUtils;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.ServletUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.framework.shiro.service.SysPasswordService;
import com.github.ecsoya.sword.framework.shiro.util.ShiroUtils;
import com.github.ecsoya.sword.system.service.ISysConfigService;
import com.github.ecsoya.sword.system.service.ISysMenuService;

/**
 * The Class SysIndexController.
 */
@Controller
public class SysIndexController extends BaseController {

	/** The menu service. */
	@Autowired
	private ISysMenuService menuService;

	/** The config service. */
	@Autowired
	private ISysConfigService configService;

	/** The password service. */
	@Autowired
	private SysPasswordService passwordService;

	/** The config. */
	@Autowired
	private GlobalConfig config;

	/** The server. */
	@Autowired
	private ServerConfig server;

	/**
	 * Index.
	 *
	 * @param path the path
	 * @param mmap the mmap
	 * @return the string
	 */
	// 系统首页
	@GetMapping("/index")
	public String index(String path, ModelMap mmap) {
		mmap.put("path", path);
		// 取身份信息
		final SysUser user = ShiroUtils.getSysUser();
		// 根据用户id取出菜单
		final List<SysMenu> menus = menuService.selectMenusByUser(user);
		mmap.put("menus", menus);
		mmap.put("user", user);
		mmap.put("config", config);
		mmap.put("server", server);
		mmap.put("sideTheme", configService.selectConfigValueByKey("sys.index.sideTheme"));
		mmap.put("skinName", configService.selectConfigValueByKey("sys.index.skinName"));
		mmap.put("ignoreFooter", configService.selectConfigValueByKey("sys.index.ignoreFooter"));
		mmap.put("copyrightYear", GlobalConfig.getCopyrightYear());
		mmap.put("demoEnabled", false);
		mmap.put("isDefaultModifyPwd",
				initPasswordIsModify(user.getPwdUpdateDate(), user.getLoginName(), user.getPassword()));
		mmap.put("isPasswordExpired", passwordIsExpiration(user.getPwdUpdateDate()));

		// 菜单导航显示风格
		final String menuStyle = configService.selectConfigValueByKey("sys.index.menuStyle");
		// 移动端，默认使左侧导航菜单，否则取默认配置
		String indexStyle = ServletUtils.checkAgentIsMobile(ServletUtils.getRequest().getHeader("User-Agent")) ? "index"
				: menuStyle;

		// 优先Cookie配置导航菜单
		final Cookie[] cookies = ServletUtils.getRequest().getCookies();
		for (final Cookie cookie : cookies) {
			if (StringUtils.isNotEmpty(cookie.getName()) && "nav-style".equalsIgnoreCase(cookie.getName())) {
				indexStyle = cookie.getValue();
				break;
			}
		}
		final String webIndex = "topnav".equalsIgnoreCase(indexStyle) ? "index-topnav" : "index";
		return webIndex;
	}

	/**
	 * Lockscreen.
	 *
	 * @param mmap the mmap
	 * @return the string
	 */
	// 锁定屏幕
	@GetMapping("/lockscreen")
	public String lockscreen(ModelMap mmap) {
		mmap.put("config", config);
		mmap.put("user", ShiroUtils.getSysUser());
		ServletUtils.getSession().setAttribute(ShiroConstants.LOCK_SCREEN, true);
		return "lock";
	}

	/**
	 * Unlockscreen.
	 *
	 * @param password the password
	 * @return the ajax result
	 */
	// 解锁屏幕
	@PostMapping("/unlockscreen")
	@ResponseBody
	public AjaxResult unlockscreen(String password) {
		final SysUser user = ShiroUtils.getSysUser();
		if (StringUtils.isNull(user)) {
			return AjaxResult.error("服务器超时，请重新登陆");
		}
		if (passwordService.matches(user, password)) {
			ServletUtils.getSession().removeAttribute(ShiroConstants.LOCK_SCREEN);
			return AjaxResult.success();
		}
		return AjaxResult.error("密码不正确，请重新输入。");
	}

	/**
	 * Switch skin.
	 *
	 * @return the string
	 */
	// 切换主题
	@GetMapping("/system/switchSkin")
	public String switchSkin() {
		return "skin";
	}

	/**
	 * Menu style.
	 *
	 * @param style    the style
	 * @param response the response
	 */
	// 切换菜单
	@GetMapping("/system/menuStyle/{style}")
	public void menuStyle(@PathVariable String style, HttpServletResponse response) {
		CookieUtils.setCookie(response, "nav-style", style);
	}

	/**
	 * Main.
	 *
	 * @param mmap the mmap
	 * @return the string
	 */
	// 系统介绍
	@GetMapping("/system/main")
	public String main(ModelMap mmap) {
		mmap.put("version", GlobalConfig.getVersion());
		// 取身份信息
		final SysUser user = ShiroUtils.getSysUser();
		List<SysRole> roles = user.getRoles();
		for (SysRole sysRole : roles) {
			String indexPage = sysRole.getIndexPage();
			if (StringUtils.isNotEmpty(indexPage)) {
				return indexPage;
			}
		}
		mmap.put("config", config);
		return "main";
	}

	/**
	 * Inits the password is modify.
	 *
	 * @param pwdUpdateDate the pwd update date
	 * @param loginName     the login name
	 * @param password      the password
	 * @return true, if successful
	 */
	// 检查初始密码是否提醒修改
	public boolean initPasswordIsModify(Date pwdUpdateDate, String loginName, String password) {
		if (StringUtils.encryptPassword(loginName, "qwer1234", "111111").equals(password)
				|| StringUtils.encryptPassword(loginName, "qwer1234", "22222").equals(password)) {
			return true;
		}
		final Integer initPasswordModify = Convert
				.toInt(configService.selectConfigValueByKey("sys.account.initPasswordModify"));
		return initPasswordModify != null && initPasswordModify == 1 && pwdUpdateDate == null;
	}

	/**
	 * Password is expiration.
	 *
	 * @param pwdUpdateDate the pwd update date
	 * @return true, if successful
	 */
	// 检查密码是否过期
	public boolean passwordIsExpiration(Date pwdUpdateDate) {
		final Integer passwordValidateDays = Convert
				.toInt(configService.selectConfigValueByKey("sys.account.passwordValidateDays"));
		if (passwordValidateDays != null && passwordValidateDays > 0) {
			if (StringUtils.isNull(pwdUpdateDate)) {
				// 如果从未修改过初始密码，直接提醒过期
				return true;
			}
			final Date nowDate = DateUtils.getNowDate();
			return DateUtils.differentDaysByMillisecond(nowDate, pwdUpdateDate) > passwordValidateDays;
		}
		return false;
	}
}
