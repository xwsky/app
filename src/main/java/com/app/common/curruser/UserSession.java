package com.app.common.curruser;

import com.platform.common.context.App;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取当前用户类
 * @author xw
 *
 */
public class UserSession {
	
	public static CurrentUser getCurrentUser(HttpServletRequest request){
		
		try{
			Object obj = request.getSession().getAttribute(App.CURRENT_USER);
			
			if(obj != null){
				return (CurrentUser)obj;
			}
		}catch(Exception ex){
			return null;
		}		
		
		return null;
	}
		
	
}
