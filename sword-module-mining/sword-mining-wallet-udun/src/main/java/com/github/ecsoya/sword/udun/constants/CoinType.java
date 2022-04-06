package com.github.ecsoya.sword.udun.constants;

/**
 * The Enum CoinType.
 */
public enum CoinType {

	/** The Bitcoin. */
	Bitcoin("0", "BTC", "0"),
	/** The Litecoin. */
	Litecoin("2", "LTC", "2"),
	/** The Dogecoin. */
	Dogecoin("3", "DOGE", "3"),
	/** The Ethereum. */
	Ethereum("60", "ETH", "60"),

	/** The Ethereum classic. */
	EthereumClassic("61", "ETC", "61"),
	/** The Ripple. */
	Ripple("144", "XRP", "144"),
	/** The Bitcoincash. */
	Bitcoincash("145", "BCH", "145"),

	/** The eos. */
	EOS("194", "EOS", "194"),
	/** The trx. */
	TRX("195", "TRX", "195"),
	/** The neo. */
	NEO("888", "NEO", "888"),
	/** The xne. */
	XNE("208", "XNE", "208"),

	/** The tec. */
	TEC("206", "TEC", "206"),
	/** The gca. */
	GCA("500", "GCA", "500"),
	/** The gcb. */
	GCB("501", "GCB", "501"),
	/** The Galaxy chain. */
	GalaxyChain("502", "GCC", "502"),

	/** The dash. */
	DASH("5", "DASH", "5"),
	/** The zec. */
	ZEC("133", "ZEC", "133"),
	/** The qtum. */
	QTUM("2301", "QTUM", "2301"),
	/** The teco. */
	TECO("506", "TECO", "506"),

	/** The cnyt. */
	CNYT("509", "CNYT", "509"),
	/** The sto. */
	STO("99", "STO", "99"),
	/** The cnt. */
	CNT("520", "CNT", "520"),
	/** The fil. */
	FIL("2307", "FIL", "2307"),

	/** The trcusd. */
	TRCUSD("195", "USDT", "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t");

	/** The code. */
	private String code;

	/** The unit. */
	private String unit;

	/** The sub coin type. */
	private String subCoinType;

	/**
	 * Instantiates a new coin type.
	 *
	 * @param code        the code
	 * @param unit        the unit
	 * @param subCoinType the sub coin type
	 */
	CoinType(String code, String unit, String subCoinType) {
		this.code = code;
		this.unit = unit;
		this.subCoinType = subCoinType;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * Gets the unit.
	 *
	 * @return the unit
	 */
	public String getUnit() {
		return this.unit;
	}

	/**
	 * Gets the sub coin type.
	 *
	 * @return the sub coin type
	 */
	public String getSubCoinType() {
		return subCoinType;
	}

	/**
	 * Of code.
	 *
	 * @param code the code
	 * @return the coin type
	 */
	public static CoinType ofCode(int code) {
		switch (code) {
		case 0:
			return Bitcoin;
		case 2:
			return Litecoin;
		case 3:
			return Dogecoin;
		case 60:
			return Ethereum;
		case 61:
			return EthereumClassic;
		case 144:
			return Ripple;
		case 145:
			return Bitcoincash;
		case 194:
			return EOS;
		case 195:
			return TRX;
		case 208:
			return XNE;
		case 500:
			return GCA;
		case 501:
			return GCB;
		case 502:
			return GalaxyChain;
		case 5:
			return DASH;
		case 133:
			return ZEC;
		case 2301:
			return QTUM;
		case 99:
			return STO;
		case 206:
			return TEC;
		case 888:
			return NEO;
		case 506:
			return TECO;
		case 509:
			return CNYT;
		case 520:
			return CNT;
		case 2307:
			return FIL;
		}
		return null;
	}

	/**
	 * Of symbol.
	 *
	 * @param symbol the symbol
	 * @return the coin type
	 */
	public static CoinType ofSymbol(String symbol) {
		if ("TRCUSD".equalsIgnoreCase(symbol) || "USDT".equalsIgnoreCase(symbol)) {
			return TRCUSD;
		}
		CoinType[] values = CoinType.values();
		for (CoinType coinType : values) {
			if (coinType.unit.equalsIgnoreCase(symbol)) {
				return coinType;
			}
		}
		return null;
	}

	/**
	 * Gets the.
	 *
	 * @param coinType the coin type
	 * @return the coin type
	 */
	public static CoinType get(String coinType) {
		if (coinType == null) {
			return null;
		}

		CoinType[] values = CoinType.values();
		for (CoinType coin : values) {
			if (coinType.equals(coin.getSubCoinType())) {
				return coin;
			}
		}

		Integer code = parseInt(coinType);
		if (code != null) {
			return ofCode(code);
		}
		return null;
	}

	/**
	 * Parses the int.
	 *
	 * @param value the value
	 * @return the integer
	 */
	private static Integer parseInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
