 package com.baustem.datasource;

import java.util.UUID;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;

public class SaltAndPassword {
	
	@Test
	public void testCreateSaltAndPassword(){
		
		String algorithm = "MD5";
		Object credentials = "123456";
		
		String salt = UUID.randomUUID().toString();
		
		salt = salt.replaceAll("-", "");
		
		int hashIterations = 1024;
		
		ByteSource  bSalt = ByteSource.Util.bytes("5d0188f7f6b94fed93dbbcf0babce8e5");
		
		SimpleHash result = new SimpleHash(algorithm, credentials, bSalt, hashIterations);
		
		System.out.println("salt="+salt);
		System.out.println("result="+result);
		
		
		
		
	}
	
	

}
