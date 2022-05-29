package com.david.demo.ldap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.stereotype.Component;

@Component
public class UserAuthoritiesPopulator implements LdapAuthoritiesPopulator {
   
    @Override
    public Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations dirContextOperations, String username) {
  
    	//TODO 获取权限信息
    	List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
  
        return authorities;
    }
}