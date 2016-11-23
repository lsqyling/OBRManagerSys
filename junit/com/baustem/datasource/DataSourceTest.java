package com.baustem.datasource;


import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DataSourceTest {

	private ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
	
	private DataSource dataSource = ioc.getBean("dataSource",DataSource.class);

	
	
	@Test
	public void testConnection() throws SQLException{
		
		Connection conn = dataSource.getConnection();
		System.out.println(conn);
		
		
	}
	
	
	
	
}
