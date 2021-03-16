package com.soyatec.sword.constants;

public interface IMiningConstants {

	String ROOT_USER_ID = "sword.rootUserId"; // 根用户ID
	String ENABLE_USER_CERTIFICATE = "sword.enableUserCertificate";// 启用实名认证
	String REGISTER_NEED_REFERRER_CODE = "sword.registerNeedReferrerCode";// 注册推荐码
	String REGISTER_MOBILE_UNIQUE = "sword.registerMobileUnique";// 注册需要手机号
	String REGISTER_EMAIL_UNIQUE = "sword.registerEmailUnique";// 注册需要邮箱
	String USER_PROFIT_ENABLED = "sword.userProfitEnabled"; // 收益全局开关
	String USER_BINARY_TREE_ENABLE = "sword.userBinaryTreeEnabled"; // 双区树

	/* 二维码前缀 */
	String USER_REFERRAL_LINK_URL = "sword.referralLinkUrl";

	String SYMBOL_FIL = "fil";
	String SYMBOL_BGA = "bga";
	String SYMBOL_USDT = "usdt";
	String CHAIN = "";
	String PARAM_CHAIN = "chain";
	String PARAM_CHAIN_ERC20 = "eth";
	String PARAM_CHAIN_OMNI = "btc";

}
