package com.platform.common.context.init.impl;


import java.util.Properties;

import javax.servlet.ServletContext;

import com.platform.common.context.App;
import com.platform.common.context.init.Initializer;
import com.platform.core.utils.FileUtil;


/**
 * 初始化全局参数
 * @author xw
 *
 */
public class ContextInitializer implements Initializer{

	public void before(ServletContext ctx) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void init(ServletContext ctx) throws Exception {
		//加载全局配置文件
		Properties applictionCfg = FileUtil.getProperties("/application.properties");
		if(applictionCfg != null){	
			
			//加密解密key
			App.SECURITY_KEY = applictionCfg.getProperty("security.key");				
			
			//静态资源路径
			App.STATIC_PATH = applictionCfg.getProperty("static.path");
			ctx.setAttribute("STATIC_PATH", App.STATIC_PATH);		
			
			//上传文件路径
			App.FILE_PATH = applictionCfg.getProperty("file.path");	
			App.FILE_REALPATH = applictionCfg.getProperty("file.realPath");
			ctx.setAttribute("FILE_PATH", App.FILE_PATH);	
			
			//报表服务器地址
			App.REPORT_PATH = applictionCfg.getProperty("report.path");
			ctx.setAttribute("REPORT_PATH", App.REPORT_PATH);	
			
		}
		
	}

	public void after(ServletContext ctx) throws Exception {
		// TODO Auto-generated method stub
		
	}
	


}
