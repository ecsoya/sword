package com.github.ecsoya.sword.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.ecsoya.sword.common.config.GlobalConfig;
import com.github.ecsoya.sword.common.constant.Constants;
import com.github.ecsoya.sword.common.utils.http.HttpUtils;

/**
 * The Class AddressUtils.
 */
public class AddressUtils {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

	/** The Constant IP_URL. */
	// IP地址查询
	public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";

	/** The Constant UNKNOWN. */
	// 未知地址
	public static final String UNKNOWN = "XX XX";

	/**
	 * Gets the real address by IP.
	 *
	 * @param ip the ip
	 * @return the real address by IP
	 */
	public static String getRealAddressByIP(String ip) {
		final String address = UNKNOWN;
		// 内网不查询
		if (IpUtils.internalIp(ip)) {
			return "内网IP";
		}
		if (GlobalConfig.isAddressEnabled()) {
			try {
				final String rspStr = HttpUtils.sendGet(IP_URL, "ip=" + ip + "&json=true", Constants.GBK);
				if (StringUtils.isEmpty(rspStr)) {
					log.error("获取地理位置异常 {}", ip);
					return UNKNOWN;
				}
				final JSONObject obj = JSON.parseObject(rspStr);
				final String region = obj.getString("pro");
				final String city = obj.getString("city");
				return String.format("%s %s", region, city);
			} catch (final Exception e) {
				log.error("获取地理位置异常 {}", e);
			}
		}
		return address;
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		System.out.println(HttpUtils.sendGet(IP_URL, "ip=42.91.172.170&json=true", Constants.GBK));
	}
}
