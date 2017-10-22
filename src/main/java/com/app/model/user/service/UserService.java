package com.app.model.user.service;

import java.io.Serializable;
import java.util.*;

import com.platform.core.http.Condition;
import com.platform.spring.baseclass.service.BaseService;
import com.app.model.user.entity.User;

public interface UserService extends BaseService<User>{
	
	/**
	 * 加载数据
	 * @return
	 */
	Map<String, Object> loadList(Condition condition);
	
	/**
	 * 加载单条数据
	 * @return
	 */
	Map<String, Object> load(Serializable id);
	
	
	/**
	 * 批量逻辑删除
	 * @param ids
	 */
	void updateDelete(Serializable[] ids);
}
