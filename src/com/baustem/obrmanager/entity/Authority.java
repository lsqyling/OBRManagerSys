package com.baustem.obrmanager.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Authority extends IdEntity implements Serializable {

	private static final long serialVersionUID = 497877949458637864L;
	
	//权限的显示名字
	private String displayName;
	//权限的shiro 配置名字
	private String name;
	//权限的允许行为：即权限的具体内容
	private String permissions;
	//父权限
	private Authority parentAuthority;
	//URL
	private String url;
	//子权限
	private List<Authority> subAuthorities = new ArrayList<Authority>();
	
	public Authority() {
	}
	
	public Authority(Long id){
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public Authority getParentAuthority() {
		return parentAuthority;
	}

	public void setParentAuthority(Authority parentAuthority) {
		this.parentAuthority = parentAuthority;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Authority> getSubAuthorities() {
		return subAuthorities;
	}

	public void setSubAuthorities(List<Authority> subAuthorities) {
		this.subAuthorities = subAuthorities;
	}

	@Override
	public String toString() {
		return "Authority [displayName=" + displayName + ", name=" + name
				+ ", permissions=" + permissions + ", url=" + url + "]";
	}

	
}
