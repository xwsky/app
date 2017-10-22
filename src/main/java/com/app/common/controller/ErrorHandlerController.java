package com.app.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.platform.core.http.messager.Messager;
import com.platform.core.utils.WebUtil;
import com.platform.spring.baseclass.controller.BaseController;
import com.platform.common.exception.InsException;

/**
 * 错误页面处理
 * @author xw
 *
 */
@Controller
public class ErrorHandlerController extends BaseController{	

	@RequestMapping(value = "/errorHandler/{errorType}")	
	public void errorHandler(@PathVariable("errorType") String errorType, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {		
		if("404".equals(errorType)){
			handler404(request, response);
			return;
		}else{
			if("500".equals(errorType)){
				handler500(request, response);			
			}else{
				// TODO					
			}				
			if(WebUtil.isAjax(request)){
				Messager.getJsonMessager().error().data("Request Exception..").send(response);
			}else{
				//一般请求
				response.sendRedirect(request.getContextPath() + "/common/500.jsp");
			}
		} 
		
	} 
	
	/**
	 * 处理404
	 */
	private void handler404(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//处理AJAX请求
		if(WebUtil.isAjax(request)){
			Messager.getJsonMessager().error().data("No Request Page..").error().send(response);		
		}else{	
			//一般请求
			response.sendRedirect(request.getContextPath() + "/common/404.jsp");
		}	
	}
	
	/**
	 * 处理500
	 */
	private void handler500(HttpServletRequest request, HttpServletResponse response) throws Exception{		
		Throwable ex = (Exception) request.getAttribute("javax.servlet.error.exception");
		Throwable causeEx = getExceptionCause(ex);		
		String msg = "ERROR_500";		
		if(causeEx instanceof InsException){
			// TODO 
		}
		response.setHeader("errorMsg", msg); 			
		
	}
	
	/**
	 * 获取起因异常
	 * @param ex
	 * @return
	 */
	private Throwable getExceptionCause(Throwable ex){
		Throwable cause = ex.getCause();
		if(cause != null){
			return getExceptionCause(cause);
		}else {
			return ex;			
		}		
	}
	
	
}
