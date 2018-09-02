package com.ithwua.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.ithwua.entity.SysPermissionInit;

import tk.mybatis.mapper.common.BaseMapper;
@Mapper
/**
 * Shiro权限Dao层
 * @author Wangshun
 * @since 2018年9月2日
 */
public interface ISysPermissionInitDao extends BaseMapper<SysPermissionInit>{
	/**
	 * 获取初始化数据
	 */
	@Select(value="select * from SYS_PERMISSION_INIT order by sort asc")
	List<SysPermissionInit> getShiroInitFilterInfo();
	
}
