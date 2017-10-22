package com.platform.core.http;



import java.net.URLDecoder;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import com.platform.core.utils.TextUtil;
import org.apache.log4j.Logger;



/**
 * 查询条件Map
 * @author xw
 *
 */
public class Condition extends LinkedHashMap<String, Object>{		
	
	
	private Logger logger = Logger.getLogger(Condition.class);
	
	private static final long serialVersionUID = 1L;

	private HttpServletRequest request;
	
	private int searchFieldCount = 0;	
	
	
	public Condition(HttpServletRequest request){
		this.request = request;
	}

	/**
	 * 构造查询条件map
	 * @param request
	 * @param keys
	 * @return
	 * 用法：
	 * 		new Condition(request, ["name","age","sex"]);	
	 */
	public Condition(HttpServletRequest request, String...keys){	
		this.request = request;
		addParamValues(request, false, keys);	
	}
	
	/**
	 * 构造查询条件map
	 * @param request
	 * @param decode	是否解码
	 * @param keys
	 */
	public Condition(HttpServletRequest request, boolean decode, String...keys){
		this.request = request;
		addParamValues(request, decode, keys);		
	}
	
	public boolean containsKey(Object key){		
		return super.containsKey(key);
	}
	
	public Condition put(String key, Object value){				
		super.put(key, value);		
		return this;
	}	
	
	public Object[] valueArray(){			
		return super.values().toArray();		
	}	
	
	public int getSearchFieldCount() {
		return searchFieldCount;
	}


	/**
	 * 获取request
	 * @return
	 */
	public HttpServletRequest getRequest() {	
		//如果未设置是否分页，则默认为分页
		Object obj = request.getAttribute("PAGER");
		if(obj == null){
			request.setAttribute("PAGER", true);
		}
		return request;
	}
	
	/**
	 * 是否分页
	 * @param pager
	 */
	public void pager(boolean pager){
		request.setAttribute("PAGER", pager);
	}
	
	/**
	 * 已经废弃，以后不要使用了，请使用pager(boolean pager)和getRequest()
	 * 获取request
	 * @param pager 是否分页
	 * @return
	 */
	@Deprecated
	public HttpServletRequest getRequest(boolean pager) {
		request.setAttribute("PAGER", pager);
		return request;
	}

	public void addParamValues(HttpServletRequest request, boolean decode, String...keys){
		for(String key : keys){			
			String value = request.getParameter(key);
			if(TextUtil.isNotEmpty(value)){
				this.searchFieldCount++;
				if(decode){
					try{
						value = URLDecoder.decode(value, "UTF-8");
					}catch(Exception ex){
						logger.error(ex);
					}					
				}
				this.put(key, value);
			}
		}	
	}
	
	
	
}
