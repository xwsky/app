package com.platform.component.service.file;



import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.platform.component.service.file.bean.FileBean;
import com.platform.core.utils.FileUtil;
import com.platform.core.utils.TextUtil;


/**
 * 上传组件
 * @author xw
 *
 */
@Component
public class BaseUploadService {
	
	Logger logger = Logger.getLogger(BaseUploadService.class);	
	
	/**
	 * 上传文件
	 * @param file
	 * @param baseRealPath
	 * @param baseUrlPath
	 * @param moduleName
	 * @return
	 * @throws Exception
	 */
	public FileBean uploadFile(MultipartFile file, String baseRealPath, String baseUrlPath, String moduleName) throws Exception{
		return this.uploadFile(file, baseRealPath, baseUrlPath, moduleName, true);		
	}
	
	/**
	 * 上传文件
	 * @param file
	 * @param baseRealPath
	 * @param baseUrlPath
	 * @param moduleName
	 * @param useTimeDir
	 * @return
	 * @throws Exception
	 */
	public FileBean uploadFile(MultipartFile file, String baseRealPath, String baseUrlPath, String moduleName, boolean useTimeDir) throws Exception{
		return this.saveMultipartFile(file, baseRealPath, baseUrlPath, moduleName, useTimeDir);		
	}
	
	/**
	 * 获取上传文件
	 * @param request
	 * @param name
	 * @return
	 */
	public MultipartFile getMultipartFile(HttpServletRequest request, String name) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;		
		Map<String,MultipartFile> fileMap = multipartRequest.getFileMap();
		MultipartFile mFile = (MultipartFile) fileMap.get(name);
		return mFile;
	}
	
	/**
	 * 获取上传文件列表
	 * @param request
	 * @param name
	 * @return
	 */
	public List<MultipartFile> getMultipartFiles(HttpServletRequest request, String name) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;		
		return multipartRequest.getFiles(name);
	}	
	
	/**
	 * 保存文件
	 * @param is
	 * @param fileName
	 * @param baseRealPath
	 * @param baseUrlPath
	 * @param moduleName
	 * @param useTimeDir
	 * @return
	 * @throws Exception
	 */
	public FileBean savefileByInputStream(InputStream is, String fileName, String baseRealPath, 
			String baseUrlPath, String moduleName, boolean useTimeDir) throws Exception{		
		String timeDir = null;
		if(useTimeDir){
			SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
			timeDir = formater.format(new Date());
		}		
		String realPath = getFinalRealPath(baseRealPath, moduleName, fileName, timeDir);
		String urlPath = getFinalUrlPath(baseUrlPath, moduleName, fileName, timeDir);		
		File outFile = new File(realPath);
		//使用commons-io工具类保存文件
		FileUtils.copyInputStreamToFile(is, outFile);	
		return this.createFileBean(realPath, urlPath);	
	}
	
	/**
	 * 保存文件
	 * @param file
	 * @param baseRealPath
	 * @param baseUrlPath
	 * @param moduleName
	 * @param useTimeDir
	 * @return
	 * @throws Exception
	 */
	public FileBean saveMultipartFile(MultipartFile file, String baseRealPath, 
			String baseUrlPath, String moduleName, boolean useTimeDir) throws Exception{
		String fileName = FileUtil.getName(file.getOriginalFilename());
		return this.savefileByInputStream(file.getInputStream(), fileName, baseRealPath, baseUrlPath, moduleName, useTimeDir);
	}
	
	/**
	 * 获取最终真实路径
	 * @param baseRealPath
	 * @param moduleName	可为空
	 * @return
	 */
	private String getFinalRealPath(String baseRealPath, String moduleName, String fileName, String timeDir){
		String f_moduleName = TextUtil.isEmpty(moduleName) ? "default" : moduleName;
		String realPath = baseRealPath + File.separatorChar + f_moduleName;
		if(TextUtil.isNotEmpty(timeDir)){			
			//生成最终路径
			realPath = realPath + File.separatorChar + timeDir + File.separatorChar + fileName;
		}else{			
			//生成最终路径
			realPath = realPath + File.separatorChar + fileName;
		}
		return realPath;
	}
	
	/**
	 * 获取最终url路径
	 * @param baseUrlPath
	 * @param moduleName	可为空
	 * @return
	 */
	private String getFinalUrlPath(String baseUrlPath, String moduleName, String fileName, String timeDir){
		String f_moduleName = TextUtil.isEmpty(moduleName) ? "default" : moduleName;
		String urlPath = "";
		if(TextUtil.isNotEmpty(baseUrlPath)){
			urlPath = baseUrlPath;
		}
		urlPath += "/" + f_moduleName;	
		
		//生成最终路径
		if(TextUtil.isNotEmpty(timeDir)){
			urlPath = urlPath + "/" + timeDir + "/" +  fileName;	
		}else{
			urlPath = urlPath + "/" + fileName;
		}		
		return urlPath;
	}
	
	/**
	 * 创建fileBean
	 * @param realPath
	 * @param urlPath
	 * @return
	 */
	private FileBean createFileBean(String realPath, String urlPath){
		FileBean fileBean = new FileBean();
		fileBean.setRealPath(realPath);
		fileBean.setUrlPath(urlPath);		
		return fileBean;		
	}
	
	
}
