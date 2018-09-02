package com.ithwua.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ithwua.service.IShiroService;

@RestController
public class ShiroController {
	
	
	@Autowired
	IShiroService shiroService;
	
	@GetMapping("/reloadShiro")
	public String reloadShiroFilter() {
		shiroService.updatePermission();
		return "成功初始化权限";
	}
}
