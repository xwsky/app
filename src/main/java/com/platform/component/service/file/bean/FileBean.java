package com.platform.component.service.file.bean;

import com.platform.core.utils.TextUtil;

public class FileBean {
	
	private String realPath;
	
	private String urlPath;
	
	private String fileName;
	
	private String fileExtName;
	
	private String fileFullName;
	
	private String baseUrlPath;
	

	public String getRealPath() {
		return realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	public String getFileName() {
		fileFullName = getFileFullName();
		if(fileFullName == null||fileFullName.indexOf(".")==-1){
			return null;
		}	
		fileName = fileFullName.substring(0, fileFullName.indexOf("."));
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileExtName() {
		fileFullName = getFileFullName();
		if(fileFullName == null||fileFullName.indexOf(".")==-1){
			return null;
		}	
		fileExtName = fileFullName.substring(fileFullName.indexOf(".") + 1);
		return fileExtName;
	}

	public void setFileExtName(String fileExtName) {
		this.fileExtName = fileExtName;
	}

	public String getFileFullName() {
		if(TextUtil.isNotEmpty(fileFullName)){
			return fileFullName;
		}
		if(urlPath == null){
			return null;
		}
		int a = urlPath.lastIndexOf("/") + 1;
		fileFullName = urlPath.substring(a);
		return fileFullName;
	}

	public void setFileFullName(String fileFullName) {
		this.fileFullName = fileFullName;
	}

	public String getBaseUrlPath() {
		if(urlPath == null){
			return null;
		}
		baseUrlPath = urlPath.substring(0, urlPath.lastIndexOf("/"));
		return baseUrlPath;
	}

	public void setBaseUrlPath(String baseUrlPath) {
		this.baseUrlPath = baseUrlPath;
	}	
	
	public static void main(String[] args) {
		FileBean f = new FileBean();
		f.setUrlPath("/asdas/asdasd/18.jpg");
		System.out.println(f.getFileFullName());
		System.out.println(f.getFileName());
		System.out.println(f.getFileExtName());
		System.out.println(f.getBaseUrlPath());
	}
	
	
}
