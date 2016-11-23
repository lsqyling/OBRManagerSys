package com.baustem.obrmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baustem.obrmanager.entity.Authority;
import com.baustem.obrmanager.entity.Role;
import com.baustem.obrmanager.mapper.RoleMapper;
import com.baustem.obrmanager.orm.Page;

@Service
public class RoleService {
	
	@Autowired
	private RoleMapper roleMapper;
	

	public Page<Role> getPage(int pageNo, int pageSize) {
		long totalCount = roleMapper.getTotalRecord();
		Page<Role> page = new Page<Role>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setTotalCount(totalCount);
		
		int firstIndex = (page.getPageNo()-1)*page.getPageSize();
		int items = page.getPageSize();
		
		List<Role> roleList = roleMapper.getList(firstIndex,items);
		page.setContent(roleList);
		
		return page;
	}


	public List<Authority> getAllAuths() {
		return roleMapper.getAllAuths();
	}


	public Role getRoleById(Long id) {
		return roleMapper.getRoleById(id);
	}

	@Transactional
	public void save(Role role) {
		//通过中间表删除 roleid 对应的权限
		roleMapper.delete(role);
		List<Authority> authorities = role.getAuthorities();
		for (Authority auth : authorities) {
			Long authId = auth.getId();
			Long roleId = role.getId();
			roleMapper.save(roleId,authId);
		}
	}


	public void create(Role role) {
		roleMapper.create(role);
	}

	@Transactional
	public void deleteRole(Long id) {
		Role role = new Role();
		role.setId(id);
		roleMapper.delete(role);
		roleMapper.deleteRoles(role);
	}


	public List<Role> getAllRoles() {
		return roleMapper.getAllRoles();
	}

}
