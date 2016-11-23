package com.baustem.obrmanager.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baustem.obrmanager.entity.Product;

public interface ProductMapper {

	long getTotalCount(Map<String, Object> mybatisMap);

	List<Product> getListProduct(Map<String, Object> mybatisMap);

	void save(Product product);

	Product getProductById(Long id);

	void clearRelated(Product product);

	void insertRelated(@Param("productId")Long productId, @Param("bndId")Long bndId);

	void updateRepoXml(Product product);

	void deleteProduct(Product product);

	Product getProductByName(String productName);

	void updateUrl(Product product);
	
	
	

}
