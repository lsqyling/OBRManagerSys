package com.baustem.obrmanager.schedule;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.baustem.obrmanager.router.RouterToken;
import com.baustem.obrmanager.service.LogService;
import com.baustem.utils.TableUtil;

public class LogScheduler extends QuartzJobBean {

	private LogService logService;
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
		RouterToken token = new RouterToken();
		token.setTokenValue("log");
		RouterToken.bindToken(token);
		
		String tableName = TableUtil.generateTableName(1);
		logService.createTable(tableName);
		
		RouterToken.bindToken(token);
		tableName = TableUtil.generateTableName(2);
		logService.createTable(tableName);
		
		RouterToken.bindToken(token);
		tableName = TableUtil.generateTableName(3);
		logService.createTable(tableName);
		
	}
	
	
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
	
	public LogService getLogService() {
		return logService;
	}
	
	
	

}
