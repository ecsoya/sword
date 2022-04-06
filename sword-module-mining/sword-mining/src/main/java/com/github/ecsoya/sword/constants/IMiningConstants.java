package com.github.ecsoya.sword.constants;

/**
 * The Interface IMiningConstants.
 */
public interface IMiningConstants {

	/** The root user id. */
	String ROOT_USER_ID = "sword.rootUserId"; // 根用户ID

	/** The enable user certificate. */
	String ENABLE_USER_CERTIFICATE = "sword.enableUserCertificate";// 启用实名认证

	/** The register need referrer code. */
	String REGISTER_NEED_REFERRER_CODE = "sword.registerNeedReferrerCode";// 注册推荐码

	/** The register mobile unique. */
	String REGISTER_MOBILE_UNIQUE = "sword.registerMobileUnique";// 注册需要手机号

	/** The register email unique. */
	String REGISTER_EMAIL_UNIQUE = "sword.registerEmailUnique";// 注册需要邮箱

	/** The user profit enabled. */
	String USER_PROFIT_ENABLED = "sword.userProfitEnabled"; // 收益全局开关

	/** The user binary tree enable. */
	String USER_BINARY_TREE_ENABLE = "sword.userBinaryTreeEnabled"; // 双区树

	/** The wallet default password. */
	String WALLET_DEFAULT_PASSWORD = "wallet.defaultPassword"; // 钱包默认密码

	/** The user referral link url. */
	/* 二维码前缀 */
	String USER_REFERRAL_LINK_URL = "sword.referralLinkUrl";

	/** The symbol fil. */
	String SYMBOL_FIL = "fil";

	/** The symbol bga. */
	String SYMBOL_BGA = "bga";

	/** The symbol usdt. */
	String SYMBOL_USDT = "usdt";

	/** The chain default. */
	String CHAIN_DEFAULT = "";

	/** The param chain. */
	String PARAM_CHAIN = "chain";

	/** The param chain erc20. */
	String PARAM_CHAIN_ERC20 = "eth";

	/** The param chain omni. */
	String PARAM_CHAIN_OMNI = "btc";

	/** The details unknown. */
	// 钱包记录details类型
	Integer DETAILS_UNKNOWN = 0;

	/** The details deposit. */
	Integer DETAILS_DEPOSIT = 1;

	/** The details withdrawal. */
	Integer DETAILS_WITHDRAWAL = 2;

	/** The details buy. */
	Integer DETAILS_BUY = 3;

	/** The details activate. */
	Integer DETAILS_ACTIVATE = 4;

	/** The details exchange. */
	Integer DETAILS_EXCHANGE = 5;
}
