package com.platform.spring.baseclass.controller;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.platform.spring.editor.TimestampEditor;
import com.platform.spring.editor.DateEditor;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

/**
 * 基础控制器
 * @author xw
 *
 */
public class BaseController{
	
	protected Logger logger = Logger.getLogger(this.getClass());	
	
	/**
	 * 绑定对象
	 * @param request
	 * @param command
	 */
    protected void bindObject(HttpServletRequest request, Object command){       
    	ServletRequestDataBinder binder = 
    			new ServletRequestDataBinder(command);		
        initBinder(binder);
        binder.bind(request);        
    }
	
	/**
	 * 表单数据绑定
	 * @param binder
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {			
		//日期
		binder.registerCustomEditor(Date.class, new DateEditor());
		binder.registerCustomEditor(Timestamp.class, new TimestampEditor());
		//字节数组		
		binder.registerCustomEditor(Byte[].class, new ByteArrayMultipartFileEditor());
		//字符串
		binder.registerCustomEditor(String.class, null, new StringTrimmerEditor(true));
		//BigDecimal
		binder.registerCustomEditor(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, true));
	}	
		
}
