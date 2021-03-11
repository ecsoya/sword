package com.soyatec.sword.user.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum Country {
	ARE("阿拉伯联合酋长国", "United Arab Emirates"), JOR("约旦", "Jordan"), SYR("叙利亚", "Syria"), HRV("克罗地亚", "Croatia"),
	BEL("比利时", "Belgium"), PAN("巴拿马", "Panama"), MLT("马耳他", "Malta"), VEN("委内瑞拉", "Venezuela"), TWN("台湾地区", "Taiwan"),
	DNK("丹麦", "Denmark"), PRI("波多黎哥", "Puerto Rico"), VNM("越南", "Vietnam"), USA("美国", "United States"),
	MNE("黑山", "Montenegro"), SWE("瑞典", "Sweden"), BOL("玻利维亚", "Bolivia"), SGP("新加坡", "Singapore"), BHR("巴林", "Bahrain"),
	SAU("沙特阿拉伯", "Saudi Arabia"), YEM("也门", "Yemen"), IND("印度", "India"), FIN("芬兰", "Finland"),
	BIH("波斯尼亚和黑山共和国", "Bosnia and Herzegovina"), UKR("乌克兰", "Ukraine"), CHE("瑞士", "Switzerland"),
	ARG("阿根廷", "Argentina"), EGY("埃及", "Egypt"), JPN("日本", "Japan"), SLV("萨尔瓦多", "El Salvador"), BRA("巴西", "Brazil"),
	ISL("冰岛", "Iceland"), CZE("捷克共和国", "Czech Republic"), POL("波兰", "Poland"), ESP("西班牙", "Spain"),
	MYS("马来西亚", "Malaysia"), COL("哥伦比亚", "Colombia"), BGR("保加利亚", "Bulgaria"), PRY("巴拉圭", "Paraguay"),
	ECU("厄瓜多尔", "Ecuador"), SDN("苏丹", "Sudan"), ROU("罗马尼亚", "Romania"), PHL("菲律宾", "Philippines"),
	TUN("突尼斯", "Tunisia"), GTM("危地马拉", "Guatemala"), KOR("韩国", "South Korea"), CYP("塞浦路斯", "Cyprus"),
	MEX("墨西哥", "Mexico"), RUS("俄罗斯", "Russia"), HND("洪都拉斯", "Honduras"), HKG("香港", "Hong Kong"), NOR("挪威", "Norway"),
	HUN("匈牙利", "Hungary"), THA("泰国", "Thailand"), IRQ("伊拉克", "Iraq"), CHL("智利", "Chile"), MAR("摩洛哥", "Morocco"),
	IRL("爱尔兰", "Ireland"), TUR("土耳其", "Turkey"), EST("爱沙尼亚", "Estonia"), QAT("卡塔尔", "Qatar"), PRT("葡萄牙", "Portugal"),
	LUX("卢森堡", "Luxembourg"), OMN("阿曼", "Oman"), ALB("阿尔巴尼亚", "Albania"), DOM("多米尼加共和国", "Dominican Republic"),
	CUB("古巴", "Cuba"), NZL("新西兰", "New Zealand"), SRB("塞尔维亚", "Serbia"), URY("乌拉圭", "Uruguay"), GRC("希腊", "Greece"),
	ISR("以色列", "Israel"), ZAF("南非", "South Africa"), FRA("法国", "France"), AUT("奥地利", "Austria"),
	AUS("澳大利亚", "Australia"), NLD("荷兰", "Netherlands"), CAN("加拿大", "Canada"), LVA("拉脱维亚", "Latvia"),
	CRI("哥斯达黎加", "Costa Rica"), KWT("科威特", "Kuwait"), LBY("利比亚", "Libya"), DEU("德国", "Germany"),
	DZA("阿尔及利亚", "Algeria"), SVK("斯洛伐克", "Slovakia"), LTU("立陶宛", "Lithuania"), ITA("意大利", "Italy"), CHN("中国", "China"),
	LBN("黎巴嫩", "Lebanon"), NIC("尼加拉瓜", "Nicaragua"), MKD("马其顿王国", "Macedonia"), BLR("白俄罗斯", "Belarus"),
	SVN("斯洛文尼亚", "Slovenia"), PER("秘鲁", "Peru"), IDN("印度尼西亚", "Indonesia"), GBR("英国", "United Kingdom");

	private static List<Map<String, String>> ENUM_JSON_LIST = null;

	private String chineseName;

	private String englishName;

	private Country(String chineseName, String englishName) {
		this.chineseName = chineseName;
		this.englishName = englishName;
	}

	public String getChineseName() {
		return chineseName;
	}

	public String getEnglishName() {
		return englishName;
	}

	public static List<Map<String, String>> list() {
		if (ENUM_JSON_LIST == null) {
			ENUM_JSON_LIST = Arrays.asList(Country.values()).parallelStream().map(c -> {
				Map<String, String> map = new HashMap<>();
				map.put("name", c.name());
				map.put("chineseName", c.getChineseName());
				map.put("englishName", c.getEnglishName());
				return map;
			}).collect(Collectors.toList());
		}
		return ENUM_JSON_LIST;
	}

}
