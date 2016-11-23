package com.baustem.obrmanager.entity;

import java.util.ArrayList;
import java.util.List;


public class Product extends IdEntity {

	// 产品服务id
	private String serviceId;
	// 产品服务名
	private String name;
	// 产品服务描述
	private String description;
	// 产品对应库的url
	private String url;
	// 产品对应bundle repository.xml 内容 文件
	private String repoXml;
	//产品所包含的bundle
	private List<Bundle> bundles = new ArrayList<Bundle>();
	
	/**
	 * 根据需要产生适合的构造器
	 */
	public Product(String serviceId, String name, String description,
			String repoXml) {
		super();
		this.serviceId = serviceId;
		this.name = name;
		this.description = description;
		this.repoXml = repoXml;
	}
	public Product() {
		super();
	}
	
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
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
	
	public String getRepoXml() {
		return repoXml;
	}
	public void setRepoXml(String repoXml) {
		this.repoXml = repoXml;
	}
	
	public List<Bundle> getBundles() {
		return bundles;
	}
	
	public void setBundles(List<Bundle> bundles) {
		this.bundles = bundles;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public List<String> getBundles2() {
		List<String> bundles = new ArrayList<String>();
		for (Bundle bundle : this.bundles) {
			bundles.add(""+bundle.getId());
		}
		return bundles;
	}
	
	public void setBundles2(List<String> bundles) {
		this.bundles.clear();
		for (String bndId : bundles) {
			Bundle bnd = new Bundle();
			bnd.setId(Long.parseLong(bndId));
			this.bundles.add(bnd);
		}
	}
	//for testing 
	@Override
	public String toString() {
		return "Product [serviceId=" + serviceId + ", name=" + name
				+ ", description=" + description + ", url=" + url
				+ ", repoXml=" + repoXml + "]";
	}
	
}
