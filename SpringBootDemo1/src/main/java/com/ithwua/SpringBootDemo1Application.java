package com.ithwua;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import com.battcn.swagger.annotation.EnableSwagger2Doc;
/**
 * SpringBoot启动程序，注意其所有的包必须在此类的同级目录或子目录下，否则会报错
 * @EnableCaching这个注解写了spring-data-cache相关注解才会生效 否则不生效
 * @author Wangshun
 * @since 2018年8月25日
 */
@SpringBootApplication
@MapperScan("com.ithwua.dao")//配置扫描mapper文件的位置
@EnableCaching
@EnableSwagger2Doc
public class SpringBootDemo1Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemo1Application.class, args);
	}
}
