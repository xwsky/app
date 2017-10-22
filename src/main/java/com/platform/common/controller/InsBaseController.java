package com.platform.common.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.platform.core.utils.JsonUtil;
import com.platform.core.utils.TextUtil;
import com.platform.spring.baseclass.controller.BaseController;

public class InsBaseController extends BaseController{

	protected Logger logger = Logger.getLogger(this.getClass());	

	/**
	 * 获取request中的字符串转数组
	 * @param request
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected String[] getSelectedItems(HttpServletRequest request, String name) {
		List<String> items = new ArrayList<String>();
		String selectedItmes = request.getParameter(name == null ? "SELECT_ITEMS" : name);
		if (TextUtil.isNotEmpty(selectedItmes)) {
			items = JsonUtil.toObject(selectedItmes, List.class);
		}
		String[] ids = new String[items.size()];
		for (int i = 0; i < items.size(); i++) {
			ids[i] = items.get(i).toString();
		}
		return ids;
	}
	
}
