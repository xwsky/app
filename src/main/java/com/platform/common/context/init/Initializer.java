package com.platform.common.context.init;

import javax.servlet.ServletContext;

public interface Initializer {

	void before(ServletContext ctx) throws Exception;
	
	void init(ServletContext ctx) throws Exception;
	
	void after(ServletContext ctx) throws Exception;
	
}
