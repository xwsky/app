package com.app.model.user.repository;

import java.io.Serializable;
import java.util.*;

import com.platform.core.http.Condition;
import com.app.model.user.entity.User;
import com.platform.spring.baseclass.repository.BaseRepository;


public interface UserRepository extends BaseRepository<User> {
	
	/**
	 * 获取查询sql语句
	 * @return
	 */
	public String getQuerySql();
	
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
