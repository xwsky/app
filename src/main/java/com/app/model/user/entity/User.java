package com.app.model.user.entity;

import com.platform.spring.persistence.annotation.Id;
import com.platform.spring.persistence.annotation.Table;

import java.io.Serializable;

@Table("sys_user")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Serializable id;	//id
	private String name;	//姓名
	private String account;	//账号
	private String password;	//密码
	private String idCard;	//身份证号
	private Integer sex;	//性别 1:男 2:女
	private String phone;	//手机
	private java.sql.Timestamp birthday;	//生日
	private String remark;	//备注
	private String photo;	//头像照片
	private Integer enabled;	//1:启用 0:禁用
	private Integer isDelete;	//1:删除 0:未删除
	private Integer isOrgadmin;	//1/0 是否是机构管理员
	private Serializable createUserId;	//创建人id
	private Integer teacherId;	//教师关联ID

	public void setId(Serializable value) {
		this.id = value;
	}
	
	public Serializable getId() {
		return this.id;
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setAccount(String value) {
		this.account = value;
	}
	
	public String getAccount() {
		return this.account;
	}
	
	public void setPassword(String value) {
		this.password = value;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setIdCard(String value) {
		this.idCard = value;
	}
	
	public String getIdCard() {
		return this.idCard;
	}
	
	public void setSex(Integer value) {
		this.sex = value;
	}
	
	public Integer getSex() {
		return this.sex;
	}
	
	public void setPhone(String value) {
		this.phone = value;
	}
	
	public String getPhone() {
		return this.phone;
	}
	
	public void setBirthday(java.sql.Timestamp value) {
		this.birthday = value;
	}
	
	public java.sql.Timestamp getBirthday() {
		return this.birthday;
	}
	
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setPhoto(String value) {
		this.photo = value;
	}
	
	public String getPhoto() {
		return this.photo;
	}
	
	public void setEnabled(Integer value) {
		this.enabled = value;
	}
	
	public Integer getEnabled() {
		return this.enabled;
	}
	
	public void setIsDelete(Integer value) {
		this.isDelete = value;
	}
	
	public Integer getIsDelete() {
		return this.isDelete;
	}
	
	public void setIsOrgadmin(Integer value) {
		this.isOrgadmin = value;
	}
	
	public Integer getIsOrgadmin() {
		return this.isOrgadmin;
	}
	
	public Serializable getCreateUserId() {
		return createUserId;
	}
	
	public void setCreateUserId(Serializable createUserId) {
		this.createUserId = createUserId;
	}
	
	public void setTeacherId(Integer value) {
		this.teacherId = value;
	}
	
	public Integer getTeacherId() {
		return this.teacherId;
	}
	
}