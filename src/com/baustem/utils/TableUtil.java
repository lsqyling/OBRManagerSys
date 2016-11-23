package com.baustem.utils;

import java.util.Calendar;
import java.util.List;

public class TableUtil {
	
	public static String generateSubSelect(List<String> tableNameList) {
		StringBuilder builder = new StringBuilder();
		for (String tableName : tableNameList) {
			builder.append("Select * from ").append("obr_log."+tableName).append(" union ");
		}
		return "("+builder.substring(0,builder.lastIndexOf(" union "))+")";
		
	}
	public static String generateTableName(int offset) {
		
		int yearOffset = (offset - (offset % 12))/12;
		
		offset = offset % 12;
		
		//logs_2016_10
		Calendar calendar = Calendar.getInstance();
		
		int year = calendar.get(Calendar.YEAR) + yearOffset;
		
		//0~11
		int month = calendar.get(Calendar.MONTH) + 1 + offset;
		
		if(month < 1) {
			month = month + 12;
			year--;
		}
		
		if(month > 12) {
			month = month - 12;
			year++;
		}
		
		return "obr_log.logs_"+year+"_"+month;
		
	}
	
	

}
