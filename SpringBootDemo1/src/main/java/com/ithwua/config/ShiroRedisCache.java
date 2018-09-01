package com.ithwua.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

/**
 * 为shiro量身定做的一个redis cache,为Authorization cache做了特别优化
 */
@Component
public class ShiroRedisCache<K, V> implements Cache<K, V> {
	@Resource
	private RedisTemplate redisTemplate;
	private String prefix = "shiro_redis:";
	private static final Logger log = LoggerFactory.getLogger(ShiroRedisCache.class);
	
	public ShiroRedisCache() {
		super();
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public ShiroRedisCache(RedisTemplate redisTemplate){
		this.redisTemplate = redisTemplate;
	}

	public ShiroRedisCache(RedisTemplate redisTemplate,String prefix){
		this(redisTemplate);
    	this.prefix = prefix;
	}

	@Override
	public V get(K k) throws CacheException {
		log.info("从Redis中获取数据");
		if (k == null) {
			return null;
		}
		byte[] bytes = getBytesKey(k);
		V v = (V)redisTemplate.opsForValue().get(bytes);
		log.info("K:"+k);
		log.info("V:"+v);
		return v;
	}

	@Override
	public V put(K k, V v) throws CacheException {
		log.info("往Redis中存数据");
		if (k== null || v == null) {
			return null;
		}
		byte[] bytes = getBytesKey(k);
		redisTemplate.opsForValue().set(bytes, v);
		log.info("K:"+k);
		log.info("V:"+v);
		return v;
	}

@Override
public V remove(K k) throws CacheException {
    if(k==null){
        return null;
    }
    byte[] bytes =getBytesKey(k);
    V v = (V)redisTemplate.opsForValue().get(bytes);
    redisTemplate.delete(bytes);
    return v;
}

@Override
public void clear() throws CacheException {
    redisTemplate.getConnectionFactory().getConnection().flushDb();
}

@Override
public int size() {
    return redisTemplate.getConnectionFactory().getConnection().dbSize().intValue();
}

@Override
public Set<K> keys() {
    byte[] bytes = (prefix+"*").getBytes();
    Set<byte[]> keys = redisTemplate.keys(bytes);
    Set<K> sets = new HashSet<>();
    for (byte[] key:keys) {
        sets.add((K)key);
    }
    return sets;
}

@Override
public Collection<V> values() {
    Set<K> keys = keys();
    List<V> values = new ArrayList<>(keys.size());
    for(K k :keys){
        values.add(get(k));
    }
    return values;
}

private byte[] getBytesKey(K key){
    if(key instanceof String){
        String prekey = this.prefix + key;
        return prekey.getBytes();
    }else {
        return SerializationUtils.serialize(key);
    }
}}