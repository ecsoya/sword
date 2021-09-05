package com.soyatec.sword.constants;

public interface IMiningConstants {

	String ROOT_USER_ID = "sword.rootUserId"; // 根用户ID
	String ENABLE_USER_CERTIFICATE = "sword.enableUserCertificate";// 启用实名认证
	String REGISTER_NEED_REFERRER_CODE = "sword.registerNeedReferrerCode";// 注册推荐码
	String REGISTER_MOBILE_UNIQUE = "sword.registerMobileUnique";// 注册需要手机号
	String REGISTER_EMAIL_UNIQUE = "sword.registerEmailUnique";// 注册需要邮箱
	String USER_PROFIT_ENABLED = "sword.userProfitEnabled"; // 收益全局开关
	String USER_BINARY_TREE_ENABLE = "sword.userBinaryTreeEnabled"; // 双区树
	String WALLET_DEFAULT_PASSWORD = "wallet.defaultPassword"; // 钱包默认密码

	/* 二维码前缀 */
	String USER_REFERRAL_LINK_URL = "sword.referralLinkUrl";

	String SYMBOL_FIL = "fil";
	String SYMBOL_BGA = "bga";
	String SYMBOL_USDT = "usdt";
	String CHAIN_DEFAULT = "";
	String PARAM_CHAIN = "chain";
	String PARAM_CHAIN_ERC20 = "eth";
	String PARAM_CHAIN_OMNI = "btc";

	// 钱包记录details类型
	Integer DETAILS_UNKNOWN = 0;
	Integer DETAILS_DEPOSIT = 1;
	Integer DETAILS_WITHDRAWAL = 2;
	Integer DETAILS_BUY = 3;
	Integer DETAILS_ACTIVATE = 4;
	Integer DETAILS_EXCHANGE = 5;
}
