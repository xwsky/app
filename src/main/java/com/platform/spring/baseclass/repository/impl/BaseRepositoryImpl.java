package com.platform.spring.baseclass.repository.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.platform.core.utils.TextUtil;
import com.platform.spring.baseclass.repository.BaseRepository;
import com.platform.spring.jdbc.JdbcAssistant;
import com.platform.spring.mapper.BeanPropertyRowMapper;
import com.platform.spring.persistence.annotation.Generator;
import com.platform.spring.persistence.annotation.Id;
import com.platform.spring.persistence.annotation.Table;
import com.platform.spring.persistence.annotation.Transient;
import com.platform.spring.persistence.constant.DataBaseType;
import com.platform.spring.persistence.constant.Operation;
import com.platform.spring.persistence.constant.Strategy;

/**
 * 基础DAO实现类
 * @author xw
 *
 * @param <T>
 */
public class BaseRepositoryImpl<T extends Serializable> implements BaseRepository<T>{
	
	protected Logger logger = Logger.getLogger(this.getClass());
		
	@Autowired
	protected JdbcAssistant jdbcAssistant;
	
	private Class<T> entityClass;
	
	@SuppressWarnings("unchecked")
	public Class<T> getEntityClass() {
		if (entityClass == null) {			
			ParameterizedType type = (ParameterizedType) getClass()
					.getGenericSuperclass();
			entityClass = (Class<T>) type.getActualTypeArguments()[0];
		}
		return entityClass;
	}

	@Override
	public T get(Serializable id) {			
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT * FROM ");
		sb.append(" "+getTableName()+" ");
		sb.append(" WHERE ");
		sb.append(" "+getIdColumnName()+" ");
		sb.append(" = ?");
		T entity = (T) jdbcAssistant.queryAsObject(sb.toString(), new Object[] { id },
				BeanPropertyRowMapper.newInstance(getEntityClass()));			
		return entity;
	}

	@Override
	public Serializable save(T entity) {
		String sql = buildSql(Operation.INSERT);
		Serializable f_id = autoInsert(entity, sql);
		return f_id;
	}

	@Override
	public int update(T entity) {
		String sql = buildSql(Operation.UPDATE);
		Object[] args = getArgs(Operation.UPDATE, entity, null);	
		return jdbcAssistant.update(sql,args);
	}

	@Override
	public int deleteById(Serializable id) {		
		String sql = buildSql(Operation.DELETE);
		StringBuffer sb = new StringBuffer();
		sb.append(sql);
		sb.append(" WHERE ");
		sb.append(getIdColumnName());
		sb.append(" = ?");
		return jdbcAssistant.update(sb.toString(), new Object[]{id});
	}
	
