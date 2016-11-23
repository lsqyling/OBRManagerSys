package com.baustem.obrmanager.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Role extends IdEntity implements Serializable {

	private static final long serialVersionUID = 3213616310597612010L;
	// 角色名称
	private String name;
	// 角色描述
	private String description;
	// 角色的状态：是否可用
	private boolean enabled;
	// 角色拥有的权限
	private List<Authority> authorities = new ArrayList<Authority>();
	// 该角色分配给了哪些用户
	private Set<User> users = new HashSet<User>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public void setAuthorities2(List<String> authorities){
		this.authorities.clear();
		for(String authorityId: authorities){
			this.authorities.add(new Authority(Long.parseLong(authorityId)));
		}
	}
	
	public List<String> getAuthorities2(){
		List<String> authorites = new ArrayList<String>();
		for(Authority authority:this.authorities){
			authorites.add("" + authority.getId());
		}
		
		return authorites;
	}

	/**
	 * testing toString()
	 */
	@Override
	public String toString() {
		return "Role [name=" + name + ", description=" + description
				+ ", enabled=" + enabled + "]";
	}
	
	
	
	

}
