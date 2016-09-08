package my.ssh.util;

import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 常用加密算法工具类
 * @author 
 */
public class EncryptUtils {
	
	/**
	 * 用MD5算法进行加密
	 * @param str 需要加密的字符串
	 * @return MD5加密后的结果
	 */
	public static String encodeMD5String(String str) {
		return encode(str, "MD5");
	}

	/**
	 * 用SHA算法进行加密
	 * @param str 需要加密的字符串
	 * @return SHA加密后的结果
	 */
	public static String encodeSHAString(String str) {
		return encode(str, "SHA");
	}

	/**
	 * 用base64算法进行加密
	 * @param str 需要加密的字符串
	 * @return base64加密后的结果
	 */
	public static String encodeBase64String(String str) {
		Base64 encoder = new Base64();
		return encoder.encodeBase64String(str.getBytes());
	}
	
	/**
	 * 用base64算法进行解密
	 * @param str 需要解密的字符串
	 * @return base64解密后的结果
	 * @throws IOException 
	 */
	public static String decodeBase64String(String str) throws IOException {
		Base64 encoder = new Base64();
		return new String(encoder.decodeBase64(str));
	}

	private static String encode(String str, String method) {
		MessageDigest md;
		String dstr = null;
		try {
			md = MessageDigest.getInstance(method);
			md.update(str.getBytes());
			dstr = new BigInteger(1, md.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return dstr;
	}
	
	public static void main(String[] args) {
		System.out.println(encodeMD5String("admin"));
	}
}

