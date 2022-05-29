package com.david.demo.ldap;

import java.util.Collection;

import javax.naming.NamingException;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsContextMapper extends LdapUserDetailsMapper {
	

    @Override
    public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {
 
    	String cName=null;
    	
    	try {
			 cName=(String)ctx.getAttributes().get("name".toLowerCase()).get();
		} catch (NamingException e) {
		}
    	User user = new User(username, "", authorities);
    	user.setCnName(cName);
    	
        return user;
    }
    
    
        public static  class User extends org.springframework.security.core.userdetails.User{

		private static final long serialVersionUID = 1L;
    	
		public User(String username, String password, boolean enabled,
				boolean accountNonExpired, boolean credentialsNonExpired,
				boolean accountNonLocked,
				Collection<? extends GrantedAuthority> authorities) {
			super(username, password, enabled, accountNonExpired, credentialsNonExpired,
					accountNonLocked, authorities);
		}
		
		
		public User(String username, String password,
				Collection<? extends GrantedAuthority> authorities) {
			super(username, password, authorities);
		}

		private String cnName;

		public String getCnName() {
			return cnName;
		}


		public void setCnName(String cnName) {
			this.cnName = cnName;
		}
		
		
		
    	
    }
}
