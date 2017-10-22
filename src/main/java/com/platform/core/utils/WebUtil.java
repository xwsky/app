package com.platform.core.utils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * web工具类
 */
public class WebUtil {
	
	/**
	 * 判断请求是否为Ajax请求
	 * @param request
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request){
		if(request.getHeader("x-requested-with") !=null 
			&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
			return true;
		}
		return false;
	}
	
	/**
	 * 获取路径
	 * @param request
	 * @return
	 */
	public static String getRealUrl(HttpServletRequest request){
		String str = request.getScheme()+ 
					"://" + request.getServerName() //服务器地址  
	                + ":"   
	                + request.getServerPort()           //端口号  
	                + request.getContextPath()      //项目名称  
	                + request.getServletPath();      //请求页面或其他地址  
        if(TextUtil.isNotEmpty(request.getQueryString())){
        	str += "?"+request.getQueryString();
        }
		return str;
	}
	
	/**
	 * 替换url中的参数
	 * @param url
	 * @param param
	 * @param value
	 * @return
	 */
	public static String replaceUrlParam(String url, String param, String value){
		if(TextUtil.isNotEmpty(url) 
				&& TextUtil.isNotEmpty(param)){
			if(TextUtil.isEmpty(value)){
				value = "";
			}
			StringBuilder sb = new StringBuilder(); 				
			try {
				value = URLEncoder.encode(URLEncoder.encode(value, "utf-8"), "utf-8");						
			} catch (UnsupportedEncodingException e) {					
				e.printStackTrace();
			}			
			int index = url.indexOf(param + "=");  
			if(index != -1){	
				//替换
				sb.append(url.substring(0, index)).append(param + "=").append(value);
				int idx = url.indexOf("&", index);  
				if (idx != -1) {  
                    sb.append(url.substring(idx));  
                }  				
			}else{
				//追加
				sb.append(url);
				if(url.indexOf("?") == -1){
					sb.append("?");
				}else{
					sb.append("&");
				}
				sb.append(param).append("=").append(value);
			}
			url = sb.toString(); 
		}
		return url;
	}
	
	/**
	 * 删除url中的参数
	 * @param url
	 * @param param
	 */
	public static String deleteUrlParam(String url, String param){		
		if(TextUtil.isNotEmpty(url) && TextUtil.isNotEmpty(param)){
			StringBuilder sb = new StringBuilder(); 	
			int index_1 = url.indexOf("?" + param + "=");  	
			int index_2 = url.indexOf("&" + param + "=");  	
			if(index_1 !=-1 ){
				sb.append(url.substring(0, index_1));
				int idx = url.indexOf("&", index_1);  
				if (idx != -1) {  
                    sb.append("?").append(url.substring(idx+1));  
                }  		
			}else if(index_2 != -1){
				sb.append(url.substring(0, index_2));					
				int idx = url.indexOf("&", index_2 + 1);  				
				if (idx != -1) {  
                    sb.append(url.substring(idx));                     
                } 
			}else{
				sb.append(url);
			}
			url = sb.toString();
		}
		return url;
	}
	
	/**
	 * 获取客户端的真实IP地址
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ipAddress = null;
		// ipAddress = this.getRequest().getRemoteAddr();
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}

		}

		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
															// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
	
	/**
	 * 获取request中的map
	 * @param request
	 * @return
	 */
	public static Map<String,String> getParameterMap(HttpServletRequest request) {  
	    // 参数Map  
	    Map<String,String[]> properties = request.getParameterMap();
	    // 返回值Map  
	    Map<String,String> returnMap = new HashMap<String,String>();  
	    Iterator<Entry<String, String[]>> entries = properties.entrySet().iterator();  
	    Entry<String,String[]> entry;
	    String name = "";  
	    String value = "";  
	    while (entries.hasNext()) {  
	    	value = "";
	        entry = (Entry<String,String[]>) entries.next();
	        name = (String) entry.getKey();  
	        Object valueObj = entry.getValue();  
	        if(null == valueObj){  
	            value = "";  
	        }else if(valueObj instanceof String[]){  
	            String[] values = (String[])valueObj;  
	            for(int i=0;i<values.length;i++){  
	                value += values[i] + ",";  
	            }  
	            value = value.substring(0, value.length()-1);  
	        }else{  
	            value = valueObj.toString();  
	        }  
	        returnMap.put(name, value);  
	    }  
	    return returnMap;  
	} 
	
