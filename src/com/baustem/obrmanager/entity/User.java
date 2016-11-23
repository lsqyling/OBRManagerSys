package com.baustem.obrmanager.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class User extends IdEntity implements Serializable {

	private static final long serialVersionUID = 3425795849743288050L;
	
	
	private String name;
	private String password;
	private boolean enabled;
	/**
	 * 加密的盐值
	 */
	private String salt;
	//该用户拥有的角色
	private Role role;
	
	public User() {
		super();
	}
	
	public User(Long id){
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public Collection<String> getRoleList(){
		Collection<String> roles = new ArrayList<String>();
		
		if(role != null){
			for (Authority auth : role.getAuthorities()) {
				roles.add(auth.getName());
			}
		}
		
		return roles;
	}
	
	/**
	 * 用于测试的 toString()
	 */ 
	@Override
	public String toString() {
		return "User [name=" + name + ", password=" + password + ", enabled="
				+ enabled + ", salt=" + salt + "]";
	}
	
	
	
	
	
	
	
	
	
	
	

}
