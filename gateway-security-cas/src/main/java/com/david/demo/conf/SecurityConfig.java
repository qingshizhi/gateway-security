package com.david.demo.conf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.jasig.cas.client.validation.TicketValidationException;
import org.jasig.cas.client.validation.TicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManagerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

import com.david.demo.common.cas.CasAuthenticationToken;
import com.david.demo.common.response.MessageCode;
import com.david.demo.common.response.WsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;


@EnableWebFluxSecurity
public class SecurityConfig {
	
	 @Autowired 
	 private CasServerConfig casServerConfig;

	 @Autowired 
	 private CasServiceConfig casServiceConfig;


    //security的鉴权排除列表
    private static final String[] excludedAuthPages = {
            "/health",
           "/api/login",
           "/api/logut"
    };

    
    @Bean
    SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http,ServerAuthenticationEntryPoint casServerAuthenticationEntryPoint) throws Exception {
        http
             .authorizeExchange()
                .pathMatchers(excludedAuthPages).permitAll()  //无需进行权限过滤的请求路径
                .anyExchange().authenticated()
                .and()
            .exceptionHandling()
            	.authenticationEntryPoint(casServerAuthenticationEntryPoint)  //基于http的接口请求鉴权失败
                .and()
            .csrf()
            	.disable()//必须支持跨域
            //TODO 登出功能待实现
            .logout()
            	.logoutUrl(casServiceConfig.getLogout())
            	//.logoutHandler(serverLogoutHandler())
            	.logoutSuccessHandler(serverLogoutSuccessHandler());
            	
        return http.build();
    }
    

    
	@Bean
	public ServerAuthenticationEntryPoint casServerAuthenticationEntryPoint() {
		ServerAuthenticationEntryPoint entryPoint = new ServerAuthenticationEntryPoint() {
			@Override
			public Mono<Void> commence(ServerWebExchange exchange,AuthenticationException e) {			
				ServerHttpResponse response = exchange.getResponse();
		        //设置headers
		        HttpHeaders httpHeaders = response.getHeaders();
		        httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
		        httpHeaders.add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
		        //设置body
		        WsResponse<String> wsResponse = WsResponse.failure(MessageCode.COMMON_AUTHORIZED_FAILURE);
		        byte[]   dataBytes={};
		        try {
		            ObjectMapper mapper = new ObjectMapper();
		            dataBytes=mapper.writeValueAsBytes(wsResponse);
		        } catch (Exception ex){
		            ex.printStackTrace();
		        }
		        DataBuffer bodyDataBuffer = response.bufferFactory().wrap(dataBytes);
		        return response.writeWith(Mono.just(bodyDataBuffer));
		
			}
		};

		return entryPoint;
	}



    @Bean
    public TicketValidator cas20ServiceTicketValidator() {
      return new Cas20ServiceTicketValidator(casServerConfig.getHost());
    }
	
	@Bean
	public AuthenticationProvider authenticationProvider(TicketValidator ticketValidator) {

		AuthenticationProvider provider = new AuthenticationProvider() {

			@Override
			public boolean supports(Class<?> authentication) {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public Authentication authenticate(Authentication authentication)
					throws AuthenticationException {
				try {
					final Assertion assertion = ticketValidator.validate(authentication
							.getCredentials().toString(),casServiceConfig.getWebHost()+casServiceConfig.getLogin());

			    	//TODO 获取权限信息
			    	List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
					return  new CasAuthenticationToken(assertion.getPrincipal().toString(), "cas", authorities);
				}
				catch (final TicketValidationException e) {
					throw new BadCredentialsException(e.getMessage(), e);
				} catch (Exception e) {
					throw new BadCredentialsException(e.getMessage(), e);
				}
			}
		};
     

      return provider;
    }


    
    
    @Bean
    ReactiveAuthenticationManager authenticationManager(AuthenticationProvider authenticationProvider) {

    
        AuthenticationManager am = new ProviderManager(Arrays.asList(authenticationProvider));

        return new ReactiveAuthenticationManagerAdapter(am);
    }
    

   
   @Bean  
   ServerSecurityContextRepository securityContextRepository() {
	   
	   return new WebSessionServerSecurityContextRepository();
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



}