	/**
	 * 下载文件	
	 * @param filePath 文件路径
	 * @param response
	 * @throws Exception
	 */
	public static void downloadFile(String filePath, HttpServletResponse response){		
		downloadFile(filePath, null, response);
	}
	

	/**
	 * 下载文件 	 
	 * @param filePath
	 *            文件路径
	 * @param newFileName
	 *            下载文件名称（包含扩展名）
	 * @param response
	 * @throws Exception
	 */
	public static void downloadFile(String filePath, String newFileName,
			HttpServletResponse response){
		InputStream is = null;
		OutputStream os = null;
		try{
			File file = new File(filePath);
			String fileName = newFileName == null ? file.getName() : newFileName;					
			String finalFileName = new String(fileName.getBytes("utf-8"), "iso8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ finalFileName + "\"");		
			response.setHeader("charset", "utf-8");
			response.setHeader("Content-Length", String.valueOf(file.length()));
			is = new FileInputStream(file);	
			os = response.getOutputStream();		
			FileUtil.transfer(is, os);		
			os.flush();
		}catch(Exception ex){
			
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {					
					e.printStackTrace();
				}
			}
			if(os != null){				
				try {
					os.close();
				} catch (IOException e) {					
					e.printStackTrace();
				}
			}				
		}
		
	}
	
	
	/**
	 * 从互联网url下载文件
	 * @param url
	 * @param localPath
	 * @throws Exception
	 */
	public static void downloadFileByNet(String url, String localPath){
		//String fileName = url.substring(url.lastIndexOf("/") + 1, url.length());	
		InputStream is = null;  
		OutputStream os = null; 		
		try{
			URL uri = new URL(url); 
			is = uri.openStream();  
			os = new FileOutputStream(new File(localPath)); 
			FileUtil.transfer(is, os);
			is.close();
			os.flush();
			os.close();
		}catch( Exception ex){
			
		}finally {
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {					
					e.printStackTrace();
				}
			}
			if(os != null){				
				try {
					os.close();
				} catch (IOException e) {					
					e.printStackTrace();
				}
			}			
		}
		
	}	
	
	
	
	/**
	 * 过滤字符串中的特殊字符
	 * 
	 * @param input
	 * @return
	 */
	public static String filter(String input) {
		return (input					
					.replaceAll("&",  "&#38")
					.replaceAll("\"", "&#34;")
					.replaceAll("'",  "&#39;")
					.replaceAll("<",  "&#60;")
					.replaceAll(">",  "&#62;")
				);
	}

	/**
	 * 反过滤
	 * 
	 * @param input
	 * @return
	 */
	public static String unFilter(String input) {
		return (input
					.replaceAll("&#62;", ">")
					.replaceAll("&#60;", "<")
					.replaceAll("&#39;", "'")
					.replaceAll("&#34;", "\"")					
					.replaceAll("&#38;", "&")
				);
	}
	
    /**
     * 去除html标签
     * @param htmlStr
     * @return
     */
    public static String delHTMLTag(String htmlStr){ 
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式 
         
        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
        Matcher m_script=p_script.matcher(htmlStr); 
        htmlStr=m_script.replaceAll(""); //过滤script标签 
         
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
        Matcher m_style=p_style.matcher(htmlStr); 
        htmlStr=m_style.replaceAll(""); //过滤style标签 
         
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
        Matcher m_html=p_html.matcher(htmlStr); 
        htmlStr=m_html.replaceAll(""); //过滤html标签 

        return htmlStr.trim(); //返回文本字符串 
    }

}
