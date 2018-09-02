package com.ithwua.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ithwua.service.IShiroService;
import com.ithwua.service.ISysPermissionIninService;
import com.ithwua.service.impl.ShiroServiceImpl;
import com.ithwua.service.impl.SysPermissionInitServiceImpl;

/**
 * shiro 主要配置信息
 * @author Wangshun
 * @since 2018年8月30日
 */
@Configuration
public class ShiroConfiguration {
	@Autowired
	ISysPermissionIninService sysPermissionIninService;
	
	private static final Logger log = LoggerFactory.getLogger(ShiroConfiguration.class);
	/**
	 * shiro管理生命周期的东西
	 * @return
	 */
	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		log.info("创建LifecycleBeanPostProcessor");
		return new LifecycleBeanPostProcessor();
	}
	/**
	 * 缓存管理器，采用本地的EhCache，下面有redis的
	 * 
	 * @return
	 */
/*	@Bean
    public EhCacheManager getEhCacheManager() {
		log.info("创建EhCacheManager");
        EhCacheManager em = new EhCacheManager();
        em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return em;
    }*/
	
	 /**
     * 加密器：这样一来数据库就可以是密文存储，为了演示我就不开启了
     *
     * @return HashedCredentialsMatcher
     */
//    @Bean
//    public HashedCredentialsMatcher hashedCredentialsMatcher() {
//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//        //散列算法:这里使用MD5算法;
//        hashedCredentialsMatcher.setHashAlgorithmName("md5");
//        //散列的次数，比如散列两次，相当于 md5(md5(""));
//        hashedCredentialsMatcher.setHashIterations(2);
//        return hashedCredentialsMatcher;
//    }
	
	
	   
	   /*@Bean
		public ISysPermissionIninService getShiroServiceImpl() {
			log.info("##################初始化ISysPermissionIninService######################");
			ISysPermissionIninService ininService  = new SysPermissionInitServiceImpl();
			return ininService;
		}*/
	
	/**
     * ShiroFilter<br/>
     * 注意这里参数中的 StudentService 和 IScoreDao 只是一个例子，因为我们在这里可以用这样的方式获取到相关访问数据库的对象，
     * 然后读取数据库相关配置，配置到 shiroFilterFactoryBean 的访问规则中。实际项目中，请使用自己的Service来处理业务逻辑。
     *
     * @param securityManager 安全管理器
     * @return ShiroFilterFactoryBean
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
    	log.info("创建ShiroFilterFactoryBean");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 如果不设置默认会自动寻找Web工程根目录下的"/login"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的连接
        shiroFilterFactoryBean.setSuccessUrl("/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/denied");
        //loadShiroFilterChain(shiroFilterFactoryBean);
        return shiroFilterFactoryBean;
    }
    
    /**
     * 加载shiroFilter权限控制规则（从数据库读取然后配置）
     * @param shiroFilterFactoryBean
     */
   private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean) {
	   log.info("创建EhCacheManager");
       /////////////////////// 下面这些规则配置最好配置到配置文件中 ///////////////////////
       // TODO 重中之重啊，过滤顺序一定要根据自己需要排序
       Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
       // 需要验证的写 authc 不需要的写 anon
       filterChainDefinitionMap.put("/resource/**", "anon");
       filterChainDefinitionMap.put("/install", "anon");
       filterChainDefinitionMap.put("/hello", "anon");
       // anon：它对应的过滤器里面是空的,什么都没做
       log.info("##################从数据库读取权限规则，加载到shiroFilter中##################");
       //Map<String, String> shiroInitFilterInfo = sysPermissionIninService.getShiroInitFilterInfo();
       // 不用注解也可以通过 API 方式加载权限规则
       Map<String, String> permissions = new LinkedHashMap<>();
       permissions.put("/users/find", "perms[user:find]");
       //filterChainDefinitionMap.putAll(shiroInitFilterInfo);
       //Map<String, String> permissions = new LinkedHashMap<>();
       //permissions.put("/users/find", "perms[user:find]");
       filterChainDefinitionMap.putAll(permissions);
       filterChainDefinitionMap.put("/**", "authc");
       shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
       log.info("##################Filter加载完成##################");
   }
	/**
	 * 自定义Realm
	 * @param cacheManager
	 * @return
	 */
   @Bean(name = "authRealm")
   public AuthRealm authRealm(ShiroRedisCacheManager redisCacheManager) {
	   log.info("创建authRealm");
       AuthRealm authRealm = new AuthRealm();
       //authRealm.setCacheManager(redisCacheManager);
       return authRealm;
   }
   /**
    *  声明SecurityManger类
    * @param authRealm
    * @return
    */
   @Bean(name = "securityManager")
   public DefaultWebSecurityManager getDefaultWebSecurityManager(AuthRealm authRealm) {
	   log.info("创建securityManager");
       DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
       defaultWebSecurityManager.setRealm(authRealm);
       // <!-- 用户授权/认证信息Cache, 采用EhCache 缓存 -->
       //defaultWebSecurityManager.setCacheManager(getEhCacheManager());
       //<!-- 用户授权/认证信息Cache, 采用Redis 缓存 -->
       defaultWebSecurityManager.setCacheManager(getShiroRedisCacheManager());
       return defaultWebSecurityManager;
   }
   /**
    * 开启AOP注解
    * @param securityManager
    * @return
    */
   @Bean
   public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(
           DefaultWebSecurityManager securityManager) {
	   log.info("开启AOP注解");
       AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
       advisor.setSecurityManager(securityManager);
       return advisor;
   }
   /**
    * RedisCacheManager类
    * @return
    */
   @Bean
   public ShiroRedisCacheManager getShiroRedisCacheManager() {
	   log.info("创建RedisCacheManager");
	   ShiroRedisCacheManager redisCacheManager = new ShiroRedisCacheManager();
	   return redisCacheManager;
   }
   /**
    * 加了这个类，权限注解才生效。。。。
    * @return
    */
   @Bean
   public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){
       return new DefaultAdvisorAutoProxyCreator();
   }

}
