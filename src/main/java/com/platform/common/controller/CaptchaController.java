package com.platform.common.controller;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.platform.core.utils.CaptchaUtil;
import com.platform.core.utils.TextUtil;
import com.platform.spring.baseclass.controller.BaseController;

/**
 * 生成验证码
 * @author xw
 */
@Controller
@RequestMapping("/captcha")
public class CaptchaController extends BaseController{
	
	@RequestMapping("/generate")
	public void generate(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String type = request.getParameter("type");
		if(TextUtil.isEmpty(type)){
			throw new IllegalArgumentException();
		}
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		ServletOutputStream responseOutputStream = response.getOutputStream();
		String code = CaptchaUtil.generateVerifyCode(4);
		request.getSession().setAttribute(type, code);
		int width = 200, height = 80;
		CaptchaUtil.outputImage(width, height, responseOutputStream, code);
		responseOutputStream.flush();
		responseOutputStream.close();				
	}
		
}
