package com.david.demo.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CasServerConfig {

	@Value("${security.cas.server.host}")
	private String host;

	@Value("${security.cas.server.login}")
	private String login;

	@Value("${security.cas.server.logout}")
	private String logout;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLogout() {
		return logout;
	}

	public void setLogout(String logout) {
		this.logout = logout;
	}

}
