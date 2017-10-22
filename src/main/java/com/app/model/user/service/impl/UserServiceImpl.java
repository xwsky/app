package com.app.model.user.service.impl;

import java.io.Serializable;
import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Qualifier;

import com.platform.core.http.Condition;
import com.platform.spring.baseclass.repository.BaseRepository;
import com.platform.spring.baseclass.service.impl.BaseServiceImpl;
import com.app.model.user.entity.User;
import com.app.model.user.repository.UserRepository;
import com.app.model.user.service.UserService;

@Service("UserServiceImpl")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{	
	
	@Autowired
	@Qualifier("UserRepositoryImpl")
	private UserRepository userRepository;

	@Override
	public BaseRepository<User> getRepository() {		
		return userRepository;
	}
	
	@Override
	public Map<String, Object> loadList(Condition condition) {			
		return userRepository.loadList(condition);
	}
	
	@Override
	public Map<String, Object> load(Serializable id) {			
		return userRepository.load(id);
	}
	
	/**
	 * 重写父类get方法
	 */
	@Override
	public User get(Serializable id) {		
		return userRepository.get(id);
	}
	
	/**
	 * 重写父类save方法
	 */
	@Override
	public Serializable save(User entity) {	
		Serializable returnValue = userRepository.save(entity);
		return returnValue;
	}
	
	/**
	 * 重写父类update方法
	 */
	@Override
	public int update(User entity) {
		int returnValue = userRepository.update(entity);
		return returnValue;
	}
	
	/**
	 * 重写父类deleteById方法
	 */
	@Override
	public int deleteById(Serializable id) {	
		int returnValue = userRepository.deleteById(id);
		return returnValue;
	}
	
	/**
	 * 重写父类deleteByIds方法
	 */
	@Override
	public int[] deleteByIds(Serializable[] ids) {	
		int [] returnValue = new int[ids.length];
		returnValue = userRepository.deleteByIds(ids);
		return returnValue;
	}
	
	@Override
	public void updateDelete(Serializable[] ids) {			
		userRepository.updateDelete(ids);
	}

}
