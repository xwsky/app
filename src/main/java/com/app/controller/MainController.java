package com.app.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.platform.common.controller.InsBaseController;
import com.platform.core.http.messager.Messager;
import com.platform.core.utils.TextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController extends InsBaseController {
	
	
	@RequestMapping("/main")
	public String main(HttpServletRequest request){		
		
		//neptune
		//crisp
		//classic			
		Object style = request.getSession().getAttribute("EXT_STYLE");
		if(style == null){
			//style = "crisp";			
		}else{
			if(!style.equals("crisp") && !style.equals("neptune")&& !style.equals("classic")){
				style = "crisp";
			}
		}			
		request.getSession().setAttribute("EXT_STYLE", style);
		
		
		return "main";
	}

} 
