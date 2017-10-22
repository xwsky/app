package com.platform.component.service.ext.form;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.platform.core.http.messager.Messager;
import com.platform.spring.jdbc.JdbcAssistant;
import com.platform.spring.mapper.ValueMapper;

public class FormService {

	@Autowired
	private JdbcAssistant jdbcAssistant;	
	
	public Map<String, Object> loadData(Object obj) {	
		return Messager.getJsonMessager().success().data(obj);
	}
	
	public Map<String, Object> loadData(String sql) {
		Map<String, Object> map = jdbcAssistant.queryOne(sql, null, null);
		return Messager.getJsonMessager().success().data(map);
	}
	
	public Map<String, Object> loadData(String sql, Object[] args) {
		Map<String, Object> map = jdbcAssistant.queryOne(sql, args, null);
		return Messager.getJsonMessager().success().data(map);
	}
	
	public Map<String, Object> loadData(String sql, Object[] args, ValueMapper mapper) {
		Map<String, Object> map = jdbcAssistant.queryOne(sql, args, mapper);
		return Messager.getJsonMessager().success().data(map);
	}
	
}
