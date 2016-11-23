package com.baustem.utils;

import java.util.UUID;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.baustem.obrmanager.entity.User;

public class GenerateUserInfo {
	
	
	public static User generateSaltPassword(User user){
		String algorithm = "MD5";
		Object credentials = user.getPassword();
		String salt = UUID.randomUUID().toString();
		salt = salt.replaceAll("-", "");
		int hashIterations = 1024;
		ByteSource  bSalt = ByteSource.Util.bytes(salt);
		SimpleHash result = new SimpleHash(algorithm, credentials, bSalt, hashIterations);
		user.setSalt(salt);
		user.setPassword(result.toString());
		
		return user;
		
	}
	
	

}
