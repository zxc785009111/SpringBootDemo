package com.ithwua.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
	@GetMapping("/get")
	public String get() {
		return "get";
	}
	/**
	 * RequiresRoles 是所需角色 包含 AND 和 OR 两种
	 * 注意：这里大括号 中写几个角色，他就会获取几次角色的对象，比如这里写3个，他就会执行三遍那个自定义remale中的方法
     * RequiresPermissions 是所需权限 包含 AND 和 OR 两种
	 * @return
	 */
	@RequiresRoles(value= {"admin","test02"},logical=Logical.OR)
	//@RequiresPermissions(value= {"user:query"},logical=Logical.OR)
	@GetMapping("/query")
	public String query() {
		return "query";
	}
	//@RequiresPermissions(value= {"user:find"},logical=Logical.OR)
	@GetMapping("/find")
    public String find() {
        return "find.....";
    }
}
