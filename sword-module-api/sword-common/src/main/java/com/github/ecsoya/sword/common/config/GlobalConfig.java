package com.github.ecsoya.sword.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * The Class GlobalConfig.
 */
@Component
@ConfigurationProperties(prefix = "sword")
public class GlobalConfig {

	/** The name. */
	private static String name;

	/** The title. */
	private static String title = "后台管理";

	/** The version. */
	private static String version;

	/** The copyright year. */
	private static String copyrightYear;

	/** The demo enabled. */
	private static boolean demoEnabled;

	/** The profile. */
	private static String profile;

	/** The address enabled. */
	private static boolean addressEnabled;

	/** The email code. */
	private static boolean emailCode = true;

	/** The main page. */
	private static String mainPage;

	/** The admin color. */
	private static String adminColor = "40,130,135";

	/** The notify enabled. */
	private static boolean notifyEnabled;

	/** The gateway. */
	private static String gateway;

	/**
	 * Gets the 项目名称.
	 *
	 * @return the 项目名称
	 */
	public static String getName() {
		return name;
	}

	/**
	 * Sets the 项目名称.
	 *
	 * @param name the new 项目名称
	 */
	public void setName(String name) {
		GlobalConfig.name = name;
	}

	/**
	 * Gets the 版本.
	 *
	 * @return the 版本
	 */
	public static String getVersion() {
		return version;
	}

	/**
	 * Sets the 版本.
	 *
	 * @param version the new 版本
	 */
	public void setVersion(String version) {
		GlobalConfig.version = version;
	}

	/**
	 * Gets the 版权年份.
	 *
	 * @return the 版权年份
	 */
	public static String getCopyrightYear() {
		return copyrightYear;
	}

	/**
	 * Sets the 版权年份.
	 *
	 * @param copyrightYear the new 版权年份
	 */
	public void setCopyrightYear(String copyrightYear) {
		GlobalConfig.copyrightYear = copyrightYear;
	}

	/**
	 * Checks if is 实例演示开关.
	 *
	 * @return the 实例演示开关
	 */
	public static boolean isDemoEnabled() {
		return demoEnabled;
	}

	/**
	 * Sets the 实例演示开关.
	 *
	 * @param demoEnabled the new 实例演示开关
	 */
	public void setDemoEnabled(boolean demoEnabled) {
		GlobalConfig.demoEnabled = demoEnabled;
	}

	/**
	 * Gets the 上传路径.
	 *
	 * @return the 上传路径
	 */
	public static String getProfile() {
//		if (profile != null) {
//			return new File(profile).getAbsoluteFile().getAbsolutePath();
//		}
		return profile;
	}

	/**
	 * Sets the 上传路径.
	 *
	 * @param profile the new 上传路径
	 */
	public void setProfile(String profile) {
		GlobalConfig.profile = profile;
	}

	/**
	 * Checks if is 获取地址开关.
	 *
	 * @return the 获取地址开关
	 */
	public static boolean isAddressEnabled() {
		return addressEnabled;
	}

	/**
	 * Sets the 获取地址开关.
	 *
	 * @param addressEnabled the new 获取地址开关
	 */
	public void setAddressEnabled(boolean addressEnabled) {
		GlobalConfig.addressEnabled = addressEnabled;
	}

	/**
	 * Gets the avatar path.
	 *
	 * @return the avatar path
	 */
	public static String getAvatarPath() {
		return getProfile() + "/avatar";
	}

	/**
	 * Gets the download path.
	 *
	 * @return the download path
	 */
	public static String getDownloadPath() {
		return getProfile() + "/download/";
	}

	/**
	 * Gets the upload path.
	 *
	 * @return the upload path
	 */
	public static String getUploadPath() {
		return getProfile() + "/upload";
	}

	/**
	 * Gets the 使用邮箱验证码.
	 *
	 * @return the 使用邮箱验证码
	 */
	public static boolean getEmailCode() {
		return emailCode;
	}

	/**
	 * Sets the 使用邮箱验证码.
	 *
	 * @param emailCode the new 使用邮箱验证码
	 */
	public void setEmailCode(boolean emailCode) {
		GlobalConfig.emailCode = emailCode;
	}

	/**
	 * Gets the 后台首页配置.
	 *
	 * @return the 后台首页配置
	 */
	public static String getMainPage() {
		return mainPage;
	}

	/**
	 * Sets the 后台首页配置.
	 *
	 * @param mainPage the new 后台首页配置
	 */
	public void setMainPage(String mainPage) {
		GlobalConfig.mainPage = mainPage;
	}

	/**
	 * Checks if is 后台邮箱验证码开关.
	 *
	 * @return the 后台邮箱验证码开关
	 */
	public static boolean isNotifyEnabled() {
		return notifyEnabled;
	}

	/**
	 * Sets the 后台邮箱验证码开关.
	 *
	 * @param notifyEnabled the new 后台邮箱验证码开关
	 */
	public void setNotifyEnabled(boolean notifyEnabled) {
		GlobalConfig.notifyEnabled = notifyEnabled;
	}

	/**
	 * Gets the 后台登录页主色.
	 *
	 * @return the 后台登录页主色
	 */
	public static String getAdminColor() {
		return adminColor;
	}

	/**
	 * Sets the 后台登录页主色.
	 *
	 * @param adminColor the new 后台登录页主色
	 */
	public void setAdminColor(String adminColor) {
		GlobalConfig.adminColor = adminColor;
	}

	/**
	 * Gets the 产品链接.
	 *
	 * @return the 产品链接
	 */
	public static String getGateway() {
		return gateway;
	}

	/**
	 * Sets the 产品链接.
	 *
	 * @param gateway the new 产品链接
	 */
	public void setGateway(String gateway) {
		GlobalConfig.gateway = gateway;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public static String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		GlobalConfig.title = title;
	}

	/**
	 * Checks if is message enabled.
	 *
	 * @return true, if is message enabled
	 */
	public static boolean isMessageEnabled() {
		try {
			Class.forName("com.github.ecsoya.sword.message.config.MessageConfig");
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
}
