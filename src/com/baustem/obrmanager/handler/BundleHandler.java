package com.baustem.obrmanager.handler;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.baustem.obrmanager.entity.Bundle;
import com.baustem.obrmanager.entity.Product;
import com.baustem.obrmanager.orm.Page;
import com.baustem.obrmanager.service.BundleService;
@RequestMapping("/bundle")
@Controller
public class BundleHandler {
	
	@Autowired
	private BundleService bndService;
	
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.DELETE)
	public String delete(@PathVariable("id")Long id,
			RedirectAttributes attributes){
		Bundle bundle = bndService.getBndById(id);
		List<Integer> relates = bndService.getRelation(bundle);
		try {
			if(relates.size()>0)
				throw new RuntimeException("bundle.id="+id+"被关联");
			bndService.remoteDeleteBundle(bundle);
		} catch (Exception e) {
			attributes.addFlashAttribute("message", "注意：bundle.id:"+id+"有关联,删除失败！");
			return "redirect:/bundle/list";
		}
		return "redirect:/bundle/list";
	}
	
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public String upload(HttpServletRequest request,
			RedirectAttributes attributes) {
		
		CommonsMultipartResolver multiReso = new CommonsMultipartResolver(request.getSession().getServletContext());
		
		//判断是否有文件上传
		if(multiReso.isMultipart(request)){
			MultipartHttpServletRequest multiReq = (MultipartHttpServletRequest)request;
			Iterator<String> fileNames = multiReq.getFileNames();
			try {
				while(fileNames.hasNext()){
					MultipartFile file = multiReq.getFile(fileNames.next());
					if(file!=null){
						String filename = file.getOriginalFilename();
						File temp = new File(new File("").getAbsolutePath()+"/"+filename);
						file.transferTo(temp);
						bndService.uploadBundle(filename,temp);
						temp.delete();
					}
					
				}
			} catch (Exception e) {
				attributes.addFlashAttribute("message","添加/更新bundle失败");
				return "redirect:/bundle/list";
			}
		}else{
			attributes.addFlashAttribute("message","添加/更新bundle失败");
			return "redirect:/bundle/list";
		}
		return "redirect:/bundle/list";
	}
	
	
	
	
	@RequestMapping("/add")
	public String add(){
		return "bundle/input";
	}
	
	@RequestMapping("/list")
	public String list(@RequestParam(value="pageNo",required=false)String pageNoStr,
			HttpServletRequest request,
			Map<String,Object> map){
		int pageNo = 1,pageSize = 10;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {
		}
		Map<String, Object> reqParamMap = WebUtils.getParametersStartingWith(request, "search_");
		
		Page<Bundle> page = bndService.getPage(pageNo,pageSize,reqParamMap);
		String queryString = parseRequestParamToQueryString(reqParamMap);
		map.put("page", page);
		map.put("queryString", queryString);
		return "bundle/list";
	}

	private String parseRequestParamToQueryString(
			Map<String, Object> reqParamMap) {
		StringBuilder sb = new StringBuilder();
		String queryStr = "";
		
		for (Entry<String,Object> entry : reqParamMap.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			sb.append("search_").append(key)
								.append("=")
								.append(value)
								.append("&");
		}
		if(sb.length()>0){
			queryStr = sb.substring(0, sb.lastIndexOf("&"));
		}
		
		return queryStr;
	}
	
	
	
	
	
	

}
