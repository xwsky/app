package com.platform.spring.persistence.constant;



public enum Strategy {
	
	/**
	 * 主键自增长，由数据库控制
	 */
	IDENTITY,
	/**
	 * 自动写入uuid
	 */
	UUID,
	/**
	 * 由程序控制，自己填写id
	 */
	MANUAL
		
}
