package com.app.model.user.repository.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.platform.component.service.ext.grid.GridService;
import com.platform.core.http.Condition;
import com.platform.spring.baseclass.repository.impl.BaseRepositoryImpl;
import com.app.model.user.entity.User;
import com.app.model.user.repository.UserRepository;

@Repository("UserRepositoryImpl")
public class UserRepositoryImpl extends BaseRepositoryImpl<User> implements UserRepository{
	
	@Autowired
	private GridService gridService;	
	
	
	@Override
	public String getQuerySql() {
		String sql = "SELECT t.* FROM sys_user t WHERE 1=1 ";
		return sql;
	}	

	@Override
	public Map<String, Object> loadList(Condition condition) {	
		String sql = getQuerySql();
		List<Object> queryParams = new ArrayList<Object>();
		sql+=" order by id desc";
		return gridService.loadData(condition.getRequest(), sql, queryParams.toArray());
	}
	
	@Override
	public Map<String, Object> load(Serializable id) {
		String sql = getQuerySql();
		sql+= " and t.id = ? ";
		return jdbcAssistant.queryOne(sql, new Object[]{id});
	}
	
	/**
	 * 重写父类get方法
	 */
	@Override
	public User get(Serializable id) {		
		return super.get(id);
	}
	
	/**
	 * 重写父类save方法
	 */
	@Override
	public Serializable save(User entity) {	
		return super.save(entity);
	}
	
	/**
	 * 重写父类update方法
	 */
	@Override
	public int update(User entity) {		
		return super.update(entity);
	}
	
	/**
	 * 重写父类deleteById方法
	 */
	@Override
	public int deleteById(Serializable id) {		
		return super.deleteById(id);
	}
	
	/**
	 * 重写父类deleteByIds方法
	 */
	@Override
	public int[] deleteByIds(Serializable[] ids) {		
		return super.deleteByIds(ids);
	}
	
	@Override
	public void updateDelete(Serializable[] ids) {	
		if(ids != null && ids.length > 0){
			String[] sqls = new String[ids.length];
			for(int i=0; i<ids.length; i++){
				sqls[i] ="update sys_user set is_del=1 where id='"+ids[i]+"'";		
			}
			jdbcAssistant.batchUpdate(sqls);
		}
	}
}
