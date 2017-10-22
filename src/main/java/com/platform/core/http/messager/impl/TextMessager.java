package com.platform.core.http.messager.impl;

import com.platform.core.http.messager.IMessager;
import com.platform.core.utils.TextUtil;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class TextMessager implements IMessager {
	
	public static final String MESSAGE_TYPE_TEXT = "text/plain;charset=utf-8";
	public static final String MESSAGE_TYPE_HTML = "text/html;charset=utf-8";

	private String content;
	private String contentType;
	
	
	public TextMessager asHtml(){
		contentType = MESSAGE_TYPE_HTML;
		return this;
	}
	
	public TextMessager asText(){
		contentType = MESSAGE_TYPE_TEXT;
		return this;
	}

	public TextMessager data(String content) {
		this.content = content;
		return this;
	}
	
	@Override
	public void send(HttpServletResponse response){
		if(TextUtil.isEmpty(content)){
			throw new IllegalArgumentException();
		}		
		if(TextUtil.isEmpty(contentType)){
			asText();
		}	
		try {			
			response.setContentType(contentType);
			response.getWriter().print(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
