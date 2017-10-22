package com.platform.spring.baseclass.service.impl;



import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.platform.spring.baseclass.repository.BaseRepository;
import com.platform.spring.baseclass.service.BaseService;
import com.platform.spring.jdbc.JdbcAssistant;


/**
 * 基础服务抽象类
 *
 * @param <T>
 */
public abstract class BaseServiceImpl<T extends Serializable> implements BaseService<T>{	
	
	@Autowired
	protected JdbcAssistant jdbcAssistant;	
	
	@Override
	public T get(Serializable id) {		
		return getRepository().get(id);
	}

	@Override
	public Serializable save(T entity) {		
		return getRepository().save(entity);
	}
	
	@Override
	public int update(T entity) {	
		return getRepository().update(entity);
	}

	@Override
	public int deleteById(Serializable id) {		
		return getRepository().deleteById(id);
	}
	
	@Override
	public int[] deleteByIds(Serializable[] ids) {	
		return getRepository().deleteByIds(ids);
	}

	@Override
	public void deleteAll() {
		getRepository().deleteAll();
	}

	public abstract BaseRepository<T> getRepository();

}
