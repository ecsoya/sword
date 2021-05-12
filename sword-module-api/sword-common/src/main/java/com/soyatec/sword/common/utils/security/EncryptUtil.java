package com.soyatec.sword.common.utils.security;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class EncryptUtil {

	private static String encodingCharset = "UTF-8";

	public static String hmacMd5(String aValue, String aKey) {
		final byte k_ipad[] = new byte[64];
		final byte k_opad[] = new byte[64];
		byte keyb[];
		byte value[];
		try {
			keyb = aKey.getBytes(encodingCharset);
			value = aValue.getBytes(encodingCharset);
		} catch (final UnsupportedEncodingException e) {
			keyb = aKey.getBytes();
			value = aValue.getBytes();
		}

		Arrays.fill(k_ipad, keyb.length, 64, (byte) 54);
		Arrays.fill(k_opad, keyb.length, 64, (byte) 92);
		for (int i = 0; i < keyb.length; i++) {
			k_ipad[i] = (byte) (keyb[i] ^ 0x36);
			k_opad[i] = (byte) (keyb[i] ^ 0x5c);
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (final NoSuchAlgorithmException e) {

			return null;
		}
		md.update(k_ipad);
		md.update(value);
		byte dg[] = md.digest();
		md.reset();
		md.update(k_opad);
		md.update(dg, 0, 16);
		dg = md.digest();
		return toHex(dg);
	}

	/***
	 * 对应python里面的hmac.new(API_SECRET, msg=message,
	 * digestmod=hashlib.sha256).hexdigest().upper()
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static String hmacSha256(String value, String key) {
		String result = null;
		final byte[] keyBytes = key.getBytes();
		final SecretKeySpec localMac = new SecretKeySpec(keyBytes, "HmacSHA256");
		try {
			final Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(localMac);
			final byte[] arrayOfByte = mac.doFinal(value.getBytes());
			final BigInteger localBigInteger = new BigInteger(1, arrayOfByte);
			result = String.format("%0" + (arrayOfByte.length << 1) + "x", new Object[] { localBigInteger });

		} catch (final InvalidKeyException e) {
			e.printStackTrace();
		} catch (final NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (final IllegalStateException e) {
			e.printStackTrace();
		}

		return result;
	}

	/***
	 * MD5加密
	 *
	 * @param string
	 * @return
	 */
	public static String MD5(String string) {
		return encrypt(string, "MD5");
	}

	/***
	 * sha-1散列加密
	 *
	 * @param string
	 * @return
	 */
	public static String SHA(String string) {
		return encrypt(string, "SHA");
	}

	/***
	 * sha-256散列加密
	 *
	 * @param string
	 * @return
	 */
	public static String SHA256(String string) {
		return encrypt(string, "SHA-256");
	}

	/***
	 * sha-512散列加密
	 *
	 * @param string
	 * @return
	 */
	public static String SHA512(String string) {
		return encrypt(string, "SHA-512");
	}

	/**
	 * 通用加密方法
	 *
	 * @param aValue
	 * @param algorithm
	 * @return
	 */
	private static String encrypt(String aValue, String algorithm) {
		aValue = aValue.trim();
		byte value[];
		try {
			value = aValue.getBytes(encodingCharset);
		} catch (final UnsupportedEncodingException e) {
			value = aValue.getBytes();
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(algorithm);
		} catch (final NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		return toHex(md.digest(value));
	}

	public static String toHex(byte input[]) {
		if (input == null) {
			return null;
		}
//		final StringBuffer output = new StringBuffer(input.length * 2);
//		for (int i = 0; i < input.length; i++) {
//			final int current = input[i] & 0xff;
//			if (current < 16) {
//				output.append("0");
//			}
//			output.append(Integer.toString(current, 16));
//		}
		return Hex.encodeHexString(input);
	}

	public static byte[] fromHex(String hex) {
		if (hex == null) {
			return null;
		}
		try {
			return Hex.decodeHex(hex);
		} catch (DecoderException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 生成签名消息
	 *
	 * @param aValue 要签名的字符串
	 * @param aKey   签名密钥
	 * @return
	 */

	/**
	 *
	 * @param args
	 * @param key
	 * @return
	 */
	public static String getHmac(String[] args, String key) {
		if (args == null || args.length == 0) {
			return (null);
		}
		final StringBuffer str = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			str.append(args[i]);
		}
		return (hmacMd5(str.toString(), key));
	}

	/**
	 * SHA加密
	 *
	 * @param aValue
	 * @return
	 */
	public static String digest(String aValue) {
		aValue = aValue.trim();
		byte value[];
		try {
			value = aValue.getBytes(encodingCharset);
		} catch (final UnsupportedEncodingException e) {
			value = aValue.getBytes();
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (final NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		return toHex(md.digest(value));

	}

	/**
	 * 对字符串进行散列, 支持md5与sha1算法.
	 */
	public static String digest(byte[] input, String algorithm, byte[] salt, int iterations) {
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);

			if (salt != null) {
				digest.update(salt);
			}

			byte[] result = digest.digest(input);

			for (int i = 1; i < iterations; i++) {
				digest.reset();
				result = digest.digest(result);
			}
			return toHex(result);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
