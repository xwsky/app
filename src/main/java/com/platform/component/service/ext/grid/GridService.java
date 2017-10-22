package com.platform.component.service.ext.grid;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.platform.core.Pager;
import com.platform.core.utils.TextUtil;
import com.platform.spring.jdbc.JdbcAssistant;
import com.platform.spring.mapper.ValueMapper;

@Component
public class GridService{	
	
	@Autowired
	private JdbcAssistant jdbcAssistant;
	
	/**
	 * 加载数据
	 * @param sql
	 * @param request
	 */
	public Map<String, Object> loadData(HttpServletRequest request, String sql){
		return loadData(request, sql, new Object[]{}, null);
	}
	
	/**
	 * 加载数据
	 * @param sql
	 * @param request
	 */
	public Map<String, Object> loadData(HttpServletRequest request, String sql, ValueMapper valueMapper){
		return loadData(request, sql, new Object[]{}, valueMapper);
	}
	
	/**
	 * 加载数据
	 * @param sql
	 * @param request
	 */
	public Map<String, Object> loadData(HttpServletRequest request, String sql, Object[] args){
		return loadData(request, sql, args, null);
	}
	
	/**
	 * 加载数据
	 * @param sql
	 * @param valueMapper
	 * @param request
	 */
	public Map<String, Object> loadData(HttpServletRequest request, String sql, Object[] args, ValueMapper valueMapper){
		GridTransModel model = new GridTransModel(request);			
		return loadDataGrid(model, sql, args, valueMapper);
	}
	
	/**
	 * 加载表格数据
	 * @param sql
	 * @param valueMapper
	 */
	private Map<String, Object> loadDataGrid(GridTransModel model, String sql, Object[] args, ValueMapper valueMapper){		
		//排序字段
		if(model.getSortColumn() != null && !"".equals(model.getSortColumn())){
			//将原有的order by干掉
			if(sql.toUpperCase().indexOf("ORDER BY") != -1){				
				sql = "SELECT * FROM (" + TextUtil.splitSqlOrderBy(sql)[0] +") A ";				
			}
			String orderByColumn = TextUtil.camelToUnderline(model.getSortColumn());
			sql += " ORDER BY " + orderByColumn + " " + model.getDirection();			
		}
		String totalSql = "SELECT COUNT(1) FROM (" + TextUtil.splitSqlOrderBy(sql)[0] + ") T_COUNT "; 
		int total = jdbcAssistant.queryAsInt(totalSql, args);		
		Pager<Map<String,Object>> pager = new Pager<Map<String,Object>>(total, model.getPageIndex(), model.getPager() ? model.getLimit() : total);
		pager = jdbcAssistant.queryAsPager(sql, args, valueMapper, pager);		
		return pager.toMap();
	}
	

}
