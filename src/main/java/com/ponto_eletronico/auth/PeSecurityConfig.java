package com.ponto_eletronico.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class PeSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String REALM = "PontoEletronico";

	@Autowired
	private AuthenticationProvider authProvider;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.authenticationProvider(authProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic()
				.authenticationEntryPoint(getBasicAuthEntryPoint()).and().logout().logoutUrl("/logout")
				.invalidateHttpSession(true);
	}

	@Bean
	BasicAuthenticationEntryPoint getBasicAuthEntryPoint() {
		BasicAuthenticationEntryPoint basicAuth = new BasicAuthenticationEntryPoint();
		basicAuth.setRealmName(REALM);
		return basicAuth;
	}
}
