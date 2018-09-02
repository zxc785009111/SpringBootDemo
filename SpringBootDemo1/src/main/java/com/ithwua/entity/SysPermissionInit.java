package com.ithwua.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 初始化shiro权限表
 * @author Wangshun
 * @since 2018年9月2日
 */
@ApiModel
@Table(name="SYS_PERMISSION_INIT")
public class SysPermissionInit implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5559665683443107350L;
	
	@ApiModelProperty("主键ID")
	private Long id;
	@ApiModelProperty("路径")
	private String url;
	@ApiModelProperty("初始化权限")
	private String permissioninit;
	@ApiModelProperty("排序,用于规定配置时拦截的顺序")
	private String sort;
	
	
	
	
	public SysPermissionInit() {
		super();
	}




	public SysPermissionInit(Long id, String url, String permissioninit, String sort) {
		super();
		this.id = id;
		this.url = url;
		this.permissioninit = permissioninit;
		this.sort = sort;
	}




	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public String getUrl() {
		return url;
	}




	public void setUrl(String url) {
		this.url = url;
	}




	public String getPermissioninit() {
		return permissioninit;
	}




	public void setPermissioninit(String permissioninit) {
		this.permissioninit = permissioninit;
	}




	public String getSort() {
		return sort;
	}




	public void setSort(String sort) {
		this.sort = sort;
	}
	
	


	
	
	
}
