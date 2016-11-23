package com.baustem.obrmanager.router;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class OBRRouter extends AbstractRoutingDataSource {
	

	@Override
	protected Object determineCurrentLookupKey() {
		RouterToken token = RouterToken.getToken();
		if(token!=null){
			String tokenValue = token.getTokenValue();
			RouterToken.removeToken();
			return tokenValue;
		}
		
		return "normal";
	}

}
