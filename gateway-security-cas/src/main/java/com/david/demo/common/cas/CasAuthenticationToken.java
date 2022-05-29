package com.david.demo.common.cas;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class CasAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 1L;

	private final String credentials;
	private final String principal;
	
	public CasAuthenticationToken( final String principal,
			final String credentials,
			Collection<? extends GrantedAuthority> authorities) throws Exception {
		
		super(authorities);
		this.credentials=credentials;
		this.principal=principal;
		if(!StringUtils.isEmpty(principal)){
			this.setAuthenticated(true);
		}else{
			if(StringUtils.isEmpty(credentials)){
				throw new Exception("credentials can not be empty");
			}
		}
	}

	

	@Override
	public Object getCredentials() {
		
		return this.credentials;
	}
	
	@Override
	public Object getPrincipal() {
		return this.principal;
	}
	
	
}

