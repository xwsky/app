package com.app.common.curruser;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * 当前用户
 * @author xw
 *
 */
public class CurrentUser implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String account;
	private String departmentId;
	private String departmentName;
	
	private Map<String, Map<String, Object>> operateMap;
	private List<String> operateResources;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}	
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}	
	public Map<String, Map<String, Object>> getOperateMap() {
		return operateMap;
	}
	public void setOperateMap(Map<String, Map<String, Object>> operateMap) {
		this.operateMap = operateMap;
	}
	public List<String> getOperateResources() {
		return operateResources;
	}
	public void setOperateResources(List<String> operateResources) {
		this.operateResources = operateResources;
	}
	
}
