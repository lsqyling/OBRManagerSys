package com.baustem.obrmanager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baustem.obrmanager.entity.Authority;
import com.baustem.obrmanager.entity.Role;

public interface RoleMapper {

	long getTotalRecord();

	List<Role> getList(@Param("firstIndex")int firstIndex,@Param("items") int items);

	List<Authority> getAllAuths();

	Role getRoleById(Long id);
	
	void delete(Role role);

	void save(@Param("roleId")Long roleId, @Param("authId") Long authId);

	void create(Role role);

	void deleteRoles(Role role);

	List<Role> getAllRoles();
	
	
	
	
	

}
