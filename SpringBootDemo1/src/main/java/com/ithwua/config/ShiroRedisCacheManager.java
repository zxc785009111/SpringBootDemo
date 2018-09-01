package com.ithwua.config;

import javax.annotation.Resource;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 自定义缓存(Redis)
 * @author Wangshun
 * @since 2018年9月1日
 */
public class ShiroRedisCacheManager implements CacheManager {
	@Resource
	private ShiroRedisCache redisCache;
	
	private static final Logger log = LoggerFactory.getLogger(ShiroRedisCacheManager.class);


	@Override
	public <K, V> Cache<K, V> getCache(String s) throws CacheException {
		log.info("获取Cache对象");
		return redisCache;
	}


	
}
