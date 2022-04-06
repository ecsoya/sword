package com.github.ecsoya.sword.common.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ecsoya.sword.common.constant.Constants;

/**
 * The Class HttpUtils.
 */
public class HttpUtils {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

	/**
	 * Send get.
	 *
	 * @param url   the url
	 * @param param the param
	 * @return the string
	 */
	public static String sendGet(String url, String param) {
		return sendGet(url, param, Constants.UTF8);
	}

	/**
	 * Send get.
	 *
	 * @param url         the url
	 * @param param       the param
	 * @param contentType the content type
	 * @return the string
	 */
	public static String sendGet(String url, String param, String contentType) {
		final StringBuilder result = new StringBuilder();
		BufferedReader in = null;
		try {
			final String urlNameString = url + "?" + param;
			log.info("sendGet - {}", urlNameString);
			final URL realUrl = new URL(urlNameString);
			final URLConnection connection = realUrl.openConnection();
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.connect();
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), contentType));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
			log.info("recv - {}", result);
		} catch (final ConnectException e) {
			log.error("调用HttpUtils.sendGet ConnectException, url=" + url + ",param=" + param, e);
		} catch (final SocketTimeoutException e) {
			log.error("调用HttpUtils.sendGet SocketTimeoutException, url=" + url + ",param=" + param, e);
		} catch (final IOException e) {
			log.error("调用HttpUtils.sendGet IOException, url=" + url + ",param=" + param, e);
		} catch (final Exception e) {
			log.error("调用HttpsUtil.sendGet Exception, url=" + url + ",param=" + param, e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (final Exception ex) {
				log.error("调用in.close Exception, url=" + url + ",param=" + param, ex);
			}
		}
		return result.toString();
	}

	/**
	 * Send post.
	 *
	 * @param url   the url
	 * @param param the param
	 * @return the string
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		final StringBuilder result = new StringBuilder();
		try {
			final String urlNameString = url;
			log.info("sendPost - {}", urlNameString);
			final URL realUrl = new URL(urlNameString);
			final URLConnection conn = realUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Accept-Charset", "utf-8");
			conn.setRequestProperty("contentType", "utf-8");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			out = new PrintWriter(conn.getOutputStream());
			out.print(param);
			out.flush();
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
			log.info("recv - {}", result);
		} catch (final ConnectException e) {
			log.error("调用HttpUtils.sendPost ConnectException, url=" + url + ",param=" + param, e);
		} catch (final SocketTimeoutException e) {
			log.error("调用HttpUtils.sendPost SocketTimeoutException, url=" + url + ",param=" + param, e);
		} catch (final IOException e) {
			log.error("调用HttpUtils.sendPost IOException, url=" + url + ",param=" + param, e);
		} catch (final Exception e) {
			log.error("调用HttpsUtil.sendPost Exception, url=" + url + ",param=" + param, e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (final IOException ex) {
				log.error("调用in.close Exception, url=" + url + ",param=" + param, ex);
			}
		}
		return result.toString();
	}

	/**
	 * Send SSL post.
	 *
	 * @param url   the url
	 * @param param the param
	 * @return the string
	 */
	public static String sendSSLPost(String url, String param) {
		final StringBuilder result = new StringBuilder();
		final String urlNameString = url + "?" + param;
		try {
			log.info("sendSSLPost - {}", urlNameString);
			final SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
			final URL console = new URL(urlNameString);
			final HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Accept-Charset", "utf-8");
			conn.setRequestProperty("contentType", "utf-8");
			conn.setDoOutput(true);
			conn.setDoInput(true);

			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			conn.connect();
			final InputStream is = conn.getInputStream();
			final BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String ret = "";
			while ((ret = br.readLine()) != null) {
				if (ret != null && !ret.trim().equals("")) {
					result.append(new String(ret.getBytes("utf-8"), "utf-8"));
				}
			}
			log.info("recv - {}", result);
			conn.disconnect();
			br.close();
		} catch (final ConnectException e) {
			log.error("调用HttpUtils.sendSSLPost ConnectException, url=" + url + ",param=" + param, e);
		} catch (final SocketTimeoutException e) {
			log.error("调用HttpUtils.sendSSLPost SocketTimeoutException, url=" + url + ",param=" + param, e);
		} catch (final IOException e) {
			log.error("调用HttpUtils.sendSSLPost IOException, url=" + url + ",param=" + param, e);
		} catch (final Exception e) {
			log.error("调用HttpsUtil.sendSSLPost Exception, url=" + url + ",param=" + param, e);
		}
		return result.toString();
	}

	/**
	 * The Class TrustAnyTrustManager.
	 */
	private static class TrustAnyTrustManager implements X509TrustManager {

		/**
		 * Check client trusted.
		 *
		 * @param chain    the chain
		 * @param authType the auth type
		 */
		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) {
		}

		/**
		 * Check server trusted.
		 *
		 * @param chain    the chain
		 * @param authType the auth type
		 */
		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) {
		}

		/**
		 * Gets the accepted issuers.
		 *
		 * @return the accepted issuers
		 */
		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}

	/**
	 * The Class TrustAnyHostnameVerifier.
	 */
	private static class TrustAnyHostnameVerifier implements HostnameVerifier {

		/**
		 * Verify.
		 *
		 * @param hostname the hostname
		 * @param session  the session
		 * @return true, if successful
		 */
		@Override
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}
}