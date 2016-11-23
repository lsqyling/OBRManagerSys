package com.baustem.filter;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FilterTest {
	
	private ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext*.xml");
	
	@Test
	public void testFilter(){
		ShiroFilterFactoryBean bean = ioc.getBean(ShiroFilterFactoryBean.class);
		System.out.println(bean);
	}
	
	
	
	
	

}
