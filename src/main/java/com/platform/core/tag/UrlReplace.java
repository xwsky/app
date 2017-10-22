package com.platform.core.tag;

import com.platform.core.utils.TextUtil;
import com.platform.core.utils.WebUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;


public class UrlReplace extends TagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String url;
	private String param;
	private String value;	
	private String delete; 
	
	@Override
	public int doStartTag() throws JspException {
		JspWriter out = this.pageContext.getOut();		
		String fianlUrl = url;
		if(param.indexOf(",") != -1 && value.indexOf(",") != -1){
			String [] params = param.split(",");
			String [] values = value.split(",");
			if(params.length == values.length){
				for(int i=0; i<params.length; i++){
					fianlUrl = WebUtil.replaceUrlParam(fianlUrl, params[i], values[i]);
				}
			}
		}else{
			fianlUrl = WebUtil.replaceUrlParam(url, param, value);		
		}
		if(TextUtil.isNotEmpty(delete)){
			if(delete.indexOf(",") != -1){
				String [] deletes = delete.split(",");
				for(int i=0; i<deletes.length; i++){
					fianlUrl = WebUtil.deleteUrlParam(fianlUrl, deletes[i]);
				}
			}else{
				fianlUrl = WebUtil.deleteUrlParam(fianlUrl, delete);
			}
		}		
		try {
			out.println(fianlUrl);
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public String getDelete() {
		return delete;
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}

	
	
}
