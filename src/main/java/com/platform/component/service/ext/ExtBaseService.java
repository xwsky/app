package com.platform.component.service.ext;


import org.springframework.beans.factory.annotation.Autowired;

import com.platform.spring.jdbc.JdbcAssistant;

/**
 * 组件服务基类
 * @author xw
 *
 */
public class ExtBaseService {
	
	@Autowired
	protected JdbcAssistant jdbcAssistant;
	
}
