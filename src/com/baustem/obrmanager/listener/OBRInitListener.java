package com.baustem.obrmanager.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.baustem.obrmanager.router.RouterToken;
import com.baustem.obrmanager.service.LogService;
import com.baustem.utils.TableUtil;

@Component
@SuppressWarnings("rawtypes")
public class OBRInitListener implements ApplicationListener {
	
	@Autowired
	private LogService logService;

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		
		if(event instanceof ContextRefreshedEvent){
			RouterToken token = new RouterToken();
			token.setTokenValue("log");
			RouterToken.bindToken(token);
			
			String tableName = TableUtil.generateTableName(0);
			logService.createTable(tableName);
			
			RouterToken.bindToken(token);
			tableName = TableUtil.generateTableName(1);
			logService.createTable(tableName);
			
			RouterToken.bindToken(token);
			tableName = TableUtil.generateTableName(2);
			logService.createTable(tableName);
			
		}
		
		
	}
	
	
	
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
	
	public LogService getLogService() {
		return logService;
	}
	

}
