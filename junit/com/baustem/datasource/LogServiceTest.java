package com.baustem.datasource;

import org.junit.Test;

import com.baustem.obrmanager.entity.Log;
import com.baustem.obrmanager.service.LogService;

public class LogServiceTest {

	@Test
	public void testCreateTable() {
	}

	@Test
	public void testGetAllTableName() {
	}

	@Test
	public void testSaveLog() {
		Log log = new Log("sunwukong", "logServie()", "LogService.class", "abc", "xxx", "xxx", "xxx", "xxx");
		LogService logService = new LogService();
		logService.saveLog(log);
	}

	@Test
	public void testGetTotalCount() {
	}

}
