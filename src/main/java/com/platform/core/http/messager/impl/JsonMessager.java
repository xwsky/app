package com.platform.core.http.messager.impl;

import com.platform.core.http.messager.IMessager;
import com.platform.core.utils.JsonUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;


public class JsonMessager extends HashMap<String, Object> implements IMessager {

	private static final long serialVersionUID = 1L;

	public JsonMessager success(){
		this.put("success", true);
		return this;
	}
	
	public JsonMessager error(){
		this.put("success", false);
		return this;
	} 
	
	public JsonMessager data(Object value){
		super.put("data", value);	
		return this;
	}
	
	public JsonMessager put(String key, Object value){						
		super.put(key, value);		
		return this;
	}	
	
	@Override
	public void send(HttpServletResponse response){			
		try {			
			response.setContentType("text/plain;charset=UTF-8");
			response.getWriter().write(JsonUtil.toJson(this));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
}
