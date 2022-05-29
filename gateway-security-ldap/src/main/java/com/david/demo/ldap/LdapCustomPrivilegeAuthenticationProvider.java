package com.david.demo.ldap;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.LdapAuthenticator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;

//@Component
public class LdapCustomPrivilegeAuthenticationProvider extends LdapAuthenticationProvider {
	
    public LdapCustomPrivilegeAuthenticationProvider(LdapAuthenticator authenticator, LdapAuthoritiesPopulator authoritiesPopulator) {
        super(authenticator, authoritiesPopulator);
    }

    @Override
    protected DirContextOperations doAuthentication(UsernamePasswordAuthenticationToken authentication) {
        String password = (String) authentication.getCredentials();
        try {
        	//TODO 在这里实现密码解密
           // String[] ps = password.split("\\.");
           // password = AesUtils.getInstance().decrypt(ps[0], ps[1]);
        } catch (Exception e) {
            throw new BadCredentialsException("密码错误！");
        }
        return super.doAuthentication(new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), password));
    }
}