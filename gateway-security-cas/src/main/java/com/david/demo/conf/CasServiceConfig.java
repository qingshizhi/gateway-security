package com.david.demo.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CasServiceConfig {

	@Value("${security.cas.service.webHost}")
	private String webHost;

	@Value("${security.cas.service.login}")
	private String login;

	@Value("${security.cas.service.logout}")
	private String logout;

	private Boolean sendRenew = false;

	public String getWebHost() {
		return webHost;
	}

	public void setWebHost(String webHost) {
		this.webHost = webHost;
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

	public Boolean getSendRenew() {
		return sendRenew;
	}

	public void setSendRenew(Boolean sendRenew) {
		this.sendRenew = sendRenew;
	}

}
