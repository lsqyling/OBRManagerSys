package com.baustem.obrmanager.router;

public class RouterToken {
	
	private static ThreadLocal<RouterToken> local = new ThreadLocal<RouterToken>();
	
	/**
	 * 根据tokenValue 决定路由器数据源返回时normal 还是log
	 */
	private String tokenValue;
	
	/**
	 * 从当前线程上获取令牌对象
	 */
	public static RouterToken getToken(){
		return local.get();
	}
	
	public static void bindToken(RouterToken token){
		local.set(token);
	}
	/**
	 * 将令牌从当前线程上移除
	 */
	
	public static void removeToken(){
		local.remove();
	}
	
	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}
	
	public String getTokenValue() {
		return tokenValue;
	}
	
	
	
	

}
