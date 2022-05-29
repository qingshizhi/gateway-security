package com.david.demo.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;

import reactor.core.publisher.Mono;

import com.david.demo.common.cas.CasAuthenticationToken;
import com.david.demo.common.response.MessageCode;
import com.david.demo.common.response.WsResponse;
import com.david.demo.conf.CasServerConfig;
import com.david.demo.conf.CasServiceConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

@RequestMapping("/api")
@RestController
public class LoginController {
	

    @Autowired
    private ReactiveAuthenticationManager authenticationManager;
    
    @Autowired
    private ServerSecurityContextRepository securityContextRepository ;
    
	 @Autowired 
	 private CasServerConfig casServerConfig;

	 @Autowired 
	 private CasServiceConfig casServiceConfig;
    
    @GetMapping("/cid")
    public String  cid(WebSession session){
    	 session.getId();
    	 Mono<Void> m=session.changeSessionId();
    	  System.out.println(m);
         return session.getId();
    }
    

    

    //http://localhost:6013/api/login
    @GetMapping(value="/login")
    public  Mono<Void>  doLogin(ServerWebExchange exchange, @RequestParam(required=false)String ticket) throws Exception{
    	
        if(StringUtils.isEmpty(ticket)){
        	return this.onAuthenticationFailure(exchange, new BadCredentialsException("ticket is empty"));
        }else{
        	CasAuthenticationToken authenticationToken=new CasAuthenticationToken("", ticket, null);
        	// 验证
        	return authenticationManager.authenticate(authenticationToken)
        			.switchIfEmpty(Mono.defer(() -> Mono.error(new IllegalStateException("wrong" ))))
        			.flatMap(authentication -> onAuthenticationSuccess(authentication, exchange))
        			.onErrorResume(AuthenticationException.class, e -> this.onAuthenticationFailure(exchange, e));
        }

        }
    
    

    
	private Mono<Void> onAuthenticationSuccess(Authentication authentication,ServerWebExchange exchange) {

		SecurityContextImpl securityContext = new SecurityContextImpl();
		securityContext.setAuthentication(authentication);
		return this.securityContextRepository.save(exchange, securityContext)
				.then(this.onAuthenticationSuccess(exchange, authentication))
			.subscriberContext(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)));
	}
	
	
	private Mono<Void> onAuthenticationFailure(ServerWebExchange exchange,AuthenticationException e) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(HttpStatus.FOUND);
		String location;
		try {
			location = casServerConfig.getLogin()+"?service="+URLEncoder.encode(casServiceConfig.getWebHost()+casServiceConfig.getLogin(),"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			location=casServerConfig.getLogin()+"?service="+casServerConfig.getLogin();
		}
		response.getHeaders().add("Location", location);
		return response.writeAndFlushWith(Mono.empty());
	}
	
	
	private Mono<Void> onAuthenticationSuccess(ServerWebExchange exchange,Authentication authentication) {
		ServerHttpResponse response = exchange.getResponse();
        //设置headers
        HttpHeaders httpHeaders = response.getHeaders();
        httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
        httpHeaders.add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        //设置body
        WsResponse<User> wsResponse = WsResponse.success();
       byte[]   dataBytes={};
        ObjectMapper mapper = new ObjectMapper();
        try {
            String  umCde=authentication.getPrincipal().toString();
            User user=new User(umCde, authentication.getCredentials().toString(), true, true, true, true, authentication.getAuthorities());
            wsResponse.setStatus(MessageCode.COMMON_SUCCESS);
            wsResponse.setResult(user);
            dataBytes=mapper.writeValueAsBytes(wsResponse);
        }
        catch (Exception ex){
            ex.printStackTrace();
            JsonObject result = new JsonObject();
            result.addProperty("status", MessageCode.COMMON_FAILURE.getCode());
            result.addProperty("message", "授权异常");
            dataBytes=result.toString().getBytes();
        }
        DataBuffer bodyDataBuffer = response.bufferFactory().wrap(dataBytes);
        return response.writeWith(Mono.just(bodyDataBuffer));
	}
    
}






