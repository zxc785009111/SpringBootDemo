package com.ithwua.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/**
 * 这个@ConfigurationProperties这个注解就是加载配置文件 ，然后后面的prefix就是前缀
 * 总体的意思就是说加载配置文件中前缀是girl的属性,声明好属性，参数名保持一直就可以了，然后写上get/set方法就可以获取了。
 * @Component注解的作用就是说把这个类实例化到Spring容器中(和Service类似)
 * @author lenovo
 *
 */
@ConfigurationProperties(prefix="girl")
@Component
public class GirlProperties {
	
	private String cupSize;
	private Integer age;
	public String getCupSize() {
		return cupSize;
	}
	public void setCupSize(String cupSize) {
		this.cupSize = cupSize;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
}
