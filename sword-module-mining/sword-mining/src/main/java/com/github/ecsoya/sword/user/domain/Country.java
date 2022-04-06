package com.github.ecsoya.sword.user.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The Enum Country.
 */
public enum Country {

	/** The are. */
	ARE("阿拉伯联合酋长国", "United Arab Emirates"),
	/** The jor. */
	JOR("约旦", "Jordan"),
	/** The syr. */
	SYR("叙利亚", "Syria"),
	/** The hrv. */
	HRV("克罗地亚", "Croatia"),

	/** The bel. */
	BEL("比利时", "Belgium"),
	/** The pan. */
	PAN("巴拿马", "Panama"),
	/** The mlt. */
	MLT("马耳他", "Malta"),
	/** The ven. */
	VEN("委内瑞拉", "Venezuela"),
	/** The twn. */
	TWN("台湾地区", "Taiwan"),

	/** The dnk. */
	DNK("丹麦", "Denmark"),
	/** The pri. */
	PRI("波多黎哥", "Puerto Rico"),
	/** The vnm. */
	VNM("越南", "Vietnam"),
	/** The usa. */
	USA("美国", "United States"),

	/** The mne. */
	MNE("黑山", "Montenegro"),
	/** The swe. */
	SWE("瑞典", "Sweden"),
	/** The bol. */
	BOL("玻利维亚", "Bolivia"),
	/** The sgp. */
	SGP("新加坡", "Singapore"),
	/** The bhr. */
	BHR("巴林", "Bahrain"),

	/** The sau. */
	SAU("沙特阿拉伯", "Saudi Arabia"),
	/** The yem. */
	YEM("也门", "Yemen"),
	/** The ind. */
	IND("印度", "India"),
	/** The fin. */
	FIN("芬兰", "Finland"),

	/** The bih. */
	BIH("波斯尼亚和黑山共和国", "Bosnia and Herzegovina"),
	/** The ukr. */
	UKR("乌克兰", "Ukraine"),
	/** The che. */
	CHE("瑞士", "Switzerland"),

	/** The arg. */
	ARG("阿根廷", "Argentina"),
	/** The egy. */
	EGY("埃及", "Egypt"),
	/** The jpn. */
	JPN("日本", "Japan"),
	/** The slv. */
	SLV("萨尔瓦多", "El Salvador"),
	/** The bra. */
	BRA("巴西", "Brazil"),

	/** The isl. */
	ISL("冰岛", "Iceland"),
	/** The cze. */
	CZE("捷克共和国", "Czech Republic"),
	/** The pol. */
	POL("波兰", "Poland"),
	/** The esp. */
	ESP("西班牙", "Spain"),

	/** The mys. */
	MYS("马来西亚", "Malaysia"),
	/** The col. */
	COL("哥伦比亚", "Colombia"),
	/** The bgr. */
	BGR("保加利亚", "Bulgaria"),
	/** The pry. */
	PRY("巴拉圭", "Paraguay"),

	/** The ecu. */
	ECU("厄瓜多尔", "Ecuador"),
	/** The sdn. */
	SDN("苏丹", "Sudan"),
	/** The rou. */
	ROU("罗马尼亚", "Romania"),
	/** The phl. */
	PHL("菲律宾", "Philippines"),

	/** The tun. */
	TUN("突尼斯", "Tunisia"),
	/** The gtm. */
	GTM("危地马拉", "Guatemala"),
	/** The kor. */
	KOR("韩国", "South Korea"),
	/** The cyp. */
	CYP("塞浦路斯", "Cyprus"),

	/** The mex. */
	MEX("墨西哥", "Mexico"),
	/** The rus. */
	RUS("俄罗斯", "Russia"),
	/** The hnd. */
	HND("洪都拉斯", "Honduras"),
	/** The hkg. */
	HKG("香港", "Hong Kong"),
	/** The nor. */
	NOR("挪威", "Norway"),

	/** The hun. */
	HUN("匈牙利", "Hungary"),
	/** The tha. */
	THA("泰国", "Thailand"),
	/** The irq. */
	IRQ("伊拉克", "Iraq"),
	/** The chl. */
	CHL("智利", "Chile"),
	/** The mar. */
	MAR("摩洛哥", "Morocco"),

