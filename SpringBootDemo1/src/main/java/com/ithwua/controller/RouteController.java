package com.ithwua.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ithwua.entity.Route;
import com.ithwua.service.IRouteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value="/route")
@Api(tags = "1.1", description = "路由管理", value = "路由管理")
public class RouteController {
	
	@Autowired
	IRouteService routeService;
	
	@GetMapping(value="/getAll/{appid}")
	@ApiOperation(value = "获取全部数据")
	public PageInfo<Route> getAll(@PathVariable @ApiParam(name = "appid",value = "系统标识") String appid,PageHelper pageHelper){
		//使用lambda表达式
		final PageInfo<Route> pageInfo = PageHelper.startPage(1, 3)
				.setOrderBy("appid").doSelectPageInfo(() ->this.routeService.getAllInfo(appid));
		return pageInfo;
	}
}
