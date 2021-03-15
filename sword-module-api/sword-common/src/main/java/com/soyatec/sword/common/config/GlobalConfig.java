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

	private static String prodUrl;

	private static boolean emailCode = true;

	private static String mainPage;

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

	public static String getProdUrl() {
		return prodUrl;
	}

	public void setProdUrl(String prodUrl) {
		GlobalConfig.prodUrl = prodUrl;
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

}
