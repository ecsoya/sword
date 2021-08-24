package com.soyatec.sword.udun.constants;

public enum CoinType {

	Bitcoin("0", "BTC", "0"),
	Litecoin("2", "LTC", "2"),
	Dogecoin("3", "DOGE", "3"),
	Ethereum("60", "ETH", "60"),
	EthereumClassic("61", "ETC", "61"),
	Ripple("144", "XRP", "144"),
	Bitcoincash("145", "BCH", "145"),
	EOS("194", "EOS", "194"),
	TRX("195", "TRX", "195"),
	NEO("888", "NEO", "888"),
	XNE("208", "XNE", "208"),
	TEC("206", "TEC", "206"),
	GCA("500", "GCA", "500"),
	GCB("501", "GCB", "501"),
	GalaxyChain("502", "GCC", "502"),
	DASH("5", "DASH", "5"),
	ZEC("133", "ZEC", "133"),
	QTUM("2301", "QTUM", "2301"),
	TECO("506", "TECO", "506"),
	CNYT("509", "CNYT", "509"),
	STO("99", "STO", "99"),
	CNT("520", "CNT", "520"),
	FIL("2307", "FIL", "2307"),
	TRCUSD("195", "USDT", "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t");

	private String code;
	private String unit;
	private String subCoinType;

	CoinType(String code, String unit, String subCoinType) {
		this.code = code;
		this.unit = unit;
		this.subCoinType = subCoinType;
	}

	public String getCode() {
		return this.code;
	}

	public String getUnit() {
		return this.unit;
	}

	public String getSubCoinType() {
		return subCoinType;
	}

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

	private static Integer parseInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