	@Override
	public int[] deleteByIds(final Serializable[] ids) {
		if(ids==null || ids.length == 0){
			throw new IllegalArgumentException();
		}
		String sql = buildSql(Operation.DELETE) + " WHERE " + getIdColumnName() + " = ?";	
		return jdbcAssistant.batchUpdate(sql, new BatchPreparedStatementSetter() {			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				 ps.setObject(1, ids[i]);  			
			}			
			@Override
			public int getBatchSize() {
				return ids.length;
			}
		});
		
	}

	@Override
	public void deleteAll() {
		String sql = " TRUNCATE TABLE " + getTableName();		
		jdbcAssistant.update(sql);
	}

	
	
	/**
	 * 根据标示创建sql语句
	 * @param Oper
	 * @return String 
	 */
	private String buildSql(Operation Oper){		
		Field[] fields =
				getEntityClass().getDeclaredFields();
		String sql = "";
		switch (Oper) {
		case INSERT:
			sql = buildInsertSql(fields);
			break;
		case UPDATE:
			sql = buildUpdateSql(fields);
			break;
		case DELETE:
			sql = buildDeleteSql(fields);
			break;	
		case SELECT:
			sql = buildSelectSql(fields);
			break;	
		default:
			break;
		}
		return sql;
	} 
	
	/**
	 * 获取参数数组
	 * @param Oper     sql表示 INSERT、UPDATE、DELETE
	 * @param entity   实体类
	 * @param f_id	   insert时使用的id(主键生成策略为UUID、或手动填写时使用)
	 * 				      如果主键生成策略为identity时传null
	 * @return Object[]
	 */
	private Object [] getArgs(Operation Oper, T entity, String f_id){
		Field[] fields = 
				getEntityClass().getDeclaredFields();
		Object [] args = null;
		if(Oper == Operation.INSERT){
			args = getInsertArgs(fields, entity, f_id);
		}else if(Oper == Operation.UPDATE){
			args = getUpdateArgs(fields, entity);
		}		
		return args;
	}
	
	
	/**
	 * 创建insert语句
	 * @param fields
	 * @return String
	 */
	private String buildInsertSql(Field[] fields){
		String id = getIdColumnName();
		StringBuffer sb = new StringBuffer();
		int fieldCount = 0;
		sb.append(" INSERT INTO ");
		sb.append(" "+getTableName()+" ");
		sb.append("(");
		for(Field field : fields){
			if(field.isAnnotationPresent(Transient.class)){
				continue;
			}	
			if(isSerialVersionUuid(field)){
				continue;
			}
			//field.setAccessible(true);
			String column = TextUtil.camelToUnderline(field.getName());
			if(column.equals(id)){				
				//存在主键生成策略
				Strategy strategy = getIdStrategy();
				if(strategy != null){
					//如果生成策略为自增长，不将Id加入到参数中
					if(strategy == Strategy.IDENTITY){
						continue;
					}					
				}
				//不存在
				else{
					//mysql和sqlserver时，默认为自增长，不将id加入到参数中
					DataBaseType type = jdbcAssistant.getDatabaseType();
					if(type == DataBaseType.MYSQL || type == DataBaseType.SQLSERVER){
						continue;
					}			
				}	
			}
			fieldCount++;			
			sb.append(column).append(",");
		}
		sb = sb.deleteCharAt(sb.length() - 1);
		sb.append(") VALUES (");
		for(int i=0;i<fieldCount;i++){
			sb.append("?,");
		}
		sb = sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		return sb.toString();
	}
	
	/**
	 * 创建update语句
	 * @param fields
	 * @return String
	 */
	private String buildUpdateSql(Field[] fields){
		String id = getIdColumnName();
		StringBuffer sb = new StringBuffer();
		sb.append(" UPDATE ");
		sb.append(" "+getTableName()+" ");
		sb.append(" SET ");
		for(Field field : fields){
			if(field.isAnnotationPresent(Transient.class)){
				continue;
			}
			if(isSerialVersionUuid(field)){
				continue;
			}
			String column = TextUtil.camelToUnderline(field.getName());				
			if(column.equals(id)){
				continue;
			}
			sb.append(column).append("=").append("?,");
		}
		sb = sb.deleteCharAt(sb.length() - 1);
		sb.append(" WHERE "+id+"=?");
		return sb.toString();
	}
	
	/**
	 * 创建删除语句
	 * @param fields
	 * @return String
	 */
	public String buildDeleteSql(Field[] fields){
		StringBuffer sb = new StringBuffer();
		sb.append(" DELETE FROM ");
		sb.append(" "+getTableName()+" ");
		return sb.toString();
	}	
	
	/**
	 * 创建查询语句
	 * @param fields
	 * @return String
	 */
	public String buildSelectSql(Field[] fields){
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT ");
		for(Field field : fields){
			if(field.isAnnotationPresent(Transient.class)){
				continue;
			}
			if(isSerialVersionUuid(field)){
				continue;
			}
			String column = TextUtil.camelToUnderline(field.getName());
			sb.append(column).append(",");			
		}
		sb = sb.deleteCharAt(sb.length() - 1);
		sb.append(" FROM ");
		sb.append(" "+getTableName()+" ");
		return sb.toString();
	}	
	
	/**
	 * 获取新增参数数组
	 * @param fields	实体类的字段列表
	 * @param entity	实体类
	 * @param f_id		insert时使用的id(主键生成策略为UUID、或手动填写时使用) 
	 * 					如果主键生成策略为identity时传null
	 * @return
	 */
	private Object[] getInsertArgs(Field[] fields, T entity, String f_id){
		String id = getIdColumnName();
		List<Object> args = new ArrayList<Object>();		
		for(Field field : fields){
			if(field.isAnnotationPresent(Transient.class)){
				continue;
			}	
			if(isSerialVersionUuid(field)){
				continue;
			}
			String column = TextUtil.camelToUnderline(field.getName());				
			if(column.equals(id)){					
				if(TextUtil.isNotEmpty(f_id)){
					args.add(f_id);
				}				
			}else{
				field.setAccessible(true);
				try {
					args.add(field.get(entity));
				}  catch (IllegalAccessException e) {					
					e.printStackTrace();
				}
			}
		}	
		return args.toArray();
	}
	
	/**
	 * 获取更新参数组
	 * @param fields
	 * @param entity
	 * @return Object[]
	 */
	private Object[] getUpdateArgs(Field[] fields, T entity){
		String id = getIdColumnName();
		Object idValue = null;
		List<Object> args = new ArrayList<Object>();
		for(Field field : fields){
			if(field.isAnnotationPresent(Transient.class)){
				continue;
			}	
			if(isSerialVersionUuid(field)){
				continue;
			}
			String column = TextUtil.camelToUnderline(field.getName());			
			if(column.equals(id)){	
				idValue = getIdValue(entity, field);
				continue;
			}else{
				field.setAccessible(true);
				try {
					args.add(field.get(entity));
				}  catch (IllegalAccessException e) {					
					e.printStackTrace();
				}
			}
		}
		args.add(idValue);
		return args.toArray();
	}

	/**
	 * 以自增长方式插入（一般用于MySQL, SQLSERVER）
	 * @param entity
	 * @param sql
	 * @return
	 */
	private Serializable identityInsert(T entity, String sql){
		final Object[] args = getArgs(Operation.INSERT, entity, null);		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcAssistant.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement  ps = 
						con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				for(int i=1;i<=args.length;i++){
					ps.setObject(i, args[i-1]);
				}				
				return ps;
			}
		}, keyHolder);	
		return keyHolder.getKey();
	}
	
	/**
	 * uuid插入
	 * @param entity
	 * @param sql
	 * @return
	 */
	private Serializable uuidInsert(T entity, String sql){
		String idValue = TextUtil.getUUID();
		Object[] uArgs = getArgs(Operation.INSERT, entity, idValue);
		jdbcAssistant.update(sql, uArgs);
		return idValue;
	}
	
	/**
	 * 手动设置id插入
	 * @param entity
	 * @param sql
	 * @return
	 */
	private Serializable manualInsert(T entity, String sql){		
		String idValue = getIdValue(entity);
		Object[] uArgs = getArgs(Operation.INSERT, entity, idValue);
		jdbcAssistant.update(sql, uArgs);
		return idValue;
	}
	
	/**
	 * 插入数据
	 * @param entity
	 * @param sql
	 * @return
	 */
	private Serializable autoInsert(T entity, String sql){			
		Serializable f_id = null;			
		Strategy strategy = getIdStrategy();
		//存在主键生成策略
		if(strategy != null){		
			switch (strategy) {
				case IDENTITY:
					f_id = identityInsert(entity, sql);
					break;
				case UUID:
					f_id = uuidInsert(entity, sql);
					break;
				case MANUAL:
					f_id = manualInsert(entity, sql);
					break;					
				default:
					break;
			}
		}
		//不存在
		else{
			DataBaseType type = jdbcAssistant.getDatabaseType();		
			if(type == DataBaseType.MYSQL){
				f_id = identityInsert(entity, sql);
			}else if(type == DataBaseType.SQLSERVER){
				f_id = identityInsert(entity, sql);
			}else if(type == DataBaseType.ORACLE){
				f_id = TextUtil.getUUID();
				Object[] uArgs = getArgs(Operation.INSERT, entity, String.valueOf(f_id));
				jdbcAssistant.update(sql, uArgs);
			}
		}	
		return f_id;
	}
	
	
	/**
	 * 获取主键生成策略
	 * @return Strategy
	 */
	private Strategy getIdStrategy(){
		Class<?> c = getEntityClass();
		Field [] fields = c.getDeclaredFields();
		for(Field field : fields){
			if(field.isAnnotationPresent(Id.class)
					&& field.isAnnotationPresent(Generator.class)){
				Generator generator = field.getAnnotation(Generator.class);
				return generator.strategy();
			}
		}
		return null;
	}		
	
	/**
	 * 获取主键值	
	 * @return Strategy
	 */
	private String getIdValue(T entity){
		Class<?> c = getEntityClass();
		Field [] fields = c.getDeclaredFields();
		for(Field field : fields){
			if(field.isAnnotationPresent(Id.class)){
				return getIdValue(entity, field);
			}
		}
		return null;
	}	
	private String getIdValue(T entity, Field field){
		Object idValue = null;
		field.setAccessible(true);
		try {
			idValue = field.get(entity);
		}  catch (IllegalAccessException e) {					
			e.printStackTrace();
		}
		return idValue != null ? String.valueOf(idValue) : null;
	}
	
	/**
	 * 获取主键列名
	 * @return String
	 */
	private String getIdColumnName(){		
		String name = "";
		Class<?> c = getEntityClass();
		Field [] fields = c.getDeclaredFields();				
		for(Field field : fields){
			if(field.isAnnotationPresent(Id.class)){
				name = TextUtil.camelToUnderline(field.getName());
			}
		}
		if(TextUtil.isEmpty(name)){
			logger.error("Get PK Exception，Entity No @Id");
			throw new RuntimeException("Get PK Exception，Entity No @Id");			
		}		
		return name;
	}
	
	/**
	 * 获取表名
	 * @return String
	 */
	private String getTableName(){
		Table table =
				getEntityClass().getAnnotation(Table.class);
		if(table == null){
			logger.error("获取表名发生异常，实体类没有定义@Table");
			throw new RuntimeException("获取表名发生异常，实体类没有定义@Table");			
		}
		String tableName = table.value();
		//如果设置了表名，则使用设置的表名
		if(TextUtil.isEmpty(tableName)){
			tableName = TextUtil.camelToUnderline(entityClass.getSimpleName());
		}
		String schema = table.schema();
		//如果设置了schema，则使用schema.tableName的方式
		if(TextUtil.isNotEmpty(schema)){
			tableName = schema + "." + tableName;
		}		
		return tableName;				
	}
	
	/**
	 * 判断字段是否为static、final
	 * @param field
	 * @return boolean
	 */
	public boolean isSerialVersionUuid(Field field){
		boolean flag = false;
		if(Modifier.isStatic(field.getModifiers())
				&& Modifier.isFinal(field.getModifiers())){
			flag = true;
		}
		return flag;
	}
	
	
}


