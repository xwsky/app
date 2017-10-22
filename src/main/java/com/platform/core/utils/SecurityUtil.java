package com.platform.core.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 加密工具
 * @author xw
 *
 */
public class SecurityUtil {
	
	/**
	 * 加密字符串
	 * 
	 * @return
	 */
	public static String encrypt(String data, String key) {
		try {

			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(key.getBytes());
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();

			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			byte[] byteContent = data.getBytes("UTF-8");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);

			byte[] result = cipher.doFinal(byteContent);

			return Base64.encodeBase64String(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 解密字符串
	 * 
	 * @param data
	 *            解密内容	
	 * @return
	 */
	public static String decrypt(String data, String key) {
		try {

			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(key.getBytes());
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, keySpec);

			byte[] result = cipher.doFinal(Base64.decodeBase64(data));

			return new String(result, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}		
	
	/**
	 * md5
	 * @param str
	 * @return
	 */
    public static String getMD5Str(String str) {  
    	if(TextUtil.isEmpty(str)){
    		throw new IllegalArgumentException();
    	}
        MessageDigest messageDigest = null;  
        try {  
            messageDigest = MessageDigest.getInstance("MD5");  
            messageDigest.reset();  
            messageDigest.update(str.getBytes("UTF-8"));  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        byte[] byteArray = messageDigest.digest();  
        StringBuffer md5StrBuff = new StringBuffer();  
        for (int i = 0; i < byteArray.length; i++) {  
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)  
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));  
            else  
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
        }  
        return md5StrBuff.toString();  
    }  

	
}
