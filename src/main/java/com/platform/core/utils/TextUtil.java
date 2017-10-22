package com.platform.core.utils;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.util.*;

/**
 * 文本工具类
 * 
 * @author xw
 *
 */
public class TextUtil {

	/**
	 * 获取 UUID
	 * 
	 * @return
	 */
	public static String getUUID() {

		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 判断字符串是否为空
	 * @param text
	 * @return
	 */
	public static boolean isEmpty(String text){
		 return text == null || text.length() == 0 || "".equals(text.trim());
	}

	/**
	 * 判断字符串是否非空
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isNotEmpty(String text) {
		return !TextUtil.isEmpty(text);
	}

	/**
	 * 删除字符串尾指定的字符
	 * 
	 * @param src
	 * @param endStr
	 * @return
	 */
	public static String delEndStr(String src, String endStr) {

		String tmp = src.trim();

		if (tmp.endsWith(endStr)) {

			return tmp.substring(0, tmp.length() - endStr.length());
		}

		return tmp;
	}

	/**
	 * 变量名格式化
	 * 
	 * @param param
	 *            要格式化的字符串 PersonName 或 personName 转化为 person_name
	 * @return
	 */
	public static String camelToUnderline(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c)) {
				if (i != 0) {
					sb.append('_');
				}
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 反格式化变量名
	 * 
	 * @param param
	 *            要格式化的字符串
	 * @param flag
	 *            首字符是否需要大写 person_name 转化为 personName 或 PersonName
	 * @return
	 */
	public static String underlineToCamel(String param, boolean flag) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		param = param.toLowerCase();
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (c == '_') {
				if (++i < len) {
					sb.append(Character.toUpperCase(param.charAt(i)));
				}
			} else {
				if (i == 0) {
					if (flag) {
						sb.append(Character.toUpperCase(c));
					} else {
						sb.append(Character.toLowerCase(c));
					}
				} else {
					sb.append(c);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 字符串左补零
	 * 
	 * @param str
	 *            原始字符串
	 * @param length
	 *            补零后长度
	 * @return
	 */
	public static String addZeroForLeft(String str, int length) {
		while (str.length() < length) {
			StringBuffer sb = new StringBuffer();
			sb.append("0").append(str);
			str = sb.toString();
		}
		return str;
	}

	/**
	 * 字符串右补零
	 * 
	 * @param str
	 *            原始字符串
	 * @param length
	 *            补零后长度
	 * @return
	 */
	public static String addZeroForRight(String str, int length) {
		while (str.length() < length) {
			StringBuffer sb = new StringBuffer();
			sb.append(str).append("0");
			str = sb.toString();
		}
		return str;
	}
	
	/**
	 * 将字符串转为List，根据符号疯
	 * @param split
	 * @return
	 */
	public static List<String> tansferString2ListBySplit(String str, String split){
		List<String> list = new ArrayList<String>();
		if(TextUtil.isEmpty(str)){
			return list;
		}
		String [] strs = str.split(split);
		for(String s : strs){
			if(TextUtil.isNotEmpty(s)){
				list.add(s);
			}		
		}
		return list;
	}
	
	/**
	 * 截取sql中的查询语句和order by
	 * @param sql
	 * @return
	 */
	public static String[] splitSqlOrderBy(String sql){
		String [] strs = new String[2];
		if(sql.toUpperCase().indexOf("ORDER BY") != -1){
			int index = sql.toUpperCase().lastIndexOf("ORDER BY");
			strs[0] = " " + sql.substring(0, index - 1) + " "; 
			strs[1] = " " + sql.substring(index, sql.length()) + " "; 
			if(strs[1].indexOf(".") != -1){
				throw new RuntimeException("Order by Not allowed to include '.' !!");
			}
		}else{
			strs[0] = " " + sql + " ";
			strs[1] = null;
		}
		return strs;
	}

	/**
	 * 截取字符串
	 * 
	 * @param str
	 *            原始字符串
	 * @param num
	 *            要截取的长度，一个中文占两位
	 * @return
	 */
	public static String subStr(String str, int num) {
		int max = num;
		try {
			max = trimGBK(str.getBytes("GBK"), num);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		int sum = 0;
		if (str != null && str.length() > max) {
			StringBuilder sb = new StringBuilder(max);
			for (int i = 0; i < str.length(); i++) {
				int c = str.charAt(i);
				// if ((c & 0xff00) != 0)
				// sum += 2;
				// else
				sum += 1;
				if (sum <= max)
					sb.append((char) c);
				else
					break;
			}
			return sb.append("...").toString();
		} else
			return str != null ? str : "";
	}

	public static int trimGBK(byte[] buf, int n) {
		int num = 0;
		boolean bChineseFirstHalf = false;
		if (buf.length < n)
			return buf.length;
		for (int i = 0; i < n; i++) {
			if (buf[i] < 0 && !bChineseFirstHalf) {
				bChineseFirstHalf = true;
			} else {
				num++;
				bChineseFirstHalf = false;
			}
		}

		return num;
	}

	/**
	 * 将字符串集合转换为sql字符串 ps：转换结果例如：('str1','str2','str3')
	 * 
	 * @param list
	 * @return
	 */
	public static String tansferList2SqlString(Collection<String> list) {
		if (list == null || list.size() == 0) {
			// 返回一个查询时不到的-1
			return " ('-1') ";
		}
		// 去重
		Set<String> set = new HashSet<String>();
		set.addAll(list);
		StringBuilder sb = new StringBuilder();
		for (String str : set) {
			if (TextUtil.isNotEmpty(str)) {
				sb.append("'").append(str).append("'").append(",");
			}
		}
		// 去逗号
		sb.deleteCharAt(sb.length() - 1);
		String str = " (" + sb.toString() + ") ";
		return str;
	}

	/**
	 * 将 JDBC Blob 转换为 String <br/>
	 * 注意：只有 Blob 存储的内容为文本内容时才可执行该转换
	 * 
	 * @param blob
	 * @return
	 * @throws Exception
	 */
	public static String blob2String(Blob blob) throws Exception {

		StringBuilder content = new StringBuilder();

		InputStream is = blob.getBinaryStream();
		byte[] tmp = new byte[512];
		int len;
		while ((len = is.read(tmp)) != -1) {

			content.append(new String(tmp, 0, len));
		}

		return content.toString();
	}

	/**
	 * 获取异常信息
	 * 
	 * @param ex
	 * @return
	 */
	public static String getExceptionDetails(Throwable ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		ex.printStackTrace(pw);
		pw.flush();
		sw.flush();
		return sw.toString();
	}

}
