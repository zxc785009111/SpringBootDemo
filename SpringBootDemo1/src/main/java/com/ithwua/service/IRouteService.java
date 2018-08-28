package com.ithwua.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ithwua.entity.Route;

public interface IRouteService {
	List<Route> getAllInfo(String appid);
	
	Route getInfoByAppid(String appid);
	
	int saveOrUpdate(Route route);
	int delete(String appid);
}
