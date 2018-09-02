package com.ithwua.service;

import java.util.Map;

/**
 * shiro权限管理Service
 * @author Wangshun
 * @since 2018年9月2日
 */
public interface IShiroService {
	
	/**
	 * 初始化权限filter
	 */
	Map<String, String> initShiroFilter();
	/**
	 * 重新加载权限
	 */
	void updatePermission();
}
