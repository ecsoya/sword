package com.github.ecsoya.sword.common.utils.security;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

/**
 * The Class EncryptUtil.
 */
public class EncryptUtil {

	/** The encoding charset. */
	private static String encodingCharset = "UTF-8";

	/**
	 * Hmac md 5.
	 *
	 * @param aValue the a value
	 * @param aKey   the a key
	 * @return the string
	 */
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

	/**
	 * Hmac sha 256.
	 *
	 * @param value the value
	 * @param key   the key
	 * @return the string
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

	/**
	 * Md5.
	 *
	 * @param string the string
	 * @return the string
	 */
	public static String MD5(String string) {
		return encrypt(string, "MD5");
	}

	/**
	 * Sha.
	 *
	 * @param string the string
	 * @return the string
	 */
	public static String SHA(String string) {
		return encrypt(string, "SHA");
	}

	/**
	 * Sha256.
	 *
	 * @param string the string
	 * @return the string
	 */
	public static String SHA256(String string) {
		return encrypt(string, "SHA-256");
	}

	/**
	 * Sha512.
	 *
	 * @param string the string
	 * @return the string
	 */
	public static String SHA512(String string) {
		return encrypt(string, "SHA-512");
	}

	/**
	 * Encrypt.
	 *
	 * @param aValue    the a value
	 * @param algorithm the algorithm
	 * @return the string
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

	/**
	 * To hex.
	 *
	 * @param input the input
	 * @return the string
	 */
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

	/**
	 * From hex.
	 *
	 * @param hex the hex
	 * @return the byte[]
	 */
	public static byte[] fromHex(String hex) {
		if (hex == null) {
			return null;
		}
		try {
			return Hex.decodeHex(hex.toCharArray());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Gets the hmac.
	 *
	 * @param args the args
	 * @param key  the key
	 * @return the hmac
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
	 * Digest.
	 *
	 * @param aValue the a value
	 * @return the string
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
	 * Digest.
	 *
	 * @param input      the input
	 * @param algorithm  the algorithm
	 * @param salt       the salt
	 * @param iterations the iterations
	 * @return the string
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
