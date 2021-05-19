package com.soyatec.sword.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 全局配置类
 *
 * @author Jin Liu (angryred@qq.com)
 */
@Component
@ConfigurationProperties(prefix = "sword")
public class GlobalConfig {
	/** 项目名称 */
	private static String name;

	private static String title = "后台管理";

	/** 版本 */
	private static String version;

	/** 版权年份 */
	private static String copyrightYear;

	/** 实例演示开关 */
	private static boolean demoEnabled;

	/** 上传路径 */
	private static String profile;

	/** 获取地址开关 */
	private static boolean addressEnabled;

	/** 使用邮箱验证码 */
	private static boolean emailCode = true;

	/** 后台首页配置 */
	private static String mainPage;
	/** 后台登录页主色 */
	private static String adminColor = "40,130,135";
	/** 后台邮箱验证码开关 */
	private static boolean notifyEnabled;

	/** 产品链接 */
	private static String gateway;

	public static String getName() {
		return name;
	}

	public void setName(String name) {
		GlobalConfig.name = name;
	}

	public static String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		GlobalConfig.version = version;
	}

	public static String getCopyrightYear() {
		return copyrightYear;
	}

	public void setCopyrightYear(String copyrightYear) {
		GlobalConfig.copyrightYear = copyrightYear;
	}

	public static boolean isDemoEnabled() {
		return demoEnabled;
	}

	public void setDemoEnabled(boolean demoEnabled) {
		GlobalConfig.demoEnabled = demoEnabled;
	}

	public static String getProfile() {
//		if (profile != null) {
//			return new File(profile).getAbsoluteFile().getAbsolutePath();
//		}
		return profile;
	}

	public void setProfile(String profile) {
		GlobalConfig.profile = profile;
	}

	public static boolean isAddressEnabled() {
		return addressEnabled;
	}

	public void setAddressEnabled(boolean addressEnabled) {
		GlobalConfig.addressEnabled = addressEnabled;
	}

	/**
	 * 获取头像上传路径
	 */
	public static String getAvatarPath() {
		return getProfile() + "/avatar";
	}

	/**
	 * 获取下载路径
	 */
	public static String getDownloadPath() {
		return getProfile() + "/download/";
	}

	/**
	 * 获取上传路径
	 */
	public static String getUploadPath() {
		return getProfile() + "/upload";
	}

	public static boolean getEmailCode() {
		return emailCode;
	}

	public void setEmailCode(boolean emailCode) {
		GlobalConfig.emailCode = emailCode;
	}

	public static String getMainPage() {
		return mainPage;
	}

	public void setMainPage(String mainPage) {
		GlobalConfig.mainPage = mainPage;
	}

	public static boolean isNotifyEnabled() {
		return notifyEnabled;
	}

	public void setNotifyEnabled(boolean notifyEnabled) {
		GlobalConfig.notifyEnabled = notifyEnabled;
	}

	public static String getAdminColor() {
		return adminColor;
	}

	public void setAdminColor(String adminColor) {
		GlobalConfig.adminColor = adminColor;
	}

	public static String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		GlobalConfig.gateway = gateway;
	}

	public static String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		GlobalConfig.title = title;
	}

}
