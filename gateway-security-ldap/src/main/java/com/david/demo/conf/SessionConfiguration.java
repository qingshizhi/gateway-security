package com.david.demo.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;
import org.springframework.web.server.session.CookieWebSessionIdResolver;
import org.springframework.web.server.session.WebSessionIdResolver;

@EnableRedisWebSession 
@Configuration
public class SessionConfiguration {

	   
	
	 @Bean
	    public WebSessionIdResolver webSessionIdResolver() {
	        CookieWebSessionIdResolver resolver = new CookieWebSessionIdResolver();
	        // 重写定义 cookie 名字
	        resolver.setCookieName("GATEWAY-SESSIONID");
	        return resolver;
	 }
	    

}