package com.david.demo.conf;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManagerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

import com.david.demo.common.response.MessageCode;
import com.david.demo.common.response.WsResponse;
import com.david.demo.ldap.UserAuthoritiesPopulator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

@EnableWebFluxSecurity
public class SecurityConfig {



    //security的鉴权排除列表
    private static final String[] excludedAuthPages = {
            "/health",
           "/api/login",
           "/api/logout"
    };

    
    @Bean
    SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) throws Exception {
        http
             .authorizeExchange()
                .pathMatchers(excludedAuthPages).permitAll()  //无需进行权限过滤的请求路径
                .anyExchange().authenticated()
                .and()
            .exceptionHandling()
            	.authenticationEntryPoint(serverAuthenticationEntryPoint())  //基于http的接口请求鉴权失败
                .and()
            .csrf()
            	.disable()//必须支持跨域
            .logout()
            	.logoutUrl("/api/logout")
            	.logoutSuccessHandler(serverLogoutSuccessHandler());

        return http.build();
    }
    

    
    
	@Bean
	ReactiveAuthenticationManager authenticationManager(
			BaseLdapPathContextSource contextSource,
			UserAuthoritiesPopulator populator,
			LdapUserDetailsMapper ldapUserDetailsMapper) {

		BindAuthenticator authenticator = new BindAuthenticator(contextSource);

		FilterBasedLdapUserSearch basedLdapUserSearch = new FilterBasedLdapUserSearch(
				"", "sAMAccountName={0}", contextSource);
		authenticator.setUserSearch(basedLdapUserSearch);

		LdapAuthenticationProvider ldp = new LdapAuthenticationProvider(
				authenticator, populator);

		ldp.setUserDetailsContextMapper(ldapUserDetailsMapper);
		AuthenticationManager am = new ProviderManager(Arrays.asList(ldp));

		return new ReactiveAuthenticationManagerAdapter(am);
	}
    

    
    
   @Bean
    public LdapContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        Map<String, Object> config = new HashMap<String, Object>();

        //TODO 从配置文件中取数
        contextSource.setUrl("ldap://10.7.15.210:389/");
        contextSource.setBase("CN=Users,DC=DevTest,DC=com");
        contextSource.setUserDn("ldap_query@DevTest");
        contextSource.setPassword("Edavid.com");
        
       
      // contextSource.setAuthenticationStrategy(new DigestMd5DirContextAuthenticationStrategy());
        
        contextSource.setPooled(false);
        contextSource.setBaseEnvironmentProperties(config);
        contextSource.afterPropertiesSet();
        return contextSource;
    }
   
   @Bean  
   ServerSecurityContextRepository securityContextRepository() {
	   return new WebSessionServerSecurityContextRepository();
   }

    
    @Bean
    public LdapTemplate ldapTemplate() {
        return  new LdapTemplate(contextSource());
    }
    
    
    @Bean
	public ServerLogoutSuccessHandler serverLogoutSuccessHandler() {

		return new ServerLogoutSuccessHandler() {

			@Override
			public Mono<Void> onLogoutSuccess(WebFilterExchange exchange,
					Authentication authentication) {
				ServerHttpResponse response = exchange.getExchange()
						.getResponse();
				// 设置headers
				HttpHeaders httpHeaders = response.getHeaders();
				httpHeaders.add("Content-Type",
						"application/json; charset=UTF-8");
				httpHeaders.add("Cache-Control",
						"no-store, no-cache, must-revalidate, max-age=0");
				// 设置body
				WsResponse<String> wsResponse = WsResponse.success();
				byte[] dataBytes = {};
				ObjectMapper mapper = new ObjectMapper();
				try {
					wsResponse.setStatus(MessageCode.COMMON_SUCCESS);
					wsResponse.setResult("sucess");
					dataBytes = mapper.writeValueAsBytes(wsResponse);
				} catch (Exception ex) {
					ex.printStackTrace();
					JsonObject result = new JsonObject();
					result.addProperty("status",
							MessageCode.COMMON_FAILURE.getCode());
					result.addProperty("message", "异常");
					dataBytes = result.toString().getBytes();
				}
				DataBuffer bodyDataBuffer = response.bufferFactory().wrap(
						dataBytes);
				return response.writeWith(Mono.just(bodyDataBuffer));
			}
		};
	}
    
    
    @Bean
    ServerAuthenticationEntryPoint serverAuthenticationEntryPoint(){
    	return new ServerAuthenticationEntryPoint() {
			
			@Override
			public Mono<Void> commence(ServerWebExchange exchange,
					AuthenticationException e) {
				
				  	ServerHttpResponse response = exchange.getResponse();
		            response.setStatusCode(HttpStatus.UNAUTHORIZED);
		            response.getHeaders().add("Content-Type", "application/json; charset=UTF-8");
		            JsonObject result = new JsonObject();
		            result.addProperty("status", MessageCode.COMMON_AUTHORIZED_FAILURE.getCode());
		            result.addProperty("message", "身份鉴权失败");
		            byte[] dataBytes=result.toString().getBytes();
		            DataBuffer bodyDataBuffer = response.bufferFactory().wrap(dataBytes);
		            return response.writeWith(Mono.just(bodyDataBuffer));
			}
		};
    }
    
    
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
       return new RestTemplate();
    }


}
