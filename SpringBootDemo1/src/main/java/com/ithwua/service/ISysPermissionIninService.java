package com.ithwua.service;

import java.util.Map;

/**
 * shiro权限Service层
 * @author Wangshun
 * @since 2018年9月2日
 */
public interface ISysPermissionIninService {
	/**
	 * 获取初始化数据
	 */
	Map<String, String> getShiroInitFilterInfo();


}
