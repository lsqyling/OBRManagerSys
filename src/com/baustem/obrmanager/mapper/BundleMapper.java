package com.baustem.obrmanager.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baustem.obrmanager.entity.Bundle;
import com.baustem.obrmanager.entity.Product;

public interface BundleMapper {

	void batchSave(List<Bundle> bndList);

	long getTotalCount(Map<String, Object> mybatisParamsMap);

	List<Bundle> getBndList(Map<String, Object> mybatisParamsMap);

	void clearData();

	Bundle getBndById(@Param("id") Long id);

	List<Bundle> getAllBundles();

	void clearRelates();

	void clearBundles();

	void batchDeleteBnds(List<Bundle> removeBundles);

	List<Integer> getRelations(Bundle bundle);
	
	
	
	

}
