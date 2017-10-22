package com.platform.core.utils;

import java.io.*;

/**
 * 进制转换工具
 * 
 * @author xw
 *
 */
public class SystemUtil {

	/**
	 * 将一个char字符转换位字节数组（2个字节），b[0]存储高位字符，大端
	 * 
	 * @param c
	 *            字符（java char 2个字节）
	 * @return 代表字符的字节数组
	 */
	public static byte[] charToBytes(char c) {
		byte[] b = new byte[8];
		b[0] = (byte) (c >>> 8);
		b[1] = (byte) c;
		return b;
	}

	/**
	 * 16进制字符串转2进制字符串
	 * 
	 * @param hexString
	 * @return
	 */
	public static String hexString2binaryString(String hexString) {
		if (hexString == null || hexString.length() % 2 != 0)
			return null;
		String bString = "", tmp;
		for (int i = 0; i < hexString.length(); i++) {
			tmp = "0000" + Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
			bString += tmp.substring(tmp.length() - 4);
		}
		return bString;
	}

	/**
	 * 2进制字符串转16进制字符串
	 * 
	 * @param bString
	 * @return
	 */
	public static String binaryString2hexString(String bString) {
		if (bString == null || bString.equals("") || bString.length() % 8 != 0)
			return null;
		StringBuffer tmp = new StringBuffer();
		int iTmp = 0;
		for (int i = 0; i < bString.length(); i += 4) {
			iTmp = 0;
			for (int j = 0; j < 4; j++) {
				iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
			}
			tmp.append(Integer.toHexString(iTmp));
		}
		return tmp.toString();
	}

	/**
	 * 16进制字符串转换为字节数组
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] hexString2binary(String hexStr) {

		if (hexStr.length() < 1) {

			return null;
		}

		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {

			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}

		return result;
	}

	/**
	 * 将字节数组转换成 16 进制字符串
	 * 
	 * @param data
	 * @return
	 */
	public static String binary2hexString(byte data[]) {

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			String hex = Integer.toHexString(data[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}

		return sb.toString();
	}

	/**
	 * 描述 : <Object转byte[]>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @param obj
	 * @return
	 */
	public static byte[] Object2ByteArray(Object obj) {
		byte[] bytes = null;
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;
		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			bytes = bos.toByteArray();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if(oos!=null){
					oos.close();
				}				
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(bos!=null){
					bos.close();
				}				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bytes;
	}

	/**
	 * 描述 : <byte[]转Object>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @param bytes
	 * @return
	 */
	public static Object ByteArray2Object(byte[] bytes) {
		Object obj = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			bis = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bis);
			obj = ois.readObject();			
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}finally{
			try {
				if(ois!=null){
					ois.close();
				}				
			} catch (IOException e) {				
				e.printStackTrace();
			}
			try {
				if(bis!=null){
					bis.close();
				}				
			} catch (IOException e) {				
				e.printStackTrace();
			}
		}
		return obj;
	}
	

}
