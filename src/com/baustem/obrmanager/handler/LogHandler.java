package com.baustem.obrmanager.handler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.baustem.obrmanager.entity.Log;
import com.baustem.obrmanager.orm.Page;
import com.baustem.obrmanager.service.LogService;

@RequestMapping("/log")
@Controller
public class LogHandler {
	
	@Autowired
	private LogService logService;
	
	@RequestMapping("/detail")
	public String detail(@RequestParam(value="pageNo",required=false) String pageNoStr,
			Map<String,Object> map,
			HttpServletRequest request){
		int pageNo = 1;
		int pageSize = 15;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {
		}
		Page<Log> page = logService.getPage(pageNo,pageSize);
		map.put("page", page);
		
		return "log/list";
	}
	
	
	

}
