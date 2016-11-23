package com.baustem.obrmanager.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.baustem.obrmanager.entity.Authority;
import com.baustem.obrmanager.entity.Role;
import com.baustem.obrmanager.entity.User;
import com.baustem.obrmanager.navigation.Navigation;
import com.baustem.obrmanager.orm.Page;
import com.baustem.obrmanager.service.RoleService;
import com.baustem.obrmanager.service.UserService;


@RequestMapping("/user")
@Controller
public class UserHandler {
	
	@Autowired
	private ResourceBundleMessageSource messageSource;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public String delete(@PathVariable("id") Long id,RedirectAttributes attributes){
		userService.deleteById(id);
		attributes.addFlashAttribute("message", "删除成功！");
		return "redirect:/user/list";
		
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public String update(@PathVariable("id") Long id,
			User user,RedirectAttributes attributes){
		userService.update(user);
		attributes.addFlashAttribute("message", "修改成功！");
		return "redirect:/user/list";
	}
	
	@RequestMapping(value="/input/{id}",method=RequestMethod.GET)
	public String input(@PathVariable("id") Long id,
			Map<String,Object> map){
		User user = userService.getUserById(id);
		map.put("user", user);
		List<Role> roles = roleService.getAllRoles();
		map.put("roles", roles);
		
		Map<String,Object> status = new HashMap<String, Object>();
		status.put("0", "无效");
		status.put("1", "有效");
		map.put("allStatus", status);
		return "user/input";
	}
	
	@RequestMapping(value="/",method=RequestMethod.POST)
	public String save(User user,RedirectAttributes attributes){
		userService.save(user);
		attributes.addFlashAttribute("message","成功创建用户！");
		return "redirect:/user/list";
	}
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String create(Map<String,Object> map){
		map.put("user", new User());
		List<Role> roles = roleService.getAllRoles();
		map.put("roles", roles);
		Map<String,String> status = new HashMap<String, String>();
		status.put("0", "无效");
		status.put("1", "有效");
		map.put("allStatus", status);
		return "user/input";
	}
	
	
	@RequestMapping("/list")
	public String list(@RequestParam(value="pageNo",required=false) String pageNoStr,
			Map<String,Object> map,
			HttpServletRequest request){
		int pageNo = 1;
		int pageSize = 10;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {
		}
		
		Map<String, Object> paramsMap = WebUtils.getParametersStartingWith(request, "search_");
		Page<User> page = userService.getPage(pageNo,pageSize,paramsMap);
		String queryString = parseParamsToQueryString(paramsMap);
		map.put("queryString", queryString);
		map.put("page", page);
		return "user/list";
	}
	
	
	
	private String parseParamsToQueryString(Map<String, Object> paramsMap) {
		StringBuilder sb = new StringBuilder();
		String query = "";
		for (Entry<String,Object> entry : paramsMap.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			sb.append("search_").append(key)
								.append("=")
								.append(value)
								.append("&");
		}
		if(sb.length()>0){
			query = sb.substring(0, sb.lastIndexOf("&"));
		}
		
		return query;
	}



	@ResponseBody
	@RequestMapping("/menus")
	public List<Navigation> menuList(HttpSession session){
		List<Navigation> navigations = new ArrayList<Navigation>();
		Navigation top = new Navigation(Long.MAX_VALUE,"OSGI管理系统");
		
		navigations.add(top);
		//session 中获取user
		User user = (User) session.getAttribute("user");
		Map<Long,Navigation> parentNavigations = new HashMap<Long,Navigation>();
		
		for (Authority auth : user.getRole().getAuthorities()) {
			//创建一般的Authority 对应的Navigation
			Navigation navi = new Navigation(auth.getId(),auth.getDisplayName());
			navi.setUrl(auth.getUrl());
			//获取父权限
			Authority parentAuth = auth.getParentAuthority();
			Long id = parentAuth.getId();
			Navigation parentNavi = parentNavigations.get(id);
			if(parentNavi == null){
				//创建一个新的
				parentNavi = new Navigation(id,parentAuth.getDisplayName());
				//设置state 为
				parentNavi.setState("opened");
				//父权限 对应的navigation 需要放入top 的children中
				top.getChildren().add(parentNavi);
				parentNavigations.put(id, parentNavi);
			}
			parentNavi.getChildren().add(navi);
		}
		
		return navigations;
	}
	
	@RequestMapping("/login")
	public String login(@RequestParam("username") String name,
			@RequestParam("password") String password,
			HttpSession session,
			RedirectAttributes attributes,
			Locale locale){
		
		Subject currentUser = SecurityUtils.getSubject();
		
		UsernamePasswordToken token = new UsernamePasswordToken(name,password);
		token.setRememberMe(true);
		
		if(!currentUser.isAuthenticated()){
			
			try {
				currentUser.login(token);
				User user = (User) currentUser.getPrincipals().getPrimaryPrincipal();
				session.setAttribute("user", user);
				
			} catch (AuthenticationException e) {
				String message = messageSource.getMessage("error.user.login", null,locale);
				attributes.addFlashAttribute("message", message);
				return "redirect:/index";
			}
		}
		return "home/success";
	}
	
	

}
