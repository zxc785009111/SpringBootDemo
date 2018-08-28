package com.ithwua.entity;

import java.io.Serializable;

import javax.persistence.Table;

import org.springframework.stereotype.Component;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Component
@ApiModel
@Table(name="ACCOUNTMANAGEMENTADDRESS")
public class Route implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1300641109547089043L;
	@ApiModelProperty("系统标识ID")
	private String appid;
	@ApiModelProperty("地址")
	private String address;

	public Route() {
		super();
	}
	public Route(String appid, String address) {
		super();
		this.appid = appid;
		this.address = address;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
