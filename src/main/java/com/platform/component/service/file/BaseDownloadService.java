package com.platform.component.service.file;



import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.platform.core.utils.WebUtil;



/**
 * 下载组件
 * @author xw
 *
 */
@Component
public class BaseDownloadService {
	
	Logger logger = Logger.getLogger(BaseDownloadService.class);	
	
	/**
	 * 下载文件	
	 * @param filePath 文件路径
	 * @param response
	 * @throws Exception
	 */
	public void downloadFile(String filePath, HttpServletResponse response){		
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
	public void downloadFile(String filePath, String newFileName,
			HttpServletResponse response){
		WebUtil.downloadFile(filePath, newFileName, response);
	}
	
	
	/**
	 * 从互联网url下载文件
	 * @param url
	 * @param newFileName
	 * @param localPath
	 * @throws Exception
	 */
	public void downloadFileByNet(String url, String localPath){
		WebUtil.downloadFileByNet(url, localPath);
	}	
}
