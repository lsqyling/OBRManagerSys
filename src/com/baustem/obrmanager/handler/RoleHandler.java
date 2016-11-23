package com.baustem.obrmanager.handler;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baustem.obrmanager.entity.Authority;
import com.baustem.obrmanager.entity.Role;
import com.baustem.obrmanager.orm.Page;
import com.baustem.obrmanager.service.RoleService;

@RequestMapping("/role")
@Controller
public class RoleHandler {
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id,
			RedirectAttributes attributes){
		roleService.deleteRole(id);
		attributes.addFlashAttribute("message", "删除成功!");
		return "redirect:/role/list";
	}
	
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public String create(Map<String,Object> map,
			Role role,
			RedirectAttributes attributes){
		roleService.create(role);
		attributes.addFlashAttribute("message", "新建角色成功！");
		return "redirect:/role/list";
	}
	
	@RequestMapping("/input")
	public String input(Map<String,Object> map){
		Role role = new Role();
		map.put("role", role);
		return "role/input";
	}
	
	@RequestMapping("/list")
	public String list(@RequestParam(value="pageNo",required=false) String pageNoStr,
			Map<String,Object> map){
		int pageNo = 1;
		int pageSize = 10;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {
		}
		Page<Role> page = roleService.getPage(pageNo,pageSize);
		map.put("page", page);
		
		return "role/list";
	}
	
	@RequestMapping("/assign/{id}")
	public String assign(@PathVariable("id") Long id,Map<String,Object> map){
		List<Authority> parentAuthorities = roleService.getAllAuths();
		Role role = roleService.getRoleById(id);
		map.put("parentAuthorities", parentAuthorities);
		map.put("role", role);
		return "role/assign";
	}
	
	@RequestMapping(value="/assign/{id}",method=RequestMethod.PUT)
	public String assign(@PathVariable("id") Long id,
			Map<String,Object> map,
			Role role,
			@RequestParam(value="authorities2",defaultValue="-1") List<Long> authorities,
			RedirectAttributes attribute){
		roleService.save(role);
		attribute.addFlashAttribute("message", "设置权限成功！");
		return "redirect:/role/list";
	}
	
	

}
