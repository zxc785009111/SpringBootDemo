package com.ithwua.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ithwua.dao.ISysPermissionInitDao;
import com.ithwua.entity.SysPermissionInit;
import com.ithwua.service.ISysPermissionIninService;
@Service
public class SysPermissionInitServiceImpl implements ISysPermissionIninService{

	@Autowired
	ISysPermissionInitDao sysPermissionInitDao;
	
	
	@Override
	public Map<String, String> getShiroInitFilterInfo() {
		 List<SysPermissionInit> shiroInitFilterInfoList = sysPermissionInitDao.getShiroInitFilterInfo();
		 Map<String, String> buildMapList = buildMapList(shiroInitFilterInfoList);
		 return buildMapList;
	}
	
	Map<String, String> buildMapList(List<SysPermissionInit> list){
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (SysPermissionInit sysPermissionInit : list) {
			map.put(sysPermissionInit.getUrl(), sysPermissionInit.getPermissioninit());
		}
		return map;
	}

}
