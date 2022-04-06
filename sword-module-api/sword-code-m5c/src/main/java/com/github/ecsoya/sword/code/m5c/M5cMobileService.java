package com.github.ecsoya.sword.code.m5c;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.utils.security.Md5Utils;

/**
 * The Class M5cMobileService.
 */
@Service
public class M5cMobileService {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(M5cMobileService.class);

	/** The properties. */
	@Autowired
	private M5cProperties properties;

	/**
	 * Send message.
	 *
	 * @param mobile  the mobile
	 * @param content the content
	 * @return the m 5 c result
	 */
	public M5cResult sendMessage(String mobile, String content) {
		log.info("M5cMobileService: {}={}", mobile, content);
		// 连接超时及读取超时设置
		System.setProperty("sun.net.client.defaultConnectTimeout", Long.toString(properties.getConnectionTimeout())); // 连接超时：30秒
		System.setProperty("sun.net.client.defaultReadTimeout", Long.toString(properties.getReadTimeout())); // 读取超时：30秒
		// 新建一个StringBuffer链接
		final StringBuffer buffer = new StringBuffer();

		// String encode = "GBK";
		// //页面编码和短信内容编码为GBK。重要说明：如提交短信后收到乱码，请将GBK改为UTF-8测试。如本程序页面为编码格式为：ASCII/GB2312/GBK则该处为GBK。如本页面编码为UTF-8或需要支持繁体，阿拉伯文等Unicode，请将此处写为：UTF-8

		final String encode = "UTF-8";

		final String username = properties.getUsername(); // 用户名

		final String password_md5 = Md5Utils.hash(properties.getPassword()); // 密码

		final String apikey = properties.getApiKey(); // apikey秘钥（请登录 http://m.5c.com.cn 短信平台-->账号管理-->我的信息 中复制apikey）

		try {

			final String contentUrlEncode = URLEncoder.encode(content, encode); // 对短信内容做Urlencode编码操作。注意：如

			// 把发送链接存入buffer中，如连接超时，可能是您服务器不支持域名解析，请将下面连接中的：【m.5c.com.cn】修改为IP：【115.28.23.78】
			buffer.append("https://m.5c.com.cn/api/send/index.php?username=" + username + "&password_md5="
					+ password_md5 + "&mobile=" + mobile + "&apikey=" + apikey + "&content=" + contentUrlEncode
					+ "&encode=" + encode);

			// System.out.println(buffer); //调试功能，输入完整的请求URL地址

			log.info("M5cMobileService: url={}", buffer.toString());

			// 把buffer链接存入新建的URL中
			final URL url = new URL(buffer.toString());

			// 打开URL链接
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// 使用POST方式发送
			connection.setRequestMethod("POST");

			// 使用长链接方式
			connection.setRequestProperty("Connection", "Keep-Alive");

			// 发送短信内容
			final BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

			// 获取返回值
			final String message = reader.readLine();
			log.debug("M5cMobileService: {}", message);
			return M5cResult.create(message);
		} catch (final Exception e) {
			e.printStackTrace();
			log.error("M5cMobileService: {}", e.getLocalizedMessage());
			return M5cResult.create(e.getLocalizedMessage());
		}
	}

}
