package com.baustem.obrmanager.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baustem.obrmanager.entity.Log;
import com.baustem.obrmanager.mapper.LogMapper;
import com.baustem.obrmanager.orm.Page;
import com.baustem.utils.TableUtil;

@Service
public class LogService {

	@Autowired
	private LogMapper logMapper;
	
	@Transactional
	public void createTable(String tableName) {
		logMapper.createTable(tableName);
	}
	
	private List<String> getAllTableName() {
		
		return logMapper.getAllTableNames();
	}
	
	@Transactional
	public void saveLog(Log log) {
		String tableName = TableUtil.generateTableName(0);
		Map<String,Object> mybatisParam = new HashMap<String,Object>();
		mybatisParam.put("tableName", tableName);
		mybatisParam.put("operator", log.getOperator());
		mybatisParam.put("methodName", log.getMethodName());
		mybatisParam.put("className", log.getClassName());
		mybatisParam.put("args", log.getArgs());
		mybatisParam.put("operateResult", log.getOperateResult());
		mybatisParam.put("returnValue", log.getReturnValue());
		mybatisParam.put("operateTime", log.getOperateTime());
		mybatisParam.put("errorMessage", log.getErrorMessage());
		
		logMapper.saveLog(mybatisParam);
	}
	
	private Long getTotalCount(){
		List<String> allTs = getAllTableName();
		String subT = TableUtil.generateSubSelect(allTs);
		long totalCount = logMapper.getSubRecord(subT);
		return totalCount;
	}

	@Transactional(readOnly=true)
	public Page<Log> getPage(int pageNo, int pageSize) {
		
		Long totalRecord = getTotalCount();
		Page<Log> page = new Page<Log>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setTotalCount(totalRecord);
		
		int firstIndex = (page.getPageNo()-1)*page.getPageSize();
		String tableName = TableUtil.generateTableName(0);
		
		Map<String,Object> mybatisParam = new HashMap<String,Object>();
		mybatisParam.put("tableName", tableName);
		mybatisParam.put("firstIndex", firstIndex);
		mybatisParam.put("items", pageSize);
		
		List<Log> content = logMapper.getLogs(mybatisParam);
		page.setContent(content);
		
		return page;
	}
	


	
	
	
	
	
	
}
