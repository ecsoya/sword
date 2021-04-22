package com.soyatec.sword.common.utils.security;

import java.nio.charset.Charset;
import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import com.soyatec.sword.common.utils.StringUtils;

public class DESUtil {

	/**
	 * 密钥算法
	 */
	private static final String ALGORITHM = "DES";
	/**
	 * 加密/解密算法-工作模式-填充模式
	 */
	private static final String CIPHER_ALGORITHM = "DES/CBC/PKCS5Padding";
	/**
	 * 默认编码
	 */
	private static final String CHARSET = "utf-8";

	/**
	 * 生成key
	 *
	 * @param password
	 * @return
	 * @throws Exception
	 */
	private static Key generateKey(String password) throws Exception {
		DESKeySpec dks = new DESKeySpec(password.getBytes(CHARSET));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		return keyFactory.generateSecret(dks);
	}

	/**
	 * DES加密字符串
	 *
	 * @param password 加密密码，长度不能够小于8位
	 * @param data     待加密字符串
	 * @return 加密后内容
	 */
	public static String encrypt(String password, String data, byte[] ivBytes) {
		if (password == null || password.length() < 8) {
			throw new RuntimeException("加密失败，key不能小于8位");
		}
		if (ivBytes == null || ivBytes.length != 8) {
			throw new RuntimeException("加密失败，ivBytes不是8位");
		}
		if (data == null)
			return null;
		try {
			Key secretKey = generateKey(password);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			IvParameterSpec iv = new IvParameterSpec(ivBytes);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
			byte[] bytes = cipher.doFinal(data.getBytes(CHARSET));

			// JDK1.8及以上可直接使用Base64，JDK1.7及以下可以使用BASE64Encoder
			// Android平台可以使用android.util.Base64
			return new String(Base64.getEncoder().encode(bytes));

		} catch (Exception e) {
			e.printStackTrace();
			return data;
		}
	}

	/**
	 * DES解密字符串
	 *
	 * @param password 解密密码，长度不能够小于8位
	 * @param data     待解密字符串
	 * @return 解密后内容
	 */
	public static String decrypt(String password, String data, byte[] ivBytes) {
		if (password == null || password.length() < 8) {
			throw new RuntimeException("加密失败，key不能小于8位");
		}
		if (ivBytes == null || ivBytes.length != 8) {
			throw new RuntimeException("加密失败，ivBytes不是8位");
		}
		if (data == null)
			return null;
		try {
			Key secretKey = generateKey(password);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			IvParameterSpec iv = new IvParameterSpec(ivBytes);
			cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
			return new String(cipher.doFinal(Base64.getDecoder().decode(data.getBytes(CHARSET))), CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
			return data;
		}
	}

	public static void main(String[] args) {
		String value = "这是原始信息DEMO";
		System.out.println("original: " + value);
		String password = StringUtils.randomStr(8);
		System.out.println("password: " + password);
		String ivs = StringUtils.randomNum(8);
		System.out.println("ivs: " + ivs);
		byte[] iv = ivs.getBytes(Charset.forName("utf-8"));

		String encrypt = encrypt(password, value, iv);
		System.out.println("encrypt: " + encrypt);

		System.out.println("decrypt: " + decrypt(password, encrypt, iv));
	}
}