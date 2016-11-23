package com.baustem.obrmanager.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baustem.obrmanager.entity.Log;

public interface LogMapper {

	void createTable(@Param("tableName") String tableName);

	List<String> getAllTableNames();
	
	long getSubRecord(@Param("subT") String subT);

	List<Log> getLogs(Map<String, Object> mybatisParam);

	void saveLog(Map<String, Object> mybatisParam);
	
	
	
	
	

}