	/** The irl. */
	IRL("爱尔兰", "Ireland"),
	/** The tur. */
	TUR("土耳其", "Turkey"),
	/** The est. */
	EST("爱沙尼亚", "Estonia"),
	/** The qat. */
	QAT("卡塔尔", "Qatar"),
	/** The prt. */
	PRT("葡萄牙", "Portugal"),

	/** The lux. */
	LUX("卢森堡", "Luxembourg"),
	/** The omn. */
	OMN("阿曼", "Oman"),
	/** The alb. */
	ALB("阿尔巴尼亚", "Albania"),
	/** The dom. */
	DOM("多米尼加共和国", "Dominican Republic"),

	/** The cub. */
	CUB("古巴", "Cuba"),
	/** The nzl. */
	NZL("新西兰", "New Zealand"),
	/** The srb. */
	SRB("塞尔维亚", "Serbia"),
	/** The ury. */
	URY("乌拉圭", "Uruguay"),
	/** The grc. */
	GRC("希腊", "Greece"),

	/** The isr. */
	ISR("以色列", "Israel"),
	/** The zaf. */
	ZAF("南非", "South Africa"),
	/** The fra. */
	FRA("法国", "France"),
	/** The aut. */
	AUT("奥地利", "Austria"),

	/** The aus. */
	AUS("澳大利亚", "Australia"),
	/** The nld. */
	NLD("荷兰", "Netherlands"),
	/** The can. */
	CAN("加拿大", "Canada"),
	/** The lva. */
	LVA("拉脱维亚", "Latvia"),

	/** The cri. */
	CRI("哥斯达黎加", "Costa Rica"),
	/** The kwt. */
	KWT("科威特", "Kuwait"),
	/** The lby. */
	LBY("利比亚", "Libya"),
	/** The deu. */
	DEU("德国", "Germany"),

	/** The dza. */
	DZA("阿尔及利亚", "Algeria"),
	/** The svk. */
	SVK("斯洛伐克", "Slovakia"),
	/** The ltu. */
	LTU("立陶宛", "Lithuania"),
	/** The ita. */
	ITA("意大利", "Italy"),
	/** The chn. */
	CHN("中国", "China"),

	/** The lbn. */
	LBN("黎巴嫩", "Lebanon"),
	/** The nic. */
	NIC("尼加拉瓜", "Nicaragua"),
	/** The mkd. */
	MKD("马其顿王国", "Macedonia"),
	/** The blr. */
	BLR("白俄罗斯", "Belarus"),

	/** The svn. */
	SVN("斯洛文尼亚", "Slovenia"),
	/** The per. */
	PER("秘鲁", "Peru"),
	/** The idn. */
	IDN("印度尼西亚", "Indonesia"),
	/** The gbr. */
	GBR("英国", "United Kingdom");

	/** The enum json list. */
	private static List<Map<String, String>> ENUM_JSON_LIST = null;

	/** The chinese name. */
	private String chineseName;

	/** The english name. */
	private String englishName;

	/**
	 * Instantiates a new country.
	 *
	 * @param chineseName the chinese name
	 * @param englishName the english name
	 */
	private Country(String chineseName, String englishName) {
		this.chineseName = chineseName;
		this.englishName = englishName;
	}

	/**
	 * Gets the chinese name.
	 *
	 * @return the chinese name
	 */
	public String getChineseName() {
		return chineseName;
	}

	/**
	 * Gets the english name.
	 *
	 * @return the english name
	 */
	public String getEnglishName() {
		return englishName;
	}

	/**
	 * List.
	 *
	 * @return the list
	 */
	public static List<Map<String, String>> list() {
		if (ENUM_JSON_LIST == null) {
			ENUM_JSON_LIST = Arrays.asList(Country.values()).parallelStream().map(c -> {
				final Map<String, String> map = new HashMap<>();
				map.put("name", c.name());
				map.put("chineseName", c.getChineseName());
				map.put("englishName", c.getEnglishName());
				return map;
			}).collect(Collectors.toList());
		}
		return ENUM_JSON_LIST;
	}

}
