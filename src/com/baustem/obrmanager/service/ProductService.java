package com.baustem.obrmanager.service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baustem.obrmanager.entity.Bundle;
import com.baustem.obrmanager.entity.Product;
import com.baustem.obrmanager.mapper.BundleMapper;
import com.baustem.obrmanager.mapper.ProductMapper;
import com.baustem.obrmanager.orm.Page;

@Service
public class ProductService {
	
	
	@Autowired
	private BundleMapper bndMapper;
	@Autowired
	private ProductMapper productMapper;
	
	
	
	public Page<Product> getPage(int pageNo, int pageSize,
			Map<String, Object> requestParamsMap) {
		Map<String, Object> mybatisMap = parseParamsToMybatisMap(requestParamsMap);
		long totalRecord = productMapper.getTotalCount(mybatisMap);
		
		Page<Product> page = new Page<Product>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setTotalCount(totalRecord);
		
		int firstIndex = (page.getPageNo()-1)*page.getPageSize();
		mybatisMap.put("firstIndex", firstIndex);
		mybatisMap.put("items", pageSize);
		
		List<Product> content = productMapper.getListProduct(mybatisMap);
		page.setContent(content);
		return page;
	}
	
	
	
	private Map<String, Object> parseParamsToMybatisMap(
			Map<String, Object> paramsMap) {
		Map<String,Object> map = new HashMap<String, Object>();
		Set<Entry<String, Object>> entrySet = paramsMap.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if(key.startsWith("LIKE_")){
				key = key.substring(key.indexOf("_")+1);
				value = "%"+value+"%";
			}
			if(key.startsWith("date")){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				try {
					value = format.parse((String)value);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			map.put(key, value);
			
		}
		return map;
	}


	@Transactional
	public void save(Product product) {
		productMapper.save(product);
	}


	@Transactional(readOnly = true)
	public Product getProductById(Long id) {
		return productMapper.getProductById(id);
	}


	@Transactional
	public void saveRelated(Product product) {
		productMapper.clearRelated(product);
		List<Bundle> bundles = product.getBundles();
		for (Bundle bundle : bundles) {
			Long productId = product.getId();
			Long bndId = bundle.getId();
			productMapper.insertRelated(productId,bndId);
			
		}
	}



	public void updateRepoXml(Product product) {
		productMapper.updateRepoXml(product);
	}


	@Transactional
	public void deleteById(Long id) {
		Product product = new Product();
		product.setId(id);
		productMapper.clearRelated(product);
		productMapper.deleteProduct(product);
	}



	public Product getProductByName(String productName) {
		return productMapper.getProductByName(productName);
	}



	public void updateUrl(Product product) {
		productMapper.updateUrl(product);
	}
	
	
	
	
	
	

}
