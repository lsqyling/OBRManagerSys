package com.baustem.obrmanager.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baustem.obrmanager.entity.User;

public interface UserMapper {

	User getUserByName(@Param("username") String username);

	long getTotalCount(Map<String, Object> mybatisParams);

	List<User> getUserList(Map<String, Object> mybatisParams);

	void save(User user);

	User getUserById(@Param("id")Long id);

	void update(User user);

	void deleteById(@Param("id")Long id);

}
