package com.ithwua.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ithwua.dao.IRouteDao;
import com.ithwua.entity.Route;
import com.ithwua.service.IRouteService;
@Service
public class RouteServiceImpl implements IRouteService{
	@Autowired
	IRouteDao routeDao;
	private static final Logger log = LoggerFactory.getLogger(RouteServiceImpl.class);

	@Override
	public List<Route> getAllInfo(String appid) {
		return routeDao.getAllAddress(appid);
	}
	/**
	 * value值其表示当前方法的返回值是会被缓存在哪个Cache上的，
	 * 对应Cache的名称。其可以是一个Cache也可以是多个Cache，
	 * 当需要指定多个Cache时其是一个数组 例如：{"cache1", "cache2"}
	 * 
	 * key值指的是参数的名字，写法：#参数名,#p参数index(例如:#p0)
	 */
	@Cacheable(value = "GetRoute", key = "#appid")
	@Override
	public Route getInfoByAppid(String appid) {
		log.info("进入 get 方法");
		return routeDao.getInfoByAppid(appid);
	}
	@CachePut(value="Route",key="#route.appid")
	@Override
	public int saveOrUpdate(Route route) {
		
		Route infoByAppid = routeDao.getInfoByAppid(route.getAppid());
		if(StringUtils.isEmpty(infoByAppid)) {
			log.info("进入 SaveOrUpdate 方法");
			routeDao.saveOrUpdate(route);
		}
		return 0;
	}
	@CacheEvict(value="route",key="#appid")
	@Override
	public int delete(String appid) {
		log.info("进入 delete 方法");
		return routeDao.deleteByAppid(appid);
	}

}
