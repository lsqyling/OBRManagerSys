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

import com.baustem.obrmanager.entity.User;
import com.baustem.obrmanager.mapper.UserMapper;
import com.baustem.obrmanager.orm.Page;
import com.baustem.utils.GenerateUserInfo;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Transactional(readOnly=true)
	public User getUserByName(String username) {
		User u = userMapper.getUserByName(username);
		return u;
	}

	public Page<User> getPage(int pageNo, int pageSize,
			Map<String, Object> paramsMap) {
		Map<String,Object> mybatisParams = parseParamsToMybatisMap(paramsMap);
		long totalRecord = userMapper.getTotalCount(mybatisParams);
		Page<User> page = new Page<User>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setTotalCount(totalRecord);
		
		int firstIndex = (page.getPageNo()-1)*page.getPageSize();
		mybatisParams.put("firstIndex", firstIndex);
		mybatisParams.put("items", pageSize);
		List<User> content = userMapper.getUserList(mybatisParams);
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

	public void save(User user) {
		User u = GenerateUserInfo.generateSaltPassword(user);
		userMapper.save(u);
	}

	public User getUserById(Long id) {
		return userMapper.getUserById(id);
	}

	public void update(User user) {
		User u = GenerateUserInfo.generateSaltPassword(user);
		userMapper.update(u);
	}

	public void deleteById(Long id) {
		userMapper.deleteById(id);
	}


}
