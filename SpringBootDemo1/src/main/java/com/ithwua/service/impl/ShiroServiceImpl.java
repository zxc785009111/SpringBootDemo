package com.ithwua.service.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ithwua.controller.LoginController;
import com.ithwua.service.IShiroService;
import com.ithwua.service.ISysPermissionIninService;

/**
 * Shiro权限管理Service实现类
 * @author Wangshun
 * @since 2018年9月2日
 */
@Service
public class ShiroServiceImpl implements IShiroService {
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	
	@Autowired
	ISysPermissionIninService sysPermissionInitService;
	
	@Autowired
	ShiroFilterFactoryBean shiroFilterFactoryBean;
	@Override
	public Map<String, String> initShiroFilter() {
		log.info("创建initShiroFilter");
	    // TODO 重中之重啊，过滤顺序一定要根据自己需要排序
	    Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
	    // 需要验证的写 authc 不需要的写 anon
	    // anon：它对应的过滤器里面是空的,什么都没做
	    log.info("##################从数据库读取权限规则，加载到shiroFilter中##################");
	    Map<String, String> shiroInitFilterInfo = sysPermissionInitService.getShiroInitFilterInfo();
	    // 不用注解也可以通过 API 方式加载权限规则
	   filterChainDefinitionMap.putAll(shiroInitFilterInfo);
	   filterChainDefinitionMap.put("/**", "authc");
	   shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
	   log.info("##################Filter加载完成##################");	
	   return filterChainDefinitionMap;
	}
	
	
	/**
	 * 重新加载权限
	 */
	@Override
   public void updatePermission() {

       synchronized (shiroFilterFactoryBean) {

           AbstractShiroFilter shiroFilter = null;
           try {
               shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean
                       .getObject();
           } catch (Exception e) {
               throw new RuntimeException(
                       "get ShiroFilter from shiroFilterFactoryBean error!");
           }

           PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter
                   .getFilterChainResolver();
           DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver
                   .getFilterChainManager();

           // 清空老的权限控制
           manager.getFilterChains().clear();

           shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
           shiroFilterFactoryBean
                   .setFilterChainDefinitionMap(initShiroFilter());
           // 重新构建生成
           Map<String, String> chains = shiroFilterFactoryBean
                   .getFilterChainDefinitionMap();
           for (Map.Entry<String, String> entry : chains.entrySet()) {
               String url = entry.getKey();
               String chainDefinition = entry.getValue().trim()
                       .replace(" ", "");
               manager.createChain(url, chainDefinition);
           }

           System.out.println("更新权限成功！！");
       }
   }

}
