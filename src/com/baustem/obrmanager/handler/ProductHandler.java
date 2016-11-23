package com.baustem.obrmanager.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.baustem.obrmanager.entity.Bundle;
import com.baustem.obrmanager.entity.Product;
import com.baustem.obrmanager.orm.Page;
import com.baustem.obrmanager.service.BundleService;
import com.baustem.obrmanager.service.ProductService;
import com.baustem.utils.AboutAceUtil;

@Controller
@RequestMapping("/product")
public class ProductHandler {
	
	@Autowired
	private ProductService productService;
	@Autowired
	private BundleService bndService;
	
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id,
			RedirectAttributes attiAttributes){
		try {
			productService.deleteById(id);
		} catch (Exception e) {
			attiAttributes.addFlashAttribute("message","删除失败");
			return "redirect:/product/list";
		}
		return "redirect:/product/list";
	}
	
	@RequestMapping("/{name}")
	@ResponseBody
	public String getUrl(@PathVariable("name") String productName,
			HttpServletResponse response,HttpServletRequest request){
		Product product = productService.getProductByName(productName);
		String url = product.getUrl();
		String repoXml = product.getRepoXml();
		List<Bundle> bundles = product.getBundles();
		if(bundles.size()==0){
			try {
				response.sendRedirect(request.getContextPath()+"/product/assign/"+product.getId());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String newRepoXml = AboutAceUtil.generateRepoXml(bundles);	
		if(repoXml==null||repoXml.length()!= newRepoXml.length()){
			product.setRepoXml(newRepoXml);
			repoXml = product.getRepoXml();
			productService.updateRepoXml(product);
			AboutAceUtil.storeRepoXml(request,product);
		}
		if(url==null||url.length()==0){
			url = AboutAceUtil.getProductUrl(request,product);
			product.setUrl(url);
			productService.updateUrl(product);
			AboutAceUtil.storeRepoXml(request,product);
		} 
		response.setContentType("text/json");
		response.setCharacterEncoding("utf-8");
		String result = "{\n\"name\":\""+product.getName()+"\",\n\"url\":\""+url+"\"\n}";
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.write(result);
		out.flush();
		
		return null;
		 
		
	}
	
	
	@RequestMapping("/repo/{id}")
	@ResponseBody
	public void repoXML(@PathVariable("id") Long id,
			HttpServletResponse response,
			HttpServletRequest request) {
		Product product = productService.getProductById(id);
		List<Bundle> bundles = product.getBundles();
		String repoXml = product.getRepoXml();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		if(bundles.size()==0){
			try {
				response.sendRedirect(request.getContextPath()+"/product/assign/"+id);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			String newRepoXml = AboutAceUtil.generateRepoXml(bundles);	
			if(repoXml==null||repoXml.length()!= newRepoXml.length()){
				product.setRepoXml(newRepoXml);
				repoXml = product.getRepoXml();
				productService.updateRepoXml(product);
				
			}
			
			PrintWriter out = null;
			Reader in = null;
			try {
				out = response.getWriter();
				in = new StringReader(repoXml);
				char[] buf = new char[2048];
				int b ;
				while((b=in.read(buf))>0){
					out.write(buf,0,b);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally{
				if(out!=null){
					out.close();
				}
				if(in!=null){
					try {
						in.close();
					} catch (IOException e) {
					}
				}
			}
		}
		
		
	}
	
	@RequestMapping(value="/uprelated/{id}",method=RequestMethod.POST)
	public String upRelated(@PathVariable("id") Long id,
			Product product,
			@RequestParam(value="bundles2",defaultValue="-1") List<Long> bundles,
			RedirectAttributes attributes){
		try {
			product.setId(id);
			productService.saveRelated(product);
		} catch (Exception e) {
			attributes.addFlashAttribute("message","关联Bundle失败!");
		}
		return "redirect:/product/list";
	}
	
	@RequestMapping("/assign/{id}")
	public String assign(Map<String,Object> map,
			@PathVariable("id") Long id){
		Product product = productService.getProductById(id);
		List<Bundle> bundles = bndService.getAllBundles();
		map.put("product", product);
		map.put("bundles", bundles); 
		return "product/assign";
	}
	
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public String create(Map<String,Object>map,
			RedirectAttributes attributes,
			Product product){
		productService.save(product);
		return "redirect:/product/list";
	}
	
	
	@RequestMapping("/input")
	public String add(Map<String,Object> map){
		Product product = new Product();
		map.put("product", product);
		return "product/input";
	}
	
	@RequestMapping("/list")
	public String list(@RequestParam(value="pageNo",required=false) String pageNoStr,
			Map<String,Object> map,
			HttpServletRequest request){
		int pageNo = 1,pageSize = 10;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {
		}
		Map<String, Object> requestParamsMap = WebUtils.getParametersStartingWith(request, "search_");
		Page<Product> page = productService.getPage(pageNo,pageSize,requestParamsMap);		
		String queryString = parseToQueryString(requestParamsMap);
		map.put("queryString", queryString);
		map.put("page", page);
		return "product/list";
	}


	private String parseToQueryString(Map<String, Object> requestParamsMap) {
		StringBuilder sb = new StringBuilder();
		String query = "";
		for (Entry<String,Object> entry : requestParamsMap.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			sb.append("search_").append(key)
			  					.append("=")
			  					.append(value)
			  					.append("&");
		}
		if(sb.length()>0){
			query = sb.substring(0,sb.lastIndexOf("&"));
		}
		return query;
	}
	
	
	

}
