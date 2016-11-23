package com.baustem.obrmanager.shiro;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.baustem.obrmanager.entity.Authority;
import com.baustem.obrmanager.entity.User;
import com.baustem.obrmanager.service.UserService;

public class ShiroRealm extends AuthorizingRealm {
	
	@Autowired
	private UserService userSerivce;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection paramPrincipalCollection) {
		
		User user = (User) paramPrincipalCollection.getPrimaryPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Set<String> roleNames = new HashSet<String>();
		for (Authority auth : user.getRole().getAuthorities()) {
			String name = auth.getName();
			roleNames.add(name);
		}
		info.addRoles(roleNames);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token)
			throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();
		User user = userSerivce.getUserByName(username);
		if(user == null){
			throw new UnknownAccountException("此账户"+username+"不存在！");
		}
		if(!user.isEnabled()){
			throw new UnknownAccountException("此账户"+username+"未启用！");
		}
		Object principal = user;
		Object credentials = user.getPassword();
		String realmName = getName();
		ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
		
		return info;
	}
	
	@PostConstruct
	public void initCredentialsMatcher(){
		
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
		matcher.setHashAlgorithmName("MD5");
		matcher.setHashIterations(1024);
		setCredentialsMatcher(matcher);
		
		
	}
	

}
