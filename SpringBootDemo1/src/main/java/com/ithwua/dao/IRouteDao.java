package com.ithwua.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ithwua.entity.Route;

import tk.mybatis.mapper.common.BaseMapper;
@Mapper
public interface IRouteDao extends BaseMapper<Route>{
	/**
	 * 获取表中的所有地址(测试)
	 * <p>第一种是基于mybatis3.x版本后提供的注解方式<p/>
	 * @return
	 */
	
	@Select("select * from ACCOUNTMANAGEMENTADDRESS where appid!=#{appid}")
	List<Route> getAllAddress(@Param(value = "appid") String appid);
	
	List<Route> getAllAddressList();
	/**
	 * 根据appid 查询路由信息
	 * @return
	 */
	@Select("select * from ACCOUNTMANAGEMENTADDRESS where appid=#{appid}")
	Route getInfoByAppid(@Param(value="appid")String appid);
	
	int saveOrUpdate(Route route);
	@Delete("delete from ACCOUNTMANAGEMENTADDRESS where appid = #{appid}")
	int deleteByAppid(@Param(value="appid")String appid);
}
