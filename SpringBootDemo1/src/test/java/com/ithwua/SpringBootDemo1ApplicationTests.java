package com.ithwua;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.ithwua.entity.Route;
import com.ithwua.service.IRouteService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootDemo1ApplicationTests {
	private static final Logger log = LoggerFactory.getLogger(SpringBootDemo1ApplicationTests.class);
	
	@Autowired
    private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	private RedisTemplate<String, Serializable> redisCacheTemplate;
	@Autowired
	private IRouteService routeService;
	//@Test
	public void get() {
        // TODO 测试线程安全
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        IntStream.range(0, 1000).forEach(i ->
                executorService.execute(() -> stringRedisTemplate.opsForValue().increment("kk", 1))
        );
        stringRedisTemplate.opsForValue().set("k1", "v1");
        final String k1 = stringRedisTemplate.opsForValue().get("k1");
        log.info("[字符缓存结果] - [{}]", k1);
        // TODO 以下只演示整合，具体Redis命令可以参考官方文档，Spring Data Redis 只是改了个名字而已，Redis支持的命令它都支持
        String key = "battcn:user:1";
        redisCacheTemplate.opsForValue().set(key, new Route("123","345"));
        // TODO 对应 String（字符串）
        final Route user = (Route) redisCacheTemplate.opsForValue().get(key);
        log.info("[对象缓存结果] - [{}]", user);
    }
	@Test
	public void Test() {
		final int rows  = routeService.saveOrUpdate(new Route("567", "456"));
		log.info("[saveOrUpdate] - [{}]", rows);
		final Route route = routeService.getInfoByAppid("appid");
        log.info("[get] - [{}]", route);
        routeService.delete("456");
	}

}
