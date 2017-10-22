package com.platform.core.http.messager;

import javax.servlet.http.HttpServletResponse;

public interface IMessager {
	
	public void send(HttpServletResponse response);
	
}
