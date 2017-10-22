package com.platform.spring.baseclass.repository;



import java.io.Serializable;




/**
 * 基础DAO接口
 * @author xw
 *
 * 
 */
public interface BaseRepository<T extends Serializable> {

	/**
	 * 根据id获取对象
	 * @param id
	 * @return T
	 */	
	T get(Serializable id);
	
	/**
	 * 保存
	 * @param entity
	 * @return Serializable
	 */
	Serializable save(T entity);
	
	/**
	 * 更新
	 * @param entity
	 */
	int update(T entity);
	
	/**
	 * 根据id删除
	 * @param id
	 */
	int deleteById(Serializable id);
	
	/**
	 * 根据id数组删除
	 * @param ids
	 */
	int[] deleteByIds(Serializable[] ids);
	
	/**
	 * 删除所有
	 */
	void deleteAll();	
	
	
}
